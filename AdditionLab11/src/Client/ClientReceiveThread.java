package Client;

import java.io.*;
import java.net.Socket;


public class ClientReceiveThread extends Thread {
    private Socket ClientSocket;
    private DataInputStream in;
    private String text;
    private String name = "Uncknown";


    public ClientReceiveThread(Socket socket) throws Exception{
        ClientSocket = socket;
        in = new DataInputStream(ClientSocket.getInputStream());

    }

    public void run() {
        try {

            while(true){
                text = in.readUTF();
                if (text.equals("@quit")) {
                    System.out.println("User " + name + " has left");
                    ClientSocket.close();
                    break;
                }

                if(text.startsWith("@name")){
                    name = text.substring(6);
                    continue;
                }

                if(text.equals("@send filename")){
                    int bytes = 0;
                    text = in.readUTF();
                    FileOutputStream fileOutputStream = new FileOutputStream("/Users/gratchuvalsky/Desktop/NewFile" + Math.random()*100 + "." + text );
                    long size = in.readLong();
                    byte[] buffer = new byte[8*1024];
                    while (size > 0 && (bytes = in.read(buffer, 0, (int)size)) != -1) {
                        fileOutputStream.write(buffer,0,bytes);
                        size -= bytes;
                    }
                    continue;
                }
                }

                System.out.println(name + ": " + text);


        } catch (Exception e) {
            try {
                ClientSocket.close();
            }
            catch (Exception a){
                a.printStackTrace();
            }
        }

    }
}
