package com.jac.game.abilities.attacks.components;

import com.jac.game.abilities.Vector;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Mob;

import java.awt.*;

public class Hitbox extends AttackComponent {

    protected int hitboxXOffset, hitboxYOffset; //Where the hitbox starts
    protected int hitboxWidth, hitboxHeight;
    protected int damage = 1;
    protected int knockbackScalar = 25;
    protected Rectangle hitbox;

    public Hitbox(Mob owner, int duration, Vector displacement, int width, int height, int hitboxXOffset, int hitboxYOffset, int hitboxWidth, int hitboxHeight, int activiationDelay, int knockbackScalar) {
        super(owner, duration, displacement, activiationDelay, width, height);
        this.hitboxXOffset = hitboxXOffset;
        this.hitboxYOffset = hitboxYOffset;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.activationDelay = activiationDelay;
        this.knockbackScalar = knockbackScalar;
    }

    public Hitbox withDamage(int damage){
        this.damage = damage;
        return this;
    }

    @Override
    protected AttackComponent copy(){
        return new Hitbox(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar)
                .withDamage(damage)
                .withActiveAnimation(activeAnimation)
                .withChargeUpAnimation(chargeUpAnimation)
                .withFX(vfx)
                .withChanneled(channeled, endLag)
                .withOnTop(renderOnTop);
    }

    public void initHitbox(double angle){
        Vector angledOffsetFromCenter = new Vector((hitboxXOffset + hitboxWidth/2) - (width/2), (hitboxYOffset + hitboxHeight/2) - (height/2));
        angledOffsetFromCenter.rotate(angle);
        hitbox = new Rectangle(x + width/2 + angledOffsetFromCenter.x - hitboxWidth/2, y + height/ 2 + angledOffsetFromCenter.y - hitboxHeight/2, hitboxWidth, hitboxHeight);
    }

    @Override
    public void tick() {
        super.tick();
        if(active){
            detectHits();
        }
    }



    /*
    @Override
    public void render(GameGraphics graphics){
        super.render(graphics);
        if(active) {
            graphics.setGColour(Color.red);
            graphics.drawRectangle(hitbox.x, hitbox.y, hitboxWidth, hitboxHeight);
            graphics.setGColour(Color.black);
        }
    }
     */




    /** Apply damage to all mobs except player
     */
    private void detectHits(){
        for(Entity e : owner.getRoom().getEntities()){
            if(e instanceof Mob && e != owner){
                Mob target = (Mob)e;
                if(target.getHurtbox().intersects(hitbox)){
                    onHit(target);
                }
            }
        }
    }

    protected void onHit(Mob target){
        target.hit(this);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    protected void activate(){
        initHitbox(angle);
        super.activate();
    }

    public int getKnockbackScalar() {
        return knockbackScalar;
    }
}
