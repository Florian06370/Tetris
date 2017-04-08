package application.model;

import application.controller.BlokusController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Represents a player and all that he can do
 */
public class Player {

    private final BlokusController parent;
    private Board board;

    /**
     * Creates a new player
     *
     * @param parent the BlokusController class
     */
    public Player(BlokusController parent) {
        this.parent = parent;
    }

    /**
     * Handle clicks on the board: gets piece when clicked, makes move if
     * legitimate
     *
     * @param x the clicked cell's column
     * @param y the clicked cell's row
     */
    public void handleClick(int x, int y) {
        if (parent.piecePreparerM.getPiece() != null) {
            BlokusPiece piece = new BlokusPiece(parent.piecePreparerM.getPiece());
            piece.setXY(new Coordinate(x, y));
            if (board.legitimateMove(piece) == "") {
                parent.makeMove(piece.toString());
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Illegitimate move");
                alert.setHeaderText(board.legitimateMove(piece));

                alert.showAndWait();
            }
        }
    }

    /**
     * Sets the piecePreparerM and Panel of current player to active
     *
     * @param string represents the boardM as a string
     * @return
     */
    public String isPlaying(String string) {
        this.board = new Board(string);
        parent.currentPanel.setActive(true);

        // Wait for click()
        return "";
    }

    /**
     * When finished or unable to place another piece, player can surrend and
     * never plays until end of game
     *
     * @param parent
     */
    public void surrender(BlokusController parent) {
        parent.makeMove(".");
    }

}
