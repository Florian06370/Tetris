package application.model;

import application.controller.BlokusGridsController;
import application.controller.BlokusController;
import java.util.ArrayList;

/**
 * Board implements the logistics of the Blokus game, including players' moves,
 * scoring and displaying the pieces
 */
public class Board {

    private final BlokusColor[][] grid;
    private final boolean[] unplacedPiecesRed = new boolean['U' - 'A' + 1];
    private final boolean[] unplacedPiecesGreen = new boolean['U' - 'A' + 1];
    private final boolean[] unplacedPiecesBlue = new boolean['U' - 'A' + 1];
    private final boolean[] unplacedPiecesYellow = new boolean['U' - 'A' + 1];
    private boolean active = false;
    private boolean displayable = true;
    private final boolean[][] unplacedPieces = {unplacedPiecesBlue, unplacedPiecesYellow, unplacedPiecesRed, unplacedPiecesGreen};
    private final ArrayList<String> moves = new ArrayList<>();
    private int currentTurn;
    private final boolean[] lastMove = new boolean[]{false, false, false, false};
    private PieceSprite preview;
    private Coordinate previewCoord;

    public BlokusGridsController gridBoard;
    private BlokusController parent;
    private int xsize;

    /**
     * Getter
     *
     * @return the 2D array of booleans representing whether a piece has been
     * placed
     */
    public boolean[][] getUnplacedPieces() {
        return unplacedPieces;
    }

    /**
     * Getter
     *
     * @return the current turn as an int (where 0=blue, ..., 4=green)
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Setter
     *
     * @param active the state the boardM should be set to; inactive makes the
     * boardM unhoverable
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter
     *
     * @return Returns an array of booleans denoting which player's last turn
     * was placing a monomino
     */
    public boolean[] getLastMove() {
        return lastMove;
    }

    /**
     * Getter
     *
     * @return the grid of the boardM as a 2D array of cells (Colors)
     */
    public BlokusColor[][] getGrid() {
        return grid;
    }

    /**
     * @return the gridBoard
     */
    public BlokusGridsController getGridBoard() {
        return gridBoard;
    }

    /**
     * @return the xsize
     */
    public int getXsize() {
        return xsize;
    }

    /**
     * Takes a BlokusPiece and transfers it to the boardM, after checking that the
 move is valid
     *
     * @param piece the piece to place, representing shape, orientation and
     * coordinate
     * @return the exiting status of the function
     */
    private boolean placePiece(BlokusPiece piece) {

        if (legitimateMove(piece) != "") {
            return false;
        }

        /* Remove piece from unplacedPieces */
        int playerId = (parent != null) ? parent.currentColourId : piece.colour.ordinal();
        int shapeId = piece.shape.ordinal();

        unplacedPieces[playerId][shapeId] = false;

        /* Setting the appropriate cells */
        BlokusColor turnColour = BlokusColor.values()[playerId];
        PieceSprite pieceSprite = new PieceSprite(piece, getXsize(), this.getGridBoard(), this);
        if (displayable) {
            this.getGridBoard().addPieceSprite(pieceSprite);
        }
        /* Set Grid for legitimateMove */
        for (Coordinate coord : pieceSprite.getCoordinates()) {
            grid[coord.getY()][coord.getX()] = turnColour;
        }

        /* Check for monomino */
        lastMove[playerId] = (shapeId == 0);
        moves.add(piece.toString());
        currentTurn = (currentTurn + 1) % 4;
        return true;
    }

    /**
     * Given a four character String, plays the encoded move, after checking
     * it's validity. Converts the string into a BlokusPiece and then calls
 placePiece(BlokusPiece piece)
     *
     * @param move a 4 character string representing a shape, orientation and
     * coordinate
     * @return the exiting status of the function
     */
    public boolean placePiece(String move) {
        if (move == ".") {
            currentTurn = (currentTurn + 1) % 4;
            moves.add(".");
            return true;
        }
        BlokusColor turnColour = BlokusColor.values()[currentTurn];
        BlokusPiece piece = new BlokusPiece(move, turnColour);
        placePiece(piece);
        return true;
    }

    /**
     * @return A String encoding of the boardM, based on the boardM's past moves
     */
    public String toString() {
        String string = "";
        for (String move : moves) {
            string += move + " ";
        }
        return string;
    }

