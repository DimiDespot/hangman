package com.example.hangman;

import javafx.application.Application;
import javafx.stage.Stage;


public class Hangman extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        HangmanController hc = new HangmanController();
        hc.showStage();
    }

    public static void main(String[] args) {
        launch();
    }
}
