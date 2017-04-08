/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.Main;
import application.model.Puzzle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author natjo
 */
public class PuzzleController implements Initializable{
        @FXML
	private GridPane gridView;

	@FXML
	private Label lbScore;

	@FXML
	private Label lbNiveau;

	private Label[][] labels;


	private Puzzle game;

	private Main main;
        
        @Override
	public void initialize(URL location, ResourceBundle resources) {
		assert gridView != null : "fx:id=\"gridView\" was not injected: check your FXML file 'Puzzle.fxml'.";
		assert lbNiveau != null : "fx:id=\"lbNiveau\" was not injected: check your FXML file 'Puzzle.fxml'.";
	}
        
        @FXML
        public void handleLaunchLibrairie(){
                main.toLibrary();
        }
        
        public void setMain(Main main) {
		this.main = main;
	}
        
        
        
}

