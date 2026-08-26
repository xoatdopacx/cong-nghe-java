<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../header.jsp"><jsp:param name="active" value="users"/></jsp:include>

<div class="page-header">
    <h1>${isEdit ? '✏️ Sửa Tài Khoản' : '➕ Thêm Tài Khoản Mới'}</h1>
    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">← Quay lại</a>
</div>

<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="card">
    <form method="post" action="${pageContext.request.contextPath}/admin/users">
        <input type="hidden" name="id" value="${editUser.id}">
        <div class="form-row">
            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" class="form-control" value="${editUser.email}" required placeholder="VD: admin@eaut.edu.vn">
            </div>
            <div class="form-group">
                <label for="fullName">Họ Tên *</label>
                <input type="text" id="fullName" name="fullName" class="form-control" value="${editUser.fullName}" required placeholder="VD: Quản trị viên">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="password">Mật khẩu ${isEdit ? '(để trống nếu không đổi)' : '*'}</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Nhập mật khẩu" ${isEdit ? '' : 'required'}>
            </div>
            <div class="form-group">
                <label for="role">Vai Trò *</label>
                <select id="role" name="role" class="form-control" required>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r}" ${editUser.role == r ? 'selected' : ''}>${r}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div style="margin-top: 1rem;">
            <button type="submit" class="btn btn-success">${isEdit ? '💾 Cập Nhật' : '➕ Thêm Mới'}</button>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="../../footer.jsp"/>
