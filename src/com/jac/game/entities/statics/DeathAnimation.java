package com.jac.game.entities.statics;

import com.jac.game.audio.Sound;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class DeathAnimation extends Entity {

    private static final int duration = 96;
    private Animation animation;
    private Sound sound;

    public DeathAnimation(Room room, int width, int height, String name) {
        super(room, width, height);
        this.animation = new Animation(false, 8, FileUtils.loadAnimationFrames("/textures/entities/" + name + "/death/" + name + "_death.png", 64, 64));
        this.sound = new Sound("entities/" + name + "/death/" + name + "_death.wav");
    }

    @Override
    public void spawn(int x, int y){
        super.spawn(x, y);
        animation.reset();
        Scheduler.getInstance().addTimedAction(duration, ()->room.removeEntity(this));
        sound.play();
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
