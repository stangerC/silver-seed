/**
 * Created by bliao on 2015/12/10.
 */
"use strict";
var jsGen = angular.module("jsGen", ["ngLocale", "ngRoute", "ngAnimate", "ngResource", "ngCookies", "ui.validate", "genTemplates", "angularFileUpload"]);
jsGen.config(["$httpProvider", "app", function (e, t) {
    var a = 0, i = !1, n = {count: 0, total: 0};
    n.cancel = function () {
        a = 0, i = !1, this.count = 0, this.total = 0, t.loading(!1, this)
    }, e.defaults.transformRequest.push(function (e) {
        return a += 1, n.count = a, n.total += 1, i || window.setTimeout(function () {
            !i && a > 0 && (i = !0, t.loading(!0, n))
        }, 1e3), e
    }), e.defaults.transformResponse.push(function (e) {
        return a -= 1, n.count = a, i && 0 === a && n.cancel(), e
    }), e.interceptors.push(function () {
        return{response: function (e) {
            var a, i = e.data;
            return angular.isObject(i) && (t.timestamp = i.timestamp, a = !i.ack && i.error), a ? (t.toast.error(a.message, a.name), t.q.reject(i)) : e
        }, responseError: function (e) {
            var a = e.data || e, i = e.status || "", n = a.message || (angular.isObject(a) ? "Error!" : a);
            return t.toast.error(n, i), t.q.reject(a)
        }}
    })
}]).run(["app", "$q", "$rootScope", "$location", "$timeout", "$filter", "getFile", "JSONKit", "toast", "timing", "cache", "restAPI", "sanitize", "mdParse", "mdEditor", "CryptoJS", "promiseGet", "myConf", "anchorScroll", "isVisible", "applyFn", "param", "store", "i18n-zh", function (e, t, a, i, n, l, o, r, s, c, u, d, p, g, m, v, f, h, b, w, y, k, T, x) {
    function S() {
        var e = P.viewWidth = E.width();
        P.viewHeight = E.height(), P.isPocket = 480 > e, P.isPhone = 768 > e, P.isTablet = !P.isPhone && 980 > e, P.isDesktop = e >= 980
    }

    function M() {
        d.index.get({}, function (t) {
            e.timeOffset = Date.now() - t.timestamp, t = t.data, t.title2 = t.description, t.info.angularjs = angular.version.full.replace(/\-build.*$/, ""), e.union(P, t), e.version = P.info.version || "", e.upyun = P.user && P.user.upyun, e.checkUser()
        })
    }

    var L = {stopUnload: !1, nextUrl: ""}, P = a.global = {isAdmin: !1, isEditor: !1, isLogin: !1, info: {}}, E = $(window);
    e.q = t, e.store = T, e.toast = s, e.param = k, e.timing = c, e.location = i, e.timeout = n, e.timeOffset = 0, e.timestamp = Date.now(), e.filter = l, e.locale = x, e.anchorScroll = b, e.isVisible = w, e.getFile = o, e.cache = u, e.restAPI = d, e.sanitize = p, e.mdParse = g, e.mdEditor = m, e.CryptoJS = v, e.promiseGet = f, e.myConf = h, e.rootScope = a, angular.extend(e, r), e.loading = function (e) {
        a.loading.show = e, y()
    }, e.validate = function (t, a) {
        var i = [], n = [];
        return t.$broadcast("genTooltipValidate", i, a), e.each(i, function (e) {
            e.validate && e.$invalid && n.push(e)
        }), 0 === n.length ? (e.validate.errorList = null, t.$broadcast("genTooltipValidate", i, !0)) : e.validate.errorList = n, !e.validate.errorList
    }, e.checkDirty = function (t, a, i) {
        var n = e.union(t);
        return n && a && i ? (e.intersect(n, i), e.each(n, function (e, t, i) {
            angular.equals(e, a[t]) && delete i[t]
        }), e.removeItem(n, void 0), L.stopUnload = !e.isEmpty(n)) : L.stopUnload = !1, L.stopUnload ? n : null
    }, e.checkUser = function () {
        P.isLogin = !!P.user, P.isAdmin = P.user && 5 === P.user.role, P.isEditor = P.user && P.user.role >= 4
    }, e.clearUser = function () {
        P.user = null, e.checkUser()
    }, e.checkFollow = function (t) {
        var a = P.user || {};
        t.isMe = t._id === a._id, t.isFollow = !t.isMe && !!e.findItem(a.followList, function (e) {
            return e === t._id
        })
    }, a.loading = {show: !1}, a.validateTooltip = {validate: !0, validateMsg: x.VALIDATE}, a.unSaveModal = {confirmBtn: x.BTN_TEXT.confirm, confirmFn: function () {
        return L.stopUnload && L.nextUrl && (L.stopUnload = !1, n(function () {
            window.location.href = L.nextUrl
        }, 100)), !0
    }, cancelBtn: x.BTN_TEXT.cancel, cancelFn: !0}, a.$on("$locationChangeStart", function (e, t) {
        L.stopUnload ? (e.preventDefault(), L.nextUrl = t, a.unSaveModal.modal(!0)) : L.nextUrl = ""
    }), a.goBack = function () {
        window.history.go(-1)
    }, a.logout = function () {
        d.user.get({ID: "logout"}, function () {
            P.user = null, e.checkUser(), i.path("/")
        })
    }, a.followMe = function (t) {
        d.user.save({ID: t._id}, {follow: !t.isFollow}, function (a) {
            a.follow ? (P.user.followList.push(t._id), e.toast.success(x.USER.followed + t.name, x.RESPONSE.success)) : e.findItem(P.user.followList, function (a, i, n) {
                return a === t._id ? (n.splice(i, 1), e.toast.success(x.USER.unfollowed + t.name, x.RESPONSE.success), !0) : void 0
            }), t.fans += t.isFollow ? -1 : 1, t.isFollow = !t.isFollow
        })
    }, E.resize(y.bind(null, S)), c(function () {
        Date.now() - e.timestamp - e.timeOffset >= 24e4 && M()
    }, 12e4), S(), M()
}]), jsGen.factory("i18n-zh", ["$locale", function (e) {
    return angular.extend(e, {RESET: {locked: "申请解锁", passwd: "找回密码", email: "请求信息已发送到您的邮箱，请查收。"}, RESPONSE: {success: "请求成功", error: "请求失败"}, VALIDATE: {required: "必填！", minlength: "太短！", maxlength: "太长！", min: "太小！", max: "太大！", more: "太多！", email: "Email无效！", pattern: "格式无效！", username: "有效字符为汉字、字母、数字、下划线，以汉字或小写字母开头！", minname: "长度应大于5字节，一个汉字3字节！", maxname: "长度应小于15字节，一个汉字3字节！", repasswd: "密码不一致！", url: "URL无效！", tag: "标签错误，不能包含“,”、“，”和“、”"}, BTN_TEXT: {confirm: "确定", cancel: "取消", remove: "删除", goBack: "返回"}, TIMING: {goHome: "秒钟后自动返回主页"}, HOME: {title: "我的主页", index: " 更新，阅读时间线：", mark: "我的收藏", article: "我的文章", comment: "我的评论", follow: "我的关注", fans: "我的粉丝"}, ADMIN: {index: "网站信息", user: "用户管理", tag: "标签管理", article: "文章管理", comment: "评论管理", message: "消息管理", global: "网站设置", updated: "成功更新 ", noUpdate: "设置暂无变更"}, ARTICLE: {title: "添加/编辑文章", preview: "预览：", reply: "评论：", removed: "成功删除 ", updated: "成功更新 ", noUpdate: "文章暂无变更", added: "成功保存 ", markdown: "Markdown简明语法", marked: "已收藏 ", unmarked: "已取消收藏 ", favored: "已支持 ", unfavored: "已取消支持 ", opposed: "已反对 ", unopposed: "已取消反对 ", highlight: "置顶 ", unhighlight: "取消置顶 "}, USER: {title: "的主页", login: "用户登录", reset: "用户信息找回", register: "用户注册", article: "的文章", fans: "的粉丝", followed: "已关注 ", unfollowed: "已取消关注 ", email: "验证邮件已发送到新邮箱，通过验证后才保存修改", updated: "用户信息更新成功", noUpdate: "用户信息暂无变更", noLogin: "您还未登录"}, TAG: {title: "热门标签", removed: "成功删除 ", updated: "成功更新 ", noUpdate: "标签暂无变更"}, FILTER: {role: ["禁言", "待验证", "会员", "组员", "编辑", "管理员"], follow: ["关注", "已关注"], favor: ["支持", "已支持"], mark: ["收藏", "已收藏"], oppose: ["反对", "已反对"], highlight: ["置顶", "取消置顶"], turn: ["开启", "关闭"], edit: ["添加", "编辑"], gender: {male: "男", female: "女"}}, DATETIME: {second: "秒", minute: "分", hour: "时", day: "天", month: "月", year: "年", fullD: "yyyy年MM月dd日 HH:mm", shortD: "MM-dd HH:mm", dayAgo: "天前", hourAgo: "小时前", minuteAgo: "分钟前", secondAgo: "刚刚"}, UPLOAD: {fileType: "文件类型无效，仅允许png、gif、jpg文件！"}}), e
}]), jsGen.constant("app", {version: Date.now()}).provider("getFile", ["app", function (e) {
    this.html = function (t) {
        return"/static/tpl/" + t + "?v=" + e.version
    }, this.md = function (t) {
        return"/static/md/" + t + "?v=" + e.version
    }, this.$get = function () {
        return{html: this.html, md: this.md}
    }
}]).config(["$routeProvider", "$locationProvider", function (e, t) {
    var a = {templateUrl: "index.html", controller: "indexCtrl"}, i = {templateUrl: "login.html", controller: "userLoginCtrl"}, n = {templateUrl: "register.html", controller: "userRegisterCtrl"}, l = {templateUrl: "user.html", controller: "homeCtrl"}, o = {templateUrl: "admin.html", controller: "adminCtrl"}, r = {templateUrl: "article-editor.html", controller: "articleEditorCtrl"}, s = {templateUrl: "index.html", controller: "tagCtrl"}, c = {templateUrl: "reset.html", controller: "userResetCtrl"}, u = {templateUrl: "user.html", controller: "userCtrl"}, d = {templateUrl: "article.html", controller: "articleCtrl"}, p = {templateUrl: "collection.html", controller: "collectionCtrl"};
    e.when("/hots", a).when("/update", a).when("/latest", a).when("/T:ID", a).when("/tag/:TAG", a).when("/login", i).when("/register", n).when("/reset", c).when("/home", l).when("/home/:OP", l).when("/admin", o).when("/admin/:OP", o).when("/tag", s).when("/add", r).when("/A:ID/edit", r).when("/user/U:ID", u).when("/user/U:ID/:OP", u).when("/U:ID", u).when("/U:ID/:OP", u).when("/A:ID", d).when("/C:ID", p).when("/", a).otherwise({redirectTo: "/"}), t.html5Mode(!0).hashPrefix("!")
}]), jsGen.factory("restAPI", ["$resource", function (e) {
    return{index: e("/api/index/:OP"), user: e("/api/user/:ID/:OP"), article: e("/api/article/:ID/:OP"), tag: e("/api/tag/:ID/:OP")}
}]).factory("cache", ["$cacheFactory", function (e) {
    return{user: e("user", {capacity: 20}), article: e("article", {capacity: 100}), comment: e("comment", {capacity: 500}), list: e("list", {capacity: 100})}
}]).factory("myConf", ["$cookieStore", "JSONKit", function (e, t) {
    function a(e, t) {
        return null == e ? t : e
    }

    function i(i, n) {
        return function (l, o, r) {
            o = t.toStr(o) + i, r = a(r, n);
            var s = a(e.get(o), r);
            return null != l && s !== a(l, r) && (e.put(o, l), s = l), s
        }
    }

    return{pageSize: i("PageSize", 10), sumModel: i("sumModel", !1)}
}]).factory("anchorScroll", function () {
    function e(e, a, i) {
        var n = $(window).height();
        e = $(e), i = i > 0 ? i : n / 10, $("html, body").animate({scrollTop: a ? e.offset().top - i : e.offset().top + e.outerHeight() + i - n}, {duration: 200, easing: "linear", complete: function () {
            t(e) || e[0].scrollIntoView(!!a)
        }})
    }

    function t(e) {
        function t(e) {
            return e > o && r > e
        }

        e = $(e);
        var a = $(window), i = a.height(), n = e.offset().top, l = e.outerHeight(), o = a.scrollTop(), r = o + i;
        return t(n + (l > i ? i : l) / 2) ? !0 : l > i ? t(n + l - i / 2) : !1
    }

    return{toView: e, inView: t}
}).factory("isVisible", function () {
    return function (e) {
        var t = e[0].getBoundingClientRect();
        return Boolean(t.bottom - t.top)
    }
}).factory("applyFn", ["$rootScope", function (e) {
    return function (t, a) {
        t = angular.isFunction(t) ? t : angular.noop, a = a && a.$apply ? a : e, t(), a.$$phase || a.$apply()
    }
}]).factory("timing", ["$rootScope", "$q", "$exceptionHandler", function (e, t, a) {
    function i(i, n, l) {
        var o, r = 0, s = t.defer(), c = s.promise;
        return i = angular.isFunction(i) ? i : angular.noop, n = parseInt(n, 10), l = parseInt(l, 10), l = l >= 0 ? l : 0, o = window.setInterval(function () {
            if (r += 1, l && r >= l)window.clearInterval(o), s.resolve(i(r, l, n)); else try {
                i(r, l, n)
            } catch (t) {
                s.reject(t), a(t)
            }
            e.$$phase || e.$apply()
        }, n), c.$timingId = o, c
    }

    return i.cancel = function (e) {
        return e && e.$timingId ? (clearInterval(e.$timingId), !0) : !1
    }, i
}]).factory("promiseGet", ["$q", function (e) {
    return function (t, a, i, n) {
        var l, o = e.defer();
        return l = i && n && n.get(i), l ? o.resolve(l) : a.get(t, function (e) {
            i && n && n.put(i, e), o.resolve(e)
        }, function (e) {
            o.reject(e.error)
        }), o.promise
    }
}]).factory("getList", ["restAPI", "cache", "promiseGet", function (e, t, a) {
    return function (i) {
        return a({ID: i, s: 10}, e.article, i, t.list)
    }
}]).factory("getArticle", ["restAPI", "cache", "promiseGet", function (e, t, a) {
    return function (i) {
        return a({ID: i}, e.article, i, t.article)
    }
}]).factory("getUser", ["restAPI", "cache", "promiseGet", function (e, t, a) {
    return function (i) {
        return a({ID: i}, e.user, i, t.user)
    }
}]).factory("getMarkdown", ["$http", function (e) {
    return e.get("/static/md/markdown.md", {cache: !0})
}]).factory("toast", ["$log", "JSONKit", function (e, t) {
    var a = {}, i = ["info", "error", "success", "warning"];
    return angular.forEach(i, function (i) {
        a[i] = function (a, n) {
            var l = e[i] || e.log;
            n = t.toStr(n), l(a, n), a = angular.isObject(a) ? angular.toJson(a) : t.toStr(a), toastr[i](a, n)
        }
    }), toastr.options = angular.extend({positionClass: "toast-bottom-full-width"}, a.options), a.clear = toastr.clear, a
}]).factory("pretty", function () {
    return window.prettyPrint
}).factory("param", function () {
    return $.param
}).factory("CryptoJS", function () {
    return window.CryptoJS
}).factory("utf8", function () {
    return window.utf8
}).factory("store", function () {
    return window.store
}).factory("JSONKit", function () {
    return window.JSONKit
}).factory("mdParse", ["JSONKit", function (e) {
    return function (t) {
        return window.marked(e.toStr(t))
    }
}]).factory("sanitize", ["JSONKit", function (e) {
    var t = Sanitize, a = t.Config, i = [new t({}), new t(a.RESTRICTED), new t(a.BASIC), new t(a.RELAXED)];
    return function (t, a) {
        var n = document.createElement("div"), l = document.createElement("div");
        return a = a >= 0 ? a : 3, n.innerHTML = e.toStr(t), l.appendChild(i[a].clean_node(n)), l.innerHTML
    }
}]).factory("mdEditor", ["mdParse", "sanitize", "pretty", "JSONKit", function (e, t, a, i) {
    return function (n, l) {
        n = i.toStr(n);
        var o = new Markdown.Editor({makeHtml: function (a) {
            return t(e(a), l)
        }}, n), r = angular.element(document.getElementById("wmd-preview" + n));
        return o.hooks.chain("onPreviewRefresh", function () {
            angular.forEach(r.find("code"), function (e) {
                e = angular.element(e), e.parent().is("pre") || e.addClass("prettyline")
            }), r.find("pre").addClass("prettyprint"), a()
        }), o
    }
}]), jsGen.filter("placeholder", ["JSONKit", function (e) {
    return function (t) {
        return e.toStr(t) || "-"
    }
}]).filter("match", ["$locale", function (e) {
    return function (t, a) {
        return e.FILTER[a] && e.FILTER[a][t] || ""
    }
}]).filter("switch", ["$locale", function (e) {
    return function (t, a) {
        return e.FILTER[a] && e.FILTER[a][+!!t] || ""
    }
}]).filter("checkName", ["JSONKit", function (e) {
    return function (t) {
        var a = /^[(\u4e00-\u9fa5)a-z][(\u4e00-\u9fa5)a-zA-Z0-9_]{1,}$/;
        return t = e.toStr(t), a.test(t)
    }
}]).filter("length", ["utf8", "JSONKit", function (e, t) {
    return function (a) {
        return a = t.toStr(a), e.stringToBytes(a).length
    }
}]).filter("cutText", ["utf8", "JSONKit", function (e, t) {
    return function (a, i) {
        a = t.toStr(a).trim();
        var n = e.stringToBytes(a);
        return i = i > 0 ? i : 0, n.length > i && (n.length = i, a = e.bytesToString(n), a = a.slice(0, -2) + "…"), a
    }
}]).filter("formatDate", ["$filter", "$locale", function (e, t) {
    return function (a, i) {
        var n = Date.now() - a, l = e("date");
        return i ? l(a, t.DATETIME.fullD) : n > 2592e5 ? l(a, t.DATETIME.shortD) : n > 864e5 ? Math.floor(n / 864e5) + t.DATETIME.dayAgo : n > 36e5 ? Math.floor(n / 36e5) + t.DATETIME.hourAgo : n > 6e4 ? Math.floor(n / 6e4) + t.DATETIME.minuteAgo : t.DATETIME.secondAgo
    }
}]).filter("formatTime", ["$locale", function (e) {
    return function (t) {
        function a(e) {
            return n = l % e, l = (l - n) / e
        }

        var i = "", n = 0, l = t > 0 ? Math.round(+t) : Math.floor(Date.now() / 1e3), o = e.DATETIME;
        return a(60), i = n + o.second, a(60), i = (n > 0 ? n + o.minute : "") + i, a(24), i = (n > 0 ? n + o.hour : "") + i, l > 0 ? l + o.day + i : i
    }
}]).filter("formatBytes", ["$locale", function () {
    return function (e) {
        return e = e > 0 ? e : 0, e ? 1024 > e ? e + "B" : 1048576 > e ? (e / 1024).toFixed(3) + " KiB" : 1073741824 > e ? (e / 1048576).toFixed(3) + " MiB" : (e / 1073741824).toFixed(3) + " GiB" : "-"
    }
}]), jsGen.directive("genParseMd", ["mdParse", "sanitize", "pretty", "isVisible", "$timeout", function (e, t, a, i, n) {
    return function (l, o, r) {
        function s(i) {
            angular.isDefined(i) && (i = e(i), i = t(i), o.html(i), angular.forEach(o.find("code"), function (e) {
                e = angular.element(e), e.parent().is("pre") || e.addClass("prettyline")
            }), o.find("pre").addClass("prettyprint"), o.find("a").attr("target", function () {
                return this.host !== location.host ? "_blank" : void 0
            }), a())
        }

        l.$watch(r.genParseMd, function (e) {
            i(o) ? s(e) : n(function () {
                s(e)
            }, 500)
        })
    }
}]).directive("genTabClick", function () {
    return{link: function (e, t, a) {
        var i = a.genTabClick;
        t.bind("click", function () {
            t.parent().children().removeClass(i), t.addClass(i)
        })
    }}
}).directive("genPagination", function () {
    return{scope: !0, templateUrl: "gen-pagination.html", link: function (e, t, a) {
        e.$watchCollection(a.genPagination, function (t) {
            if (angular.isObject(t)) {
                var a = 1, i = [], n = Math.ceil(t.total / t.pageSize) || 1;
                if (a = t.pageIndex >= 1 ? t.pageIndex : 1, a = n >= a ? a : n, i[0] = a, 6 >= a)for (; i[0] > 1;)i.unshift(i[0] - 1); else i.unshift(i[0] - 1), i.unshift(i[0] - 1), i.unshift("…"), i.unshift(2), i.unshift(1);
                if (5 >= n - a)for (; i[i.length - 1] < n;)i.push(i[i.length - 1] + 1); else i.push(i[i.length - 1] + 1), i.push(i[i.length - 1] + 1), i.push("…"), i.push(n - 1), i.push(n);
                e.prev = a > 1 ? a - 1 : 0, e.next = n > a ? a + 1 : 0, e.total = t.total, e.pageIndex = a, e.showPages = i, e.pageSize = t.pageSize, e.perPages = t.sizePerPage || [10, 20, 50], e.path = t.path && t.path + "?p="
            }
        }), e.paginationTo = function (t, a) {
            !e.path && t > 0 && (a = a || e.pageSize, e.$emit("genPagination", t, a))
        }
    }}
}).directive("genModal", ["$timeout", function (e) {
    var t = 0;
    return{scope: !0, transclude: !0, templateUrl: "gen-modal.html", link: function (a, i, n) {
        function l(e) {
            return function () {
                var t = p(e) ? e() : !0;
                r(!t)
            }
        }

        function o() {
            var e = $(window), t = c.children(), a = .382 * (e.height() - t.outerHeight()), i = {};
            i.marginTop = a > 0 ? a : 0, t.css(i)
        }

        function r(t) {
            c.modal(t ? "show" : "hide"), t && e(o)
        }

        var s, c = i.children(), u = ["Confirm", "Cancel", "Delete"], d = a.$eval(n.genModal), p = angular.isFunction;
        d.cancelFn = d.cancelFn || !0, d.backdrop = d.backdrop || !0, d.show = d.show || !1, d.modal = r, a.$watch(function () {
            return d
        }, function (e) {
            angular.extend(a, e)
        }, !0), a.id = a.id || n.genModal + "-" + t++, angular.forEach(u, function (e) {
            var t = e.toLowerCase(), i = t + "Cb", n = t + "Fn", o = t + "Btn";
            a[i] = d[n] && l(d[n]), a[o] = d[o] || d[n] && e
        }), c.on("shown.bs.modal", function () {
            s = !0
        }), c.on("hidden.bs.modal", function () {
            s && p(d.cancelFn) && d.cancelFn(), s = !1
        }), c.modal(d)
    }}
}]).directive("genTooltip", ["$timeout", "isVisible", function (e, t) {
    return{require: "?ngModel", link: function (a, i, n, l) {
        function o(e) {
            if (l.validate = u && d.validate && t(i), l.validate) {
                var a = l.$name && l.$name + " " || "";
                e && d.validateMsg && angular.forEach(l.$error, function (e, t) {
                    a += e && d.validateMsg[t] && d.validateMsg[t] + ", " || ""
                }), a = a.slice(0, -2) || n.originalTitle || n.title, n.$set("dataOriginalTitle", a ? a : ""), c(!!e)
            } else c(!1)
        }

        function r(t) {
            return e(function () {
                o(l.$invalid)
            }), t
        }

        function s() {
            i.off(".tooltip").removeData("bs.tooltip"), i.tooltip(d)
        }

        function c(e) {
            i.hasClass("invalid-error") !== e && (i[e ? "addClass" : "removeClass"]("invalid-error"), i.tooltip(e ? "show" : "hide"))
        }

        var u = !1, d = a.$eval(n.genTooltip) || {};
        "inner" === d.container ? d.container = i : "ngView" === d.container && (d.container = i.parents(".ng-view")[0] || i.parents("[ng-view]")[0]), d.validate ? (d.template = '<div class="tooltip validate-tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>', d.trigger = "manual", d.placement = d.placement || "right", l ? (l.$formatters.push(r), l.$parsers.push(r)) : a.$watch(function () {
            return n.originalTitle || n.dataOriginalTitle
        }, c), i.bind("focus", function () {
            i.trigger("input"), i.trigger("change")
        }), a.$on("genTooltipValidate", function (e, t, a) {
            u = !a, l && (angular.isArray(t) && t.push(l), o(l.$invalid))
        })) : d.click && i.bind("click", function () {
            i.tooltip(d.click)
        }), i.bind("hidden.bs.tooltip", s), s()
    }}
}]).directive("genMoving", ["anchorScroll", function (e) {
    return{link: function (t, a, i) {
        function n() {
            var e = a.find("textarea");
            e.is(e) && e.css({height: "auto", width: "100%"})
        }

        var l = t.$eval(i.genMoving);
        l.appendTo = function (e) {
            a.appendTo($(e)), n()
        }, l.prependTo = function (e) {
            a.prependTo($(e)), n()
        }, l.childrenOf = function (e) {
            return $(e).find(a).is(a)
        }, l.scrollIntoView = function (t, i) {
            e.toView(a, t, i)
        }, l.inView = function () {
            return e.inView(a)
        }
    }}
}]).directive("genSrc", ["isVisible", function (e) {
    return{priority: 99, link: function (t, a, i) {
        i.$observe("genSrc", function (t) {
            if (t && e(a)) {
                var n = new Image;
                n.onload = function () {
                    i.$set("src", t)
                }, n.src = t
            }
        })
    }}
}]).directive("genUploader", ["$fileUploader", "app", function (e, t) {
    return{scope: !0, templateUrl: "gen-uploader.html", link: function (a, i, n) {
        {
            var l = a.$eval(n.genUploader);
            l.fileType
        }
        a.triggerUpload = function () {
            setTimeout(function () {
                i.find(".upload-input").click()
            })
        }, a.clickImage = l.clickImage || angular.noop;
        var o = a.uploaded = [], r = a.uploader = e.create({scope: l.scope || a, url: l.url, formData: [
            {policy: l.policy, signature: l.signature}
        ], filters: [function (e) {
            var a = !0, i = e.name.split(".");
            return i = i.length > 1 ? i.slice(-1)[0] : "", (!i || l.allowFileType.indexOf(i.toLowerCase()) < 0) && (a = !1, t.toast.warning(t.locale.UPLOAD.fileType)), a
        }]});
        r.bind("complete", function (e, a, i) {
            var n = t.parseJSON(a.response) || {};
            if (~[200, 201].indexOf(a.status)) {
                var r = t.union(i.file, n);
                r.url = l.baseUrl + r.url, o.push(r), i.remove()
            } else i.progress = 0, t.toast.warning(n.message, n.code)
        })
    }}
}]), jsGen.controller("indexCtrl", ["app", "$scope", "$routeParams", "getList", function (e, t, a, i) {
    function n() {
        var i = e.location.path().slice(1).split("/");
        a.TAG || /^T[0-9A-Za-z]{3,}$/.test(i[0]) ? (r = e.restAPI.tag, t.other._id = i[0], t.other.title = a.TAG || i[0], t.parent.viewPath = "") : (r = e.restAPI.article, t.parent.viewPath = i[0] || "latest"), o = a.TAG || i[0] || "latest"
    }

    function l() {
        var i = {ID: o, p: a.p, s: a.s || s.pageSize(null, "index", 20)};
        e.promiseGet(i, r, e.param(i), e.cache.list).then(function (a) {
            var i = a.pagination || {};
            a.tag && (t.other.title = a.tag.tag, t.other._id = a.tag._id), i.path = e.location.path(), i.pageSize = s.pageSize(i.pageSize, "index"), t.pagination = i, t.articleList = a.data
        })
    }

    var o = "", r = e.restAPI.article, s = e.myConf, c = e.rootScope.global;
    c.title2 = c.description, t.parent = {getTpl: "index-article.html", viewPath: "latest", sumModel: s.sumModel(null, "index", !1)}, t.other = {}, t.pagination = {}, t.setListModel = function () {
        var a = t.parent;
        a.sumModel = s.sumModel(!a.sumModel, "index"), s.pageSize(a.sumModel ? 20 : 10, "index"), e.location.search({})
    }, n(), l(), i("comment").then(function (a) {
        a = e.union(a.data), e.each(a, function (t) {
            t.content = e.filter("cutText")(t.content, 180)
        }), t.hotComments = a.slice(0, 6)
    })
}]).controller("tagCtrl", ["app", "$scope", "$routeParams", "getList", function (e, t, a, i) {
    var n = e.restAPI.tag, l = e.myConf, o = {p: a.p, s: a.s || l.pageSize(null, "tag", 50)};
    e.rootScope.global.title2 = e.locale.TAG.title, t.parent = {getTpl: "index-tag.html"}, t.pagination = {}, e.promiseGet(o, n, e.param(o), e.cache.list).then(function (a) {
        var i = a.pagination || {};
        i.path = e.location.path(), i.pageSize = l.pageSize(i.pageSize, "tag"), i.sizePerPage = [50, 100, 200], t.pagination = i, t.tagList = a.data
    }), i("comment").then(function (a) {
        a = e.union(a.data), e.each(a, function (t) {
            t.content = e.filter("cutText")(t.content, 180)
        }), t.hotComments = a.slice(0, 6)
    })
}]).controller("userLoginCtrl", ["app", "$scope", function (e, t) {
    e.clearUser(), e.rootScope.global.title2 = e.locale.USER.login, t.login = {logauto: !0, logname: "", logpwd: ""}, t.reset = {title: "", type: ""}, t.submit = function () {
        if (e.validate(t)) {
            var a = e.union(t.login);
            a.logtime = Date.now() - e.timeOffset, a.logpwd = e.CryptoJS.SHA256(a.logpwd).toString(), a.logpwd = e.CryptoJS.HmacSHA256(a.logpwd, "jsGen").toString(), a.logpwd = e.CryptoJS.HmacSHA256(a.logpwd, a.logname + ":" + a.logtime).toString(), e.restAPI.user.save({ID: "login"}, a, function (a) {
                e.rootScope.global.user = a.data, e.checkUser(), t.$destroy(), e.location.path("/home")
            }, function (a) {
                t.reset.type = a.error.name, t.reset.title = e.locale.RESET[a.error.name]
            })
        }
    }
}]).controller("userResetCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i() {
        t.timingModal.modal(!0), n = e.timing(function (e, a) {
            t.parent.timing = a - e
        }, 1e3, t.parent.timing), n.then(function () {
            t.timingModal.modal(!1), e.location.search({}).path("/")
        })
    }

    var n, l = e.locale;
    e.rootScope.global.title2 = l.USER.reset, t.reset = {name: "", email: "", request: a.req}, t.parent = {title: l.RESET[a.type], timing: 5}, t.timingModal = {confirmBtn: l.BTN_TEXT.goBack, confirmFn: function () {
        return e.timing.cancel(n), e.timing(null, 100, 1).then(function () {
            e.location.search({}).path("/")
        }), !0
    }, cancelBtn: l.BTN_TEXT.cancel, cancelFn: function () {
        return e.timing.cancel(n)
    }}, t.submit = function () {
        e.validate(t) && e.restAPI.user.save({ID: "reset"}, t.reset, function () {
            e.toast.success(l.RESET.email, l.RESPONSE.success), i()
        })
    }, ["locked", "passwd"].indexOf(a.type) < 0 && e.restAPI.user.get({ID: "reset", OP: a.req}, function () {
        e.toast.success(3 + l.TIMING.goHome, l.RESPONSE.success), e.timing(null, 1e3, 3).then(function () {
            e.location.search({}).path("/home")
        })
    }, i)
}]).controller("userRegisterCtrl", ["app", "$scope", function (e, t) {
    var a = e.filter, i = a("length"), n = e.rootScope.global;
    e.clearUser(), n.title2 = e.locale.USER.register, t.user = {name: "", email: "", passwd: "", passwd2: ""}, t.checkName = function (e, t) {
        return a("checkName")(t.$value)
    }, t.checkMin = function (e, t) {
        return i(t.$value) >= 5
    }, t.checkMax = function (e, t) {
        return i(t.$value) <= 15
    }, t.submit = function () {
        var a = t.user;
        if (e.validate(t)) {
            var i = {name: a.name, email: a.email};
            i.passwd = e.CryptoJS.SHA256(a.passwd).toString(), i.passwd = e.CryptoJS.HmacSHA256(i.passwd, "jsGen").toString(), e.restAPI.user.save({ID: "register"}, i, function (a) {
                e.rootScope.global.user = a.data, e.checkUser(), t.$destroy(), e.location.path("/home")
            })
        }
    }
}]).controller("homeCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i(e) {
        switch (e) {
            case"follow":
            case"fans":
                return"user-list.html";
            case"detail":
                return"user-edit.html";
            case"article":
            case"comment":
            case"mark":
                return"user-article.html";
            default:
                return"user-article.html"
        }
    }

    var n = e.rootScope.global;
    return n.isLogin ? (n.title2 = e.locale.HOME.title, t.user = n.user, void(t.parent = {getTpl: i(a.OP), isMe: !0, viewPath: a.OP || "index"})) : e.location.search({}).path("/")
}]).controller("userCtrl", ["app", "$scope", "$routeParams", "getUser", function (e, t, a, i) {
    function n() {
        switch (a.OP) {
            case"fans":
                return"user-list.html";
            case"article":
                return"user-article.html";
            default:
                return"user-article.html"
        }
    }

    e.rootScope.global.title2 = e.locale.USER.title, t.parent = {getTpl: n(), isMe: !1, viewPath: a.OP || "index"}, i("U" + a.ID).then(function (a) {
        t.user = a.data, e.rootScope.global.title2 = t.user.name + e.locale.USER.title
    })
}]).controller("userListCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    var i = e.restAPI.user, n = e.myConf, l = e.locale, o = {ID: a.ID && "U" + a.ID || a.OP, OP: a.OP || "fans", p: a.p, s: a.s || n.pageSize(null, "user", 20)};
    t.parent = {title: ""}, e.promiseGet(o, i, e.param(o), e.cache.list).then(function (i) {
        var r = i.pagination || {};
        r.path = e.location.path(), r.pageSize = n.pageSize(r.pageSize, "user"), t.pagination = r, e.each(i.data, function (t) {
            e.checkFollow(t)
        }), t.parent.title = a.ID ? i.user.name + l.USER[o.OP] : l.HOME[o.OP], t.userList = i.data
    })
}]).controller("userArticleCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i() {
        var i = {ID: a.ID && "U" + a.ID || a.OP, OP: a.OP || (a.ID ? "article" : "index"), p: a.p, s: a.s || l.pageSize(null, "home", 20)};
        e.promiseGet(i, n, e.param(i), e.cache.list).then(function (n) {
            var s = 0, c = n.pagination || {};
            if (c.path = e.location.path(), c.pageSize = l.pageSize(c.pageSize, "home"), t.pagination = c, a.ID)t.parent.title = n.user.name + o.USER[i.OP]; else {
                var u = r.user || {};
                e.each(n.data, function (e) {
                    n.readtimestamp > 0 && (e.read = e.updateTime < n.readtimestamp, s += !e.read), e.isAuthor = e.author._id === u._id
                }), t.parent.title = "index" !== i.OP ? o.HOME[i.OP] : s + o.HOME.index + e.filter("date")(n.readtimestamp, "medium")
            }
            t.articleList = n.data
        })
    }

    var n = e.restAPI.user, l = e.myConf, o = e.locale, r = e.rootScope.global;
    t.parent = {sumModel: l.sumModel(null, "index", !1), title: ""}, t.pagination = {}, t.removeArticle = null, t.removeArticleModal = {confirmBtn: o.BTN_TEXT.confirm, confirmFn: function () {
        var a = t.removeArticle;
        return e.restAPI.article.remove({ID: a._id}, function () {
            e.findItem(t.articleList, function (t, i, n) {
                return t._id === a._id ? (n.splice(i, 1), e.toast.success(o.ARTICLE.removed + a.title, o.RESPONSE.success), !0) : void 0
            }), t.removeArticle = null
        }), !0
    }, cancelBtn: o.BTN_TEXT.cancel, cancelFn: function () {
        return t.removeArticle = null, !0
    }}, t.setListModel = function () {
        var a = t.parent;
        a.sumModel = l.sumModel(!a.sumModel, "home"), l.pageSize(a.sumModel ? 20 : 10, "home"), e.location.search({})
    }, t.remove = function (e) {
        (e.isAuthor || r.isEditor) && (t.removeArticle = e, t.removeArticleModal.modal(!0))
    }, i()
}]).controller("userEditCtrl", ["app", "$scope", function (e, t) {
    function a() {
        i = e.union(o.user), e.each(i.tagsList, function (e, t, a) {
            a[t] = e.tag
        }), i = e.intersect(e.union(s), i), t.user = e.union(i), e.checkDirty(s, i, t.user)
    }

    var i = {}, n = e.locale, l = e.filter, o = e.rootScope.global, r = l("length"), s = {avatar: "", name: "", sex: "", email: "", desc: "", passwd: "", tagsList: [""]};
    t.sexArray = ["male", "female"], t.checkName = function (e, t) {
        return l("checkName")(t.$value)
    }, t.checkMin = function (e, t) {
        return r(t.$value) >= 5
    }, t.checkMax = function (e, t) {
        return r(t.$value) <= 15
    }, t.checkDesc = function (e, t) {
        return r(t.$value) <= o.SummaryMaxLen
    }, t.checkTag = function (e, t) {
        var a = t.$value || "";
        return a = angular.isString(a) ? a.split(/[,，、]/) : a, a.length <= o.UserTagsMax
    }, t.checkPwd = function (e, a) {
        var i = a.$value || "";
        return i === (t.user.passwd || "")
    }, t.getTag = function (e) {
        var a = t.user.tagsList;
        a.indexOf(e.tag) < 0 && a.length < o.UserTagsMax && (t.user.tagsList = a.concat(e.tag))
    }, t.reset = function () {
        t.user = e.union(i)
    }, t.verifyEmail = function () {
        e.restAPI.user.save({ID: "reset"}, {request: "role"}, function () {
            e.toast.success(n.RESET.email, n.RESPONSE.success)
        })
    }, t.submit = function () {
        var l = e.union(t.user);
        e.validate(t) && (l = e.checkDirty(s, i, l), e.isEmpty(l) ? e.toast.info(n.USER.noUpdate) : (l.passwd && (l.passwd = e.CryptoJS.SHA256(l.passwd).toString(), l.passwd = e.CryptoJS.HmacSHA256(l.passwd, "jsGen").toString()), l.email && (e.restAPI.user.save({ID: "reset"}, {email: l.email, request: "email"}, function () {
            e.toast.success(n.USER.email, n.RESPONSE.success)
        }), delete l.email), e.isEmpty(l) ? a() : e.restAPI.user.save({}, l, function (t) {
            e.union(o.user, t.data), a(), e.toast.success(n.USER.updated, n.RESPONSE.success)
        })))
    }, t.$watchCollection("user", function (t) {
        e.checkDirty(s, i, t)
    }), a()
}]).controller("articleCtrl", ["app", "$scope", "$routeParams", "mdEditor", "getList", "getMarkdown", function (e, t, a, i, n, l) {
    function o(t) {
        var a = w._id;
        angular.isObject(t) && (t.isAuthor = a === t.author._id, t.isMark = !!e.findItem(t.markList, function (e) {
            return e._id === a
        }), t.isFavor = !!e.findItem(t.favorsList, function (e) {
            return e._id === a
        }), t.isOppose = !!e.findItem(t.opposesList, function (e) {
            return e._id === a
        }), e.each(t.commentsList, function (e) {
            o(e)
        }))
    }

    function r() {
        return p.isLogin || e.toast.error(d.USER.noLogin), p.isLogin
    }

    function s() {
        var e = t.comment, a = t.article;
        e.replyToComment = "", e.title = "评论：" + v(a.title, p.TitleMaxLen - 9), e.content = "", e.refer = a._id, t.replyMoving.prependTo("#comments")
    }

    var c = "A" + a.ID, u = e.myConf, d = e.locale, p = e.rootScope.global, g = e.filter, m = g("length"), v = g("cutText"), f = e.cache.comment, h = e.cache.list, b = e.restAPI.article, w = p.user || {};
    w = {_id: w._id, name: w.name, avatar: w.avatar}, t.parent = {wmdPreview: !1, contentBytes: 0, markdownHelp: ""}, t.comment = {title: "", content: "", refer: "", replyToComment: ""}, t.replyMoving = {}, t.commentMoving = {}, t.markdownModal = {title: d.ARTICLE.markdown, cancelBtn: d.BTN_TEXT.goBack}, t.validateTooltip = e.union(e.rootScope.validateTooltip), t.validateTooltip.placement = "bottom", t.removeCommentModal = {confirmBtn: d.BTN_TEXT.confirm, confirmFn: function () {
        var a = t.removeComment;
        return e.restAPI.article.remove({ID: a._id}, function () {
            e.findItem(t.article.commentsList, function (i, n, l) {
                return i._id === a._id ? (l.splice(n, 1), t.article.comments = l.length, e.toast.success(d.ARTICLE.removed + a.title, d.RESPONSE.success), !0) : void 0
            }), t.removeComment = null
        }), !0
    }, cancelBtn: d.BTN_TEXT.cancel, cancelFn: function () {
        return t.removeComment = null, !0
    }}, t.remove = function (e) {
        (e.isAuthor || p.isEditor) && (t.removeComment = e, t.removeCommentModal.modal(!0))
    }, t.wmdHelp = function () {
        l.success(function (e) {
            t.parent.markdownHelp = e, t.markdownModal.modal(!0)
        })
    }, t.wmdPreview = function () {
        t.parent.wmdPreview = !t.parent.wmdPreview, t.replyMoving.scrollIntoView(!0)
    }, t.checkContentMin = function (e, a) {
        var i = m(a.$value);
        return t.parent.contentBytes = i, i >= p.ContentMinLen
    }, t.checkContentMax = function (e, t) {
        return m(t.$value) <= p.ContentMaxLen
    }, t.reply = function (a) {
        var i = t.comment;
        i.refer = a._id, t.parent.wmdPreview = !1, a._id === t.article._id ? s() : (i.replyToComment = a._id, i.title = d.ARTICLE.reply + v(e.sanitize(e.mdParse(a.content), 0), p.TitleMaxLen - 9), t.replyMoving.appendTo("#" + a._id)), t.replyMoving.scrollIntoView()
    }, t.getComments = function (a, i) {
        function n() {
            var t = [];
            return e.each(a, function (e) {
                r[e] && t.push(r[e])
            }), t
        }

        var l = [], r = {};
        if (t.referComments = [], i && a && a.length > 0) {
            if (t.commentMoving.childrenOf("#" + i._id))return void t.commentMoving.appendTo("#comments");
            t.commentMoving.appendTo("#" + i._id), e.each(a, function (e) {
                var t = f.get(e);
                t ? r[e] = t : l.push(e)
            }), t.referComments = n(), l.length > 0 && b.save({ID: "comment"}, {data: l}, function (a) {
                e.each(a.data, function (e) {
                    o(e), f.put(e._id, e), r[e._id] = e
                }), t.referComments = n()
            })
        }
    }, t.highlight = function (e) {
        e.status = 2 === e.status ? 1 : 2
    }, t.setMark = function (t) {
        r() && b.save({ID: t._id, OP: "mark"}, {mark: !t.isMark}, function () {
            t.isMark = !t.isMark, t.isMark ? t.markList.push(w) : e.removeItem(t.markList, w._id), e.toast.success(d.ARTICLE[t.isMark ? "marked" : "unmarked"])
        })
    }, t.setFavor = function (t) {
        r() && b.save({ID: t._id, OP: "favor"}, {favor: !t.isFavor}, function () {
            t.isFavor = !t.isFavor, t.isFavor ? (t.favorsList.push(w), e.removeItem(t.opposesList, w._id), t.isOppose = !1) : e.removeItem(t.favorsList, w._id), e.toast.success(d.ARTICLE[t.isFavor ? "favored" : "unfavored"])
        })
    }, t.setOppose = function (t) {
        r() && b.save({ID: t._id, OP: "oppose"}, {oppose: !t.isOppose}, function () {
            t.isOppose = !t.isOppose, t.isOppose ? (t.opposesList.push(w), e.removeItem(t.favorsList, w._id), t.isFavor = !1) : e.removeItem(t.opposesList, w._id), e.toast.success(d.ARTICLE[t.isOppose ? "opposed" : "unopposed"])
        })
    }, t.submit = function () {
        if (r() && e.validate(t)) {
            var a = e.union(t.comment), i = t.article;
            b.save({ID: i._id, OP: "comment"}, a, function (a) {
                var n = a.data, l = t.comment.replyToComment;
                i.commentsList.unshift(n), i.comments += 1, i.updateTime = Date.now(), l && e.findItem(i.commentsList, function (e) {
                    return l === e._id ? (e.commentsList.push(n._id), !0) : void 0
                }), f.put(n._id, n), s()
            })
        }
    }, t.$on("genPagination", function (a, i, n) {
        a.stopPropagation();
        var l = {ID: c, OP: "comment", p: i, s: u.pageSize(n, "comment", 10)};
        e.promiseGet(l, b, e.param(l), h).then(function (a) {
            var i = a.pagination || {}, n = a.data;
            i.pageSize = u.pageSize(i.pageSize, "comment"), t.pagination = i, e.each(n, function (e) {
                o(e), f.put(e._id, e)
            }), t.article.commentsList = n, e.anchorScroll.toView("#comments", !0)
        })
    }), i().run(), e.promiseGet({ID: c}, b, c, e.cache.article).then(function (a) {
        var i = a.pagination || {}, n = a.data;
        i.pageSize = u.pageSize(i.pageSize, "comments"), o(n), e.each(n.commentsList, function (e) {
            f.put(e._id, e)
        }), p.title2 = n.title, t.pagination = i, t.article = n, s(), e.promiseGet({ID: n.author._id, OP: "article"}, e.restAPI.user, n.author._id, h).then(function (a) {
            var i = a.user, n = t.article.author;
            e.checkFollow(i), e.union(n, i), n.articlesList = a.data
        })
    }), n("hots").then(function (e) {
        t.hotArticles = e.data.slice(0, 10)
    })
}]).controller("articleEditorCtrl", ["app", "$scope", "$routeParams", "mdEditor", "getMarkdown", function (e, t, a, i, n) {
    function l(a) {
        b = e.union(h), a ? (a = e.union(a), e.each(a.tagsList, function (e, t, a) {
            a[t] = e.tag
        }), a.refer = a.refer && a.refer.url, e.intersect(b, a), t.article = e.union(b), e.checkDirty(h, b, t.article)) : (t.article = {}, e.each(h, function (a, i) {
            t.article[i] = e.store.get("article." + i) || a
        })), o(a)
    }

    function o(e) {
        var a = t.parent, i = t.article;
        e ? (a.title = u.ARTICLE.preview + c(i.title), a.content = i.content) : n.success(function (e) {
            a.title = u.ARTICLE.markdown, a.content = e
        })
    }

    var r, s = a.ID && "A" + a.ID, c = e.toStr, u = e.locale, d = e.rootScope.global, p = e.filter, g = e.upyun, m = p("length"), v = (p("cutText"), e.restAPI.article), f = e.cache.article, h = {title: "", content: "", refer: "", tagsList: []}, b = e.union(h);
    if (!d.isLogin)return e.location.search({}).path("/");
    d.title2 = e.locale.ARTICLE.title, t.parent = {edit: !!s, wmdPreview: !0, contentBytes: 0, titleBytes: 0, title: "", content: ""};
    var w = d.cloudDomian || d.url;
    t.uploaderOptions = {scope: t, allowFileType: g.allowFileType, url: g.url, baseUrl: w, policy: g.policy, signature: g.signature, clickImage: function (e) {
        t.article.content += "\n![" + e.name + "](" + e.url + ")\n"
    }}, t.validateTooltip = e.union(e.rootScope.validateTooltip), t.validateTooltip.placement = "bottom", t.store = function (a) {
        var i = t.article[a];
        e.store.set("article." + a, i)
    }, t.checkTitleMin = function (a, i) {
        var n = m(i.$value);
        return t.parent.titleBytes = n, t.parent.wmdPreview && (t.parent.title = u.ARTICLE.preview + e.sanitize(i.$value, 0)), n >= d.TitleMinLen
    }, t.checkTitleMax = function (e, t) {
        return m(t.$value) <= d.TitleMaxLen
    }, t.checkContentMin = function (e, a) {
        var i = m(a.$value);
        return t.parent.contentBytes = i, t.parent.wmdPreview && (t.parent.content = a.$value), i >= d.ContentMinLen
    }, t.checkContentMax = function (e, t) {
        return m(t.$value) <= d.ContentMaxLen
    }, t.checkTag = function (e, t) {
        var a = t.$value || "";
        return a = angular.isString(a) ? a.split(/[,，、]/) : a, a.length <= d.ArticleTagsMax
    }, t.getTag = function (e) {
        var a = t.article.tagsList;
        a.indexOf(e.tag) < 0 && a.length < d.ArticleTagsMax && (t.article.tagsList = a.concat(e.tag), t.store("tagsList"))
    }, t.wmdPreview = function () {
        var e = t.parent;
        e.wmdPreview = !e.wmdPreview, o(e.wmdPreview)
    }, t.submit = function () {
        var a = e.union(t.article);
        e.validate(t) && (e.checkDirty(h, b, a) ? (a.title = e.sanitize(a.title, 0), v.save({ID: s || "index", OP: s && "edit"}, a, function (t) {
            var a = t.data;
            r && (delete a.commentsList, a = t.data = e.union(r.data, a)), f.put(a._id, t), l(a), e.toast.success(u.ARTICLE[s ? "updated" : "added"] + a.title);
            var i = e.timing(null, 1e3, 2);
            i.then(function () {
                e.location.search({}).path("/" + a._id)
            }), e.store.clear()
        })) : e.toast.info(u.ARTICLE.noUpdate))
    }, e.store.enabled || t.$watchCollection("article", function (t) {
        e.checkDirty(h, b, t)
    }), i().run(), s ? (r = f.get(s), e.promiseGet({ID: s}, v, s, f).then(function (e) {
        l(e.data)
    })) : l()
}]).controller("adminCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i(e) {
        return e = "comment" === e ? "article" : e, "admin-" + e + ".html"
    }

    var n = e.rootScope.global, l = a.OP || "index";
    return n.isEditor ? (t.parent = {getTpl: i(l), viewPath: l}, void(n.title2 = e.locale.ADMIN[l])) : e.location.search({}).path("/")
}]).controller("adminUserCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i(a) {
        n = e.intersect(e.union(c), a), t.userList = e.union(n), t.parent.editSave = !!e.checkDirty(c, n, t.userList)
    }

    var n = {}, l = e.restAPI.user, o = e.myConf, r = e.locale, s = {ID: "admin", p: a.p, s: a.s || o.pageSize(null, "userAdmin", 20)}, c = [
        {_id: "", name: "", locked: !1, email: "", role: 0, score: 0, date: 0, lastLoginDate: 0}
    ];
    t.parent = {editSave: !1, isSelectAll: !1}, t.pagination = {}, t.roleArray = [0, 1, 2, 3, 4, 5], t.selectAll = function () {
        e.each(t.userList, function (e) {
            e.isSelect = t.parent.isSelectAll
        })
    }, t.reset = function () {
        t.userList = e.union(n)
    }, t.submit = function () {
        var a = [
            {_id: "", locked: !1, role: 0}
        ];
        if (e.validate(t)) {
            var o = e.checkDirty(c, n, t.userList);
            e.isEmpty(o) ? e.toast.info(r.USER.noUpdate) : (o = e.intersect(a, o), l.save({ID: "admin"}, {data: o}, function (a) {
                var n = [];
                e.each(a.data, function (a) {
                    e.findItem(t.userList, function (t) {
                        return a._id === t._id ? (e.union(t, a), n.push(a.name), !0) : void 0
                    })
                }), i(t.userList), e.toast.success(r.USER.updated + n.join(", "), r.RESPONSE.success)
            }))
        }
    }, t.$watch("userList", function (a) {
        t.parent.editSave = !!e.checkDirty(c, n, a)
    }, !0), e.promiseGet(s, l).then(function (a) {
        var n = a.pagination || {};
        n.path = e.location.path(), n.pageSize = o.pageSize(n.pageSize, "userAdmin"), n.sizePerPage = [20, 100, 200], t.pagination = n, i(a.data)
    })
}]).controller("adminTagCtrl", ["app", "$scope", "$routeParams", function (e, t, a) {
    function i(a) {
        n = e.union(e.union(c), a), t.tagList = e.union(n), t.parent.editSave = !!e.checkDirty(c, n, t.tagList)
    }

    var n = {}, l = e.restAPI.tag, o = e.myConf, r = e.locale, s = {ID: "index", p: a.p, s: a.s || o.pageSize(null, "tagAdmin", 20)}, c = [
        {_id: "", articles: 0, tag: "", users: 0}
    ];
    t.parent = {editSave: !1}, t.pagination = {}, t.removeTag = null, t.removeTagModal = {confirmBtn: r.BTN_TEXT.confirm, confirmFn: function () {
        var a = t.removeTag;
        return l.remove({ID: a._id}, function () {
            e.findItem(t.tagList, function (t, i, n) {
                return t._id === a._id ? (n.splice(i, 1), e.toast.success(r.TAG.removed + a.tag, r.RESPONSE.success), !0) : void 0
            }), i(t.tagList), t.removeTag = null
        }), !0
    }, cancelBtn: r.BTN_TEXT.cancel, cancelFn: function () {
        return t.removeTag = null, !0
    }}, t.checkTag = function (t, a) {
        var i = e.toStr(a.$value);
        return!/[,，、]/.test(i)
    }, t.checkTagMin = function (t, a) {
        return e.filter("length")(a.$value) >= 3
    }, t.reset = function () {
        t.tagList = e.union(n)
    }, t.remove = function (e) {
        t.removeTag = e, t.removeTagModal.modal(!0)
    }, t.submit = function () {
        var a = [
            {_id: "", tag: ""}
        ];
        if (e.validate(t)) {
            var o = e.checkDirty(c, n, t.tagList);
            e.isEmpty(o) ? e.toast.info(r.TAG.noUpdate) : (o = e.intersect(a, o), l.save({ID: "admin"}, {data: o}, function (a) {
                var n = [];
                e.each(o, function (i) {
                    var n = a.data[i._id];
                    n || e.findItem(t.tagList, function (e, t, a) {
                        return i._id === e._id ? (a.splice(t, 1), !0) : void 0
                    })
                }), e.each(a.data, function (a) {
                    e.findItem(t.tagList, function (t) {
                        return a._id === t._id ? (e.union(t, a), n.push(a.tag), !0) : void 0
                    })
                }), i(t.tagList), e.toast.success(r.TAG.updated + n.join(", "), r.RESPONSE.success)
            }))
        }
    }, t.$watch("tagList", function (a) {
        t.parent.editSave = !!e.checkDirty(c, n, a)
    }, !0), e.promiseGet(s, l).then(function (a) {
        var n = a.pagination || {};
        n.path = e.location.path(), n.pageSize = o.pageSize(n.pageSize, "tagAdmin"), n.sizePerPage = [20, 50, 100], t.pagination = n, i(a.data)
    })
}]).controller("adminArticleCtrl", ["app", "$scope", function () {
}]).controller("adminGlobalCtrl", ["app", "$scope", function (e, t) {
    function a(a) {
        t.global = e.union(a), n = e.union(a), n = e.intersect(e.union(i), n), t.editGlobal = e.union(n), e.checkDirty(i, n, t.editGlobal)
    }

    {
        var i, n = {}, l = e.locale, o = e.filter, r = e.restAPI.index;
        o("length")
    }
    t.parent = {switchTab: "tab1"}, t.reset = function () {
        t.editGlobal = e.union(n)
    }, t.submit = function () {
        var o = e.union(t.editGlobal);
        e.validate(t) && (o = e.checkDirty(i, n, o), e.isEmpty(o) ? e.toast.info(l.ADMIN.noUpdate) : r.save({OP: "admin"}, o, function (t) {
            a(t.data);
            var i = e.union(n);
            delete i.smtp, delete i.email, e.union(e.rootScope.global, i), e.toast.success(l.ADMIN.updated, l.RESPONSE.success)
        }))
    }, t.$watchCollection("editGlobal", function (t) {
        e.checkDirty(i, n, t)
    }), e.promiseGet({OP: "admin"}, r).then(function (e) {
        i = e.configTpl, a(e.data)
    })
}]), angular.module("genTemplates", []).run(["$templateCache", function (e) {
    e.put("admin-article.html", '<div ng-controller="adminArticleCtrl"><div class="panel"><div class="inner page-header"><strong>文章评论批量管理</strong></div><div class="inner well text-right" ng-show="parent.editSave"><button class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></div><div gen-pagination="pagination"></div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("admin-global.html", '<div class="panel" ng-controller="adminGlobalCtrl"><div class="inner page-header"><strong>网站设置</strong></div><ul class="nav nav-tabs inner"><li class="active" gen-tab-click="active"><a ng-click="parent.switchTab=\'tab1\'">基本设置</a></li><li gen-tab-click="active"><a ng-click="parent.switchTab=\'tab2\'">参数设置</a></li><li gen-tab-click="active"><a ng-click="parent.switchTab=\'tab3\'">缓存设置</a></li><li gen-tab-click="active"><a ng-click="parent.switchTab=\'tab4\'">SMTP设置</a></li><li gen-tab-click="active"><a ng-click="parent.switchTab=\'tab5\'">云存储设置</a></li></ul><div class="inner well" ng-switch="" on="parent.switchTab"><div ng-switch-default=""><form class="pure-form pure-form-aligned" novalidate><div class="pure-control-group"><label>网站域名：</label> <input type="text" class="pure-input-1-2" name="网站域名" ng-model="editGlobal.domain" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>ICP备案：</label> <input type="text" class="pure-input-1-2" name="ICP备案" ng-model="editGlobal.beian"></div><div class="pure-control-group"><label>网站网址[含http://]：</label> <input type="url" class="pure-input-1-2" name="网站网址" ng-model="editGlobal.url" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>网站Logo：</label> <input type="text" class="pure-input-1-2" name="logo" ng-model="editGlobal.logo" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>管理员Email[不公开]：</label> <input type="email" name="管理员Email" ng-model="editGlobal.email" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>网站名称：</label> <input type="text" class="pure-input-1-2" name="网站名称" ng-model="editGlobal.title" ng-maxlength="20" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>网站副标题：</label> <input type="text" class="pure-input-1-2" ng-model="editGlobal.description"></div><div class="pure-control-group"><label>网站Meta标题[SEO]：</label> <textarea class="pure-input-1-2" rows="4" ng-model="editGlobal.metatitle">\n                    </textarea></div><div class="pure-control-group"><label>网站Meta描述[SEO]：</label> <textarea class="pure-input-1-2" rows="4" ng-model="editGlobal.metadesc">\n                    </textarea></div><div class="pure-control-group"><label>网站Meta关键词[SEO]：</label> <textarea class="pure-input-1-2" rows="4" ng-model="editGlobal.keywords">\n                    </textarea></div><div class="pure-control-group"><label>搜索引擎机器人：</label> <textarea class="pure-input-1-2" rows="4" ng-model="editGlobal.robots">\n                    </textarea></div><div class="pure-control-group"><label>用户注册：</label> <input type="button" class="pure-button pure-button-small" ng-class="{\'primary-bg\':editGlobal.register}" ng-click="editGlobal.register=!editGlobal.register" value="{{!editGlobal.register | switch:\'turn\'}}"></div><div class="pure-control-group"><label>邮箱验证：</label> <input type="button" class="pure-button pure-button-small" ng-class="{\'primary-bg\':editGlobal.emailVerification}" ng-click="editGlobal.emailVerification=!editGlobal.emailVerification" value="{{!editGlobal.emailVerification | switch:\'turn\'}}"></div><div class="pure-controls"><button type="submit" class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div><div ng-switch-when="tab2"><form class="pure-form pure-form-aligned" novalidate><div class="pure-control-group"><label>文章最多标签数量：</label> <input type="number" ng-model="editGlobal.ArticleTagsMax"></div><div class="pure-control-group"><label>用户最多标签数量：</label> <input type="number" ng-model="editGlobal.UserTagsMax"></div><div class="pure-control-group"><label>标题最短字节数：</label> <input type="number" ng-model="editGlobal.TitleMinLen"></div><div class="pure-control-group"><label>标题最长字节数：</label> <input type="number" ng-model="editGlobal.TitleMaxLen"></div><div class="pure-control-group"><label>摘要最长字节数：</label> <input type="number" ng-model="editGlobal.SummaryMaxLen"></div><div class="pure-control-group"><label>文章最短字节数：</label> <input type="number" ng-model="editGlobal.ContentMinLen"></div><div class="pure-control-group"><label>文章最长字节数：</label> <input type="number" ng-model="editGlobal.ContentMaxLen"></div><div class="pure-control-group"><label>用户名最短字节数：</label> <input type="number" ng-model="editGlobal.UserNameMinLen"></div><div class="pure-control-group"><label>用户名最长字节数：</label> <input type="number" ng-model="editGlobal.UserNameMaxLen"></div><div class="pure-control-group"><label>用户积分系数：</label> <input type="text" ng-model="editGlobal.UsersScore" ng-list="/[,，、]/"></div><div class="pure-controls-group"><small>评论×<strong>{{editGlobal.UsersScore[0]}}</strong> ，文章×<strong>{{editGlobal.UsersScore[1]}}</strong>，关注×<strong>{{editGlobal.UsersScore[2]}}</strong> ，粉丝×<strong>{{editGlobal.UsersScore[3]}}</strong>，文章热度×<strong>{{editGlobal.UsersScore[4]}}</strong>， 注册时长天数×<strong>{{editGlobal.UsersScore[5]}}</strong></small></div><div class="pure-control-group"><label>评论提升系数：</label> <input type="text" ng-model="editGlobal.ArticleStatus" ng-list="/[,，、]/"></div><div class="pure-controls-group"><small>评论的评论数达到<strong>{{editGlobal.ArticleStatus[0]}}</strong>时，自动提升正常文章， 达到<strong>{{editGlobal.ArticleStatus[1]}}</strong>时，自动提升为推荐文章</small></div><div class="pure-control-group"><label>文章热度系数：</label> <input type="text" ng-model="editGlobal.ArticleHots" ng-list="/[,，、]/"></div><div class="pure-controls-group"><small>访问+<strong>{{editGlobal.ArticleHots[0]}}</strong>，支持/反对±<strong>{{editGlobal.ArticleHots[1]}}</strong>，评论+<strong>{{editGlobal.ArticleHots[2]}}</strong>，收藏+ <strong>{{editGlobal.ArticleHots[3]}}</strong>，推荐+<strong>{{editGlobal.ArticleHots[4]}}</strong></small></div><div class="pure-controls"><button type="submit" class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div><div ng-switch-when="tab3"><form class="pure-form pure-form-aligned" novalidate><div class="pure-control-group"><label>用户缓存容量：</label> <input type="number" ng-model="editGlobal.userCache"></div><div class="pure-control-group"><label>文章缓存容量：</label> <input type="number" ng-model="editGlobal.articleCache"></div><div class="pure-control-group"><label>评论缓存容量：</label> <input type="number" ng-model="editGlobal.commentCache"></div><div class="pure-control-group"><label>列表缓存容量：</label> <input type="number" ng-model="editGlobal.listCache"></div><div class="pure-control-group"><label>标签缓存容量：</label> <input type="number" ng-model="editGlobal.tagCache"></div><div class="pure-control-group"><label>合集缓存容量：</label> <input type="number" ng-model="editGlobal.collectionCache"></div><div class="pure-control-group"><label>消息缓存容量：</label> <input type="number" ng-model="editGlobal.messageCache"></div><div class="pure-control-group"><label>分页导航缓存：</label> <input type="number" ng-model="editGlobal.paginationCache"></div><div class="pure-controls-group"><small>分页导航缓存有效期<strong>{{editGlobal.paginationCache}}</strong>秒</small></div><div class="pure-control-group"><label>操作限时缓存：</label> <input type="number" ng-model="editGlobal.TimeInterval"></div><div class="pure-controls-group"><small>搜索、用户添加文章或评论等限制操作的最小时间间隔，最小值为3秒</small></div><div class="pure-controls"><button type="submit" class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div><div ng-switch-when="tab4"><form class="pure-form pure-form-aligned" novalidate><div class="pure-control-group"><label>服务器地址：</label> <input type="text" ng-model="editGlobal.smtp.host"></div><div class="pure-control-group"><label>服务器端口：</label> <input type="number" ng-model="editGlobal.smtp.port"></div><div class="pure-control-group"><label>SMTP用户名：</label> <input type="text" ng-model="editGlobal.smtp.auth.user"></div><div class="pure-control-group"><label>SMTP用户密码：</label> <input type="password" ng-model="editGlobal.smtp.auth.pass"></div><div class="pure-control-group"><label>发送人名称：</label> <input type="text" ng-model="editGlobal.smtp.senderName"></div><div class="pure-control-group"><label>发送人Email：</label> <input type="text" ng-model="editGlobal.smtp.senderEmail"> <small>部分SMTP服务器要求其与用户名一致</small></div><div class="pure-control-group"><label>是否开启SSL：</label> <input type="button" class="pure-button pure-button-small" ng-class="{\'primary-bg\':editGlobal.smtp.secureConnection}" ng-click="editGlobal.smtp.secureConnection=!editGlobal.smtp.secureConnection" value="{{!editGlobal.smtp.secureConnection | switch:\'turn\'}}"></div><div class="pure-controls"><button type="submit" class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div><div ng-switch-when="tab5"><form class="pure-form pure-form-aligned" novalidate><div class="pure-control-group"><label>云存储空间绑定域名：</label> <input type="text" ng-model="editGlobal.cloudDomian"></div><div class="pure-control-group"><label>又拍云bucket：</label> <input type="text" ng-model="editGlobal.upyun.bucket"></div><div class="pure-control-group"><label>又拍云表单密匙：</label> <input type="password" ng-model="editGlobal.upyun.form_api_secret"></div><div class="pure-control-group"><label>是否开启图片上传：</label> <input type="button" class="pure-button pure-button-small" ng-class="{\'primary-bg\':editGlobal.upload}" ng-click="editGlobal.upload=!editGlobal.upload" value="{{!editGlobal.upload | switch:\'turn\'}}"></div><div class="pure-controls"><button type="submit" class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div></div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("admin-index.html", '<div class="panel" ng-controller="adminGlobalCtrl"><div class="page-header inner"><strong>网站信息</strong></div><ul class="nav nav-tabs inner"><li class="active" gen-tab-click="active"><a ng-click="parent.switchTab=\'tab1\'">站点信息</a></li><li gen-tab-click="active"><a ng-click="parent.switchTab=\'tab2\'">服务器信息</a></li></ul><div class="inner" ng-switch="" on="parent.switchTab"><div ng-switch-default=""><dl class="dl-horizontal"><dt>网站名称：</dt><dd>{{global.title | placeholder}}</dd><dt>网站网址：</dt><dd>{{global.url | placeholder}}</dd><dt>网站副标题：</dt><dd>{{global.description | placeholder}}</dd><dt>网站Meta标题：</dt><dd>{{global.metatitle | placeholder}}</dd><dt>网站Meta描述：</dt><dd>{{global.metadesc | placeholder}}</dd><dt>网站Meta关键词：</dt><dd>{{global.keywords | placeholder}}</dd><dt>上线时间：</dt><dd>{{global.date | date:\'yy-MM-dd HH:mm\'}}</dd><dt>访问总数：</dt><dd>{{global.visitors | placeholder}}</dd><dt>会员总数：</dt><dd>{{global.users | placeholder}}</dd><dt>文章总数：</dt><dd>{{global.articles | placeholder}}</dd><dt>评论总数：</dt><dd>{{global.comments | placeholder}}</dd><dt>在线访客：</dt><dd>{{global.onlineNum | placeholder}}</dd><dt>在线会员：</dt><dd>{{global.onlineUsers | placeholder}}</dd><dt>在线记录：</dt><dd>{{global.maxOnlineNum | placeholder}}</dd><dt>记录时间：</dt><dd>{{global.maxOnlineTime | date:\'yy-MM-dd HH:mm\'}}</dd></dl></div><div ng-switch-when="tab2"><dl class="dl-horizontal"><dt>服务器已运行时间：</dt><dd>{{global.sys.uptime | formatTime}}</dd><dt>服务器操作系统：</dt><dd>{{global.sys.platform | placeholder}}</dd><dt>软件信息：</dt><dd><span>软件：<a ng-href="{{global.info.homepage}}"><strong>{{global.info.name}}</strong></a></span><br><span>版本：{{global.info.version}}</span><br><span>作者：<a ng-href="{{global.info.author.url}}">{{global.info.author.name}}</a></span><br><span>邮箱：<a href="mailto:#">{{global.info.author.email}}</a></span></dd><dt>Node.js环境：</dt><dd><span ng-repeat="(key, value) in global.sys.node"><strong>{{key}}：</strong>{{value}}<br></span></dd><dt>CPUs[{{global.sys.cpus.length}}]：</dt><dd ng-repeat="data in global.sys.cpus"><span ng-repeat="(key, value) in data"><strong>{{key}}：</strong>{{value}}<br></span></dd><dt>系统内存：</dt><dd><span ng-repeat="(key, value) in global.sys.memory track by $index"><strong>{{key}}：</strong>{{value | formatBytes}}<br></span></dd><dt>用户缓存：</dt><dd><span>容量：{{global.sys.user.capacity}}</span> <span>当前数量：{{global.sys.user.length}}</span></dd><dt>文章缓存：</dt><dd><span>容量：{{global.sys.article.capacity}}</span> <span>当前数量：{{global.sys.article.length}}</span></dd><dt>评论缓存：</dt><dd><span>容量：{{global.sys.comment.capacity}}</span> <span>当前数量：{{global.sys.comment.length}}</span></dd><dt>列表缓存：</dt><dd><span>容量：{{global.sys.list.capacity}}</span> <span>当前数量：{{global.sys.list.length}}</span></dd><dt>标签缓存：</dt><dd><span>容量：{{global.sys.tag.capacity}}</span> <span>当前数量：{{global.sys.tag.length}}</span></dd><dt>合集缓存：</dt><dd><span>容量：{{global.sys.collection.capacity}}</span> <span>当前数量：{{global.sys.collection.length}}</span></dd><dt>消息缓存：</dt><dd><span>容量：{{global.sys.message.capacity}}</span> <span>当前数量：{{global.sys.message.length}}</span></dd><dt>分页导航缓存：</dt><dd><span>限时(s)：{{global.sys.pagination.timeLimit}}</span> <span>当前数量：{{global.sys.pagination.length}}</span></dd><dt>操作限时缓存：</dt><dd><span>限时(s)：{{global.sys.timeInterval.timeLimit}}</span> <span>当前数量：{{global.sys.timeInterval.length}}</span></dd></dl></div></div></div>'), e.put("admin-message.html", '<div><div class="panel"><div class="inner page-header"><strong>消息批量管理</strong></div><div class="inner well text-right" ng-show="parent.editSave"><button class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></div><div gen-pagination="pagination"></div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("admin-tag.html", '<div ng-controller="adminTagCtrl"><div class="panel"><div class="inner page-header"><strong>标签管理</strong> <small class="right"><button class="pure-button pure-button-link" ng-click="predicate=\'tag\';reverse=!reverse"><i class="fa fa-sort-alpha-asc"></i>名称</button> <button class="pure-button pure-button-link" ng-click="predicate=\'articles\';reverse=!reverse"><i class="fa fa-sort-amount-asc"></i>文章</button> <button class="pure-button pure-button-link" ng-click="predicate=\'users\';reverse=!reverse"><i class="fa fa-sort-amount-asc"></i>用户</button></small></div><form class="pure-form pure-g-r inner"><div class="pure-u-1-2 edit-tag" ng-repeat="tag in tagList | orderBy:predicate:reverse" id="{{tag._id}}"><a title="删除标签" ng-click="remove(tag)" gen-tooltip=""><i class="fa fa-trash-o"></i></a> <input class="pure-input-1-2 input-flat" type="text" name="标签" ng-model="tag.tag" ui-validate="{tag:checkTag,minlength:checkTagMin}" gen-tooltip="validateTooltip" required> <small class="muted"><a ng-href="/{{tag._id}}">文章 {{tag.articles}}/会员 {{tag.users}}</a></small></div></form><div class="inner well text-right" ng-show="parent.editSave"><button class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></div><div gen-pagination="pagination"></div><div gen-modal="removeTagModal">确定要删除标签 {{removeTag.tag}}？</div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("admin-user.html", '<div ng-controller="adminUserCtrl"><div class="panel"><div class="inner page-header"><strong>用户批量查询/设置</strong></div><div class="inner"><table class="pure-table pure-table-horizontal"><thead><tr><th class="text-left"><input type="checkbox" ng-model="parent.isSelectAll" ng-click="selectAll()"></th><th><i class="fa fa-sort-alpha-asc"></i><a ng-click="predicate=\'name\';reverse=!reverse">用户名</a></th><th><i class="fa fa-sort-alpha-asc"></i><a ng-click="predicate=\'email\';reverse=!reverse">Email</a></th><th><i class="fa fa-sort-alpha-asc"></i><a ng-click="predicate=\'role\';reverse=!reverse">权限</a></th><th><i class="fa fa-sort-amount-asc"></i><a ng-click="predicate=\'score\';reverse=!reverse">积分</a></th><th><i class="fa fa-sort-amount-asc"></i><a ng-click="predicate=\'date\';reverse=!reverse">注册</a></th><th><i class="fa fa-sort-amount-asc"></i><a ng-click="predicate=\'lastLoginDate\';reverse=!reverse">登录</a></th></tr></thead><tbody><tr ng-repeat="user in userList | orderBy:predicate:reverse"><td><input type="checkbox" ng-model="user.isSelect"></td><td><a ng-href="/{{user._id}}">{{user.name}}</a><a ng-show="user.locked" title="该用户已被锁定，点击保存后解锁" ng-click="user.locked=false" gen-tooltip=""><i class="fa fa-minus-circle"></i></a></td><td>{{user.email}}</td><td><select class ng-model="user.role" ng-options="role | match:\'role\' for role in roleArray"></select></td><td class="text-center">{{user.score}}</td><td class="text-center">{{user.date | date:\'yy-MM-dd\'}}</td><td class="text-center">{{user.lastLoginDate | date:\'yy-MM-dd\'}}</td></tr></tbody></table></div><div class="inner well text-right" ng-show="parent.editSave"><button class="pure-button success-bg" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></div><div gen-pagination="pagination"></div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("admin.html", '<div class="pure-g-r wrap"><div class="pure-u-2-3"><div ng-include="" src="parent.getTpl"></div></div><div class="pure-u-1-3 aside"><div class="panel pure-menu pure-menu-open"><ul class="text-center"><li ng-class="{active: parent.viewPath==\'index\'}"><a href="/admin/index"><i class="fa fa-chevron-left left"></i>网站信息</a></li><li ng-class="{active: parent.viewPath==\'user\'}"><a href="/admin/user"><i class="fa fa-chevron-left left"></i>用户管理</a></li><li ng-class="{active: parent.viewPath==\'tag\'}"><a href="/admin/tag"><i class="fa fa-chevron-left left"></i>标签管理</a></li><li ng-class="{active: parent.viewPath==\'article\'}"><a href="/admin/article"><i class="fa fa-chevron-left left"></i>文章管理</a></li><li ng-class="{active: parent.viewPath==\'comment\'}"><a href="/admin/comment"><i class="fa fa-chevron-left left"></i>评论管理</a></li><li ng-class="{active: parent.viewPath==\'message\'}"><a href="/admin/message"><i class="fa fa-chevron-left left"></i>消息管理</a></li><li ng-class="{active: parent.viewPath==\'global\'}" ng-show="global.isAdmin"><a href="/admin/global"><i class="fa fa-chevron-left left"></i>网站设置</a></li></ul></div></div></div>'), e.put("article-editor.html", '<div class="pure-g-r"><div class="pure-u-1-2 pure-visible-desktop"><div class="panel"><div class="page-header inner"><strong>{{parent.title}}</strong></div><div class="article-content" gen-parse-md="parent.content"></div></div></div><div ng-class="{\'pure-u-1-2\':global.isDesktop,\'pure-u-1\':!global.isDesktop}"><div class="panel file-drop" ng-file-drop="" ng-file-over="file-drop-over"><div class="page-header inner"><strong>{{parent.edit | switch:\'edit\'}}文章</strong></div><form class="inner pure-form pure-form-stacked" novalidate><label>文章标题 <span class="info">[必填，{{global.TitleMinLen}} 到 {{global.TitleMaxLen}} 字节，当前<strong class="hot">{{parent.titleBytes}}</strong>字节]</span></label> <input class="pure-input-1" type="text" placeholder="文章标题" name="文章标题" ng-model="article.title" ng-change="store(\'title\')" ui-validate="{maxlength:checkTitleMax,minlength:checkTitleMin}" gen-tooltip="validateTooltip" required> <label>文章来源 <span class="info">[如转载文章，请填完整的原文URL地址]</span></label> <input class="pure-input-1" type="text" placeholder="文章来源URL" name="文章来源" ng-model="article.refer" ng-change="store(\'refer\')"> <label>文章内容 <span class="info">[使用<a class="hot" ng-click="wmdPreview()">MarkDown语法</a>，{{global.ContentMinLen}} 到 {{global.ContentMaxLen}} 字节，当前<strong class="hot">{{parent.contentBytes}}</strong>字节]</span></label><div id="wmd-button-bar"></div><textarea class="wmd-input pure-input-1" id="wmd-input" name="文章内容" placeholder="{{global.isLogin?\'请使用MarkDown语法编辑内容\':\'您还没有登录，不能发表文章哦\'}}" ng-model="article.content" rows="10" ng-change="store(\'content\')" ui-validate="{maxlength:checkContentMax,minlength:checkContentMin}" gen-tooltip="validateTooltip" required>\n                </textarea> <label>文章标签 <span class="info">(最多设置 {{global.ArticleTagsMax}} 个标签){{article.tagsList}}</span></label> <textarea class="pure-input-1" name="文章标签" ng-model="article.tagsList" ng-list="" ng-trim="false" ng-change="store(\'tagsList\')" ui-validate="{more:checkTag}" gen-tooltip="validateTooltip">\n                </textarea><mark>热门标签：</mark><a class="pure-button pure-button-link" ng-repeat="tag in global.tagsList" ng-click="getTag(tag)"><small>{{tag.tag}}</small></a><div class="well" gen-uploader="uploaderOptions" ng-if="global.upload"></div><div class="well"><button class="pure-button pure-button-small info-bg" ng-click="wmdPreview()">MarkDown语法 / 文章预览</button> <button type="submit" class="pure-button pure-button-small success-bg" ng-class="{\'pure-button-disabled\':!global.isLogin}" ng-click="submit()">提交</button> <button class="pure-button pure-button-small primary-bg" ng-click="goBack()">返回</button></div></form></div></div></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("article.html", '<div class="pure-g-r wrap"><div class="pure-u-2-3"><div class="panel" id="{{article._id}}"><div class="article-header"><h3>{{article.title}} <small><a ng-show="global.isEditor" ng-click="highlight(article)" ng-class="{info:article.status<2,warning:article.status==2}"><i class="fa fa-hand-o-up"></i>{{article.status==2 | switch:\'highlight\'}}</a></small></h3><div class="article-info"><a ng-href="{{article.refer.url}}" title="原文"><i class="fa fa-external-link--square success"></i>{{article.refer.url | cutText:25}}</a> <i class="fa fa-clock-o" data-original-title="{{article.date | formatDate:true}}发布" gen-tooltip="">{{article.date | formatDate}}</i> <i class="fa fa-refresh" data-original-title="{{article.updateTime | formatDate:true}}更新" gen-tooltip="">{{article.updateTime | formatDate}}</i> <i class="fa fa-eye" data-original-title="访问{{article.visitors}}次" gen-tooltip="">{{article.visitors}}</i> <i class="fa fa-star-o" data-original-title="热度{{article.hots}}" gen-tooltip=""><span>{{article.hots}}</span></i> <i class="fa fa-comments-o" data-original-title="评论{{article.comments}}条" ng-show="article.comments" gen-tooltip="">{{article.comments}}</i> <a ng-repeat="tag in article.tagsList" ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-link">{{tag.tag}}</a> <a class="success" ng-show="article.isAuthor||global.isEditor" ng-href="{{\'/\'+article._id+\'/edit\'}}"><i class="fa fa-pencil"></i> 编辑</a></div></div><div class="article-content" gen-parse-md="article.content"></div><div class="pure-g-r article-footer"><div class="pure-u-1-2 pure-hidden-phone"><div class="article-info" ng-show="article.favorsList"><i class="fa fa-thumbs-up">支持</i> <span ng-repeat="user in article.favorsList"><a ng-href="{{\'/\'+user._id}}">{{user.name}}</a></span></div><div class="article-info" ng-show="article.opposesList"><i class="fa fa-thumbs-down">反对</i> <span ng-repeat="user in article.opposesList"><a ng-href="{{\'/\'+user._id}}">{{user.name}}</a></span></div><div class="article-info" ng-show="article.markList"><i class="fa fa-bookmark">收藏</i> <span ng-repeat="user in article.markList"><a ng-href="{{\'/\'+user._id}}">{{user.name}}</a></span></div></div><div class="pure-u-1-2 text-right"><div class="pure-button-group"><button class="pure-button pure-button-small info-bg" title="支持" ng-click="setFavor(article)"><i ng-class="{\'fa fa-thumbs-up\':article.isFavor,\'fa fa-thumbs-o-up\':!article.isFavor}"></i> {{article.favorsList.length}}</button> <button class="pure-button pure-button-small info-bg" title="收藏" ng-click="setMark(article)"><i ng-class="{\'fa fa-bookmark\':article.isMark,\'fa fa-bookmark-o\':!article.isMark}"></i> {{article.markList.length}}</button> <button class="pure-button pure-button-small info-bg" title="反对" ng-click="setOppose(article)"><i ng-class="{\'fa fa-thumbs-down\':article.isOppose,\'fa fa-thumbs-o-down\':!article.isOppose}"></i> {{article.opposesList.length}}</button></div><div class="pure-button-group"><button ng-click="reply(article)" class="pure-button pure-button-small success-bg">发表评论<i class="fa fa-reply"></i></button></div></div></div></div><div class="panel" id="comments"><div class="inner" id="replyForm" gen-moving="replyMoving"><h4>{{comment.title}}</h4><div class="wmd-preview article-content" ng-show="parent.wmdPreview" gen-parse-md="comment.content"></div><form class="pure-form" ng-hide="parent.wmdPreview"><div id="wmd-button-bar"></div><textarea class="wmd-input pure-input-1" id="wmd-input" name="评论内容" placeholder="{{global.isLogin?\'请使用MarkDown语法编辑内容\':\'您还没有登录，不能发表评论哦\'}}" ng-model="comment.content" rows="6" ui-validate="{maxlength:checkContentMax,minlength:checkContentMin}" gen-tooltip="validateTooltip" required>\n                    </textarea></form><div class="article-info">[使用<a ng-click="wmdHelp()">MarkDown语法</a>，{{global.ContentMinLen}} 到 {{global.ContentMaxLen}} 字节，当前<strong class="hot">{{parent.contentBytes}}</strong>字节]</div><div class="text-right"><div class="pure-button-group"><button class="pure-button pure-button-small info-bg" ng-click="wmdPreview()">编辑 / 预览</button></div><div class="pure-button-group"><button class="pure-button pure-button-small info-bg" ng-if="comment.replyToComment" ng-click="reply(article)">返回</button> <button class="pure-button pure-button-small success-bg" ng-click="submit()">提交</button></div></div></div><ul class="media-list comments"><li class="media" ng-repeat="comment in article.commentsList"><a class="media-object left" ng-href="{{\'/\'+comment.author._id}}" ng-show="!global.isPocket"><img class="img-small" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{comment.author.avatar}}"></a><div class="media-body" id="{{comment._id}}"><div class="media-heading"><a ng-click="getComments(comment.refer._id, comment)">{{comment.title}}</a> <a class="right" title="删除评论" ng-show="comment.isAuthor||global.isEditor" ng-click="remove(comment)"><i class="fa fa-trash-o"></i></a></div><div class="media-content list-content" gen-parse-md="comment.content"></div><div class="pure-g-r media-footer"><div class="pure-u-1-2"><a ng-href="{{\'/\'+comment.author._id}}">{{comment.author.name}}</a> <span data-original-title="{{comment.date | formatDate:true}}发布" gen-tooltip="">{{comment.date | formatDate}}发表</span></div><div class="pure-u-1-2 text-right"><div class="pure-button-group"><button class="pure-button pure-button-link" title="支持" ng-click="setFavor(comment)"><i ng-class="{\'fa fa-thumbs-up\':comment.isFavor,\'fa fa-thumbs-o-up\':!comment.isFavor}">{{comment.favorsList.length}}</i></button> <button class="pure-button pure-button-link" title="评论" ng-click="getComments(comment.commentsList, comment)"><i class="fa fa-comments-o"></i> {{comment.commentsList.length}}</button> <button class="pure-button pure-button-link" title="反对" ng-click="setOppose(comment)"><i ng-class="{\'fa fa-thumbs-down\':comment.isOppose,\'fa fa-thumbs-o-down\':!comment.isOppose}"></i> {{comment.opposesList.length}}</button></div><div class="pure-button-group"><button class="pure-button pure-button-link" ng-click="reply(comment)">回复<i class="fa fa-reply"></i></button></div></div></div></div></li></ul><ul class="media-list comments" id="commentForm" gen-moving="commentMoving"><li class="media" ng-repeat="comment in referComments"><a class="media-object left" ng-href="{{\'/\'+comment.author._id}}" ng-show="!global.isPocket"><img class="img-small" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{comment.author.avatar}}"></a><div class="media-body"><div class="media-heading">{{comment.title}}</div><div class="media-content list-content" gen-parse-md="comment.content"></div><div class="pure-g-r media-footer"><div class="pure-u-1-2"><a ng-href="{{\'/\'+comment.author._id}}">{{comment.author.name}}</a> <span data-original-title="{{comment.date | formatDate:true}}发布" gen-tooltip="">{{comment.date | formatDate}}发表</span></div><div class="pure-u-1-2 text-right"><div class="pure-button-group"><button class="pure-button pure-button-link" title="支持" ng-click="setFavor(comment)"><i ng-class="{\'fa fa-thumbs-up\':comment.isFavor,\'fa fa-thumbs-o-up\':!comment.isFavor}">{{comment.favorsList.length}}</i></button> <button class="pure-button pure-button-link" title="反对" ng-click="setOppose(comment)"><i ng-class="{\'fa fa-thumbs-down\':comment.isOppose,\'fa fa-thumbs-o-down\':!comment.isOppose}"></i> {{comment.opposesList.length}}</button></div></div></div></div></li></ul></div><div gen-pagination="pagination"></div></div><div class="pure-u-1-3 aside"><div class="panel"><div class="panel-heading">作者信息</div><div class="media inner"><a class="media-object left" ng-href="{{\'/\'+article.author._id}}"><img class="img-small" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{article.author.avatar}}"></a><div class="media-body"><div class="media-header"><a ng-href="{{\'/\'+article.author._id}}">{{article.author.name}}</a></div><button class="pure-button success-bg" ng-show="!article.author.isMe" ng-click="followMe(article.author)">{{article.author.isFollow | switch:\'follow\'}}</button></div></div><ul class="inner list-inline article-info"><li><strong class="info">{{article.author.role | match:\'role\'}}</strong></li><li ng-show="article.author.score">积分：<strong>{{article.author.score}}</strong></li><li ng-show="article.author.fans">粉丝：<strong>{{article.author.fans}}</strong></li><li ng-show="article.author.follow">关注：<strong>{{article.author.follow}}</strong></li><li ng-show="article.author.articles">文章/评论：<strong>{{article.author.articles}}</strong></li><li ng-show="article.author.collections">合集：<strong>{{article.author.collections}}</strong></li></ul></div><div class="panel" ng-show="article.author.articlesList.length>0"><div class="panel-heading">作者文章</div><ul class="media-list comments"><li ng-repeat="item in article.author.articlesList"><span class="label">{{item.date | formatDate}}</span>&nbsp;<a ng-href="{{\'/\'+item._id}}" title="{{item.author.name+\'发表\'}}">{{item.title}}</a></li></ul></div><div class="panel pure-hidden-phone" ng-show="hotArticles.length>0"><div class="panel-heading">热门文章</div><ul class="media-list comments"><li class="media" ng-repeat="item in hotArticles"><div class="media-body" id="{{item._id}}"><small class="hot" title="热度">{{item.hots}}</small>&nbsp; <span class="media-content"><a ng-href="{{\'/\'+item._id}}" title="{{item.author.name+\'发表\'}}">{{item.title}}</a></span></div></li></ul></div></div></div><div gen-modal="markdownModal"><div gen-parse-md="parent.markdownHelp"></div></div><div gen-modal="removeCommentModal">确定要删除评论《{{removeComment.title}}》？</div>'), e.put("gen-modal.html", '<div class="pure-u modal fade" id="{{id}}" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header" ng-show="title"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">{{title}}</h4></div><div class="modal-body" ng-transclude=""></div><div class="modal-footer" ng-show="confirmBtn || cancelBtn || deleteBtn"><button type="button" class="pure-button warning-bg pull-left" ng-show="deleteBtn" ng-click="deleteCb()">{{deleteBtn}}</button> <button type="button" class="pure-button primary-bg" ng-show="confirmBtn" ng-click="confirmCb()">{{confirmBtn}}</button> <button type="button" class="pure-button" ng-show="cancelBtn" ng-click="cancelCb()">{{cancelBtn}}</button></div></div></div></div>'), e.put("gen-pagination.html", '<ul class="pagination" ng-show="total>perPages[0]"><li ng-class="{disabled: !prev}"><a ng-href="{{path&&prev?path+prev:\'\'}}" title="上一页" ng-click="paginationTo(prev)">&#171;</a></li><li ng-class="{active: n==pageIndex, disabled: n==\'…\'}" ng-repeat="n in showPages track by $index"><a ng-href="{{path&&n!=\'…\'&&n!=pageIndex?path+n:\'\'}}" title="{{n!=\'…\'?\'第\'+n+\'页\':\'\'}}" ng-click="paginationTo(n)">{{n}}</a></li><li ng-class="{disabled: !next}"><a ng-href="{{path&&next?path+next:\'\'}}" title="下一页" ng-click="paginationTo(next)">&#187;</a></li><li class="pure-hidden-phone" ng-class="{active: s==pageSize}" ng-repeat="s in perPages"><a ng-href="{{path&&s!=pageSize?path+\'1&s=\'+s:\'\'}}" title="每页{{s}}" ng-click="paginationTo(1, s)">{{s}}</a></li></ul>'), e.put("gen-uploader.html", '<table class="pure-table pure-table-horizontal file-upload-info" ng-show="uploader.queue.length"><thead><tr><th width="35%">名称</th><th width="15%">大小</th><th width="30%">进度</th><th width="20%">总数：{{ uploader.queue.length }}</th></tr></thead><tbody><tr ng-repeat="item in uploader.queue"><td class="file-upload-filename"><strong>{{ item.file.name }}</strong></td><td nowrap="">{{ item.file.size/1024|number:1 }} KB</td><td><div class="progress" style="margin-bottom: 0;"><div class="progress-bar" role="progressbar" ng-style="{ \'width\': item.progress + \'%\' }"></div></div></td><td nowrap=""><button type="button" class="pure-button pure-button-xsmall success-bg" ng-click="item.upload()" ng-disabled="item.isUploading"><span class="fa fa-upload"></span>上传</button> <button type="button" class="pure-button pure-button-xsmall warning-bg" ng-click="item.remove()"><span class="fa fa-trash-o"></span>删除</button></td></tr></tbody></table><div class="progress" ng-show="uploader.queue.length"><div class="progress-bar" role="progressbar" ng-style="{ \'width\': uploader.progress + \'%\' }"></div></div><div><button type="button" class="pure-button pure-button-small info-bg" ng-click="triggerUpload()"><span class="fa fa-plus-square-o"></span> 上传图片</button><input class="upload-input" ng-file-select="" type="file" multiple> <button type="button" class="pure-button pure-button-small success-bg" ng-click="uploader.uploadAll()" ng-show="uploader.queue.length" ng-disabled="!uploader.getNotUploadedItems().length"><span class="fa fa-upload"></span> 上传所有</button> <button type="button" class="pure-button pure-button-small warning-bg" ng-click="uploader.clearQueue()" ng-show="uploader.queue.length"><span class="fa fa-trash-o"></span> 删除所有</button></div><ul class="nav file-uploaded" ng-show="uploaded.length"><li ng-repeat="file in uploaded"><a class="file-thumbnail" style="background-image: url({{file.url}});" ng-click="clickImage(file)" title="{{file.name}}"></a></li></ul>'), e.put("index-article.html", '<ul class="nav nav-tabs inner"><li ng-class="{active: parent.viewPath==\'latest\'}"><a href="/latest" title="最新文章">最新</a></li><li ng-class="{active: parent.viewPath==\'hots\'}"><a href="/hots" title="热门文章">热门</a></li><li ng-class="{active: parent.viewPath==\'update\'}"><a href="/update" title="最近更新文章">更新</a></li><li ng-class="{active: !parent.viewPath}" ng-show="other._id"><a ng-href="/{{other._id}}">{{other.title}}</a></li><div class="pure-button-group right"><a href="/add" class="pure-button success-bg" ng-if="global.isLogin">发表文章</a> <a class="pure-button list-model" title="{{parent.sumModel?\'摘要模式\':\'简洁模式\'}}" ng-if="!global.isPocket" ng-class="{\'info-bg\':parent.sumModel}" ng-click="setListModel()"><i class="fa fa-th-large"></i></a></div></ul><ul class="list-inline inner"><li ng-repeat="tag in global.tagsList"><a ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-xsmall info-bg">{{tag.tag}}</a></li><li><a href="/tag" class="pure-button pure-button-xsmall"><i class="fa fa-search"></i>更多</a></li></ul><ul class="media-list"><li class="media" ng-repeat="article in articleList"><div class="media-body" id="{{article._id}}"><div class="media-header"><a ng-href="{{\'/\'+article._id}}"><i class="primary" ng-class="{\'fa fa-hand-o-up\':article.status==2, \'fa fa-external-link\':article.status!=2}"></i>{{article.title}}</a> <i ng-show="article.status==1" class="fa fa-thumbs-up hot" title="推荐"></i> <i class="fa fa-star-o right hot hover-icon" title="热度" ng-show="!global.isPhone">{{article.hots}}</i></div><div class="media-content list-content" ng-show="parent.sumModel" gen-parse-md="article.content"></div><div class="media-footer"><a ng-href="{{\'/\'+article.author._id}}"><i class="fa fa-pencil success"></i>{{article.author.name}}</a> <i class="fa fa-clock-o" title="{{article.date | formatDate:true}}发布">{{article.date | formatDate}}</i> <i class="fa fa-refresh" title="{{article.updateTime | formatDate:true}}更新">{{article.updateTime | formatDate}}</i> <i class="fa fa-comments-o" title="评论" ng-show="article.comments">{{article.comments}}</i> <a ng-repeat="tag in article.tagsList" ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-link">{{tag.tag}}</a></div></div></li></ul>'), e.put("index-tag.html", '<div class="inner page-header"><span>热门标签</span></div><ul class="inner list-inline"><li ng-repeat="tag in tagList" id="{{tag._id}}" class="text-center"><a class="pure-button pure-button-small" ng-href="{{\'/\'+tag._id}}"><strong>{{tag.tag}}</strong> <small class="muted">文章 {{tag.articles}}/会员 {{tag.users}}</small></a></li></ul>'), e.put("index.html", '<div class="pure-g-r wrap"><div class="pure-u-2-3"><div class="panel" ng-include="" src="parent.getTpl"></div><div gen-pagination="pagination"></div></div><div class="pure-u-1-3"><div class="panel pure-hidden-phone"><div class="inner text-center"><img src="http://cdn.angularjs.cn/img/angularjs.png" alt="AngularJS"><div class="text-show text-left">使用超动感HTML &amp; JS开发WEB应用！</div></div></div><div class="panel pure-hidden-phone"><div class="inner text-center"><a href="https://jianliao.com/?s=angularjs" target="_blank"><img src="http://cdn.angularjs.cn/img/teambition.png" alt="简聊"></a></div></div><div class="panel pure-hidden-phone"><div class="inner text-center"><a href="http://www.angularjs.cn/A2m9" target="_blank"><img src="http://cdn.angularjs.cn/img/apicloud.jpg" alt="APICloud"></a></div></div><div class="panel pure-hidden-phone"><div class="inner text-center"><a href="https://www.upyun.com/" target="_blank"><img src="http://cdn.angularjs.cn/img/upyun.png" alt="又拍云存储"></a><div class="text-show text-left">帮助企业提供静态文件云存储、云处理、云分发的云服务平台。<a href="http://weibo.com/upaiyun" target="_blank"><i class="fa fa-weibo fa-lg"></i></a></div><div class="text-show"><a href="http://segmentfault.com/t/%E5%8F%88%E6%8B%8D%E4%BA%91%E5%AD%98%E5%82%A8" target="_blank"><strong>segmentfault 又拍云问答专区</strong></a></div></div></div><div class="panel site-link"><div class="panel-heading muted"><i class="fa fa-link"></i>友情链接</div><ul class="inner"><li><a href="https://www.teambition.com" target="_blank"><img src="http://cdn.angularjs.cn/img/logo_teambition.png" alt="Teambition"></a></li><li><a href="https://coding.net" target="_blank"><img src="http://cdn.angularjs.cn/img/logo_coding.png" alt="Coding.net"></a></li><li><a href="http://f2e.im/" target="_blank"><img src="http://cdn.angularjs.cn/img/logo_f2e.png" alt="f2e.im"></a></li><li><a href="http://www.apicloud.com" target="_blank"><img src="http://cdn.angularjs.cn/img/logo_apicloud.png" alt="APICloud"></a></li></ul></div><div class="panel"><div class="panel-heading muted"><i class="fa fa-dashboard"></i>网站状态</div><ul class="inner list-inline"><li>访问总数：{{global.visitors}}</li><li>文章总数：{{global.articles}}</li><li>评论总数：{{global.comments}}</li><li>会员总数：{{global.users}}</li><li>在线访客：{{global.onlineNum}}</li><li>在线会员：{{global.onlineUsers}}</li><li>在线记录：{{global.maxOnlineNum}}</li><li>时间：{{global.maxOnlineTime | date:\'yy-MM-dd HH:mm\'}}</li></ul></div></div></div>'), e.put("login.html", '<div class="pure-g-r wrap"><div class="pure-u-1-2"><div class="panel pure-hidden-phone"><div class="page-header text-center"><span class="hot">欢迎回到AngularJS中文社区</span></div><div class="inner text-center"><img src="http://cdn.angularjs.cn/img/angularjs.png" alt="angularjs"><div class="text-show">使用超动感HTML &amp; JS开发WEB应用！</div><div class="text-show text-hot">QQ交流群 <strong>278252889</strong></div><div class="text-show"><a href="https://github.com/angular/angular.js" class="pure-button pure-button-small" target="_blank"><i class="fa fa-github fa-2x"></i>GitHub</a> <a href="http://weibo.com/angularjs/" class="pure-button pure-button-small" target="_blank"><i class="fa fa-weibo fa-2x"></i>WeiBo</a></div></div></div></div><div class="pure-u-1-2"><div class="panel transparent"><div class="page-header inner"><strong class="primary">用户登录</strong></div><form class="pure-form pure-form-aligned inner" novalidate><div class="pure-control-group"><label>登录名：</label> <input type="text" placeholder="name or Email or Uid" name="登录名" ng-model="login.logname" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>密码：</label> <input type="password" placeholder="password" name="密码" ng-model="login.logpwd" ng-minlength="6" ng-maxlength="20" gen-tooltip="validateTooltip" required></div><div class="pure-controls"><label for="auto-login" class="pure-checkbox"><input id="auto-login" type="checkbox" ng-model="login.logauto">3天内自动登录</label> <button class="pure-button primary-bg" ng-click="submit()">登录</button> <a class="pure-button warning-bg" ng-show="reset.title" ng-href="{{\'/reset?type=\'+reset.type}}">{{reset.title}}</a></div></form></div></div></div>'), e.put("register.html", '<div class="pure-g-r wrap"><div class="pure-u-1-2"><div class="panel pure-hidden-phone"><div class="page-header text-center"><span class="hot">欢迎来到AngularJS中文社区</span></div><div class="inner text-center"><img src="http://cdn.angularjs.cn/img/angularjs.png" alt="angularjs"><div class="text-show">使用超动感HTML &amp; JS开发WEB应用！</div><div class="text-show text-hot">QQ交流群 <strong>278252889</strong></div><div class="text-show"><a href="https://github.com/angular/angular.js" class="pure-button pure-button-small" target="_blank"><i class="fa fa-github fa-2x"></i>GitHub</a> <a href="http://weibo.com/angularjs/" class="pure-button pure-button-small" target="_blank"><i class="fa fa-weibo fa-2x"></i>WeiBo</a></div></div></div></div><div class="pure-u-1-2"><div class="panel transparent"><div class="page-header inner"><strong class="success">用户注册</strong></div><form class="pure-form pure-form-aligned inner" novalidate><div class="pure-control-group"><label>用户名：</label> <input type="text" placeholder="name" name="用户名" ng-model="user.name" ui-validate="{username:checkName,minname:checkMin,maxname:checkMax}" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>邮箱：</label> <input type="email" placeholder="email" name="邮箱" ng-model="user.email" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>密码：</label> <input type="password" placeholder="password" name="密码" ng-model="user.passwd" ng-minlength="6" ng-maxlength="20" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>重复密码：</label> <input type="password" placeholder="password" name="重复密码" ng-model="user.passwd2" ui-validate="{repasswd:\'$value==user.passwd\'}" ui-validate-watch="\'user.passwd\'" gen-tooltip="validateTooltip" required></div><div class="pure-controls"><button class="pure-button success-bg" ng-click="submit()">注册</button></div></form></div></div></div>'), e.put("reset.html", '<div class="pure-g-r wrap"><div class="pure-u-1-2"><div class="panel pure-hidden-phone"><div class="page-header text-center"><span class="hot">欢迎来到AngularJS中文社区</span></div><div class="inner text-center"><img src="http://cdn.angularjs.cn/img/angularjs.png" alt="angularjs"><div class="text-show">使用超动感HTML &amp; JS开发WEB应用！</div><div class="text-show text-hot">QQ交流群 <strong>278252889</strong></div><div class="text-show"><a href="https://github.com/angular/angular.js" class="pure-button pure-button-small" target="_blank"><i class="fa fa-github fa-2x"></i>GitHub</a> <a href="http://weibo.com/angularjs/" class="pure-button pure-button-small" target="_blank"><i class="fa fa-weibo fa-2x"></i>WeiBo</a></div></div></div></div><div class="pure-u-1-2" ng-show="parent.title"><div class="panel transparent"><div class="page-header inner"><strong class="warning">{{parent.title}}</strong></div><form class="pure-form pure-form-aligned inner" novalidate><div class="pure-control-group"><label>用户名/Uid：</label> <input type="text" placeholder="name or Uid" name="用户名/Uid" ng-model="reset.name" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>邮箱：</label> <input type="email" placeholder="email" name="邮箱" ng-model="reset.email" gen-tooltip="validateTooltip" required></div><div class="pure-controls"><button class="pure-button warning-bg" ng-click="submit()">提交</button></div></form></div></div><div gen-modal="timingModal">{{parent.timing}}秒钟后自动返回首页</div></div>'), e.put("user-article.html", '<div ng-controller="userArticleCtrl"><div class="page-header inner"><a class="pure-button list-model right" title="{{parent.sumModel?\'摘要模式\':\'简洁模式\'}}" ng-if="!global.isPocket" ng-class="{\'info-bg\':parent.sumModel}" ng-click="setListModel()"><i class="fa fa-th-large"></i></a> <strong>{{parent.title}}</strong></div><ul class="media-list"><li class="media" ng-repeat="article in articleList"><a class="media-object left" title="删除文章" ng-show="article.isAuthor||global.isEditor" ng-click="remove(article)"><i class="fa fa-trash-o"></i></a><div class="media-body" id="{{article._id}}" ng-class="{muted: article.read}"><div class="media-header"><a ng-href="{{\'/\'+article._id}}"><i class="primary" ng-class="{\'fa fa-hand-o-up\':article.status==2, \'fa fa-external-link\':article.status!=2}" ng-hide="article.isAuthor"></i>{{article.title}}</a> <i ng-show="article.status==1" class="fa fa-thumbs-up hot" title="推荐"></i> <a class="pure-button pure-button-link" ng-href="{{\'/\'+article._id+\'/edit\'}}" ng-show="isMe"><i class="fa fa-edit"></i></a> <i class="fa fa-star-o right hot hover-icon" title="热度" ng-show="!global.isPhone">{{article.hots}}</i></div><div class="media-content list-content" ng-show="parent.sumModel" gen-parse-md="article.content"></div><div class="media-footer"><a ng-href="{{\'/\'+article.author._id}}"><i class="fa fa-pencil success"></i>{{article.author.name}}</a> <i class="fa fa-clock-o" title="{{article.date | formatDate:true}}发布">{{article.date | formatDate}}</i> <i class="fa fa-refresh" title="{{article.updateTime | formatDate:true}}更新">{{article.updateTime | formatDate}}</i> <i class="fa fa-comments-o" title="评论" ng-show="article.comments">{{article.comments}}</i> <a ng-repeat="tag in article.tagsList" ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-link">{{tag.tag}}</a></div></div></li></ul><p class="inner" ng-show="articleList.length==0">暂无</p><div gen-pagination="pagination"></div><div gen-modal="removeArticleModal">确定要删除文章《{{removeArticle.title}}》？</div></div>'), e.put("user-edit.html", '<div ng-controller="userEditCtrl"><div class="page-header inner"><strong>用户设置</strong></div><form class="pure-form pure-form-aligned inner" novalidate><div class="pure-control-group"><label>&nbsp;</label> <img class="img-small pure-help-inline" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{user.avatar}}"></div><div class="pure-control-group"><label>头像URL：</label> <input class="pure-input-1-2" type="url" name="头像" ng-model="user.avatar" gen-tooltip="validateTooltip"></div><div class="pure-control-group"><label>用户名：</label> <input type="text" placeholder="name" name="用户名" ng-model="user.name" ui-validate="{username:checkName,minname:checkMin,maxname:checkMax}" gen-tooltip="validateTooltip" required></div><div class="pure-control-group"><label>邮箱：</label> <input type="email" placeholder="email" name="邮箱" ng-model="user.email" gen-tooltip="validateTooltip" required> <input type="button" class="pure-button pure-button-small success-bg" title="修改邮箱后会自动发送一封验证邮件，通过验证后才保存修改" ng-click="verifyEmail()" value="验证邮箱" gen-tooltip=""></div><div class="pure-control-group"><label>用户性别：</label><select ng-model="user.sex" ng-options="sex | match:\'gender\' for sex in sexArray"></select></div><div class="pure-control-group"><label>用户标签：</label> <textarea class="pure-input-1-2" name="用户标签" ng-model="user.tagsList" ng-list="/[,，、]/" ui-validate="{more:checkTag}" gen-tooltip="validateTooltip">\n            </textarea></div><div class="pure-controls-group"><a class="pure-button pure-button-link" ng-repeat="tag in global.tagsList" ng-click="getTag(tag)"><small>{{tag.tag}}</small></a></div><div class="pure-control-group"><label>个人简介：</label> <textarea class="pure-input-1-2" name="个人简介" ng-model="user.desc" ui-validate="{maxlength:checkDesc}" gen-tooltip="validateTooltip">\n            </textarea></div><div class="pure-control-group"><label>修改密码（不改留空）：</label> <input type="password" placeholder="password" name="密码" ng-model="user.passwd" ng-minlength="6" ng-maxlength="20" gen-tooltip="validateTooltip"></div><div class="pure-control-group"><label>再次输入密码：</label> <input type="password" placeholder="password" name="重复密码" ng-model="user.passwd2" ui-validate="{repasswd:checkPwd}" ui-validate-watch="\'user.passwd\'" gen-tooltip="validateTooltip"></div><div class="pure-controls"><button class="pure-button success-bg" type="submit" ng-click="submit()">保存</button> <button class="pure-button info-bg" ng-click="reset()">重置</button></div></form></div><div gen-modal="unSaveModal">确定要离开？未保存的数据将会丢失！</div>'), e.put("user-list.html", '<div ng-controller="userListCtrl"><div class="page-header inner"><strong>{{parent.title}}</strong></div><ul class="media-list"><li class="media" ng-repeat="user in userList"><a class="media-object left" ng-href="{{\'/\'+user._id}}"><img class="img-small" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{user.avatar}}"></a><div class="media-body" id="{{user._id}}"><div class="media-heading"><div class="media-heading"><a ng-click="followMe(user)" class="pure-button pure-button-small success-bg right" ng-class="{\'success-bg\':!user.isFollow,\'primary-bg\':user.isFollow}" ng-hide="user.isMe">{{user.isFollow | switch:\'follow\'}}</a> <a ng-href="{{\'/\'+user._id}}">{{user.name}}</a> <small class="muted">{{user.date | formatDate:true}}注册 / {{user.lastLoginDate | formatDate:true}}最后登录</small><ul class="inner list-inline article-info"><li><strong class="hot">{{user.role | match:\'role\'}}</strong></li><li ng-show="user.email">UID：<strong>{{user._id}}</strong></li><li ng-show="user.sex">性别：<strong>{{user.sex | match:\'gender\'}}</strong></li><li ng-show="user.score">积分：<strong>{{user.score}}</strong></li><li ng-show="user.fans">粉丝：<strong>{{user.fans}}</strong></li><li ng-show="user.follow">关注：<strong>{{user.follow}}</strong></li><li ng-show="user.articles">文章/评论：<strong>{{user.articles}}</strong></li><li ng-show="user.collections">合集：<strong>{{user.collections}}</strong></li></ul></div></div><div class="media-content" gen-parse-md="user.desc"></div><div><ul class="list-inline"><li ng-repeat="tag in user.tagsList"><a ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-xsmall">{{tag.tag}}</a></li></ul></div></div></li></ul><p class="inner" ng-show="userList.length==0">暂无</p><div gen-pagination="pagination"></div></div>'), e.put("user.html", '<div class="pure-g-r wrap"><div class="pure-u-2-3"><div class="panel" ng-include="" src="parent.getTpl"></div></div><div class="pure-u-1-3 aside"><div class="panel"><div class="media inner"><a class="media-object left"><img class="img-small" src="http://cdn.angularjs.cn/img/avatar.png" gen-src="{{user.avatar}}"></a><div class="media-body"><div class="media-header"><a ng-href="{{\'/\'+user._id}}">{{user.name}}</a></div><button ng-show="!parent.isMe" ng-click="followMe(user)" class="pure-button success-bg">{{user.isFollow | switch:\'follow\'}}</button></div></div><ul class="inner list-inline article-info"><li><strong class="hot">{{user.role | match:\'role\'}}</strong></li><li ng-show="user.email">Email：<strong>{{user.email}}</strong></li><li ng-show="user.sex">性别：<strong>{{user.sex | match:\'gender\'}}</strong></li><li ng-show="user.score">积分：<strong>{{user.score}}</strong></li><li ng-show="user.fans">粉丝：<strong>{{user.fans}}</strong></li><li ng-show="user.follow">关注：<strong>{{user.follow}}</strong></li><li ng-show="user.collect">收藏：<strong>{{user.collect}}</strong></li><li ng-show="user.articles">文章/评论：<strong>{{user.articles}}</strong></li><li ng-show="user.collections">合集：<strong>{{user.collections}}</strong></li><li ng-show="user.date"><strong>{{user.date | formatDate:true}}</strong>注册<br><strong>{{user.lastLoginDate | formatDate:true}}</strong>最后登录</li></ul><div class="inner article-info"><strong>用户简介：</strong><div gen-parse-md="user.desc"></div><strong>用户标签：</strong><div><a ng-repeat="tag in user.tagsList" ng-href="{{\'/\'+tag._id}}" class="pure-button pure-button-link">{{tag.tag}}</a></div></div></div><div class="panel pure-menu pure-menu-open"><ul class="text-center" ng-show="parent.isMe"><li ng-class="{active: parent.viewPath==\'index\'}"><a href="/home"><i class="fa fa-chevron-left left"></i>我的主页</a></li><li ng-class="{active: parent.viewPath==\'follow\'}"><a href="/home/follow"><i class="fa fa-chevron-left left"></i>我的关注</a></li><li ng-class="{active: parent.viewPath==\'fans\'}"><a href="/home/fans"><i class="fa fa-chevron-left left"></i>我的粉丝</a></li><li ng-class="{active: parent.viewPath==\'mark\'}"><a href="/home/mark"><i class="fa fa-chevron-left left"></i>我的收藏</a></li><li ng-class="{active: parent.viewPath==\'article\'}"><a href="/home/article"><i class="fa fa-chevron-left left"></i>我的文章</a></li><li ng-class="{active: parent.viewPath==\'comment\'}"><a href="/home/comment"><i class="fa fa-chevron-left left"></i>我的评论</a></li><li ng-class="{active: parent.viewPath==\'detail\'}"><a href="/home/detail"><i class="fa fa-chevron-left left"></i>用户设置</a></li></ul><ul class="text-center" ng-hide="parent.isMe"><li ng-class="{active: parent.viewPath==\'article\'}"><a ng-href="/{{user._id}}/article"><i class="fa fa-chevron-left left"></i>{{user.name}}的文章</a></li><li ng-class="{active: parent.viewPath==\'fans\'}"><a ng-href="/{{user._id}}/fans"><i class="fa fa-chevron-left left"></i>{{user.name}}的粉丝</a></li></ul></div></div></div>')
}]);
