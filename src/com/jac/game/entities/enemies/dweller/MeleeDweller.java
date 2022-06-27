package com.jac.game.entities.enemies.dweller;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityCounter;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.fx.VisualEffect;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.io.File;

public class MeleeDweller extends Mob {

    private Ability stomp;
    private Ability shield;
    private Ability twinMissile;
    private Ability handSlash;

    public MeleeDweller(Room room, int health) {
        super(room, 96, 96, health, "melee_dweller");
        setBehaviour(new FollowAlways(this, room.getPlayer(), 3));
        setBoundingBox(12, 48, 72, 48);
        weight = 1;
    }

    @Override
    protected void initAttacks() {

        Animation goldenMissileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile.png", 32, 32));
        Animation goldenMissileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile_charge.png", 32, 32));

        Animation handCharge = new Animation(false, 3, FileUtils.loadAnimationFrames("/textures/attacks/hand_charge.png", 96, 96));
        Animation hand = new Animation(false, 3, FileUtils.loadAnimationFrames("/textures/attacks/hand.png", 96, 96));

        VisualEffect rocks = new VisualEffect(room, new Animation(false, 4, FileUtils.loadAnimationFrames("/textures/entities/melee_dweller/jump/jump_effect.png", 64, 64)), 96, 96, 36, false);
        Animation crack = new Animation(false, 4, FileUtils.loadAnimationFrames("/textures/entities/melee_dweller/jump/crack.png", 64, 64));

        Ability stompAttack = new Attack("stompattack", 0, 192, 120, this)
                .withComponent(0, new AttackComponent(this, 36, new Vector(-48, -48), 44, 96, 96)
                        .withChanneled(true, 24))
                .withComponent(44, new Hitbox(this, 1, new Vector(-48, -48), 96, 96, 0, 0, 96, 96, 0, 24)
                        .withFX(rocks))
                .withComponent(44,  new AttackComponent(this, 36, new Vector(-48, -48), 0, 96, 96)
                        .withActiveAnimation(crack.clone()))
                .withAnimation("jump", 7, true);

        stomp = new CompoundAbility("stomp", 0, 256, 120, this, stompAttack, new AbilityDash(0, 0, this, 16, 16, 8))
                .withAnimation("jump", 7, true);

        shield = new AbilityCounter("shield", 0, 180, this,
                new AbilityDash(0, 0, this, 0, -3, 48)
                        .withChanneled()
                        .withAnimation("walk", 10, true));

        twinMissile = new Attack("circle_missiles", 0, 128, 300, this)
                .withComponent(0, new Missile(Missile.CIRCLE, this, 60, new Vector(-16, -96), 32, 32, 8, 8, 16, 16, 30, 24)
                        .withInitialSpeed(12.0f)
                        .withAcceleration(-0.08f)
                        .withTurnRate(0.08)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                .withComponent(0, new Missile(Missile.CIRCLE, this, 60, new Vector(-16, 64), 32, 32, 8, 8, 16, 16, 30, 24)
                        .withStartingAngle(Math.PI)
                        .withInitialSpeed(12.0f)
                        .withAcceleration(-0.08f)
                        .withTurnRate(0.08)
                        .withChanneled(true, 80)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()));

        handSlash = new Attack("hand", 0, 96, 120, this)
                .withComponent(0, new AttackComponent(this, 18, new Vector(28, -72), 24, 144, 144)
                        .withChanneled(true, 24)
                        .withChargeUpAnimation(handCharge)
                        .withActiveAnimation(hand)
                        .withOnTop(true))
                .withComponent(24, new Hitbox(this, 6, new Vector(36, -18), 36, 36, 0, 0, 36, 36, 0, 18))
                .withComponent(24, new Hitbox(this, 1, new Vector(54 + 28, -72), 36, 36, 0, 0, 36, 36, 0, 12))
                .withComponent(27, new Hitbox(this, 1, new Vector(108 + 28, -18), 36, 36, 0, 0, 36, 36, 0, 18))
                .withComponent(30, new Hitbox(this, 1, new Vector(54 + 28, 36), 36, 36, 0, 0, 36, 36, 0, 12))
                .withAnimation("channel", 8, true);

        abilities.add(handSlash);
        abilities.add(stomp);
        //abilities.add(twinMissile);
        //abilities.add(shield);
    }
}
