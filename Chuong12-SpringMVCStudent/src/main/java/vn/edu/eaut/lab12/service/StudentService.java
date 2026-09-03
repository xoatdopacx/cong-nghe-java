package vn.edu.eaut.lab12.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bài 2, 6, 7, 8, 9, 10: Service quản lý dữ liệu sinh viên trong bộ nhớ (In-Memory).
 */
@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    public StudentService() {
        // Dữ liệu mẫu ban đầu
        save(new Student(null, "20230752", "Nguyễn Văn Hùng", "hung@eaut.edu.vn", "DCCNTT 14.2", "0912345678", "Hà Nội"));
        save(new Student(null, "20230753", "Trần Thị Mai", "mai@eaut.edu.vn", "DCCNTT 14.2", "0987654321", "Hải Phòng"));
        save(new Student(null, "20230754", "Lê Văn Nam", "nam@eaut.edu.vn", "DCCNTT 14.1", "0901234567", "Đà Nẵng"));
        save(new Student(null, "20230755", "Phạm Thị Lan", "lan@eaut.edu.vn", "DCCNTT 14.1", "0978123456", "Hà Nội"));
        save(new Student(null, "20230756", "Hoàng Minh Tuấn", "tuan@eaut.edu.vn", "DCKTPM 14.1", "0965432100", "Nghệ An"));
        save(new Student(null, "20230757", "Vũ Thị Hương", "huong@eaut.edu.vn", "DCCNTT 14.2", "0934567890", "Thanh Hóa"));
    }

    /**
     * Bài 3: Lấy toàn bộ danh sách sinh viên.
     */
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    /**
     * Bài 6: Tìm sinh viên theo ID.
     */
    public Optional<Student> findById(Long id) {
        if (id == null) return Optional.empty();
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    /**
     * Bài 4 & 7: Lưu sinh viên (Thêm mới nếu id null, Cập nhật nếu id đã tồn tại).
     */
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
            students.add(student);
            return student;
        } else {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    return student;
                }
            }
            students.add(student);
            return student;
        }
    }

    /**
     * Bài 8: Xóa sinh viên theo ID.
     */
    public boolean deleteById(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }

    /**
     * Bài 9: Tìm kiếm sinh viên theo từ khóa (Mã SV, Họ tên, Lớp).
     */
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String term = keyword.trim().toLowerCase();
        return students.stream()
                .filter(s -> (s.getStudentCode() != null && s.getStudentCode().toLowerCase().contains(term))
                          || (s.getFullName() != null && s.getFullName().toLowerCase().contains(term))
                          || (s.getClassName() != null && s.getClassName().toLowerCase().contains(term))
                          || (s.getEmail() != null && s.getEmail().toLowerCase().contains(term)))
                .collect(Collectors.toList());
    }

    /**
     * Bài 10: Kiểm tra mã sinh viên đã tồn tại chưa (phục vụ validation chống trùng mã).
     * @param studentCode mã sinh viên cần kiểm tra
     * @param excludeId ID sinh viên bỏ qua khi cập nhật (có thể null nếu thêm mới)
     */
    public boolean existsByStudentCode(String studentCode, Long excludeId) {
        if (studentCode == null || studentCode.trim().isEmpty()) return false;
        String code = studentCode.trim().toLowerCase();
        return students.stream()
                .anyMatch(s -> s.getStudentCode().equalsIgnoreCase(code)
                            && (excludeId == null || !s.getId().equals(excludeId)));
    }

    public long count() {
        return students.size();
    }
}
