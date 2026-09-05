package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab13.entity.Student;

import java.util.List;

/**
 * Bài 3: StudentRepository kế thừa JpaRepository với method query tìm kiếm.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** Bài 7: Tìm kiếm theo họ tên (không phân biệt hoa thường) */
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    /** Bài 3: Tìm theo mã sinh viên */
    List<Student> findByStudentCodeContainingIgnoreCase(String code);

    /** Tìm theo lớp */
    List<Student> findByClassNameContainingIgnoreCase(String className);

    /** Kiểm tra trùng mã sinh viên */
    boolean existsByStudentCode(String studentCode);

    /** Kiểm tra trùng mã sinh viên nhưng loại trừ 1 ID (khi sửa) */
    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);
}
