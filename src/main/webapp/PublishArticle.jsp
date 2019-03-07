<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/2/28
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PublishArticle</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css" />
    <link href="/css/adminAlter.css" rel="stylesheet" type="text/css" />
    <link href="/css/form-validation.css" rel="stylesheet" type="text/css" />
</head>
<body>


<script>
    var errorInfo="${error}";
    if(errorInfo.trim().length!=0){
        alert("${error}");
    }
</script>
<c:remove var="error" scope="session"></c:remove>

<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">PublishArticle</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">

        </div>
    </nav>
</header>

<!-- Begin page content -->
<main role="main" class="container">
    <!--
     <p class="lead">Pin a fixed-height footer to the bottom of the viewport in desktop browsers with this custom HTML and CSS. A fixed navbar has been added with <code>padding-top: 60px;</code> on the <code>body &gt; .container</code>.</p>
     <p>Back to <a href="../sticky-footer">the default sticky footer</a> minus the navbar.</p>-->


    <form method="post" action="/publisharticle">
        <h2 class="mt-5" align="center">标题：<input type="text" name="title"></h2>
        <br>
        <h5>正文：</h5>
        <textarea  class="form-control" rows="15" style="resize:none" name="content"></textarea>
        <br />
        <div align="center">
            <button type="submit" class="btn btn-sm btn-primary">发布</button>&nbsp&nbsp&nbsp&nbsp
            <button type="reset" class="btn btn-sm btn-info">重置</button>&nbsp&nbsp&nbsp&nbsp
            <a href="javascript:window.history.back(-1)"><button type="button" class="btn btn-sm btn-danger">返回</button></a>
        </div>
        <br />
    </form>



    </div>
</main>

<!-- <footer class="footer">
   <div class="container">
     <span class="text-muted">Place sticky footer content here.</span>
   </div>
 </footer>-->

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="/js/bootstrap.min.js"></script>
</body>
</html>
