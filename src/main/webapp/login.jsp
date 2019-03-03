<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/2/26
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>LoginPage</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/signin.css" rel="stylesheet" type="text/css">

</head>

<body class="text-center">

<script>
    var errorInfo="${error}";
    if(errorInfo.trim().length!=0){
        //alert(a.trim());
        //alert(a.size);
        //alert(a.trim().length);
        alert("${error}");
    }
    var successInfo="${success}";
    if(successInfo.trim().length!=0){
        alert("${success}");
    }
</script>


<form class="form-signin" method="post" action="/login">
    <img class="mb-4" src="img/sign.jpg" alt="" width="72" height="72">
    <h2>Community System</h2>
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <div>
        <p align="left">用户名</p>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
    </div>
    <br />
    <p align="left">密码</p>
    <div>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    </div>

    <br />
    <button class="btn btn-lg btn-info" style="width:145px" type="button" onclick="window.location.href='registration.jsp'">注册</button>
    <button class="btn btn-lg btn-primary" style="width:145px" type="submit">登录</button>
    <p class="mt-5 mb-3 text-muted">Designed By ZeyuGuo</p>
</form>
<br>

</body>
</html>