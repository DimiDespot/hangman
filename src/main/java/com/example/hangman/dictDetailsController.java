package com.example.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class dictDetailsController {
    private Stage dictStage;
    private HangmanController hc;

    public dictDetailsController(HangmanController hc) throws Exception {
        this.hc = hc;

        dictStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dictDetails.fxml"));
        loader.setController(this);
        dictStage.setScene(new Scene(loader.load()));
        dictStage.setTitle("Dictionary Details");
    }

    public void initialize() {
        stat1Label.setText(String.valueOf(this.hc.getG().r.getP1()) + "%");
        stat2Label.setText(String.valueOf(this.hc.getG().r.getP2()) + "%");
        stat3Label.setText(String.valueOf(this.hc.getG().r.getP3()) + "%");
    }

    public void showStage() {
        dictStage.show();
    }

    @FXML
    private Button OK_btn;

    @FXML
    private Label stat1Label;

    @FXML
    private Label stat2Label;

    @FXML
    private Label stat3Label;

    @FXML
    void OKClicked(ActionEvent event) {
        dictStage.close();
    }

}
