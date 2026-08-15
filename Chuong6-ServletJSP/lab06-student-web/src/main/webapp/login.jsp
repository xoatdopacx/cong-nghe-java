<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; }
        .login-card { background: #fff; border-radius: 12px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); padding: 40px; width: 400px; }
        .login-card h2 { text-align: center; color: #2c3e50; margin-bottom: 8px; font-size: 24px; }
        .login-card .subtitle { text-align: center; color: #95a5a6; margin-bottom: 30px; font-size: 13px; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 6px; color: #34495e; font-weight: 600; font-size: 14px; }
        .form-group input { width: 100%; padding: 12px 16px; border: 2px solid #ecf0f1; border-radius: 8px; font-size: 14px; transition: border-color 0.3s; }
        .form-group input:focus { outline: none; border-color: #667eea; }
        .btn-login { width: 100%; padding: 14px; background: linear-gradient(135deg, #667eea, #764ba2); color: #fff; border: none; border-radius: 8px; font-size: 16px; font-weight: 600; cursor: pointer; transition: opacity 0.3s; }
        .btn-login:hover { opacity: 0.9; }
        .error-msg { color: #e74c3c; text-align: center; margin-top: 15px; font-size: 14px; font-weight: 500; }
        .info { text-align: center; margin-top: 20px; color: #95a5a6; font-size: 12px; line-height: 1.8; }
        .info strong { color: #2c3e50; }
    </style>
</head>
<body>
<div class="login-card">
    <h2>🔐 Đăng nhập hệ thống</h2>
    <p class="subtitle">Lab 6 - Công nghệ Java (IT3242)</p>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username" placeholder="admin hoặc user" required>
        </div>
        <div class="form-group">
            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" placeholder="123456" required>
        </div>
        <button type="submit" class="btn-login">Đăng nhập</button>
    </form>

    <p class="error-msg">${error}</p>

    <div class="info">
        <strong>Admin:</strong> admin / 123456 (Toàn quyền CRUD)<br>
        <strong>User:</strong> user / 123456 (Chỉ xem danh sách)
    </div>
</div>
</body>
</html>
