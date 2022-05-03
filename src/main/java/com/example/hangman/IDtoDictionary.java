package com.example.hangman;

import java.io.IOException;

public class IDtoDictionary {
    private String dictionary_ID;
    private String open_lib_ID;

    public IDtoDictionary(String dictID, String olID) {
        this.dictionary_ID = dictID;
        this.open_lib_ID = olID;
    }

    public void create() throws IOException, UnbalancedException, UndersizeException {
        FetchURL myURL = new FetchURL(this.open_lib_ID);
        String description = myURL.getText();
        DictionaryCreator dc = new DictionaryCreator(description, this.dictionary_ID);
        dc.createDict();
    }
}
