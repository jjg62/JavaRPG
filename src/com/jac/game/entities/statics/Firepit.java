package com.jac.game.entities.statics;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.fx.VisualEffect;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class Firepit extends Entity {

    private static Animation firepit = new Animation(100, FileUtils.loadImage("/textures/entities/interacts/firepit.png"));
    private static Animation firepitLit = new Animation(12, FileUtils.loadAnimationFrames("/textures/entities/interacts/firepit_lit.png", 32, 32));
    //private static Animation lightAnimation = new Animation(12, FileUtils.loadAnimationFrames("/textures/fx/torchFlicker.png", 32, 32));

    private Animation animation;
    private VisualEffect light;

    public Firepit(Room room) {
        super(room, 64, 64);
        setBoundingBox(0, 32, 64, 32);
        //light = new VisualEffect(room, lightAnimation.clone(), 128, 128, 48, true);
        animation = firepit;
    }


    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.draw(animation.getFrame(), x, y, width, height);
    }

    public void ignite(){
        animation = firepitLit;
        //light.play(x-32, y-32);
        //play sound
    }
}
