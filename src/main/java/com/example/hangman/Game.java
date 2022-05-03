package com.example.hangman;

import java.util.*;
import java.io.*;

public class Game {
    public Round r;
    private String selectedDict;

    /**
     * The constructor of the Game class.
     * By default, it sets the selected dictionary ID to "WEAPONS".
     */
    public Game() {
        this.selectedDict = "WEAPONS";
    }

    /**
     * Setter for selectedDict field
     * @param selectedDict The dictionary ID to be set as active
     */
    public void setSelectedDict(String selectedDict) {
        this.selectedDict = selectedDict;

    }

    /**
     * Getter for r field
     * @return Current round r
     */
    public Round getR() {
        return r;
    }

    /**
     * The function that initializes a new round with a word from the selected dictionary
     */
    public void start() {
        try {
            if (this.selectedDict.equals("")) {
                throw new UndefinedDictionaryException("Please Load an existing dictionary, or Create a new one");
            }
            String dict_name = "medialab/hangman_" + selectedDict + ".txt";
            String data, selectedWord = "";
            Set<String> words = new HashSet<>();
            File myObj = new File(dict_name);
            Scanner myReader = new Scanner(myObj);
            int size = myReader.nextInt();
            int c1 = myReader.nextInt();
            int c2 = myReader.nextInt();
            int c3 = myReader.nextInt();
            Random ran = new Random();
            int selection = ran.nextInt(size) + 1;
            for (int i = 0; i < size; i++) {
                data = myReader.nextLine();
                if (data.length() > 5) words.add(data);
                if (i == selection) selectedWord = data;
            }
            myReader.close();
            this.r = new Round(selectedWord, words, size, c1, c2, c3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A function to create a new dictionary.
     * The dictionary is stored in directory "medialab/"
     * @param dID Dictionary ID to be used in file name
     * @param olID Open Library ID to be used to find a specific book
     * @throws IOException Exception due to malformed URL
     * @throws UndersizeException The dictionary does not contain enough words
     * @throws UnbalancedException The dictionary does not contain enough words with length longer than 9
     */
    public void createDict(String dID, String olID) throws IOException, UndersizeException, UnbalancedException {
        IDtoDictionary dictCreator = new IDtoDictionary(dID, olID);
        dictCreator.create();
    }

    /**
     * A function that updates the log file with a new entry: the result of the last game.
     * The log file is located at <a href="{@docRoot}/medialab/log.txt">log.txt</a>
     * @param d A data structure that contains the result of the last game
     * @throws IOException Directory not found
     */
    public void updateLog(logData d) throws IOException {
        FileWriter fw = new FileWriter("medialab/log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(d.getWord() + " " + d.getAttempts() + " " + d.getWinner() + "\n");
        bw.close();
    }
}
