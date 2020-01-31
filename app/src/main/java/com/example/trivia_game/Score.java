package com.example.trivia_game;

/**
 * @author khaleel esa
 * this is a simple Score class that crates a socre opject
 * so we can store the user score with his email in the database
 */
public class Score {
    private int user_score;
    private String user_email;


    public  Score(){

    }
    public Score(int user_score,String user_email) {
        this.user_email = user_email;
        this.user_score= user_score;
    }

    public int getUser_score() {
        return user_score;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    @Override
    public String toString(){
        return this.user_email + " / " + this.user_score + " . ";
    }

}
