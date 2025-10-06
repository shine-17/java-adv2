package mychat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadHandler implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private boolean closed;

    public ReadHandler(Socket socket, DataInputStream input) throws IOException {
        this.socket = socket;
        this.input = input;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = input.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                break;
            }
        }

    }
}
