package chat.server;

import chat.session.Session;
import chat.session.SessionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ChatServer {
    private static final int PORT = 12345;
    private static final SessionManager sessionManager = new SessionManager();

    public static void main(String[] args) throws IOException {

        log("채팅 서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("채팅 서버 소켓 시작 - 리스닝 포트: " + PORT);

        try {
            while (true) {
                Socket socket = serverSocket.accept(); // 블로킹 메서드
//                log("소켓 연결: " + socket);

                Session session = new Session(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (Exception e) {
            log("채팅 서버 소켓 종료: " + e);
        }


    }
}
