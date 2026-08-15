<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách sinh viên - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; padding: 30px; }
        .container { max-width: 960px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 30px; }
        h2 { color: #2c3e50; margin-bottom: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { color: #667eea; text-decoration: none; font-weight: 500; margin-right: 15px; }
        .toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; flex-wrap: wrap; gap: 10px; }
        .search-form { display: flex; gap: 8px; }
        .search-form input { padding: 8px 14px; border: 2px solid #ecf0f1; border-radius: 6px; font-size: 14px; width: 220px; }
        .search-form input:focus { outline: none; border-color: #667eea; }
        .btn { padding: 8px 18px; border: none; border-radius: 6px; font-size: 13px; font-weight: 600; cursor: pointer; text-decoration: none; display: inline-block; color: #fff; }
        .btn-add { background: #27ae60; }
        .btn-add:hover { background: #219a52; }
        .btn-search { background: #3498db; }
        .btn-search:hover { background: #2980b9; }
        .btn-edit { background: #f39c12; padding: 5px 12px; font-size: 12px; }
        .btn-edit:hover { background: #e67e22; }
        .btn-delete { background: #e74c3c; padding: 5px 12px; font-size: 12px; }
        .btn-delete:hover { background: #c0392b; }
        .btn-clear { background: #95a5a6; }
        .btn-clear:hover { background: #7f8c8d; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th { background: #667eea; color: #fff; padding: 12px 14px; text-align: left; font-size: 14px; }
        td { padding: 10px 14px; border-bottom: 1px solid #ecf0f1; font-size: 14px; color: #2c3e50; }
        tr:hover { background: #f8f9fa; }
        tr:nth-child(even) { background: #fafbfc; }
        .no-data { text-align: center; padding: 30px; color: #95a5a6; font-style: italic; }
        .search-msg { color: #e67e22; margin-bottom: 10px; font-weight: 500; }
        .badge { display: inline-block; padding: 3px 8px; border-radius: 10px; font-size: 11px; font-weight: 600; color: #fff; }
        .badge-admin { background: #e74c3c; }
        .badge-user { background: #3498db; }
        .user-info { color: #7f8c8d; font-size: 13px; margin-bottom: 15px; }
    </style>
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="${pageContext.request.contextPath}/dashboard">← Dashboard</a>
        <a href="${pageContext.request.contextPath}/welcome.jsp">Trang chủ</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </div>

    <h2>📋 Danh sách sinh viên</h2>
    <p class="user-info">
        Đăng nhập: <strong>${sessionScope.username}</strong>
        <span class="badge ${sessionScope.role == 'admin' ? 'badge-admin' : 'badge-user'}">${sessionScope.role}</span>
    </p>

    <div class="toolbar">
        <%-- Bài 6: Form tìm kiếm sinh viên --%>
        <form class="search-form" action="${pageContext.request.contextPath}/students" method="get">
            <input type="text" name="search" placeholder="Tìm theo họ tên..." value="${searchKeyword}">
            <button type="submit" class="btn btn-search">🔍 Tìm</button>
            <c:if test="${not empty searchKeyword}">
                <a href="${pageContext.request.contextPath}/students" class="btn btn-clear">✕ Xóa lọc</a>
            </c:if>
        </form>

        <%-- Bài 9: Chỉ Admin mới thấy nút Thêm --%>
        <c:if test="${sessionScope.role == 'admin'}">
            <a href="${pageContext.request.contextPath}/student-form.jsp" class="btn btn-add">➕ Thêm sinh viên</a>
        </c:if>
    </div>

    <%-- Bài 6: Thông báo kết quả tìm kiếm --%>
    <c:if test="${not empty searchMessage}">
        <p class="search-msg">⚠️ ${searchMessage}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty students}">
            <table>
                <tr>
                    <th>STT</th>
                    <th>Mã SV</th>
                    <th>Họ tên</th>
                    <th>Lớp</th>
                    <th>Email</th>
                    <%-- Bài 9: Chỉ Admin mới thấy cột Thao tác --%>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <th>Thao tác</th>
                    </c:if>
                </tr>
                <c:forEach var="sv" items="${students}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><strong>${sv.id}</strong></td>
                        <td>${sv.name}</td>
                        <td>${sv.className}</td>
                        <td>${sv.email}</td>
                        <c:if test="${sessionScope.role == 'admin'}">
                            <td>
                                <%-- Bài 8: Nút Sửa --%>
                                <a href="${pageContext.request.contextPath}/students?action=edit&id=${sv.id}"
                                   class="btn btn-edit">✏️ Sửa</a>
                                <%-- Bài 7: Nút Xóa --%>
                                <a href="${pageContext.request.contextPath}/students?action=delete&id=${sv.id}"
                                   class="btn btn-delete"
                                   onclick="return confirm('Bạn có chắc muốn xóa sinh viên ${sv.name}?')">🗑 Xóa</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">Chưa có sinh viên nào trong danh sách.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
