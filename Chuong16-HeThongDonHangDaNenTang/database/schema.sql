-- ============================================
-- Lab 16: Hệ thống xử lý đơn hàng đa nền tảng
-- File: schema.sql - Lược đồ cơ sở dữ liệu dùng chung
-- Sinh viên: Nguyễn Văn Hùng - MSV: 20230752
-- ============================================

DROP DATABASE IF EXISTS java_integrated_lab;
CREATE DATABASE java_integrated_lab
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE java_integrated_lab;

-- Bảng người dùng hệ thống
CREATE TABLE users (
    id         BIGINT        PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50)   NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name  VARCHAR(100)  NOT NULL,
    role       VARCHAR(20)   NOT NULL COMMENT 'CUSTOMER|WAREHOUSE|MANAGER|ADMIN',
    enabled    BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Bảng sản phẩm (version cho optimistic locking)
CREATE TABLE products (
    id      BIGINT         PRIMARY KEY AUTO_INCREMENT,
    code    VARCHAR(30)    NOT NULL UNIQUE,
    name    VARCHAR(150)   NOT NULL,
    price   DECIMAL(15,2)  NOT NULL,
    stock   INT            NOT NULL DEFAULT 0,
    active  BOOLEAN        NOT NULL DEFAULT TRUE,
    version INT            NOT NULL DEFAULT 0
);

-- Bảng đơn hàng
CREATE TABLE orders (
    id           BIGINT         PRIMARY KEY AUTO_INCREMENT,
    customer_id  BIGINT         NOT NULL,
    created_at   DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(15,2)  NOT NULL DEFAULT 0,
    status       VARCHAR(20)    NOT NULL COMMENT 'PENDING|PROCESSING|READY|SHIPPING|COMPLETED|CANCELLED',
    note         VARCHAR(500),
    version      INT            NOT NULL DEFAULT 0,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES users(id)
);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_items (
    id          BIGINT         PRIMARY KEY AUTO_INCREMENT,
    order_id    BIGINT         NOT NULL,
    product_id  BIGINT         NOT NULL,
    quantity    INT            NOT NULL,
    unit_price  DECIMAL(15,2)  NOT NULL,
    subtotal    DECIMAL(15,2)  NOT NULL,
    CONSTRAINT fk_items_order   FOREIGN KEY (order_id)   REFERENCES orders(id),
    CONSTRAINT fk_items_product FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng lịch sử thay đổi trạng thái
CREATE TABLE order_status_history (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    order_id    BIGINT       NOT NULL,
    old_status  VARCHAR(20),
    new_status  VARCHAR(20)  NOT NULL,
    changed_by  BIGINT       NOT NULL,
    changed_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    platform    VARCHAR(30)  NOT NULL COMMENT 'JAKARTA_EE|JAVA_SWING|SPRING_BOOT',
    note        VARCHAR(500),
    CONSTRAINT fk_history_order FOREIGN KEY (order_id)   REFERENCES orders(id),
    CONSTRAINT fk_history_user  FOREIGN KEY (changed_by) REFERENCES users(id)
);

-- Index hiệu năng
CREATE INDEX idx_orders_status   ON orders(status);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_history_order   ON order_status_history(order_id);
CREATE INDEX idx_products_code   ON products(code);
