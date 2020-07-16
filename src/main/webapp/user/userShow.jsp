<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

    <script>

        //页面懒加载 延迟加载
        $(function () {
            init();

            //发送验证码
            $("#sendPhoneCode").click(function () {
                var phoneVal  = $("#phone").val();
                //将js对象转成json格式的数据

                $.ajax({
                    url:"${path}/backUser/sendPhoneCode",
                    method:"post",
                    data:{"phone":phoneVal},
                    dateType:"json",
                    success:function (message) {
                        $("#phone").val("");
                        $("#message").text(message);
                        $('.alert').removeClass('hide').addClass('in').addClass('alert-success');
                        setTimeout(function () {
                            $('.alert').addClass('hide');
                        },2000);
                        init();
                    },
                    error:function () {
                        alert("2");
                    }
                });
            });

            //导出用户信息
            $("#outputUserInformation").click(function () {
                $.ajax({
                    url:"${path}/backUser/outputUserInformation",
                    type:"post",
                    dataType: "json",
                    success:function (data) {
                        $("#message").text(data.message);
                        $('.alert').removeClass('hide').addClass('in').addClass('alert-success');
                        setTimeout(function () {
                            $('.alert').addClass('hide');
                        },2000);
                    },
                    error: function (data) {
                        $("#message").text(data.message);
                        $('.alert').removeClass('hide').addClass('in').addClass('alert-danger');
                        setTimeout(function () {
                            $('.alert').addClass('hide');
                        },2000);
                    }
                });


            });

        });

        //初始化表格数据
        function init() {

                $("#tablist").jqGrid({
                    url: "${path}/backUser/queryByPager",
                    datatype: "json",
                    styleUI:"Bootstrap",
                    rowList : [ 10, 20, 30],
                    autowidth: true,  //随父容器的大小改变
                    pager: "#listPager",  //分页功能
                    viewrecords : true, //表格右下角是否显示有多少条数据
                    rowNum: 10, //每页显示多少条数据
                    height:"auto",
                    cellEdit: false, //单元格编辑功能
                    editurl: "${path}/backUser/operateUser",
                    //multiselect : true, //多选按钮
                    //toolbar:[true, "top"], 工具栏
                    //prmNames:{id:"0"},  设置默认参数
                    // caption: " ", //表头标题
                    colNames: ["Id", "手机号", "昵称","头像","简介","微信","注册时间","状态","性别","城市"],
                    colModel: [
                        {name:"id",align:"center"},
                        {name:"phone",editable:true,align:"center"},
                        {name:"username",editable:true,align:"center"},

                        {name: "headImg",
                            edittype:"file",
                            editable:true,
                            align:"center",
                            //cellvalue 单元格值
                            //options 操作
                            //rowObject  行对象
                            formatter:function (cellvalue, options, rowObject) {
                                return "<img src="+cellvalue+" width='120px' height='100px' >"
                            }
                        },

                        {name:"brief",editable:false,align:"center"},
                        {name:"wechat",editable:false,align:"center"},
                        {name:"createDate",editable:false,align:"center"},

                        {name:"status",editable:false,align:"center",
                            formatter:function (cellvalue, options, rowObject) {
                                if(cellvalue == 1){  //判断状态值 =1为正常状态
                                    return "<button  onclick='updateUserStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")' class='btn btn-success'>冻结</button>"
                                }else{
                                    return "<button  onclick='updateUserStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")' class='btn btn-danger'>解除冻结</button>"

                                }
                            }},
                        {name:"sex",align:"center",editable:true,edittype: 'select',editoptions: {value:'男:男;女:女'},

                        },


                        {name:"city",align:"center",editable:true,edittype: 'select',
                            editoptions:
                                {value:
                         '北京:北京;  上海:上海 ; 南京:南京; 河南:河南 ;天津:天津;湖北:湖北 ;湖南:湖南;山西:山西 ; 安微:安微 ; 杭州:杭州'
                                }
                        }
                    ],
                });

                //操作工具栏
                $("#tablist").jqGrid("navGrid", "#listPager",
                    {edit:false,add:true,del:true,search:false,addtext:"添加"},
                    {},//修改之后的操作
                    {//添加之后的操作
                        closeAfterAdd:true,  //关闭添加框
                        afterSubmit:function (data) { //添加提交之后的操作
                           // console.log(data.responseJSON.id);
                            //jqGrid添加图片时 图片路径错误 需要添加过后修改照片的路径以及上传图片
                            //需要添加之后返回一个参数 便于照片路径的修改
                            $.ajaxFileUpload({
                                url:"${path}/backUser/headUpLoad",
                                type:"post",
                                data:{"id":data.responseJSON.id},       //以id为参数
                                fileElementId:"headImg",  //对应着colModel中的name属性对应的字段
                                success:function () {
                                    //上传成功之后刷新整个页面
                                    $("#tablist").trigger("reloadGrid");
                                }
                            });
                            return "null";  //如果不返回 则设置的添加框不会关闭
                        }
                    },

                    {},//删除之后的操作
                    {},//查询之后的操作
                );
        }

        //修改用户状态
        function updateUserStatus(id,status) {
            $.ajax({
                url:"${path}/backUser/updateUserStatus",
                type: "POST",
                data:{"id":id,"status":status},
                success:function () {
                    //刷新表格数据
                    $("#tablist").trigger("reloadGrid");
                }
            });
        }


    </script>

<%--面板--%>
<div class="panel panel-primary">
    <div class="panel-body panel-info">
        <h2>用户信息</h2>
    </div>
</div>
<%--警告框--%>
<div class="alert alert-dismissable hide" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <span id="message"></span>
</div>


<%--标签页--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <div class="row">
        <div class="col-xs-1">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户信息</a></li>
        </div>
            <%--验证码输入框--%>
            <div class="input-group" style="width: 300px;float: right">
                <input type="text" id="phone" class="form-control" placeholder="请输入手机号" aria-describedby="basic-addon2">
                <span class="input-group-addon" id="sendPhoneCode">点击发送验证码</span>
            </div>
        </div>
    </ul>
</div>

<%--按钮--%>

<button id="outputUserInformation" class="btn btn-danger">导出用户信息</button>
<br>



<%--表单--%>
<table id="tablist"></table>

<%--分页框--%>
<div id="listPager" style="height: 60px"></div>
