package com.jac.game.entities;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.IAction;
import com.jac.game.rooms.Room;

public class LocationTrigger extends MovingCollider{

    private IAction action;

    public LocationTrigger(Room room, int width, int height, IAction action) {
        super(room, width, height, "");
        this.action = action;
    }

    @Override
    public void tick(){
        if(collisionWithEntity(0, 0).contains(room.getPlayer())){
            room.removeEntity(this);
            action.run();
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.drawRectangle(x, y, width, height);
    }
}
