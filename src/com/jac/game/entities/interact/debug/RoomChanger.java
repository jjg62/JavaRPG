package com.jac.game.entities.interact.debug;

import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.Interactable;
import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.rooms.RoomList;
import com.jac.game.textures.TileTextures;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class RoomChanger extends Interactable {

    protected Room roomTarget;
    protected int spawnX;
    protected int spawnY;
    private BufferedImage image;

    private static int timeWhenDoorLastUsed;

    //temp
    private boolean showSprite;

    public RoomChanger(Room room, Room roomTarget, int spawnX, int spawnY, boolean showSprite) {
        super(room, 0, 0);
        this.roomTarget = roomTarget;
        this.spawnX = spawnX;
        this.spawnY = spawnY;

        //temp
        this.showSprite = showSprite;
        image = FileUtils.loadImage("/textures/entities/interacts/door.png");
    }

    @Override
    public void interaction(InteractingManager manager) {
        Camera.fade(true);

        Scheduler.getInstance().addTimedAction(30, ()->{
            changeRoom(roomTarget, spawnX, spawnY);

            if(timeWhenDoorLastUsed != GameInfo.getInstance().getTime()){
                timeWhenDoorLastUsed = GameInfo.getInstance().getTime();
                QuestlineList.getInstance().updateNPCs();
                System.out.println("Update NPCs to time " + GameInfo.getInstance().getTime());
            }

            Camera.fade(false);
        });
    }

    @Override
    public void render(GameGraphics graphics) {
        if(showSprite) graphics.draw(image, x, y, 96, 96);
    }

    private void changeRoom(Room room, int x, int y){
        room.getInteractingManager().finishInteracting();
        room.movePlayer(x, y);
        this.room.changeRoom(room);
    }
}
