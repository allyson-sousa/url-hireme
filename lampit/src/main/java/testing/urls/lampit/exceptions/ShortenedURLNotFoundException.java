package testing.urls.lampit.exceptions;

public class ShortenedURLNotFoundException extends RuntimeException {

    public ShortenedURLNotFoundException() {
        super("SHORTENED URL NOT FOUND");
    }
}

