package com.jac.game.encounters;

import com.jac.game.entities.enemies.bh.BHSequence;
import com.jac.game.entities.structs.Scheduler;

public class BHPhase extends Phase{

    private BHSequence sequence;
    private int duration;
    private int x, y, speed; //Moving to the right place

    public BHPhase(int duration, BHSequence sequence, int x, int y, int speed){
        super(1);
        this.sequence = sequence;
        this.duration = duration;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public Phase inEncounter(Encounter encounter){
        this.sequence = (BHSequence) this.sequence.inEncounter(encounter);
        this.encounter = encounter;
        return this;
    }

    @Override
    public void start(int x, int y) {
        sequence.spawn(x, y);
        sequence.startMovingTowards(this.x, this.y, speed);
        Scheduler.getInstance().addTimedAction(duration, ()->sequence.die());
    }
}
