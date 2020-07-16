<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<%--<style>
隐藏<input type="file">后面的"未选择文件"
    input[type="file"] {
        color: transparent;
    }
</style>--%>

<%--jquery的表单验证js文件--%>
<script src="${path}/login/assets/js/jquery.validate.min.js"></script>

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
                url:"${path}/backVideo/queryAllVideoByPage",
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
                    "<td>"+list.title+"</td>"+
                    "<td>"+list.brief+"</td>"+
                    "<td><img width='150px' height='80px' src="+list.coverPath+"></td>"+
                    "<td><video width='150px' height='80px' controls src="+list.videoPath+'></video>'+ "</td>"+
                    "<td>"+list.createDate+"</td>"+
                    "<td>"+list.categoryId+"</td>"+
                    "<td>"+list.userId+"</td>"+
                    "<td>"+list.groupId+"</td>"+
                    "<td>"+
                    "<button class='btn btn-danger' onclick=\"deleteVideo('" + val + "')\">删除</button>&nbsp;&nbsp;"+
                    "&nbsp;&nbsp;<button class='btn btn-success' onclick=\"updateVideo('" + val + "')\">修改</button>"+
                    "</td>"+
                    "</tr>"
                );
            });
        }

        //查出所有二级类别 用于上传视屏选择类别
        function queryAllTwoCate() {
            $.post("${path}/backCategory/queryAllTwoCategory",function (data) {
                //将查询出来的二级类别数据拼接到模态框的下拉框中
                $.each(data,function (index,twoCategoryList) {
                    $("#categorySelect").append(
                        "<option value="+twoCategoryList.id+">"+twoCategoryList.cateName+"</option>"
                    );
                });

            },"json");
        }

        //查出所有二级类别 用于修改视屏信息回显
        function queryAllTwoCategory() {
            $.post("${path}/backCategory/queryAllTwoCategory",function (data) {
                //将查询出来的二级类别数据拼接到模态框的下拉框中
                $.each(data,function (index,twoCategoryList) {
                    $("#exampleInputUpdateCategoryId").append(
                        "<option value="+twoCategoryList.id+">"+twoCategoryList.cateName+"</option>"
                    );
                });
            },"json");
        }

        //修改视屏信息 （数据回显）
        function updateVideo(val) {
            //回显所有类别的函数
            queryAllTwoCategory();
            //将接受的json串转成js对象
            var videoDOM = $.parseJSON(val);
            //将文件对应的值赋给模态框
            $("#exampleInputUpdateId").val(videoDOM.id); //id
            $("#exampleInputUpdateTitle").val(videoDOM.title);  //标题
            $("#exampleInputUpdateBrief").val(videoDOM.brief);  // 简介
            $("#exampleInputOldCoverPath").val(videoDOM.coverPath); //封面
            $("#exampleInputOldVideoPath").val(videoDOM.videoPath); //视屏
            $("#exampleInputUpdateCreatDate").val(videoDOM.createDate);  //上传时间
            $("#exampleInputUpdateCategoryId option:selected").val(videoDOM.categoryId);//表单中类别的id
            $("#exampleInputUpdateUserId").val(videoDOM.userId);
            $("#exampleInputUpdateGroupId").val(videoDOM.groupId);
            //显示模态框
            $("#updateModal").modal('show');
        }

        //删除视频
        function deleteVideo(val){
          //将json串转换成js对象
            var video = $.parseJSON(val);
            $.ajax({
                url:"${path}/backVideo/deleteVideo",
                data:video,
                dataType:"text",
                method:"post",
                success: function () {
                    pageInit();
                }
            });
        }


        $(function () {

            /*jquery的表单验证*/
            $.extend($.validator.messages, {
                required: "必选字段",
                /*remote: "请修正该字段",
                email: "请输入正确格式的电子邮件",
                url: "请输入合法的网址",
                date: "请输入合法的日期",
                dateISO: "请输入合法的日期 (ISO).",
                number: "请输入合法的数字",
                digits: "只能输入整数",
                creditcard: "请输入合法的信用卡号",
                equalTo: "请再次输入相同的值",
                accept: "请输入拥有合法后缀名的字符串",
                maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
                minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
                rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
                range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
                max: $.validator.format("请输入一个最大为 {0} 的值"),
                min: $.validator.format("请输入一个最小为 {0} 的值")*/
            });


            //初始化表格
            pageInit();

/*
            //添加模态框关闭 数据清空
           $('#myModal').on('hidden.bs.modal', function (){
                // document.getElementById("#myForm").reset();
                setTimeout(function () {

                },4000);

            });

            //修改模态框关闭 数据清空
            $('#updateModal').on('hidden.bs.modal', function (){
                setTimeout(function () {
                },3000);

            });
 */

            //点击上一页
            $("#upPage").click(function () {
                var row  = $("#nowRows option:selected").val(); //获取每页展示的条数
                //判断是否为第一页
                var now =$("#Npage").text(); //span标前获取当前所在页数
                var pag = parseInt(now);
                if(pag !== 1){
                    $.post("${path}/backVideo/queryAllVideoByPage",{"rows":row,"page":pag-1},function (map) {
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
                    $.post("${path}/backVideo/queryAllVideoByPage",{"page":noPg+1,"rows":row},function (map) {
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
                    $.post("${path}/backVideo/queryAllVideoByPage",{"rows":row,"page":skPage},function (map) {
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
                $.post("${path}/backVideo/queryAllVideoByPage",{"page":1,"rows":rows},function (map) {
                    dataEach(map);
                },"JSON");
            });

            //上传视频 单击事件
            $("#addVideo").click(function () {
                //弹出一个模态框 模态框中包含一个form表单
                queryAllTwoCate();
                $("#myModal").modal('show');
            });


            //上传视频 Save按钮解提交数据,发送ajax提交数据
            $("#uploadVideo").click(function () {
                var formData = new FormData();
                var title = $("#exampleInputTitle").val(); //表单中标题的内容
                var brief = $("#exampleInputBrief").val(); //表单中简介的内容
                var categoryId  = $("#categorySelect option:selected").val();//表单中类别的id
                var file = document.getElementById("exampleInputVideo").files[0];
                formData.append('file', file);
                formData.append('title',title);
                formData.append('brief', brief);
                formData.append('categoryId', categoryId);
                //开启表单验证
                var isOk = $("#myForm").valid();

                if(file == null || file == ''){
                    $("#updateMeg").append("请选择上传的视频");
                    return false;
                }
                if (file.lastIndexOf('.')==-1){    //如果不存在"."
                    $("#updateMeg").append("路径不正确");
                    return false;
                }
                var AllImgExt=".jpg|.jpeg|.gif|.bmp|.png|";
                var extName = file.substring(file.lastIndexOf(".")).toLowerCase();//（把路径中的所有字母全部转换为小写）
                if(AllImgExt.indexOf(extName+"|")==-1)
                {
                    var ErrMsg="该文件类型不允许上传。请上传 "+AllImgExt+" 类型的文件，当前文件类型为"+extName;
                    alert(ErrMsg);
                    return false;
                }

                /* if(isOk){

                     //关闭模态框
                     $("#myModal").modal("hide");
                     //发送ajax
                     $.ajax({
                         url:"${path}/backVideo/videoUpload",
                        method: "POST",
                        dataType: "text",
                        processData: false,//用于对data参数进行序列化处理 这里必须false
                        contentType: false, //必须
                        data:formData,
                        success:function (data) {
                            $("#myForm")[0].reset();
                            pageInit();
                        }
                    });
                } else {

                }*/

            });

//回调函数不能执行  原因是返回数据格式不是json 但是dataType指定格式是json
            //Save按钮提交修改后数据,发送ajax提交数据
            $("#saveUpdateVideo").click(function () {
                //表单数据传递
                var formData = new FormData();
                formData.append('id',$("#exampleInputUpdateId").val());
                formData.append('title',$("#exampleInputUpdateTitle").val());
                formData.append('brief', $("#exampleInputUpdateBrief").val());
                formData.append('coverPath', $("#exampleInputOldCoverPath").val());
                formData.append('videoPath', $("#exampleInputOldVideoPath").val());
                formData.append('createDate', $("#exampleInputUpdateCreatDate").val());
                formData.append('categoryId', $("#exampleInputUpdateCategoryId option:selected").val());
                formData.append('userId', $("#exampleInputUpdateUserId").val());
                formData.append('groupId', $("#exampleInputUpdateGroupId").val());
                formData.append('coverFile', document.getElementById("exampleInputUpdateCoverPath").files[0]);
                formData.append('videoFile', document.getElementById("exampleInputUpdateVideoPath").files[0]);
                //发送ajax
                $.ajax({
                    url:"${path}/backVideo/updateVideo",
                    method: "post",
                    dataType: "json",
                    processData: false,//用于对data参数进行序列化处理 这里必须false
                    contentType: false, //必须
                    data:formData,
                    success: function (data) {
                        $("#updateModalForm")[0].reset();
                        pageInit();
                    },
                    error: function () {
                        console.log("走着了");
                    }
                });

            });

        });


        //与下面隐藏的div对应
        /*$(function () {
            var row  = $("#nowRows option:selected").val();
            var pag  = $("#nowPage .active").text();
            pageInit(pag,row);
            //点击页码分页查询
            //分页查询需要两个参数  rows:每行展示多少条数据  page:当前页
            $("#nowPage").on("click","li",function(){
                $("#videoBody").empty();
                //获取当前页数  默认为第1页
                $(this).siblings('li').removeClass("active"); //移除上个li样式
                if($(this).index() != 0 & $(this).index() != 6){ //上一页和下一页不显示样式
                    $(this).addClass("active");   //给当前点击的li加上样式
                }else {
                    $(this).next("li").addClass("active");
                   /!* $(this).addClass("active");
                    setTimeout(function () {
                        $(this).removeClass("active");
                    },1000);*!/
                }

                //alert($(this).index());
                /!*!//获取当前每页展示的数据条数  默认为5条
                row  = $("#nowRows option:selected").val();
                pageInit(pag,row)*!/
            });
*/

    </script>



<%--面板--%>
<div class="panel panel-info" style="background: #1b6d85">
    <div class="panel-body panel-info">
        <h2>视屏信息</h2>
    </div>
</div>

<%--标签页--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#" aria-controls="home" role="tab" data-toggle="tab">视屏信息</a></li>
    </ul>
</div>
<br>
<button class="btn btn-success" id="addVideo">上传视屏</button>
<br>

<%--表格--%>
<table class="table table-striped table-bordered">
    <thead >
        <tr style="text-align:center" class="success">
            <th style="text-align: center;line-height: 50px;padding-top: 5px">Id</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">标题</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">简介</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">封面路径</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">视屏路径</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">上传时间</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">类别ID</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">用户ID</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px">分组ID</th>
            <th style="text-align: center;line-height: 50px;padding-top: 0px;width: 80px;">操作</th>
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

<%--添加视频模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Fill in the uploaded video information.</h4>
            </div>
            <div class="modal-body">

                <%--表单--%>
                <form id="myForm" method="post"  enctype="multipart/form-data" >
                    <div class="form-group">
                        <p class="help-block"><span id="updateMeg"></span></p>
                        <label for="exampleInputTitle">标题</label>
                        <input type="text" required name="title" class="form-control" id="exampleInputTitle"  placeholder="Title">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputBrief">简介</label>
                        <input type="text"required name="brief" class="form-control" id="exampleInputBrief"  placeholder="Brief Introduction">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputVideo">上传视频</label>
                        <input type="file" name="file" id="exampleInputVideo" >
                    </div>
                    <div class="form-group">
                        <label for="categorySelect">视频类别</label>
                        <select class="form-control" id="categorySelect">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" <%--data-dismiss="modal"--%> id="uploadVideo">Save</button>
            </div>
        </div>
    </div>
</div>

<%--数据回显模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="updateModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Fill in the uploaded video information.</h4>
            </div>
            <div class="modal-body">

                <%--表单--%>
                <form id="updateModalForm" action="" enctype="multipart/form-data" method="post">
                    <div class="form-group">
                        <p class="help-block"><span></span></p>
                        <label for="exampleInputUpdateId">ID</label>
                        <input type="text" name="id" class="form-control" readonly id="exampleInputUpdateId">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateTitle">标题</label>
                        <input type="text" name="title" class="form-control" id="exampleInputUpdateTitle">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateBrief">简介</label>
                        <input type="text" name="brief" class="form-control" id="exampleInputUpdateBrief">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateCoverPath">封面</label>
                        <input type="file"  name="coverPath"  id="exampleInputUpdateCoverPath">
                    </div>
                        <div class="form-group">
                            <%--封面原路径--%>
                            <input type="text" name="coverPath"  hidden id="exampleInputOldCoverPath">
                        </div>

                    <div class="form-group">
                        <label for="exampleInputUpdateVideoPath">视屏</label>
                        <input type="file" name="videoPath"   id="exampleInputUpdateVideoPath">
                    </div>
                        <div class="form-group">
                            <%--视屏原路径--%>
                            <input type="text" name="videoPath" hidden id="exampleInputOldVideoPath">
                        </div>

                    <div class="form-group">
                        <label for="exampleInputUpdateCreatDate">上传时间</label>
                        <input type="date" name="createDate" class="form-control" id="exampleInputUpdateCreatDate">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateCategoryId">类别</label>
                        <select class="form-control" name="categoryId" id="exampleInputUpdateCategoryId">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateUserId">用户ID</label>
                        <input type="text" name="userId" class="form-control" id="exampleInputUpdateUserId">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputUpdateGroupId">分组ID</label>
                        <input type="text" name="groupId" class="form-control" id="exampleInputUpdateGroupId">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveUpdateVideo">Save</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<%--<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4 col-xs-offset-2">
                <ul class="pagination" id="nowPage">
                    <li>
                        <a href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="active"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li>
                        <a href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
        </div>

        <div class="col-xs-1 col-xs-pull-2" style="padding-top: 20px">
                <select class="form-control" id="nowRows">
                    <option class="selected" value="5">5</option>
                    <option value="10">10</option>
                    <option value="20">20</option>
                </select>
        </div>
    </div>

</div>--%>



