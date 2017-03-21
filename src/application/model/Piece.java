package application.model;

import java.util.ArrayList;

public class Piece {
	
	private ArrayList<int[]> coord;
	/**
	 * Cette case permettra la rotation des pi�ces
	 */
	private int[] pivot;
	
	private String color;
	
	public Piece(ArrayList<int[]> coord){
		this.setCoord(coord);
	}
	
	public ArrayList<int[]> toLeft(){
		ArrayList<int[]> left = new ArrayList();
            for(int i = 0; i < 4; i++){
                left.add(new int[]{this.getCoord().get(i)[0], this.getCoord().get(i)[1] - 1});
            }
		return left;
	}
	
	public ArrayList<int[]> toRight(){
            ArrayList<int[]> right = new ArrayList();
            for(int i = 0; i < 4; i++){
                right.add(new int[]{this.getCoord().get(i)[0], this.getCoord().get(i)[1] + 1});
            }
		return right;
	}
	
	public ArrayList<int[]> toDown(){
		ArrayList<int[]> down = new ArrayList();
            for(int i = 0; i < 4; i++){
                down.add(new int[]{this.getCoord().get(i)[0] + 1, this.getCoord().get(i)[1]});
            }
		return down;
	}
	
	public ArrayList<int[]> rotate(){
		
		return null;
	}

	public ArrayList<int[]> getCoord() {
		return coord;
	}

	public void setCoord(ArrayList<int[]> coord) {
		this.coord = coord;
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
