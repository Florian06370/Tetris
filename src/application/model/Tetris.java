package application.model;

import java.util.ArrayList;
import java.util.Random;

import application.controller.TetrisController;

public class Tetris {
	/**
	 * Matrice de la grille
	 */
	private Piece[][] grid;

	private Piece nextPiece;

	private Piece moveablePiece;

	private ArrayList<Piece> unmoveablePiece;

	private String keyPressed;

	private String[] pieces = { "S", "Z", "L", "J", "T", "O", "I" };

	private TetrisController observer;

	public Tetris() {
		this.grid = new Piece[20][10];		

		int rnd = new Random().nextInt(pieces.length);
		this.nextPiece = PieceFactory.getPiece(pieces[rnd]); // Selection aleatoire de la piece
		
		rnd = new Random().nextInt(pieces.length);
		this.moveablePiece = PieceFactory.getPiece(pieces[rnd]);

		this.unmoveablePiece = new ArrayList<Piece>();
		
		this.setPosition(moveablePiece);
	}

	public void run() {

		while (true) {
			/*ArrayList<int[]> newCoord = moveablePiece.getCoord();

			switch (keyPressed) {
			case "UP":
				newCoord = moveablePiece.rotate();
				break;
			case "LEFT":
				newCoord = moveablePiece.toLeft();
				break;
			case "RIGHT":
				newCoord = moveablePiece.toRight();
				break;
			}

			if (!keyPressed.equals("") && checkPosition(newCoord)) {
				moveablePiece.setCoord(newCoord);
			}

			keyPressed = "";

			newCoord = moveablePiece.toDown();

			if (checkPosition(newCoord)) {
				moveablePiece.setCoord(newCoord);
			} else {
				unmoveablePiece.add(moveablePiece);
				moveablePiece = nextPiece;

				int rnd = new Random().nextInt(pieces.length);
				nextPiece = PieceFactory.getPiece(pieces[rnd]);
			}*/
			
			moveablePiece.setCoord(moveablePiece.toDown());
			
			this.setPosition(moveablePiece);
			
			this.notifyObserver();
		}
	}

	private boolean checkPosition(ArrayList<int[]> coord) {
		return true;
	}

	public void setKeyPressed(String keyPressed) {
		this.keyPressed = keyPressed;
	}

	public void setObserver(TetrisController tetrisController) {
		this.observer = tetrisController;
	}

	public void notifyObserver() {
		observer.update();
	}
	
	public Piece[][] getGrid(){
		return grid;
	}
	
	public void setPosition(Piece piece){
		for(int[] coord : piece.getCoord()){
			this.grid[coord[0]][coord[1]] = piece;
		}
	}

}
