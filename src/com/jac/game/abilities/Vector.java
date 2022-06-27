package com.jac.game.abilities;

public class Vector {

    public int x;
    public int y;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return x + ", " + y;
    }

    public Vector scale(int scalar){
        return new Vector(x * scalar, y * scalar);
    }

    public void rotate(double angle){

        int newX = (int) (x * Math.cos(angle) - y * Math.sin(angle));
        int newY = (int) (x * Math.sin(angle) + y * Math.cos(angle));

        this.x = newX;
        this.y = newY;
    }
}
