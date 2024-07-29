<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>





<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Menu Page</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    <link href="css/myStyle.css" rel="stylesheet" type="text/css"/>

</head>
<!--begin of menu-->
<jsp:include page="Navbar.jsp"></jsp:include>



<div class="video-wrapper" style="background-color: rgba(0, 0, 0, 0.5);">
    <video autoplay loop muted width="100%" height="30%">
        <source src="NEW Hamilton Dune watches inspired by the prop they designed for the film.mp4" type="video/mp4">
    </video>
</div>
<div class="pagebuilder-collage-content">
    <div class="content-wrapper"  style="background-color: transparent; color: #ffffff;">
        <p class="banner-text" style="font-size: 50px;"><strong>HAMILTON X DUNE</strong></p>
        <p style="font-size: 18px;">Dive into the realm of Dune Part Two <br> with Hamilton's unique creations</p>
            <c:url var="homeURL" value="homecontroll" />
            <c:choose>
                <c:when test="${empty sessionScope.acc}">
                <button onclick="window.location.href = '${homeURL}'" class="pagebuilder-banner-button pagebuilder-button-primary pagebuilder-button-transparent" data-element="button" data-pb-style="GKETQ8W" style="font-size: 24px; background-color: transparent; color: #ffffff; border: 2px solid #ffffff; padding: 10px 20px;">DISCOVER</button>
            </c:when>
        </c:choose>

    </div>
</div>























<!--end of menu-->
