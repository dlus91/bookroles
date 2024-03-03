<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>bookroles.com</title>
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<script type="text/javascript" src="./js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="./js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./js/arttemplate.js"></script>
    <style type="text/css">
        body{
            background-color: #EEEEEE;
        }
        .bigBox {
            background-width: 100%;
            background-image:url("./image/16pic_7402886_b.jpg");
            background-size: cover;
            -moz-background-size: cover;
            background-repeat: no-repeat;
            height: 80vh;
        }
        .bigBox h1{
            font-size: 40px;
            font-family: fantasy;
            font-style: oblique;
            padding-top: 60vh;
            color:black;
            text-align:center;
        }
        .navbar{
            margin-top: -55vh;
            opacity:0.9;
        }
        #collapse_Navbar a{
            color: black;
        }
        #collapse_Navbar .show{
            background-color: lightgrey;
            text-decoration:underline;
            font-weight: bold;
        }
        #collapse_Navbar .active{
            text-decoration:underline;
            font-weight: bold;
        }
        .nav{
            margin-top: -10vh;
            opacity:0.9;
        }
        #collapse_Navbar .nav-item{
            margin-left: 10px;
            margin-right: 10px;
        }
        .books-content{
        }
        .books-content .book{
            margin-top: 5px;
            box-model: border-box;
            border: 3px solid transparent;
            background-clip:padding-box;
        }
        .books-content .book:hover{
            border-bottom: #909090 2px solid;
            background: #fff;
            color: #333;
        }
        .books-content .book .card-img-top:hover {
            position: relative;
            transform: scale(1.02);
            transition: all 2s ease-out 0.5s;
            cursor: pointer;
        }
        .books-content .book .card-img-top{
            width: 255px;
            height: 340px;
        }
        .books-content .book .smaller{
            width:27%;
            height:27px;
            background-color:rgba(0,0,0,0.6);
            position:absolute;
            top:0;
            text-align:center;
            color:white;
        }
        .books-content .book .smaller a{
            color: white;
            font-size: 14px;
        }
        .dropdown .dropdown-menu{
            -webkit-animation: swing-in-top-fwd 2s cubic-bezier(0.175, 0.885, 0.320, 1.275) alternate both;
            animation: swing-in-top-fwd 2s cubic-bezier(0.175, 0.885, 0.320, 1.275) alternate both;
        }
        @-webkit-keyframes swing-in-top-fwd {
            0% {
                -webkit-transform: rotateX(-100deg);
                transform: rotateX(-100deg);
                -webkit-transform-origin: top;
                transform-origin: top;
                opacity: 0;
            }
            100% {
                -webkit-transform: rotateX(0deg);
                transform: rotateX(0deg);
                -webkit-transform-origin: top;
                transform-origin: top;
                opacity: 1;
            }
        }
        @keyframes swing-in-top-fwd {
            0% {
                -webkit-transform: rotateX(-100deg);
                transform: rotateX(-100deg);
                -webkit-transform-origin: top;
                transform-origin: top;
                opacity: 0;
            }
            100% {
                -webkit-transform: rotateX(0deg);
                transform: rotateX(0deg);
                -webkit-transform-origin: top;
                transform-origin: top;
                opacity: 1;
            }
        }

    </style>

	<script id="template_book_rows" type="text/html">
        {{each list as value index}}
            <div class="card col-lg-3 col-md-offset-2 book img-fluid">
                <a class="mx-auto justify-content-center" href="#">
                    <img class="card-img-top img-thumbnail" src=
                            "{{value.image}}"
                         alt="{{value.book_name}}"
                         title="{{value.book_name}}"
                    >
                </a>
                <div class="smaller"><a href="#">{{value.type_name}}</a></div>
                <div class="card-body">
                    <h3 class="card-title" style="text-align: center;">
                        <a href="#">{{value.book_name}}</a>
                    </h3>
                    <p class="card-text" style="text-align: center;">
                        {{each value.author_name as authorName}}
                            <a href="#" onclick="showAuthorTypeBook('{{authorName}}')">{{authorName}}</a>
                        {{/each}}
                    </p>
                </div>
            </div>
        {{/each}}
	</script>

    <script id="template_breadcrumb_li" type="text/html">
        当前归类：
        {{each list as value index}}
            <li class="breadcrumb-item">{{value}}</li>
        {{/each}}
    </script>


