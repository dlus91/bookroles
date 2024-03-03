<%@ page import="com.bookroles.Constant" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>bookroles.com</title>
    <link rel="shortcut icon" href="./image/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<script type="text/javascript" src="./js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="./js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./js/arttemplate.js"></script>
    <script type="text/javascript" src="./js/crypto-js2.min.js"></script>
    <style type="text/css">
        @font-face{
            font-family:bookroles1;
            src:url('css/bookroles1.ttf');
        }
        body{
            background-color: #EEEEEE;
        }
        .navbar{
            margin-top: -55vh;
            opacity:0.9;
        }
        #collapse_Navbar a{
            color: black;
            font-family: bookroles1;
        }
        #collapse_Navbar .show{
            background-color: lightgrey;
        }
        #collapse_Navbar .active{
            text-decoration:underline;
            font-weight: bold;
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
        .big-box {
            background-width: 100%;
            background-image:url("./image/16pic_7402886_b.jpg");
            background-size: cover;
            -moz-background-size: cover;
            background-repeat: no-repeat;
            height: 80vh;
        }
        .big-box h1{
            font-size: 50px;
            font-family: bookroles1;
            font-style: oblique;
            font-weight: bold;
            padding-top: 60vh;
            color:black;
            text-align:center;

        }
        .top-search{
            padding: 0.5rem 0;
            opacity:0.9;
        }
        .book-notice{
            padding-bottom: 3px;
            margin-bottom: 0;
            font-family: bookroles1;
        }
        button{
            font-family: bookroles1;
        }
        input{
            font-family: bookroles1;
        }
        .books-content{
            font-family: bookroles1;
        }
        .books-content .book{
            box-model: border-box;
            border: 3px solid transparent;
            background-clip:padding-box;
        }
        .books-content .book .book-top-img{
            margin-top: 3px;
        }
        .books-content .book:hover{
            border-bottom: 1px solid;
            background: #fff;
            color: #333;
        }
        .books-content .book .card-img-top:hover{
            position: relative;
            transform: scale(1.02);
            transition: all 2s ease-out 0.5s;
            cursor: pointer;
        }
        .books-content .book .card-img-top{
            width: 255px;
            height: 340px;
        }
        .books-content .book .card-title{
            width:100%;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
            margin-bottom: 0.25rem;
        }
        .books-content .book .card-text{
            border-top: 1px solid #EEEEEE;
        }
        .books-content .book .card-body{
            padding: 0.5rem;
        }
        .books-content .book .smaller{
            margin-left: -2px;
            width:27%;
            height:27px;
            background-color:rgba(0,0,0,0.6);
            position:absolute;
            top:2px;
            text-align:center;
            color:white;
        }
        .books-content .book .smaller a{
            color: white;
            font-size: 14px;
        }

        #lookMore{
            margin-bottom: 10px;
        }

    </style>

    <script id="template_book_rows" type="text/html">
        {{each list as value index}}
        <div class="card col-lg-3 col-md-offset-2 book img-fluid text-center">
            <a class="book-top-img" href="./detail/{{value.file}}" target="_blank">
                <img class="card-img-top img-fluid img-thumbnail" src=
                        "./image/{{value.image}}"
                     alt="{{value.name}}"
                     title="{{value.name}}"
                >
            </a>
            <div class="smaller"><a href="javascript:void(0);" archiveType="{{value.archive_type_id}}" onclick="showNavItemContent('{{value.archive_type_id}}')">{{value.archive_type_name}}</a></div>
            <div class="card-body">
                <h5 alt="{{value.book_name}}" title="{{value.name}}" class="card-title">
                    <a href="./detail/{{value.file}}" class="text-dark" target="_blank"><b>{{value.name}}</b></a>
                </h5>
                <p class="card-text">
                    {{each value.author as author_name}}
                        <a alt="{{author_name}}"
                           title="{{author_name}}"
                           href="javascript:void(0);" class="text-black-50" onclick="showAuthorTypeBook('{{author_name}}',1)">{{author_name}}</a>
                    {{/each}}
                </p>
            </div>
        </div>
        {{/each}}
    </script>

    <script id="template_breadcrumb_li" type="text/html">
        当前位置：
        {{each list as value index}}
            <li class="breadcrumb-item">{{value}}</li>
        {{/each}}
    </script>

