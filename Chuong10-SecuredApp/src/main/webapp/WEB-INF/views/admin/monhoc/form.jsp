<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="monhoc"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Môn Học' : '➕ Thêm Môn Học Mới'}</h1>
    <a href="${pageContext.request.contextPath}/admin/mon-hoc" class="btn btn-secondary">← Quay lại</a>
</div>

<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/admin/mon-hoc">
        <input type="hidden" name="id" value="${monHoc.id}">
        <div class="form-row">
            <div class="form-group">
                <label for="maMon">Mã Môn *</label>
                <input type="text" id="maMon" name="maMon" class="form-control" value="${monHoc.maMon}" required placeholder="VD: IT3242">
            </div>
            <div class="form-group">
                <label for="tenMon">Tên Môn *</label>
                <input type="text" id="tenMon" name="tenMon" class="form-control" value="${monHoc.tenMon}" required placeholder="VD: Công nghệ Java">
            </div>
        </div>
        <div class="form-group">
            <label for="soTinChi">Số Tín Chỉ</label>
            <input type="number" id="soTinChi" name="soTinChi" class="form-control" value="${monHoc.soTinChi}" min="1" max="10">
        </div>
        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/admin/mon-hoc" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../../footer.jsp"/>
