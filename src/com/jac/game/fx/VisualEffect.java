package com.jac.game.fx;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.rooms.Room;
import com.jac.game.rooms.RoomList;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class VisualEffect implements Ticking {

    private Room room;
    private String name;
    private int speed;
    private Animation animation;
    private int x;
    private int y;
    private int width, height;
    private int duration;
    private boolean loop = false;

    public VisualEffect(Room room, String name, int speed, int width, int height, int duration){
        this.room = room;
        this.name = name;
        this.speed = speed;
        this.duration = duration;
        this.width = width;
        this.height = height;
        this.animation = new Animation(speed, FileUtils.loadAnimationFrames("/textures/fx/" + name + ".png", width, height));
    }

    public VisualEffect(Room room, Animation animation, int width, int height, int duration, boolean loop){
        this.room = room;
        this.animation = animation;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.loop = loop;
    }

    @Override
    public void tick() {
        animation.tick();
    }

    public void end(){
        room.removeEffect(this);
    }

    public void play(int x, int y){
        this.x = x;
        this.y = y;
        room.addEffect(this);
        animation.reset();

        if(!loop) {
            Scheduler.getInstance().addTimedAction(duration, () -> end());
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.draw(animation.getFrame(), x, y, width, height);
    }

    public void setRoom(Room room){
        this.room = room;
    }
}
