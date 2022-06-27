package com.jac.game.abilities.attacks.components.projectiles.missiles;

import com.jac.game.abilities.ProjectileBehaviour;
import com.jac.game.abilities.Vector;

public class MissileBehaviour extends ProjectileBehaviour {

    private float realSpeed = 1f;
    private float acceleration = 0.1f;


    @Override
    public Vector moveCommand() {
        realSpeed += acceleration;
        int speed = (int)realSpeed;
        return new Vector((int)(speed*Math.cos(projectile.getAngle())), (int)(speed*Math.sin(projectile.getAngle())));
    }

    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }

    public void setSpeed(float realSpeed){
        this.realSpeed = realSpeed;
    }

    public float getSpeed() {
        return realSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    @Override
    public ProjectileBehaviour copy() {
        return null;
    }
}
