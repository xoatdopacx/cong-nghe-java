<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chào mừng - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; padding: 40px; }
        .container { max-width: 600px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 40px; }
        h2 { color: #2c3e50; margin-bottom: 20px; }
        ul { list-style: none; padding: 0; }
        ul li { margin-bottom: 12px; }
        ul li a { display: inline-block; padding: 10px 20px; background: #667eea; color: #fff; text-decoration: none; border-radius: 6px; font-weight: 500; transition: background 0.3s; }
        ul li a:hover { background: #5a6fd6; }
        ul li a.logout { background: #e74c3c; }
        ul li a.logout:hover { background: #c0392b; }
        .role-badge { display: inline-block; padding: 4px 12px; border-radius: 12px; font-size: 12px; font-weight: 600; color: #fff; margin-left: 10px; }
        .role-admin { background: #e74c3c; }
        .role-user { background: #3498db; }
    </style>
</head>
<body>
<div class="container">
    <h2>👋 Xin chào, <strong>${sessionScope.username}</strong>
        <span class="role-badge ${sessionScope.role == 'admin' ? 'role-admin' : 'role-user'}">
            ${sessionScope.role}
        </span>
    </h2>
    <p style="color:#7f8c8d; margin-bottom:30px;">Bạn đã đăng nhập thành công vào hệ thống Lab 6.</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/dashboard">📊 Trang Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/students">📋 Quản lý sinh viên</a></li>
        <li><a href="${pageContext.request.contextPath}/hello">🖐 Hello Servlet</a></li>
        <li><a href="${pageContext.request.contextPath}/logout" class="logout">🚪 Đăng xuất</a></li>
    </ul>
</div>
</body>
</html>
