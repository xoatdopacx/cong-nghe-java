package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.List;

/**
 * Bài 2, 3, 6, 7, 8: Controller xử lý CRUD sinh viên.
 * GET /students          → Hiển thị danh sách (Bài 3)
 * GET /students?search=  → Tìm kiếm sinh viên (Bài 6)
 * GET /students?action=delete&id= → Xóa sinh viên (Bài 7)
 * GET /students?action=edit&id=   → Mở form sửa (Bài 8)
 * POST /students         → Thêm hoặc cập nhật sinh viên (Bài 2, 8)
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // Bài 9: Kiểm tra quyền admin cho action quản trị
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        // Bài 7: Xóa sinh viên
        if ("delete".equals(action)) {
            if (!"admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/403.jsp");
                return;
            }
            String id = request.getParameter("id");
            StudentStore.delete(id);
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        // Bài 8: Mở form sửa sinh viên
        if ("edit".equals(action)) {
            if (!"admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/403.jsp");
                return;
            }
            String id = request.getParameter("id");
            Student student = StudentStore.findById(id);
            if (student != null) {
                request.setAttribute("editStudent", student);
            }
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        // Bài 6: Tìm kiếm sinh viên theo họ tên
        String search = request.getParameter("search");
        List<Student> students;
        if (search != null && !search.trim().isEmpty()) {
            students = StudentStore.search(search);
            request.setAttribute("searchKeyword", search);
            if (students.isEmpty()) {
                request.setAttribute("searchMessage",
                        "Không tìm thấy sinh viên nào có tên chứa \"" + search + "\"");
            }
        } else {
            students = StudentStore.findAll();
        }

        // Bài 9: Truyền role để JSP hiển thị/ẩn nút quản trị
        request.setAttribute("role", role);
        request.setAttribute("students", students);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Bài 9: Kiểm tra quyền admin cho thao tác ghi
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if (!"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/403.jsp");
            return;
        }

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String className = request.getParameter("className");
        String email = request.getParameter("email");
        String editMode = request.getParameter("editMode");

        Student student = new Student(id, name, className, email);

        // Bài 8: Nếu đang ở chế độ sửa thì update, ngược lại thêm mới
        if ("true".equals(editMode)) {
            StudentStore.update(student);
        } else {
            StudentStore.add(student);
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
