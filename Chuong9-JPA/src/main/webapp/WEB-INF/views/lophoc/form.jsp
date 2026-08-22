<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="lophoc"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Lớp Học' : '➕ Thêm Lớp Học Mới'}</h1>
    <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-secondary">← Quay lại</a>
</div>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/lop-hoc">
        <input type="hidden" name="id" value="${lopHoc.id}">

        <div class="form-row">
            <div class="form-group">
                <label for="maLop">Mã Lớp *</label>
                <input type="text" id="maLop" name="maLop" class="form-control"
                       value="${lopHoc.maLop}" required placeholder="VD: DCCNTT14.2">
            </div>
            <div class="form-group">
                <label for="tenLop">Tên Lớp *</label>
                <input type="text" id="tenLop" name="tenLop" class="form-control"
                       value="${lopHoc.tenLop}" required placeholder="VD: DCCNTT 14.2">
            </div>
        </div>

        <div class="form-group">
            <label for="khoa">Khoa</label>
            <input type="text" id="khoa" name="khoa" class="form-control"
                   value="${lopHoc.khoa}" placeholder="VD: Công nghệ thông tin">
        </div>

        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../footer.jsp"/>
