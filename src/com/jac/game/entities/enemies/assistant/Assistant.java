package com.jac.game.entities.enemies.assistant;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityCounter;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.display.Camera;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class Assistant extends Mob {

    private Ability missiles;
    private Ability circleMissiles;
    private Ability shield;

    public Assistant(Room room) {
        super(room, 96, 96, 5, "assistant");
        setBehaviour(new FollowAlways(this, room.getPlayer(), 1));
        setBoundingBox(16, 32, 64, 64);
    }

    @Override
    protected void initAttacks() {

        //laggy
        Animation missileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/missile.png", 32, 32));
        Animation missileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/missile_charge.png", 32, 32));
        Animation goldenMissileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile.png", 32, 32));
        Animation goldenMissileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile_charge.png", 32, 32));



        missiles = new Attack("missiles", 0, 256, 180, this)
                .withComponent(0, new Missile(Missile.TOWARDS_PLAYER, this, 120, new Vector(48, 106), 32, 32, 8, 8, 16, 16, 60, 12)
                        .withStartingAngle(0.5)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                .withComponent(20, new Missile(Missile.TOWARDS_PLAYER,this, 120, new Vector(48, -124), 32, 32, 8, 8, 16, 16, 40, 12)
                        .withStartingAngle(-0.5)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                .withComponent(10, new Missile(Missile.TOWARDS_PLAYER,this, 120, new Vector(32, -16), 32, 32, 8, 8, 16, 16, 50, 12)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                .withAnimation("channel", 8);

        circleMissiles = new Attack("circle_missiles", 0, 128, 300, this)
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
                        .withActiveAnimation(goldenMissileAnimation.clone()));

        //Cool unused attack
        shield = new AbilityCounter("counter_dash", 0, 180, this,
                circleMissiles)
                .withAnimation("shield", 10, true);


        abilities.add(missiles);
        //abilities.add(circleMissiles);
        //abilities.add(shield);
    }

    @Override
    public void tick(){
        super.tick();
        Camera.duelCamera(this, room.getPlayer());
    }
}