</head>
<body>
    <nav class="navbar navbar-expand-sm bg-secondary navbar-dark sticky-top container-fluid">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapse_Navbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse col-lg-10" id="collapse_Navbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="javascript:void(0);" id="home_nav" data-toggle="tab">推荐</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" id="technology_nav">科学技术</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="2" id="computer_nav">计算机</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="3" id="software_nav">软件</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="4" id="hardware_nav">硬件</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" id="economics_management_nav">经济管理</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="5" id="economics_nav">经济学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="6" id="behaviorEconomics_nav">行为经济学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="7" id="management_nav">管理学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="8" id="finance_nav">金融学</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" id="humanities_nav">人文社科</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="9" id="society_nav">社会科学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="10" id="psychology_nav">心理学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="11" id="history_nav">历史</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="12" id="influence_nav">影响力</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" id="success_cognize_nav">成功认知</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="13" id="success_nav">成功学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="14" id="cognize_nav">认知科学</a>
                        <a class="dropdown-item" href="javascript:void(0);" archiveType="15" id="workplace_nav">职场进阶</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="javascript:void(0);" id="merge_nav" archiveType="16" data-toggle="tab">合集</a>
                </li>
            </ul>
        </div>
    </nav>

    <div id="header" class="top-search container sticky-top bg-white" style="display: none;">

    </div>

    <div class="big-box container-fluid">
        <h1>
            bookroles.com
        </h1>
        <div class="input-group col-lg-4 mx-auto justify-content-start">
            <input class="form-control" type="text" id="navSearchInput" placeholder="查询书籍/出版社">
            <button class="btn btn-primary" id="navSearchBtn">
                查询
            </button>
        </div>
    </div>

    <div class="container">
        <nav>
            <ol class="breadcrumb book-notice">

            </ol>
        </nav>
    </div>

    <div class="container books-content">
        <div class="row">

        </div>

    </div>

    <div class="container mt-1" id="lookMore" style="display: none;">
        <div class="row bg-white mx-auto justify-content-center" >
            <button id="lookMoreBtn" type="button" style="margin-top:15px;margin-bottom: 15px;" class="btn btn-info btn-lg" row="1" module="">点击更多↓</button>
        </div>
    </div>

    <div id="footer"></div>

