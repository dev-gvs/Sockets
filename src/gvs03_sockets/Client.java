package gvs03_sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private final String IP = "127.0.0.1";
    private final int PORT = 9737;

    public void start() throws IOException {
        System.out.println("ПРОГРАММА КЛИЕНТ НА СОКЕТАХ TCP/IP");
        System.out.println("Подключение к серверу " + IP + ":" + PORT + "...");

        try {
            clientSocket = new Socket(IP, PORT);
            System.out.println("Подключение к серверу успешно");
        } catch (IOException e) {
            System.err.println("Не могу подключиться к серверу!");
            System.err.println("Завершение работы");
            System.exit(0);
        }

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.println("Передаем задание серверу:");
            double a = 4;
            double b = 0;
            double x = 4;

            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("x = " + x);

            out.println(a);
            out.println(b);
            out.println(x);
            out.println("stop");

            System.out.println("Ожидание ответа от сервера ...");
            String result = in.readLine();
            System.out.println("СЕРВЕР ПОСЛАЛ ОТВЕТ: " + result);
        } catch (Exception e) {
            System.err.println("Не могу получить ответ от сервера!");
            System.err.println("Завершение работы");
            System.exit(0);
        }
        System.out.println("РАБОТА КЛИЕНТА ЗАВЕРШЕНА");
    }

    public void stop() throws IOException {
        out.close();
        in.close();
        clientSocket.close(); 
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.start();
        // client.stop();
    }
}
