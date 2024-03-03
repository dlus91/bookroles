<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/9/1
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>bookroles.com</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./bootstrap-icons/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="./css/monokai_sublime.min.css">
    <script type="text/javascript" src="./js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./js/bootstrap.min.js"></script>
    <script type="text/javascript" src="./js/marked.min.js"></script>
    <script type="text/javascript" src="./js/highlight.min.js"></script>
    <style type="text/css">
        body{
            background-color: #EEEEEE;
        }
        .top-search{
            padding: 0.5rem 0;
            opacity:0.9;
        }
        .card{
            margin-bottom: 5px;
        }
        .card .card-img-top{
            width: 255px;
            height: 340px;
        }
        .card .content-intro{
            border: 1px solid #EEEEEE;
        }
        .card .card-detail-row span:first-child{

        }
        .card .card-detail-row span:nth-child(2){

        }
    </style>
</head>
<body>
    <div id="header" class="top-search container sticky-top bg-white"></div>

    <div class="container card">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title"><b>技术陷阱</b></h3>
                <a href="#" class="card-link text-info">
                    <i class="bi bi-archive text-info"></i>
                    认知科学</a>
                <a href="#" class="card-link text-info">
                    <i class="bi bi-file-person text-info"></i>
                    弗雷・卡尔</a>
                <span class="card-link">
                    <i class="bi bi-eye-fill"></i>
                    22</span>
            </div>
            <div class="row no-gutters">
                <div class="col-lg-3 text-left">
                    <img class="card-img-top img-fluid img-thumbnail" src="./image/1693380712834.jpg" alt="技术陷阱">
                </div>
                <div class="col-lg-9">
                    <div class="card-body card-detail-row">
                        <p class="card-text"><span><i class="bi bi-bookmarks"></i>出版社:</span><span>中信出版社</span></p>
                        <p class="card-text"><span><i class="bi bi-calendar3"></i>出版日期:</span><span>2020/06</span></p>
                        <p class="card-text"><span><i class="bi bi-list-stars"></i>评分:</span><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-half"></i><i class="bi bi-star"></i></p>
                        <p class="card-text"><span><i class="bi bi-arrow-up-square"></i> 上传时间:</span><span>2周前</span></p>
                        <p class="card-text"><span><i class="bi bi-arrow-down-square"></i>格式:</span><span>epub</span></p>
                        <p class="card-text"><span><i class="bi bi-archive"></i>归类:</span><span>认知科学</span></p>
                        <p class="card-text"><span><i class="bi bi-file-person"></i>作者:</span><span>弗雷・卡尔</span></p>
                        <p class="card-text"><span><i class="bi bi-eye"></i>查询次数:</span><span>22</span></p>
                    </div>
                </div>
            </div>
            <div class="card-body content-intro">
                <h5 class="card-title"><b>简介</b></h5>
                <p class="card-text">

                </p>
            </div>
<%--            <ul class="list-group list-group-flush">
                <li class="list-group-item">An item</li>
                <li class="list-group-item">A second item</li>
                <li class="list-group-item">A third item</li>
            </ul>--%>
            <div class="card-body">
                <h5 class="card-title"><b>下载地址</b></h5>
                <a target="_blank" href="https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd=7ihr" class="card-link">https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd=7ihr</a>
            </div>
        </div>

    </div>

    <div id="footer"></div>


    <script type="text/javascript">

        $(function(){
            hljs.initHighlightingOnLoad();
            markedRun1();
            // markedRun2();
            runEvent();

            function markedRun1(){
                var rendererMD = new marked.Renderer();
                marked.setOptions({
                    renderer: rendererMD,
                    gfm: true,
                    tables: true,
                    breaks: false,
                    pedantic: false,
                    sanitize: false,
                    smartLists: true,
                    smartypants: false,
                    highlight: function (code) {
                        return hljs.highlightAuto(code).value;
                    }
                });

                var str1 = "<p>本书系统而全面地回顾了近几百年<a target='_blank' href='https://baike.baidu.com/item/技术/13014499?fromModule=lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p>\n" +
                    "<p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 [1] </p>\n" +
                    "<p><a target='_blank' href='https://baike.baidu.com/item/工业革命/895?fromModule=lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p>\n" +
                    "<p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p>\n" +
                    "<p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p>\n" +
                    "<p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>";

                var str2 = '```js\n console.log("hello"); \n```';
                // var markdownObj = marked.lexer(str1);//把text解析为一个marked.js的内部对象
                // console.log(markdownObj);//又把这个对象转化为html字符串。（<p>text</p>）
                // str1 = marked.parser(markdownObj);
                $(".content-intro .card-text").html(str1);





            }

            function runEvent(){
                $("#header").load("./header.html");

                $("#footer").load("./footer.html");
            };
        });

    </script>
</body>
</html>
