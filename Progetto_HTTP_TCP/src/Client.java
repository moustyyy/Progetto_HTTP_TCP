import java.awt.*;
import java.io.*;
import java.net.*;

public class Client {
    private String host;
    private int porta;

    public Client(String h, int p) {
        this.host = h;
        this.porta = p;
    }

    public void start() {
        try {
            Socket s = new Socket(host, porta);
            try {
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println("Host: " + host);
                out.println();

                File file = new File("risposta.html");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    String risposta;
                    while ((risposta = in.readLine()) != null) {
                        bw.write(risposta);
                        bw.write("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Desktop.isDesktopSupported()) Desktop.getDesktop().browse(file.toURI());

            } finally {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 80);
        client.start();
    }
}
