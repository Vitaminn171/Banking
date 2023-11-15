/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package banking;

import static BLL.Controller.startConnectToServer;
import com.formdev.flatlaf.FlatDarkLaf; 
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author Quoc An
 */
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        FlatLightLaf.setup(); 

        
        startConnectToServer();
    }
    
}
