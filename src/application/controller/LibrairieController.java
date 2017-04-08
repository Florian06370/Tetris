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
		main.toTetris();
	}
	
	@FXML
	public void handleLaunchBlokus(){
		main.toBlokus();
	}
	
	@FXML
	public void handleLaunchPuzzle(){
		main.toPuzzle();
	}
        
        
}
