package com.example.hangman;

import javafx.beans.property.SimpleStringProperty;

public class logData {
    SimpleStringProperty word;
    SimpleStringProperty attempts;
    SimpleStringProperty winner;

    public logData(String w, int a, char win) {
        this.word = new SimpleStringProperty(w);
        this.attempts = new SimpleStringProperty(String.valueOf(a));
        this.winner = new SimpleStringProperty(Character.toString(win));
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public void setWinner(char winner) {
        this.winner.set(Character.toString(winner));
    }

    public void setAttempts(int attempts) {
        this.attempts.set(String.valueOf(attempts));
    }

    public String getAttempts() {
        return attempts.get();
    }

    public String getWinner() {
        return winner.get();
    }

    public String getWord() {
        return word.get();
    }
}
