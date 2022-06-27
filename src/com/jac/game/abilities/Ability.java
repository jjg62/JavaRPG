package com.jac.game.abilities;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Mob;
import com.jac.game.entities.enemies.bh.BHSequence;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.utils.DirectionUtils;

public abstract class Ability implements Ticking {

    private boolean enabled = true;
    protected String name;
    protected Mob owner;
    private int cooldownTimer = 0;
    private int cooldown;
    protected boolean isOnCooldown = true;
    protected Vector directionVector;
    private int key;
    private int activationDelay = 0;
    protected String channelAnimation = "channel";
    protected int channelAnimationSpeed = 6;
    private boolean channelAnimationSouthOnly = false;

    public Ability(String name, int key, int cooldown, Mob owner, int activationDelay){
        this.name = name;
        this.key = key;
        this.cooldown = cooldown;
        this.owner = owner;
        this.directionVector = new Vector(0, 0);
        this.activationDelay = activationDelay;
    }

    public Ability(String name, int key, int cooldown, Mob owner){
        this(name, key, cooldown, owner, 0);
    }

    public Ability withAnimation(String type, int speed, boolean southOnly){
        this.channelAnimation = type;
        this.channelAnimationSpeed = speed;
        this.channelAnimationSouthOnly = southOnly;
        return this;
    }

    public Ability withAnimation(String type, int speed){
        this.channelAnimation = type;
        this.channelAnimationSpeed = speed;
        return this;
    }

    public void tick() {
        if(!isOnCooldown) attemptCooldown();
    }

    @Override
    public void render(GameGraphics graphics) {

    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }

    protected void attemptCooldown(){
        cooldownTimer++;
        if(cooldownTimer >= cooldown){
            isOnCooldown = true;
            cooldownTimer = 0;
        }
    }

    public double getCooldownRatio() {
        if(isOnCooldown) return 1.0;
        return cooldownTimer/(cooldown/1.0);
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void updateDirectionVector(){
        if(owner instanceof BHSequence){
            directionVector.x = 1;
            directionVector.y = 0;
        }else{
            directionVector.x = owner.getDirectionVector().x;
            directionVector.y = owner.getDirectionVector().y;
        }
    }

    public boolean cast(){
        if(isOnCooldown && !owner.isChanneling()){
            isOnCooldown = false;
            owner.playAbilitySound(this);
            Scheduler.getInstance().addTimedAction(activationDelay, ()->{
                updateDirectionVector();
                updateChannelAnimation();
                play();
            });
            return true;
        }
        return false;
    }

    public abstract void play();

    public int getActivationDelay() {
        return activationDelay;
    }

    protected void updateChannelAnimation(){
        if (channelAnimationSouthOnly) {

            System.out.println("Channel: " + channelAnimation);

            owner.setChannelAnimation(channelAnimation, "s", channelAnimationSpeed);
        }else{
            owner.setChannelAnimation(channelAnimation, DirectionUtils.vectorToString(directionVector), channelAnimationSpeed);
        }
    }

    public boolean shouldOwnerChannel(){
        return false;
    }

    public void cancel(boolean force){
        owner.cancelAbilitySound(this);
    }
}