<script type="text/javascript">

    function home() {
        window.location.href = "home.jsp";
    }

    <%
        /*response.setIntHeader("Refresh", 5);*/
        long ms = Long.parseLong(request.getSession().getAttribute("currentMs").toString());
        Map<String, String> keyMap = Constant.getKeystoreMap(ms);
        System.out.println(keyMap);
    %>
    const key = "<%=keyMap.get("key")%>";
    const iv = "<%=keyMap.get("iv")%>";
    function encrypt(content) {
        return CryptoJS.AES.encrypt(content, CryptoJS.enc.Utf8.parse(key),{
            iv: CryptoJS.enc.Utf8.parse(iv),
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        }).toString();
    }

    function decrypt(content) {
        let decrypted = CryptoJS.AES.decrypt(content, CryptoJS.enc.Utf8.parse(key), {
            iv: CryptoJS.enc.Utf8.parse(iv),
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    }

	function headerSearch(){
        let word = $("#headerSearchInput").val();
        $("#lookMoreBtn").attr("module","search").attr("word",word).attr("row","1").removeAttr("author");
        showSearchContent(word, 1);
    }
    function GetQueryString(){
        let queryParam = {};
        var search = window.location.search;
        if(search && search.indexOf("?") != -1 ){
            var strArray = search.split("?")[1].split("&");
            $.each(strArray,function(index,str){
                let strParam = str.split("=")
                queryParam[strParam[0]] = decodeURIComponent(strParam[1]);
            })
        }
        return queryParam;
    }

    function showSearchContent(word, row){
	    if(!word){
            $("#home_nav").trigger("click");
            return;
        }
        let param = {"word" : word};
        if(row && row > 0) param.row = row;
        let jParam = JSON.stringify(param)
        param = encrypt(jParam);
        $.post("/bookroles/book/findByWord.do",param,function(result,status){
            if(status == "success"){
                let resultData = "";
                if(decrypt(result).trim()){
                    resultData = JSON.parse(decrypt(result));
                }
                if(row > 1){
                    showAppendContent(resultData);
                }else{
                    showContent(resultData);
                }
                if(resultData && resultData.length > 0){
                    $("#lookMore").show();
                }else{
                    $("#lookMore").hide();
                }
            }else{
                console.log("加载失败")
            }
        });
    }

    function showAuthorTypeBook(author_name, row){
        $("#collapse_Navbar .nav-item:not(.dropdown) a").each(function(index,that){
            $(that).removeClass("active");
        });
        $("#collapse_Navbar .dropdown a").each(function(index,that){
            $(that).removeClass("active");
        });
        showBreadcrumbContent(author_name);
        let param = {"author" : author_name};
        if(row && row > 0) {param.row = row;}
        let jParam = JSON.stringify(param)
        param = encrypt(jParam);
        $.post("/bookroles/book/findByAuthor.do",param,function(result,status){
            if(status == "success"){
                let resultData = "";
                if(decrypt(result).trim()){
                    resultData = JSON.parse(decrypt(result));
                }
                if(row > 1){
                    showAppendContent(resultData);
                }else{
                    showContent(resultData);
                }
                if(resultData && resultData.length > 0){
                    $("#lookMore").show();
                }else{
                    $("#lookMore").hide();
                }
            }else{
                console.log("加载失败")
            }
        });
        $("#lookMoreBtn").attr("module","author").attr("author",author_name).attr("row","1").removeAttr("word");
    }

    function showNavItemContent(id){
        $("#collapse_Navbar .nav-item [archiveType]").each(function(index, that){
            if($(that).attr("archiveType") == id){
                $(that).trigger("click");
            }
        });
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
            value.author = value.author.split(" ");
        });
        $(".books-content .row").html("").html(template("template_book_rows",{"list" : result}));
    }

    function showAppendContent(result){
        if(!result){
            $(".books-content .row").html("");
            return;
        }
        $.each(result,function(index, value){
            value.author = value.author.split(" ");
        });
        $(".books-content .row").append(template("template_book_rows",{"list" : result}));
    }

	$(function(){

        loadMethod();

        loadEvent();

        runEvent();

        function loadMethod(){
            if (!Array.isArray) {
                Array.isArray = function(arg) {
                    return Object.prototype.toString.call(arg) === '[object Array]';
                };
            }
        }

        function loadEvent(){
            $("#collapse_Navbar .nav-item").hover(function(){
                $(this).addClass("active");
            },function(){
                $(this).removeClass("active");
            });
            $("#collapse_Navbar .nav-item:not(.dropdown) a").on("click",function() {
                let bookNavName = $(this).attr("id");
                $(this).parents(".navbar-nav").find(".nav-item:not(.dropdown)").each(function(index,that){
                    if(bookNavName != $(that).find("a").attr("id")){
                        $(that).find("a").removeClass("active");
                    }
                });
                $("#collapse_Navbar .dropdown a").each(function(index,that){
                    $(that).removeClass("active");
                });
                showBreadcrumbContent($(this).html());
                let archiveTypeId = $(this).attr("archiveType");
                switch (bookNavName) {
                    case "home_nav" : showHomeContent(1); $("#lookMoreBtn").attr("module","home").attr("row","1").removeAttr("author").removeAttr("word"); break;
                    case "merge_nav" : showMergeContent(archiveTypeId, 1); $("#lookMoreBtn").attr("module","merge").attr("row","1").removeAttr("author").removeAttr("word"); break;
                    default : showHomeContent(1); $("#lookMoreBtn").attr("module","home").attr("row","1").removeAttr("author").removeAttr("word"); break;
                };
            });
            $("#collapse_Navbar .nav-item .dropdown-menu a").on("click",function() {
                let bookNavName = $(this).attr("id");
                $(this).addClass("active");
                $("#collapse_Navbar .nav-item:not(.dropdown) a").each(function(index,that){
                    $(that).removeClass("active");
                });
                $(this).parents(".navbar-nav").find(".dropdown").each(function(index,that){
                    $(that).find(".dropdown-item").each(function(index,that){
                        if(bookNavName != $(that).attr("id")){
                            $(that).removeClass("active");
                        }
                    });
                });
                showBreadcrumbContent([$(this).parent(".dropdown-menu").siblings("a").html(),$(this).html()]);
                let archiveTypeId = $(this).attr("archiveType");
                showArchiveTypeContent(archiveTypeId, 1);
                $("#lookMoreBtn").attr("module","archiveType").attr("row","1").removeAttr("author").removeAttr("word");
            });

            $("#navSearchBtn").on("click",function(){
                let word = $("#navSearchInput").val();
                showSearchContent(word, 1);
                $("#lookMoreBtn").attr("module","search").attr("word",word).attr("row","1").removeAttr("author");
            });

            $("#lookMoreBtn").on("click",function(){
                let row = $(this).attr("row");
                let module = $(this).attr("module");
                let author = $(this).attr("author");
                let word = $(this).attr("word");
                row++;
                switch (module) {
                    case "home" : showHomeContent(row); break;
                    case "merge" : showMergeContent(row); break;
                    case "archiveType" :
                        showArchiveTypeContent($("#collapse_Navbar .nav-item .dropdown-menu .active").attr("archivetype"), row);
                        break;
                    case "author" :
                        showAuthorTypeBook(author, row);
                        break;
                    case "search" :
                        word = "";
                        if($("#headerSearchInput").is(":hidden")){
                            word = $("#navSearchInput").val();
                        }else{
                            word = $("#headerSearchInput").val();
                        }
                        showSearchContent(word, row);
                        break;
                    default : showHomeContent(row); break;
                }
                $(this).attr("row",row);
            });

            function showArchiveTypeContent(archiveTypeId, row){
                let param = {"archiveTypeId" : archiveTypeId};
                if(row && row > 0) param.row = row;
                let jParam = JSON.stringify(param)
                param = encrypt(jParam);
                $.post("/bookroles/book/findByArchiveType.do",param,function(result,status){
                    if(status == "success"){
                        let resultData = "";
                        if(decrypt(result).trim()){
                            resultData = JSON.parse(decrypt(result));
                        }
                        if(row > 1){
                            showAppendContent(resultData);
                        }else{
                            showContent(resultData);
                        }
                        if(resultData && resultData.length > 0){
                            $("#lookMore").show();
                        }else{
                            $("#lookMore").hide();
                        }
                    }else{
                        console.log("加载失败")
                    }
                });
            }

            function showHomeContent(row){
                let param = {};
                if(row && row > 0) param.row = row;
                let jParam = JSON.stringify(param)
                param = encrypt(jParam);
                $.post("/bookroles/book/findHot.do",param,function(result,status){
                    if(status == "success"){
                        let resultData = "";
                        if(decrypt(result).trim()){
                            resultData = JSON.parse(decrypt(result));
                        }
                        if(row > 1){
                            showAppendContent(resultData);
                        }else{
                            showContent(resultData);
                        }
                        if(resultData && resultData.length > 0){
                            $("#lookMore").show();
                        }else{
                            $("#lookMore").hide();
                        }
                    }else{
                        console.log("加载失败")
                    }
                });
            }

            function showMergeContent(archiveTypeId, row){
                console.log("合集");
                showArchiveTypeContent(archiveTypeId, row)
            }

        };

        function runEvent(){

            $("#header").load("./header.html");

            $(window).scroll(function(){
                if($(window).scrollTop() > 190 && $("#header").is(":hidden")){
                    $(".navbar").css("visibility","hidden");
                    $("#headerSearchInput").val($("#navSearchInput").val());
                    $("#header").fadeIn();
                }else if($(window).scrollTop() < 190 && !$("#header").is(":hidden")){
                    $("#header").hide();
                    $("#navSearchInput").val($("#headerSearchInput").val());
                    $(".navbar").css("visibility","visible");
                }
            });

            let queryParam = GetQueryString();
            let module = queryParam.module
            switch (module) {
                case "search" : loadSearchModule(queryParam); break;
                case "archiveType" : loadNavItemMudule(queryParam); break;
                case "author" : loadAuthorModule(queryParam); break;
                default : $("#home_nav").trigger("click"); break;
            }

            function loadNavItemMudule(queryParam) {
                let archiveTypeiId = queryParam.id;
                if(archiveTypeiId && archiveTypeiId != "null") {
                    showNavItemContent(archiveTypeiId);
                }
            }

            function loadAuthorModule(queryParam){
                let author = queryParam.author;
                if(author && author != "null") {
                    showAuthorTypeBook(author, 1);
                }
            }

            function loadSearchModule(queryParam){
                let word = queryParam.word;
                if(word && word != "null"){
                    $("#navSearchInput").val(word);
                    showSearchContent(word, 1);
                    $("#lookMoreBtn").attr("module","search").attr("word",word).attr("row","1").removeAttr("author");
                }
            }

            $("#footer").load("./footer.html");


        }

	});

</script>

</body>
</html>