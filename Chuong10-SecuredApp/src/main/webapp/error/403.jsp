<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 - Không có quyền truy cập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="error-wrapper">
    <div class="error-card">
        <div class="error-code">403</div>
        <div class="error-title">🚫 Không có quyền truy cập</div>
        <p class="error-message">Bạn không đủ quyền để truy cập trang này. Vui lòng liên hệ quản trị viên nếu bạn cho rằng đây là lỗi.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">← Về Dashboard</a>
        <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-secondary" style="margin-left: 0.5rem;">🚪 Đăng xuất</a>
    </div>
</div>
</body>
</html>
