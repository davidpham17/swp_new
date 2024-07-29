/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.InforUser;
import model.OrderDetail;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProcessUserData", urlPatterns = {"/processuserdata"})
public class ProcessUserData extends HttpServlet {
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = ((Account)session.getAttribute("acc")).getId();

        String name = request.getParameter("name");
        String birthdateStr = request.getParameter("birthdate");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        try {
            Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateStr);

            InforUser user = new InforUser(userID, name, birthdate, phone, email, address);
            ProductDAO dao = new ProductDAO();
            dao.upsertUserInformation(user);

            RequestDispatcher rd=request.getRequestDispatcher("InforUser.jsp");  
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    int userID = ((Account)session.getAttribute("acc")).getId();
    System.out.println("User ID: " + userID);

    ProductDAO dao = new ProductDAO();
    InforUser user = null;
    try {
        user = dao.getUserInformation(userID);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ProcessUserData.class.getName()).log(Level.SEVERE, null, ex);
    }
    List<OrderDetail> purchaseHistory = dao.getPurchaseHistory(userID);
    System.out.println("Purchase history: " + purchaseHistory.size());
    request.setAttribute("user", user);
    request.setAttribute("purchaseHistory", purchaseHistory);
    RequestDispatcher rd = request.getRequestDispatcher("InforUser.jsp");
    rd.forward(request, response);
}
}