package application.model;

/**
 *  Class of coordinates to be usefully handle
 */
public class Coordinate {
    private final int x;
    private final int y;

    /**
     * Create a new Coordinate
     * @param x the x (vertical) position in the grid
     * @param y the y (horizontal) position in the grid
     */
    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the Coordinate's X-value
     */
    public int getX() { return x; }

    /**
     * @return the Coordinate's Y-value
     */
    public int getY() { return y; }

    /**
     * Move the piece, coords by coords
     * @param move the coordinate to move by
     * @return the new moved coordinate
     */
    public Coordinate moveCoordinate(Coordinate move) {
        return new Coordinate (x + move.x, y + move.y);
    }

    /**
     * Flip the coordinate vertically
     * @return the new flipped coordinate
     */
    public Coordinate flipCoordinate() {
        return new Coordinate (-x, y);
    }

    /**
     * Rotate the coordinate clockwise
     * @return the new rotated coordinate
     */
    public Coordinate rotateCoordinate() {
        return new Coordinate (-y, x);
    }

    /**
     * Scalar Multiplication: multiply each X and Y by an integer c
     * @param c  the scale factor
     * @return   the new coordinate scaled by c
     */
    public Coordinate times(int c) {
        return new Coordinate(x*c,y*c);
    }

    /**
     * Equals function to compare two Coordinates
     * @param o  the other coordinate to compare to
     * @return   the outcome of the comparison: true if equal, false if not
     */
    public Boolean equals(Coordinate o) {
        return (x == o.x && y == o.y);
    }

    /**
     * @return  a list of neighbouring side cells
     */
    public Coordinate[] getSideCells() {
        int count = 4;
        if(x==0 || x==19) count--;
        if(y==0 || y==19) count--;
        Coordinate[] sideCells = new Coordinate[count];
        if(x>0) sideCells[--count] = new Coordinate(x-1,y);
        if(x<19) sideCells[--count] = new Coordinate(x+1,y);
        if(y>0) sideCells[--count] = new Coordinate(x,y-1);
        if(y<19) sideCells[--count] = new Coordinate(x,y+1);
        return sideCells;
    }

    /**
     * @return a list of neighbouring diagonal neighbours
     */
    public Coordinate[] getDiagonalCells() {
        int count = 4;
        if(x==0 || x==19) count-=2;
        if(y==0 || y==19) count-=2;
        if(count==0) count+=2;
        Coordinate[] sideCells = new Coordinate[count];
        if((x>0 && y>0) || (x==0 && y==0)) sideCells[--count] = new Coordinate(x-1,y-1);
        if((x<19 && y<19) || (x==19 && y==19)) sideCells[--count] = new Coordinate(x+1,y+1);
        if((x>0 && y<19) || (x==0 && y==19)) sideCells[--count] = new Coordinate(x-1,y+1);
        if((x<19 && y>0) || (x==19 && y==0)) sideCells[--count] = new Coordinate(x+1,y-1);
        return sideCells;
    }

    /**
     * @return a string encoding of the Coordinate matching the encoding used for moves
     */
    @Override
    public String toString() {
        return Character.toString((char) (x+'A')) + Character.toString((char) (y+'A'));
    }
}
