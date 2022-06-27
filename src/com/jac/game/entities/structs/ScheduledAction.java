package com.jac.game.entities.structs;

public class ScheduledAction {

    private long scheduledTime;
    private IAction action;

    public long getScheduledTime() {
        return scheduledTime;
    }

    public IAction getAction() {
        return action;
    }

    public ScheduledAction(long scheduledTime, IAction action){
        this.scheduledTime = scheduledTime;
        this.action = action;
    }
}
