package com.jac.game.entities.interact;

import com.jac.game.audio.Sound;
import com.jac.game.cutscene.events.PanCameraEvent;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.debug.RoomChanger;
import com.jac.game.entities.interact.npc.Questline;
import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.DirectionUtils;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Door extends RoomChanger {

    private static HashMap<String, Animation> animations = new HashMap<>(){{
        String[] types = {"opening", "closing", "locked"};
        String[] directions = {"N", "E", "S", "W"};
        for(String type : types){
            for(String direction : directions){
                int speed = !type.equals("opening") ? 12 : 3;
                boolean loop = type.equals("locked");
                int height = direction.equals("N") ? 192 : 96;
                String path = "/textures/entities/interacts/door/" + type + direction + ".png";

                put(type + direction, new Animation(loop, speed, FileUtils.loadAnimationFrames(path, 96, height)));
            }
        }
    }};

    private static Sound doorOpenSound = new Sound("entities/interacts/door_open.wav");
    private static Sound doorCloseSound = new Sound("entities/interacts/door_close.wav");

    private Animation lockedDoorAnimation;
    private Animation doorOpeningAnimation;
    private Animation doorClosingAnimation;

    private boolean locked;
    private Animation animation;
    private String[] lockedText = {"The door is locked."};

    //For the opening animation
    private boolean opened = false;
    private boolean closed = true;

    public Door(Room room, Room roomTarget, int spawnX, int spawnY, char dir, boolean locked) {
        super(room, roomTarget, spawnX, spawnY, dir == 'N');
        this.locked = locked;
        this.width = 96;
        this.height = dir == 'N' ? 192 : 96;

        //Setting the animations
        lockedDoorAnimation = animations.get("locked" + dir).clone();
        doorOpeningAnimation = animations.get("opening" + dir).clone();
        doorClosingAnimation = animations.get("closing" + dir).clone();

        animation = lockedDoorAnimation;
    }

    public Door withLockedText(String... text){
        this.lockedText = text;
        return this;
    }

    public void unlock(){
        locked = false;
        //play sound and change animation
    }

    @Override
    public void render(GameGraphics graphics){
        graphics.draw(animation.getFrame(), x, y, width, height);
    }

    @Override
    public void tick() {
        super.tick();
        animation.tick();
        if(!locked) {
            if (!opened && targeted) {
                opened = true;
                closed = false;
                doorOpeningAnimation.reset();
                animation = doorOpeningAnimation.clone();
                doorOpenSound.play();
            }
            if (!closed && !targeted) {
                closed = true;
                opened = false;
                doorClosingAnimation.reset();
                animation = doorClosingAnimation.clone();
                doorCloseSound.play();
            }
            lockedDoorAnimation.reset();
        }
    }

    @Override
    public void interaction(InteractingManager manager){
        if(locked){
            manager.showTextboxSequence("Info", lockedText);
        }else{
            super.interaction(manager);
        }
    }

    @Override
    public void setTargeted(boolean targeted){
        super.setTargeted(targeted);
    }

}
