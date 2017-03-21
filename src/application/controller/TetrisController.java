package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Tetris;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TetrisController implements Initializable {

	@FXML
	private GridPane gridView;

	private Label[][] labels;

	private Tetris game;
	
	private Main main;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert gridView != null : "fx:id=\"gridView\" was not injected: check your FXML file 'Tetris.fxml'.";

	}

	public void init(){
		game.setObserver(this);
		
		gridView.setStyle("-fx-border-color:grey");
		
	    double height = gridView.getPrefHeight()/game.getGrid().length;
	    double width = gridView.getPrefWidth()/game.getGrid()[0].length;
	    
		labels = new Label[game.getGrid().length][game.getGrid()[0].length];

		for (int i = 0; i < game.getGrid().length; i++) {
			for (int j = 0; j < game.getGrid()[0].length; j++) {
				Label label = new Label();
				label.setPrefHeight(height);
				label.setPrefWidth(width);
				label.setAlignment(Pos.CENTER);
				
				gridView.setRowIndex(label,i);
				gridView.setColumnIndex(label,j);
				gridView.getChildren().add(label);
				
				labels[i][j] = label;
			}
		}

		this.update();
	}
	
	public void update() {
		for (int i = 0; i < game.getGrid().length; i++) {
			for (int j = 0; j < game.getGrid()[0].length; j++) {
				if (game.getGrid()[i][j] != null)
					labels[i][j].setStyle("-fx-border-color:grey;-fx-background-color:" + game.getGrid()[i][j].getColor()+";");
			}
		}
	}	
	
	
	public void setMain(Main main){
		this.main = main;
	}
	
	public void setGame(Tetris game){
		this.game = game;
	}
}
