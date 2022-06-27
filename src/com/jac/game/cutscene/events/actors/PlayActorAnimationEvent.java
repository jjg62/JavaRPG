package com.jac.game.cutscene.events.actors;


import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.entities.structs.Scheduler;

public class PlayActorAnimationEvent extends CutsceneEvent {

    private int actorID;
    private String name;
    private int speed, duration;
    private boolean finished;

    private boolean indefinite;
    private boolean resume;

    public PlayActorAnimationEvent(int actorID, String name, int speed, int duration){
        this.actorID = actorID;
        this.name = name;
        this.speed = speed;
        this.duration = duration;
    }

    public PlayActorAnimationEvent(int actorID, String name, int speed){
        indefinite = true;
        this.actorID = actorID;
        this.name = name;
        this.speed = speed;
    }

    public PlayActorAnimationEvent(int actorID){
        resume = true;
    }


    @Override
    protected void onStart() {
        if(resume){
            finished = true;
            cutscene.getActor(actorID).stopCustomAnimation();
        }else if(indefinite){
            finished = true;
            cutscene.getActor(actorID).playCustomAnimation(name, speed);
        }else{
            finished = false;
            cutscene.getActor(actorID).playCustomAnimation(name, speed);
            Scheduler.getInstance().addTimedAction(duration, ()->{
                cutscene.getActor(actorID).stopCustomAnimation();
                finished = true;
            });
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return finished;
    }
}
