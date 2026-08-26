<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="sanpham"/></jsp:include>

<div class="page-header">
    <h1>📦 Quản lý Sản Phẩm</h1>
    <a href="${pageContext.request.contextPath}/staff/san-pham?action=create" class="btn btn-primary">➕ Thêm Sản Phẩm</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<form class="search-bar" method="get" action="${pageContext.request.contextPath}/staff/san-pham">
    <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm theo mã SP, tên SP..." value="${keyword}">
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    <c:if test="${not empty keyword}"><a href="${pageContext.request.contextPath}/staff/san-pham" class="btn btn-secondary">Xóa lọc</a></c:if>
</form>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Mã SP</th><th>Tên SP</th><th>Đơn Giá</th><th>Số Lượng</th><th>Danh Mục</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="sp" items="${sanPhams}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><span class="badge badge-primary">${sp.maSP}</span></td>
                    <td>${sp.tenSP}</td>
                    <td><fmt:formatNumber value="${sp.donGia}" type="number" groupingUsed="true"/> ₫</td>
                    <td>${sp.soLuong}</td>
                    <td><span class="badge badge-info">${sp.danhMuc}</span></td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/staff/san-pham?action=edit&id=${sp.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/staff/san-pham?action=delete&id=${sp.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa sản phẩm ${sp.tenSP}?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty sanPhams}"><tr><td colspan="7" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr></c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../../footer.jsp"/>
