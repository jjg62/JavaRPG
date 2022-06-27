package com.jac.game.entities;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.abilities.Vector;
import com.jac.game.audio.MobSounds;
import com.jac.game.audio.Sound;
import com.jac.game.display.GameGraphics;
import com.jac.game.encounters.Encounter;
import com.jac.game.entities.enemies.assistant.Assistant;
import com.jac.game.entities.enemies.ninjas.TigerNinja;
import com.jac.game.entities.statics.DeathAnimation;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.fx.VisualEffect;
import com.jac.game.textures.MobAnimations;
import com.jac.game.entities.combat.Combat;
import com.jac.game.entities.structs.TickList;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import javax.sound.sampled.Clip;
import java.awt.*;

public abstract class Mob extends MovingCollider{

    protected int maxHealth;
    protected int health;

    //Invulnerability
    protected boolean invulnerable = false;
    private int invTimer; //How long I've been invulnerable for
    protected int iFrames; //How long I will be invulnerable for
    private Hitbox lastHitBy;

    protected boolean isSpriteVisible = true;

    //Countering
    private boolean countering = false;
    private boolean countered = false;

    //Hitstun
    protected boolean hitstun = false;
    private Vector knockbackVector;

    private static Animation cancelAnimation = new Animation(false, 3, FileUtils.loadAnimationFrames("/textures/fx/cancel.png", 64, 64));
    private VisualEffect cancelFeedback;
    private static Sound cancelSound = new Sound("cancel.wav");

    //Attacks
    protected TickList<Ability> abilities;
    private boolean channeling = false;
    private String channelAnimation = "attack";
    private String channelDirection = "s";
    private int channelAnimationSpeed = 6;

    //Sounds
    private MobSounds sounds;

    //Encounter
    private Encounter encounter;
    private boolean showDeathAnimation = false;


    public Mob(Room room, int width, int height, int health, String name) {
        super(room, width, height, name);
        this.health = health;
        this.maxHealth = health;
        this.abilities = new TickList<>();
        initAttacks();
        iFrames = Combat.defaultIFrames;

        //Sounds
        this.sounds = new MobSounds(this);

        //Effects
        cancelFeedback = new VisualEffect(room, cancelAnimation.clone(), 96, 96, 24, false);
    }

    public Mob inEncounter(Encounter encounter){
        this.encounter = encounter;
        return this;
    }

    public Mob withDeathAnimation(){
        this.showDeathAnimation = true;
        return this;
    }

    public void hit(Hitbox hb){
        if(!countering) {
            if (!invulnerable) {
                lastHitBy = hb;
                invulnerable = true;
                damage(hb.getDamage());

                knockback(hb.getKnockbackScalar());
                if(hb.getKnockbackScalar() > 12) {
                    //Interrupt channel
                    if (channeling) {
                        cancelAbilities(false);
                    }
                }

                //Play Sound
                sounds.playAVariant("hit");

                //Update healthbar
                if(encounter != null) encounter.updateTotalHealth();
            }
        }else{
            countered = true;
        }
    }

    protected void damage(int amount){
        health -= amount;
    }

    private void checkDeath(){
        if(health <= 0){
            health = 0;
            die();
        }
    }

    private void cancelAbilities(boolean force){
        for(Ability a : abilities){
            a.cancel(force);
        }
         if(!(this instanceof Player) && !force){
             cancelFeedback.play(x, y-48);
             cancelSound.play();
         }
    }

    public void knockback(int knockbackScalar){
        hitstun = true;
        Scheduler.getInstance().addTimedAction(20, () -> hitstun = false);

        int xDistance = (x + getWidth()/2) -  (lastHitBy.getX() + lastHitBy.getWidth()/2);
        int yDistance = (y + getHeight()/2) -(lastHitBy.getY() + lastHitBy.getHeight()/2);

        //If the mob is directly on top of the hitbox, dont cause divison by 0
        if(xDistance == 0 && yDistance == 0){
            yDistance = 1;
        }
        double absDistance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);

