package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Lớp lưu trữ danh sách sinh viên trong bộ nhớ (Store trong MVC).
 * Hỗ trợ CRUD, tìm kiếm và thống kê (Bài 3, 6, 7, 8, 10, 12).
 */
public class StudentStore {

    private static final List<Student> students = Collections.synchronizedList(new ArrayList<>());

    static {
        // Dữ liệu mẫu ban đầu (Bài 3)
        students.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        students.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
    }

    /** Lấy tất cả sinh viên */
    public static List<Student> findAll() {
        return new ArrayList<>(students);
    }

    /** Thêm sinh viên mới */
    public static void add(Student student) {
        students.add(student);
    }

    /** Tìm sinh viên theo mã */
    public static Student findById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /** Cập nhật thông tin sinh viên (Bài 8) */
    public static boolean update(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updated.getId())) {
                students.set(i, updated);
                return true;
            }
        }
        return false;
    }

    /** Xóa sinh viên theo mã (Bài 7) */
    public static boolean delete(String id) {
        return students.removeIf(s -> s.getId().equals(id));
    }

    /** Tìm kiếm sinh viên theo họ tên, không phân biệt hoa/thường (Bài 6) */
    public static List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /** Đếm tổng số sinh viên (Bài 10) */
    public static int count() {
        return students.size();
    }

    /** Đếm số sinh viên theo từng lớp (Bài 10) */
    public static Map<String, Long> countByClassName() {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.counting()));
    }

    /** Xóa tất cả và thay bằng danh sách mới (Bài 12 - Listener init) */
    public static void replaceAll(List<Student> newStudents) {
        students.clear();
        students.addAll(newStudents);
    }
}
