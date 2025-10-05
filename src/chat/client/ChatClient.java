package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ChatClient {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

//            log("소켓 연결: " + socket);

            Scanner scanner = new Scanner(System.in);
            System.out.print("ID : ");
            String id = scanner.nextLine();
            output.writeUTF(id);

            while (true) {
                System.out.print("전송 문자: ");
                String toSend = scanner.nextLine();

                // 서버에게 문자 보내기
                output.writeUTF(toSend);
                log("client -> server: " + toSend);

                if (toSend.equals("exit")) {
                    break;
                }

                // 서버로부터 문자 받기
                String received = input.readUTF();
                log("client <- server: " + received);
            }
        } catch (IOException e) {
            log(e);
        }
    }
}
