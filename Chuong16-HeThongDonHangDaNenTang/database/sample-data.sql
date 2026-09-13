-- ============================================
-- Lab 16: Dữ liệu mẫu kiểm thử
-- File: sample-data.sql
-- ============================================
USE java_integrated_lab;

-- Tài khoản người dùng (mật khẩu BCrypt của '123456')
INSERT INTO users (username, password_hash, full_name, role) VALUES
('customer01', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Nguyễn Văn Khách', 'CUSTOMER'),
('customer02', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Trần Thị Mua', 'CUSTOMER'),
('warehouse01', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Trần Văn Kho', 'WAREHOUSE'),
('warehouse02', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Lê Văn Kho', 'WAREHOUSE'),
('manager01', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Lê Văn Quản Lý', 'MANAGER'),
('admin01', '$2a$10$SC21XRuGxhngBJBNgCZkW.0TeJ4BRbDIXiE8Oqx1C.9GQFxHdMKDy', 'Quản Trị Hệ Thống', 'ADMIN');

-- Sản phẩm mẫu
INSERT INTO products (code, name, price, stock) VALUES
('SP001', 'Bàn phím cơ', 850000, 20),
('SP002', 'Chuột không dây', 350000, 30),
('SP003', 'Tai nghe Bluetooth', 650000, 15),
('SP004', 'Webcam Full HD', 720000, 10),
('SP005', 'Ổ cứng SSD 1TB', 1850000, 8),
('SP006', 'Màn hình 24 inch', 3200000, 5),
('SP007', 'USB Hub 7 cổng', 280000, 25);
