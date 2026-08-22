<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="diem"/></jsp:include>

<div class="page-header">
    <h1>📝 Quản lý Điểm</h1>
    <a href="${pageContext.request.contextPath}/diem?action=create" class="btn btn-primary">➕ Thêm Điểm</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Sinh Viên</th><th>Mã SV</th><th>Môn Học</th><th>Điểm</th><th>Xếp Loại</th><th>Ghi Chú</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="d" items="${diems}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${d.sinhVien.hoTen}</td>
                    <td><span class="badge badge-primary">${d.sinhVien.maSV}</span></td>
                    <td>${d.monHoc.tenMon}</td>
                    <td style="font-weight:700; font-size:1.1rem;">
                        <c:choose>
                            <c:when test="${d.diemSo >= 8.5}"><span style="color:#6ee7b7;">${d.diemSo}</span></c:when>
                            <c:when test="${d.diemSo >= 7.0}"><span style="color:#67e8f9;">${d.diemSo}</span></c:when>
                            <c:when test="${d.diemSo >= 5.5}"><span style="color:#fcd34d;">${d.diemSo}</span></c:when>
                            <c:otherwise><span style="color:#fca5a5;">${d.diemSo}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${d.xepLoai == 'Giỏi'}"><span class="badge badge-success">${d.xepLoai}</span></c:when>
                            <c:when test="${d.xepLoai == 'Khá'}"><span class="badge badge-info">${d.xepLoai}</span></c:when>
                            <c:when test="${d.xepLoai == 'Trung bình'}"><span class="badge badge-warning">${d.xepLoai}</span></c:when>
                            <c:otherwise><span class="badge badge-danger">${d.xepLoai}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${d.ghiChu}</td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/diem?action=edit&id=${d.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/diem?action=delete&id=${d.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Xóa điểm?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty diems}">
                <tr><td colspan="8" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../footer.jsp"/>
