package application.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import application.controller.TetrisController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Tetris {

	/**
	 * Matrice de la grille
	 */
	private Piece[][] grid;

	private Piece[][] gridProchain;

	private Piece nextPiece;

	private Piece moveablePiece;

	private ArrayList<Piece> unmoveablePiece;

	private String[] pieces = { "S", "Z", "L", "J", "T", "O", "I" };

	private int score;

	private int niveau;

	private boolean gameOver;

	private TetrisController observer;

	private Timeline timeline;

	public Tetris() {
		this.grid = new Piece[20][10];

		this.gridProchain = new Piece[4][4];

		int rnd = new Random().nextInt(pieces.length);
		this.nextPiece = PieceFactory.getPiece(pieces[rnd]); // Selection
																// aleatoire de
																// la piece

		for (int[] coord : nextPiece.getCoord()) {
			gridProchain[coord[0]][coord[1]] = nextPiece;
		}

		rnd = new Random().nextInt(pieces.length);
		this.moveablePiece = PieceFactory.getPiece(pieces[rnd]);

		for (int[] coord : moveablePiece.getCoord()) {
			grid[coord[0]][coord[1]] = moveablePiece;
		}

		this.unmoveablePiece = new ArrayList<Piece>();

		this.score = 0;

		this.niveau = 1;

		this.gameOver = false;
	}

	/**
	 * Timer périodique du jeu
	 */
	public void run() {

		timeline = new Timeline(new KeyFrame(Duration.millis(1000 - 100 * niveau), ae -> logic()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Logique du jeu
	 */
	public void logic() {
		if (checkGameOver()) {
			gameOver = true;
			timeline.stop();
		} else {

			ArrayList<int[]> newCoord = moveablePiece.toDown();

			if (checkPosition(newCoord)) {
				changeCoord(newCoord);
			} else {
				unmoveablePiece.add(moveablePiece);
				moveablePiece = nextPiece;

				checkRow();

				boolean stop = false;

				for (int[] coord : moveablePiece.getCoord()) {
					if (grid[coord[0]][coord[1]] != null) {
						stop = true;
					}

					if (grid[coord[0]][coord[1]] == null && !stop) {
						grid[coord[0]][coord[1]] = moveablePiece;
					}
				}

				int rnd = new Random().nextInt(pieces.length);
				nextPiece = PieceFactory.getPiece(pieces[rnd]);

				gridProchain = new Piece[4][4];

				for (int[] coord : nextPiece.getCoord()) {
					gridProchain[coord[0]][coord[1]] = nextPiece;
				}
			}
		}

		notifyObserver();
	}

	public void handleKeyPressed(KeyCode keyCode) {
		if (!isGameOver()) {
			ArrayList<int[]> newCoord = moveablePiece.getCoord();

			switch (keyCode) {
			case UP:
				newCoord = moveablePiece.rotate();
				break;
			case LEFT:
				newCoord = moveablePiece.toLeft();
				break;
			case RIGHT:
				newCoord = moveablePiece.toRight();
				break;
			case DOWN:
				newCoord = moveablePiece.toDown();
				break;
			default:
				return;
			}

			if (checkPosition(newCoord)) {
				changeCoord(newCoord);
			}

			this.notifyObserver();
		}
	}

	// Méthodes privées nécessaire à la logique du jeu

	private void checkRow() {
		boolean lignePleine = false;
		for (int i = grid.length - 1; i >= 0; i--) {
			lignePleine = true;
			for (int j = 0; j < grid[0].length && lignePleine; j++) {
				// verif si piece unmoveable
				if (grid[i][j] == null || grid[i][j] == moveablePiece) {
					lignePleine = false;
				}
			}
			if (lignePleine) {
				supprimerLigne(i);
				i++;
			}
		}
	}

	private void supprimerLigne(int indice) {
		for (int i = indice; i >= 0; i--) {
			for (int j = 0; j < grid[0].length && i > 0; j++) {
				grid[i][j] = grid[i - 1][j];
			}
		}

		score += 100;
		this.notifyObserver();

		if (score >= niveau * 1000 && niveau < 9) {
			niveau++;
			this.notifyObserver();
			timeline.stop();
			run();
		}
	}

	private boolean checkGameOver() {
		for (int j = 0; j < grid[0].length; j++) {
			if (grid[0][j] instanceof Piece && grid[0][j] != moveablePiece) {
				return true;
			}
		}
		return false;
	}

	private void changeCoord(ArrayList<int[]> coords) {
		for (int[] coord : moveablePiece.getCoord()) {
			grid[coord[0]][coord[1]] = null;
		}

		for (int[] coord : coords) {
			grid[coord[0]][coord[1]] = moveablePiece;
		}

		moveablePiece.setCoord(coords);
	}

	private boolean checkPosition(ArrayList<int[]> newCoord) {
		for (int[] coord : newCoord) {
			if (coord[0] < 0 || coord[0] >= grid.length) {
				return false;
			}
			if (coord[1] < 0 || coord[1] >= grid[0].length) {
				return false;
			}
			if (grid[coord[0]][coord[1]] instanceof Piece && grid[coord[0]][coord[1]] != moveablePiece) {
				return false;
			}
		}
		return true;
	}

	public void setObserver(TetrisController tetrisController) {
		this.observer = tetrisController;
	}

	private void notifyObserver() {
		observer.update();
	}

	// GETTER

	public Piece[][] getGrid() {
		return grid;
	}

	public Piece[][] getGridProchain() {
		return gridProchain;
	}

	public String getScore() {
		return String.valueOf(score);
	}

	public String getNiveau() {
		return String.valueOf(niveau);
	}

	public boolean isGameOver() {
		return gameOver;
	}
}
