package com.arabcoderz.ezcode;

public class ListChallenges {
    private int id;
    private String challenge_title;
    private String challenge_programming_language;
    private String challenge_level;
    private String challenge_points;

    public ListChallenges(int id, String challenge_title, String challenge_programming_language, String challenge_level, String challenge_points) {
        this.id = id;
        this.challenge_title = challenge_title;
        this.challenge_programming_language = challenge_programming_language;
        this.challenge_level = challenge_level;
        this.challenge_points = challenge_points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChallenge_title() {
        return challenge_title;
    }

    public void setChallenge_title(String challenge_title) {
        this.challenge_title = challenge_title;
    }

    public String getChallenge_programming_language() {
        return challenge_programming_language;
    }

    public void setChallenge_programming_language(String challenge_programming_language) {
        this.challenge_programming_language = challenge_programming_language;
    }

    public String getChallenge_level() {
        return challenge_level;
    }

    public void setChallenge_level(String challenge_level) {
        this.challenge_level = challenge_level;
    }

    public String getChallenge_points() {
        return challenge_points;
    }

    public void setChallenge_points(String challenge_points) {
        this.challenge_points = challenge_points;
    }
}
