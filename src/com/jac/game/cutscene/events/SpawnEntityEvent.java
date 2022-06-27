package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.entities.CutsceneActor;
import com.jac.game.entities.Entity;

public class SpawnEntityEvent extends CutsceneEvent {

    private Entity entity;
    private String name;
    private int width, height;
    private int x, y;

    public SpawnEntityEvent(String name, int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public SpawnEntityEvent(Entity entity, int x, int y){
        this.x = x;
        this.y = y;
        this.entity = entity;
    }

    @Override
    protected void onStart() {
        if(entity != null){
            entity.spawn(x, y);
        }else{
            CutsceneActor actor = new CutsceneActor(cutscene.getRoom(), width, height, name);
            cutscene.addActor(actor);
            actor.spawn(x, y);
        }

    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return true;
    }
}
