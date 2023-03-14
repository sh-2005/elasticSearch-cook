<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; ISO-8859-1" %>
<html lang="en">

<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../static/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
    <script>
       $(function () {
           $("#aaa").submit(function () {
               $.get("${pageContext.request.contextPath}/user/login",$("#aaa").serialize(),function (res) {
                   if (res.success){
                       alert(res.msg+"点击跳转到主页");
                       location.href="${pageContext.request.contextPath}/cook/findAll";
                   }else {
                       alert(res.msg);
                   }
               },"JSON");
               return false;
           })
       })
    </script>
</head>

<body>
<div id="wrap" class="container-fluid">
    <div id="top_content" class="row"  style="margin: 0 auto;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="header">
                <div id="topheader">
                    <h1 class="text-center text-info">欢迎进入菜谱管理系统v1.0</h1>
                </div>
                <div id="navigation">
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="content">
                <form method="post" action="" id="aaa">
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text"  v-model="username" id="username" class="form-control" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="password"  v-model="password"  class="form-control" name="password"/></div>
                    <br>
                    <input type="submit" style="width: 98%"   class="btn btn-danger" value="登录&raquo;"/>
                </form>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 40px;">
        <div class="col-sm-8 col-sm-offset-2">
            <h5 class="text-center">cookie book @136.com</h5>
        </div>
    </div>
</div>
</body>
</html>
