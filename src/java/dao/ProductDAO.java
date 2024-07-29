/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Category;
import model.InforUser;
import model.Order;
import model.OrderDetail;
import model.Product;
import model.UserInfo;

/**
 *
 * @author ADMIN
 */
public class ProductDAO {

    Connection conn = null;
    PreparedStatement ptm = null;
    ResultSet rs = null;

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    
    public List<OrderDetail> getListOrderDetail() {
        List<OrderDetail> list = new ArrayList<>();
        String query = "select * from OrderDetail join Product on OrderDetail.id = Product.id";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);
            rs = ptm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDetail(
                        rs.getInt(1),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(2),
                        new Product(
                                rs.getInt(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getDouble(9),
                                rs.getString(10),
                                rs.getString(11)
                        )
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<UserInfo> listUserInfoRestricted() {
        List<UserInfo> list = new ArrayList<>();
        String query = "select * from dbo.infor join dbo.accs on dbo.infor.\"uID\" = dbo.accs.\"uID\" where \"role\" = 'us'";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);
            rs = ptm.executeQuery();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                list.add(userInfo);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<OrderDetail> getListOrderDetail(int orderID) {
        String sql = "select * from [OrderDetail] join [Product] on [OrderDetail].id = [Product].id where [OrderDetail].orderID = " + orderID;
        ArrayList<OrderDetail> listOrderDetail = new ArrayList<>();
        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql);
            ResultSet res = ptm.executeQuery();
            while (res.next()) {
//                System.out.println(res.getInt(1));
                listOrderDetail.add(new OrderDetail(
                        res.getInt(1),
                        res.getInt(3),
                        res.getInt(4),
                        res.getDouble(2),
                        new Product(
                                res.getInt(6),
                                res.getString(7),
                                res.getString(8),
                                res.getDouble(9),
                                res.getString(10),
                                res.getString(11))
                ));
            }
        } catch (Exception e) {
        }
        return listOrderDetail;
    }

