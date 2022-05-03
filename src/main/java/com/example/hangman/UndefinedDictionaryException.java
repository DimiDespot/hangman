package com.example.hangman;

public class UndefinedDictionaryException extends Exception {
    public UndefinedDictionaryException() {
    }

    public UndefinedDictionaryException(String msg) {
        super(msg);
    }
}