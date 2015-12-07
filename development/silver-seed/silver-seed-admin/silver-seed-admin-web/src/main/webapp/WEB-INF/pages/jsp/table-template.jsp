<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${tableMeta.title}</title>
        <link rel="stylesheet" type="text/css" media="screen" href="common/js/jqgrid-4.4.5/css/ui.jqgrid.css"/>
        <link rel="stylesheet" type="text/css" media="screen" 
              href="common/js/jquery-ui-1.10.3.custom/css/ui-lightness/jquery-ui-1.10.3.custom.min.css"/>

        <script src="common/js/jqgrid-4.4.5/js/jquery-1.9.0.js" type="text/javascript"></script>
        <script src="common/js/jqgrid-4.4.5/js/jquery.jqGrid.src.js" type="text/javascript"></script>
        <script src="common/js/jqgrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function(){ 
                $("#list").jqGrid({
                    url:'${tableMeta.viewMeta.url}',
                    datatype: '${tableMeta.viewMeta.dataType}',
                    mtype: '${tableMeta.viewMeta.mtype}',
                    colNames:[${tableMeta.viewMeta.colNames}],
                    colModel :[ 
                        ${tableMeta.viewMeta.colModel}
                    ],
                    jsonReader : {
                        root: "data",
                        page: "page",
                        total: "total",
                        records: "records",
                        repeatitems: true,
                        cell: "cell",
                        id: "id",
                        userdata: "userdata",
                        subgrid: {root:"data", 
                            repeatitems: true, 
                            cell:"cell"
                        } 
                    },
                    pager: '#pager',
                    rowNum:10,
                    rowList:[10,20,30],
                    sortname: 'invid',
                    sortorder: 'desc',
                    viewrecords: true,
                    gridview: true,
                    caption: '${tableMeta.viewMeta.caption}'
                }); 
            }); 
        </script>
    </head>
    <body>
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </body>
</html>
