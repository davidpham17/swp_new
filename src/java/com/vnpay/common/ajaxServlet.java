package com.vnpay.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.ProductDAO;
import model.Account;
import model.Cart;
import model.Item;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/vnpayajax")
public class ajaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            double total = Double.parseDouble(req.getParameter("totalAmount"));
            if (total <= 0) {
                resp.sendRedirect("error.jsp?message=Total payment must be greater than zero");
                return;
            }

            // Cập nhật cơ sở dữ liệu sau khi tạo URL thanh toán
            String paymentUrl = processVnpayPayment(req, total);
            JsonObject job = new JsonObject();
            job.addProperty("code", "00");
            job.addProperty("message", "success");
            job.addProperty("data", paymentUrl);
            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(job));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?message=Invalid total amount");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?message=Database update failed");
        }
    }

    private String processVnpayPayment(HttpServletRequest req, double total) throws IOException, SQLException, ClassNotFoundException, ParseException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) total * 100;
        String bankCode = req.getParameter("bankCode");
        String orderInfo = req.getParameter("orderInfo");

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef + " den tu khach hang " + orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        // Cập nhật cơ sở dữ liệu
//        updateOrderInDatabase(req, total);

        return Config.vnp_PayUrl + "?" + queryUrl;
    }
//
//    private void updateOrderInDatabase(HttpServletRequest req, double total) throws SQLException, ClassNotFoundException, ParseException {
//        HttpSession session = req.getSession(false);
//        ProductDAO dao = new ProductDAO();
//        Account account = (Account) session.getAttribute("acc");
//        int uID = account.getId();
//
//        dao.beginTransaction();
//
//        java.util.Date utilDate = new java.util.Date();
//        java.sql.Date orderDate = new java.sql.Date(utilDate.getTime());
//        Order orderDetail = dao.createOrder(orderDate, total, uID);
//
//        int orderId = orderDetail.getOrderID();
//        Cart cart = (Cart) session.getAttribute("cart");
//        List<Item> listItem = cart.getListCart();
//        for (Item currentItem : listItem) {
//            dao.addOrderDetail(currentItem.getTotal(), currentItem.getQuantity(), orderId, currentItem.getProduct().getId());
//            dao.updateProductQuantity(currentItem.getProduct().getId(), currentItem.getQuantity());
//        }
//
//        dao.commitTransaction();
//
//        if (session != null) {
//            session.removeAttribute("cart"); // Xóa thuộc tính lưu trữ giỏ hàng trong session
//        }
//    }
}
