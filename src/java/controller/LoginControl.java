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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.UserInfo;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginControl", urlPatterns = {"/login"})
public class LoginControl extends HttpServlet {

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
        Cookie arr[] = request.getCookies();
            if (arr != null) {
                for (Cookie cookie : arr) {
                    if (cookie.getName().equals("userC")) {
                        request.setAttribute("username", cookie.getValue());
                    }
                    if (cookie.getName().equals("passC")) {
                        request.setAttribute("password", cookie.getValue());
                    }
                }
            }
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String remember = request.getParameter("rem");

        ProductDAO dao = new ProductDAO();

        Account account = dao.checkLogin(username, password);
        String em = "invalid username or password";

        if (account == null) {
            // Login is unsuccessful, set an error message and forward to Login.jsp
            request.setAttribute("error", em);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {

            HttpSession session = request.getSession();
            UserInfo userInfo = dao.getUserInfoByID(account.getId());
            session.setAttribute("acc", account);
            session.setAttribute("userInfo", userInfo);
            session.setMaxInactiveInterval(60*60*24);

            Cookie u = new Cookie("userC", username);
            Cookie p = new Cookie("passC", password);

            u.setMaxAge(2000);

            if (remember != null) {
                p.setMaxAge(2000);
            } else {
                p.setMaxAge(0);
            }

            response.addCookie(u);
            response.addCookie(p);

            

            response.sendRedirect("homecontroll");
        };
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
