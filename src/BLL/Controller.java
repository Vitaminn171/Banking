/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DTO.UserDTO;
import GUI.Home;
import GUI.Login;
//import GUI.Login;
import com.google.gson.Gson;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKey;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Quoc An
 */
public class Controller {

    public static Socket socket = null;
    public static Gson gson = new Gson();
    public static Thread t;
    static BufferedWriter out;
    static BufferedReader in;
    static ExecutorService excutor;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static SecretKey key;
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    //^                                   # start of line
    //  (?=.*[0-9])                       # positive lookahead, digit [0-9]
    //  (?=.*[a-z])                       # positive lookahead, one lowercase character [a-z]
    //  (?=.*[A-Z])                       # positive lookahead, one uppercase character [A-Z]
    //  (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the special character in this [..]
    //  .                                 # matches anything
    //  {8,20}                            # length at least 8 characters and maximum of 20 characters
    //$                                   # end of line

    public static final Pattern VALID_OTP_REGEX = Pattern.compile("^[0-9]{1,6}$");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateOTP(String OTPStr) {
        Matcher matcher = VALID_OTP_REGEX.matcher(OTPStr);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
    
    private static void sendKey() throws IOException, Exception{
        while(true) {
            
            String line = in.readLine();
            if(line!=null){
//                System.out.println(line);
                AES aes = new AES();
                aes.init();
//                System.out.println(aes.getSecretKey());
                String key_send = aes.getSecretKey();
                RSA rsa = new RSA();
                X509EncodedKeySpec spec= new X509EncodedKeySpec(decode(line));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PublicKey publicKey = kf.generatePublic(spec);
                String data =rsa.Encrpytion(key_send, publicKey);
                key = AES.StringtoSecretKey(key_send);
//                System.out.println("Key :"+key);
//                System.out.println("En :"+aes.encrypt("Huỳnh Hoàng Huy", aes.getSKey()));
                out.write(data);
                out.newLine();
                out.flush();
                
                return;
            }
               
        }
        
    }
    
    public static void startConnectToServer() throws Exception {
        try {
            //TODO: use api to auto get ip from server
            // Lên trang mà server để local ip để lấy về
            Document doc = Jsoup.connect("https://retoolapi.dev/FFY4oG/data/1")
                    .ignoreContentType(true).ignoreHttpErrors(true)
                    .header("Content-Type", "application/json")
                    .method(Connection.Method.GET).execute().parse();
            JSONObject jsonObject = new JSONObject(doc.text());
            System.out.println(jsonObject.get("ip")); //local ip của server
            InetAddress svAddress = InetAddress.getByName(jsonObject.get("ip").toString());
            socket = new Socket(svAddress, 6666);//kết nối tới server
            System.out.println("connecting...");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendKey();
            new Login().setVisible(true);
        } catch (UnknownHostException e) {
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Server not available!");

        }
    }

    public static void closeConnectToServer() throws IOException {
        //close

        in.close();
        out.close();
        socket.close();
        System.out.println("closeConnectToServer");
    }

    public String SendReceiveData(String data) throws IOException, Exception {
        String encryptedData = AES.encrypt(data, key);
        out.write(encryptedData);
        out.newLine();
        out.flush();
        String dataReceive = in.readLine();
        return AES.decrypt(dataReceive, key);
    }

    public String convertToJSON(Map<String, String> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return json;
    }

    public String generateOTP() {

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[6];

        for (int i = 0; i < 6; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }
}

