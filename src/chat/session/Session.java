package chat.session;

import network.tcp.SocketCloseUtil;
import network.tcp.v6.SessionManagerV6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class Session implements Runnable {

    private static final String ID_SEPARATOR = "ID : ";

    private final Socket socket;
    private ChatSession session;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private boolean closed;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                String message = input.readUTF();
//                log("client -> server: " + received);

                if (message.startsWith(ID_SEPARATOR)) {
                    String id = message.split(ID_SEPARATOR)[1];

                    ChatSession session = new ChatSession(id, socket, input, output);
                    sessionManager.add(session);
                    this.session = session;

                    sessionManager.sendTo("[" + session.getId() + "] 님 입장하셨습니다.");
                }

                if (message.equals("exit")) {
                    break;
                }

                // 클라이언트에게 문자 보내기
                sessionManager.sendTo(message);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(session);
            close();
        }
    }

    // 세션 종료시, 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        if (closed) {
            return;
        }

        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
//        log("연결 종료: " + socket + " isClosed: " + socket.isClosed());
        System.out.println("[" + session.getId() + "] 님 퇴장하셨습니다.");
    }
}
