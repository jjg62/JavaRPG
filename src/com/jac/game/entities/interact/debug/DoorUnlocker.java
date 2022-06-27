package com.jac.game.entities.interact.debug;

import com.jac.game.cutscene.CutsceneList;
import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.Door;
import com.jac.game.entities.interact.Interactable;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.TileTextures;

public class DoorUnlocker extends Interactable {

    private Door door;

    public DoorUnlocker(Room room, Door door) {
        super(room, 32, 32);
        this.door = door;
    }

    @Override
    public void interaction(InteractingManager manager) {
        CutsceneList.playDoorUnlockCutscene(door);
        manager.finishInteracting();
    }

    @Override
    public void render(GameGraphics graphics) {
        if(targeted){
            graphics.draw(TileTextures.floor, x, y, 32, 32);
        }else{
            graphics.draw(TileTextures.wall, x, y, 32, 32);
        }
    }
}
