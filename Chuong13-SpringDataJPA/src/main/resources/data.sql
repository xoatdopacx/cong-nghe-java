-- ============================================
-- Lab 13 - Dữ liệu mẫu ban đầu cho H2 Database
-- Sinh viên: Nguyễn Văn Hùng - MSV: 20230752
-- ============================================

-- Bảng students (tự động tạo bởi Hibernate từ @Entity Student)
INSERT INTO students (student_code, full_name, email, class_name, phone_number, address) VALUES ('20230752', 'Nguyễn Văn Hùng', 'hung@eaut.edu.vn', 'DCCNTT 14.2', '0912345678', 'Hà Nội');
INSERT INTO students (student_code, full_name, email, class_name, phone_number, address) VALUES ('20230753', 'Trần Thị Mai', 'mai@eaut.edu.vn', 'DCCNTT 14.2', '0987654321', 'Hải Phòng');
INSERT INTO students (student_code, full_name, email, class_name, phone_number, address) VALUES ('20230754', 'Lê Văn Nam', 'nam@eaut.edu.vn', 'DCCNTT 14.1', '0901234567', 'Đà Nẵng');
INSERT INTO students (student_code, full_name, email, class_name, phone_number, address) VALUES ('20230755', 'Phạm Thị Lan', 'lan@eaut.edu.vn', 'DCCNTT 14.1', '0978123456', 'Hà Nội');
INSERT INTO students (student_code, full_name, email, class_name, phone_number, address) VALUES ('20230756', 'Hoàng Minh Tuấn', 'tuan@eaut.edu.vn', 'DCKTPM 14.1', '0965432100', 'Nghệ An');

-- Bảng courses (Bài 8: Entity Course)
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3242', 'Công nghệ Java', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3220', 'Lập trình Web', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT1010', 'Nhập môn lập trình', 4);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT2030', 'Cơ sở dữ liệu', 3);
INSERT INTO courses (course_code, course_name, credits) VALUES ('IT3050', 'Phát triển ứng dụng di động', 3);
