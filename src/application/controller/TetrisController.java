package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Tetris;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TetrisController implements Initializable {

	@FXML
	private GridPane gridView;

	@FXML
	private GridPane gridProchain;

	@FXML
	private Label lbScore;

	@FXML
	private Label lbNiveau;

	private Label[][] labels;

	private Label[][] labelsProchain;

	private Tetris game;

	private Main main;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert gridView != null : "fx:id=\"gridView\" was not injected: check your FXML file 'Tetris.fxml'.";
		assert gridProchain != null : "fx:id=\"gridProchain\" was not injected: check your FXML file 'Tetris.fxml'.";
		assert lbScore != null : "fx:id=\"lbScore\" was not injected: check your FXML file 'Tetris.fxml'.";
		assert lbNiveau != null : "fx:id=\"lbNiveau\" was not injected: check your FXML file 'Tetris.fxml'.";
	}

	public void init() {
		game.setObserver(this);

		// Configuration de la grille de jeu

		gridView.setStyle("-fx-border-color:grey");

		double height = gridView.getPrefHeight() / game.getGrid().length;
		double width = gridView.getPrefWidth() / game.getGrid()[0].length;

		labels = new Label[game.getGrid().length][game.getGrid()[0].length];

		for (int i = 0; i < game.getGrid().length; i++) {               //affiche les textes (labels)
			for (int j = 0; j < game.getGrid()[0].length; j++) {
				Label label = new Label();
				label.setPrefHeight(height);
				label.setPrefWidth(width);
				label.setAlignment(Pos.CENTER);

				gridView.setRowIndex(label, i);
				gridView.setColumnIndex(label, j);
				gridView.getChildren().add(label);

				labels[i][j] = label;
			}
		}

		// Configuration de l'affichage de la prochaine pièce

		gridProchain.setStyle("-fx-border-color:grey");

		height = gridProchain.getPrefHeight() / game.getGridProchain().length;
		width = gridProchain.getPrefWidth() / game.getGridProchain()[0].length;

		labelsProchain = new Label[game.getGridProchain().length][game.getGridProchain()[0].length];

		for (int i = 0; i < game.getGridProchain().length; i++) {
			for (int j = 0; j < game.getGridProchain()[0].length; j++) {
				Label label = new Label();
				label.setPrefHeight(height);
				label.setPrefWidth(width);
				label.setAlignment(Pos.CENTER);

				gridProchain.setRowIndex(label, i);
				gridProchain.setColumnIndex(label, j);
				gridProchain.getChildren().add(label);

				labelsProchain[i][j] = label;
			}
		}

		this.update();
	}

	public void update() {
                
                //Si la partie est finie, affiche une page game over
		if (game.isGameOver()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tetris");
			alert.setHeaderText("GAME OVER");
			alert.show();
		} 
                
                
                else {     
                    
                        //actualise la grille de jeu
			for (int i = 0; i < game.getGrid().length; i++) {
				for (int j = 0; j < game.getGrid()[0].length; j++) {
					if (game.getGrid()[i][j] != null) {
						labels[i][j].setStyle(
								"-fx-border-color:grey;-fx-background-color:" + game.getGrid()[i][j].getColor() + ";");
					} else {
						labels[i][j].setStyle("");
					}
				}
			}
                        
                        
                        //actualise la grille de la prochaine piece
			for (int i = 0; i < game.getGridProchain().length; i++) {               
				for (int j = 0; j < game.getGridProchain()[0].length; j++) {
					if (game.getGridProchain()[i][j] != null) {
						labelsProchain[i][j].setStyle("-fx-border-color:grey;-fx-background-color:"
								+ game.getGridProchain()[i][j].getColor() + ";");
					} else {
						labelsProchain[i][j].setStyle("");
					}
				}
			}

			lbScore.setText(game.getScore());

			lbNiveau.setText(game.getNiveau());
		}
	}
        
        
        @FXML
        public void handleLaunchLibrairie(){
                game.getTimeline().stop();
                main.showLirairie();
        }

        @FXML
	public void handleLaunchTetris(){
                game.getTimeline().stop();
		main.showTetris();
	}
        
        @FXML
        public void pause(){
                game.getTimeline().stop();                
        }
        
        @FXML
        public void start(){
            
                game.getTimeline().play();
        }
        
        
	public void setMain(Main main) {
		this.main = main;
	}

	public void setGame(Tetris game) {
		this.game = game;
	}
}
