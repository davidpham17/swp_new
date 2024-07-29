/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import java.util.ArrayList;
import model.Size;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author codevn
 */
public class SizeDAO {
    
    public void addSizeClock(Size sizeClock) {
        String sql = "INSERT INTO dbo.sizeClock (size_id, size) VALUES (?, ?)";
        try{
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sizeClock.getSize_id());
            preparedStatement.setString(2, sizeClock.getSize());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Size getSizeClockById(int sizeId) {
        Size sizeClock = null;
        String sql = "SELECT size_id, size FROM dbo.sizeClock WHERE size_id = ?";
        try{
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sizeId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("size_id");
            String size = rs.getString("size");
            sizeClock = new Size(id, size);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sizeClock;
    }
    
    public List<Size> getAllSizeClocks() {
        List<Size> sizeClocks = new ArrayList<>();
        String sql = "SELECT size_id, size FROM dbo.sizeClock";
        try{
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int sizeId = rs.getInt("size_id");
                String size = rs.getString("size");
                sizeClocks.add(new Size(sizeId, size));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sizeClocks;
    }
    
    public void updateSizeClock(Size sizeClock) {
        String sql = "UPDATE dbo.sizeClock SET size = ? WHERE size_id = ?";
        try{
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sizeClock.getSize());
            preparedStatement.setInt(2, sizeClock.getSize_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSizeClock(int sizeId) {
        String sql = "DELETE FROM dbo.sizeClock WHERE size_id = ?";
        try{
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sizeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) {
//        SizeDAO sizeDao = new SizeDAO();
//        System.out.println(sizeDao.getSizeClockById(8));
//    }

}