    /**
     * @return A String representation of the boardM's grid, used for testing
     * and debugging
     */
    public String gridToString() {
        String string = "";
        for (BlokusColor[] aGrid : grid) {
            for (BlokusColor anAGrid : aGrid) {
                string += anAGrid.name().substring(0, 1).replace("E", "•") + " ";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Initializes a new Board to be displayed in a JavaFX app
     *
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the size to be used when displaying the cells
     * @param color the background color of the grid
     * @param parent the parent BlokusController class
     */
    public Board(int col, int row, int size, BlokusColor color, BlokusController parent) {
        this.gridBoard = new BlokusGridsController(col, row, size, color, this);
        this.parent = parent;
        this.xsize = size;

        /* Initialise the grid */
        grid = new BlokusColor['T' - 'A' + 1]['T' - 'A' + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = BlokusColor.Empty;
            }
        }

        /* Fill up array of unused pieces */
        for (boolean[] unplacedPieceList : unplacedPieces) {
            for (int i = 0; i < unplacedPieceList.length; i++) {
                unplacedPieceList[i] = true;
            }
        }

        /* When the mouse leaves the boardM, hide the Display piece */
        //    this.gridBoard.setOnMouseExited(event -> isUnhovered());
    }

    /**
     * Construct a new Board not for JavaFX displaying
     *
     * @param game a string encoding of a game containing a previous set of
     * moves
     */
    public Board(String game) {
        /* Won't be displayed as a GridPane */
        displayable = false;

        /* Fill up array of unused pieces */
        for (boolean[] unplacedPieceList : unplacedPieces) {
            for (int i = 0; i < unplacedPieceList.length; i++) {
                unplacedPieceList[i] = true;
            }
        }

        /* Initialise the grid from the String */
        game = game.replace(" ", "");
        grid = new BlokusColor['T' - 'A' + 1]['T' - 'A' + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = BlokusColor.Empty;
            }
        }
        String[] moves = splitMoves(game);
        for (String move : moves) {
            placePiece(move);
        }
    }

    /**
     * Split a string encoding into an array of strings, each representing a
     * single move
     *
     * @param game the string encoding of a game
     * @return moves an array of strings, each one representing a single move
     */
    public static String[] splitMoves(String game) {

        /* An array is used instead of an ArrayList for compatibility */
        game = game.replace(" ", "");

        /* Work out how long the array should be */
        int passCount = game.length() - game.replace(".", "").length();
        int moveCount = (game.length() - passCount) / 4;
        int totalCount = passCount + moveCount;
        if ((passCount + 4 * moveCount) != game.length()) {
            throw new IllegalArgumentException("Invalid game");
        }
        String[] moves = new String[totalCount];

        int index = 0;
        for (int i = 0; i < totalCount; i++) {
            if (game.charAt(index) == '.') {
                moves[i] = ".";
                index++;
                continue;
            }
            moves[i] = game.substring(index, index + 4);
            index += 4;
        }

        return moves;
    }

    /**
     * Checks that a potential BlokusPiece move is legitimate
     *
     * @param piece the move encoded as a piece to check
     * @return the legitimacy of the move
     */
    public String legitimateMove(BlokusPiece piece) {

        int playerId = currentTurn;

        /* Check that piece hasn't been played yet */
        if (!unplacedPieces[playerId][piece.shape.ordinal()]) {
            return "Starting piece must occupy the player's respective corner.";
        }

        Coordinate[] cells = piece.getOccupiedCells();
        BlokusColor turnColour = piece.colour;

        String touchingSide = "Pieces must be connected to at least one other piece of the same color by the corner.";
        for (Coordinate cell : cells) {

            /* Check that the coordinate is not outside the grid */
            if (cell.getX() < 0 || cell.getX() > 19 || cell.getY() < 0 || cell.getY() > 19) {
                return "Piece must be placed entirely on the board.";
            }

            /* Check that coordinates are empty */
            if (grid[cell.getY()][cell.getX()] != BlokusColor.Empty) {
                return "Pieces cannot overlap.";
            }

            /* Check that no sides are being touched */
            for (Coordinate sideCell : cell.getSideCells()) {
                if (cellAt(sideCell) == turnColour) {
                    return "Pieces of the same color cannot share edges with one another.";
                }
            }

            /* Check that there's at least one touching diagonal */
            for (Coordinate diagonalCell : cell.getDiagonalCells()) {
                if (cellAt(diagonalCell) == turnColour) {
                    touchingSide = "";
                }
            }
        }
        return touchingSide;
    }

    /**
     * Given a Coordinate, returns the cell on the boardM at the location,
     * including corners for starting positions
     *
     * @param c the coordinate to query
     * @return the cell (as a BlokusColor) at the location
     */
    public BlokusColor cellAt(Coordinate c) {

        /* Check for starting corners */
        if (c.getX() == -1 && c.getY() == -1) {
            return BlokusColor.Blue;
        }
        if (c.getX() == -1 && c.getY() == 20) {
            return BlokusColor.Green;
        }
        if (c.getX() == 20 && c.getY() == -1) {
            return BlokusColor.Yellow;
        }
        if (c.getX() == 20 && c.getY() == 20) {
            return BlokusColor.Red;
        }

        /* Not a corner */
        return grid[c.getY()][c.getX()];
    }

    /**
     * When the boardM is clicked, determine the coordinate of the cell clicked
     * and surrender it to the current player
     *
     * @param cell the cell under the mouse for the click event
     */
    public void isClicked(CellSprite cell) {
        int x = this.getGridBoard().getGridpane().getColumnIndex(cell);
        int y = this.getGridBoard().getGridpane().getRowIndex(cell);

        parent.currentPlayer.handleClick(x, y);

    }

    /**
     * Deals with the Preview piece getting in the way of clicks
     *
     * @param sprite the PieceSprite on the boardM that was clicked
     */
    public void isClicked(PieceSprite sprite) {
        if (sprite == preview) {
            int x = sprite.getCoordinates()[0].getX();
            int y = sprite.getCoordinates()[0].getY();
            parent.currentPlayer.handleClick(x, y);

        }
    }

    /**
     * Deals with the boardM being right-clicked
     *
     * @param sprite the PieceSprite on the boardM that was right-clicked
     */
    public void isRightClicked(PieceSprite sprite) {
        parent.piecePreparerM.rotatePiece();
        BlokusPiece piece = parent.piecePreparerM.getPiece();
        if (piece == null) {
            return;
        }
        piece = new BlokusPiece(piece);
        piece.setXY(previewCoord);
        isUnhovered();
        previewPiece(piece);
    }

    /**
     * Previews a Preview piece (or shadow piece) under the mouse when a cell is
     * hovered over
     *
     * @param cell the CellSprite being hovered over by the mouse
     */
    public void isHovered(CellSprite cell) {
        if (!active || parent.currentPlayer == null) {
            return;
        }
        int x = this.getGridBoard().getGridpane().getColumnIndex(cell);
        int y = this.getGridBoard().getGridpane().getRowIndex(cell);
        Coordinate tempCoord = new Coordinate(x, y);
        if (previewCoord != null && tempCoord.equals(previewCoord)) {
            return;
        }

        /* Remove the previous shadow piece */
        isUnhovered();
        previewCoord = tempCoord;
        BlokusPiece piece = parent.piecePreparerM.getPiece();
        if (piece == null) {
            return;
        }
        piece = new BlokusPiece(piece);
        piece.setXY(previewCoord);
        this.previewPiece(piece);
    }

    /**
     * Remove the current preview piece under the mouse
     */
    public void isUnhovered() {
        if (preview != null) {
            this.getGridBoard().removePieceSprite(preview);
            preview = null;
        }
        previewCoord = null;
    }

    /**
     * Displays a transparent BlokusPiece on top of the boardM (above other pieces)
     *
     * @param piece the BlokusPiece to place
     */
    public void previewPiece(BlokusPiece piece) {
        preview = new PieceSprite(piece, getXsize(), this.getGridBoard(), this);
        this.getGridBoard().addPieceSprite(preview);
    }

    /**
     * Returns the score of a game
     *
     * @return an array of ints representing the score of each player
     */
    public int[] currentScore() {
        int[] scores = new int[]{89, 89, 89, 89};
        int[] pieceLenghts = new int[]{1, 2, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < getUnplacedPieces()[i].length; j++) {
                if (getUnplacedPieces()[i][j]) {
                    scores[i] -= pieceLenghts[j];
                }
            }
        }

        return scores;
    }

}
