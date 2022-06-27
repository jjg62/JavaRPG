package com.jac.game.abilities;

import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.IAction;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.utils.DirectionUtils;

public class AbilityDash extends Ability{

    private boolean isDashing;
    private int dashSpeed;
    private int dashDuration;
    private Vector dashVector;
    private Vector fixedVector;
    private boolean channeled;


    public AbilityDash(int key, int cooldown, Mob owner, int activationDelay, int dashSpeed, int dashDuration) {
        super("Dash", key, cooldown, owner, activationDelay);
        this.dashSpeed = dashSpeed;
        this.dashDuration = dashDuration;
    }

    public AbilityDash withChanneled(){
        channeled = true;
        return this;
    }

    public AbilityDash withFixedVector(Vector fixedVector){
        this.fixedVector = fixedVector;
        return this;
    }

    @Override
    public void play() {
        getDashVector();
        isDashing = true;
        Scheduler.getInstance().addTimedAction(dashDuration, () -> isDashing = false);
    }

    @Override
    public void cancel(boolean force) {
        super.cancel(force);
        isDashing = false;
    }

    private void getDashVector(){
        if(fixedVector != null){
            dashVector = fixedVector.scale(dashSpeed);
        }else {
            dashVector = DirectionUtils.standardiseVector(new Vector(directionVector.x * dashSpeed, directionVector.y * dashSpeed));
        }
    }

    @Override
    public void tick(){
        super.tick();
        if(isDashing){
            owner.changeSpeed(dashVector.x, dashVector.y);
        }
    }

    @Override
    public boolean shouldOwnerChannel(){
        return isDashing;
    }
}
