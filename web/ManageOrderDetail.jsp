<%-- 
    Document   : ManagerProduct
    Created on : Dec 28, 2020, 5:19:02 PM
    Author     : trinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">    
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Store</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            img{
                width: 200px;
                height: 120px;
            }
            
            .left-nav {
                position: absolute;
                
                .left-nav__title {
                    background-color: #435D7D;
                    padding: 8px;
                    color: white;
                    border-radius: 2px
                }
                
                .left-nav__item {
                    display: block;
                    text-decoration: none;
                    padding: 8px;
                    color: black;
                    font-size: 14px;
                }
                
                .left-nav__item:hover {
                    background-color: #ccc;
                }
            }
        </style>
    <body>
        <div class="left-nav">
            <h3 class="left-nav__title">Manage Store</h3>
            <a href="/vnpay_jsp/managercontrol" class="left-nav__item">Product</a>
            <a href="/vnpay_jsp/manage-user" class="left-nav__item">User</a>
            <c:if test= "${sessionScope.acc.role == 'ad'}" >
                <a href="/vnpay_jsp/report-monthly" class="left-nav__item">Report</a>
            </c:if>
        </div>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2><b>Order Detail (Order ID: <%=request.getParameter("id")%>)</b></h2>
                        </div>
                        <div class="col-sm-6 text-right">
                            <a href="homecontroll" class="btn btn-primary"><i class="material-icons">home</i>Home</a>
                            		
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>
                                <span class="custom-checkbox">
                                    <input type="checkbox" id="selectAll">
                                    <label for="selectAll"></label>
                                </span>
                            </th>
                            <th>Order Detail ID</th>
                            <th>Total Price</th>
                            <th>Quantity</th>
                            <th>Product Name</th>
                            <th>Product Image</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listOrderDetail}" var="o">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="checkbox1" name="options[]" value="1">
                                        <label for="checkbox1"></label>
                                    </span>
                                </td>
                                <td>${o.getDetailId()}</td>
                                <td>${o.getPrice()}</td>
                                <td>${o.getQuantity()}</td>
                                <td>${o.getProduct().getName()}</td>
                                <td>
                                    <img src="${o.getProduct().getImage()}" alt="alt"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
           
            </div>
        </div>
        <!-- Edit Modal HTML -->
        <div id="addUserModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="add-user" method="POST">
                        <div class="modal-header">
                            <h4 class="modal-title">Add User</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">					
                            <div class="form-group">
                                <label>Username</label>
                                <input name="username" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input name="password" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Role</label>
                                <select name="role" class="form-select" aria-label="Default select example">
                                    <option value="us">Normal user</option>
                                    <option value="st">Staff</option>
                                    <option value="ad">Administrator</option>
                                </select>
                            </div>  
                            <div class="form-group">
                                <label>Name</label>
                                <input name="name" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Birthday</label>
                                <input name="birthdate" type="date" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Phone number</label>
                                <input name="phone" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Email Address</label>
                                <input name="email" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Address</label>
                                <textarea name="address" class="form-control" required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-success" value="Add">
                        </div>
                    </form>
                </div>
            </div>
        </div>
  
        <div id="editUserModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="edit-user" method="POST">
                        <div class="modal-header">
                            <h4 class="modal-title">Edit User</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>ID</label>
                                <input name="id" type="text" class="form-control" required readonly id="edit-id">
                            </div>
                            <div class="form-group">
                                <label>Name</label>
                                <input name="name" type="text" class="form-control" required  id="edit-name">
                            </div>
                            <div class="form-group">
                                <label>Birthday</label>
                                <input name="birthdate" type="date" class="form-control" required  id="edit-birthdate">
                            </div>
                            <div class="form-group">
                                <label>Phone number</label>
                                <input name="phone" type="text" class="form-control" required  id="edit-phone">
                            </div>
                            <div class="form-group">
                                <label>Email Address</label>
                                <input name="email" type="text" class="form-control" required  id="edit-email">
                            </div>
                            <div class="form-group">
                                <label>Address</label>
                                <textarea name="address" class="form-control" required  id="edit-address"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-success" value="Confirm Edit">
                        </div>
                    </form>
                </div>
            </div>
        </div>
       
        <div id="deleteEmployeeModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form>
                        <div class="modal-header">						
                            <h4 class="modal-title">Delete Product</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">					
                            <p>Are you sure you want to delete these Records?</p>
                            <p class="text-warning"><small>This action cannot be undone.</small></p>
                        </div>
                        <div class="modal-footer">      
                           <input type="button" class="btn btn-default" onclick="window.location.href='ManagerProduct.jsp';" value="Cancel">

                            <input type="submit" class="btn btn-danger" value="Delete">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/manager.js" type="text/javascript"></script>
        <script>
            function handleEditId(id, name, birthdate, phone, email, address) {
                document.querySelector("#edit-id").value = id;
                document.querySelector("#edit-name").value = name;
                document.querySelector("#edit-birthdate").value = birthdate;
                document.querySelector("#edit-phone").value = phone;
                document.querySelector("#edit-email").value = email;
                document.querySelector("#edit-address").value = address;
            }
            function showMess(id) {
                var option = confirm("Are you sure to delete " + id);
                if (option === true) {
                    window.location.href = 'delete-user?id=' + id;
                }
            }
        </script>
    </body>
</html>