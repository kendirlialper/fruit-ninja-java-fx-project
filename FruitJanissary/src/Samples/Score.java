package Samples;

import java.util.Date;

public class Score {
    private String userName;
    private int score;
    private String date;
    private int time;

    public Score(String userName, int score, String date, int time) {
        this.userName = userName;
        this.score = score;
        this.date = date;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBirthDate() {
        return date;
    }

    public void setBirthDate(Date birthDate) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
