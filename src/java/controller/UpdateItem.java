/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
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

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateItem", urlPatterns = {"/updateitem"})
public class UpdateItem extends HttpServlet {

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
            out.println("<title>Servlet UpdateItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateItem at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
       response.sendRedirect("homecontroll");
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
        ProductDAO dao = new ProductDAO();
        String sizeId = request.getParameter("sizeId");
        String productID = request.getParameter("productId");
        String action = request.getParameter("action");
        double totalAmount = 0;

        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart != null) {
            int productId = Integer.parseInt(productID);
            Item item = cart.getItemByProductId(productId, Integer.parseInt(sizeId));

            if (item != null) {
                if ("increment".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1);
                } else if ("decrement".equals(action) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                }
                for (Item i : cart.getListCart()) {
                    totalAmount += i.getTotal();
                }

            }
            
        }

        
        request.setAttribute("totalAmount", totalAmount);

        request.getRequestDispatcher("Cart.jsp").forward(request, response);

        //response.sendRedirect("Cart.jsp");
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
