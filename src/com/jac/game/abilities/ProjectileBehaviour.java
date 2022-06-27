package com.jac.game.abilities;

import com.jac.game.abilities.attacks.components.projectiles.Projectile;

public abstract class ProjectileBehaviour {

    protected Projectile projectile;
    protected double angle;

    public void startProjectile(Projectile projectile){
        this.projectile = projectile;
        this.angle = projectile.getAngle();
    }

    public abstract Vector moveCommand();

    public abstract ProjectileBehaviour copy();

}
