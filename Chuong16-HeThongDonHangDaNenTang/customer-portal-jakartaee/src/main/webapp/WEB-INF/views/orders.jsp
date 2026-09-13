<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Đơn Hàng Của Tôi | TechMart" />
<c:set var="activeMenu" value="orders" />
<jsp:include page="layout/header.jsp" />

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; flex-wrap: wrap; gap: 1rem;">
    <div>
        <h1 style="font-size: 1.6rem; font-weight: 800;">Lịch Sử Đơn Hàng 📋</h1>
        <p style="color: var(--text-secondary); font-size: 0.95rem;">Theo dõi trạng thái và tiến độ xử lý các đơn hàng của bạn.</p>
    </div>
    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-sm">
        + Mua thêm sản phẩm
    </a>
</div>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        <span>⚠️</span>
        <span>${errorMessage}</span>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty orders}">
        <div class="card" style="text-align: center; padding: 4rem 2rem;">
            <div style="font-size: 4rem; margin-bottom: 1rem;">📦</div>
            <h2 style="font-size: 1.4rem; font-weight: 700; margin-bottom: 0.5rem;">Bạn chưa có đơn hàng nào</h2>
            <p style="color: var(--text-secondary); margin-bottom: 1.5rem;">Hãy khám phá các sản phẩm công nghệ tuyệt vời và tạo đơn hàng đầu tiên.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                Mua sắm ngay ➔
            </a>
        </div>
    </c:when>

    <c:otherwise>
        <div class="table-container">
            <table class="table">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày tạo</th>
                        <th>Ghi chú</th>
                        <th style="text-align: right;">Tổng thanh toán</th>
                        <th style="text-align: center;">Trạng thái</th>
                        <th style="text-align: center;">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td>
                                <strong>#${o.id}</strong>
                            </td>
                            <td style="color: var(--text-secondary); font-size: 0.85rem;">
                                ${o.createdAt}
                            </td>
                            <td style="color: var(--text-secondary); max-width: 250px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                                <c:out value="${o.note != null && !o.note.isEmpty() ? o.note : '-'}" />
                            </td>
                            <td style="text-align: right; font-weight: 700; color: var(--primary);">
                                <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                            </td>
                            <td style="text-align: center;">
                                <span class="badge badge-${o.status}">${o.status}</span>
                            </td>
                            <td style="text-align: center;">
                                <a href="${pageContext.request.contextPath}/orders/detail?id=${o.id}" class="btn btn-secondary btn-sm">
                                    Chi tiết ➔
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="layout/footer.jsp" />
