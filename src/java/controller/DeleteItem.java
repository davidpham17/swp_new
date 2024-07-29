/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.Item;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "DeleteItem", urlPatterns = {"/deleteitem"})
public class DeleteItem extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteItem at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Lấy productId từ tham số của URL
        String productId = request.getParameter("did");

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart != null) {
            // Tìm mục trong giỏ hàng dựa trên productId và loại bỏ nó
            Item itemToRemove = null;
            for (Item item : cart.getListCart()) {
                if (String.valueOf(item.getProduct().getId()).equals(productId)) {
                    itemToRemove = item;
                    break;
                }
            }
            
            if (itemToRemove != null) {
                cart.removeItem(itemToRemove);
            }
        }

        // Redirect trở lại trang giỏ hàng hoặc trang khác tùy ý

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
