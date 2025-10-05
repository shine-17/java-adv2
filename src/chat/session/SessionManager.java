package chat.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private final List<ChatSession> sessions = new ArrayList<>();

    public synchronized void add(ChatSession session) {
        sessions.add(session);
    }

    public synchronized void remove(ChatSession session) {
        sessions.remove(session);
    }

    public synchronized List<ChatSession> getSessions() {
        return new ArrayList<>(sessions);
    }

    public void sendTo(String message) {
        for (ChatSession session : sessions) {
            try {
                session.sendTo(message);
            } catch (IOException e) {
                System.out.println(session.getId() + " 님 메세지 전송오류가 발생하였습니다.");
            }
        }
    }

}
