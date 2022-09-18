package Client;

import java.io.*;
import java.net.Socket;

public class ClientSendThread  extends Thread{
    private Socket ClientSocket;
    private DataOutputStream out;
    private String text;
    private String name;
    private BufferedReader keyboard;

    public ClientSendThread(Socket socket) throws Exception{
        ClientSocket = socket;
        out = new DataOutputStream(ClientSocket.getOutputStream());
        keyboard = new BufferedReader(new InputStreamReader(System.in));

    }

    public void run(){
        try{
            while(true){
                text = keyboard.readLine();
                out.writeUTF(text);
                if(text.equals("@quit")) {
                    ClientSocket.close();
                    break;
                }
                if(text.equals("@send filename")){
                    int bytes = 0;
                    text = keyboard.readLine();
                    String [] tmp = text.split("[.]");
                    out.writeUTF(tmp[1]);
                    File file = new File(text);
                    InputStream in = new BufferedInputStream(new FileInputStream(file));
                    out.writeLong(file.length());
                    byte[] buffer = new byte[8*1024];
                    while ((bytes = in.read(buffer))!=-1){
                        out.write(buffer,0,bytes);
                        out.flush();
                    }
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
