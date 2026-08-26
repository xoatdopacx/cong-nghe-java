<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>500 - Lỗi hệ thống</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="error-wrapper">
    <div class="error-card">
        <div class="error-code">500</div>
        <div class="error-title">⚠️ Lỗi hệ thống</div>
        <p class="error-message">Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau hoặc liên hệ quản trị viên.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">← Về Dashboard</a>
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary" style="margin-left: 0.5rem;">🔐 Đăng nhập</a>
    </div>
</div>
</body>
</html>
