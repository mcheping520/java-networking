//import java.io.DataInputStream;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketTimeoutException;

import java.io.*;
import java.net.*;

public class GreetingServer extends Thread {
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run(){
        while(true){
            try{
                System.out.println("等待远程链接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("听我说谢谢你：" + server.getLocalSocketAddress() + "\n GoodBye!");
                server.close();
            }catch (SocketTimeoutException s){
                System.out.println("Socket timed out!");
                break;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
        try {
            Thread t = new GreetingServer(port);
            t.run();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
