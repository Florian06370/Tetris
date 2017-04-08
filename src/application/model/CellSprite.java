package application.model;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 * CellSprite represents a Cell
 */
public class CellSprite extends Pane {

    private Object parent = null;

    /**
     * Creates a new CellSprite to represent a cell on the board
     *
     * @param width the width for displaying the cell
     * @param height the height for displaying the cell
     * @param color the color of the desired cell
     * @param parent the piece which the cell belongs to
     */
    public CellSprite(double width, double height, BlokusColor color, Object parent) {
        super();
        super.setMinSize(width, height);
        this.parent = parent;

        /* Set background image for the cell */
        String format = "-fx-background-image: url('application/resources/%s.png'); -fx-background-size: 100%%;";
        this.setStyle(String.format(format, color.name()));
        eventWatcher();
    }

    /**
     * Creates a new CellSprite with a custom background image (e.g. for
     * corners)
     *
     * @param width the width for displaying the cell
     * @param height the height for displaying the cell
     * @param image the image to use as a background
     * @param parent the piece which the cell belongs to
     */
    public CellSprite(double width, double height, String image, Object parent) {
        super();
        super.setMinSize(width, height);
        this.parent = parent;

        /* Set background image for the cell */
        String format = "-fx-background-image: url('application/resources/%s.png'); -fx-background-size: 100%%;";
        this.setStyle(String.format(format, image));
        eventWatcher();
    }

    /**
     * Watch for mouse clicks and mouse hovers to pass back to the Parent class
     */
    private void eventWatcher() {
        CellSprite dummyCell = this;

        /* On mouse click */
        this.setOnMouseClicked(event -> {
            /* Check for RIGHT click */
            if (event.getButton() == MouseButton.SECONDARY) {
                ((PieceSprite) parent).isRightClicked(dummyCell);
            } /* Check for LEFT click */ else {
                if (parent instanceof PieceSprite) {
                    ((PieceSprite) parent).isClicked(dummyCell);
                }
                if (parent instanceof Board) {
                    ((Board) parent).isClicked(dummyCell);
                }
            }
        });

        /* On mouse hover */
        this.setOnMouseEntered(event -> {
            if (parent instanceof Panel) {
                return;
            }
            if (parent instanceof Board) {
                ((Board) parent).isHovered(dummyCell);
            }

        });
    }

}
