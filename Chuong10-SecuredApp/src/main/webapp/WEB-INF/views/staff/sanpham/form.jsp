<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="sanpham"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Sản Phẩm' : '➕ Thêm Sản Phẩm Mới'}</h1>
    <a href="${pageContext.request.contextPath}/staff/san-pham" class="btn btn-secondary">← Quay lại</a>
</div>

<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/staff/san-pham">
        <input type="hidden" name="id" value="${sanPham.id}">
        <div class="form-row">
            <div class="form-group">
                <label for="maSP">Mã Sản Phẩm *</label>
                <input type="text" id="maSP" name="maSP" class="form-control" value="${sanPham.maSP}" required placeholder="VD: SP001">
            </div>
            <div class="form-group">
                <label for="tenSP">Tên Sản Phẩm *</label>
                <input type="text" id="tenSP" name="tenSP" class="form-control" value="${sanPham.tenSP}" required placeholder="VD: Laptop Dell XPS 15">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="donGia">Đơn Giá (VNĐ)</label>
                <input type="number" id="donGia" name="donGia" class="form-control" value="${sanPham.donGia}" step="1000" min="0">
            </div>
            <div class="form-group">
                <label for="soLuong">Số Lượng</label>
                <input type="number" id="soLuong" name="soLuong" class="form-control" value="${sanPham.soLuong}" min="0">
            </div>
        </div>
        <div class="form-group">
            <label for="danhMuc">Danh Mục</label>
            <input type="text" id="danhMuc" name="danhMuc" class="form-control" value="${sanPham.danhMuc}" placeholder="VD: Laptop, Điện thoại, Phụ kiện">
        </div>
        <div class="form-group">
            <label for="moTa">Mô Tả</label>
            <textarea id="moTa" name="moTa" class="form-control" placeholder="Mô tả sản phẩm...">${sanPham.moTa}</textarea>
        </div>
        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/staff/san-pham" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../../footer.jsp"/>
