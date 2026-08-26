<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="lophoc"/></jsp:include>

<div class="page-header">
    <h1>🏫 Quản lý Lớp Học</h1>
    <a href="${pageContext.request.contextPath}/admin/lop-hoc?action=create" class="btn btn-primary">➕ Thêm Lớp Học</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<form class="search-bar" method="get" action="${pageContext.request.contextPath}/admin/lop-hoc">
    <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm theo mã lớp, tên lớp..." value="${keyword}">
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    <c:if test="${not empty keyword}"><a href="${pageContext.request.contextPath}/admin/lop-hoc" class="btn btn-secondary">Xóa lọc</a></c:if>
</form>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Mã Lớp</th><th>Tên Lớp</th><th>Khoa</th><th>Số SV</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="lop" items="${lopHocs}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><span class="badge badge-primary">${lop.maLop}</span></td>
                    <td>${lop.tenLop}</td>
                    <td>${lop.khoa}</td>
                    <td><span class="badge badge-info">${lop.sinhViens.size()}</span></td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/admin/lop-hoc?action=edit&id=${lop.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/lop-hoc?action=delete&id=${lop.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa lớp ${lop.tenLop}?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty lopHocs}"><tr><td colspan="6" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr></c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../../footer.jsp"/>
