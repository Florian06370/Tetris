package application.model;

/**
 * Piece represents a set of cells from a shape with color, rotation and
 * position
 */
public class BlokusPiece {

    public final Shape shape;
    public final BlokusColor colour;
    public int rotation = 0;
    public boolean flip = false;

    private Coordinate[] occupiedCells;
    private final int cellNumber;

    /**
     * Initializes a Piece object from a shape id and a color
     *
     * @param id the piece shape, e.g. shape.D
     * @param c one of: Red, Blue, Green, Yellow
     */
    public BlokusPiece(Shape id, BlokusColor c) {
        shape = id;
        colour = c;
        occupiedCells = shape.getCoordinates();
        cellNumber = shape.getCellNumber();
    }

    /**
     * Initializes a Piece object from an string encoded move and a color
     *
     * @param move a string encoding of a move which is 4 characters long
     * @param c the color of the player making the move
     */
    public BlokusPiece(String move, BlokusColor c) {
        int pieceChar = move.charAt(0) - 'A';
        char rotation = move.charAt(1);
        int x = move.charAt(2) - 'A';
        int y = move.charAt(3) - 'A';
        Coordinate coord = new Coordinate(x, y);
        shape = Shape.values()[pieceChar];
        occupiedCells = shape.getCoordinates();
        cellNumber = shape.getCellNumber();
        initialisePiece(coord, rotation);
        colour = c;
    }

    /**
     * Sets a new location for the piece by first shifting it to the origin,
     * then shifting it to the new desired location
     *
     * @param newCoord new location of home cell
     */
    public void setXY(Coordinate newCoord) {
        shiftPiece(this.occupiedCells[0].times(-1));
        shiftPiece(newCoord);
    }

    /**
     * @return the array of cells (as Coordinates) the Piece is made up of
     */
    public Coordinate[] getOccupiedCells() {
        return occupiedCells.clone();
    }

    /**
     * Flips a piece in the y direction, maintaining the coordinate of the
     * piece's base cell
     */
    private void flipPiece() {
        Coordinate origin = new Coordinate(occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < cellNumber; i++) {
            occupiedCells[i] = occupiedCells[i].flipCoordinate();
        }
        Coordinate shift = new Coordinate(origin.getX() - occupiedCells[0].getX(),
                origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
        flip = !flip;
    }

    /**
     * Rotates a piece about the piece's origin cell
     */
    private void rotatePiece() {
        Coordinate origin = new Coordinate(occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < cellNumber; i++) {
            occupiedCells[i] = occupiedCells[i].rotateCoordinate();
        }
        Coordinate shift = new Coordinate(origin.getX() - occupiedCells[0].getX(),
                origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
        rotation = (rotation + 1) % 4;
    }

    /**
     * Given a four character String and the identifying number of the current
     * player, sets the grid coordinates to that player's color where the piece
     * is played.
     *
     * @param orientation a character denoting the desired orientation.
     * 'A','B','C','D' are rotation by 0, 90, 180 and 270 degrees respectively.
     * 'E','F','G','H' are reflections in the y axis followed by rotations of 0,
     * 90, 180 and 270 degrees respectively
     */
    private void orientatePiece(char orientation) {
        if (orientation > 'D') {
            flipPiece();
        }
        for (int i = 0; i < (orientation - 'A') % 4; i++) {
            rotatePiece();
        }
    }

    /**
     * Translates a piece a certain number of cells in the x direction and a
     * certain number of cells in the y direction
     *
     * @param shift a coordinate object representing the desired shift amount in
     * the x and y direction
     */
    private void shiftPiece(Coordinate shift) {
        for (int i = 0; i < cellNumber; i++) {
            occupiedCells[i] = occupiedCells[i].moveCoordinate(shift);
        }
    }

    /**
     * Used to set the initial position of the piece. Can be used multiple times
     * and can be a handy 'reset' function.
     *
     * @param origin a coordinate which sets where the base cell will go
     * @param orientation a char from 'A' - 'H' which sets the initial
     * orientation of the piece
     */
    public void initialisePiece(Coordinate origin, char orientation) {
        occupiedCells = shape.getCoordinates();
        flip = false;
        rotation = 0;
        orientatePiece(orientation);
        shiftPiece(origin);
    }

    /**
     * Creates a copy of piece
     *
     * @return a new piece object with identical field values
     */
    public BlokusPiece(BlokusPiece original) {
        this(original.toString(), original.colour);
    }

    /**
     * This function encapsulates all transformations: shift, rotate, flip. If
     * only a shift is required, set rotateClockwise to 0 and flip to false. If
     * no shift is required, simply omit the shift parameter.
     *
     * @param rotateClockwise an int determining the number of times the piece
     * is to be rotated clockwise
     * @param flip a boolean determining if the piece is to be reflected
     */
    public void movePiece(int rotateClockwise, boolean flip) {
        int newRotation = (((flip && rotation % 2 == 1) ? 2 : 0) + rotateClockwise + rotation) % 4;
        boolean newFlip = flip ^ this.flip;
        initialisePiece(getOccupiedCells()[0], (char) (newRotation + ((newFlip) ? 4 : 0) + 'A'));
    }

    /**
     * Piece's toString function, according to Board's encoding scheme
     *
     * @return string encoding of the piece, comprised of color, shape and the
     * home coordinate
     */
    @Override
    public String toString() {
        String shapeS = shape.toString();
        String orienS = Character.toString((char) (((flip) ? 4 : 0) + rotation + 'A'));
        String coordS = occupiedCells[0].toString();
        return shapeS + orienS + coordS;
    }
}
