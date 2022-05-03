package com.example.hangman;

import java.util.ArrayList;

public class LetterPair {
    char letter;
    ArrayList<Character> pos_list;

    public LetterPair(char l, ArrayList<Character> p) {
        this.letter = l;
        this.pos_list = p;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public ArrayList<Character> getPos_list() {
        return pos_list;
    }

    public void setPos_list(ArrayList<Character> pos_list) {
        this.pos_list = pos_list;
    }
}
