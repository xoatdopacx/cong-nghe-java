<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="sinhvien"/></jsp:include>

<div class="page-header">
    <h1>👨‍🎓 Quản lý Sinh Viên</h1>
    <a href="${pageContext.request.contextPath}/admin/sinh-vien?action=create" class="btn btn-primary">➕ Thêm Sinh Viên</a>
</div>

<c:if test="${not empty success}">
    <div class="alert alert-success">✅ ${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">❌ ${error}</div>
</c:if>

<form class="search-bar" method="get" action="${pageContext.request.contextPath}/admin/sinh-vien">
    <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm theo mã SV, họ tên..." value="${keyword}">
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    <c:if test="${not empty keyword}">
        <a href="${pageContext.request.contextPath}/admin/sinh-vien" class="btn btn-secondary">Xóa lọc</a>
    </c:if>
</form>

<div class="table-wrapper">
    <table>
        <thead>
            <tr>
                <th>#</th><th>Mã SV</th><th>Họ Tên</th><th>Email</th><th>Điện Thoại</th><th>Lớp</th><th>Thao Tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sv" items="${sinhViens}" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * 5 + loop.index + 1}</td>
                    <td><span class="badge badge-primary">${sv.maSV}</span></td>
                    <td>${sv.hoTen}</td>
                    <td>${sv.email}</td>
                    <td>${sv.dienThoai}</td>
                    <td>
                        <c:choose>
                            <c:when test="${sv.lopHoc != null}"><span class="badge badge-info">${sv.lopHoc.maLop}</span></c:when>
                            <c:otherwise><span class="badge badge-warning">Chưa xếp</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/admin/sinh-vien?action=edit&id=${sv.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/sinh-vien?action=delete&id=${sv.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa sinh viên ${sv.hoTen}?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty sinhViens}">
                <tr><td colspan="7" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/admin/sinh-vien?page=${currentPage-1}&keyword=${keyword}">« Trước</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}"><span class="active">${i}</span></c:when>
                <c:otherwise><a href="${pageContext.request.contextPath}/admin/sinh-vien?page=${i}&keyword=${keyword}">${i}</a></c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/admin/sinh-vien?page=${currentPage+1}&keyword=${keyword}">Sau »</a>
        </c:if>
    </div>
</c:if>

<p style="text-align:center; color:var(--text-muted); margin-top:0.5rem; font-size:0.85rem;">
    Tổng: ${totalRecords} sinh viên | Trang ${currentPage}/${totalPages > 0 ? totalPages : 1}
</p>

<jsp:include page="../../footer.jsp"/>
