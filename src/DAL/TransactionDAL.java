/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.TransactionDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Quoc An
 */
public class TransactionDAL extends MyDatabaseManager{
    
    public TransactionDAL() {
        TransactionDAL.connectDB();
    }
    
    public ArrayList readTransaction(String email) throws SQLException {
        String query = "SELECT * FROM transaction where receiver = ? OR sender = ? ORDER BY date DESC";
        PreparedStatement p = TransactionDAL.getConnection().prepareStatement(query);
        p.setString(1, email);
        p.setString(2, email);
        ResultSet rs = p.executeQuery();
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                TransactionDTO t = new TransactionDTO();
                t.setId(rs.getInt("transaction_id"));
                t.setSender(rs.getString("sender"));
                t.setReceiver(rs.getString("receiver"));
                t.setNote(rs.getString("note"));
                t.setTotal(rs.getInt("total"));
                t.setDate(rs.getString("date"));
                list.add(t);
            }
        }
        return list;
    }
    
    public ArrayList readTransaction_recent(String email) throws SQLException {
        String query = "SELECT * FROM transaction where receiver = ? OR sender = ? ORDER BY date DESC LIMIT 3 ";
        PreparedStatement p = TransactionDAL.getConnection().prepareStatement(query);
        p.setString(1, email);
        p.setString(2, email);
        ResultSet rs = p.executeQuery();
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                TransactionDTO t = new TransactionDTO();
                t.setId(rs.getInt("transaction_id"));
                t.setSender(rs.getString("sender"));
                t.setReceiver(rs.getString("receiver"));
                t.setNote(rs.getString("note"));
                t.setTotal(rs.getInt("total"));
                t.setDate(rs.getString("date"));
                list.add(t);
            }
        }
        return list;
    }
    
    public ArrayList readTransactionDate(String email, String date) throws SQLException {
        String query = "SELECT * FROM transaction where (receiver = ? OR sender = ?) AND date = ? ORDER BY date DESC";
        PreparedStatement p = TransactionDAL.getConnection().prepareStatement(query);
        p.setString(3, date);
        p.setString(1, email);
        p.setString(2, email);
        ResultSet rs = p.executeQuery();
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                TransactionDTO t = new TransactionDTO();
                t.setId(rs.getInt("transaction_id"));
                t.setSender(rs.getString("sender"));
                t.setReceiver(rs.getString("receiver"));
                t.setNote(rs.getString("note"));
                t.setTotal(rs.getInt("total"));
                t.setDate(rs.getString("date"));
                list.add(t);
            }
        }
        return list;
    }
    
    public ArrayList readTransactionBetweenDay(String email, String fromDate, String toDate) throws SQLException {
        String query = "SELECT * FROM transaction where (receiver = ? OR sender = ?) AND date BETWEEN ? AND ? ORDER BY date DESC";
        PreparedStatement p = TransactionDAL.getConnection().prepareStatement(query);
        p.setString(3, fromDate);
        p.setString(4, toDate);
        p.setString(1, email);
        p.setString(2, email);
        ResultSet rs = p.executeQuery();
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                TransactionDTO t = new TransactionDTO();
                t.setId(rs.getInt("transaction_id"));
                t.setSender(rs.getString("sender"));
                t.setReceiver(rs.getString("receiver"));
                t.setNote(rs.getString("note"));
                t.setTotal(rs.getInt("total"));
                t.setDate(rs.getString("date"));
                list.add(t);
            }
        }
        return list;
    }
    
    public TransactionDTO getTransactionByID(int id) throws SQLException {
        String query = "SELECT * FROM transaction WHERE transaction_id = ?";
        PreparedStatement p = TransactionDAL.getConnection().prepareStatement(query);
        p.setInt(1, id);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            TransactionDTO t = new TransactionDTO();
            t.setSender(rs.getString("sender"));
            t.setReceiver(rs.getString("receiver"));
            t.setNote(rs.getString("note"));
            t.setDate(rs.getString("date"));
            t.setTotal(rs.getInt("total"));
            return t;
        }

        return null;
    }
    
    public int transferMoney(String email, String date, String note, int money, String receiver) {
        String query_transfer = "INSERT INTO transaction (sender, receiver, total, date, note) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement p_transfer; 
        int result_transfer = 0;
        try {
            p_transfer = TransactionDAL.getConnection().prepareStatement(query_transfer);
            p_transfer.setString(1, email);
            p_transfer.setString(2, receiver);
            p_transfer.setInt(3, money);
            p_transfer.setString(4, date);
            p_transfer.setString(5, note);
            result_transfer = p_transfer.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result_transfer;
    }
}
