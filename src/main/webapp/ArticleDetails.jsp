<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/3/1
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ArticleDetails</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css" />
    <link href="/css/adminAlter.css" rel="stylesheet" type="text/css" />
    <link href="/css/form-validation.css" rel="stylesheet" type="text/css" />
</head>
<body>

<script>
    var successInfo="${success}";
    if(successInfo.trim().length!=0){
        alert("${success}");
    }
</script>

<c:remove var="success" scope="session"></c:remove>
<c:remove var="success" scope="request"></c:remove>

<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">ArticleDetails</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        </div>

        <div align="right">
            <a class="nav-link" href="javascript:window.history.back(-1)">Back</a>
        </div>
        <c:if test="${not empty user}" >
        <div align="right">
            <a class="nav-link" href="/logout">Sign Out</a>
        </div>
        </c:if>
        <c:if test="${empty user}" >
            <div align="right">
                <a class="nav-link" href="login.jsp">Login</a>
            </div>
        </c:if>

    </nav>
</header>

<!-- Begin page content -->
<main role="main" class="container">
    <!--
     <p class="lead">Pin a fixed-height footer to the bottom of the viewport in desktop browsers with this custom HTML and CSS. A fixed navbar has been added with <code>padding-top: 60px;</code> on the <code>body &gt; .container</code>.</p>
     <p>Back to <a href="../sticky-footer">the default sticky footer</a> minus the navbar.</p>-->

    <h2 class="mt-5" align="center">${detail.title}</h2>
    <br>
    <hr>
    <textarea  class="form-control" rows="10" style="background-color:white; resize:none;border: none" disabled="disabled" >${detail.content}</textarea>

    <div align="left">
    <div align="right">作者：${detail.authorname}</div>
    <div align="right">时间：${detail.publishtime}</div>
    </div>


    <hr />
    <br>
    <c:if test="${not empty articleMessage}">
        <h5>留言板</h5>
    </c:if>
    <br>

    <c:forEach items="${articleMessage}" var="f">
        <div>

            <div align="center" style="display: inline-block; width: 140px;height: 120px;">
               <img src="${f.imgpath}" width="72" height="72"><br>
                <p>用户名：${f.messageperson}</p>
            </div>

            <div style="display: inline-block;border-right:1px solid gainsboro;width: 1px;height: 90px;">
            </div>

            <div style="display:inline-block;">
                <div  style="display:block;width: 780px;height: 72px">${f.message}</div>

                <P align="right">发表时间：${f.mdate}</P>
            </div>

        </div>
        <hr />
    </c:forEach>


    <p></p>



<c:if test="${not empty user}">
    <h5>我要留言:</h5>
    <form method="post" action="/uploadMessage">
        <textarea  class="form-control" rows="5" style="resize:none" name="message"></textarea>
        <br />
        <div align="center">
            <button type="reset" class="btn btn-sm btn-info">清空留言</button>&nbsp&nbsp&nbsp&nbsp
            <button type="submit" class="btn btn-sm btn-primary">发布留言</button>
        </div>
        <br />
        <input name="articleID" value="${detail.id}" type="hidden" />
    </form>
</c:if>

</main>

<c:if test="${not empty detail}">
    <c:remove var="detail" scope="session"></c:remove>
    <c:remove var="articleMessage" scope="session"></c:remove>
</c:if>






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
