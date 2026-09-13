<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Giỏ Hàng | TechMart" />
<c:set var="activeMenu" value="cart" />
<jsp:include page="layout/header.jsp" />

<div style="margin-bottom: 1.5rem;">
    <h1 style="font-size: 1.6rem; font-weight: 800;">Giỏ Hàng Của Bạn 🛍️</h1>
    <p style="color: var(--text-secondary); font-size: 0.95rem;">Kiểm tra các sản phẩm đã chọn trước khi tiến hành tạo đơn hàng.</p>
</div>

<c:if test="${not empty param.added}">
    <div class="alert alert-success">
        <span>✓</span>
        <span>Đã thêm sản phẩm vào giỏ hàng thành công!</span>
    </div>
</c:if>

<c:if test="${not empty param.error}">
    <div class="alert alert-danger">
        <span>⚠️</span>
        <span>${param.error}</span>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty cartItems}">
        <div class="card" style="text-align: center; padding: 4rem 2rem;">
            <div style="font-size: 4rem; margin-bottom: 1rem;">🛒</div>
            <h2 style="font-size: 1.4rem; font-weight: 700; margin-bottom: 0.5rem;">Giỏ hàng của bạn đang trống</h2>
            <p style="color: var(--text-secondary); margin-bottom: 1.5rem;">Hãy khám phá hàng loạt sản phẩm công nghệ hấp dẫn và thêm vào giỏ.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary" style="display: inline-flex;">
                ➔ Mua sắm ngay
            </a>
        </div>
    </c:when>

    <c:otherwise>
        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; align-items: start;">
            <!-- Cart Table -->
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th style="text-align: right;">Đơn giá</th>
                            <th style="text-align: center;">Số lượng</th>
                            <th style="text-align: right;">Thành tiền</th>
                            <th style="text-align: center;">Xóa</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>
                                    <div style="font-weight: 700;">${item.product().name}</div>
                                    <div style="font-size: 0.75rem; color: var(--text-muted); font-weight:600;">Mã: ${item.product().code} | Kho: ${item.product().stock}</div>
                                </td>
                                <td style="text-align: right; font-weight: 600;">
                                    <fmt:formatNumber value="${item.product().price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                </td>
                                <td style="text-align: center;">
                                    <form method="post" action="${pageContext.request.contextPath}/cart/update" style="display: inline-flex; align-items: center; gap: 4px;">
                                        <input type="hidden" name="productId" value="${item.product().id}">
                                        <input type="number" name="quantity" value="${item.quantity()}" min="1" max="${item.product().stock}"
                                               style="width: 60px; padding: 4px 6px; border: 1px solid var(--border); border-radius: 4px; text-align: center;">
                                        <button type="submit" class="btn btn-secondary btn-sm" title="Cập nhật số lượng">✓</button>
                                    </form>
                                </td>
                                <td style="text-align: right; font-weight: 700; color: var(--primary);">
                                    <fmt:formatNumber value="${item.subtotal()}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                </td>
                                <td style="text-align: center;">
                                    <form method="post" action="${pageContext.request.contextPath}/cart/remove" style="display: inline;">
                                        <input type="hidden" name="productId" value="${item.product().id}">
                                        <button type="submit" class="btn btn-danger btn-sm" title="Xóa khỏi giỏ" style="padding: 4px 8px;">✕</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="padding: 1rem 1.25rem; display: flex; justify-content: space-between; align-items: center; background: #f8fafc; border-top: 1px solid var(--border);">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary btn-sm">
                        ← Tiếp tục chọn sản phẩm
                    </a>
                    <a href="${pageContext.request.contextPath}/cart/clear" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa toàn bộ giỏ hàng?')">
                        🗑️ Xóa hết giỏ
                    </a>
                </div>
            </div>

            <!-- Order Summary & Checkout -->
            <div class="card" style="padding: 1.5rem;">
                <h3 style="font-size: 1.2rem; font-weight: 700; margin-bottom: 1rem; border-bottom: 1px solid var(--border); padding-bottom: 0.75rem;">
                    Tổng Đơn Hàng
                </h3>

                <div style="display: flex; justify-content: space-between; margin-bottom: 0.75rem; color: var(--text-secondary); font-size: 0.95rem;">
                    <span>Tạm tính:</span>
                    <span style="font-weight: 600;">
                        <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                    </span>
                </div>

                <div style="display: flex; justify-content: space-between; margin-bottom: 1.25rem; color: var(--text-secondary); font-size: 0.95rem;">
                    <span>Phí vận chuyển:</span>
                    <span style="font-weight: 600; color: #059669;">Miễn phí 🎉</span>
                </div>

                <div style="display: flex; justify-content: space-between; margin-bottom: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border); font-size: 1.2rem; font-weight: 800;">
                    <span>Tổng cộng:</span>
                    <span style="color: var(--primary);">
                        <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                    </span>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/checkout">
                    <div class="form-group">
                        <label class="form-label" for="note">Ghi chú giao hàng (tùy chọn)</label>
                        <textarea id="note" name="note" class="form-control" rows="3" placeholder="Địa chỉ nhận hàng, số điện thoại hoặc lời dặn cho shipper..."></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block" style="padding: 0.85rem; font-size: 1rem;">
                        ⚡ Đặt Hàng Ngay (Tạo Đơn PENDING)
                    </button>
                </form>

                <div style="margin-top: 1rem; font-size: 0.8rem; color: var(--text-muted); text-align: center;">
                    Đơn hàng sẽ được ghi trực tiếp vào CSDL chung và chuyển đến Nhân viên kho (Warehouse Desktop) duyệt.
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="layout/footer.jsp" />
