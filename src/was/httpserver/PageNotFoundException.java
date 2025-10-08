package was.httpserver;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(String message) {
        super(message);
    }
}
