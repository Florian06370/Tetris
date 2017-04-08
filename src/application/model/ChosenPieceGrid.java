package application.model;

import application.controller.BlokusGridsController;
import application.controller.BlokusController;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 * Class that represents the little grid where the chosen piece by the player is
 * placed, and where we can rotate and flip the previewed piece.
 */
public class ChosenPieceGrid {

    private PieceSprite thePieceSprite;
    private boolean active;

    private BlokusGridsController gridPreview;
    private BlokusController parent;
    private int xsize;

    /**
     * Creates new ChosenPieceGrid
     *
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the dimension for displaying cells
     * @param color the default background color
     * @param parent the BlokusController class which instantiates this class
     */
    public ChosenPieceGrid(int col, int row, int size, BlokusColor color, BlokusController parent) {
        this.gridPreview = new BlokusGridsController(col, row, size, color, this);
        this.parent = parent;
        this.xsize = size;

        eventWatcher();
        active = false;

        Pane rotate = new CellSprite(size, size, "Rotate", this);
        this.gridPreview.getGridpane().add(rotate, 0, 0);

        Pane flip = new CellSprite(size, size, "Flip", this);
        this.gridPreview.getGridpane().add(flip, 4, 0);
        flip.setOnMouseClicked(event -> {
            if (!active) {
                return;
            }
            flipPiece();

            rotatePiece();
            rotatePiece();
            rotatePiece();

        });

    }
    
    
    /**
     * Used to pass along the current piece to the board
     *
     * @return the piece shown in the preparer
     */
    public BlokusPiece getPiece() {
        if (thePieceSprite == null) {
            return null;
        }
        return thePieceSprite.piece;
    }
    
    /**
     * @return the gridPreview
     */
    public BlokusGridsController getGridPreview() {
        return gridPreview;
    }

    
    /**
     * Add a shape to the viewer though addPiece
     *
     * @param shape the shape to show
     * @param c the color of the shape
     * @param orientation the orientation to initialize it to
     */
    public void addShape(Shape shape, BlokusColor c, char orientation) {
        removePiece();
        BlokusPiece piece = new BlokusPiece(shape, c);
        piece.initialisePiece(new Coordinate(0, 0), orientation);
        addPiece(piece);
    }

    /**
     * Adds a piece to the viewer
     *
     * @param piece the piece to add to the viewer
     */
    private void addPiece(BlokusPiece piece) {

        piece.setXY(new Coordinate(0, 0));
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        for (Coordinate coord : piece.getOccupiedCells()) {
            minX = Math.min(minX, coord.getX());
            minY = Math.min(minY, coord.getY());
            maxX = Math.max(maxX, coord.getX());
            maxY = Math.max(maxY, coord.getY());
        }
        /*sets to 0 and uses left over space*/
        int newX = -minX + (int) Math.floor((5 - (maxX - minX)) / 2); 
        int newY = -minY + (int) Math.floor((5 - (maxY - minY)) / 2);

        piece.setXY(new Coordinate(newX, newY));
        thePieceSprite = new PieceSprite(piece, xsize, this.getGridPreview(), this);
        this.getGridPreview().addPieceSprite(thePieceSprite);
    }

    /**
     * Flip the piece being shown
     */
    public void flipPiece() {
        BlokusPiece piece = thePieceSprite.piece;
        piece.movePiece(0, true);
        removePiece();
        addPiece(piece);
    }

    /**
     * Rotate the piece being shown
     */
    public void rotatePiece() {
        BlokusPiece piece = thePieceSprite.piece;
        piece.movePiece(1, false);
        removePiece();
        addPiece(piece);
    }

    /**
     * Watch for clicks on the entire grid for rotating and flipping the piece
     */
    private void eventWatcher() {
        this.getGridPreview().getGridpane().setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (!active) {
                    return;
                }
                flipPiece();
            } else {
                if (!active) {
                    return;
                }
                rotatePiece();
            }
        });
    }

    /**
     * Remove the piece from the grid
     */
    public void removePiece() {
        this.getGridPreview().removePieceSprite(thePieceSprite);
        thePieceSprite = null;
    }

    /**
     * Set active if the player is currently playing
     *
     * @param active whether or not to set the preparer to active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
