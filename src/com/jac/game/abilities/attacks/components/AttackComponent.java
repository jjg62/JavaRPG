package com.jac.game.abilities.attacks.components;

import com.jac.game.abilities.Vector;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.fx.VisualEffect;
import com.jac.game.textures.Animation;

import java.awt.*;
import java.util.ArrayList;

public class AttackComponent implements Ticking {

    protected int x, y, width, height;
    protected double angle;
    protected Vector displacement;
    protected Mob owner;

    protected int lifetime;
    protected int duration;
    protected int activationDelay;
    protected boolean active;
    private boolean over = false;

    //Channel
    protected boolean channeled = false;
    protected int endLag = 0;
    private boolean shouldOwnerChannel;

    //Optional
    protected Animation animation;
    protected Animation chargeUpAnimation;
    protected Animation activeAnimation;
    protected ArrayList<VisualEffect> vfx;
    protected boolean renderOnTop = false;

    public AttackComponent(Mob owner, int duration, Vector displacement, int activationDelay, int width, int height){
        this.owner = owner;
        this.duration = duration;
        this.displacement = displacement;
        lifetime = 0;
        this.activationDelay = activationDelay;
        this.width = width;
        this.height = height;
        this.vfx = new ArrayList<>();
    }

    public AttackComponent withChanneled(boolean channeled, int endLag){
        this.channeled = channeled;
        this.endLag = endLag;
        return this;
    }

    public AttackComponent withActiveAnimation(Animation animation){
        this.activeAnimation = animation;
        return this;
    }

    public AttackComponent withChargeUpAnimation(Animation animation){
        this.chargeUpAnimation = animation;
        this.animation = chargeUpAnimation;
        return this;
    }

    public AttackComponent withFX(VisualEffect vfx){
        this.vfx.add(vfx);
        return this;
    }

    public AttackComponent withFX(ArrayList<VisualEffect> vfx){
        for(VisualEffect fx : vfx){
            this.vfx.add(fx);
        }
        return this;
    }

    public AttackComponent withOnTop(boolean onTop){
        this.renderOnTop = onTop;
        return this;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public AttackComponent createAt(int x, int y, double angle){
        AttackComponent created = copy();
        initInfo(created, x, y, angle);
        owner.getRoom().addComponent(created, renderOnTop);
        return created;
    }

    private void setChanneling(){
        shouldOwnerChannel = true;
        Scheduler.getInstance().addTimedAction(activationDelay + endLag, ()->shouldOwnerChannel = false);
    }

    protected void initInfo(AttackComponent created, int x, int y, double angle){
        Rectangle hurtbox = owner.getHurtbox();
        int xStart = hurtbox.x + hurtbox.width/2;
        int yStart = hurtbox.y + hurtbox.height/2;

        Vector displacementOfCenter = new Vector(displacement.x + width/2, displacement.y + height/2);
        Vector angledDisplacement =  displacementOfCenter.scale(1); //copy of
        angledDisplacement.rotate(angle);
        angledDisplacement.x -= width/2;
        angledDisplacement.y -= height/2;
        created.setX(xStart + angledDisplacement.x);
        created.setY(yStart + angledDisplacement.y);
        created.setAngle(angle);

        if(activeAnimation != null) activeAnimation.reset();
        if(chargeUpAnimation != null) chargeUpAnimation.reset();

        if(channeled) created.setChanneling();

        Scheduler.getInstance().addTimedAction(activationDelay, ()->created.activate());
    }

    public boolean attemptCancel(boolean force){
        if((channeled && !active) || force){
            onDeath();
            shouldOwnerChannel = false;
            return true;
        }
        return false;
    }

    public void onDeath(){
        owner.getRoom().removeComponent(this, renderOnTop);
        over = true;
    }

    protected AttackComponent copy(){
        return new AttackComponent(owner, duration, displacement, activationDelay, width, height)
                .withActiveAnimation(activeAnimation)
                .withChargeUpAnimation(chargeUpAnimation)
                .withFX(vfx)
                .withChanneled(channeled, endLag)
                .withOnTop(renderOnTop);
    }


    @Override
    public void tick() {
        if(animation != null) animation.tick();

        if(channeled){
            if(!shouldOwnerChannel && !active){
                onDeath();
            }
        }
    }


    @Override
    public void render(GameGraphics graphics) {
        if(animation != null) graphics.drawAtAngle(animation.getFrame(), x, y, width, height, angle, width/2, height/2);

        //debug
        //graphics.drawRectangle(x, y, width, height);
    }


    protected void activate(){
        if(over) return;
        active = true;
        animation = activeAnimation;
        for(VisualEffect fx : vfx){
            fx.play(x, y);
        }
        Scheduler.getInstance().addTimedAction(duration, ()->onDeath());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Mob getOwner() {
        return owner;
    }

    public boolean shouldOwnerChannel(){
        return shouldOwnerChannel;
    }
}
