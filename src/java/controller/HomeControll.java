/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "HomeControll", urlPatterns = {"/homecontroll"})
public class HomeControll extends HttpServlet {

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
        ProductDAO dao = new ProductDAO();

        List<Product> list = dao.listProduct();
        int page, numberPerPage = 6;

        int size = list.size();
        int numpage = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);

        String pickedPage = request.getParameter("page"); // Trang được chọn
        if (pickedPage == null) {
            page = 1; // Khi mới vào thì chưa có trang đc chọn, cho = 1
        } else {
            page = Integer.parseInt(pickedPage);
        }

        int start, end;
        start = (page - 1) * numberPerPage;
        end = Math.min(page * numberPerPage, size);

        List<Product> listP = dao.getListByPage(list, start, end);

        List<Category> listC = dao.getAllCategory();

        request.setAttribute("listCC", listC);
        request.setAttribute("listP", listP);
        request.setAttribute("page", page);
        request.setAttribute("num", numpage);
         
        
        request.getRequestDispatcher("Home.jsp").forward(request, response);
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        processRequest(request, response);

    }

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
