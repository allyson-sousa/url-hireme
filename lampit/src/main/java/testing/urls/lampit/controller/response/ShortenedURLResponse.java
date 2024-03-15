package testing.urls.lampit.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenedURLResponse {

    private String alias;
    private String url;
    private Statistics statistics;

    public ShortenedURLResponse(String alias, String url, String timeTaken) {
        this.alias = alias;
        this.url = url;
        this.statistics = new Statistics(timeTaken);
    }

}

