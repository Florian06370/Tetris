package application.model;

import java.util.ArrayList;

public class PieceFactory {

    
    /**
     * Creation des pieces 
     * @param name
     * @return Piece
     */
	public static Piece getPiece(String name) {

		ArrayList<int[]> coord = new ArrayList<int[]>();
		Piece piece;
		switch (name) {
		case "S":
			coord.add(new int[]{0,2});
			coord.add(new int[]{0,1});
			coord.add(new int[]{1,1});
			coord.add(new int[]{1,0});
			piece = new Piece(coord);
			piece.setPivot(coord.get(1));
			piece.setColor("red");
			return piece;
		case "Z":
			coord.add(new int[]{0,0});
			coord.add(new int[]{0,1});
			coord.add(new int[]{1,1});
			coord.add(new int[]{1,2});
			piece = new Piece(coord);
			piece.setPivot(coord.get(1));
			piece.setColor("blue");
			return piece;
		case "J":
			coord.add(new int[]{0,1});
			coord.add(new int[]{0,2});
			coord.add(new int[]{0,3});
			coord.add(new int[]{1,3});
			piece = new Piece(coord);
			piece.setPivot(coord.get(2));
			piece.setColor("orange");
			return piece;
		case "L":
			coord.add(new int[]{0,0});
			coord.add(new int[]{1,0});
			coord.add(new int[]{2,0});
			coord.add(new int[]{2,1});
			piece = new Piece(coord);
			piece.setPivot(coord.get(2));
			piece.setColor("yellow");
			return piece;
		case "O":
			coord.add(new int[]{0,0});
			coord.add(new int[]{0,1});
			coord.add(new int[]{1,0});
			coord.add(new int[]{1,1});
			piece = new Piece(coord);
			piece.setPivot(coord.get(1));
			piece.setColor("green");
			return piece;
		case "I":
			coord.add(new int[]{0,0});
			coord.add(new int[]{1,0});
			coord.add(new int[]{2,0});
			coord.add(new int[]{3,0});
			piece = new Piece(coord);
			piece.setPivot(coord.get(1));
			piece.setColor("purple");
			return piece;
		case "T":
			coord.add(new int[]{0,0});
			coord.add(new int[]{1,0});
			coord.add(new int[]{2,0});
			coord.add(new int[]{1,1});
			piece = new Piece(coord);
			piece.setPivot(coord.get(3));
			piece.setColor("cyan");
			return piece;
                case "i":
                        coord.add(new int[]{0,0});
                        coord.add(new int[]{1,0});
			coord.add(new int[]{2,0});
                        
                case "bbi":
                        coord.add(new int[]{0,0});
                        coord.add(new int[]{1,0});
                        
		}
		return null;
	}

}
