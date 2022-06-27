package com.jac.game.entities;

import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.textures.MobAnimations;
import com.jac.game.tiles.old.Tile;

import java.util.ArrayList;

/** This class needs work but will do for now
 * For entities that move and collide with tiles
 */
public abstract class MovingCollider extends Moving {

    protected int weight = 1;

    //Animations
    protected String name;
    protected Animation currentAnimation;
    protected MobAnimations animations;

    public MovingCollider(Room room, int width, int height, String name)  {
        super(room, width, height);

        //Animations
        this.name = name;
        this.animations = new MobAnimations(this, name, 64, 64);

        if(getDirectionVector() != null){
            changeAnimation();
        }
    }

    protected void moveX(){
        int xTarget = x + xSpeed + bounds.x + (xSpeed > 0 ? bounds.width : 0);
        if(collisionWithTile(xTarget, y + bounds.y) || collisionWithTile(xTarget, y + bounds.y + bounds.height)){
            x = (xTarget / Tile.TILE_WIDTH) * Tile.TILE_WIDTH - bounds.x + (xSpeed > 0 ? - bounds.width - 1 : Tile.TILE_WIDTH);
        }else {
            super.moveX();
        }
    }

    @Override
    protected void moveY(){
        int yTarget = y + ySpeed + bounds.y + (ySpeed > 0 ? bounds.height : 0);
        if(collisionWithTile(x + bounds.x, yTarget) || collisionWithTile(x + bounds.x + bounds.width, yTarget)){
            y = (yTarget / Tile.TILE_HEIGHT) * Tile.TILE_HEIGHT - bounds.y + (ySpeed > 0 ? -bounds.height - 1 : Tile.TILE_HEIGHT);
        }else{
            super.moveY();
        }
    }

    private boolean collisionWithTile(int x, int y){
        return room.getTile(x, y).isSolid();
    }

    protected ArrayList<Entity> collisionWithEntity(int x, int y){
        ArrayList<Entity> collidingEntities = new ArrayList<>();
        for(Entity e : room.getEntities()){
            if(e != this && e.getHurtbox().intersects(this.getHurtbox(x, y))){
                collidingEntities.add(e);
            }
        }
        return collidingEntities;
    }

    protected void changeAnimation(){
        if(name.equals("melee_dweller")) System.out.println(still());
        currentAnimation = animations.getMoveAnimation(still());
        currentAnimation.tick();
    }

    private void unstickEntity(){
        ArrayList<Entity> colliders = collisionWithEntity(0, 0);

        for(Entity collider : colliders) {
            if (collider != null) {
                int myXMiddle = x + bounds.x + bounds.width / 2;
                int myYMiddle = y + bounds.y + bounds.height / 2;
                int eXMiddle = collider.getX() + collider.getBounds().x + collider.getBounds().width / 2;
                int eYMiddle = collider.getY() + collider.getBounds().y + collider.getBounds().height / 2;

                int xMoveDir = Integer.signum(myXMiddle - eXMiddle) != 0 ? Integer.signum(myXMiddle - eXMiddle) : 1;
                int yMoveDir = Integer.signum(myYMiddle - eYMiddle) != 0 ? Integer.signum(myYMiddle - eYMiddle) : 1;

                double weightRatio = collider instanceof MovingCollider ? weight / (double) (weight + ((MovingCollider) collider).getWeight()) : 0;

                xSpeed += xMoveDir * 4 * (1-weightRatio);
                ySpeed += yMoveDir * 4 * (1-weightRatio);
            }
        }
    }

    @Override
    protected void usingMoveValues() {
        super.usingMoveValues();
        changeAnimation();
    }

    @Override
    public void move(){
        unstickEntity();
        moveX();
        moveY();
        xSpeed = 0;
        ySpeed = 0;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
