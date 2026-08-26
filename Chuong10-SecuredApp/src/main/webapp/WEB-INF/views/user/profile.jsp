<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="profile"/></jsp:include>

<div class="page-header">
    <h1>👤 Hồ sơ cá nhân</h1>
</div>

<div class="profile-grid">
    <%-- Bài 8: Cập nhật hồ sơ --%>
    <div class="card profile-card">
        <h3>📝 Thông tin cá nhân</h3>

        <c:if test="${not empty profileSuccess}">
            <div class="alert alert-success">✅ ${profileSuccess}</div>
        </c:if>
        <c:if test="${not empty profileError}">
            <div class="alert alert-danger">❌ ${profileError}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/user/profile">
            <input type="hidden" name="action" value="updateProfile">

            <div class="form-group">
                <label for="fullName">Họ tên</label>
                <input type="text" id="fullName" name="fullName" class="form-control"
                       value="${profileUser.fullName}" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control"
                       value="${profileUser.email}" required>
            </div>
            <div class="form-group">
                <label>Vai trò</label>
                <input type="text" class="form-control" value="${profileUser.role}" readonly
                       style="opacity: 0.7; cursor: not-allowed;">
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <input type="text" class="form-control"
                       value="${profileUser.active ? 'Đang hoạt động' : 'Đã khóa'}" readonly
                       style="opacity: 0.7; cursor: not-allowed;">
            </div>

            <button type="submit" class="btn btn-success">💾 Cập nhật hồ sơ</button>
        </form>
    </div>

    <%-- Bài 9: Đổi mật khẩu --%>
    <div class="card profile-card">
        <h3>🔑 Đổi mật khẩu</h3>

        <c:if test="${not empty passwordSuccess}">
            <div class="alert alert-success">✅ ${passwordSuccess}</div>
        </c:if>
        <c:if test="${not empty passwordError}">
            <div class="alert alert-danger">❌ ${passwordError}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/user/profile">
            <input type="hidden" name="action" value="changePassword">

            <div class="form-group">
                <label for="oldPassword">Mật khẩu cũ</label>
                <input type="password" id="oldPassword" name="oldPassword" class="form-control"
                       placeholder="Nhập mật khẩu hiện tại" required>
            </div>
            <div class="form-group">
                <label for="newPassword">Mật khẩu mới</label>
                <input type="password" id="newPassword" name="newPassword" class="form-control"
                       placeholder="Nhập mật khẩu mới (tối thiểu 4 ký tự)" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
                       placeholder="Nhập lại mật khẩu mới" required>
            </div>

            <button type="submit" class="btn btn-warning">🔐 Đổi mật khẩu</button>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
