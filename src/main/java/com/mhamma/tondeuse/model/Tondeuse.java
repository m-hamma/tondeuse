package com.mhamma.tondeuse.model;

import lombok.Data;
/**
 * Classe qui represente une tondeuse;
*/
@Data
public class Tondeuse {
	private int x;
	private int y;
	private String orientation;
	public String toString () {
	    return this.x+" "+this.y+" " + this.orientation;
	}
 	public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}