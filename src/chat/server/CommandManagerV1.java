package chat.server;

import java.io.IOException;

public class CommandManagerV1 implements CommandManager {

    private final SessionManager sessionManager;

    public CommandManagerV1(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {
        if (totalMessage.startsWith("/exit")) {
            // IOException이 발생하면 종료 및 자원 정리 로직으로 진행됨
            throw new IOException("exit");
        }

        sessionManager.sendAll(totalMessage);
    }
}
