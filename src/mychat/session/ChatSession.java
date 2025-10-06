package mychat.session;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatSession {
    private final String id;
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    private static final String MESSAGE_FORMAT = "";

    public ChatSession(String id, Socket socket, DataInputStream input, DataOutputStream output) {
        this.id = id;
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    public void sendTo(String message) throws IOException {
        output.writeUTF(message);
    }

    public String getId() {
        return id;
    }
}
