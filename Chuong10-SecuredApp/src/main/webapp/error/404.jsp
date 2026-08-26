<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>404 - Không tìm thấy trang</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="error-wrapper">
    <div class="error-card">
        <div class="error-code">404</div>
        <div class="error-title">🔍 Không tìm thấy trang</div>
        <p class="error-message">Trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">← Về Dashboard</a>
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary" style="margin-left: 0.5rem;">🔐 Đăng nhập</a>
    </div>
</div>
</body>
</html>
