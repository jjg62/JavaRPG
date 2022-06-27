package com.jac.game.abilities.attacks.components.projectiles.missiles;

import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.projectiles.Projectile;
import com.jac.game.entities.Mob;
import com.jac.game.entities.Player;
import com.jac.game.entities.structs.IAction;

import java.util.HashMap;
import java.util.Random;

public class Missile extends Projectile {

    public static final int TOWARDS_PLAYER = 0;
    public static final int RANDOM = 1;
    public static final int CIRCLE = 2;
    public static final int STRAIGHT = 3;

    private double startingAngle;
    protected double turnRate = 0.035;
    private Player player;

    private int behaviour;
    private HashMap<Integer, IAction> behaviours;

    public Missile(int behaviour, Mob owner, int duration, Vector displacement, int width, int height, int hitboxXOffset, int hitboxYOffset, int hitboxWidth, int hitboxHeight, int activationDelay, int knockbackScalar) {
        super(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar, new MissileBehaviour());
        player = owner.getRoom().getPlayer();
        behaviours = new HashMap<>();
        behaviours.put(0, ()->turnTowardsPlayer());
        behaviours.put(1, ()->turnRandomly());
        behaviours.put(2, ()->circle());
        behaviours.put(3, ()->{});
        this.behaviour = behaviour;
        this.renderOnTop = true;
    }

    public Missile withStartingAngle(double angle){
        this.startingAngle = angle;
        return this;
    }

    public Missile withTurnRate(double turnRate){
        this.turnRate = turnRate;
        return this;
    }

    public Missile withAcceleration(float acceleration){
        ((MissileBehaviour)this.guide).setAcceleration(acceleration);
        return this;
    }

    public Missile withInitialSpeed(float speed){
        ((MissileBehaviour)this.guide).setSpeed(speed);
        return this;
    }

    @Override
    public void tick(){
        if(active) turn();
        super.tick();
    }

    @Override
    protected void initInfo(AttackComponent created, int x, int y, double angle){
        super.initInfo(created, x, y, angle);
        created.setAngle(created.getAngle() + startingAngle);
    }

    protected void turn(){
        behaviours.get(behaviour).run();
    }

    private void turnTowardsPlayer(){
        int dy = player.getY() + player.getHeight()/2 - (y + height/2) ;
        int dx = player.getX() + player.getWidth()/2 - (x + width/2);
        double bestAngle = Math.atan2(dy, dx);

        double da = standardiseAngle(bestAngle) - standardiseAngle(angle);

        da = Math.abs(da) > Math.PI ? - da : da;

        angle += Math.signum(da) * turnRate;
        angle %= 2*Math.PI;
    }

    private void turnRandomly(){
        Random rand = new Random();
        angle += (rand.nextInt(3) - 1)*turnRate;
        angle %= 2*Math.PI;
    }

    private void circle(){
        angle += turnRate;
        angle %= 2*Math.PI;
    }

    private double standardiseAngle(double angle){
        if(angle < 0){
            return 2 * Math.PI + angle;
        }else{
            return angle;
        }
    }


    @Override
    protected AttackComponent copy(){
        return new Missile(behaviour, owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar)
                .withInitialSpeed(((MissileBehaviour)guide).getSpeed())
                .withAcceleration(((MissileBehaviour)guide).getAcceleration())
                .withTurnRate(turnRate)
                .withStartingAngle(startingAngle)
                .withDamage(damage)
                .withActiveAnimation(activeAnimation)
                .withChargeUpAnimation(chargeUpAnimation)
                .withFX(vfx)
                .withChanneled(channeled, endLag);
    }

}
