/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$(document).ready(function () {
    let lastScrollTop = 0;
    let delta = 5;
    let navbarHeight = $('.navbar-default').outerHeight();
    
    $(window).scroll(function () {
        let st = $(this).scrollTop();
        
        if (Math.abs(lastScrollTop - st) <= delta) return;
        
        if (st > lastScrollTop && st > navbarHeight) {
            $('.navbar-default').addClass('navbar-hidden').removeClass('navbar-shrink');
        } else {
            if (st + $(window).height() < $(document).height()) {
                $('.navbar-default').removeClass('navbar-hidden').addClass('navbar-shrink');
            }
        }
        
        lastScrollTop = st;
    });

    $(window).scroll(function() {
        if ($(this).scrollTop() > 50) {
            $('.navbar-default').addClass('navbar-shrink');
        } else {
            $('.navbar-default').removeClass('navbar-shrink');
        }
    });
});


