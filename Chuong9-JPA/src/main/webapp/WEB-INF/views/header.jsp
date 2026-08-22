<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 09 - JPA Repository | EAUT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="app-wrapper">
    <!-- Sidebar -->
    <aside class="sidebar">
        <div class="sidebar-brand">
            <h2>🎓 Lab 09 - JPA</h2>
            <small>Nguyễn Văn Hùng - 20230752</small><br>
            <small>Lớp: DCCNTT 14.2</small>
        </div>
        <ul class="nav-menu">
            <li><a href="${pageContext.request.contextPath}/" class="${param.active == 'dashboard' || empty param.active ? 'active' : ''}">
                <span class="icon">📊</span> Dashboard
            </a></li>
            <li><a href="${pageContext.request.contextPath}/sinh-vien" class="${param.active == 'sinhvien' ? 'active' : ''}">
                <span class="icon">👨‍🎓</span> Sinh Viên
            </a></li>
            <li><a href="${pageContext.request.contextPath}/lop-hoc" class="${param.active == 'lophoc' ? 'active' : ''}">
                <span class="icon">🏫</span> Lớp Học
            </a></li>
            <li><a href="${pageContext.request.contextPath}/mon-hoc" class="${param.active == 'monhoc' ? 'active' : ''}">
                <span class="icon">📚</span> Môn Học
            </a></li>
            <li><a href="${pageContext.request.contextPath}/diem" class="${param.active == 'diem' ? 'active' : ''}">
                <span class="icon">📝</span> Điểm
            </a></li>
            <li><a href="${pageContext.request.contextPath}/san-pham" class="${param.active == 'sanpham' ? 'active' : ''}">
                <span class="icon">📦</span> Sản Phẩm
            </a></li>
        </ul>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
