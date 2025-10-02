package network.exception.close.reset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static util.MyLogger.log;

public class ResetCloseClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결: " + socket);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // 1. client <- server: FIN
        // 서버가 close() 호출할 때까지 잠시 대기
        Thread.sleep(1000);

        // 2. client -> server: PUSH[1]
        output.write(1);

        // 3. client <- server: RST (Reset packet). 연결 종료하라는 의미
        // RST 메시지 전송 대기
        Thread.sleep(1000);

        try {
            int read = input.read();
            System.out.println("read = " + read);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            output.write(1);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
