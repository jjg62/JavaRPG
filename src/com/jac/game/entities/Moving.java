package com.jac.game.entities;

import com.jac.game.abilities.Vector;
import com.jac.game.entities.behaviour.Behaviour;
import com.jac.game.entities.structs.Facing;
import com.jac.game.rooms.Room;

public abstract class Moving extends Entity implements Facing {

    protected int xSpeed = 0;
    protected int ySpeed = 0;
    protected Behaviour behaviour;

    public Moving(Room room, int width, int height) {
        super(room, width, height);
    }

    public void changeSpeed(int amountX, int amountY){
        xSpeed += amountX;
        ySpeed += amountY;
    }

    public void move(){
        moveX();
        moveY();
    }

    protected void moveX(){
        x += xSpeed;
        xSpeed = 0;
    }

    protected void moveY(){
        y += ySpeed;
        ySpeed = 0;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Vector getDirectionVector(){
        if(behaviour != null)  return behaviour.getDirectionVector();
        return new Vector(Integer.signum(xSpeed), Integer.signum(ySpeed));
    }

    public void setBehaviour(Behaviour behaviour){
        this.behaviour = behaviour;
    }

    private void getMovementsFromBehaviour(){
        if(behaviour != null){
            Vector movements = behaviour.moveCommand();
            changeSpeed(movements.x, movements.y);
        }
    }

    public void tick(){
        if(!frozen()) {
            getMovementsFromBehaviour();
        }
        usingMoveValues();
        move();
    }

    protected boolean still(){
        return Math.abs(xSpeed) + Math.abs(ySpeed) == 0;
    }

    //For any processing done using the move values before they are reset to 0 by move()
    protected void usingMoveValues(){

    }

    protected boolean frozen(){
        return room.isInCutscene();
    }
}
