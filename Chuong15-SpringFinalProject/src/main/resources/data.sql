-- ============================================
-- Lab 15: Dữ liệu mẫu
-- ============================================

-- Sinh viên
INSERT INTO students (student_code, full_name, email, class_name) VALUES ('SV001', 'Nguyễn Văn Hùng', 'hung@eaut.edu.vn', 'DCCNTT 14.2');
INSERT INTO students (student_code, full_name, email, class_name) VALUES ('SV002', 'Trần Thị Mai', 'mai@eaut.edu.vn', 'DCCNTT 14.1');
INSERT INTO students (student_code, full_name, email, class_name) VALUES ('SV003', 'Lê Hoàng Nam', 'nam@eaut.edu.vn', 'DCCNTT 14.2');
INSERT INTO students (student_code, full_name, email, class_name) VALUES ('SV004', 'Phạm Minh Tuấn', 'tuan@eaut.edu.vn', 'DCCNTT 14.1');
INSERT INTO students (student_code, full_name, email, class_name) VALUES ('SV005', 'Hoàng Thị Lan', 'lan@eaut.edu.vn', 'DCCNTT 14.3');

-- Khóa học
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3242', 'Công nghệ Java', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3210', 'Lập trình Web', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3150', 'Cơ sở dữ liệu', 4);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3230', 'Mạng máy tính', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3100', 'Cấu trúc dữ liệu', 4);

-- Đăng ký học phần
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-01', 1, 1);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-01', 1, 2);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-02', 2, 1);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-02', 2, 3);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-03', 3, 1);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-03', 3, 4);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-04', 4, 2);
INSERT INTO enrollments (enroll_date, student_id, course_id) VALUES ('2026-09-04', 5, 5);
