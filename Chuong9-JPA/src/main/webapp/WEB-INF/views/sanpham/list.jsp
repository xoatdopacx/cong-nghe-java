<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="sanpham"/></jsp:include>

<div class="page-header">
    <h1>📦 Quản lý Sản Phẩm</h1>
    <a href="${pageContext.request.contextPath}/san-pham?action=create" class="btn btn-primary">➕ Thêm Sản Phẩm</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<form class="search-bar" method="get" action="${pageContext.request.contextPath}/san-pham">
    <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm sản phẩm..." value="${keyword}">
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    <c:if test="${not empty keyword}">
        <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">Xóa lọc</a>
    </c:if>
</form>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Mã SP</th><th>Tên Sản Phẩm</th><th>Đơn Giá</th><th>Số Lượng</th><th>Danh Mục</th><th>Thành Tiền</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="sp" items="${sanPhams}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><span class="badge badge-primary">${sp.maSP}</span></td>
                    <td>${sp.tenSP}</td>
                    <td style="text-align:right;"><fmt:formatNumber value="${sp.donGia}" pattern="#,###"/> ₫</td>
                    <td style="text-align:center;">
                        <c:choose>
                            <c:when test="${sp.soLuong > 10}"><span class="badge badge-success">${sp.soLuong}</span></c:when>
                            <c:when test="${sp.soLuong > 0}"><span class="badge badge-warning">${sp.soLuong}</span></c:when>
                            <c:otherwise><span class="badge badge-danger">Hết hàng</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td><span class="badge badge-info">${sp.danhMuc}</span></td>
                    <td style="text-align:right; font-weight:600;"><fmt:formatNumber value="${sp.thanhTien}" pattern="#,###"/> ₫</td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Xóa ${sp.tenSP}?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty sanPhams}">
                <tr><td colspan="8" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../footer.jsp"/>
