package com.jac.game.entities.enemies.assistant;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityCounter;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class AssistantPhase2 extends Assistant{

    private Ability circleMissiles;

    public AssistantPhase2(Room room) {
        super(room);
    }

    @Override
    protected void initAttacks() {

        super.initAttacks();

        //laggy
        Animation goldenMissileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile.png", 32, 32));
        Animation goldenMissileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile_charge.png", 32, 32));


        circleMissiles = new Attack("circle_missiles", 0, 128, 180, this)
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
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                .withComponent(0, new Missile(Missile.CIRCLE, this, 60, new Vector(64, -16), 32, 32, 8, 8, 16, 16, 30, 24)
                        .withStartingAngle(Math.PI/2)
                        .withInitialSpeed(12.0f)
                        .withAcceleration(-0.08f)
                        .withTurnRate(0.08)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                .withComponent(0, new Missile(Missile.CIRCLE, this, 60, new Vector(-96, -16), 32, 32, 8, 8, 16, 16, 30, 24)
                        .withStartingAngle(-Math.PI/2)
                        .withInitialSpeed(12.0f)
                        .withAcceleration(-0.08f)
                        .withTurnRate(0.08)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                .withAnimation("shield", 10, true);

        abilities.add(circleMissiles);
    }
}
