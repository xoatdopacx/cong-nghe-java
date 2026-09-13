<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Chi Tiết Đơn Hàng #${order.id} | TechMart" />
<c:set var="activeMenu" value="orders" />
<jsp:include page="layout/header.jsp" />

<div style="margin-bottom: 1.5rem; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 1rem;">
    <div>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-secondary btn-sm" style="margin-bottom: 0.5rem;">
            ← Quay lại danh sách đơn
        </a>
        <h1 style="font-size: 1.6rem; font-weight: 800; display: flex; align-items: center; gap: 0.75rem;">
            Đơn Hàng #${order.id}
            <span class="badge badge-${order.status}">${order.status}</span>
        </h1>
        <p style="color: var(--text-secondary); font-size: 0.9rem;">Thời gian đặt hàng: <strong>${order.createdAt}</strong></p>
    </div>

    <c:if test="${order.status == 'PENDING'}">
        <form method="post" action="${pageContext.request.contextPath}/orders/cancel"
              onsubmit="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này không? Hành động này không thể hoàn tác.')">
            <input type="hidden" name="id" value="${order.id}">
            <button type="submit" class="btn btn-danger">
                🚫 Hủy Đơn Hàng Này
            </button>
        </form>
    </c:if>
</div>

<c:if test="${not empty param.created}">
    <div class="alert alert-success">
        <span>🎉</span>
        <span>Đặt hàng thành công! Đơn hàng của bạn đang ở trạng thái <strong>PENDING</strong> và đã sẵn sàng được tiếp nhận bởi bộ phận Kho hàng.</span>
    </div>
</c:if>

<c:if test="${not empty param.cancelled}">
    <div class="alert alert-success">
        <span>✓</span>
        <span>Đã hủy đơn hàng thành công. Trạng thái hiện tại: <strong>CANCELLED</strong>.</span>
    </div>
</c:if>

<c:if test="${not empty param.error}">
    <div class="alert alert-danger">
        <span>⚠️</span>
        <span>${param.error}</span>
    </div>
</c:if>

<div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; align-items: start;">
    <!-- Items Table -->
    <div class="table-container">
        <div style="padding: 1rem 1.25rem; font-weight: 700; border-bottom: 1px solid var(--border); background: #f8fafc;">
            Danh Sách Sản Phẩm Đã Mua
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th style="text-align: right;">Đơn giá</th>
                    <th style="text-align: center;">Số lượng</th>
                    <th style="text-align: right;">Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="it" items="${order.items}">
                    <tr>
                        <td>
                            <div style="font-weight: 600;">${it.productName}</div>
                            <div style="font-size: 0.75rem; color: var(--text-muted);">Mã: ${it.productCode}</div>
                        </td>
                        <td style="text-align: right;">
                            <fmt:formatNumber value="${it.unitPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                        <td style="text-align: center; font-weight: 600;">
                            ${it.quantity}
                        </td>
                        <td style="text-align: right; font-weight: 700; color: var(--primary);">
                            <fmt:formatNumber value="${it.subtotal}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="padding: 1.25rem; background: #f8fafc; border-top: 1px solid var(--border); display: flex; justify-content: flex-end; align-items: center; gap: 1.5rem;">
            <span style="font-size: 1.1rem; font-weight: 700;">Tổng tiền thanh toán:</span>
            <span style="font-size: 1.4rem; font-weight: 800; color: var(--primary);">
                <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
            </span>
        </div>
    </div>

    <!-- Order Info & Workflow Card -->
    <div style="display: flex; flex-direction: column; gap: 1.5rem;">
        <div class="card" style="padding: 1.5rem;">
            <h3 style="font-size: 1.1rem; font-weight: 700; margin-bottom: 1rem; border-bottom: 1px solid var(--border); padding-bottom: 0.5rem;">
                Thông Tin Giao Nhận
            </h3>
            <div style="margin-bottom: 0.75rem;">
                <span style="font-size: 0.85rem; color: var(--text-muted); display: block;">Khách hàng:</span>
                <strong>${sessionScope.currentUser.fullName()} (${sessionScope.currentUser.username()})</strong>
            </div>
            <div>
                <span style="font-size: 0.85rem; color: var(--text-muted); display: block;">Ghi chú đơn hàng:</span>
                <p style="background: #f8fafc; padding: 0.75rem; border-radius: var(--radius-sm); border: 1px solid var(--border); font-size: 0.9rem; margin-top: 0.25rem;">
                    <c:out value="${order.note != null && !order.note.isEmpty() ? order.note : '(Không có ghi chú)'}" />
                </p>
            </div>
        </div>

        <div class="card" style="padding: 1.5rem; background: #faf5ff; border-color: #e9d5ff;">
            <h3 style="font-size: 1rem; font-weight: 700; color: #6b21a8; margin-bottom: 0.75rem;">
                🔄 Quy Trình Tích Hợp Đa Nền Tảng
            </h3>
            <ul style="list-style: none; font-size: 0.85rem; color: #581c87; display: flex; flex-direction: column; gap: 0.6rem;">
                <li>✓ <strong>Jakarta EE</strong>: Bạn đã tạo đơn hàng này trực tiếp vào CSDL MySQL chung.</li>
                <li>➔ <strong>Java Swing (Warehouse)</strong>: Nhân viên kho mở ứng dụng Desktop, kiểm tra đơn và duyệt CONFIRMED -> PROCESSING -> SHIPPED (trừ tồn kho tự động).</li>
                <li>➔ <strong>Spring Boot (Portal)</strong>: Ban quản lý theo dõi báo cáo doanh thu và nhật ký trạng thái (Audit History) theo thời gian thực.</li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="layout/footer.jsp" />