</head>
<body>
    <%--<nav class="navbar navbar-expand-sm bg-secondary navbar-dark sticky-top container-fluid">
        <button class="navbar-toggler" type="button"
                data-toggle="collapse" data-target="#collapse_Navbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapse_Navbar">
            <ul class="navbar-nav">
                <li class="nav-item" data-toggle="dropdown">
                    <a class="nav-link" data-toggle="dropdown" href="#" id="home_nav">推荐</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" id="technology_nav">科学技术</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#" id="computer_nav">计算机</a>
                        <a class="dropdown-item" href="#" id="software_nav">软件</a>
                        <a class="dropdown-item" href="#" id="hardware_nav">硬件</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" id="economics_management_nav">经济管理</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#" id="economics_nav">经济学</a>
                        <a class="dropdown-item" href="#" id="behaviorEconomics_nav">行为经济学</a>
                        <a class="dropdown-item" href="#" id="management_nav">管理学</a>
                        <a class="dropdown-item" href="#" id="finance_nav">金融学</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" id="humanities_nav">人文社科</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#" id="society_nav">社会科学</a>
                        <a class="dropdown-item" href="#" id="psychology_nav">心理学</a>
                        <a class="dropdown-item" href="#" id="history_nav">历史</a>
                        <a class="dropdown-item" href="#" id="influence_nav">影响力</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" id="success_cognize_nav">成功认知</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#" id="success_nav">成功学</a>
                        <a class="dropdown-item" href="#" id="cognize_nav">认知科学</a>
                        <a class="dropdown-item" href="#" id="workplace_nav">职场进阶</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="dropdown" href="#" id="merge_nav">合集</a>
                </li>
            </ul>
        </div>


        <form class="form-inline" action="#">

            <div class="input-group">
                <span id="basic-addon1" class="input-group-text">
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-zoom-in" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"></path>
                            <path d="M10.344 11.742c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1 6.538 6.538 0 0 1-1.398 1.4z"></path>
                            <path fill-rule="evenodd" d="M6.5 3a.5.5 0 0 1 .5.5V6h2.5a.5.5 0 0 1 0 1H7v2.5a.5.5 0 0 1-1 0V7H3.5a.5.5 0 0 1 0-1H6V3.5a.5.5 0 0 1 .5-.5z"></path>
                        </svg>
                        搜索
                    </a>
                </span>
                <button class="btn btn-dark" style="display: none;" type="submit">
                    查询
                </button>
                <input type="text" class="form-control mr-sm-5" style="display: none;" placeholder="查询书籍" aria-label="回车查询书籍">
            </div>
        </form>
    </nav>--%>

    <nav class="navbar navbar-expand-sm bg-secondary navbar-dark sticky-top container-fluid">
        <button class="navbar-toggler" type="button"
                data-toggle="collapse" data-target="#collapse_Navbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <ul class="nav nav-tabs" id="myTab" role="tablist" style="margin-top: 0;">
            <li class="nav-item">
                <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab">Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab">Contact</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="home-tab1" data-toggle="tab" href="#home" role="tab">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="profile-tab1" data-toggle="tab" href="#profile" role="tab">Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="contact-tab1" data-toggle="tab" href="#contact" role="tab">Contact</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="home-tab2" data-toggle="tab" href="#home" role="tab">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="profile-tab2" data-toggle="tab" href="#profile" role="tab">Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="contact-tab2" data-toggle="tab" href="#contact" role="tab">Contact</a>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" >...1</div>
            <div class="tab-pane fade" id="profile" role="tabpanel" >...2</div>
            <div class="tab-pane fade" id="contact" role="tabpanel" >...3</div>
        </div>


    </nav>

    <div class="bigBox container-fluid">
        <h1>
            bookroles.com
        </h1>
        <form class="form-inline mx-auto justify-content-center" action="#">
            <input class="form-control col-lg-3" type="text" placeholder="查询书籍">
            <button class="btn btn-dark" type="submit">
                查询
            </button>
        </form>
    </div>

    <div class="container">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">

            </ol>
        </nav>
    </div>

    <div class="container books-content">
        <div class="row mt-3">

        </div>

    </div>



