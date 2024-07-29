<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html lang="en">
    <header>  <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Login Form</title></header>


    <body>
        <div id="logreg-forms">
            <form class="form-signin" action="login" method="post">
                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>

                <p class="text-danger"> ${error} </p>

                <input value="${username}" name="user"  type="text" id="inputEmail" class="form-control" placeholder="Username" required="" autofocus="">
                <input value="${password}" name="pass"  type="password" id="inputPassword" class="form-control" placeholder="Password" required="">

                <div class="form-group form-check">
                    <input name="rem" value="1" type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Remember </label>
                </div>

                <button class="btn btn-success btn-block" type="submit"><i class="fas fa-sign-in-alt"></i> Sign in</button>

            </form>

            <button class="btn btn-primary btn-block" type="button" id="btn-signup" onclick="redirectToSignUpPage()">
                <i class="fas fa-user-plus"></i> Sign up
            </button>

            <br>
        </div>
        <div class="form-group">
            <a href="forgotPassword.jsp" class="forgot-password">Forgot password?</a>
        </div>


        <script>
            function redirectToSignUpPage() {

                window.location.href = "signup.jsp";
            }
        </script>
    </body>

</html>