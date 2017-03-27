/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import application.controller.PuzzleController;

/**
 *
 * @author natjo
 */
public class Puzzle {
    
    	private Piece[][] grid;     //grille du jeu
	private String[] pieces = { "PIv", "PIh", "Piv", "Pih"};    //Tableau de toutes les pieces disponibles
        private Piece piecev, pieceh, pieceJoueur;
        private int niveau;
        private PuzzleController observer;

        public Puzzle(){
            this.grid = new Piece[5][5];
            this.piecev = PieceFactory.getPiece("Piv");
            this.pieceh = PieceFactory.getPiece("Pih");
            this.pieceJoueur = PieceFactory.getPiece("Pih");
            pieceJoueur.setColor("red");
            
            for(int[] coord : piecev.getCoord()){
                grid[coord[0]+2][coord[1]+3] = piecev;
                grid[coord[0]+3][coord[1]+2] = piecev;
            }
            for(int[] coord : pieceh.getCoord()){
                grid[coord[0]+1][coord[1]+1] = pieceh;
                grid[coord[0]+1][coord[1]+3] = pieceh;
                grid[coord[0]+4][coord[1]+3] = pieceh;
                grid[coord[0]+2][coord[1]] = pieceJoueur;
            }
        }

}
