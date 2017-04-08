package application.model;

import application.controller.BlokusGridsController;
import application.controller.BlokusController;
import javafx.scene.Node;
import java.util.ArrayList;

/**
 * Panel is one of the player's grid, showing all of the remaining piece to 
 place on the board.
 */
public final class Panel{

    public  ArrayList<Shape> shapes= new ArrayList<>();
    public  ArrayList<Shape> activeShapes = new ArrayList<>();
    public PieceSprite temporary = null;

    private  ArrayList<PieceSprite> pieceSprites = new ArrayList<>();
    private final BlokusColor color;
    private final BlokusController parent;
    private boolean active = false;
    
    private BlokusGridsController gridPanel;
    private int xsize;

    /**
     * Creates a new Panel to display the pieces of a player
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the dimension for displaying cells
     * @param color the default background color
     * @param parent the BlokusController class which instantiates this class
     */
    public Panel(int col, int row, int size, BlokusColor color, BlokusController parent) {
        this.gridPanel = new BlokusGridsController(col, row, size, color, this);
        this.xsize = size;
        this.parent = parent;
        this.color = color;
        for(Node node : this.gridPanel.getGridpane().getChildren())
            node.setOpacity(0.2);
        this.setActive(false);

            addPiece(Shape.A, 'H', 0, 0);
            addPiece(Shape.B, 'H', 2,0);
            addPiece(Shape.J, 'H', 5,0);
            addPiece(Shape.C, 'H', 0,2);
            addPiece(Shape.E, 'H', 4,2);
            addPiece(Shape.Q, 'D', 7,4);
            addPiece(Shape.K, 'D', 0,4);
            addPiece(Shape.F, 'E', 5,4);
            addPiece(Shape.M, 'B', 2,7);
            addPiece(Shape.H, 'H', 8,6);
            addPiece(Shape.T, 'H', 1,9);
            addPiece(Shape.P, 'G', 5,10);
            addPiece(Shape.R, 'G', 7,11);
            addPiece(Shape.O, 'G', 0,14);
            addPiece(Shape.U, 'F', 6,13);
            addPiece(Shape.G, 'E', 9,13);
            addPiece(Shape.S, 'F', 4,17);
            addPiece(Shape.I, 'B', 7,15);
            addPiece(Shape.L, 'G', 1,19);
            addPiece(Shape.D, 'E', 5,18);
            addPiece(Shape.N, 'G', 8,19);
       
    }
    
    /**
     * @return the gridPanel
     */
    public BlokusGridsController getGridPanel() {
        return gridPanel;
    }
    
    /**
     * If the Panel is un-active (not the current player's panel), sets the 
     * opacity to 40%.
     * @param active a boolean representing whether the Panel is active
     */
    public void setActive(boolean active) {
        this.active = active;
        this.getGridPanel().getGridpane().setOpacity(active?1:0.4);
    }

    /**
     * Adds a Shape to the Panel
     * @param shape the shape to add to the Panel
     * @param orientation the orientation to set the shape to
     * @param x the x-value to move the shape to
     * @param y the y-value to move the shape to
     */
    private void addPiece(Shape shape, char orientation, int x, int y) {
        BlokusPiece myPiece = new BlokusPiece(shape, color);
        myPiece.initialisePiece(new Coordinate(x, y), orientation);
        PieceSprite myPieceSprite = new PieceSprite(myPiece, xsize, this.getGridPanel(), this);
        pieceSprites.add(myPieceSprite);
        shapes.add(shape);
        activeShapes.add(shape);
        this.getGridPanel().addPieceSprite(myPieceSprite);
    }

    /**
     * Removes a piece from the Panel
     * @param shape the shape to remove from the Panel
     */
    public void removePiece(Shape shape) {
        int index = shapes.indexOf(shape);
        if(index==-1) return;
        PieceSprite sprite = pieceSprites.get(index);
        pieceSprites.remove(index);
        shapes.remove(index);
        activeShapes.remove(shape);
        this.getGridPanel().removePieceSprite(sprite);
    }

    /**
     * Handle click events by passing the selected piece to the PiecePreparer
     * @param sprite the sprite which has been clicked
     */
    public void isClicked(PieceSprite sprite) {
        if(!active || !activeShapes.contains(sprite.piece.shape)) return;
        replace();
        temporary = sprite;
        this.getGridPanel().removePieceSprite(sprite);
        parent.piecePreparerM.addShape(sprite.piece.shape, color,sprite.piece.toString().charAt(1));
        parent.piecePreparerM.setActive(true);
        pieceSprites.remove(sprite);
        shapes.remove(sprite.piece.shape);
        activeShapes.remove(sprite.piece.shape);
    }

    public void replace() {
        if(temporary != null) {
            pieceSprites.add(temporary);
            shapes.add(temporary.piece.shape);
            activeShapes.add(temporary.piece.shape);
            this.getGridPanel().addPieceSprite(temporary);
        }
        temporary=null;
    }
    
}
