package com.jac.game.entities.enemies;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.entities.behaviour.StayWithin;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class RangedDweller extends Mob {

    private Ability missile;
    private Ability blink;

    public RangedDweller(Room room, int health) {
        super(room, 96, 96, health, "melee_dweller");
        setBehaviour(new StayWithin(this, room.getPlayer(), 1));
        setBoundingBox(12, 48, 72, 48);
        weight = 2;
    }

    @Override
    protected void initAttacks() {

        Animation missileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/missile.png", 32, 32));
        Animation missileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/missile_charge.png", 32, 32));
        Animation arrow = new Animation(false, 3, FileUtils.loadAnimationFrames("/textures/attacks/arrow.png", 16, 16));

        blink = new CompoundAbility("dash", 0, 96, 240, this,
                new AbilityDash(0, 0, this, 24, 16, 12),
                new Attack("indicator", 0, 0, 0, this)
                        .withComponent(0, new AttackComponent(this, 0, new Vector(64, -16), 24, 32, 32)
                                .withChargeUpAnimation(arrow.clone())
                                .withChanneled(true, 12)))
                .withAnimation("walk", 6);

        abilities.add(blink);

        missile = new Attack("missile", 0, 260, 120, this)
                .withComponent(0, new Missile(Missile.TOWARDS_PLAYER,this, 120, new Vector(32, -16), 32, 32, 8, 8, 16, 16, 30, 12)
                        .withInitialSpeed(8)
                        .withAcceleration(0.05f)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                .withAnimation("channel", 8, true);

        abilities.add(missile);
    }
}
