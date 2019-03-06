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


    <div class="col-md-11 order-md-2" style="padding-left: 200px" >
        <form class="nneeds-validation" method="post" action="/register">

           
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="username" name="username" placeholder="username" required>

                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">密码</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password" name="password" placeholder="password" required>
                </div>
            </div>

            <div class="form-group">
                <label for="confirm" class="col-sm-2 control-label">确认密码</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="confirm" name="confirm" placeholder="confirm password" required>
                </div>
            </div>

            <div class="form-group">
                <label for="identitycode" class="col-sm-2 control-label">验证码</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" style="width: 300px; vertical-align: middle;display: inline"  id="identitycode" name="identitycode" placeholder="Input IdentityCode" required>&nbsp;&nbsp;&nbsp;
                    <img src="/identityCode" class="img-fluid" style="vertical-align: middle;display: inline" >
                </div>
            </div>

            <br>

            <div class="form-group" style="padding-left: 15px">
            <input type="submit" class="btn btn-success btn-sm" value="注册用户" style="width: 220px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="btn btn-primary btn-sm" value="返回登陆" onclick="window.location.href='login.jsp'" style="width: 220px">
            </div>
        </form>

    </div>
</div>

</body>
</html>
