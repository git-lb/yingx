<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<%--引入GoEasy的js文件--%>
<%--<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.17.js"></script>--%>


<script type="text/javascript">
    $(function(){
        var myChart = echarts.init(document.getElementById('main'));
        $.ajax({
            url:"${path}/backUser/queryBySexAndMonths",
            type:"post",
            dataType:"json",
            success:function (data) {
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '近一年用户注册详情'
                    },
                    tooltip: {},
                    legend: {
                        data: ['女', '男']
                    },

                    xAxis: {
                        data: data.months
                    },
                    yAxis: {},
                    series: [
                        {
                            name: '女',
                            type: 'line',
                            data: data.woman
                        },
                        {
                            name: '男',
                            type: 'line',
                            data: data.man
                        }
                    ],

                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },
            error:function () {
            }
        });
    });
</script>
<script type="text/javascript">

    $(function () {
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-443efdd7d9bb4fb193ed9223f1837f5f", //替换为您的应用appkey
        });
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        goEasy.subscribe({
            channel: "yingx_liub", //替换为您自己的channel
            onMessage: function (message) {
                console.log(message.content);
                var val = message.content;
                var data = JSON.parse(val);
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '近一年用户注册详情'
                    },
                    tooltip: {},
                    legend: {
                        data: ['女', '男']
                    },

                    xAxis: {
                        data: data.map.months
                    },
                    yAxis: {},
                    series: [
                        {
                            name: '女',
                            type: 'line',
                            data: data.map.woman
                        },
                        {
                            name: '男',
                            type: 'line',
                            data: data.map.man
                        }
                    ],

                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    });

</script>

<div align="center">

<div id="main" style="width: 1000px;height:600px;" ></div>

</div>