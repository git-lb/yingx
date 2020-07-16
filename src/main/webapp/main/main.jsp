<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>

    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">
    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script src="${path}/goEasy/goeasy-1.0.5.js" type="text/javascript"></script>
    <script src="${path}/echarts/echarts.min.js"></script>

    <style>
        .carousel-inner > .item > img, .carousel-inner > .item > a > img {
            line-height: 25;
        }
    </style>

</head>
<body>
<!--顶部导航-->
<%-- 导航条(navbar 包含着布局容器) --%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">应学App后台管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:
                    <mark>${sessionScope.admin.username}</mark>
                </a></li>
                <li class="dropdown">
                    <a href="${path}/backAdmin/logout">
                        退出登录 <span class="glyphicon glyphicon-log-out"></span>
                    </a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!--栅格系统-->
<!--左边手风琴部分-->
<!--巨幕开始-->
<!--右边轮播图部分-->
<!--页脚-->
<!--栅格系统-->

<div class="container-fluid">
    <div class="row">
        <%-- 手风琴 --%>
        <div class="col-sm-2">
            <div class="panel-group text-center" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body ">
                            <p>
                                <%-- 将标签内容替换 --%>
                                <a href="javascript:$('#showContent').load('/yingx/user/userShow.jsp')"
                                   class="btn btn-success">用户展示</a>
                            </p>
                        </div>
                        <div class="panel-body ">
                            <p>
                                <%-- 将标签内容替换 --%>
                                <a href="javascript:$('#showContent').load('/yingx/user/userDetail.jsp')"
                                   class="btn btn-success">用户详情</a>
                            </p>
                        </div>
                        <div class="panel-body ">
                            <p>
                                <%-- 将标签内容替换 --%>
                                <a href="javascript:$('#showContent').load('/yingx/user/userScattergram.jsp')"
                                   class="btn btn-success">用户分布</a>
                            </p>
                        </div>
                    </div>




                </div>

                <hr>

                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingOne1">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne1"
                               aria-expanded="true" aria-controls="collapseOne1">
                                类别管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne1" class="panel-collapse collapse " role="tabpanel"
                         aria-labelledby="headingOne1">
                        <div class="panel-body">
                            <p>
                                <a href="javascript:$('#showContent').load('/yingx/category/categoryShow.jsp')"
                                   class="btn btn-danger">类别展示</a>
                                <%-- 将标签内容替换成页面 --%>
                                <%--<a href="javascript:$('#showContent').load('/category/categoryShow.jsp')"  class="btn btn-danger">类别展示</a>--%>
                            </p>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-warning">
                    <div class="panel-heading" role="tab" id="headingOne2">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne2"
                               aria-expanded="true" aria-controls="collapseOne2">
                                视屏管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne2" class="panel-collapse collapse " role="tabpanel"
                         aria-labelledby="headingOne2">
                        <div class="panel-body">
                            <p>
                                <a href="javascript:$('#showContent').load('/yingx/video/videoShow.jsp')" class="btn btn-warning">视屏展示</a>
                            </p>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingOne3">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne3"
                               aria-expanded="true" aria-controls="collapseOne3">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne3" class="panel-collapse collapse " role="tabpanel"
                         aria-labelledby="headingOne3">
                        <div class="panel-body">
                            <p>
                                <a href="javascript:$('#showContent').load('/yingx/log/logShow.jsp')" class="btn btn-primary">日志展示</a>
                            </p>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="headingOne4">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne4"
                               aria-expanded="true" aria-controls="collapseOne4">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne4" class="panel-collapse collapse " role="tabpanel"
                         aria-labelledby="headingOne4">
                        <div class="panel-body">
                            <p>
                                <a href="javascript:$('#showContent').load('/yingx/feedback/feedbackShow.jsp')" class="btn btn-primary">反馈展示</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%-- 内容 --%>
        <div class="col-sm-10" id="showContent">
            <%-- 巨幕 --%>
            <div class="jumbotron">
                <h1>欢迎来到应学视屏App管理后台系统</h1>
            </div>

            <!--轮播图-->
            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active" style="text-align: center">
                        <img src="${path}/bootstrap/img/pic4.jpg" alt="..." style="display: inline-block">
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item" style="text-align: center">
                        <img src="${path}/bootstrap/img/pic3.jpg" alt="..." style="display: inline-block">
                        <div class="carousel-caption">

                        </div>
                    </div>

                    <div class="item" style="text-align: center">
                        <img src="${path}/bootstrap/img/pic2.jpg" alt="..." style="display: inline-block">
                        <div class="carousel-caption">
                        </div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>


        </div>
    </div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
    <%--页脚--%>
    <div class="footer ">
        <div class="container">
            <%--<div class="row footer-top">
                <div class="col-sm-6 col-lg-6">
                    <h4></h4>
                    <p>欢迎你加入，这里有你想要的.</p>
                </div>
                <div class="col-sm-6  col-lg-5 col-lg-offset-1">
                    <div class="row about">
                        <div class="col-xs-3">
                            <h4>关于</h4>
                            <ul class="list-unstyled">
                                <li>
                                    <a href="">关于我们</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-xs-3">
                            <h4>联系方式</h4>
                            <ul class="list-unstyled">
                                <li>
                                    <a target="_blank" title="云梦网官方微博" href="">新浪微博</a>
                                </li>
                                <li>
                                    <a href="">电子邮件</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>--%>
            <hr>
            <div class="row footer-bottom">
                <ul class="list-inline text-center">
                    <li>应学教育 &copy;45410821.163.com</li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>



