package com.jac.game.entities.interact.npc;

import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.events.CustomEvent;
import com.jac.game.rooms.Room;

public class NPCState {

    protected NPC npc;
    private int spawnX, spawnY;
    private Cutscene interaction;
    private Room room;

    public NPCState(NPC npc, int spawnX, int spawnY, Cutscene interaction){
        this.npc = npc;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.room = interaction.getRoom();
        this.interaction = interaction;
    }

    public NPCState withNPC(NPC npc){
        this.npc = npc;
        System.out.println(npc);
        return this;
    }

    public void init(){
        npc.setRoom(room);
        npc.spawn(spawnX, spawnY);
    }

    public Cutscene getInteraction(){
        return interaction;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public Room getRoom() {
        return room;
    }
}
