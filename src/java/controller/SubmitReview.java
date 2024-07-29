package controller;

import dao.ReviewDAO;
import model.Review;
import model.Account;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "SubmitReviewServlet", urlPatterns = {"/submitReview"})
@MultipartConfig
public class SubmitReview extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String productId = request.getParameter("productId");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Account user = (Account) request.getSession().getAttribute("acc");
        int userId = user.getId();

        // Handle image upload
        Part imagePart = request.getPart("image");
        String imagePath = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) uploadDirFile.mkdir();
            String relativePath = "uploads" + File.separator + fileName;
            imagePath = getServletContext().getContextPath() + "/" + relativePath;
            imagePart.write(uploadDir + File.separator + fileName);
        }

        Review review = new Review();
        review.setProductID(Integer.parseInt(productId));
        review.setUserID(userId);
        review.setRating(rating);
        review.setComment(comment);
        review.setImagePath(imagePath); // Set image path

        ReviewDAO dao = new ReviewDAO();
        dao.addReview(review);

        response.sendRedirect("detail?pid=" + productId);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
