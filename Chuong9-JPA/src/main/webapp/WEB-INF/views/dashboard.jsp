<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp"><jsp:param name="active" value="dashboard"/></jsp:include>

<div class="page-header">
    <h1>📊 Dashboard - Tổng quan hệ thống</h1>
</div>

<div class="stats-grid">
    <div class="stat-card">
        <div class="icon">👨‍🎓</div>
        <div class="value">${totalSV}</div>
        <div class="label">Sinh Viên</div>
    </div>
    <div class="stat-card">
        <div class="icon">🏫</div>
        <div class="value">${totalLop}</div>
        <div class="label">Lớp Học</div>
    </div>
    <div class="stat-card">
        <div class="icon">📚</div>
        <div class="value">${totalMH}</div>
        <div class="label">Môn Học</div>
    </div>
    <div class="stat-card">
        <div class="icon">📝</div>
        <div class="value">${totalDiem}</div>
        <div class="label">Bản ghi Điểm</div>
    </div>
    <div class="stat-card">
        <div class="icon">📦</div>
        <div class="value">${totalSP}</div>
        <div class="label">Sản Phẩm</div>
    </div>
    <div class="stat-card">
        <div class="icon">👤</div>
        <div class="value">${totalND}</div>
        <div class="label">Người Dùng</div>
    </div>
</div>

<div class="card">
    <h3 style="margin-bottom: 1rem;">📋 Thông tin Lab 9</h3>
    <table>
        <tr><td style="font-weight:600; width:200px;">Môn học</td><td>IT3242 - Công nghệ Java</td></tr>
        <tr><td style="font-weight:600;">Bài lab</td><td>Lab 9 - Tích hợp JPA: Entity, Repository, Transaction</td></tr>
        <tr><td style="font-weight:600;">Sinh viên</td><td>Nguyễn Văn Hùng - 20230752</td></tr>
        <tr><td style="font-weight:600;">Lớp</td><td>DCCNTT 14.2</td></tr>
        <tr><td style="font-weight:600;">Công nghệ</td><td>Jakarta Persistence 3.1, Hibernate ORM 6.4, MySQL, Servlet/JSP</td></tr>
        <tr><td style="font-weight:600;">Architecture</td><td>MVC (Servlet Controller → Repository → JPA Entity → MySQL)</td></tr>
    </table>
</div>

<jsp:include page="footer.jsp"/>
