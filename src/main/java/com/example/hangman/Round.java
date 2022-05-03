package com.example.hangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;

public class Round {
    private final String word;
    ObservableList<LetterPair> word_progress;
    private int attempts;
    private int fails;
    private final int dict_count;
    private int points;
    private final float p1;
    private final float p2;
    private final float p3;
    private Set<String> possible_words;
    private int[][] letter_points;
    private logData ld;
    private int result = 0;
    private String imageURL;

    public Round(String w, Set<String> pw, int c, int c1, int c2, int c3) {
        this.word = w;
        this.possible_words = new HashSet<>();
        for (String s : pw) {
            if (s.length() == w.length()) this.possible_words.add(s);
        }
        this.attempts = 0;
        this.fails = 0;
        this.points = 0;
        this.dict_count = c;
        this.p1 = (float) (Math.round(1000 * c1 / (float) c) / 10.0);
        this.p2 = (float) (Math.round(1000 * c2 / (float) c) / 10.0);
        this.p3 = (float) (Math.round(1000 * c3 / (float) c) / 10.0);
        this.imageURL = "hangman_pics/0.png";
        this.ld = new logData("", 0, ' ');
        this.word_progress = FXCollections.observableArrayList();
        this.letter_points = new int[w.length()][26];
        for (int i = 0; i < w.length(); i++) {
           this.word_progress.add(new LetterPair('_', new ArrayList<>()));
           for (int j = 0; j < 26; j++) this.letter_points[i][j] = 0;
        }
        this.fix_lists_points();
    }

    public int getAttempts() {
        return attempts;
    }

    public String getWord() {
        return word;
    }

    public int getFails() {
        return fails;
    }

    public int getDict_count() {
        return dict_count;
    }

    public int getPoints() {
        return points;
    }

    public float getP1() {
        return p1;
    }

    public float getP2() {
        return p2;
    }

    public float getP3() {
        return p3;
    }

    public logData getLd() {
        return ld;
    }

    public int getResult() {
        return result;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String foundWord() {
        StringBuilder ans = new StringBuilder();
        for (LetterPair lp : this.word_progress) {
            ans.append(lp.getLetter()).append(" ");
        }
        return ans.toString();
    }

    public ObservableList<LetterPair> getWord_progress() {
        return word_progress;
    }

    public void OnSubmit(int pos, char letter) {
        boolean wrong_ans = false;
        if (!this.word_progress.get(pos-1).getPos_list().contains(letter)) {
            return;
        }
        this.attempts ++;
        if (this.word.charAt(pos-1) == letter) {    //right guess
            this.word_progress.get(pos-1).letter = letter;
            this.points += this.letter_points[pos-1][(int) letter - 65];
            if (this.attempts - this.fails == this.word.length()) {
                ld.setAttempts(this.attempts);
                ld.setWinner('U');
                ld.setWord(this.word);
                this.result = 1;
                this.imageURL = "hangman_pics/7.png";
            }
        }
        else {      //wrong guess
            wrong_ans = true;
            this.fails ++;
            this.points = (this.points >= 15) ? this.points - 15 : 0;
            if (this.fails == 6) {
                ld.setAttempts(this.attempts);
                ld.setWinner('C');
                ld.setWord(this.word);
                this.result = -1;
            }
            this.imageURL = "hangman_pics/" + this.fails + ".png";
        }
        this.correctList(pos, letter, wrong_ans);
        this.fix_lists_points();
    }

    public void correctList(int pos, char letter, boolean wrong_ans) {
        Set<String> rejected_words = new HashSet<>();
        if (wrong_ans) {
            for (String w : this.possible_words) {
                if (w.charAt(pos - 1) == letter) {
                    rejected_words.add(w);
                }
            }
        } else {
            for (String w : this.possible_words) {
                if (w.charAt(pos - 1) != letter) {
                    rejected_words.add(w);
                }
            }
        }
        this.possible_words.removeAll(rejected_words);
    }

    public void fix_lists_points() {
        MyPair[] letter_freq = new MyPair[26];
        for (int i = 0; i < this.word.length(); i++) {
            LetterPair lp = this.word_progress.get(i);
            lp.pos_list = new ArrayList<>();
            if (lp.letter != '_') {
                continue;
            }

            for (int j = 0; j < 26; j++) {
                letter_freq[j] = new MyPair(0, (char) (j + 65));
            }
            for (String w: this.possible_words) {
                if (w.length() != this.word.length()) continue;
                letter_freq[(int) w.charAt(i) - 65].count ++;
            }
            Arrays.sort(letter_freq, (p1, p2) -> p2.count - p1.count);

            int freq_sum = 0;
            for (int k = 0; k < 26; k++) {
                if (letter_freq[k].count == 0) break;
                lp.pos_list.add(letter_freq[k].letter);
                freq_sum += letter_freq[k].count;
            }
            for (int k = 0; k < 26; k++) {
                if (letter_freq[k].count == 0) break;
                float possibility = letter_freq[k].count / (float) freq_sum;
                if (possibility >= 0.6) this.letter_points[i][(int) letter_freq[k].letter - 65] = 5;
                else if (possibility >= 0.4) this.letter_points[i][(int) letter_freq[k].letter - 65] = 10;
                else if (possibility >= 0.25) this.letter_points[i][(int) letter_freq[k].letter - 65] = 15;
                else this.letter_points[i][(int) letter_freq[k].letter - 65] = 30;
            }
        }
    }

    boolean isPos(String i) {
        try
        {
            int a = Integer.parseInt(i);
            if (a < 0 || a > word.length()) return false;
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }

    boolean isLet(String s) {
        if (s.length() != 1) return false;
        int a = s.charAt(0);
        return a >= (int) 'A' && a <= (int) 'Z';
    }
}
