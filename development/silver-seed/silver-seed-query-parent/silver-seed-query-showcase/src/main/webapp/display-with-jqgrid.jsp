<%-- 
    Document   : display-with-jqgrid
    Created on : May 10, 2013, 11:04:46 AM
    Author     : CN085910
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>display with jqgrid</title>
        <link rel="stylesheet" type="text/css" media="screen" href="common/js/jqgrid-4.4.5/css/ui.jqgrid.css"/>
        <link rel="stylesheet" type="text/css" media="screen" 
              href="common/js/jquery-ui-1.10.3.custom/css/ui-lightness/jquery-ui-1.10.3.custom.min.css"/>

        <script src="common/js/jqgrid-4.4.5/js/jquery-1.9.0.min.js" type="text/javascript"></script>
        <script src="common/js/jqgrid-4.4.5/js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="common/js/jqgrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function(){ 
                $("#list").jqGrid({
                    url:'DataServlet',
                    datatype: 'xml',
                    mtype: 'GET',
                    colNames:['name','red', 'green','blue','alpha','Notes'],
                    colModel :[ 
                        {name:'name', index:'name', width:60}, 
                        {name:'red', index:'red', width:90}, 
                        {name:'green', index:'green', width:80, align:'right'}, 
                        {name:'blue', index:'blue', width:80, align:'right'}, 
                        {name:'hex', index:'hex', width:80, align:'right'}, 
                        {name:'note', index:'note', width:150, sortable:false} 
                    ],
                    jsonReader : {
                        root: "rows",
                        page: "page",
                        total: "total",
                        records: "records",
                        repeatitems: true,
                        cell: "cell",
                        id: "id",
                        userdata: "userdata",
                        subgrid: {root:"rows", 
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
                    caption: 'Color Grid'
                }); 
            }); 
        </script>
    </head>
    <body>
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </body>
</html>
