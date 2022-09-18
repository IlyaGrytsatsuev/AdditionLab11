package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket server_socket;
    private Socket serverToClientSocket;
    private int port;
    private HashMap<String, Socket> ClientMap = new HashMap<>();


    public Server(int port_) throws Exception{
        port = port_;
        server_socket = new ServerSocket(port);

    }

    public void start() throws Exception{

                serverToClientSocket = server_socket.accept();
                ServerReceiveThread thread1 = new ServerReceiveThread(serverToClientSocket);
                ServerSendThread thread2 = new ServerSendThread(serverToClientSocket);
                thread1.start();
                thread2.start();
    }
}
