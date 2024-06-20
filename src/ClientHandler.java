import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket s;

    public ClientHandler(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            System.out.println("Nuovo client connesso! IP: " + s.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            String input;
            while (!(input = in.readLine()).isEmpty()) {
                sb.append(input).append("\n");
            }

            System.out.println("Nuova richiesta ricevuta da  \n" + sb);

            String rispostaHTTP = """
                    HTTP/1.1
                    \r
                    <html>
                    <link rel="stylesheet" href="risposta.css">
                    <body>
                        <h1>ESEMPIO HTTP TCP</h1>
                        <ul>
                            <li><a href="https://docs.google.com/document/d/1GByqXbc2Cm-WLsBujwx5Jya8QOjDdFYP3sgj2amH44o/edit?usp=sharing">Relazione Ndiaye</a> </li>
                            <li><a href="https://developer.mozilla.org/en-US/docs/Web/HTTP">Documentazione HTTP</a></li>
                            <li><a href="https://developer.mozilla.org/en-US/docs/Glossary/TCP">Documentazione TCP</a></li>
                        </ul>
                    </body>
                    </html>
                    """;

            bw.write(rispostaHTTP);
            bw.flush();
            System.out.println("Risposta inviata con successo");
            System.out.println();

            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

