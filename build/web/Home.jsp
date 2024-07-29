<%-- 
    Document   : index
    Created on : Oct 11, 2023, 6:31:46 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Product"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link href="css/category.css" rel="stylesheet" type="text/css"/>

        <script src="js/checkLoginToShopping.js"></script>
        <script>
            var isLoggedIn = ${sessionScope.acc != null};
        </script>



    </head>
    <body>
        <jsp:include page="Navbar.jsp"></jsp:include>
        <%
            String currentURL = request.getRequestURI();
            String queryString = request.getQueryString();
            boolean hideTopBlock = false;

            // Kiểm tra nếu đường dẫn chứa "/search" hoặc là trang search
          

            // Kiểm tra nếu có tham số page hoặc cid trong chuỗi truy vấn
            if (queryString != null && (queryString.startsWith("page=") || queryString.startsWith("cid="))) {
                hideTopBlock = true;
            }
        %>

        <div class="top-block" <% if (hideTopBlock) { %> style="display: none;" <% }%>>
            <img src="https://www.hamiltonwatch.com/media/wysiwyg/INT_HQ/2024-Homepage/Q3-Homepage/July/Hero-mecha.jpg" alt="Top Block Image">
            <div class="text-overlay">
                <h1>THE ORIGINAL FIELD</h1>
                <h1 class="text-center">  WATCH</h1>
                <br>
                <h5>New colors for a time-tested icon: three new </h5>
                <h5 class="text-center">Khaki Field Mechanical references</h5>
            </div>
        </div>















        <div class="container mt-4 mb-4">
            <div class="row">
                <div class="col text-center"> 
                    <h3> WRIST WATCHES </h3>
                    </ol>
                </div>
            </div>
        </div>




        <div class="col-sm-12">
            <div class="row">
                <c:forEach items="${listP}" var="o">
                    <div class="col-12 col-md-6 col-lg-4 mb-4">
                        <div class="card position-relative">
                            <a href="detail?pid=${o.id}">
                                <img class="card-img-top" src="${o.image}" alt="Card image cap">
                            </a>

                            <c:if test="${o.quantity <= 5}">
                                <img class="sold-out-overlay" src="icons8-sold-out-64.png" alt="Sold Out" >
                            </c:if>

                            <div class="card-body">
                                <h4 class="card-title show_txt">
                                    <c:choose>
                                        <c:when test="${o.quantity > 5}">
                                            <a href="detail?pid=${o.id}" title="View Product" style="color: #000000">${o.name}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #000000">${o.name}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </h4>

                                <c:if test="${o.quantity > 5}">
                                    <div class="row">
                                        <div class="col">
                                            <p class="btn btn-block price-button" onclick="checkLoginClickPrice(${o.id})">
                                                <span class="price-text">${o.price} $</span>
                                            </p>
                                        </div>
                                        <div class="col">
                                            <a href="#" class="btn btn-block add-to-cart-button" onclick="checkLoginAndAddToCart(${o.id})">Add To Cart</a>
                                        </div>
                                    </div>

                                </c:if>



                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>










        <div class="col-sm-12">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <c:choose>
                        <c:when test="${page > 1}">
                            <a class="page-link" href="homecontroll?page=${page - 1}" style="color: black">
                                <i class="fas fa-chevron-left"></i>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span class="page-link disabled">
                                <i class="fas fa-chevron-left"></i>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </li>
                <c:forEach begin="1" end="${num}" var="i">
                    <li class="page-item ${page == i ? 'active' : ''}">
                        <c:choose>
                            <c:when test="${page == i}">
                                <span class="page-link">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="homecontroll?page=${i}" style="color: black">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <c:choose>
                        <c:when test="${page < num}">
                            <a class="page-link" href="homecontroll?page=${page + 1}" style="color: black">
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span class="page-link disabled">
                                <i class="fas fa-chevron-right"></i>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>




    </div>
</div>
<div class="row justify-content-center">
    <div class="col-md-6 text-center"><h4 style="color: #333333">DISCOVER</h4></div>
</div>

<!--            phân loại-->
<div class="row">
    <div class="col-md-3">
        <div class="card mb-4 shadow-sm">
            <a href="category?cid=1">
                <img src="https://www.hamiltonwatch.com/media/wysiwyg/categories_slider/mens-watches/default.jpg" class="card-img-top" alt="Men">
                <div class="card-body">
                    <p class="card-text">FOR MEN</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card mb-4 shadow-md">
            <a href="category?cid=2">
                <img src="https://www.hamiltonwatch.com/media/wysiwyg/categories_slider/women-watches/default.jpg" class="card-img-top" alt="Women">
                <div class="card-body">
                    <p class="card-text">FOR WOMEN</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card mb-4 shadow-md">
            <a href="category?cid=3">
                <img src="https://www.hamiltonwatch.com/media/wysiwyg/categories_slider/cinema-watches/default.jpg" class="card-img-top" alt="Cinemas">
                <div class="card-body">
                    <p class="card-text">CINEMA</p>
                </div>
            </a>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card mb-4 shadow-md">
            <a href="category?cid=4">
                <img src="https://www.hamiltonwatch.com/media/wysiwyg/categories_slider/pilot-watches/default.jpg" class="card-img-top" alt="Pilot type">
                <div class="card-body">
                    <p class="card-text">PILOT</p>
                </div>
            </a>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="register-block row">
        <div class="register-text col-md-6">
            <h2>REGISTER MY WATCH</h2>
            <p>You can now register your Hamilton watches into your Hamilton account. Sign up, add your watch to your collection and enjoy the full Tissot Experience. Want to personalize your watch with a compatible strap? Want to know about your warranty status? Want to know how to get the most of your watch thanks to your user guide? Want to access your past order and personal information? All you need is accessible in your account.</p>
            <button class="register-button" onclick="window.location.href = 'signup.jsp'">REGISTER</button>
        </div>
        <div class="watch-image col-md-6">
            <img src="https://www.tissotwatches.com/media/content_push/RMW_Image_1.jpg?im=Resize=(1784,1338)" alt="Watches">
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>




