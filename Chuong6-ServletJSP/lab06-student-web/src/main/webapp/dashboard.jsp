<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; padding: 30px; }
        .container { max-width: 800px; margin: 0 auto; }
        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #fff; border-radius: 12px; padding: 30px; margin-bottom: 25px; }
        .header h2 { font-size: 22px; margin-bottom: 8px; }
        .header p { opacity: 0.9; font-size: 14px; }
        .cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 25px; }
        .card { background: #fff; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); padding: 24px; text-align: center; }
        .card .number { font-size: 36px; font-weight: 700; color: #667eea; }
        .card .label { color: #7f8c8d; font-size: 14px; margin-top: 6px; }
        .card.green .number { color: #27ae60; }
        .card.orange .number { color: #f39c12; }
        .section { background: #fff; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); padding: 24px; margin-bottom: 25px; }
        .section h3 { color: #2c3e50; margin-bottom: 15px; font-size: 16px; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #667eea; color: #fff; padding: 10px; text-align: left; font-size: 13px; }
        td { padding: 10px; border-bottom: 1px solid #ecf0f1; font-size: 14px; }
        .links { display: flex; gap: 12px; flex-wrap: wrap; }
        .links a { display: inline-block; padding: 10px 20px; background: #667eea; color: #fff; text-decoration: none; border-radius: 6px; font-weight: 500; font-size: 14px; }
        .links a:hover { background: #5a6fd6; }
        .links a.logout { background: #e74c3c; }
        .links a.logout:hover { background: #c0392b; }
        .badge { display: inline-block; padding: 3px 10px; border-radius: 10px; font-size: 12px; font-weight: 600; color: #fff; }
        .badge-admin { background: #e74c3c; }
        .badge-user { background: #3498db; }
    </style>
</head>
<body>
<div class="container">

    <%-- Header --%>
    <div class="header">
        <h2>📊 Dashboard - Hệ thống Quản lý Sinh viên</h2>
        <p>Xin chào, <strong>${sessionScope.username}</strong>
            <span class="badge ${sessionScope.role == 'admin' ? 'badge-admin' : 'badge-user'}">${sessionScope.role}</span>
            &nbsp;|&nbsp; Đăng nhập lúc: ${sessionScope.loginTime}
        </p>
    </div>

    <%-- Stats Cards --%>
    <div class="cards">
        <div class="card">
            <div class="number">${totalStudents}</div>
            <div class="label">Tổng số sinh viên</div>
        </div>
        <div class="card green">
            <div class="number">${classStats.size()}</div>
            <div class="label">Số lớp học</div>
        </div>
        <div class="card orange">
            <div class="number">
                <c:choose>
                    <c:when test="${sessionScope.role == 'admin'}">Admin</c:when>
                    <c:otherwise>User</c:otherwise>
                </c:choose>
            </div>
            <div class="label">Vai trò hiện tại</div>
        </div>
    </div>

    <%-- Thống kê theo lớp --%>
    <div class="section">
        <h3>📈 Thống kê sinh viên theo lớp</h3>
        <table>
            <tr>
                <th>Lớp</th>
                <th>Số lượng sinh viên</th>
            </tr>
            <c:forEach var="entry" items="${classStats}">
                <tr>
                    <td><strong>${entry.key}</strong></td>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <%-- Liên kết nhanh --%>
    <div class="section">
        <h3>🔗 Liên kết nhanh</h3>
        <div class="links">
            <a href="${pageContext.request.contextPath}/students">📋 Quản lý sinh viên</a>
            <c:if test="${sessionScope.role == 'admin'}">
                <a href="${pageContext.request.contextPath}/student-form.jsp">➕ Thêm sinh viên</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/hello">🖐 Hello Servlet</a>
            <a href="${pageContext.request.contextPath}/welcome.jsp">🏠 Trang chủ</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">🚪 Đăng xuất</a>
        </div>
    </div>

</div>
</body>
</html>
