package gvs03_sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private final int PORT = 9737;

    public void start() throws IOException {
        System.out.println("ПРОГРАММА СЕРВЕР НА СОКЕТАХ TCP/IP");

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Не могу подключиться к порту " + PORT + "!");
            System.err.println("Завершение работы");
            System.exit(0);
        }

        while (true) {
            try {
                System.out.println("Ожидание подключения клиента ...");
                clientSocket = serverSocket.accept();
                System.out.println("Подключен клиент с адресом " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Ошибка при ожидании клиента");
                System.err.println("Завершение работы");
                System.exit(0);
            }

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.println("Ожидание задания от клиента ...");
            double a = Double.parseDouble(receive(in));
            double b = Double.parseDouble(receive(in));
            double x = Double.parseDouble(receive(in));
            String lastMessage = receive(in);

            System.out.println("Задание от клиента получено. Обработка задания ...");
            double result = solve(a, b, x);

            System.out.println("Передача ответа клиенту");
            out.println(result);

            if (lastMessage.equalsIgnoreCase("stop")) {
                break;
            }
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("РАБОТА СЕРВЕРА ЗАВЕРШЕНА");
    }

    private String receive(BufferedReader in) throws IOException {
        String messageFromClient;
        while ((messageFromClient = in.readLine()) != null) {
            break;
        }
        return messageFromClient;
    }

    private double solve(double a, double b, double x) {
        double result;
        if (x <= 4) {
            result = ((a * a) / (x * x)) + 6 * x;
        } else {
            result = b * b * ((4 + x) * (4 + x));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        server.stop();
    }
}
