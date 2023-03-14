<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>前台首页</title>
    <link rel="stylesheet" href="../static/css/bootstrap.min.css"/>
    <script src="../static/js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <!--搜索框-->
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12" style="text-align: center">
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/cook/selectIndex">
                <div class="form-group" style="width: 600px;">
                    <input type="text" name="name"  style="width: 600px;" class="form-control" placeholder="请输入查询条件">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>

    <h1 class="page-header">搜索结果</h1>
    <!--搜索列表-->
    <div class="row" style="margin-top: 20px">
        <c:forEach items="${requestScope.cook}" var="cook">
        <div class="col-sm-3">
            <div class="thumbnail">
                <img src="${pageContext.request.contextPath}/files/${cook.img}" class="img-circle" style="width: 200px;height: 120px;">
                <div class="caption">
                    <h3 class="text-center">${cook.name}</h3>
                    <p>${cook.zhaiyao}</p>
                    <p><a href="detail.html" class="btn btn-danger btn-block" role="button">查看详细</a></p>
                </div>
            </div>
        </div>
        </c:forEach>

        </div>
    <!--分页工具栏-->
    <nav aria-label="Page navigation">
        <ul class="pagination pull-right">
            <li>
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="active"><a href="#" >1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
                <a href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>


</div>
</body>
</html>