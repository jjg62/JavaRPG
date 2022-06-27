package com.jac.game.entities.interact;

import com.jac.game.abilities.Vector;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.rooms.RoomList;

import java.util.ArrayList;

public class MysteryDoor extends Door{

    private ArrayList<Integer> combos;
    private ArrayList<Room> targetRooms;
    private ArrayList<Vector> targetCoordinates;

    public MysteryDoor(Room room, Room roomTarget, int spawnX, int spawnY, char dir, boolean locked) {
        super(room, roomTarget, spawnX, spawnY, dir, locked);
        combos = new ArrayList<>();
        targetRooms = new ArrayList<>();
        targetCoordinates = new ArrayList<>();
    }

    public MysteryDoor withCombo(int combo, Room targetRoom, int spawnX, int spawnY){
        combos.add(combo);
        targetRooms.add(targetRoom);
        targetCoordinates.add(new Vector(spawnX, spawnY));
        return this;
    }

    @Override
    public void interaction(InteractingManager manager){
        boolean correct = updateTargetRoom();
        super.interaction(manager);

    }

    private boolean updateTargetRoom(){
        int code = RoomList.comboTorch1.getColour() * 1000 + RoomList.comboTorch2.getColour() * 100 + RoomList.comboTorch3.getColour() * 10 + RoomList.comboTorch4.getColour();
        for(int i = 0; i < combos.size(); i++){
            if(code == combos.get(i)){
                roomTarget = targetRooms.get(i);
                Vector coordinates = targetCoordinates.get(i);
                spawnX = coordinates.x;
                spawnY = coordinates.y;
                return true;
            }
        }
        roomTarget = RoomList.room_l6_mysterydoor;
        spawnX = spawnY = 400;
        return false;
    }

}

