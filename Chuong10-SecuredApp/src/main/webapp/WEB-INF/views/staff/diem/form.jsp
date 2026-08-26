<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="diem"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Điểm' : '➕ Thêm Điểm Mới'}</h1>
    <a href="${pageContext.request.contextPath}/staff/diem" class="btn btn-secondary">← Quay lại</a>
</div>

<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/staff/diem">
        <input type="hidden" name="id" value="${diem.id}">
        <div class="form-row">
            <div class="form-group">
                <label for="sinhVienId">Sinh Viên *</label>
                <select id="sinhVienId" name="sinhVienId" class="form-control" required>
                    <option value="">-- Chọn sinh viên --</option>
                    <c:forEach var="sv" items="${sinhViens}">
                        <option value="${sv.id}" ${diem.sinhVien != null && diem.sinhVien.id == sv.id ? 'selected' : ''}>${sv.maSV} - ${sv.hoTen}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="monHocId">Môn Học *</label>
                <select id="monHocId" name="monHocId" class="form-control" required>
                    <option value="">-- Chọn môn học --</option>
                    <c:forEach var="mh" items="${monHocs}">
                        <option value="${mh.id}" ${diem.monHoc != null && diem.monHoc.id == mh.id ? 'selected' : ''}>${mh.maMon} - ${mh.tenMon}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="diemSo">Điểm Số *</label>
                <input type="number" id="diemSo" name="diemSo" class="form-control" value="${diem.diemSo}" step="0.1" min="0" max="10" required>
            </div>
            <div class="form-group">
                <label for="ghiChu">Ghi Chú</label>
                <input type="text" id="ghiChu" name="ghiChu" class="form-control" value="${diem.ghiChu}" placeholder="Ghi chú (tùy chọn)">
            </div>
        </div>
        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/staff/diem" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../../footer.jsp"/>
