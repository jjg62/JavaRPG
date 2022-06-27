package com.jac.game.entities.structs;

import com.jac.game.display.GameGraphics;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scheduler implements Ticking {

    //Singleton
    private static Scheduler instance = new Scheduler();

    private long currentTick = 0;
    public IterableChangingList<ScheduledAction> scheduledActions;

    public Scheduler(){
        scheduledActions = new IterableChangingList<>();
    }

    @Override
    public void tick(){
        currentTick++;
        completeScheduledActions();
        scheduledActions.updateContents();
    }

    /** Check if any actions should be ran, and if so run them and delete them.
     */
    private void completeScheduledActions(){
        for(ScheduledAction a : scheduledActions){
            if(a.getScheduledTime() <= currentTick){
                a.getAction().run();
                scheduledActions.remove(a);
            }
        }
    }

    public void render(GameGraphics graphics){

    }

    public void addTimedAction(int delay, IAction action){
        if(delay == 0){
            action.run();
        }else{
            scheduledActions.add(new ScheduledAction(currentTick + delay, action));
        }
    }


    public static Scheduler getInstance() {
        return instance;
    }
}
