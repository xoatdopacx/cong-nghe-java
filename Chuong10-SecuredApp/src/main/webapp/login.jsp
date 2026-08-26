<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Lab 10 Secured App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="login-wrapper">
    <div class="login-card">
        <div class="login-header">
            <h1>🔐 Đăng nhập</h1>
            <p>Lab 10 - Hệ thống quản lý đa vai trò</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">❌ ${error}</div>
        </c:if>

        <form class="login-form" method="post" action="${pageContext.request.contextPath}/auth">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control"
                       placeholder="Nhập email đăng nhập" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" class="form-control"
                       placeholder="Nhập mật khẩu" required>
            </div>
            <button type="submit" class="btn btn-primary">🚀 Đăng nhập</button>
        </form>

        <div class="login-footer">
            <p>
                <strong>Tài khoản test:</strong><br>
                ADMIN: admin@eaut.edu.vn / admin123<br>
                STAFF: staff@eaut.edu.vn / staff123<br>
                USER: user@eaut.edu.vn / user123
            </p>
        </div>
    </div>
</div>
</body>
</html>
