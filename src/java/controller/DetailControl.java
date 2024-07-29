package controller;

import dao.ProductDAO;
import dao.ReviewDAO;
import dao.SizeDAO;
import model.Category;
import model.Product;
import model.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Size;

@WebServlet(name = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("pid");

        ProductDAO dao = new ProductDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        Product product = dao.getProductByID(id);
        List<Category> listCC = dao.getAllCategory();
        List<Review> reviews = reviewDAO.getReviewsByProductID(id);

        // Create a map to store usernames for each review
        Map<Integer, String> userNames = new HashMap<>();
        for (Review review : reviews) {
            String username = reviewDAO.getUsernameByUserID(review.getUserID());
            userNames.put(review.getUserID(), username);
        }

        // Calculate total reviews and rating distribution
        int totalReviews = reviews.size();
        int[] ratingDistribution = new int[5];
        double totalRating = 0;

        for (Review review : reviews) {
            int rating = review.getRating();
            if (rating >= 1 && rating <= 5) {
                ratingDistribution[rating - 1]++;
                totalRating += rating;
            }
        }

        // Convert rating distribution to percentages
        for (int i = 0; i < ratingDistribution.length; i++) {
            if (totalReviews > 0) {
                ratingDistribution[i] = (int) Math.round((ratingDistribution[i] * 100.0) / totalReviews);
            } else {
                ratingDistribution[i] = 0;
            }
        }

        // Round average rating to 2 decimal places
        double averageRating = totalReviews > 0 ? totalRating / totalReviews : 0;
        BigDecimal avgRatingRounded = new BigDecimal(averageRating).setScale(2, RoundingMode.HALF_UP);

        // Create a list from 5 to 1 to send to JSP
        List<Integer> ratingRange = Arrays.asList(5, 4, 3, 2, 1);

        request.setAttribute("listCC", listCC);
        request.setAttribute("detail", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("userNames", userNames); // Send map with usernames to JSP
        request.setAttribute("averageRating", avgRatingRounded.doubleValue());
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("ratingDistribution", ratingDistribution);
        request.setAttribute("ratingRange", ratingRange);
        
        // size
        SizeDAO sizeDao = new SizeDAO();
        List<Size> sizes = sizeDao.getAllSizeClocks();
        request.setAttribute("sizes", sizes);
        
        // top 5 recommend
        List<Product> top5 = new ArrayList<>();
        int count = 0;
        for (Product product1 : dao.listProduct()) {
            if (count == 5) {
                break;
            }
            if (product1.getTitle().compareTo(product.getTitle()) != 0) {
                if (product1.getName().compareTo(product.getName()) != 0) {
                    top5.add(product1);
                    count++;
                    continue;
                }
                top5.add(product1);
                count++;
            }
        }
        request.setAttribute("top5recommend", top5);
        
        request.getRequestDispatcher("Detail.jsp").forward(request, response);
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
