package com.jac.game.cutscene.events.actors;

import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.entities.CutsceneActor;

public class MoveActorEvent extends CutsceneEvent {

    private int actorID;
    private CutsceneActor actor;
    private int x, y;
    private int xTarget, yTarget;
    private int speed;
    private boolean reached = false;

    public MoveActorEvent(int actorID, int x, int y, int speed){
        this.actorID = actorID;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    protected void onStart() {
        reached = false;
        actor = cutscene.getActor(actorID);
        xTarget = actor.getX() + x;
        yTarget = actor.getY() + y;
    }

    @Override
    public void tick() {
        int dx = xTarget - actor.getX();
        int dy = yTarget - actor.getY();
        int xSpeed = 0, ySpeed = 0;

        reached = true;

        if(Math.abs(dx) > speed){
            xSpeed = Integer.signum(dx) * speed;
            reached = false;
        }else{
            actor.setX(xTarget);
        }

        if(Math.abs(dy) > speed){
            ySpeed = Integer.signum(dy) * speed;
            reached = false;
        }else{
            actor.setY(yTarget);
        }

        actor.changeSpeed(xSpeed, ySpeed);
        actor.updateDirectionVector();
    }

    @Override
    public boolean endCondition() {
        return reached;
    }
}
