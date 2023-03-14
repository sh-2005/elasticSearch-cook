<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; ISO-8859-1" isELIgnored="false" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台列表页面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#create').click(function () {
                $.post("${pageContext.request.contextPath}/cook/createIndexAll",function (res) {
                    if (res.success){
                        alert(res.status);
                    }else {
                        alert(res.status);
                    }
                },"JSON")
                return false;
            })
            $("#clear").click(function () {
                $.post("${pageContext.request.contextPath}/cook/clearIndexAll",function (res) {
                    if (res.success){
                        alert(res.status);
                    } else {
                        alert(res.status);
                    }
                },"JSON")
                return false;
            })
        })
    </script>
</head>
<body>

<!--功能按钮-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">后台食谱管理系统</a>
        </div>

        <!--功能按钮 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left">
                <button type="button" class="btn btn-danger" id="clear">清空ES索引库</button>
                <button type="button" class="btn btn-info" id="create">基于mysql数据重建索引</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="text-info">${sessionScope.user.username}</span></a></li>
                <li><a href="${pageContext.request.contextPath}/user/exit">退出系统</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <a href="${pageContext.request.contextPath}/back/add.jsp" class="btn btn-info">添加</a>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>菜谱名称</th>
                    <th>图片</th>
                    <th>录入时间</th>
                    <th>录入人</th>
                    <th>关于摘要</th>
                    <th>步骤摘要</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cook" items="${requestScope.cook}">
                <tr>
                    <th scope="row">1</th>
                    <td>${cook.name}</td>
                    <td><img style="width: 200px;height: 120px;" src="${pageContext.request.contextPath}/files/${cook.img}" class="img-thumbnail" alt=""></td>
                    <td>${cook.time}</td>
                    <td>${cook.people}</td>
                    <td>${cook.zhaiyao}</td>
                    <td>${cook.buzhou}</td>
                    <td><a href="${pageContext.request.contextPath}/cook/delete?id=${cook.id}" class="btn btn-danger">删除</a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/cook/selectOne?id=${cook.id}" class="btn btn-info">修改</a></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!--热词处理-->

</div>


</body>
</html>