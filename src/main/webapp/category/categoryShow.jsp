<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

    <script>

        //初始化数据
        $(function(){
            pageInit();
        });

        //父表格和子表格初始化函数
        function pageInit(){
            //父表格初始化数据
            $("#cateList").jqGrid(
                {
                    url : "${path}/backCategory/findCateByPage",
                    datatype : "json",
                    height : "auto",
                    autowidth:true,
                    rowNum : 8,
                    rowList : [ 8, 10, 20, 30 ],
                    pager : '#catePager',
                    viewrecords : true,   //显示数据总条数
                    editurl: "${path}/backCategory/operCategory?levels="+1, //操作父表
                    styleUI:"Bootstrap",
                    colNames : [ 'ID', '类别名', '级别'],
                    colModel : [
                        {name : 'id',index : 'id',  width : 55,align:"center"},
                        {name : 'cateName',index : 'invdate',width : 90,editable:true,align:"center"},
                        {name : 'levels',index : 'name',width : 100,align:"center"},

                    ],
                    subGrid : true,   //开启子容器
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        //subgrid_id 子表的id    row_id 父表的id
                        addSubGrid(subgrid_id,row_id);
                    },
                });

            //操作父表格
            $("#cateList").jqGrid('navGrid', '#catePager',
                { edit : false,add : true, del : true,addtext:"添加一级类别",deltext:"删除一级类别"},
                //添加提交之后关闭添加框}
                {},
                {closeAfterAdd:true },
                {
                    beforeSubmit:function (data) {  //提交之前的函数  data形参 id
                        // alert("删除一级类别"+data);
                        //删除前 查询是否有二级类别
                        $.ajax({
                            url:"${path}/backCategory/findOneCateUnderTwoCate",
                            type:"post",
                            data:{"id":data},       //以id为参数
                            success:function (map) {
                                //alert(map.status);
                                if(map.status == 1){
                                    // ==1说明此类别下没有二级类别 直接删除
                                    return "hello";
                                }else{
                                    $("#delMessage").html("<h3>此类别下有二级类别,请删除二级类别后删除！！<h3>")
                                    //自动弹出模态框
                                    $("#MyModal").modal("show");
                                }
                            }
                        });
                        return "hello";
                    }
                },  //删除提交之前的验证
            );
        }


        //子表格数据初始化函数
        function addSubGrid(subgridId,rowId) {

            var subgridTableId= subgridId + "Table";   //表格<table>id
            var pagerId = subgridId + "Page" ;      //分页<div>id

            //创建子表格和工具栏并且设置id
            $("#" + subgridId).html("" +
                "<table id='"+subgridTableId+"' />" +
                "<div id='"+pagerId+"' />"
            );

            //初始化子表格
            $("#" + subgridTableId).jqGrid(
                {
                    url : "${path}/backCategory/findCateByPage?parentId="+rowId, //修改查找数据需要传递一个父id
                    datatype : "json",
                    rowNum : 10,
                    viewrecords : true,   //显示数据总条数
                    pager : "#"+pagerId,
                    height : "auto",
                    autowidth:true,
                    rowList : [ 8, 10, 20, 30 ],
                    editurl: "${path}/backCategory/operCategory?parentId="+rowId+"&levels="+2, //操作子表格
                    styleUI:"Bootstrap",
                    colNames : [ 'ID', '类别名', '级别', '所属类别' ],
                    colModel : [
                        {name : "id",  index : "num",width : 80,key : true},
                        {name : "cateName",index : "item",  width : 130,editable:true,align:"center"},
                        {name : "levels",index : "qty",width : 70,align:"center"},
                        {name : "parentId",index : "unit",width : 70,align:"center"},
                    ],

                });
            //操作子表格
            $("#" + subgridTableId).jqGrid('navGrid',
                "#" + pagerId, {edit : false, add : true, del : true,addtext: "添加二级类别",deltext: "删除二级类别"},
                {},
                {closeAfterAdd: true},
                {
                    beforeSubmit:function (data) {  //提交之前的函数  data形参 id
                        //删除前 查询是否有二级类别
                        $.ajax({
                            url:"${path}/backCategory/findTwoCateUnderVideo",  //查找二级类别下有没有所属视屏
                            type:"post",
                            data:{"id":data},       //以id为参数
                            success:function () {

                                if(map.status == 1){
                                    // ==1说明此类别下没有视频 直接删除
                                    return "hello";
                                }else{
                                    $("#delMessage").html("<h3>此类别下有所属视频,不能删除！！<h3>")
                                    //弹出模态框
                                    $("#MyModal").modal("show");
                                }
                            }
                        });
                        return "hello";
                    }
                },
                );
        }

    </script>

<%--面板--%>
<div class="panel panel-primary">
    <div class="panel-body panel-info">
        <h2>类别信息</h2>
    </div>
</div>

<%--标签页--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#" aria-controls="home" role="tab" data-toggle="tab">类别信息</a></li>
    </ul>
</div>

<%--按钮--%>
<%--<br>
<button class="btn btn-danger">删除类别</button>
<br>
<br>--%>

<%--表单--%>
<table id="cateList"></table>

<%--分页框--%>
<div id="catePager" style="height: 60px"></div>

<%--模态框--%>
<div id="MyModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">警告框</h4>
            </div>
            <div class="modal-body">
                <p id="delMessage"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
