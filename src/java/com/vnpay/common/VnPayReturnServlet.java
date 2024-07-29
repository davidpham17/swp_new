package com.vnpay.common;

import dao.ProductDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Cart;
import model.Item;
import model.Order;

@WebServlet("/vnpayreturn")
public class VnPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, String> fields = new TreeMap<>();
            for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
                if (entry.getKey().startsWith("vnp_")) {
                    fields.put(entry.getKey(), entry.getValue()[0]);
                }
            }

            String vnp_SecureHash = fields.remove("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");

            StringBuilder hashData = new StringBuilder();
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                hashData.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()));
            }

            String secureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            if (secureHash.equals(vnp_SecureHash)) {
                if ("00".equals(req.getParameter("vnp_TransactionStatus"))) {
                    double total = Double.parseDouble(fields.get("vnp_Amount")) / 100;
                    try {
                        updateOrderInDatabase(req, total);
                        resp.sendRedirect("Successful.jsp");
                    } catch (Exception e) {
                        e.printStackTrace();
                        resp.sendRedirect("error.jsp?message=Error updating database");
                    }
                } else {
                    resp.sendRedirect("error.jsp?message=Payment failed with response code: " + fields.get("vnp_ResponseCode"));
                }
            } else {
                resp.sendRedirect("error.jsp?message=Invalid secure hash");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?message=Error processing payment result");
        }
    }

    private void updateOrderInDatabase(HttpServletRequest req, double total) throws ClassNotFoundException, SQLException {
        HttpSession session = req.getSession(false);
        ProductDAO dao = new ProductDAO();
        Account account = (Account) session.getAttribute("acc");
        int uID = account.getId();

        dao.beginTransaction();

        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date orderDate = new java.sql.Date(utilDate.getTime());
            Order orderDetail = dao.createOrder(orderDate, total, uID);

            int orderId = orderDetail.getOrderID();
            Cart cart = (Cart) session.getAttribute("cart");
            List<Item> listItem = cart.getListCart();
            for (Item currentItem : listItem) {
                dao.addOrderDetail(currentItem.getTotal(), currentItem.getQuantity(), orderId, currentItem.getProduct().getId());
                dao.updateProductQuantity(currentItem.getProduct().getId(), currentItem.getQuantity());
            }

            dao.commitTransaction();
        } catch (Exception e) {
           
            throw e; // Re-throw the exception to be handled in the doGet method
        } finally {
            if (session != null) {
                session.removeAttribute("cart"); // Xóa thuộc tính lưu trữ giỏ hàng trong session
            }
        }
    }
}
