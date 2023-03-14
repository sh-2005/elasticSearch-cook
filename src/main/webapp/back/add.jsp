<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台列表页面</title>
    <link rel="stylesheet" href="../static/css/bootstrap.min.css"/>
    <script src="../static/js/jquery-3.5.1.min.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/cook/insert" method="post" enctype="multipart/form-data">
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
                <div class="form-group">
                    <label for="exampleInputEmail1">菜谱名称:</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="name">
                </div>
                <div class="form-group">
                    <label >菜谱图片:</label>
                    <input type="file" class="form-control" name="aaa">
                </div>

                <div class="form-group">
                    <label >菜谱关于:</label>
                    <input type="text" class="form-control" name="zhaiyao">
                </div>

                <div class="form-group">
                    <label >烹饪步骤:</label>
                    <input type="text" class="form-control" name="buzhou">
                </div>
                <button type="submit" class="btn btn-info btn-block">录入菜谱</button>
                <a href="${pageContext.request.contextPath}/cook/findAll" type="button" class="btn btn-danger btn-block">返回列表</a>
        </div>
    </div>
</div>
</form>
</body>
</html>