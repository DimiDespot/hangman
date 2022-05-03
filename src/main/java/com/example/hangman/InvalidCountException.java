package com.example.hangman;

class InvalidCountException extends Exception {
    public InvalidCountException() {
    }

    public InvalidCountException(String msg) {
        super(msg);
    }
}