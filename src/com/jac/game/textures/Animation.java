package com.jac.game.textures;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Ticking;

import java.awt.image.BufferedImage;

public class Animation implements Ticking {

    private boolean loop = true;
    private final int normalSpeed;
    private int speed;
    private int timer = 0;
    private int index = 0;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage... frames){
        this.normalSpeed = speed;
        this.speed = normalSpeed;
        this.frames = frames;
    }

    public Animation(boolean loop, int speed, BufferedImage... frames){
        this(speed, frames);
        this.loop = loop;
    }

    public void tick(){
        timer++;
        if(timer >= speed){
            index++;
            timer = 0;

            if(index >= frames.length){
                index = loop ? 0 : frames.length-1;
            }
        }
    }

    public BufferedImage getFrame(){
        return frames[index];
    }

    public Animation clone(){
        return new Animation(loop, speed, frames);
    }

    public void reset(){
        index = 0;
    }

    @Override
    public void render(GameGraphics graphics) {
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
