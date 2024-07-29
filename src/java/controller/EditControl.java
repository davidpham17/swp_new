/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import helper.Helper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "EditControl", urlPatterns = {"/editcontrol"})
public class EditControl extends HttpServlet {

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
            out.println("<title>Servlet EditControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        if (!Helper.isAdminOrStaff((Account)request.getSession().getAttribute("acc"))) {
            response.sendRedirect("homecontroll");
            return;
        }
        
        String id_edit = request.getParameter("id");
        String name_edit = request.getParameter("name");
        String image_edit = request.getParameter("image");
        String price_edit = request.getParameter("price");
        String quantity_edit = request.getParameter("quantity");
        String title_edit = request.getParameter("title");
        String description_edit = request.getParameter("description");
        String category_edit = request.getParameter("category");
        
        ProductDAO dao = new ProductDAO();
        dao.editProduct(id_edit, name_edit, image_edit, price_edit, title_edit, description_edit, category_edit, Integer.parseInt(quantity_edit));
        
        response.sendRedirect("managercontrol");
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
