package com.example.hangman;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class HangmanController {
    private final Game G;
    private final Stage mainStage;
    private ObservableList<LetterCandidates> data;

    public HangmanController() throws Exception {
        G = new Game();

        mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Hangman.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        mainStage.setScene(scene);
        mainStage.setTitle("MediaLab Hangman");

        hangmanImage.setImage(new Image(new FileInputStream("hangman_pics/0.png")));

        data = FXCollections.observableArrayList();
        table = new TableView<>();
        positionTableColumn = new TableColumn("Position");
        possibilitiesTableColumn = new TableColumn("Possible letters");
        positionTableColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        possibilitiesTableColumn.setCellValueFactory(new PropertyValueFactory<>("pos_list"));
        possibilitiesTableColumn.setPrefWidth(400);
        possibilitiesTableColumn.setSortable(false);
        positionTableColumn.setSortable(false);
        positionTableColumn.setStyle("-fx-alignment: CENTER;");
        table.getColumns().addAll(positionTableColumn, possibilitiesTableColumn);
        table.setItems(data);
        table.setVisible(false);
        table.setMaxSize(800, 200);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableVbox = new VBox();
        tableVbox.setFillWidth(true);
        tableVbox.getChildren().add(table);
        centerHbox.getChildren().add(tableVbox);
    }

    @FXML
    private HBox centerHbox;

    @FXML
    private VBox tableVbox;

    @FXML
    void initialize() {
        submitButton.setDisable(true);
        positionIn.setDisable(true);
        letterIn.setDisable(true);
    }

    public void showStage() {
        mainStage.show();
    }

    public Game getG() {
        return G;
    }

    @FXML
    private Label resultLabel;

    @FXML
    private Label AvailWordsLabel;

    @FXML
    private Label PointsLabel;

    @FXML
    private Label SuccessRateLabel;

    @FXML
    private ImageView hangmanImage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField letterIn;

    @FXML
    private TableView<LetterCandidates> table;

    @FXML
    private MenuItem menuCreate;

    @FXML
    private MenuItem menuDictionary;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuLoad;

    @FXML
    private MenuItem menuRounds;

    @FXML
    private MenuItem menuSolution;

    @FXML
    private MenuItem menuStart;

    @FXML
    private TableColumn positionTableColumn;

    @FXML
    private TextField positionIn;

    @FXML
    private TableColumn possibilitiesTableColumn;

    @FXML
    private Button submitButton;

    @FXML
    private Label wordProgressLabel;

    @FXML
    void SubmitClicked(ActionEvent event) {
        try {
            if (!G.r.isPos(positionIn.getText()) || !G.r.isLet(letterIn.getText())) {
                throw new InvalidInputException("Invalid Input");
            }
            G.r.OnSubmit(Integer.parseInt(positionIn.getText()), letterIn.getText().charAt(0));

            //Updating labels
            PointsLabel.setText(String.valueOf(this.G.r.getPoints()));
            float rate = (G.r.getAttempts() != 0) ? ((G.r.getAttempts() - G.r.getFails()) / (float) G.r.getAttempts()) : 0;
            SuccessRateLabel.setText(String.valueOf(Math.round(rate * 1000) / 10) + "%");
            wordProgressLabel.setText(this.G.r.foundWord());
            hangmanImage.setImage(new Image(new FileInputStream(this.G.getR().getImageURL())));

            //Adding data to Letter Table
            data.clear();
            for (int i = 0; i < this.G.getR().getWord().length(); i++) {
                data.add(new LetterCandidates(i + 1, this.G.getR().getWord_progress().get(i).getPos_list().toString().substring(1, this.G.getR().getWord_progress().get(i).getPos_list().toString().indexOf("]"))));
            }

            //Checking if game is over
            if (this.getG().getR().getResult() == 1) {
                menuSolution.setDisable(true);
                submitButton.setDisable(true);
                resultLabel.setText("WE HAVE A WINNER!");
                resultLabel.setVisible(true);
                resultLabel.setTextFill(Color.web("#05c327"));
                this.getG().updateLog(this.getG().getR().getLd());
                submitButton.setDisable(true);
            } else if (this.getG().getR().getResult() == -1) {
                StringBuilder w = new StringBuilder();
                String sol = this.G.getR().getWord();
                for (int i = 0; i < sol.length(); i++) {
                    w.append(sol.charAt(i)).append(" ");
                }
                wordProgressLabel.setText(w.toString());
                menuSolution.setDisable(true);
                submitButton.setDisable(true);
                resultLabel.setText("OOPS... YOU LOST!");
                resultLabel.setVisible(true);
                resultLabel.setTextFill(Color.web("#fa3f07"));
                this.getG().updateLog(this.getG().getR().getLd());
                submitButton.setDisable(true);
            }
        }
        catch (InvalidInputException e) {
            Text message = new Text(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setContent(message);
            alert.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menuCreateClicked(ActionEvent event) throws Exception {
        CreateController cc = new CreateController(this);
        cc.showStage();
    }

    @FXML
    void menuDictionaryClicked(ActionEvent event) throws Exception {
        dictDetailsController dc = new dictDetailsController(this);
        dc.showStage();
    }

    @FXML
    void menuExitClicked(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void menuLoadClicked(ActionEvent event) throws Exception {
        LoadController lc = new LoadController(this);
        lc.showStage();
    }

    @FXML
    void menuRoundsClicked(ActionEvent event) throws Exception {
        RoundsController rc = new RoundsController(this);
        rc.showStage();
    }

    @FXML
    void menuSolutionClicked(ActionEvent event) throws Exception {
        menuSolution.setDisable(true);
        submitButton.setDisable(true);
        this.G.updateLog(new logData(this.G.getR().getWord(), this.G.getR().getAttempts(), 'C'));
        StringBuilder w = new StringBuilder();
        String sol = this.G.getR().getWord();
        for (int i = 0; i < sol.length(); i++) {
            w.append(sol.charAt(i)).append(" ");
        }
        wordProgressLabel.setText(w.toString());
    }

    @FXML
    void menuStartClicked(ActionEvent event) throws Exception {
        G.start();
        submitButton.setDisable(false);
        hangmanImage.setImage(new Image(new FileInputStream("hangman_pics/0.png")));
        data.clear();
        for (int i = 0; i < this.G.getR().getWord().length(); i++) {
            data.add(new LetterCandidates(i + 1, this.G.getR().getWord_progress().get(i).getPos_list().toString().substring(1, this.G.getR().getWord_progress().get(i).getPos_list().toString().indexOf("]"))));
        }
        table.setVisible(true);
        menuSolution.setDisable(false);
        wordProgressLabel.setText(this.G.r.foundWord());
        resultLabel.setVisible(false);
        submitButton.setDisable(false);
        AvailWordsLabel.setText(String.valueOf(this.G.getR().getDict_count()));
        PointsLabel.setText("0");
        SuccessRateLabel.setText("0");
        positionIn.setText("");
        letterIn.setText("");
        positionIn.setDisable(false);
        letterIn.setDisable(false);
    }

}
