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
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Order;

@WebServlet(name="ReportMonthly", urlPatterns={"/report-monthly"})
public class ReportMonthly extends HttpServlet {
   
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
        ArrayList<Order> orders = dao.getListOrder();

        ArrayList<Double> income = new ArrayList<>();
        ArrayList<String> month = new ArrayList<>();
        income.add(orders.get(0).getTotal());
        month.add(orders.get(0).getOrderDate().toString().substring(0, 7));
        for (int i = 1; i < orders.size(); ++i) {
            String previousMonth = orders.get(i - 1).getOrderDate().toString().substring(0, 7);
            String currentMonth = orders.get(i).getOrderDate().toString().substring(0, 7);
            if (!previousMonth.equals(currentMonth)) {
                income.add(0.0);
                month.add(currentMonth);
            }
            Double currentMonthTotal = income.get(income.size() - 1);
            income.set(income.size() - 1, currentMonthTotal + orders.get(i).getTotal());
        }
        
        double maxIncome = 0;
        for (int i = 0; i < income.size(); ++i) maxIncome = Double.max(income.get(i), maxIncome);
        maxIncome += maxIncome / 5;
        ArrayList<Double> percentage = new ArrayList<>();
        for (int i = 0; i < income.size(); ++i) {
            percentage.add(income.get(i) / maxIncome * 100);
        }
        
        int sectionSize = 1;
        while (maxIncome / sectionSize > 10) sectionSize *= 10;
        Double sectionPercentage = sectionSize / maxIncome * 100;
        int sectionCount = (int)maxIncome / sectionSize;
        
        request.setAttribute("income", income);
        request.setAttribute("month", month);
        request.setAttribute("percentage", percentage);
        request.setAttribute("sectionSize", sectionSize);
        request.setAttribute("sectionPercentage", sectionPercentage);
        request.setAttribute("sectionCount", sectionCount);
        
        request.getRequestDispatcher("ManageOrderMonthly.jsp").forward(request, response);
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

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
