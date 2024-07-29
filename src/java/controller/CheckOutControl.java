/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckOutControl", urlPatterns = {"/checkoutcontrol"})
public class CheckOutControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

if ("Payment".equals(action)) {
    // Lấy thông tin từ request
    String fullName = request.getParameter("fullName");
    String phoneNumber = request.getParameter("phonenumber");
    String userEmail = request.getParameter("userEmail");
    String address = request.getParameter("address");

    if (fullName != null && userEmail != null) {
        try {
            // Lấy tổng giá trị đơn hàng từ form
            double total = Double.parseDouble(request.getParameter("totalAmount"));

            if (total > 0) { // Check if the total payment is greater than zero
                // Tiếp tục xử lý như thông thường
                ProductDAO dao = new ProductDAO();
                HttpSession session = request.getSession(false);
                Account account = (Account) session.getAttribute("acc");
                int uID = account.getId();
                // Bắt đầu giao dịch
                dao.beginTransaction();

                // Tạo đơn hàng
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date orderDate = new java.sql.Date(utilDate.getTime());
                Order orderDetail = dao.createOrder(orderDate, total, uID);

                // Tạo thông tin người dùng
                Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");

                InforUser user = new InforUser(uID, fullName, birthdate, phoneNumber, userEmail, address);
                dao.upsertUserInformation(user);
                session.setAttribute("userInfo", user);

                int orderId = orderDetail.getOrderID();
                Cart cart = (Cart) request.getSession().getAttribute("cart");
                List<Item> listItem = cart.getListCart();
                for (int i = 0; i < listItem.size(); ++i) {
                    Item currentItem = listItem.get(i);
                    dao.addOrderDetail(currentItem.getTotal(), currentItem.getQuantity(), orderId, currentItem.getProduct().getId());
                    dao.updateProductQuantity(currentItem.getProduct().getId(), currentItem.getQuantity());
                }

                // Kết thúc giao dịch
                dao.commitTransaction();

                // Gửi email
                sendShippingEmail(userEmail, orderDetail, address, fullName, phoneNumber);
                if (session != null) {
                    session.removeAttribute("cart"); // Xóa thuộc tính lưu trữ giỏ hàng trong session
                    // Hoặc session.setAttribute("cart", new Cart()); // Làm trống giỏ hàng
                }
                // Chuyển hướng
                response.sendRedirect("Successful.jsp");
            } else {
                // Handle the case where the total payment is zero
                response.sendRedirect("error.jsp?message=Total payment must be greater than zero");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Phản hồi với thông báo lỗi nếu cần thiết
            response.sendRedirect("error.jsp"); // Chuyển hướng tới trang lỗi
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CheckOutControl.class.getName()).log(Level.SEVERE, null, ex);
            // Handle database or class not found exceptions
            response.sendRedirect("error.jsp");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    } else {
        // Xử lý logic mặc định hoặc thông báo lỗi nếu có
        response.sendRedirect("error.jsp"); // Chuyển hướng tới trang lỗi
    }
}
    }


    private void sendShippingEmail(String userEmail, Order orderDetail, String address, String fullName, String PhoneNumber) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dhoan652003@gmail.com", "uusz dvll ietv tikh");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dhoan652003@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Shipping Information for your order ");
            message.setText("Dear " + fullName + ", Thank you for your order. Here is your shipping information: " + orderDetail +
    ". We will contact you via the following phone number: " + PhoneNumber +
    ". We will deliver the goods to " + address + " as soon as possible");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */