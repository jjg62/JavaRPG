package com.jac.game.entities;

import com.jac.game.abilities.Vector;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.textures.MobAnimations;
import com.jac.game.utils.FileUtils;

public class CutsceneActor extends MovingCollider {

    private boolean inCustomAnimation = false;
    private Animation customAnimation;
    private Vector directionVector;

    public CutsceneActor(Room room, int width, int height, String name) {
        super(room, width, height, name);
        directionVector = new Vector(0, 0);
        setBoundingBox(0, 0, 0, 0);
    }

    @Override
    public void changeAnimation(){
        if(inCustomAnimation){
            currentAnimation = customAnimation;
            currentAnimation.tick();
        }else{
            super.changeAnimation();
        }
    }

    public void updateDirectionVector(){
        directionVector.x = Integer.signum(xSpeed);
        directionVector.y = Integer.signum(ySpeed);
    }

    //This can be optimised, by implementing the same kind of thing in moving.
    @Override
    public Vector getDirectionVector(){
        if(directionVector == null) return new Vector(0, 0);
        return directionVector;
    }

    public void playCustomAnimation(String name, int speed){
        inCustomAnimation = true;
        String path = "/textures/entities/" + this.name + "/custom/" + this.name + "_" + name + ".png";
        customAnimation = new Animation(speed, FileUtils.loadAnimationFrames(path, 64, 64));
    }

    public void stopCustomAnimation(){
        inCustomAnimation = false;
    }

    @Override
    public void render(GameGraphics graphics) {
        renderShadow(graphics);
        graphics.draw(currentAnimation.getFrame(), x, y, width, height);
    }
}
