package com.arabcoderz.ezcode;

public class List_challenges {
    private int id;
    private String challenge_title;
    private String challenge_content;
    private String challenge_programming_language;
    private String challenge_level;
    private String challenge_answer;
    private int challenge_points;

    public List_challenges(int id, String challenge_title, String challenge_content, String challenge_programming_language, String challenge_level, String challenge_answer, int challenge_points) {
        this.id = id;
        this.challenge_title = challenge_title;
        this.challenge_content = challenge_content;
        this.challenge_programming_language = challenge_programming_language;
        this.challenge_level = challenge_level;
        this.challenge_answer = challenge_answer;
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

    public String getChallenge_content() {
        return challenge_content;
    }

    public void setChallenge_content(String challenge_content) {
        this.challenge_content = challenge_content;
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

    public String getChallenge_answer() {
        return challenge_answer;
    }

    public void setChallenge_answer(String challenge_answer) {
        this.challenge_answer = challenge_answer;
    }

    public int getChallenge_points() {
        return challenge_points;
    }

    public void setChallenge_points(int challenge_points) {
        this.challenge_points = challenge_points;
    }
}
