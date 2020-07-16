<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    //页面初始化
    function pageInit(){
        //初始化当前页为   page = 1 ;  rows = 5 ;
        //获取 下拉框的值
        var row  = $("#nowRows option:selected").val();
        var pag  = 1;
        //页面数据初始化
        $.ajax({
            //分页查询全部视频展示
            url:"${path}/backLog/queryAllLogByPage",
            method:"post",
            data:{"rows":row,"page":pag},
            dataType:"json",
            success:function (map) {
                dataEach(map);
            }
        });
    };

    //数据遍历
    function dataEach(map){
        //表格数据清空
        $("#videoBody").empty();
        //拼接新数据
        $("#Npage").text(map.page);
        $("#tatolPage").text(map.total);
        var videoList = map.list;
        $.each(videoList,function (index,list) {
            var val = JSON.stringify(list).replace(/"/g, "&quot;");//将js对象转换成json串
            $("#videoBody").append(
                "<tr>"+
                "<td>"+list.id+"</td>"+
                "<td>"+list.adminName+"</td>"+
                "<td>"+list.operDate+"</td>"+
                "<td>"+list.brief+"</td>"+
                "<td>"+list.status+"</td>"+
                "</tr>"
            );
        });
    }


    $(function () {
        pageInit();
        //点击上一页
        $("#upPage").click(function () {
            var row  = $("#nowRows option:selected").val(); //获取每页展示的条数
            //判断是否为第一页
            var now =$("#Npage").text(); //span标前获取当前所在页数
            var pag = parseInt(now);
            if(pag !== 1){
                $.post("${path}/backLog/queryAllLogByPage",{"rows":row,"page":pag-1},function (map) {
                    dataEach(map);
                },"JSON");
            }else{
                //不做操作
            }
        });

        //点击下一页
        $("#nex").click(function () {
            var row  = $("#nowRows option:selected").val(); //获取每页展示的条数
            //判断是否为最后一页
            var NowPage =$("#Npage").text(); //span标签获取当前所在页数
            var lastPage =$("#tatolPage").text(); //span标签获取总页数
            var noPg = parseInt(NowPage); //转换成数值类型
            var lapg = parseInt(lastPage);
            if(noPg !== lapg){
                $.post("${path}/backLog/queryAllLogByPage",{"page":noPg+1,"rows":row},function (map) {
                    dataEach(map);
                },"JSON");
            }else{
                //不做操作
            }
        });

        //跳页浏览
        $("#pageSkip").click(function () {
            var row  = $("#nowRows option:selected").val(); //获取每页展示的条数
            var lastPage =$("#tatolPage").text(); //soan标前获取总页数
            var  skPage = $("#skipToPage").val(); //跳转到的页数值
            var skp = parseInt(skPage);
            var lap = parseInt(lastPage);
            if(skp<=lap || skp>0){
                $.post("${path}/backLog/queryAllLogByPage",{"rows":row,"page":skPage},function (map) {
                    dataEach(map);
                },"JSON");
            }else{
                //不做操作
            }
        });

        //下拉框值改变事件
        $("#nowRows").change(function(){
            var rows = $("#nowRows").val();
            $(this).attr("selected")
            $.post("${path}/backLog/queryAllLogByPage",{"page":1,"rows":rows},function (map) {
                dataEach(map);
            },"JSON");
        });


    });

</script>

<%--面板--%>
<div class="panel panel-info" style="background: #1b6d85">
    <div class="panel-body panel-info">
        <h2>日志信息</h2>
    </div>
</div>

<%--标签页--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#" aria-controls="home" role="tab" data-toggle="tab">日志信息</a></li>
    </ul>
</div>


<%--表格--%>
<table class="table table-striped table-bordered">
    <thead >
        <tr style="text-align:center" class="success">
            <th style="text-align: center;line-height: 50px;padding-top: 5px">Id</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">管理员</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">操作时间</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">简介</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">状态</th>
        </tr>
    </thead>
    <tbody id="videoBody">

    </tbody>
</table>
<table class="tab tab-content col-lg-offset-2">
    <tr>
        <td>当前页数：
            <span id="Npage"></span>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
            <ul class="pagination" id="upPage">
                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;上一页</span>
                    </a>
                </li>
            </ul>
        </td>

        <td>
            <ul class="pagination" id="nex">
                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">下一页&raquo;</span>
                    </a>
                </li>
            </ul>
        </td>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span>当前每页展示条数：</span>
        </td>
        <td>
            <select class="form-control" id="nowRows">
                <option class="selected" value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
            </select>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>跳转至</td>
        <td>
            <form>
                <input type="text" style="width: 50px"  value=""  id="skipToPage"/>
            </form>
        </td>
        <td>页</td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
            <button class="btn btn-info" id="pageSkip">跳转</button>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
            总页数：
            <span id="tatolPage">

            </span>
        </td>
    </tr>
</table>
