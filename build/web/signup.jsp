<%-- 
    Document   : signup.jsp
    Created on : Mar 8, 2024, 12:06:14 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>sign up Form</title>
    </head>
    <body>
     <form action="signup" method="post" class="form-signup">
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign up</h1>

            <p class="text-danger"> ${err} </p>
            <p class="text-danger"> ${er} </p>

            <input name="user" type="text" id="user-name" class="form-control" placeholder="User name" required="" autofocus="">
            <input name="pass" type="password" id="user-pass" class="form-control" placeholder="Password" required autofocus="">
            <input name="repass" type="password" id="user-repeatpass" class="form-control" placeholder="Repeat Password" required autofocus="">

            <button class="btn btn-primary btn-block" type="submit"><i class="fas fa-user-plus"></i> Sign Up</button>
            <a href="Login.jsp" id="cancel_signup"><i class="fas fa-angle-left"></i> Back</a>
        </form>
    </body>
</html>
