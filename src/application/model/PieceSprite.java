package application.model;

import application.controller.BlokusGridsController;

/**
 * PieceSprite handles click events through their cells being clicked on
 */
public class PieceSprite {

    public final int CELL_COUNT;
    final BlokusPiece piece;

    private final BlokusGridsController gridSprite;
    private final CellSprite[] cells;
    private final Coordinate[] coordinates;
    private Object parent = null;

    /**
     * Creates an array of CellSprites to render onto a BlokusGridsController
     *
     * @param piece the piece to show
     * @param CELL_DIM the size of the cells
     * @param gridSprite the gridsprite it belongs to
     */
    public PieceSprite(BlokusPiece piece, int CELL_DIM, BlokusGridsController gridSprite, Object parent) {
        this.CELL_COUNT = piece.shape.getCellNumber();
        BlokusColor colour = piece.colour;
        this.piece = piece;

        this.parent = parent;

        this.gridSprite = gridSprite;
        this.cells = new CellSprite[CELL_COUNT];
        this.coordinates = piece.getOccupiedCells();
        for (int i = 0; i < CELL_COUNT; i++) {
            cells[i] = new CellSprite(CELL_DIM, CELL_DIM, colour, this);
        }
    }

    /**
     * @return the cells
     */
    public CellSprite[] getCells() {
        return cells;
    }

    /**
     * @return the coordinates
     */
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /**
     * Set the opacity of each CellSprite
     *
     * @param opacity the opacity to set the cellsprites to
     */
    public void setOpacity(double opacity) {
        for (CellSprite cell : getCells()) {
            cell.setOpacity(opacity);
        }
    }

    /**
     * Handles cells being clicked
     *
     * @param cellSprite which cell was clicked
     */
    public void isClicked(CellSprite cellSprite) {
        if (parent instanceof Panel) {
            ((Panel) parent).isClicked(this);
        }
        if (parent instanceof Board) {
            ((Board) parent).isClicked(this);
        }
    }

    /**
     * Handles cells being right-clicked
     *
     * @param cellSprite which cell was clicked
     */
    public void isRightClicked(CellSprite cellSprite) {
        ((Board) parent).isRightClicked(this);
    }

}
