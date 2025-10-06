package mychat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ChatClient {

    private static final int PORT = 12345;

    private static ReadHandler readHandler;
    private static WriteHandler writeHandler;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

//            log("소켓 연결: " + socket);

            Scanner scanner = new Scanner(System.in);
            System.out.print("ID : ");
            String id = scanner.nextLine();
            output.writeUTF("ID : " + id);
//            System.out.println("[" + id + "] 님이 채팅방에 입장하셨습니다");

            readHandler = new ReadHandler(socket, input);
            writeHandler = new WriteHandler(socket, output, id);

            Thread readThread = new Thread(readHandler);
            Thread writeThread = new Thread(writeHandler);

            readThread.start();
            writeThread.start();

            writeThread.join();
            readThread.interrupt();
//            readThread.join();

            System.out.println("[" + id + "] 님이 퇴장하셨습니다");

        } catch (IOException | InterruptedException e) {
            log(e);
        }
    }
}
