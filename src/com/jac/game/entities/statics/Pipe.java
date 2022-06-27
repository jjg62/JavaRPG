package com.jac.game.entities.statics;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class Pipe extends Entity {

    private static Animation pipeImg = new Animation(12, FileUtils.loadAnimationFrames("/textures/entities/interacts/pipe_small.png", 64, 64));
    private Animation animation;

    public Pipe(Room room) {
        super(room, 128, 128);
        setBoundingBox(0, 0, 128, 128);
        animation = pipeImg.clone();
    }


    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.draw(animation.getFrame(), x, y, width, height);
    }
}
