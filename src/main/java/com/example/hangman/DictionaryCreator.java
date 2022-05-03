package com.example.hangman;

import java.util.*;
import java.io.*;

public class DictionaryCreator {
    private final String text, book_id;
    private int word_count;
    private int long_word_count;

    public DictionaryCreator(String s, String id) {
        text = s;
        book_id = id;
        word_count = 0;
        long_word_count = 0;
    }

    public void createDict() throws UndersizeException, UnbalancedException{
        try {
            int c1 = 0, c2 = 0, c3 = 0;
            Set<String> dict = new HashSet<>();
            String[] words = text.toUpperCase().split("[^A-Z]+");
            for (String a : words) {
                if (a.length() < 6 || dict.contains(a)) continue;
                if (a.length() >= 10) c3 ++;
                else if (a.length() >= 7) c2 ++;
                else c1 ++;
                dict.add(a);
                word_count++;
                if (a.length() >= 9) long_word_count++;
            }

            if (word_count < 20) {
                throw new UndersizeException("Too few valid words");
            }
            if (long_word_count / (double) word_count < 0.2) {
                throw new UnbalancedException("Too few big words");
            }
            FileWriter fw = new FileWriter("medialab/hangman_" + book_id + ".txt");
            fw.write(dict.size() + "\n" + c1 + " " + c2 + " " + c3 + "\n");
            for (String w : dict) {
                fw.write(w + "\n");
            }
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
