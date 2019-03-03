<%--
  Created by IntelliJ IDEA.
  User: tony
  Date: 2019/2/26
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RegistrationPage</title>
    <link href="/css/bootstrap-old.css" rel="stylesheet" type="text/css">
    <link href="/css/form-validation.css" rel="stylesheet" type="text/css">

</head>


<body class="bg-light">

<script>
    var errorInfo="${error}";
    if(errorInfo.trim().length!=0){
        alert("${error}");
    }
</script>

<div class="container">
    <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="/img/sign.jpg" alt="" width="72" height="72">
        <h2>注册账户</h2>
        <p class="lead">注册成为用户，以登陆使用本论坛。</p>
    </div>


    <div class="col-md-8 order-md-1" >
        <form class="nneeds-validation" method="post" action="/register">



                <div class="mb-3">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="username" required>
                    <br />
                </div>

                <div class="mb-3">
                    <label for="password">密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="password" required>
                    <br />
                </div>

                <div class="mb-3">
                    <label for="confirm">确认密码</label>
                    <input type="password" class="form-control" id="confirm" name="confirm" placeholder="confirm password" required>
                    <br />
                </div>

            <input type="submit" class="btn btn-primary btn-sm" value="注册" style="width: 280px">&nbsp;&nbsp;&nbsp;
            <input type="button" class="btn btn-primary btn-sm" value="返回" onclick="window.location.href='login.jsp'" style="width: 280px">

        </form>

    </div>
</div>

</body>
</html>
