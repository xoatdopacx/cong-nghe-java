<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${not empty editStudent ? 'Sửa' : 'Thêm'} sinh viên - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f4f6f9; padding: 40px; }
        .container { max-width: 550px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 40px; }
        h2 { color: #2c3e50; margin-bottom: 25px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 6px; color: #34495e; font-weight: 600; font-size: 14px; }
        .form-group input { width: 100%; padding: 10px 14px; border: 2px solid #ecf0f1; border-radius: 6px; font-size: 14px; }
        .form-group input:focus { outline: none; border-color: #667eea; }
        .form-group input[readonly] { background: #ecf0f1; color: #7f8c8d; }
        .btn { padding: 12px 24px; border: none; border-radius: 6px; font-size: 14px; font-weight: 600; cursor: pointer; text-decoration: none; display: inline-block; margin-right: 10px; }
        .btn-save { background: #27ae60; color: #fff; }
        .btn-save:hover { background: #219a52; }
        .btn-back { background: #95a5a6; color: #fff; }
        .btn-back:hover { background: #7f8c8d; }
        .nav { margin-bottom: 20px; }
        .nav a { color: #667eea; text-decoration: none; font-weight: 500; }
    </style>
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="${pageContext.request.contextPath}/dashboard">← Dashboard</a> |
        <a href="${pageContext.request.contextPath}/students">Danh sách SV</a>
    </div>

    <h2>${not empty editStudent ? '✏️ Sửa thông tin sinh viên' : '➕ Thêm sinh viên mới'}</h2>

    <form action="${pageContext.request.contextPath}/students" method="post">
        <%-- Bài 8: Nếu đang sửa, đánh dấu editMode --%>
        <c:if test="${not empty editStudent}">
            <input type="hidden" name="editMode" value="true">
        </c:if>

        <div class="form-group">
            <label for="id">Mã sinh viên:</label>
            <input type="text" id="id" name="id" value="${editStudent.id}"
                   ${not empty editStudent ? 'readonly' : ''} required
                   placeholder="Ví dụ: SV008">
        </div>

        <div class="form-group">
            <label for="name">Họ tên:</label>
            <input type="text" id="name" name="name" value="${editStudent.name}" required
                   placeholder="Ví dụ: Nguyễn Văn A">
        </div>

        <div class="form-group">
            <label for="className">Lớp:</label>
            <input type="text" id="className" name="className" value="${editStudent.className}" required
                   placeholder="Ví dụ: DCCNTT12">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${editStudent.email}" required
                   placeholder="Ví dụ: nguyenvana@eaut.edu.vn">
        </div>

        <button type="submit" class="btn btn-save">
            ${not empty editStudent ? '💾 Cập nhật' : '💾 Lưu sinh viên'}
        </button>
        <a href="${pageContext.request.contextPath}/students" class="btn btn-back">Hủy</a>
    </form>
</div>
</body>
</html>
