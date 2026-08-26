<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="diem"/></jsp:include>

<div class="page-header">
    <h1>📝 Quản lý Điểm</h1>
    <a href="${pageContext.request.contextPath}/staff/diem?action=create" class="btn btn-primary">➕ Thêm Điểm</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Sinh Viên</th><th>Môn Học</th><th>Điểm Số</th><th>Xếp Loại</th><th>Ghi Chú</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="d" items="${diems}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${d.sinhVien.hoTen} <span class="badge badge-primary">${d.sinhVien.maSV}</span></td>
                    <td>${d.monHoc.tenMon}</td>
                    <td><strong>${d.diemSo}</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${d.diemSo >= 8.5}"><span class="badge badge-success">${d.xepLoai}</span></c:when>
                            <c:when test="${d.diemSo >= 7.0}"><span class="badge badge-info">${d.xepLoai}</span></c:when>
                            <c:when test="${d.diemSo >= 5.5}"><span class="badge badge-warning">${d.xepLoai}</span></c:when>
                            <c:otherwise><span class="badge badge-danger">${d.xepLoai}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${d.ghiChu}</td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/staff/diem?action=edit&id=${d.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/staff/diem?action=delete&id=${d.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa điểm này?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty diems}"><tr><td colspan="7" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr></c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../../footer.jsp"/>
