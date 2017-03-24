package application.model;

import java.util.ArrayList;

public class Piece {
	
	private ArrayList<int[]> coord;
	/**
	 * Cette case permettra la rotation des pièces
	 */
	private int[] pivot;
	
	private String color;
	
        //Piece définie par ses coordonnées
	public Piece(ArrayList<int[]> coord){
		this.coord = coord;
	}
	
    //Fonctions de deplacement des pieces
	public ArrayList<int[]> toLeft(){
		ArrayList<int[]> left = new ArrayList<int[]>();

		int[] newPivot = pivot;
		
		for(int[] cord : coord){
			int[] newCoord = new int[]{cord[0],cord[1]-1};
            
			left.add(newCoord);
            
            if(pivot == cord){
				newPivot = newCoord;
			}
        }
		
		left.add(newPivot);
		
		return left;
	}
	
	public ArrayList<int[]> toRight(){
		ArrayList<int[]> right = new ArrayList<int[]>();

		int[] newPivot = pivot;
		
		for(int[] cord : coord){
			int[] newCoord = new int[]{cord[0],cord[1]+1};
            
			right.add(newCoord);
            
            if(pivot == cord){
				newPivot = newCoord;
			}
        }
		
		right.add(newPivot);
		
		return right;
	}
	
	public ArrayList<int[]> toDown(){
		ArrayList<int[]> down = new ArrayList<int[]>();
		int[] newPivot = pivot;
		
		for(int[] cord : coord){
			int[] newCoord = new int[]{cord[0]+1,cord[1]};
            
            down.add(newCoord);
            
            if(pivot == cord){
				newPivot = newCoord;
			}
        }
		
		down.add(newPivot);
		
		return down;
	}
	
	public ArrayList<int[]> rotate(){
		ArrayList<int[]> rotate = new ArrayList<int[]>();
		int[] newPivot = pivot;
		
		for(int[] cord : coord){
			
			int[] vector = new int[]{cord[0]-pivot[0],cord[1]-pivot[1]};
			
			int[] vectorRotate = new int[]{-vector[1],vector[0]};
			
			int[] newCoord = new int[]{pivot[0]+vectorRotate[0],pivot[1]+vectorRotate[1]};
			
			rotate.add(newCoord);
			
			if(pivot == cord){
				newPivot = newCoord;
			}
		}
		
		rotate.add(newPivot);
		
		return rotate;
	}

        
        //GETTER & SETTER
	public ArrayList<int[]> getCoord() {
		return coord;
	}

	public void setCoord(ArrayList<int[]> coord) {
		this.coord = coord;
		pivot = coord.remove(coord.size()-1);
	}
	
	public void setPivot(int [] pivot){
		this.pivot = pivot;
	}
	
	public String getColor(){
		return color;
	}
	
	public void setColor(String color){
		this.color = color;
	}

}
