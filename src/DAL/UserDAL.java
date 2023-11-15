package DAL;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import DTO.UserDTO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAL extends MyDatabaseManager {

    public UserDAL() {
        UserDAL.connectDB();
    }

    public ArrayList readUser() throws SQLException {
        String query = "SELECT * FROM user";
        ResultSet rs = UserDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                UserDTO u = new UserDTO();
                u.setEmail(rs.getString("email"));
                list.add(u);
            }
        }
        return list;
    }

    public ArrayList readOnlineUser() throws SQLException {
        String query = "SELECT * FROM user WHERE LogStatus = 1";
        ResultSet rs = UserDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                UserDTO u = new UserDTO();
                u.setEmail(rs.getString("email"));
                u.setLogStatus(rs.getBoolean("status"));
                list.add(u);
            }
        }
        return list;
    }

    public UserDTO getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setString(1, email);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            UserDTO u = new UserDTO();
            u.setEmail(rs.getString("email"));
            u.setLogStatus(rs.getBoolean("status"));
            u.setMoney(rs.getInt("money"));
            return u;
        }

        return null;
    }

    //update 16/12 by Quoc An
//    public UserDTO getUserByUsername(String Username) throws SQLException {
//        String query = "SELECT * FROM user WHERE Username = ?";
//        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
//        p.setString(1, Username);
//        ResultSet rs = p.executeQuery();
//        UserDTO u = new UserDTO();
//        if (rs.next()) {
//
//            u.setUserID(rs.getInt("UserID"));
//            u.setUsername(rs.getString("Username"));
//            u.setFullname(rs.getString("Fullname"));
//            u.setDateofBirth(Date.valueOf(rs.getString("Birth")));
//            u.setGender(rs.getBoolean("Gender"));
//            return u;
//        }
//        return null;
//    }
    //update 16/12 by Quoc An

    public UserDTO getUser(String email, String Password) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setString(1, email);
        p.setString(2, Password);
        ResultSet rs = p.executeQuery();
        UserDTO u = new UserDTO();
        if (rs.next()) {
            u.setEmail(rs.getString("email"));
            u.setLogStatus(rs.getBoolean("status"));
            u.setMoney(rs.getInt("money"));
            u.setPassword(rs.getString("Password"));
            return u;
        }
        return null;
    }
//
    public int insertUser(String Email, String Password) {
        String query = "INSERT INTO user (Email, Password, Status, Money) VALUES (?, ?, ?, ?)";
        PreparedStatement p;
        int result = 0;
        try {
            p = UserDAL.getConnection().prepareStatement(query);
            p.setString(1, Email);
            p.setString(2, Password);
            p.setBoolean(3, true);
            p.setInt(4, 0);
            result = p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
//
//    public int updateUser(String Username, String Fullname, boolean Gender, String Birth) throws SQLException {
//        String query = "UPDATE user SET Fullname = ? , Gender = ?, Birth = ? WHERE Username = ?";
//        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
//        p.setString(1, Fullname);
//        p.setBoolean(2, Gender);
//        p.setString(3, Birth);
//        p.setString(4, Username);
//        int result = p.executeUpdate();
//        return result;
//    }
//
//    public UserDTO getBlockStatus(String Username) throws SQLException {
//        String query = "SELECT UserID, BlockAddExam, BlockTakeExam FROM user WHERE Username = ?";
//        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
//        p.setString(1, Username);
//        ResultSet rs = p.executeQuery();
//        UserDTO u = new UserDTO();
//        if (rs.next()) {
//            u.setUserID(rs.getInt("UserID"));
//            u.setBlockAddExam(rs.getBoolean("BlockAddExam"));
//            u.setBlockTakeExam(rs.getBoolean("BlockTakeExam"));
//            return u;
//        }
//        return null;
//    }

    public int blockLogin(int UserID, boolean block) throws SQLException {
        String query = "UPDATE user SET BlockLogin = ? WHERE UserID = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setBoolean(1, block);
        p.setInt(2, UserID);
        int result = p.executeUpdate();
        return result;
    }

    public int blockAddExam(int UserID, boolean block) throws SQLException {
        String query = "UPDATE user SET BlockAddExam = ? WHERE UserID = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setBoolean(1, block);
        p.setInt(2, UserID);
        int result = p.executeUpdate();
        return result;
    }

    public int blockTakeExam(int UserID, boolean block) throws SQLException {
        String query = "UPDATE user SET BlockTakeExam = ? WHERE UserID = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setBoolean(1, block);
        p.setInt(2, UserID);
        int result = p.executeUpdate();
        return result;
    }

    public int getNumberOfUser(String str) throws SQLException {
        String query;
        if (str.equals("online")) {
            query = "SELECT COUNT(*) as usercount FROM user WHERE status = 1";
        } else {
            query = "SELECT COUNT(*) as usercount FROM user";
        }
        ResultSet rs = UserDAL.doReadQuery(query);
        rs.next();
        int count = rs.getInt("usercount");
        return count;
    }

    public int changePassword(String email, String Password) throws SQLException {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setString(1, Password);
        p.setString(2, email);
        int result = p.executeUpdate();
        return result;
    }

    public int Logon(String Email) throws SQLException {
        String query = "UPDATE user SET status = 1 WHERE email = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setString(1, Email);
        int result = p.executeUpdate();
        return result;
    }

    public int Logout(String Email) throws SQLException {
        String query = "UPDATE user SET Status = 0 WHERE Email = ?";
        PreparedStatement p = UserDAL.getConnection().prepareStatement(query);
        p.setString(1, Email);
        int result = p.executeUpdate();
        return result;
    }

    public int addMoney(String Email, int Money) {
        String query = "UPDATE user SET Money = ? WHERE Email = ?";
        PreparedStatement p;
        int result = 0;
        try {
            p = UserDAL.getConnection().prepareStatement(query);
            p.setInt(1, Money);
            p.setString(2, Email);
            result = p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