<script type="text/javascript">

	function getParam() {
		return "${param.id}";
	}

	function loadMethod(){
        if (!Array.isArray) {
            Array.isArray = function(arg) {
                return Object.prototype.toString.call(arg) === '[object Array]';
            };
        }
    }

    function showAuthorTypeBook(author_name){
        showBreadcrumbContent(author_name);
        showContent();
    }

    function showBreadcrumbContent(value){
        if(!Array.isArray(value)){
            value = [value];
        }
        $(".breadcrumb").html("").html(template("template_breadcrumb_li",{"list" : value}));
    }

    function showContent(result){
        if(!result){
            $(".books-content .row").html("");
            return;
        }
        $.each(result,function(index, value){
            value.author_name = value.author_name.split(" ");
        });
        $(".books-content .row").html("").html(template("template_book_rows",{"list" : result}));
    }

	$(function(){
	    loadMethod();

        loadEvent();

        runEvent();

        function loadEvent(){
            $("#basic-addon1").on("click",function(){
                if($(this).siblings("input").is(":hidden")){
                    $(this).siblings("input").fadeIn();
                    $(this).hide();
                    $(this).siblings("button").show();
                }
            });
            $("#collapse_Navbar .nav-item").hover(function(){
                $(this).addClass("active");
            },function(){
                $(this).removeClass("active");
            });
            $("#collapse_Navbar .nav-item:not(.dropdown) a").on("click",function() {
                let bookNavName = $(this).attr("id");
                showBreadcrumbContent($(this).html());
                switch (bookNavName) {
                    case "home_nav" : showHomeContent(); break;
                    case "merge_nav" : showMergeContent(); break;
                    default : showHomeContent(); break;
                };
                // $(this).parent(".nav-item").addClass("show");
            });

            $("#collapse_Navbar .nav-item .dropdown-menu a").on("click",function() {
                let bookNavName = $(this).attr("id");
                console.log(bookNavName);
                showBreadcrumbContent([$(this).parent(".dropdown-menu").siblings("a").html(),$(this).html()]);
                switch (bookNavName) {
                    case "computer_nav" : showComputerContent(); break;
                    case "software_nav" : showSoftContent(); break;
                    case "hardware_nav" : showHardContent(); break;
                    case "economics_nav" : showEconomicsContent(); break;
                    case "behaviorEconomics_nav" : showBehaviorEconomicsContent(); break;
                    case "management_nav" : showManagementContent(); break;
                    case "finance_nav" : showFinanceContent(); break;
                    case "society_nav" : showSocietyContent(); break;
                    case "psychology_nav" : showPsychologyContent(); break;
                    case "history_nav" : showHistoryContent(); break;
                    case "influence_nav" : showInfluenceContent(); break;
                    case "success_nav" : showSuccessContent(); break;
                    case "cognize_nav" : showCognizeContent(); break;
                    case "workplace_nav" : showWorkplaceContent(); break;
                    default : showHomeContent(); break;
                };
            });

            function showHomeContent(){
                let result = [{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                },{
                    "book_name" : "技术陷阱",
                    "author_name" : "弗雷・卡尔",
                    "image" : "./image/1693380712834.jpg",
                    "type_name" : "认知科学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "认知科学"
                },{
                    "book_name" : "情绪词典",
                    "author_name" : "朱建军 曹昱",
                    "image" : "./image/1693383144955.jpg",
                    "type_name" : "认知科学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "认知科学"
                },{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "认知科学"
                },{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "科学技术"
                },{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "科学技术"
                },{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                },{
                    "book_name" : "价值共生",
                    "author_name" : "陈春花",
                    "image" : "./image/1693382935689.jpg",
                    "type_name" : "科学技术"
                },{
                    "book_name" : "持续成长",
                    "author_name" : "李尚龙",
                    "image" : "./image/1693381712215.jpg",
                    "type_name" : "成功学"
                }
                ];
                showContent(result);
            }

            function showMergeContent(){
                showContent();
            }

            function showComputerContent(){
                showContent();
            }

            function showComputerContent(){
                showContent();
            }

            function showSoftContent(){
                showContent();
            }

            function showHardContent(){
                showContent();
            }

            function showEconomicsContent(){
                showContent();
            }

            function showBehaviorEconomicsContent(){
                showContent();
            }

            function showManagementContent(){
                showContent();
            }

            function showFinanceContent(){
                showContent();
            }

            function showSocietyContent(){
                showContent();
            }

            function showPsychologyContent(){
                showContent();
            }

            function showHistoryContent(){
                showContent();
            }

            function showInfluenceContent(){
                showContent();
            }

            function showSuccessContent(){
                showContent();
            }

            function showCognizeContent(){
                showContent();
            }

            function showWorkplaceContent(){
                showContent();
            }

        }


        function runEvent(){
            $("#home_nav").trigger("click");

        }

	});
</script>
</body>
</html>