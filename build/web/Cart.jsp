<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <style>
            .shopping-cart {
                margin-top: 85px;
            }
            .bg-custom-dark {
                background-color: #f8f9fa;
            }
            .bg-custom-light {
                background-color: #ffffff;
            }
            .text-custom-light {
                color: #000000;
            }
            .btn-danger-custom {
                background-color: #dc3545;
                color: #fff;
            }
            .btn-primary-custom {
                background-color: #007bff;
                color: #fff;
            }
            .btn-lg {
                font-size: 1.25rem;
            }
            .custom-quantity-btn {
                border: 1px solid #ddd;
                background-color: #f8f9fa;
                font-size: 1.25rem;
                width: 35px;
                height: 35px;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                border-radius: 50%;

            }
            .custom-table th, .custom-table td {
                vertical-align: middle;
            }
            .order-summary {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .total-amount {
                font-size: 1.5rem;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavbarLight.jsp"></jsp:include>
        <div class="shopping-cart" style="margin-top: 135px;">
                <div class="px-4 px-lg-0">
                    <div class="pb-5">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-8 p-5 bg-custom-dark rounded shadow-sm ">
                                    <!-- Shopping cart table -->
                                    <div class="table-responsive">
                                        <table class="table custom-table">
                                            <thead>
                                                <tr>
                                                    <th scope="col" class="border-0 bg-custom-light">
                                                        <div class="p-2 px-3 text-uppercase">Items</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-custom-light">
                                                        <div class="py-2 text-uppercase">Price</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-custom-light">
                                                        <div class="py-2 text-uppercase">Quantity</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-custom-light">
                                                        <div class="py-2 text-uppercase">Amount</div>
                                                    </th><th scope="col" class="border-0 bg-custom-light">
                                                        <div class="py-2 text-uppercase">Size</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-custom-light">
                                                        <div class="py-2 text-uppercase">Remove</div>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${cart.listCart}" var="o">
                                                <tr>
                                                    <th scope="row" class="align-middle">
                                                        <div class="p-2">
                                                            <img src="${o.product.image}" alt="" class="img-fluid rounded shadow-sm">
                                                            <div class="ml-3 d-inline-block align-middle">
                                                                <h5 class="mb-0"> <a href="#" class="text-custom-light d-inline-block">${o.product.name}</a></h5>
                                                            </div>
                                                        </div>
                                                    </th>
                                                    <td class="align-middle"><strong>${o.product.price}</strong></td>
                                                    <td class="align-middle">
                                                        <form action="updateitem" method="post" class="d-inline">
                                                            <input type="hidden" name="productId" value="${o.product.id}">
                                                            <input type="hidden" name="sizeId" value="${o.size.size_id}">
                                                            <input type="hidden" name="action" value="increment">
                                                            <button class="custom-quantity-btn" type="submit">+</button>
                                                        </form>
                                                        <strong style="font-size: 18px; margin: 0 10px;">${o.quantity}</strong>
                                                        <form action="updateitem" method="post" class="d-inline">
                                                            <input type="hidden" name="productId" value="${o.product.id}">
                                                            <input type="hidden" name="sizeId" value="${o.size.size_id}">
                                                            <input type="hidden" name="action" value="decrement">
                                                            <button class="custom-quantity-btn" type="submit">-</button>
                                                        </form>
                                                    </td>
                                                    <td class="align-middle"><strong>${o.getTotal()}</strong></td>
                                                    <td class="align-middle">${o.size.size}</td>
                                                    <td class="align-middle">
                                                        <a href="deleteitem?did=${o.product.id}" class="text-custom-light">
                                                            <button type="button" class="btn btn-danger-custom">
                                                                <i class="fas fa-trash-alt"></i>
                                                            </button>
                                                        </a>
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- End -->
                            </div>
                            <div class="col-lg-4 order-summary">
                                <h4 class="d-flex justify-content-between align-items-center mb-3">
                                    <span class="text-muted">Order summary</span>
                                    <span class="badge badge-secondary badge-pill">${cart.listCart.size()}</span>
                                </h4>
                                <ul class="list-group mb-3">
                                    <c:set var="totalAmount" value="0" />
                                    <c:forEach items="${cart.listCart}" var="item">
                                        <c:set var="itemTotal" value="${item.getTotal()}" />
                                        <c:set var="totalAmount" value="${totalAmount + itemTotal}" />
                                    </c:forEach>
                                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                        <div>
                                            <h6 class="my-0">Total cost</h6>
                                        </div>
                                        <span class="text-muted">${totalAmount} $</span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                        <div>
                                            <h6 class="my-0">Shipping</h6>
                                        </div>
                                        <span class="text-muted">Free ship</span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                        <div>
                                            <h6 class="my-0">Total payment</h6>
                                        </div>
                                        <span class="total-amount">${totalAmount} $</span>
                                    </li>
                                </ul>

                                <form action="checkoutcontrol" method="post" class="mt-3">
                                    <label for="name">Full Name:</label>
                                    <input type="text" id="name" name="fullName" value="${userInfo.name}" class="form-control mb-3" required>

                                    <label for="phone">Telephone:</label>
                                    <input type="number" id="phone" name="phonenumber" value="${userInfo.phone}" class="form-control mb-3" required>

                                    <label for="email">Your Email:</label>
                                    <input type="email" id="userEmail" name="userEmail" value="${userInfo.email}" class="form-control mb-3" required>

                                    <label for="address">Address:</label>
                                    <textarea id="address" name="address" rows="4" cols="50" class="form-control mb-3" required>${userInfo.address}</textarea>

                                    <input type="hidden" name="totalAmount" value="${totalAmount}">



                                    <button type="submit" name="action" value="Payment" class="btn btn-primary-custom btn-lg btn-block">Cash on Delivery</button>
                                </form>

                                <!-- VNPAY Form -->
                                <form id="vnpayForm" action="vnpay_pay.jsp" method="post" class="mt-3">
                                    <input type="hidden" name="fullName" id="vnpayFullName">
                                    <input type="hidden" name="phonenumber" id="vnpayPhoneNumber">
                                    <input type="hidden" name="userEmail" id="vnpayUserEmail">
                                    <input type="hidden" name="address" id="vnpayAddress">
                                    <input type="hidden" name="totalAmount" id="vnpayTotalAmount">
                                    <button type="button" name="action" value="VNPAY" class="btn btn-primary-custom btn-lg btn-block" onclick="submitVnpayForm()">Online Payment with VNPAY</button>
                                </form>

                                <script type="text/javascript">
                                    function submitVnpayForm() {
                                        // Copy values from main form to hidden inputs in VNPAY form
                                        document.getElementById('vnpayFullName').value = document.getElementById('name').value;
                                        document.getElementById('vnpayPhoneNumber').value = document.getElementById('phone').value;
                                        document.getElementById('vnpayUserEmail').value = document.getElementById('userEmail').value;
                                        document.getElementById('vnpayAddress').value = document.getElementById('address').value;
                                        document.getElementById('vnpayTotalAmount').value = document.querySelector('input[name="totalAmount"]').value;
                                        document.getElementById('vnpayForm').submit();
                                    }
                                </script>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7HUbX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