        int knockbackX = (int)(xDistance * knockbackScalar / absDistance);
        int knockbackY = (int)(yDistance * knockbackScalar / absDistance);

        knockbackVector = new Vector(knockbackX, knockbackY);
    }

    private void decayKnockback(){
        changeSpeed(knockbackVector.x, knockbackVector.y);
        knockbackVector.x = (int)(knockbackVector.x * 0.8);
        knockbackVector.y = (int)(knockbackVector.y * 0.8);
    }

    private void attemptVulnerablise(){
        if(invTimer >= iFrames){
            vulnerablise();
        }else{
            invTimer++;
            if(invTimer % 8 == 0){
                isSpriteVisible = !isSpriteVisible; //Flicker sprite for invincibility
            }
        }
    }

    public void vulnerablise(){
        invulnerable = false;
        invTimer = 0;
        isSpriteVisible = true;
    }

    private void checkForAttacks(){
        boolean attackCausingChannel = false;
        boolean attackCasted = false;

        if(room.isPeaceful()) return;
        for(Ability a : abilities){
            if(!attackCasted && getAbilityFromBehaviour(a) && !frozen() && a.isEnabled()){
                attackCasted |= a.cast();
            }
            attackCausingChannel |= a.shouldOwnerChannel();
        }
        channeling = attackCausingChannel;
        weight = channeling ? 10 : 1;
    }

    private boolean getAbilityFromBehaviour(Ability a){
        if(behaviour != null){
            return behaviour.abilityCommand(a);
        }else{
            return false;
        }
    }

    protected void changeAnimation(){
        if(channeling){
            currentAnimation = animations.get(channelAnimation, channelDirection);
            currentAnimation.setSpeed(channelAnimationSpeed);
            currentAnimation.tick();
        }else{
            super.changeAnimation();
        }
    }

    @Override
    public void tick(){
        abilities.tick();
        checkForAttacks();
        if(invulnerable) attemptVulnerablise();
        if(hitstun) decayKnockback();
        checkDeath();
        super.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        if(isSpriteVisible){
            renderShadow(graphics);
            graphics.draw(currentAnimation.getFrame(), x, y, width, height);
            //renderHealth(graphics);
        }
        abilities.render(graphics);
    }

    protected void renderHealth(GameGraphics graphics){
        if(invulnerable){
            graphics.setGColour(Color.black);
            graphics.fillRectangle(x + 16, y - 16, 64, 16);

            double healthFraction = health / (maxHealth / 1.0);
            graphics.setGColour(Color.red);
            graphics.fillRectangle(x + 16, y - 16, (int)(64*healthFraction), 16);

            graphics.setGColour(Color.black);
            graphics.drawRectangle(x + 16, y - 16, 64, 16);
        }
    }

    protected void die(){
        cancelAbilities(true);
        channeling = false;
        room.removeEntity(this);
        if(encounter != null) encounter.progressPhase(x, y);

        if(showDeathAnimation) new DeathAnimation(room, width, height, name).spawn(x, y);
    }

    protected abstract void initAttacks();

    @Override
    protected boolean frozen(){
        return super.frozen() || hitstun || channeling;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public TickList<Ability> getAbilities() {
        return abilities;
    }

    public void setChanneling(boolean channeling){
        this.channeling = channeling;
    }

    public boolean isChanneling() {
        return channeling;
    }

    public void setCountering(boolean countering) {
        this.countering = countering;
    }

    public boolean isCountered() {
        return countered;
    }

    public void setCountered(boolean countered) {
        this.countered = countered;
    }

    public void setChannelAnimation(String type, String direction, int speed){
        channelAnimation = type;
        channelDirection = direction;
        channelAnimationSpeed = speed;

        animations.get(channelAnimation, channelDirection).reset();
    }

    public void playAbilitySound(Ability a){
        sounds.playAbilitySound(a);
    }

    public void cancelAbilitySound(Ability a){
        Clip abilitySound = sounds.get("ability", a.getName());
        if(abilitySound != null) abilitySound.stop();
    }
}
