<%@ page contentType="text/html; ISO-8859-1" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台修改页面</title>
    <link rel="stylesheet" href="../static/css/bootstrap.min.css"/>
    <script src="../static/js/jquery-3.5.1.min.js"></script>
</head>
<body>



<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <form action="${pageContext.request.contextPath}/cook/update" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label">ID</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">
                            ${cook.id}
                            <input type="hidden" name="id" value="${cook.id}">
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label >菜谱名称:</label>
                    <input type="text" value="${requestScope.cook.name}" class="form-control" name="name">
                </div>
                <div class="form-group">
                    <label >菜谱图片:</label>
                    <input type="file" class="form-control" name="bbb" value="${cook.img}">
                    <img src="${pageContext.request.contextPath}/files/${cook.img}" width="300px" height="200px"/>
                </div>

                <div class="form-group">
                    <label >菜谱关于:</label>
                    <input type="text" value="${requestScope.cook.zhaiyao}" class="form-control" name="zhaiyao">
                </div>

                <div class="form-group">
                    <label >烹饪步骤:</label>
                    <input type="text" value="${requestScope.cook.buzhou}" class="form-control" name="buzhou">
                </div>
                <button type="submit" class="btn btn-info btn-block">录入菜谱</button>
                <a href="${pageContext.request.contextPath}/cook/findAll" type="button" class="btn btn-danger btn-block">返回列表</a>
            </form>
        </div>
    </div>

</div>

</body>
</html>