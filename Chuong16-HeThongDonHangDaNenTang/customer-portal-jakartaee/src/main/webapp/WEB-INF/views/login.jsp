<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đăng nhập | Cổng Khách Hàng" />
<jsp:include page="layout/header.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">
        <div class="auth-header">
            <div style="font-size: 2.5rem;">🛍️</div>
            <h2>Đăng Nhập Khách Hàng</h2>
            <p>Truy cập hệ thống đặt hàng trực tuyến</p>
        </div>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                <span>⚠️</span>
                <span>${param.error}</span>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span>⚠️</span>
                <span>${errorMessage}</span>
            </div>
        </c:if>
        <c:if test="${not empty param.loggedOut}">
            <div class="alert alert-success">
                <span>✓</span>
                <span>Bạn đã đăng xuất thành công.</span>
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label class="form-label" for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" class="form-control"
                       value="${username != null ? username : 'customer01'}" required autofocus>
            </div>

            <div class="form-group">
                <label class="form-label" for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" class="form-control"
                       value="123456" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1rem; padding: 0.75rem;">
                Đăng nhập ngay ➔
            </button>
        </form>

        <div class="demo-account-box">
            <strong>Tài khoản thử nghiệm sẵn có:</strong>
            <div style="margin-top: 4px;">• Tài khoản: <code>customer01</code> | Mật khẩu: <code>123456</code></div>
            <div>• Hoặc nhấn <a href="${pageContext.request.contextPath}/register" style="color: var(--primary); font-weight:600;">Đăng ký tài khoản mới</a></div>
        </div>
    </div>
</div>

<jsp:include page="layout/footer.jsp" />
