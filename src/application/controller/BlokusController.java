package application.controller;

import application.Main;
import application.model.Blokus;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nejko
 */
public class BlokusController {

    @FXML private Button passBtn;
    @FXML private Button ruleBtn;
    @FXML private Button quitBtn;
    private Blokus game;
    private Main main;

    
    public void setMain(Main main){ this.main = main; }
    public void setGame(Blokus game){ this.game = game;}
    
    @FXML
    private void passBtnAction(){
        
    }
    
    @FXML
    private void ruleBtnAction(ActionEvent event) throws IOException{
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/view/BlokusRules.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(Exception e) {
           System.out.println("Can't load new window");
          }
    }
    
    @FXML
    private void quitBtnAction(){
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }
    
    
    
    
    
    
    
    
}