    public ArrayList<Order> getListOrder() {
        String sql = "select * from [Order]";
        ArrayList<Order> listOrder = new ArrayList<Order>();
        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql);
            ResultSet res = ptm.executeQuery();
            while (res.next()) {
//                System.out.println(res.getInt(1));
                listOrder.add(new Order(res.getInt(1), res.getDate(2), res.getDouble(3)));
            }
        } catch (Exception e) {
        }
        return listOrder;
    }

    public void addOrderDetail(double price, int quantity, int orderID, int productID) {
        String sql = "insert into dbo.OrderDetail(price, quantity, orderID, id) values (" + price + ", " + quantity + ", " + orderID + ", " + productID + ")";
        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql);
            ptm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void deleteUser(int uid) {
        String sql1 = "delete from infor where \"uID\"=" + uid;
        String sql2 = "delete from accs where \"uID\"=" + uid;

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql1);
            ptm.executeUpdate();
            ptm = conn.prepareStatement(sql2);
            ptm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void editInfo(int uid, String name, String birthdate, String phone, String email, String address) {
        String sql = "update infor set \"name\"='" + name + "',birthdate='" + birthdate + "',phone='" + phone + "',email='" + email + "',\"address\"='" + address + "'where \"uID\" = " + uid;
//        System.out.println(sql);

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql);
            ptm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void addInfo(int uid, String name, String birthdate, String phone, String email, String address) {
        String sql = "insert into infor(\"uID\", \"name\", birthdate, phone, email, \"address\") values(" + uid + ", '" + name + "', '" + birthdate + "', '" + phone + "', '" + email + "', '" + address + "')";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql);
            int row = ptm.executeUpdate();
            ptm.getGeneratedKeys();
        } catch (Exception e) {
        }
    }

    public int addAccount(String username, String password, String role) {
        String sql = "insert into accs(\"user\", pass, \"role\") values('" + username + "', '" + password + "', '" + role + "')";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int row = ptm.executeUpdate();
            ResultSet res = ptm.getGeneratedKeys();
            res.next();
            return (res.getInt(1));
        } catch (Exception e) {
        }
        return 0;
    }

    public List<UserInfo> listUserInfo() {
        List<UserInfo> list = new ArrayList<>();
        String query = "select * from dbo.infor";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);
            rs = ptm.executeQuery();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                list.add(userInfo);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Product> listProduct() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM dbo.Product";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);
            rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getDouble("price"), rs.getString("title"), rs.getString("description"));
                p.setQuantity(rs.getInt("quantity")); // Đặt giá trị cho quantity
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateProductQuantity(int productId, int quantity) throws SQLException {
        String query = "UPDATE Product SET quantity = quantity - ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Product> getListByPage(List<Product> list, int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Product> getProductByCID(String cid) {
        List<Product> list = new ArrayList<>();
        String query = "select * from dbo.Product where categoryID=?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, cid);
            rs = ptm.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(8)));

            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Product> searchByName(String txtSearch) {
        List<Product> list = new ArrayList<>();
        String query = "select * from Product where [name] LIKE ?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, "%" + txtSearch + "%"); // Câu lệnh LIKE cần %%
            rs = ptm.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(8)));  // Thêm trường này
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Product getProductByID(String id) {
        String query = "select * from Product\n"
                + "where id = ?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, id);
            rs = ptm.executeQuery();
            while (rs.next()) {
                return new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "select * from dbo.category";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            rs = ptm.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1),
                        rs.getString(2)
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Account checkLogin(String username, String password) {
        String query = "select * from accs where [user] = ? AND pass=? ";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, username);
            ptm.setString(2, password);
            rs = ptm.executeQuery();
            while (rs.next()) {
                //return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

            }
        } catch (Exception e) {

        }
        return null;
    }

    public void signUp(String username, String password) {
        String query = "INSERT INTO dbo.accs ([user], pass, role) VALUES (?, ?, 'us')";

        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, username);
            ptm.setString(2, password);
            ptm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account checkAcconutExist(String username) {
        String query = "select * from accs where [user] = ?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, username);

            rs = ptm.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void delete(String id) {
        String query = "delete from Product where id = ?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, id);
            ptm.executeUpdate();
        } catch (Exception e) {

        }

    }

    public void insertProduct(String name, String image, String price, String title, String description, String category, int quantity) {
        String query = "insert into [dbo].[Product]([name],[image],[price],[title],[description],[categoryID],[quantity])\n"
                + "values(?,?,?,?,?,?,?)";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setString(1, name);
            ptm.setString(2, image);
            ptm.setString(3, price);
            ptm.setString(4, title);
            ptm.setString(5, description);
            ptm.setString(6, category);
            ptm.setInt(7, quantity);

            ptm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void editProduct(String id, String name, String image, String price, String title, String description, String category, int quantity) {
        String query = "UPDATE [dbo].[Product]\n"
                + "SET name = ?,\n"
                + "image = ?,\n"
                + "price = ?,\n"
                + "title = ?,\n"
                + "description = ?,\n"
                + "categoryID = ?,\n"
                + "quantity = ?\n"
                + "WHERE id = ?";

        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);

            ptm.setString(1, name);
            ptm.setString(2, image);
            ptm.setString(3, price);
            ptm.setString(4, title);
            ptm.setString(5, description);
            ptm.setString(6, category);
            ptm.setInt(7, quantity);
            ptm.setString(8, id);

            ptm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

//    public void createOrder(java.sql.Date orderDate, double total, int userID) {
//        String query = "INSERT INTO [dbo].[Order] (orderDate, total, [uID]) \n"
//                + "VALUES (?, ?, ?);";
//        try {
//            conn = new DBManager().getConnection();//mo ket noi voi SQL Server
//            ptm = conn.prepareStatement(query);
//
//            ptm.setDate(1, orderDate); // orderDate là một java.sql.Date
//            ptm.setDouble(2, total);
//            ptm.setInt(3, userID);
//
//            ptm.executeUpdate();
//        } catch (Exception e) {
//
//        }
//    }
    
  public Order createOrder(java.sql.Date orderDate, double total, int uID) {
        String query = "INSERT INTO [dbo].[Order] (orderDate, total, uID) VALUES (?, ? , ?)";
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet generatedKeys = null;
        Order order = null; // Khởi tạo đối tượng Order

        try {
            conn = new DBManager().getConnection();
            ptm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ptm.setDate(1, orderDate);
            ptm.setDouble(2, total);
            ptm.setInt(3, uID);
            ptm.executeUpdate();

            generatedKeys = ptm.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderID = generatedKeys.getInt(1);
                order = new Order(orderID, utilDate, total);
                order.setOrderID(orderID); // Đặt orderID cho đối tượng Order
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (ptm != null) {
                    ptm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return order; // Trả về đối tượng Order
    }




    public void beginTransaction() throws SQLException, ClassNotFoundException {
        // Bắt đầu giao dịch
        Connection conn = new DBManager().getConnection(); // Lấy kết nối đến cơ sở dữ liệu
        conn.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException, ClassNotFoundException {
        // Kết thúc giao dịch và lưu các thay đổi
        Connection conn = new DBManager().getConnection(); // Lấy kết nối đến cơ sở dữ liệu

        conn.commit();
        conn.setAutoCommit(true);
    }

    public void createOrderDetail(int orderID, int productID, double price, int quantity) {

        String query = "INSERT INTO OrderDetail (orderID, id, price, quantity) VALUES (?, ?, ?, ?)";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi SQL Server
            ptm = conn.prepareStatement(query);

            ptm.setInt(1, orderID);
            ptm.setInt(2, productID);
            ptm.setDouble(3, price);
            ptm.setInt(4, quantity);
            ptm.executeUpdate();

            ptm.executeUpdate();
        } catch (Exception e) {

        }

    }

    public void updateUser(int id, String address, java.sql.Date birthdate, String phone, String mail) {
        String query = "UPDATE accs\n"
                + "    SET\n"
                + "        [address] = ?,\n"
                + "        [birthday] = ?,\n"
                + "        [phone] = ?,\n"
                + "        [mail] = ?\n"
                + "    WHERE [uID] = ?;";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi SQL Server
            ptm = conn.prepareStatement(query);

            ptm.setInt(5, id);
            ptm.setNString(1, address);
            ptm.setDate(2, birthdate);
            ptm.setString(3, phone);
            ptm.setString(4, mail);

            ptm.executeUpdate();
        } catch (Exception e) {

        }
    }

    public Account selectUser(int id) {
        String query = "select * from accs where [uID] = ?";
        try {
            conn = new DBManager().getConnection();//mo ket noi voi sql
            ptm = conn.prepareStatement(query);
            ptm.setInt(1, id);

            rs = ptm.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public InforUser getUserInformation(int userID) throws ClassNotFoundException {
        String selectQuery = "SELECT * FROM infor WHERE uID = ?";
        InforUser user = null;

        try {
            conn = new DBManager().getConnection();
            ptm = conn.prepareStatement(selectQuery);
            ptm.setInt(1, userID);
            rs = ptm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                Date birthdate = rs.getDate("birthdate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                user = new InforUser(userID, name, birthdate, phone, email, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ptm != null) {
                    ptm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    
        public List<OrderDetail> getPurchaseHistory(int uID) {
        List<OrderDetail> list = new ArrayList<>();
        String query = "SELECT * FROM OrderDetail "
                + "JOIN Product ON OrderDetail.id = Product.id "
                + "JOIN [Order] ON OrderDetail.orderID = [Order].orderID "
                + "WHERE [Order].uID = ?";

        try {
            conn = DBManager.getConnection();

            assert conn != null;
            ptm = conn.prepareStatement(query);
            ptm.setInt(1, uID);
            rs = ptm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDetail(
                        rs.getInt(1),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(2),
                        new Product(
                                rs.getInt(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getDouble(9),
                                rs.getString(10),
                                rs.getString(11)
                        ),
                        new Order(
                                rs.getInt(14),
                                rs.getDate(15),
                                rs.getDouble(16)
                        )
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
        
            public void upsertUserInformation(InforUser user) throws ClassNotFoundException {
        String selectQuery = "SELECT * FROM infor WHERE uID = ?";
        String updateQuery = "UPDATE infor SET name = ?, birthdate = ?, phone = ?, email = ?, address = ? WHERE uID = ?";
        String insertQuery = "INSERT INTO infor (uID, name, birthdate, phone, email, address) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = new DBManager().getConnection();
            ptm = conn.prepareStatement(selectQuery);
            ptm.setInt(1, user.getuID());
            rs = ptm.executeQuery();

            if (rs.next()) {
                // Update existing user info
                ptm = conn.prepareStatement(updateQuery);
                ptm.setString(1, user.getName());
                ptm.setDate(2, new java.sql.Date(user.getBirthdate().getTime()));
                ptm.setString(3, user.getPhone());
                ptm.setString(4, user.getEmail());
                ptm.setString(5, user.getAddress());
                ptm.setInt(6, user.getuID());
            } else {
                // Insert new user info
                ptm = conn.prepareStatement(insertQuery);
                ptm.setInt(1, user.getuID());
                ptm.setString(2, user.getName());
                ptm.setDate(3, new java.sql.Date(user.getBirthdate().getTime()));
                ptm.setString(4, user.getPhone());
                ptm.setString(5, user.getEmail());
                ptm.setString(6, user.getAddress());
            }

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ptm != null) ptm.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public UserInfo getUserInfoByID(int uID) {
        String query = "SELECT * FROM dbo.infor WHERE uID = ?";
        try {
            conn = DBManager.getConnection();
            ptm = conn.prepareStatement(query);
            ptm.setInt(1, uID);
            rs = ptm.executeQuery();
            while (rs.next()) {
                return new UserInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    
}
