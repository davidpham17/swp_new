// custom.js

var lastSelectedButton = null;
var selectedSize = null;

function setSize(button, size) {

    var displayElement = document.getElementById('selectedSizeDisplay');
    displayElement.textContent = 'Selected Size: ' + size;

    button.focus();

    if (lastSelectedButton) {
        lastSelectedButton.classList.remove('active');
    }
    button.classList.add('active');
    lastSelectedButton = button;

    selectedSize = size;
    console.log("Selected size:", size);
}

function checkLoginClickPrice(productId) {
    if (isLoggedIn) {
        // Nếu đã đăng nhập, thực hiện hành động thêm vào giỏ hàng
        window.location.href = "addandcart?productId=" + productId + "&sizeId=" + selectedSize.size_id;
    } else {
        // Nếu chưa đăng nhập, hiển thị thông báo yêu cầu đăng nhập
        alert("Please login to shopping!");
        // Hoặc hiển thị một modal yêu cầu đăng nhập
        // Ví dụ: $('#loginModal').modal('show');
    }
}

function checkLoginAndAddToCart(productId) {
    if (isLoggedIn) {
        // Nếu đã đăng nhập, thực hiện hành động thêm vào giỏ hàng
        window.location.href = "addcart?productId=" + productId + "&sizeId=" + selectedSize.size_id;
    } else {
        // Nếu chưa đăng nhập, hiển thị thông báo yêu cầu đăng nhập
        alert("Please login to shopping!");
        // Hoặc hiển thị một modal yêu cầu đăng nhập
        // Ví dụ: $('#loginModal').modal('show');
    }
}




