package com.jac.game.entities;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.rooms.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

/** An entity is an object inside a room that will need to be updated and rendered evert tick.
 */
public abstract class Entity implements Ticking {

    public Entity(Room room, int width, int height){
        this.width = width;
        this.height = height;
        this.room = room;

        bounds = new Rectangle(0, 0, width-1, height-1);
    }

    public void spawn(int x, int y){
        setX(x);
        setY(y);
        room.addEntity(this);
    }

    protected Room room;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Rectangle bounds;

    public abstract void tick();

    public abstract void render(GameGraphics graphics);

    public void setBoundingBox(int x, int y, int width, int height){
        bounds = new Rectangle(x, y, width-1, height-1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Room getRoom() {
        return room;
    }

    public Rectangle getHurtbox(){
        return getHurtbox(0, 0);
    }

    public Rectangle getHurtbox(int xOffset, int yOffset){
        return new Rectangle(x + bounds.x + xOffset, y + bounds.y + yOffset, bounds.width, bounds.height);
    }

    protected void renderShadow(GameGraphics graphics){
        graphics.drawShadow(x + width/3, y + height * 5/6, bounds.width/64.0);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
