<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../header.jsp"><jsp:param name="active" value="monhoc"/></jsp:include>

<div class="page-header">
    <h1>📚 Quản lý Môn Học</h1>
    <a href="${pageContext.request.contextPath}/mon-hoc?action=create" class="btn btn-primary">➕ Thêm Môn</a>
</div>

<c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">❌ ${error}</div></c:if>

<div class="table-wrapper">
    <table>
        <thead><tr><th>#</th><th>Mã Môn</th><th>Tên Môn</th><th>Số Tín Chỉ</th><th>Thao Tác</th></tr></thead>
        <tbody>
            <c:forEach var="mh" items="${monHocs}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><span class="badge badge-primary">${mh.maMon}</span></td>
                    <td>${mh.tenMon}</td>
                    <td><span class="badge badge-info">${mh.soTinChi} TC</span></td>
                    <td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/mon-hoc?action=edit&id=${mh.id}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/mon-hoc?action=delete&id=${mh.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Xóa môn ${mh.tenMon}?')">🗑️ Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty monHocs}">
                <tr><td colspan="5" style="text-align:center; color:var(--text-muted);">Không có dữ liệu</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="../footer.jsp"/>
