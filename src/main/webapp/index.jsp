<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/2/28
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CommunityMainPage</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css" />
    <link href="/css/adminAlter.css" rel="stylesheet" type="text/css" />
    <link href="/css/form-validation.css" rel="stylesheet" type="text/css" />
    <link href="/css/carousel.css" rel="stylesheet" type="text/css" />
</head>



<body >
<script>
    var successInfo="${success}";
    if(successInfo.trim().length!=0){
        alert("${success}");
    }
    var errorInfo="${error}";
    if(errorInfo.trim().length!=0){
        alert("${error}");
    }
</script>

<c:if test="${empty list}">
    <c:redirect url="/list"></c:redirect>
</c:if>

<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">CommunityArticle</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        </div>

        <c:if test="${not empty user}">
        <div align="right">
            <a class="nav-link" href="PublishArticle.jsp">PublishArticle</a>
        </div>
        <div align="right">
            <a class="nav-link" href="ManageMyArticle.jsp">MyArticle</a>
        </div>
        <div align="right">
            <a class="nav-link" href="AlterPassword.jsp">PersonalCentre</a>
        </div>
        <div align="right">
            <a class="nav-link" href="/logout">Sign Out</a>
        </c:if>

        <c:if test="${empty user}">
        <div align="right">
            <a class="nav-link" href="registration.jsp">Register</a>
        </div>
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
    <div align="right">

        <form class="form-inline mt-2 mt-md-0" method="post" action="/search">
            <h5 style="color: lightblue">Search By Title：</h5>
            <input class="form-control mr-sm-2" type="text" name="searchtitle" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
    <h2 class="mt-5" align="center">All Articles</h2>
    <br>
    <hr>
    <table class="table table-striped table-sm">
        <thead>
        <tr>
            <th>标题</th>
            <th>作者</th>
            <th>发布时间</th>
            <th>查看详情</th>
        </tr>
        </thead>
        <c:forEach items="${list}" var="f">
            <tbody>
            <tr>
                <td>${f.title}</td>
                <td>${f.authorname}</td>
                <td>${f.publishtime}</td>
                <td><form method="post" action="/detail"><input type="hidden" name="titleID" value="${f.id}"><button class="btn btn-sm btn-info" type="submit">详情</button> </form></td>
            </tr>
            </tbody>
        </c:forEach>
        <c:remove var="f" scope="session"></c:remove>
    </table>
    <hr />

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
