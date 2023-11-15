package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DTO.UserDTO;
import DAL.UserDAL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserBLL {

    UserDAL uDAL;

    public UserBLL() {
        uDAL = new UserDAL();
    }

    public List LoadAllUser() throws SQLException {
        ArrayList list = uDAL.readUser();
        return list;
    }

    public List LoadOnlineUser() throws SQLException {
        ArrayList list = uDAL.readOnlineUser();
        return list;
    }

    public UserDTO getUserByID(String email) throws SQLException {
        UserDTO u = uDAL.getUserByEmail(email);
        return u;
    }

    public UserDTO getUser(String Username,String password) throws SQLException {
        UserDTO u = uDAL.getUser(Username, password);//password
        return u;
    }

    public int Logon(String Email) throws SQLException {
        int result = uDAL.Logon(Email);
        return result;
    }

    public int Logout(String Email) throws SQLException {
        int result = uDAL.Logout(Email);
        return result;
    }
    
    public int insertUser(String Email, String Password) throws SQLException{
         int result = uDAL.insertUser(Email,Password);
        return result;
    }
    
    public int addMoney(String Email,int Money) throws SQLException{
         int result = uDAL.addMoney(Email,Money);
        return result;
    }

//  
}
