<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 - Không có quyền truy cập</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .error-card { text-align: center; background: #fff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 50px; max-width: 500px; }
        .error-card .icon { font-size: 72px; margin-bottom: 15px; }
        .error-card h1 { color: #e74c3c; font-size: 48px; margin-bottom: 10px; }
        .error-card h2 { color: #2c3e50; margin-bottom: 15px; }
        .error-card p { color: #7f8c8d; margin-bottom: 25px; line-height: 1.6; }
        .error-card a { display: inline-block; padding: 12px 24px; background: #667eea; color: #fff; text-decoration: none; border-radius: 6px; font-weight: 600; }
        .error-card a:hover { background: #5a6fd6; }
    </style>
</head>
<body>
<div class="error-card">
    <div class="icon">🚫</div>
    <h1>403</h1>
    <h2>Không có quyền truy cập</h2>
    <p>Tài khoản của bạn không có quyền thực hiện thao tác này.<br>
       Chỉ tài khoản <strong>Admin</strong> mới được phép thêm, sửa, xóa sinh viên.</p>
    <a href="${pageContext.request.contextPath}/students">← Quay lại danh sách</a>
</div>
</body>
</html>
