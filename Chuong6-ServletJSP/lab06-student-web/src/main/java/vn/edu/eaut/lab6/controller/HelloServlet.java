package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Bài 1: Servlet hiển thị thông báo "Hello, Servlet - Lab 6 Công nghệ Java".
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Hello Servlet</title>");
        out.println("<style>body{font-family:Arial,sans-serif;text-align:center;margin-top:80px;background:#f4f6f9;}");
        out.println("h1{color:#2c3e50;} p{color:#7f8c8d;}</style></head>");
        out.println("<body>");
        out.println("<h1>Hello, Servlet - Lab 6 Cong nghe Java</h1>");
        out.println("<p>Servlet đang chạy trên Web Container (Tomcat 10.x)</p>");
        out.println("<p>Sinh viên: Nguyễn Văn Hùng - MSSV: 20230752</p>");
        out.println("<a href='" + request.getContextPath() + "/login.jsp'>Đăng nhập hệ thống</a>");
        out.println("</body></html>");
    }
}
