package application.controller;

import application.model.BlokusColor;
import application.model.CellSprite;
import application.model.PieceSprite;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * A controller for each grids in the game (Board, Panel and ChosenPieceGrid)
 */
public class BlokusGridsController {

    private final int xsize;
    private final ArrayList<PieceSprite> pieceSprites = new ArrayList<>();
    private final GridPane gridpane;
    private final Object parent;

    /**
     * Creates a new GridPane to display cells
     *
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param xsize the dimension for displaying cells
     * @param color the default background color
     * @param parent the model linked to the controller
     */
    public BlokusGridsController(int col, int row, int xsize, BlokusColor color, Object parent) {
        this.parent = parent;
        this.xsize = xsize;
        this.gridpane = new GridPane();

        /* Puts a neutral cellSprite at every location */
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                CellSprite cell = new CellSprite(xsize, xsize, color, parent);
                this.gridpane.add(cell, i, j);
            }
        }
        pieceSprites.forEach((pieceSprite) -> {
            addToGridPane(pieceSprite);
        });
    }

    /**
     * @return the gridpane
     */
    public GridPane getGridpane() {
        return gridpane;
    }

    /**
     * Takes a PieceSprite and adds each CellSprite pane to the
     * BlokusGridsController
     *
     * @param pieceSprite the piece being added to the BlokusGridsController
     */
    public void addToGridPane(PieceSprite pieceSprite) {
        for (int i = 0; i < pieceSprite.CELL_COUNT; i++) {
            int x = pieceSprite.getCoordinates()[i].getX();
            int y = pieceSprite.getCoordinates()[i].getY();
            if (y >= 20 || y < 0 || x >= 20 || x < 0) {
                continue;
            }
            this.gridpane.add(pieceSprite.getCells()[i], pieceSprite.getCoordinates()[i].getX(), pieceSprite.getCoordinates()[i].getY());
        }
    }

    /**
     * Add a PieceSprite to the BlokusGridsController
     *
     * @param pieceSprite the piece to be added
     */
    public void addPieceSprite(PieceSprite pieceSprite) {
        pieceSprites.add(pieceSprite);
        addToGridPane(pieceSprite);
    }

    /**
     * Removes a given PieceSprite form the BlokusGridsController
     *
     * @param pieceSprite the piece to remove
     */
    public void removePieceSprite(PieceSprite pieceSprite) {
        pieceSprites.remove(pieceSprite);
        if (pieceSprite == null) {
            return;
        }
        for (int i = 0; i < pieceSprite.CELL_COUNT; i++) {
            this.gridpane.getChildren().remove(pieceSprite.getCells()[i]);
        }
    }

}
