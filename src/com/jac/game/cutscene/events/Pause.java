package com.jac.game.cutscene.events;

import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.entities.structs.Scheduler;

public class Pause extends CutsceneEvent {

    private int duration;
    private boolean finished;

    public Pause(int duration) {
        this.duration = duration;
    }

    @Override
    protected void onStart(){
        finished = false;
        Scheduler.getInstance().addTimedAction(duration, ()->finished = true);
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return finished;
    }
}
