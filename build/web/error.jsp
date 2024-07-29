<%-- 
    Document   : error
    Created on : Mar 13, 2024, 4:22:25 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
            <style>
        body {
            text-align: center;
            margin-top: 50px;
        }

        h1 {
            color: red;
        }

        form {
            display: inline-block;
            margin-top: 20px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
    </style>
    </head>
    <body>
        <h1>Please Buying!</h1>
            <form action="homecontroll" method="get">
        <input type="submit" value="Home">
    </form>
    </body>
</html>
