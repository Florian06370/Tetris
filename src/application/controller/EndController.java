package application.controller;

import application.Main;
import application.model.BlokusColor;
import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

/**
 * Controller for displaying the end of the game.
 */
public class EndController implements Initializable {

    @FXML
    private Text mainTitle;
    @FXML
    private TextFlow title;
    @FXML
    private Label blueScore;
    @FXML
    private Label yellowScore;
    @FXML
    private Label redScore;
    @FXML
    private Label greenScore;
    @FXML
    private Button backBtn;
    private Main parent;

    /**
     * Creates a new EndController scene, which displays the scores of each
     * player and the winner of the game.
     *
     * @param score list of integers representing the score of each player
     * @param playerColors list of Colors of players
     */
    public void endGame(int[] score, BlokusColor[] playerColors) {

        ArrayList<String> winners = new ArrayList<>();
        int maxScore = score[0];
        for (int i = 1; i < score.length; i++) {
            if (score[i] > maxScore) {
                maxScore = score[i];
            }
        }
        for (int i = 0; i < score.length; i++) {
            if (score[i] == maxScore) {
                winners.add(playerColors[i].name());
            }

        }
        for (int i = 0; i < winners.size(); i++) {
            if (winners.size() > 1) {
                mainTitle.setText("It's a tie between\n");
            } else {
                mainTitle.setText("The winner is\n");
            }
        }
        for (String winner : winners) {
            Text playerText = new Text(((winner + "\n")));
            playerText.setFont(Font.font("Consolas", FontWeight.BOLD, 80));

            if ("Blue".equals(winner)) {
                playerText.setFill(Color.valueOf("rgb(11, 66, 155)"));
            }
            if ("Yellow".equals(winner)) {
                playerText.setFill(Color.valueOf("rgb(237,157, 0)"));
            }
            if ("Red".equals(winner)) {
                playerText.setFill(Color.valueOf("rgb(155, 11, 66)"));
            }
            if ("Green".equals(winner)) {
                playerText.setFill(Color.valueOf("rgb(66,155, 11)"));
            }
            title.getChildren().add(playerText);
        }
        blueScore.setText(score[0] + "");
        yellowScore.setText(score[1] + "");
        redScore.setText(score[2] + "");
        greenScore.setText(score[3] + "");

        backBtn.setOnAction((ActionEvent e) -> {
            parent.toLibrary();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert mainTitle != null : "fx:id=\"mainTitle\" was not injected: check your FXML file 'End.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'End.fxml'.";
        assert blueScore != null : "fx:id=\"blueScore\" was not injected: check your FXML file 'End.fxml'.";
        assert yellowScore != null : "fx:id=\"yellowScore\" was not injected: check your FXML file 'End.fxml'.";
        assert redScore != null : "fx:id=\"redScore\" was not injected: check your FXML file 'End.fxml'.";
        assert greenScore != null : "fx:id=\"greenScore\" was not injected: check your FXML file 'End.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'End.fxml'.";

    }

    public void setMain(Main main) {
        this.parent = main;
    }

}
