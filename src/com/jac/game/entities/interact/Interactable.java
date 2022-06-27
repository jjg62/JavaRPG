package com.jac.game.entities.interact;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Player;
import com.jac.game.fx.VisualEffect;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public abstract class Interactable extends Entity {

    private int distanceToPlayer;
    private Player player;

    protected int interactingRange = 128;
    protected boolean targeted = false;

    public Interactable(Room room, int width, int height) {
        super(room, width, height);
    }

    public void setRoom(Room room){
        this.room = room;
    }

    @Override
    public void spawn(int x, int y){
        super.spawn(x, y);
        player = room.getPlayer();
        updateDistanceToPlayer();
        room.getInteractingManager().add(this);
    }

    public void setTargeted(boolean targeted){
        this.targeted = targeted;
    }

    public boolean isInRange() {
        return Math.abs(distanceToPlayer) < interactingRange;
    }

    public int getDistanceToPlayer() {
        return distanceToPlayer;
    }

    private void updateDistanceToPlayer(){
        int xDistance = player.getX() + player.getWidth()/2 - (x + width/2);
        int yDistance = player.getY() + player.getHeight()/2 - (y + height/2);
        distanceToPlayer = (int) Math.sqrt(xDistance*xDistance + yDistance*yDistance);
        if(!isInRange()){
            setTargeted(false);
            room.getInteractingManager().resetInteractTarget();
        }
    }

    @Override
    public void tick(){
        updateDistanceToPlayer();
    }


    public abstract void interaction(InteractingManager manager);

}
