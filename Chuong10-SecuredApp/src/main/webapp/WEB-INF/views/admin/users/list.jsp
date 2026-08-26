<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="users"/></jsp:include>

<div class="page-header">
    <h1>👥 Quản lý Tài Khoản</h1>
    <a href="${pageContext.request.contextPath}/admin/users?action=create" class="btn btn-primary">➕ Thêm Tài Khoản</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<form class="search-bar" method="get" action="${pageContext.request.contextPath}/admin/users">
    <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm theo email, họ tên..." value="${keyword}">
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    <c:if test="${not empty keyword}"><a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Xóa lọc</a></c:if>
</form>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Email</th><th>Họ Tên</th><th>Vai Trò</th><th>Trạng Thái</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="u" items="${users}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${u.email}</td>
                    <td>${u.fullName}</td>
                    <td>
                        <span class="badge badge-${u.role == 'ADMIN' ? 'danger' : (u.role == 'STAFF' ? 'warning' : 'info')}">${u.role}</span>
                    </td>
                    <td>
                        <span class="badge badge-${u.active ? 'success' : 'danger'}">${u.active ? 'Hoạt động' : 'Đã khóa'}</span>
                    </td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/users?action=toggle&id=${u.id}" class="btn btn-${u.active ? 'danger' : 'success'} btn-sm">${u.active ? '🔒 Khóa' : '🔓 Mở'}</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty users}"><tr><td colspan="6" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr></c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../../footer.jsp"/>
