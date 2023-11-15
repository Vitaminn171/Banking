/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author bunhu
 */
public class ServerConnection implements Runnable{
    private Socket server;
    private BufferedReader in;
    private BufferedWriter out;
    public ServerConnection(Socket server) throws IOException {
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
    }
    
    @Override
    public void run() {
        
    }
    
}
