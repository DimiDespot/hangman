package com.example.hangman;

import java.net.*;
import java.io.*;
import java.util.stream.Collectors;
import org.json.*;

public class FetchURL {
    private final String book_id;

    public FetchURL(String id) {
        book_id = id;
    }

    public String getText() throws IOException {
        URL book = new URL("https://openlibrary.org/works/" + book_id + ".json");
        BufferedReader in = new BufferedReader(new InputStreamReader(book.openStream()));
        String data = in.lines().collect(Collectors.joining());
        JSONObject obj = new JSONObject(data);
        String text = obj.getJSONObject("description").getString("value");
        in.close();
        return text;
    }
}
