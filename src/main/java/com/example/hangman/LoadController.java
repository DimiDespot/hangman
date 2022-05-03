package com.example.hangman;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoadController {
    private Stage loadStage;
    private HangmanController hc;

    public LoadController(HangmanController hc) throws Exception {
        this.hc = hc;

        loadStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadPopup.fxml"));
        loader.setController(this);
        loadStage.setScene(new Scene(loader.load()));
        loadStage.setTitle("Load Dictionary");
    }

    public void showStage() {
        loadStage.show();
    }

    @FXML
    private Button cancelLoad_btn;

    @FXML
    private TextField dict_id_text;

    @FXML
    private Button load_btn;

    @FXML
    void cancelLoadClicked(ActionEvent event) {
        this.loadStage.close();
    }

    @FXML
    void popupLoad(ActionEvent event) throws Exception {
        String dictID = dict_id_text.getText();
        this.hc.getG().setSelectedDict(dictID);
        this.hc.menuStartClicked(new ActionEvent());
        this.loadStage.close();
    }

}
