package com.example.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateController {
    private final Stage createStage;
    private final HangmanController hc;

    public CreateController(HangmanController hc) throws Exception {
        this.hc = hc;

        createStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createPopup.fxml"));
        loader.setController(this);
        createStage.setScene(new Scene(loader.load()));
        createStage.setTitle("Create Dictionary");
    }

    public void showStage() {
        createStage.show();
    }


    @FXML
    private Button cancelCreate_btn;

    @FXML
    private Button create_btn;

    @FXML
    private TextField dict_id_create_text;

    @FXML
    private TextField ol_id_text;

    @FXML
    void cancelCreateClicked(ActionEvent event) {
        createStage.close();
    }

    @FXML
    void createClicked(ActionEvent event) {
        try {
            this.hc.getG().createDict(dict_id_create_text.getText(), ol_id_text.getText());
            createStage.close();
        }
        catch (UndersizeException | UnbalancedException e) {
            Text message = new Text(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setContent(message);
            alert.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
