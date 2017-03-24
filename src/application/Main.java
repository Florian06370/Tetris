package application;

import java.io.IOException;

import application.controller.LibrairieController;
import application.controller.TetrisController;
import application.model.Tetris;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		try {

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Librairie");

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

			showLirairie();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showLirairie() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Librairie.fxml"));
			AnchorPane librairie = (AnchorPane) loader.load();

			LibrairieController controller = loader.getController();
			controller.setMain(this);
                        
                        this.setSize(librairie);
                        
			rootLayout.setCenter(librairie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTetris() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Tetris.fxml"));
			AnchorPane tetris = (AnchorPane) loader.load();

			Tetris game = new Tetris();

			TetrisController controller = loader.getController();
			controller.setMain(this);
			controller.setGame(game);

			this.setSize(tetris);

			rootLayout.setCenter(tetris);

			controller.init();

			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					game.handleKeyPressed(event.getCode());
				}

			});

			game.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showBlokus() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Blokus.fxml"));
			AnchorPane blokus = (AnchorPane) loader.load();

			this.setSize(blokus);

			rootLayout.setCenter(blokus);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPuzzle() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Puzzle.fxml"));
			AnchorPane puzzle = (AnchorPane) loader.load();

			this.setSize(puzzle);

			rootLayout.setCenter(puzzle);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSize(AnchorPane pane) {
		primaryStage.setHeight(pane.getPrefHeight() + 50);
		primaryStage.setWidth(pane.getPrefWidth());
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
