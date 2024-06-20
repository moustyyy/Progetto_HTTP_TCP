import java.io.*;
import java.net.*;

public class Server {
    private int porta;

    public Server(int p) {
        this.porta = p;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            while (true) {
                try {
                    Socket s = serverSocket.accept();
                    Thread thread = new Thread(new ClientHandler(s));
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(80);
        System.out.println("Server acceso...");
        server.start();
    }
}
