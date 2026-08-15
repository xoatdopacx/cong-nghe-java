-- ============================================================
-- DATABASE CREATION SCRIPT FOR LAB 5 - MINISHOP (JAVA SWING & JDBC)
-- Tên CSDL: minishop_db
-- Trường Đại học Công nghệ Đông Á (EAUT)
-- ============================================================

CREATE DATABASE IF NOT EXISTS minishop_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE minishop_db;

-- 1. Bảng danh_muc (Bài 6)
CREATE TABLE IF NOT EXISTS danh_muc (
    ma_dm INT AUTO_INCREMENT PRIMARY KEY,
    ten_dm VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Bảng san_pham (Bài 2, 6, 7)
CREATE TABLE IF NOT EXISTS san_pham (
    ma_sp INT AUTO_INCREMENT PRIMARY KEY,
    ten_sp VARCHAR(100) NOT NULL,
    don_gia DECIMAL(12,2) NOT NULL,
    so_luong INT NOT NULL DEFAULT 0,
    ma_dm INT NULL,
    FOREIGN KEY (ma_dm) REFERENCES danh_muc(ma_dm) ON DELETE SET NULL
);

-- 3. Bảng khach_hang (Bài 3)
CREATE TABLE IF NOT EXISTS khach_hang (
    ma_kh INT AUTO_INCREMENT PRIMARY KEY,
    ten_kh VARCHAR(100) NOT NULL,
    sdt VARCHAR(10) NOT NULL,
    dia_chi VARCHAR(255)
);

-- 4. Bảng tai_khoan (Bài 10)
CREATE TABLE IF NOT EXISTS tai_khoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL -- ADMIN, NHANVIEN, KETOAN
);

-- 5. Bảng hoa_don (Bài 4, 10)
CREATE TABLE IF NOT EXISTS hoa_don (
    ma_hd INT AUTO_INCREMENT PRIMARY KEY,
    ngay_lap DATE NOT NULL,
    ma_kh INT NOT NULL,
    tong_tien DECIMAL(12,2) DEFAULT 0,
    username VARCHAR(50) NULL,
    FOREIGN KEY (ma_kh) REFERENCES khach_hang(ma_kh),
    FOREIGN KEY (username) REFERENCES tai_khoan(username) ON DELETE SET NULL
);

-- 6. Bảng chi_tiet_hoa_don (Bài 4)
CREATE TABLE IF NOT EXISTS chi_tiet_hoa_don (
    ma_hd INT NOT NULL,
    ma_sp INT NOT NULL,
    so_luong INT NOT NULL,
    don_gia DECIMAL(12,2) NOT NULL,
    thanh_tien DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (ma_hd, ma_sp),
    FOREIGN KEY (ma_hd) REFERENCES hoa_don(ma_hd) ON DELETE CASCADE,
    FOREIGN KEY (ma_sp) REFERENCES san_pham(ma_sp)
);

-- ============================================================
-- DỮ LIỆU MẪU (SEED DATA)
-- ============================================================

-- Thêm danh mục
INSERT INTO danh_muc (ten_dm) VALUES 
('Phụ kiện máy tính'),
('Thiết bị âm thanh'),
('Lưu trữ & Mạng'),
('Màn hình & Bàn ghế')
ON DUPLICATE KEY UPDATE ten_dm=VALUES(ten_dm);

-- Thêm sản phẩm
INSERT INTO san_pham(ten_sp, don_gia, so_luong, ma_dm) VALUES
('Ban phim Logitech K120', 180000, 50, 1),
('Chuot khong day Rapoo', 220000, 40, 1),
('USB Kingston 32GB', 150000, 100, 3),
('Tai nghe Sony Basic', 350000, 30, 2),
('Ban phim co RGB Gaming', 650000, 4, 1), -- Tồn kho < 5 để test cảnh báo
('Man hinh Dell 24 inch Full HD', 3200000, 15, 4),
('Webcam Logitech C920', 1250000, 2, 1)  -- Tồn kho < 5
ON DUPLICATE KEY UPDATE ten_sp=VALUES(ten_sp);

-- Thêm khách hàng
INSERT INTO khach_hang(ten_kh, sdt, dia_chi) VALUES
('Nguyen Van An', '0912345678', 'Ha Noi'),
('Tran Thi Binh', '0987654321', 'Bac Ninh'),
('Le Van Cuong', '0901111222', 'Hai Duong'),
('Pham Minh Duc', '0934567890', 'Nam Dinh')
ON DUPLICATE KEY UPDATE ten_kh=VALUES(ten_kh);

-- Thêm tài khoản người dùng (Mật khẩu đơn giản để test: 123456)
INSERT INTO tai_khoan (username, password, ho_ten, vai_tro) VALUES
('admin', '123456', 'Quan Tri Vien Main', 'ADMIN'),
('nv01', '123456', 'Nguyen Van Nhan Vien', 'NHANVIEN'),
('kt01', '123456', 'Tran Thi Ke Toan', 'KETOAN')
ON DUPLICATE KEY UPDATE ho_ten=VALUES(ho_ten);

-- Thêm một số hóa đơn mẫu để test thống kê
INSERT INTO hoa_don (ngay_lap, ma_kh, tong_tien, username) VALUES
(CURDATE(), 1, 400000, 'nv01'),
(DATE_SUB(CURDATE(), INTERVAL 1 DAY), 2, 700000, 'nv01'),
(DATE_SUB(CURDATE(), INTERVAL 3 DAY), 3, 3200000, 'admin');

INSERT INTO chi_tiet_hoa_don (ma_hd, ma_sp, so_luong, don_gia, thanh_tien) VALUES
(1, 1, 1, 180000, 180000),
(1, 2, 1, 220000, 220000),
(2, 4, 2, 350000, 700000),
(3, 6, 1, 3200000, 3200000);
