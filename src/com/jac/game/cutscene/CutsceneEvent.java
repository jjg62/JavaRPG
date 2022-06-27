package com.jac.game.cutscene;

public abstract class CutsceneEvent {

    protected Cutscene cutscene;

    public CutsceneEvent inCutscene(Cutscene cutscene){
        this.cutscene = cutscene;
        return this;
    }

    public void play(){
        cutscene.setCurrentEvent(this);
        onStart();
    }

    protected abstract void onStart();

    public abstract void tick();

    public abstract boolean endCondition();

}
