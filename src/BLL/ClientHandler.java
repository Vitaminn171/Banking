/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import static BLL.Controller.socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.json.JSONObject;


/**
 *
 * @author thieu
 */
public class ClientHandler implements Runnable{
    public Socket client ;
    private BufferedReader in;
    private BufferedWriter out;
    private SecretKey key;
    private RSA rsa;
    
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        rsa = new RSA();
        getClientKey();
    }
    
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
    
    private void getClientKey() throws IOException{
        PrivateKey privateKey= rsa.getPrivateKey();
        PublicKey publicKey=rsa.getPublicKey();
        out.write(encode(publicKey.getEncoded()));
        out.newLine();
        out.flush();

        key = AES.StringtoSecretKey(rsa.Descrpytion(in.readLine(), privateKey));
    }
    
    
    @Override
    public void run() {
        try {
            while (true) {                
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                AES aes= new AES();
                if (line.equals("bye")) {
                    break;
                }
                String data = AES.decrypt(line, key);
                Controller_Server con_admin = new Controller_Server();
                JSONObject json = new JSONObject(data);
                json = con_admin.checkFunction(json);
                line = json.toString();
                StringBuilder newline = new StringBuilder();
                newline.append(line);
                line = newline.toString();
                
                data = AES.encrypt(line, key);
                out.write(data);
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            client.close();
        } catch (Exception e) {
            System.err.println(e);
        } 
    }
    
    
}
