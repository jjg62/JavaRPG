package com.jac.game.entities;



import com.jac.game.abilities.*;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.MomentumAttack;
import com.jac.game.abilities.attacks.components.ChannelHitbox;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.audio.LoopingSound;
import com.jac.game.audio.Music;
import com.jac.game.audio.Sound;
import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.behaviour.UseKeyboard;
import com.jac.game.fx.VisualEffect;
import com.jac.game.items.Skill;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.textures.MobAnimations;
import com.jac.game.utils.FileUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.HashMap;

public class Player extends Mob {

    private int moveSpeed = 4;

    //Momentum
    private int momentum;
    private int momentumLevel;
    private HashMap<Integer, Ability> attackLevels;
    private static HashMap<Integer, Sound> momentumSounds;
    private static Animation momentumUpAnimation = new Animation(4, FileUtils.loadAnimationFrames("/textures/fx/momentum_build.png", 64, 64));
    private VisualEffect momentumUp;

    private MobAnimations chargedAnimations;
    private MobAnimations normalAnimations;

    private Ability attack;
    private Ability dash;
    private Ability attackLvl1;
    private Ability attackLvl2;
    private Ability attackLvl3;
    private Ability dashForAttack;
    private Ability dashAttack;

    private Ability consume;
    private Ability skill;

    private Ability nullAbility;

    public Player(Room room) {
        super(room, 96, 96, 5, "player");
        setBoundingBox(12, 48, 72, 48);
        setBehaviour(new UseKeyboard(this, moveSpeed));
        weight = 3;

        this.normalAnimations = animations;
        this.chargedAnimations = new MobAnimations(this, "player", 64, 64);

        momentumUp = new VisualEffect(room, momentumUpAnimation.clone(), 96, 96, 12, false);

        momentumSounds = new HashMap<>();
        for (int i = 0; i < 3; i++){
            momentumSounds.put(i, new Sound("entities/player/momentum/player_momentum_" + i + ".wav"));
        }

    }

    /** Define all the player's attacks and set its normal and special.
     */
    @Override
    protected void initAttacks() {

        attack = new MomentumAttack("attack", KeyEvent.VK_LEFT, 96, 60, this)
                .withComponent(0, new Hitbox(this, 8, new Vector(-48, -48), 96, 96, 0, 0, 96, 96, 8, 8)
                        .withChanneled(true, 20))
                .withAnimation("attack", 2, true);

        attackLvl1 = new MomentumAttack("attack", KeyEvent.VK_LEFT, 96, 60, this)
                .withComponent(0, new Hitbox(this, 8, new Vector(-48, -48), 96, 96, 0, 0, 96, 96, 8, 8).withDamage(1)
                        .withChanneled(true, 20))
                .withAnimation("attack", 2, true);

        attackLvl2 = new MomentumAttack("attack", KeyEvent.VK_LEFT, 96, 60, this)
                .withComponent(0, new Hitbox(this, 8, new Vector(-48, -48), 96, 96, 0, 0, 96, 96, 8, 24).withDamage(3)
                        .withChanneled(true, 20))
                .withAnimation("attack", 2, true);



        nullAbility = new Ability("null", 0, 1, this, 0) {
            @Override
            public void play() {

            }

            @Override
            public void cancel(boolean force) {

            }
        };

        skill = nullAbility;

        dash = new AbilityDash(KeyEvent.VK_RIGHT, 60, this, 0, 12, 6);

        consume = new AbilityConsume(KeyEvent.VK_DOWN, this);

        abilities.add(dash);
        abilities.add(consume);


        //Momentum scaled
        attackLevels = new HashMap<>();
        attackLevels.put(0, attack);
        attackLevels.put(1, attackLvl1);
        attackLevels.put(2, attackLvl2);

        abilities.add(attack);
        abilities.add(attackLvl1);
        abilities.add(attackLvl2);

        attackLvl1.setEnabled(false);
        attackLvl2.setEnabled(false);
    }

    /** Player should become frozen if currently talking to a Talking.
     * @return If the player should be locked from moving and attacking.
     */
    @Override
    public boolean frozen(){
        return room.getInteractingManager().isCurrentlyTalking() || super.frozen() || room.isInInventory();
    }


    //pLAYER TAKING DAMAGE HAS ADDITIONAL EFFECTS.
    @Override
    protected void damage(int damage){
        super.damage(damage);
        Camera.shake(8, 10, 20, true);
        momentum = 0;
        updateMomentum();
    }

    public void setMomentum(int momentum){
        this.momentum = momentum;
        updateMomentum();
    }

    private void updateMomentum(){
        momentum = Math.abs(xSpeed) + Math.abs(ySpeed) > 0 ? momentum + 2 : momentum - 1;
        momentum = Math.max(0, Math.min(momentum, 600));
        changeMomentumLevel(momentumToLevel(momentum));
    }

    private int momentumToLevel(int momentum){
        if(momentum < 0.5 * 600){
            return 0;
        }else if (momentum < 0.9 * 600){
            return 1;
        }else{
            return 2;
        }
    }

    public void changeMomentumLevel(int level){
        if(this.momentumLevel != level) {

            if(momentumLevel < level){
                momentumUp.play(x, y);
                momentumSounds.get(level).play();
            }


            momentumLevel = level;
            attack.setEnabled(false);
            attack = attackLevels.get(level);
            attack.setEnabled(true);

            moveSpeed = 4 + (level+1)/2;
            setBehaviour(new UseKeyboard(this, moveSpeed));
            if(momentumLevel == 2){
                this.animations = chargedAnimations;
            }else{
                this.animations = normalAnimations;
            }
        }
    }

    @Override
    protected void usingMoveValues() {
        super.usingMoveValues();
        if(!room.isPeaceful()) updateMomentum();
    }

    public int getMomentum(){
        return momentum;
    }

    public Ability getConsume() {
        return consume;
    }

    public void refreshConsume(){
        ((AbilityConsume)consume).refreshCooldown();
    }

    public Ability getSkill(){
        return skill;
    }

    public void updateSkill(){
        abilities.remove(this.skill);
        Skill equippedSkill = GameInfo.getInstance().getInventory().getEquippedSkill();
        if(equippedSkill != null) {
            this.skill = GameInfo.getInstance().getInventory().getEquippedSkill().ability(this);
            abilities.add(this.skill);
        }
    }


    @Override
    protected void renderHealth(GameGraphics graphics){
        //brugh
    }

    @Override
    public void setChannelAnimation(String type, String direction, int speed){
        super.setChannelAnimation(type, direction, speed);
        chargedAnimations.get(type, direction).reset();
        normalAnimations.get(type, direction).reset();
    }

}
