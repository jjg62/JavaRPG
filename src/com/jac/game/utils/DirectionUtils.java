package com.jac.game.utils;

import com.jac.game.abilities.Vector;

import java.util.HashMap;

public class DirectionUtils {


    public static String vectorToString(Vector directionVector){
        String directionString = "";
        directionString += (directionVector.y == -1 ? "n" : "") + (directionVector.y == 1 ? "s" : "");
        directionString += (directionVector.x == 1 ? "e" : "") + (directionVector.x == -1 ? "w" : "");

        if(directionString.equals("")) directionString = "s";
        return directionString;
    }

    public static double vectorToRadians(Vector directionVector){
        return Math.atan2(directionVector.y, directionVector.x);
    }

    //Account for pythagoras in diagonals
    public static Vector standardiseVector(Vector vector){
        Vector standardised = new Vector(0, 0);
        double multiplier = 1.0;
        if(vector.x != 0 && vector.y != 0){
            multiplier = 0.71;
        }
        standardised.x = (int)(vector.x * multiplier);
        standardised.y = (int)(vector.y * multiplier);
        return standardised;
    }


    public static Vector radiansToDirectionVector(double radians){
        HashMap<Integer, Vector> sectorToVector = new HashMap<>(){{
            put(0, new Vector(1, 0));
            put(1, new Vector(1, 1));
            put(2, new Vector(0, 1));
            put(3, new Vector(-1, 1));
            put(4, new Vector(-1, 0));
            put(5, new Vector(-1, -1));
            put(6, new Vector(0, -1));
            put(7, new Vector(1, -1));
        }};


        double sector = radians;
        sector = sector < 0 ? 2 * Math.PI + sector : sector;
        sector += Math.PI/8;
        sector /= (Math.PI / 4.0);
        sector %= 8;
        return sectorToVector.get((int)sector);
    }
}
