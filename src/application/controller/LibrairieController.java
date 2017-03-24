package application.controller;

import application.Main;
import javafx.fxml.FXML;

public class LibrairieController {
	
	private Main main;
	
	public void setMain(Main main){
		this.main = main;
	}
	
	@FXML
	public void handleLaunchTetris(){
		main.showTetris();
	}
	
	@FXML
	public void handleLaunchBlokus(){
		main.showBlokus();
	}
	
	@FXML
	public void handleLaunchPuzzle(){
		main.showPuzzle();
	}
        
        
}
