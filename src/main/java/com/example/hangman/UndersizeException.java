package com.example.hangman;

public class UndersizeException extends Exception {
    public UndersizeException() {
    }

    public UndersizeException(String msg) {
        super(msg);
    }
}