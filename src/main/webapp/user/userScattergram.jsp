<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<%--引入中国地图js--%>
<script src="${path}/echarts/echarts.min.js"></script>
<script src="${path}/echarts/china.js"></script>
<script>

    $(function () {
        var chart = echarts.init(document.getElementById('main'));
        $.get("${path}/backUser/queryBySexAndCity",function (data) {
            //定义一个js对象替换下面的值
            var val = [];
            for (var i = 0; i< data.length;i++){
                //遍历赋值
                var e = data[i];
                //向数组推送数据
                val.push(
                    {
                        name:e.sex,
                        type: 'map',
                        map: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: e.city,
                    },
                )

            }

            chart.setOption({
                title: {
                    text: '用户注册分布图',   //大标题
                    subtext: '应学用户2020年度国内注册分布图',  //小标题
                    sublink: '',//超链接
                    textStyle: {    //标题打字 颜色设置
                        fontSize: 30,
                        color: "rgba(132, 70, 80, 1)"
                    },
                },
                legend: {//选项卡
                    itemHeight: 22,

                    data: ['女', '男'],
                },
                tooltip: {
                    trigger: 'item',
                    formatter:
                        '{b}<br/>{c} (人)'
                },

                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },

                visualMap: {
                    min: 1,      //最小值
                    max: 10,     //最大值
                    text: ['High', 'Low'],   //高低
                    realtime: false,
                    calculable: true,
                    inRange: {
                        color: ['lightskyblue', 'yellow', 'orangered']
                    }
                },

                series: val,
            });
        },"json");
    });
</script>

<script>
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-443efdd7d9bb4fb193ed9223f1837f5f", //替换为您的应用appkey
    });

    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        goEasy.subscribe({
            channel: "yingx_liub", //替换为您自己的channel
            onMessage: function (message) {
                var a = message.content;
                var b = JSON.parse(a);
                var s = b.list;
                //console.log(s);
                // 指定图表的配置项和数据
                //定义一个js对象替换下面的值
                var val = [];
                for (var i = 0; i< s.length;i++){
                    //遍历赋值
                    var e = s[i];
                    //向数组推送数据
                    val.push(
                        {
                            name:e.sex,
                            type: 'map',
                            map: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: e.city,
                        },
                    )

                }

                var option = ({
                    title: {
                        text: '用户注册分布图',   //大标题
                        subtext: '应学用户2020年度国内注册分布图',  //小标题
                        sublink: '',//超链接
                        textStyle: {    //标题打字 颜色设置
                            fontSize: 30,
                            color: "rgba(132, 70, 80, 1)"
                        },
                    },
                    legend: {//选项卡
                        itemHeight: 22,

                        data: ['女', '男'],
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter:
                            '{b}<br/>{c} (人)'
                    },

                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            dataView: {readOnly: false},
                            restore: {},
                            saveAsImage: {}
                        }
                    },

                    visualMap: {
                        min: 1,      //最小值
                        max: 10,     //最大值
                        text: ['High', 'Low'],   //高低
                        realtime: false,
                        calculable: true,
                        inRange: {
                            color: ['lightskyblue', 'yellow', 'orangered']
                        }
                    },

                    series: val,
                });
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    });

</script>
<div align="center">

<div id="main" style="width: 1000px;height:600px;" ></div>

</div>