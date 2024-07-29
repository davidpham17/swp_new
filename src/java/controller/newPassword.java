/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import dao.DBManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author codevn
 */
@WebServlet(name = "newPassword", urlPatterns = {"/newPassword"})
public class newPassword extends HttpServlet {

    public static Connection conn = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

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
            out.println("<title>Servlet newPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet newPassword at " + request.getContextPath() + "</h1>");
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

        HttpSession session = request.getSession();
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        RequestDispatcher dispatcher = null;

        // Kiểm tra mật khẩu mới và mật khẩu xác nhận
        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {
            try {
                // Kết nối CSDL
                conn = DBManager.getConnection();
                // Chuẩn bị câu lệnh SQL
                String query = "UPDATE dbo.accs SET pass = ? WHERE uID IN (SELECT uID FROM dbo.infor WHERE email = ?);";
                ps = conn.prepareStatement(query);
                ps.setString(1, newPassword);
                ps.setString(2, (String) session.getAttribute("email"));

                // Thực thi câu lệnh SQL
                int rowCount = ps.executeUpdate();

                // Kiểm tra kết quả và chuyển hướng
                if (rowCount > 0) {
                    request.setAttribute("status", "resetSuccess");
                } else {
                    request.setAttribute("status", "resetFailed");
                }

                // Đóng kết nối và chuyển hướng về trang đích
                conn.close();
                dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                // Xử lý ngoại lệ SQL
                e.printStackTrace();
                request.setAttribute("status", "resetFailed");
                dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(newPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Nếu mật khẩu không khớp, thông báo lỗi
            request.setAttribute("status", "passwordMismatch");
            dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
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

