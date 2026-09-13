<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle != null ? pageTitle : 'Cổng Khách Hàng | Lab 16 Hệ Thống Đa Nền Tảng'}</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="navbar">
    <div class="navbar-container">
        <a href="${pageContext.request.contextPath}/products" class="brand">
            <div class="brand-icon">🛒</div>
            <div>
                <span>TechMart</span>
                <span class="brand-badge">Khách hàng</span>
            </div>
        </a>

        <nav>
            <ul class="nav-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/products" class="nav-link ${activeMenu == 'products' ? 'active' : ''}">
                        <span>📦</span> Sản phẩm
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/cart" class="nav-link ${activeMenu == 'cart' ? 'active' : ''}">
                        <span>🛍️</span> Giỏ hàng
                        <c:if test="${sessionScope.cartCount != null && sessionScope.cartCount > 0}">
                            <span class="cart-badge">${sessionScope.cartCount}</span>
                        </c:if>
                    </a>
                </li>

                <c:choose>
                    <c:when test="${sessionScope.currentUser != null}">
                        <li>
                            <a href="${pageContext.request.contextPath}/orders" class="nav-link ${activeMenu == 'orders' ? 'active' : ''}">
                                <span>📋</span> Đơn của tôi
                            </a>
                        </li>
                        <li>
                            <div class="user-tag">
                                <div class="user-avatar">👤</div>
                                <span>${sessionScope.currentUser.fullName()}</span>
                            </div>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary btn-sm" title="Đăng xuất">
                                Đăng xuất ➔
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary btn-sm">
                                Đăng nhập
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-sm">
                                Đăng ký
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</header>

<main class="main-content">
