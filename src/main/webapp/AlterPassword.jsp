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

    <title>AlterPassword</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css" />
    <link href="/css/adminManagePage.css" rel="stylesheet" type="text/css" />

</head>



<body>
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Menu</a>
    <p class=" navbar-link col-sm-3 " style="font-size:20px; color:#FFF">CommunitySystem-AlterPassword</p>

    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="index.jsp">Back</a>
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
                        <a class="nav-link active" href="AlterPassword.jsp">
                            <span data-feather="home"></span>
                            AlterPassword<span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="ManageMyArticle.jsp">
                            <span data-feather="file"></span>
                            ManageMyArticle
                        </a>
                    </li>
                </ul>

            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <div class="table-responsive">
                    <form method="post" action="#">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>选择</th>
                                <th>用户名</th>
                                <th>密码</th>
                                <th>公司</th>
                                <th>电话</th>
                                <th>注册类型</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr>
                                <td><input type="checkbox" name="checkbox[]" id="checkbox"   value=""/></td>
                                <td><?=$row['username'] ?></td>
                                <td><?=$row['password'] ?></td>
                                <td><?=$row['company'] ?></td>
                                <td><?=$row['tel'] ?></td>
                                <td><?=$row['usertype'] ?></td>
                                <td><a href="adminAlter.php?username=">修改</a></td>
                            </tr>


                            </tbody>
                        </table>
                        <p><button class="btn btn-sm btn-info" type="submit">删除</button></p>
                    </form>
                </div>
            </div>





        </main>
    </div>
</div>

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