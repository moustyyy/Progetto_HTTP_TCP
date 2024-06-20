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

            String html = """
                    <html lang="">
                    <head>
                        <meta charset="UTF-8">
                        <title>Richiesta HTTP</title>
                        <link rel="stylesheet" href="risposta.css">
                    </head>
                    <body>
                        <h1>ESEMPIO PAGINA HTTP TCP</h1>
                        <ul>
                            <li><a href="https://docs.google.com/document/d/1GByqXbc2Cm-WLsBujwx5Jya8QOjDdFYP3sgj2amH44o/edit?usp=sharing">Relazione Ndiaye</a> </li>
                            <li><a href="https://developer.mozilla.org/en-US/docs/Web/HTTP">Documentazione HTTP</a></li>
                            <li><a href="https://developer.mozilla.org/en-US/docs/Glossary/TCP">Documentazione TCP</a></li>
                        </ul>
                    </body>
                    </html>
                    """;
            String CRLF = "\r\n";

            /* ho imparato a usare questa struttura con un tutorial su youtube.
             il problema che non sono riuscito a trovare un modo per mettere il messaggio http nell'header
             di conseguenza nella pagina html richiesta dal client appaiono i dettagli HTTP.
             ho visto che su youtube molti hanno optato per ad usare InputStream e OutputStream che al momento non so usare bene.
             in teoria al caricamento del file html il browser non dovrebbe mostrare l'header HTTP.
              */

            String rispostaHTTP =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-Type: text/html" + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF;

            bw.write(rispostaHTTP);
            bw.flush();
            System.out.println("Pagina Richiesta dal client inviata con successo");
            System.out.println();

            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

