<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/2/27
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <title>ManageMyArticle</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css" />
    <link href="/css/adminManagePage.css" rel="stylesheet" type="text/css" />

</head>



<body>

<c:if test="${empty myarticle}">
    <c:redirect url="/listMyArticle"></c:redirect>
</c:if>

<script>
    var errorInfo="${error}";
    if(errorInfo.trim().length!=0){
        alert("${error}");
    }
    var successInfo="${success}";
    if(successInfo.trim().length!=0){
        alert("${success}");
    }
</script>

<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Menu</a>
    <p class=" navbar-link col-sm-3 " style="font-size:20px; color:#FFF">CommunitySystem-ManageMyArticle</p>

    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="javascript:window.history.back(-1)">Back</a>
        </li>
    </ul>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="/logout">Sign out</a>
        </li>
    </ul>

</nav>
<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="AlterPassword.jsp">
                            <span data-feather="home"></span>
                            AlterPassword
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="ManageMyArticle.jsp">
                            <span data-feather="file"></span>
                            ManageMyArticle<span class="sr-only">(current)</span>
                        </a>
                    </li>
                </ul>

            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <div class="table-responsive">
                    <form method="post" action="/delete">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>选择</th>
                                <th>标题</th>
                                <th>作者</th>
                                <th>发布时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <c:forEach items="${myarticle}" var="t">
                                <tbody>
                                <tr>
                                    <td><input type="checkbox"  name="deleteID" id="deleteID"   value="${t.id}"/></td>
                                    <td>${t.title}</td>
                                    <td>${t.authorname}</td>
                                    <td>${t.publishtime}</td>
                                    <td><a  class="btn btn-sm btn-info" href="/detail?titleID=${t.id}">详情</a>&nbsp;
                                        <a  class="btn btn-sm btn-success" href="/modifyPage?titleID=${t.id}">修改</a></td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                        <p><button class="btn btn-sm btn-danger" type="submit">删除</button></p>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
<c:if test="${not empty myarticle}">
    <c:remove var="myarticle" scope="session"></c:remove>
</c:if>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

<!-- Icons -->
<script src="/js/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>