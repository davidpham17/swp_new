/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ProductDAO;
import helper.Helper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.OrderDetail;
import model.Product;

@WebServlet(name="ReportProduct", urlPatterns={"/report-product"})
public class ReportProduct extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (!Helper.isAdminOrStaff((Account)request.getSession().getAttribute("acc"))) {
            response.sendRedirect("homecontroll");
            return;
        }
        
        ProductDAO dao = new ProductDAO();
        List<OrderDetail> orderDetails = dao.getListOrderDetail();
        
        HashMap<Integer, Integer> productCount = new HashMap<>();
        for (int i = 0; i < orderDetails.size(); ++i) {
            Integer productId = orderDetails.get(i).getProduct().getId();
            Integer currentCount = productCount.get(productId);
            if (currentCount == null) {
                productCount.put(productId, 0);
                currentCount = 0;
            }
            Integer quantity = orderDetails.get(i).getQuantity();
            productCount.put(productId, currentCount + quantity);
        }

        int maxProduct = 0;
        ArrayList<Product> products = new ArrayList<>();
        for (Integer i : productCount.keySet()) {
            products.add(dao.getProductByID(i.toString()));
            products.get(products.size() - 1).setQuantity(productCount.get(i));
            products.get(products.size() - 1).setId(productCount.get(i));
            maxProduct = Integer.max(maxProduct, productCount.get(i));
        }
        products.sort((o1, o2) -> o2.getId() - o1.getId());
//        if (products.size() > 13) products.get(12).setName("OTHER WATCHES");
//        while (products.size() > 13) {
//            products.get(12).setQuantity(products.get(12).getQuantity() + products.get(products.size() - 1).getQuantity());
//            products.remove(products.size() - 1);
//        }
        while (products.size() > 13) {
            products.remove(products.size() - 1);
        }
        
        maxProduct += maxProduct / 5;
        ArrayList<Double> percentages = new ArrayList<>();
        for (int i = 0; i < products.size(); ++i) {
            percentages.add(products.get(i).getQuantity() * 1.0 / maxProduct * 100);
        }
        
        int sectionSize = 1;
        while (maxProduct / sectionSize > 10) sectionSize *= 10;
        
        int sectionCount = maxProduct / sectionSize;
        double sectionPercentage = sectionSize * 1.0 / maxProduct;
        
        request.setAttribute("sectionSize", sectionSize);
        request.setAttribute("products", products);
        request.setAttribute("percentages", percentages);
        request.setAttribute("sectionCount", sectionCount);
        request.setAttribute("sectionSize", sectionSize);
        request.setAttribute("sectionPercentage", sectionPercentage);
        
        request.getRequestDispatcher("ManageOrderProduct.jsp").forward(request, response);
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
