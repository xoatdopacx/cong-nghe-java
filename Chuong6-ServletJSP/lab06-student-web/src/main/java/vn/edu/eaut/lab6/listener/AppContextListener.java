package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.util.Arrays;
import java.util.List;

/**
 * Bài 5, 12: Listener ghi log khi ứng dụng khởi động/kết thúc.
 * Bài 12: Khởi tạo ít nhất 5 sinh viên mẫu khi ứng dụng chạy.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("====================================================");
        System.out.println("[AppContextListener] Ung dung Lab 6 da khoi dong!");
        System.out.println("====================================================");

        // Bài 12: Khởi tạo dữ liệu mẫu 7 sinh viên
        List<Student> sampleStudents = Arrays.asList(
                new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@eaut.edu.vn"),
                new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@eaut.edu.vn"),
                new Student("SV003", "Le Van Cuong", "DCCNTT13", "cuong@eaut.edu.vn"),
                new Student("SV004", "Pham Thi Dung", "DCCNTT13", "dung@eaut.edu.vn"),
                new Student("SV005", "Hoang Van Em", "DCCNTT12", "em@eaut.edu.vn"),
                new Student("SV006", "Nguyen Thi Fen", "DCCNTT14", "fen@eaut.edu.vn"),
                new Student("SV007", "Vo Van Giang", "DCCNTT14", "giang@eaut.edu.vn")
        );

        StudentStore.replaceAll(sampleStudents);

        // Lưu vào ServletContext để các Servlet/JSP có thể truy cập
        sce.getServletContext().setAttribute("appName", "Lab 06 - Student Web MVC");
        sce.getServletContext().setAttribute("sampleCount", sampleStudents.size());

        System.out.println("[AppContextListener] Da khoi tao " + sampleStudents.size()
                + " sinh vien mau vao StudentStore");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        int count = StudentStore.count();
        System.out.println("====================================================");
        System.out.println("[AppContextListener] Ung dung Lab 6 da dung.");
        System.out.println("[AppContextListener] Tong so sinh vien luc dung: " + count);
        System.out.println("====================================================");
    }
}
