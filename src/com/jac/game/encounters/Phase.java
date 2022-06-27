package com.jac.game.encounters;

public abstract class Phase {

    protected Encounter encounter;
    protected int progressRequired;

    public Phase(int progressRequired){
        this.progressRequired = progressRequired;
    }

    public Phase inEncounter(Encounter encounter){
        this.encounter = encounter;
        return this;
    }

    public int getProgressRequired(){
        return progressRequired;
    }

    public abstract void start(int x, int y);

}
