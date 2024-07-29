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
                            <h2><b>Product</b></h2>
                        </div>
                        <div class="col-sm-6 text-right">
                            <a href="homecontroll" class="btn btn-primary"><i class="material-icons">home</i>Home</a>
                            <a href="#addWatchModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Product</span></a>
						
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
                            <th>ID</th>
                            <th>Name</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listPM}" var="o">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="checkbox1" name="options[]" value="1">
                                        <label for="checkbox1"></label>
                                    </span>
                                </td>
                                <td>${o.id}</td>
                                <td>${o.name}</td>
                                <td>
                                    <img src="${o.image}">
                                </td>
                                <td>${o.price}$</td>
                                <td>${o.quantity}</td>
                                <td>
                                    <a 
                                        onclick="handleEditId(${o.id}, `${o.name}`, `${o.image}`, `${o.price}`, `${o.title}`, `${o.description}`, `${o.quantity}`)"
                                        href="#editWatchModal"  class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                    <a style="cursor: pointer" class="delete" onclick="showMess(${o.id})"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
           
            </div>
        </div>
        <!-- Edit Modal HTML -->
        <div id="addWatchModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="addcontrol" method="POST">
                        <div class="modal-header">						
                            <h4 class="modal-title">Add Product</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">					
                            <div class="form-group">
                                <label>Name</label>
                                <input name="name" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Image</label>
                                <input name="image" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Price</label>
                                <input name="price" type="text" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Quantity</label>
                                <input name="quantity" type="number" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Title</label>
                                <textarea name="title" class="form-control" required></textarea>
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <textarea name="description" class="form-control" required></textarea>
                            </div>
                            <div class="form-group">
                                <label>Category</label>
                                <select name="category" class="form-select" aria-label="Default select example">
                                    <c:forEach items="${listCC}" var="o">
                                        <option value="${o.cid}">${o.cname}</option>
                                    </c:forEach>
                                </select>
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
  
        <div id="editWatchModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="editcontrol" method="POST">
                        <div class="modal-header">						
                            <h4 class="modal-title">Edit Product</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">	
                            <div class="form-group">
                                <label>ID</label>
                                <input name="id" type="text" class="form-control" required readonly id="edit-id">
                            </div>
                            <div class="form-group">
                                <label>Name</label>
                                <input name="name" type="text" class="form-control" required id="edit-name">
                            </div>
                            <div class="form-group">
                                <label>Image</label>
                                <input name="image" type="text" class="form-control" required id="edit-image">
                            </div>
                            <div class="form-group">
                                <label>Price</label>
                                <input name="price" type="text" class="form-control" required id="edit-price">
                            </div>
                            <div class="form-group">
                                <label>Quantity</label>
                                <input name="quantity" type="number" class="form-control" required id="edit-quantity">
                            </div>
                            <div class="form-group">
                                <label>Title</label>
                                <textarea name="title" class="form-control" required id="edit-title"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <textarea name="description" class="form-control" required id="edit-description"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Category</label>
                                <select name="category" class="form-select" aria-label="Default select example" id="edit-select">
                                    <c:forEach items="${listCC}" var="o">
                                        <option value="${o.cid}">${o.cname}</option>
                                    </c:forEach>
                                </select>
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
       
        <div id="deleteWatchModal" class="modal fade">
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
            function handleEditId(id, name, image, price, title, description, quantity) {
                document.querySelector("#edit-id").value = id;
                document.querySelector("#edit-name").value = name;
                document.querySelector("#edit-image").value = image;
                document.querySelector("#edit-price").value = price;
                document.querySelector("#edit-title").value = title;
                document.querySelector("#edit-description").value = description;
                document.querySelector("#edit-quantity").value = quantity;
            }
            function showMess(id) {
                var option = confirm("Are you sure to delete " + id);
                if (option === true) {
                    window.location.href = 'deletecontrol?pid=' + id;
                }
            }
        </script>
    </body>
</html>