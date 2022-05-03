package com.example.hangman;

public class UnbalancedException extends Exception {
    public UnbalancedException() {
    }

    public UnbalancedException(String msg) {
        super(msg);
    }
}