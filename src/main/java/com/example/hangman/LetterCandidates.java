package com.example.hangman;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LetterCandidates {
    SimpleIntegerProperty position;
    SimpleStringProperty pos_list;

    public LetterCandidates(int p, String pl) {
        this.position = new SimpleIntegerProperty(p);
        this.pos_list = new SimpleStringProperty(pl);
    }

    public void setPos_list(String pos_list) {
        this.pos_list.set(pos_list);
    }

    public void setPosition(int position) {
        this.position.set(position);
    }

    public String getPos_list() {
        return pos_list.get();
    }

    public int getPosition() {
        return position.get();
    }
}
