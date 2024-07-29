/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import dao.SizeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.Item;
import model.Product;
import model.Size;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddAndCart", urlPatterns = {"/addandcart"})
public class AddAndCart extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddAndCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAndCart at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy productId từ request
        String productId = request.getParameter("productId");

        // Thực hiện thêm sản phẩm vào giỏ hàng (tương tự như trong servlet "addcart")
        String productID = request.getParameter("productId");
        String sizeID = request.getParameter("sizeId");
       
        ProductDAO dao = new ProductDAO();
        SizeDAO sizeDao = new SizeDAO();
        Product product = dao.getProductByID(productID);
        Size size = sizeDao.getSizeClockById(Integer.parseInt(sizeID));

        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        } else {
            for (Item item : cart.getListCart()) {
                if (item.getProduct().getId() == Integer.parseInt(productID) && item.getSize().getSize_id() == Integer.parseInt(sizeID)) {
                    item.setQuantity(item.getQuantity() + 1);
                    response.sendRedirect("homecontroll");
                    return;
                }
            }
        }

        Item item = new Item(product, 1, size);
        cart.addItem(item);

        response.sendRedirect("homecontroll");

        // Lấy số lượng từ đối tượng Item
        // Lưu số lượng vào thuộc tính "quantity" của request
        // Chuyển hướng đến trang "cart.jsp"
        response.sendRedirect("Cart.jsp");

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
