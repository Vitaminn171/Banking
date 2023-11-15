package DTO;

import java.util.Date;

public class UserDTO {
    String Email, Password;
    private int money;
    boolean Logstatus;

    public UserDTO() {
    }

    public UserDTO(String Email, String Password, boolean LogStatus, int money) {
        this.Email = Email;
        this.Password = Password;
        this.Logstatus = LogStatus;
        this.money = money;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
   
    public boolean isLogStatus() {
        return Logstatus;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    public void setLogStatus(boolean Logstatus) {
        this.Logstatus = Logstatus;
    }

    /**
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
