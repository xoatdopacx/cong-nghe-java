<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Bài 10: Header với menu hiển thị theo role --%>
<c:set var="user" value="${sessionScope.currentUser}" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 10 - Secured App | EAUT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="app-wrapper">
    <!-- Sidebar -->
    <aside class="sidebar">
        <div class="sidebar-brand">
            <h2>🔐 Lab 10</h2>
            <small>Nguyễn Văn Hùng - 20230752</small><br>
            <small>Lớp: DCCNTT 14.2</small>
        </div>
        <ul class="nav-menu">
            <%-- Dashboard - tất cả role --%>
            <li><a href="${pageContext.request.contextPath}/dashboard" class="${param.active == 'dashboard' ? 'active' : ''}">
                <span class="icon">📊</span> Dashboard
            </a></li>

            <%-- Menu ADMIN: Quản trị hệ thống --%>
            <c:if test="${user.role == 'ADMIN'}">
                <li class="nav-section">Quản trị</li>
                <li><a href="${pageContext.request.contextPath}/admin/sinh-vien" class="${param.active == 'sinhvien' ? 'active' : ''}">
                    <span class="icon">👨‍🎓</span> Sinh Viên
                </a></li>
                <li><a href="${pageContext.request.contextPath}/admin/lop-hoc" class="${param.active == 'lophoc' ? 'active' : ''}">
                    <span class="icon">🏫</span> Lớp Học
                </a></li>
                <li><a href="${pageContext.request.contextPath}/admin/mon-hoc" class="${param.active == 'monhoc' ? 'active' : ''}">
                    <span class="icon">📚</span> Môn Học
                </a></li>
                <li><a href="${pageContext.request.contextPath}/admin/users" class="${param.active == 'users' ? 'active' : ''}">
                    <span class="icon">👥</span> Quản lý TK
                </a></li>
            </c:if>

            <%-- Menu ADMIN + STAFF: Nghiệp vụ --%>
            <c:if test="${user.role == 'ADMIN' || user.role == 'STAFF'}">
                <li class="nav-section">Nghiệp vụ</li>
                <li><a href="${pageContext.request.contextPath}/staff/diem" class="${param.active == 'diem' ? 'active' : ''}">
                    <span class="icon">📝</span> Điểm
                </a></li>
                <li><a href="${pageContext.request.contextPath}/staff/san-pham" class="${param.active == 'sanpham' ? 'active' : ''}">
                    <span class="icon">📦</span> Sản Phẩm
                </a></li>
            </c:if>

            <%-- Menu USER: Cá nhân --%>
            <li class="nav-section">Cá nhân</li>
            <li><a href="${pageContext.request.contextPath}/user/profile" class="${param.active == 'profile' ? 'active' : ''}">
                <span class="icon">👤</span> Hồ sơ
            </a></li>
        </ul>

        <%-- User info + Logout --%>
        <div class="sidebar-user">
            <div class="user-info">
                <div class="user-avatar">${user.fullName.substring(0,1)}</div>
                <div class="user-details">
                    <div class="user-name">${user.fullName}</div>
                    <div class="user-role">
                        <span class="badge badge-${user.role == 'ADMIN' ? 'danger' : (user.role == 'STAFF' ? 'warning' : 'info')}">
                            ${user.role}
                        </span>
                    </div>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="logout-btn">
                🚪 Đăng xuất
            </a>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
