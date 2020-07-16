<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>yingx Login</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${path}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${path}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${path}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${path}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${path}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${path}/login/assets/js/scripts.js"></script>
    <script src="${path}/login/assets/js/jquery.validate.min.js"></script>

    <script>
        $(function () {

            //jquery的表单验证
            $.extend($.validator.messages, {
                required: "必选字段",
                remote: "请修正该字段",
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
                min: $.validator.format("请输入一个最小为 {0} 的值")
            });

            //点击图片更换验证码
            $("#captchaImage").click(function () {
                $(this).prop("src", "${path}/backAdmin/getImageCode?time=" + new Date().getTime())
            });


            //点击登录
            $("#loginButtonId").click(function () {
                let $msgDiv = $("#msgDiv");
                //验证用户名和密码以及验证码输入不为空
                let nameval = $("#username").val();
                let pwdval = $("#password").val();
                let codeval = $("#enCode").val();
               // console.log(val)
               if(nameval == null || nameval == ""){
                   $msgDiv.text("用户名不能为空!");
               }else if (pwdval == null || pwdval == ""){
                   $msgDiv.text("密码不能为空!");
               }else if (codeval == null || codeval == ""){
                   $msgDiv.text("验证码不能为空!");
               }else{
                   //发送请求验证用户名和密码的正确性
                   $.ajax({
                       url:"${path}/backAdmin/login",
                       dataType:"json",
                       type:"POST",
                       data:$("#loginForm").serialize(),
                       success:function (data) {
                           if(data.msg == "success"){
                               location.href="${path}/main/main.jsp";
                           }else if (data.msg == "验证码错误!"){
                               $msgDiv.text(data.msg);
                               $("#captchaImage").prop("src", "${path}/backAdmin/getImageCode?time=" + new Date().getTime())
                           }
                           $msgDiv.text(data.msg);
                       }
                   });
               }
            });


        });

    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>YINGX</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>YINGX</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showAll</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input id="username" type="text" name="username" placeholder="请输入用户名..." class="form-username form-control" required id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input id="password" type="password" name="password" placeholder="请输入密码..." class="form-password form-control" required id="form-password">
                            </div>
                            <div class="form-group">
                                <%--<label class="sr-only" for="form-code">Code</label>--%>
                                <img id="captchaImage" style="height: 48px" class="captchaImage" src="${path}/backAdmin/getImageCode">
                                <input  id="enCode" style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;" required type="test" name="enCode" id="form-code">
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;" id="loginButtonId" value="登录">
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="copyrights">Collect from <a href="http://www.cssmoban.com/" title="网站模板">网站模板</a></div>


<!-- Javascript -->

<!--[if lt IE 10]>
<script src="assets/js/placeholder.js"></script>
<![endif]-->

</body>

</html>