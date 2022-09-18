package Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerReceiveThread extends Thread{

    private Socket ToClientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    String input;
    String TargetName;
    String name = "Uncknown";

    public ServerReceiveThread(Socket socket) {
        try {
            ToClientSocket = socket;
            in = new DataInputStream(ToClientSocket.getInputStream());

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                input = in.readUTF();
                if (input.equals("@quit")) {
                    System.out.println("User " + name + " has left");
                    ToClientSocket.close();
                    break;
                }
                if(input.startsWith("@name")){
                    name = input.substring(6);
                    continue;
                }
                if(input.equals("@send filename")){
                    int bytes = 0;
                    input = in.readUTF();
                    FileOutputStream fileOutputStream = new FileOutputStream("/Users/gratchuvalsky/Desktop/NewFile" + Math.random()*100 + "." + input );
                    long size = in.readLong();
                    byte[] buffer = new byte[8*1024];
                    while (size > 0 && (bytes = in.read(buffer, 0, (int)size)) != -1) {
                        fileOutputStream.write(buffer,0,bytes);
                        size -= bytes;
                    }
                    continue;
                }

                System.out.println(name + ": " + input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
