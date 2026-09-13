<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Danh Mục Sản Phẩm | TechMart" />
<c:set var="activeMenu" value="products" />
<jsp:include page="layout/header.jsp" />

<section class="hero-banner">
    <h1 class="hero-title">Chào mừng đến với TechMart! ⚡</h1>
    <p class="hero-desc">Khám phá các sản phẩm công nghệ chất lượng cao. Đặt hàng ngay hôm nay để nhận thông báo xử lý đơn hàng theo thời gian thực.</p>
</section>

<c:if test="${not empty param.added}">
    <div class="alert alert-success">
        <span>✓</span>
        <span>Đã thêm sản phẩm vào giỏ hàng thành công! <a href="${pageContext.request.contextPath}/cart" style="font-weight:700; color:inherit; text-decoration:underline;">Xem giỏ hàng</a></span>
    </div>
</c:if>

<c:if test="${not empty param.registered}">
    <div class="alert alert-success">
        <span>✓</span>
        <span>Chúc mừng! Tài khoản đã đăng ký và đăng nhập thành công. Hãy bắt đầu chọn sản phẩm!</span>
    </div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        <span>⚠️</span>
        <span>${errorMessage}</span>
    </div>
</c:if>

<div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 1rem; margin-bottom: 1.5rem;">
    <h2 style="font-size: 1.4rem; font-weight: 700;">Danh Sách Sản Phẩm (${products != null ? products.size() : 0})</h2>

    <form method="get" action="${pageContext.request.contextPath}/products" style="display: flex; gap: 0.5rem; max-width: 380px; width: 100%;">
        <input type="text" name="q" class="form-control" placeholder="Tìm tên hoặc mã sản phẩm..." value="${searchQuery}">
        <button type="submit" class="btn btn-primary">Tìm</button>
        <c:if test="${not empty searchQuery}">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary">Xóa</a>
        </c:if>
    </form>
</div>

<div class="product-grid">
    <c:forEach var="p" items="${products}">
        <div class="product-card">
            <div class="product-img-placeholder">
                <c:choose>
                    <c:when test="${p.code.startsWith('LT')}">💻</c:when>
                    <c:when test="${p.code.startsWith('PH')}">📱</c:when>
                    <c:when test="${p.code.startsWith('TB')}">📲</c:when>
                    <c:when test="${p.code.startsWith('SW')}">⌚</c:when>
                    <c:when test="${p.code.startsWith('MN')}">🖥️</c:when>
                    <c:when test="${p.code.startsWith('KB')}">⌨️</c:when>
                    <c:when test="${p.code.startsWith('MS')}">🖱️</c:when>
                    <c:when test="${p.code.startsWith('HP')}">🎧</c:when>
                    <c:otherwise>📦</c:otherwise>
                </c:choose>
                <span class="stock-tag ${p.stock > 0 ? 'in-stock' : 'out-stock'}">
                    ${p.stock > 0 ? 'Còn: ' += p.stock : 'Hết hàng'}
                </span>
            </div>

            <div class="product-body">
                <span class="product-code">${p.code}</span>
                <h3 class="product-title">${p.name}</h3>

                <div class="product-footer">
                    <div>
                        <div class="product-price">
                            <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${p.stock > 0}">
                            <form method="post" action="${pageContext.request.contextPath}/cart/add" style="display: flex; align-items: center; gap: 6px;">
                                <input type="hidden" name="productId" value="${p.id}">
                                <input type="hidden" name="redirect" value="catalog">
                                <input type="hidden" name="quantity" value="1">
                                <button type="submit" class="btn btn-primary btn-sm" title="Thêm vào giỏ">
                                    + Giỏ hàng
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-secondary btn-sm" disabled style="opacity: 0.6;">Tạm hết</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="layout/footer.jsp" />
