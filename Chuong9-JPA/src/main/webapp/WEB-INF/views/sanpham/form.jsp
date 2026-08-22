<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="sanpham"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Sản Phẩm' : '➕ Thêm Sản Phẩm Mới'}</h1>
    <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">← Quay lại</a>
</div>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/san-pham">
        <input type="hidden" name="id" value="${sanPham.id}">

        <div class="form-row">
            <div class="form-group">
                <label for="maSP">Mã Sản Phẩm *</label>
                <input type="text" id="maSP" name="maSP" class="form-control"
                       value="${sanPham.maSP}" required placeholder="VD: SP001">
            </div>
            <div class="form-group">
                <label for="tenSP">Tên Sản Phẩm *</label>
                <input type="text" id="tenSP" name="tenSP" class="form-control"
                       value="${sanPham.tenSP}" required placeholder="VD: Laptop Dell XPS 15">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="donGia">Đơn Giá (VNĐ)</label>
                <input type="number" id="donGia" name="donGia" class="form-control"
                       value="${sanPham.donGia}" min="0" step="1000" placeholder="VD: 32000000">
            </div>
            <div class="form-group">
                <label for="soLuong">Số Lượng</label>
                <input type="number" id="soLuong" name="soLuong" class="form-control"
                       value="${sanPham.soLuong}" min="0" placeholder="VD: 10">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="danhMuc">Danh Mục</label>
                <select id="danhMuc" name="danhMuc" class="form-control">
                    <option value="">-- Chọn danh mục --</option>
                    <option value="Laptop" ${sanPham.danhMuc == 'Laptop' ? 'selected' : ''}>Laptop</option>
                    <option value="Điện thoại" ${sanPham.danhMuc == 'Điện thoại' ? 'selected' : ''}>Điện thoại</option>
                    <option value="Phụ kiện" ${sanPham.danhMuc == 'Phụ kiện' ? 'selected' : ''}>Phụ kiện</option>
                    <option value="Khác" ${sanPham.danhMuc == 'Khác' ? 'selected' : ''}>Khác</option>
                </select>
            </div>
            <div class="form-group">
                <label for="moTa">Mô Tả</label>
                <textarea id="moTa" name="moTa" class="form-control" placeholder="Mô tả sản phẩm...">${sanPham.moTa}</textarea>
            </div>
        </div>

        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../footer.jsp"/>
