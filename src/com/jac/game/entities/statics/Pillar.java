package com.jac.game.entities.statics;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.rooms.Room;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class Pillar extends Entity {

    private static BufferedImage pillarImg = FileUtils.loadImage("/textures/entities/interacts/pillar.png");

    public Pillar(Room room) {
        super(room, 96, 192);
        setBoundingBox(0, 128, 96, 64);
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(GameGraphics graphics) {
        renderShadow(graphics);
        graphics.draw(pillarImg, x, y, width, height);
    }
}
