package mychat.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WriteHandler implements Runnable {

    private final Socket socket;
    private final DataOutputStream output;
    private final String id;
    private boolean closed;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public WriteHandler(Socket socket, DataOutputStream output, String id) throws IOException {
        this.socket = socket;
        this.output = output;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
//                System.out.print("메세지를 입력하세요 : ");
                String message = input.readLine();

                if (message.equals("exit")) {
                    output.writeUTF("[" + id +"] 님이 퇴장하셨습니다.");
                    break;
                }

                output.writeUTF("[" + id +"] " + message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
