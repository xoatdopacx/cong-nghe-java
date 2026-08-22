<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="sinhvien"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Sinh Viên' : '➕ Thêm Sinh Viên Mới'}</h1>
    <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-secondary">← Quay lại</a>
</div>

<c:if test="${not empty errors}">
    <div class="alert alert-danger">
        <strong>❌ Vui lòng sửa các lỗi sau:</strong>
        <ul style="margin: 0.5rem 0 0 1.5rem;">
            <c:forEach var="err" items="${errors}">
                <li>${err}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">❌ ${error}</div>
</c:if>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/sinh-vien">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="${sinhVien.id}">

        <div class="form-row">
            <div class="form-group">
                <label for="maSV">Mã Sinh Viên *</label>
                <input type="text" id="maSV" name="maSV" class="form-control"
                       value="${sinhVien.maSV}" required placeholder="VD: 20230752"
                       ${isEdit ? 'readonly' : ''}>
            </div>
            <div class="form-group">
                <label for="hoTen">Họ Tên *</label>
                <input type="text" id="hoTen" name="hoTen" class="form-control"
                       value="${sinhVien.hoTen}" required placeholder="VD: Nguyễn Văn Hùng">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control"
                       value="${sinhVien.email}" placeholder="VD: hung@eaut.edu.vn">
            </div>
            <div class="form-group">
                <label for="dienThoai">Điện Thoại</label>
                <input type="text" id="dienThoai" name="dienThoai" class="form-control"
                       value="${sinhVien.dienThoai}" placeholder="VD: 0912345678">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="diaChi">Địa Chỉ</label>
                <input type="text" id="diaChi" name="diaChi" class="form-control"
                       value="${sinhVien.diaChi}" placeholder="VD: Hà Nội">
            </div>
            <div class="form-group">
                <label for="lopHocId">Lớp Học</label>
                <select id="lopHocId" name="lopHocId" class="form-control">
                    <option value="">-- Chọn lớp --</option>
                    <c:forEach var="lop" items="${lopHocs}">
                        <option value="${lop.id}"
                            ${sinhVien.lopHoc != null && sinhVien.lopHoc.id == lop.id ? 'selected' : ''}>
                            ${lop.maLop} - ${lop.tenLop}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../footer.jsp"/>
