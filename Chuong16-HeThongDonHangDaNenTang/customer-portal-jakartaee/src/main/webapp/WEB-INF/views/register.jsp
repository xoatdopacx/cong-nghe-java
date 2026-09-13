<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đăng ký tài khoản | Cổng Khách Hàng" />
<jsp:include page="layout/header.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">
        <div class="auth-header">
            <div style="font-size: 2.5rem;">✨</div>
            <h2>Đăng Ký Tài Khoản</h2>
            <p>Tạo tài khoản khách hàng mới để mua sắm</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span>⚠️</span>
                <span>${errorMessage}</span>
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group">
                <label class="form-label" for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" class="form-control"
                       value="${username}" placeholder="Ví dụ: mycustomer" required autofocus>
            </div>

            <div class="form-group">
                <label class="form-label" for="fullName">Họ và tên</label>
                <input type="text" id="fullName" name="fullName" class="form-control"
                       value="${fullName}" placeholder="Ví dụ: Nguyễn Văn An" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Tối thiểu 4 ký tự" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1rem; padding: 0.75rem;">
                Đăng Ký Tài Khoản ➔
            </button>
        </form>

        <div style="text-align: center; margin-top: 1.5rem; font-size: 0.9rem; color: var(--text-secondary);">
            Đã có tài khoản? <a href="${pageContext.request.contextPath}/login" style="color: var(--primary); font-weight:600; text-decoration:none;">Đăng nhập ngay</a>
        </div>
    </div>
</div>

<jsp:include page="layout/footer.jsp" />
