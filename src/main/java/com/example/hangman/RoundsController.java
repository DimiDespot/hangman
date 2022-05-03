package com.example.hangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;

public class RoundsController {
    private Stage roundsStage;
    private HangmanController hc;

    public RoundsController(HangmanController hc) throws Exception {
        this.hc = hc;

        Scene scene = new Scene(new Group());
        roundsStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("roundsDetails.fxml"));
        loader.setController(this);
        roundsStage.setScene(new Scene(loader.load()));
        roundsStage.setTitle("Rounds Details");

        final Label roundsLabel = new Label("Results of the last 5 games:");
        roundsLabel.setFont(new Font(20));

        roundsTable = new TableView<>();
        ObservableList<logData> data = FXCollections.observableArrayList();

        File file = new File("medialab/log.txt");
        ReversedLinesFileReader object = new ReversedLinesFileReader(file);
        for (int i = 0; i < 5; i++) {
            String[] line = object.readLine().split(" ");
            data.add(new logData(line[0], Integer.parseInt(line[1]), line[2].charAt(0)));
        }
        object.close();

        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

        wordCol = new TableColumn("Word");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        attemptsCol = new TableColumn("Attempts");
        attemptsCol.setCellValueFactory(new PropertyValueFactory("attempts"));
        winnerCol = new TableColumn("Winner");
        winnerCol.setCellValueFactory(new PropertyValueFactory("winner"));
        roundsTable.setItems(data);
        wordCol.setSortable(false);
        attemptsCol.setStyle("-fx-alignment: CENTER;");
        attemptsCol.setSortable(false);
        winnerCol.setStyle("-fx-alignment: CENTER;");
        winnerCol.setSortable(false);
        roundsTable.getColumns().addAll(wordCol, attemptsCol, winnerCol);
        roundsTable.setPrefSize(400, 180);
        roundsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setFillWidth(true);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(roundsLabel, roundsTable, OK_btn);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        roundsStage.setScene(scene);
    }

    public void showStage() {
        roundsStage.show();
    }

    @FXML
    private Button OK_btn;

    @FXML
    private TableColumn<logData, String> attemptsCol;

    @FXML
    private TableView<logData> roundsTable;

    @FXML
    private TableColumn<logData, String> winnerCol;

    @FXML
    private TableColumn<logData, String> wordCol;

    @FXML
    void OKClicked(ActionEvent event) {
        roundsStage.close();
    }


}
