/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.TransactionDAL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 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
 * @author Quoc An
 */
public class TransactionBLL {

    TransactionDAL tDAL;

    public TransactionBLL() {
        tDAL = new TransactionDAL();
    }

    public List LoadAllTransaction(String email) throws SQLException {
        ArrayList list = tDAL.readTransaction(email);
        return list;
    }

    public List LoadRecentTransaction(String email) throws SQLException {
        ArrayList list = tDAL.readTransaction_recent(email);
        return list;
    }

    public List LoadTransactionDate(String email, String date) throws SQLException {
        ArrayList list = tDAL.readTransactionDate(email, date);
        return list;
    }

    public List LoadTransactionBetweenDate(String email, String fromDate, String toDate) throws SQLException {
        ArrayList list = tDAL.readTransactionBetweenDay(email, fromDate, toDate);
        return list;
    }

    public int TransferMoney(String email, String date, String note, int money, String receiver) throws SQLException {
        int result = tDAL.transferMoney(email, date, note, money, receiver);
        return result;
    }
}
