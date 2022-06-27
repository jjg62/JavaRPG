package com.jac.game.entities.behaviour.projectiles;

import com.jac.game.abilities.ProjectileBehaviour;
import com.jac.game.abilities.Vector;

public class Bouncing extends ProjectileBehaviour {

    protected Vector moveVector;
    protected int speed;

    public Bouncing(int speed){
        this.speed = speed;
    }

    @Override
    public Vector moveCommand() {
        if(moveVector == null){
            moveVector = new Vector(speed, 0);
            moveVector.rotate(angle);
        }
        checkForBounce();
        return moveVector;
    }

    protected void checkForBounce(){
        int px = projectile.getX() + projectile.getWidth()/2;
        int py = projectile.getY() + projectile.getHeight()/2;

        if(collisionWithTile(px + moveVector.x, py)){
            moveVector.x = -moveVector.x;
        }
        if(collisionWithTile(px, py + moveVector.y)){
            moveVector.y = -moveVector.y;
        }
    }

    private boolean collisionWithTile(int x, int y){
        return projectile.getOwner().getRoom().getTile(x, y).isSolid();
    }

    public ProjectileBehaviour copy(){
        return new Bouncing(speed);
    }

}
