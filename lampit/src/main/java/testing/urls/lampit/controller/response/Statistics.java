package testing.urls.lampit.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {
    @JsonProperty("time_taken")
    private String timeTaken;

    public Statistics(String timeTaken) {
        this.timeTaken = timeTaken;
    }

}