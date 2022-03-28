package model.world;

import java.awt.Point;

public class Cover {

	private int currentHP;
	private Point location = new Point();
	
	public Cover(int x, int y) {
		this.location.x=x;
		this.location.y=y;
		
		this.currentHP = (int) ((int) 100 + (Math.random() * ((999 - 100) + 1)));
	}

	public Point getLocation() {
		return location;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public static void main(String[] args) {
		Cover c = new Cover(1, 2);
		System.out.println(c.getCurrentHP());
	}
}
