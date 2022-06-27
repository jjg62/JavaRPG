package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.entities.structs.IAction;

public class CustomEvent extends CutsceneEvent {

    private IAction action;

    public CustomEvent(IAction action){
        this.action = action;
    }

    @Override
    protected void onStart() {
        action.run();
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return true;
    }
}
