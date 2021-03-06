package application;

import application.controller.BlokusController;
import java.io.IOException;

import application.controller.LibrairieController;
import application.controller.PuzzleController;
import application.controller.TetrisController;
import application.model.Tetris;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public Scene menu;
    

    @Override
    public void start(Stage primaryStage) {
        try {

            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Library");

            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            toLibrary();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toLibrary() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Librairie.fxml"));
            AnchorPane librairie = (AnchorPane) loader.load();
            menu = new Scene(librairie);
            primaryStage.setScene(menu);
            setTitle("Library: Choose your game!");

            LibrairieController controller = loader.getController();
            controller.setMain(this);

            rootLayout.setCenter(librairie);
        } catch (Exception e) {
            System.out.println("Failed to open Library : /n"+e);
        }
    }

    public void toTetris() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Tetris.fxml"));
            AnchorPane tetris = (AnchorPane) loader.load();
            this.primaryStage.setScene(new Scene(tetris));
            setTitle("Tetris: Play!");

            Tetris game = new Tetris();

            TetrisController controller = loader.getController();
            controller.setMain(this);
            controller.setGame(game);

            controller.init();
            primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                game.handleKeyPressed(event.getCode());
            });

            game.run();
        } catch (IOException e) {
            System.out.println("Failed to open Tetris : /n"+e);
        }
    }

    public void toBlokus() {
        BlokusController game = new BlokusController(new Group(), 840, 700, this);
        this.primaryStage.setScene(game);
        setTitle("Blokus: Play!");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> game.start()));
        timeline.play();
    }

    public void toPuzzle() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Puzzle.fxml"));
            AnchorPane puzzle = (AnchorPane) loader.load();
            this.primaryStage.setScene(new Scene(puzzle));
            
            
            PuzzleController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            System.out.println("Failed to open Puzzle : /n"+e);
        }
    }

    /**
     * Sets the window's title
     *
     * @param title : name of Title to display
     */
    public void setTitle(String title) {
        this.primaryStage.setTitle(title);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
