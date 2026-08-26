-- ==========================================================
-- BỘ MÔN CÔNG NGHỆ PHẦN MỀM - KHOA CNTT - ĐẠI HỌC CÔNG NGHỆ ĐÔNG Á
-- CÔNG NGHỆ JAVA (IT3242) - LAB 10
-- CƠ SỞ DỮ LIỆU: lab10_secured
-- Sinh viên: Nguyễn Văn Hùng - MSV: 20230752 - Lớp: DCCNTT 14.2
-- ==========================================================

CREATE DATABASE IF NOT EXISTS `lab10_secured` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `lab10_secured`;

-- 1. Bảng users (Tài khoản & Phân quyền)
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `role` VARCHAR(20) NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng lop_hoc (Lớp học)
DROP TABLE IF EXISTS `lop_hoc`;
CREATE TABLE `lop_hoc` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `ma_lop` VARCHAR(20) NOT NULL UNIQUE,
    `ten_lop` VARCHAR(100) NOT NULL,
    `khoa` VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Bảng sinh_vien (Sinh viên)
DROP TABLE IF EXISTS `sinh_vien`;
CREATE TABLE `sinh_vien` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `ma_sv` VARCHAR(20) NOT NULL UNIQUE,
    `ho_ten` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100),
    `dien_thoai` VARCHAR(15),
    `dia_chi` VARCHAR(255),
    `lop_hoc_id` BIGINT,
    CONSTRAINT `fk_sinhvien_lophoc` FOREIGN KEY (`lop_hoc_id`) REFERENCES `lop_hoc` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Bảng mon_hoc (Môn học)
DROP TABLE IF EXISTS `mon_hoc`;
CREATE TABLE `mon_hoc` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `ma_mon` VARCHAR(20) NOT NULL UNIQUE,
    `ten_mon` VARCHAR(100) NOT NULL,
    `so_tin_chi` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Bảng diem (Điểm số)
DROP TABLE IF EXISTS `diem`;
CREATE TABLE `diem` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sinh_vien_id` BIGINT NOT NULL,
    `mon_hoc_id` BIGINT NOT NULL,
    `diem_so` DOUBLE,
    `ghi_chu` VARCHAR(255),
    CONSTRAINT `fk_diem_sinhvien` FOREIGN KEY (`sinh_vien_id`) REFERENCES `sinh_vien` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_diem_monhoc` FOREIGN KEY (`mon_hoc_id`) REFERENCES `mon_hoc` (`id`) ON DELETE CASCADE,
    CONSTRAINT `uq_sinhvien_monhoc` UNIQUE (`sinh_vien_id`, `mon_hoc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Bảng san_pham (Sản phẩm)
DROP TABLE IF EXISTS `san_pham`;
CREATE TABLE `san_pham` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `ma_sp` VARCHAR(20) NOT NULL UNIQUE,
    `ten_sp` VARCHAR(150) NOT NULL,
    `don_gia` DOUBLE,
    `so_luong` INT,
    `mo_ta` TEXT,
    `danh_muc` VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================================
-- DỮ LIỆU MẪU (SEED DATA)
-- ==========================================================

-- Dữ liệu tài khoản (ADMIN, STAFF, USER)
INSERT INTO `users` (`email`, `password`, `full_name`, `role`, `active`) VALUES
('admin@eaut.edu.vn', 'admin123', 'Quản trị viên', 'ADMIN', 1),
('staff@eaut.edu.vn', 'staff123', 'Nhân viên Đào tạo', 'STAFF', 1),
('user@eaut.edu.vn', 'user123', 'Nguyễn Văn Hùng', 'USER', 1);

-- Dữ liệu lớp học
INSERT INTO `lop_hoc` (`ma_lop`, `ten_lop`, `khoa`) VALUES
('DCCNTT14.2', 'DCCNTT 14.2', 'Công nghệ thông tin'),
('DCCNTT14.1', 'DCCNTT 14.1', 'Công nghệ thông tin'),
('DCKTPM14.1', 'DCKTPM 14.1', 'Kỹ thuật phần mềm');

-- Dữ liệu sinh viên
INSERT INTO `sinh_vien` (`ma_sv`, `ho_ten`, `email`, `dien_thoai`, `dia_chi`, `lop_hoc_id`) VALUES
('20230752', 'Nguyễn Văn Hùng', 'hung@eaut.edu.vn', '0912345678', 'Hà Nội', 1),
('20230753', 'Trần Thị Mai', 'mai@eaut.edu.vn', '0987654321', 'Hải Phòng', 1),
('20230754', 'Lê Văn Nam', 'nam@eaut.edu.vn', '0901234567', 'Đà Nẵng', 2),
('20230755', 'Phạm Thị Lan', 'lan@eaut.edu.vn', '0978123456', 'Hà Nội', 2),
('20230756', 'Hoàng Minh Tuấn', 'tuan@eaut.edu.vn', '0965432100', 'Nghệ An', 3),
('20230757', 'Vũ Thị Hương', 'huong@eaut.edu.vn', '0934567890', 'Thanh Hóa', 1);

-- Dữ liệu môn học
INSERT INTO `mon_hoc` (`ma_mon`, `ten_mon`, `so_tin_chi`) VALUES
('IT3242', 'Công nghệ Java', 3),
('IT3220', 'Lập trình Web', 3),
('IT1010', 'Nhập môn lập trình', 4),
('IT2030', 'Cơ sở dữ liệu', 3);

-- Dữ liệu điểm
INSERT INTO `diem` (`sinh_vien_id`, `mon_hoc_id`, `diem_so`, `ghi_chu`) VALUES
(1, 1, 9.0, 'Xuất sắc'),
(1, 2, 8.5, 'Giỏi'),
(2, 1, 7.5, 'Khá'),
(2, 3, 6.0, 'Trung bình'),
(3, 1, 8.0, 'Giỏi'),
(3, 4, 7.0, 'Khá'),
(4, 2, 5.5, 'Trung bình'),
(5, 1, 9.5, 'Xuất sắc');

-- Dữ liệu sản phẩm
INSERT INTO `san_pham` (`ma_sp`, `ten_sp`, `don_gia`, `so_luong`, `mo_ta`, `danh_muc`) VALUES
('SP001', 'Laptop Dell XPS 15', 32000000.0, 10, 'Laptop cao cấp màn hình OLED', 'Laptop'),
('SP002', 'iPhone 15 Pro Max', 29990000.0, 25, 'Điện thoại thông minh cao cấp', 'Điện thoại'),
('SP003', 'Samsung Galaxy S24', 22990000.0, 15, 'Flagship mới nhất của Samsung', 'Điện thoại'),
('SP004', 'MacBook Air M3', 28990000.0, 8, 'MacBook mỏng nhẹ chip Apple M3', 'Laptop'),
('SP005', 'Tai nghe Sony WH-1000XM5', 7490000.0, 30, 'Tai nghe chống ồn chủ động tốt nhất', 'Phụ kiện');
