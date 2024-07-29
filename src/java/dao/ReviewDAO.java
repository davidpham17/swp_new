package dao;

import model.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> getReviewsByProductID(String productId) {
        List<Review> reviews = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String sql = "SELECT * FROM Reviews WHERE productID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("reviewID"));
                review.setProductID(rs.getInt("productID"));
                review.setUserID(rs.getInt("userID"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setImagePath(rs.getString("imagePath"));
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

     public void addReview(Review review) {
        try {
            Connection conn = DBManager.getConnection();
            String sql = "INSERT INTO Reviews (productID, userID, rating, comment, imagePath) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getProductID());
            ps.setInt(2, review.getUserID());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setString(5, review.getImagePath()); // Set image path
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void insertReview(Review review) {
     String sql = "INSERT INTO Review(productID, userID, rating, comment, imagePath) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DBManager.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, review.getProductID());
        ps.setInt(2, review.getUserID());
        ps.setInt(3, review.getRating());
        ps.setString(4, review.getComment());
        ps.setString(5, review.getImagePath());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

   

    // Phương thức lấy tên người dùng dựa trên userID
        public String getUsernameByUserID(int userID) {
        String username = null;
        try {
            Connection conn = DBManager.getConnection();
            String sql = "SELECT [user] FROM accs WHERE uID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                username = rs.getString("user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
}

