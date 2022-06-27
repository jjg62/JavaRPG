package com.jac.game.entities.enemies.assistant;

import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.display.Camera;
import com.jac.game.entities.enemies.bh.BHSequence;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class AssistantBH extends BHSequence {

    private Attack bigMissiles;
    private Attack bigCircleMissiles;

    public AssistantBH(Room room) {
        super(room, "assistant", 96, 96);
    }

    @Override
    protected void initAttacks() {
        Animation missileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/missile.png", 32, 32));
        Animation missileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/missile_charge.png", 32, 32));
        Animation goldenMissileAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile.png", 32, 32));
        Animation goldenMissileChargeAnimation = new Animation(false, 10, FileUtils.loadAnimationFrames("/textures/attacks/golden_missile_charge.png", 32, 32));


        bigMissiles = new Attack("greater_missiles", 0, 256, 0, this)
                //Bottom-Right
                .withComponent(0, new Missile(Missile.RANDOM, this, 180, new Vector(64, 106), 32, 32, 8, 8, 16, 16, 60, 12)
                        .withTurnRate(0.2)
                        .withStartingAngle(Math.PI / 4)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                //Top-Right
                .withComponent(20, new Missile(Missile.RANDOM, this, 180, new Vector(64, -124), 32, 32, 8, 8, 16, 16, 40, 12)
                        .withTurnRate(0.2)
                        .withStartingAngle(-Math.PI / 4)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                //Right
                .withComponent(10, new Missile(Missile.RANDOM, this, 180, new Vector(128, -16), 32, 32, 8, 8, 16, 16, 50, 12)
                        .withTurnRate(0.2)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                //Bottom-Left
                .withComponent(50, new Missile(Missile.RANDOM, this, 180, new Vector(-96, 106), 32, 32, 8, 8, 16, 16, 10, 12)
                        .withTurnRate(0.2)
                        .withStartingAngle(3*Math.PI/4)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                //Top-Left
                .withComponent(30, new Missile(Missile.RANDOM, this, 180, new Vector(-96, -124), 32, 32, 8, 8, 16, 16, 30, 12)
                        .withTurnRate(0.2)
                        .withStartingAngle(-3*Math.PI/4)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                //Left
                .withComponent(40, new Missile(Missile.RANDOM, this, 180, new Vector(-160, -16), 32, 32, 8, 8, 16, 16, 20, 12)
                        .withTurnRate(0.2)
                        .withStartingAngle(Math.PI)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(missileChargeAnimation.clone())
                        .withActiveAnimation(missileAnimation.clone()))
                .withAnimation("channel", 8);




        bigCircleMissiles = new Attack("greater_circle_missiles", 0, 256, 0, this)
                //Bottom-Right
                .withComponent(0, new Missile(Missile.CIRCLE, this, 180, new Vector(64, 106), 32, 32, 8, 8, 16, 16, 60, 12)
                        .withStartingAngle(Math.PI / 4)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                //Top-Right
                .withComponent(20, new Missile(Missile.CIRCLE, this, 180, new Vector(64, -124), 32, 32, 8, 8, 16, 16, 40, 12)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withStartingAngle(-Math.PI / 4)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                //Right
                .withComponent(10, new Missile(Missile.CIRCLE, this, 180, new Vector(128, -16), 32, 32, 8, 8, 16, 16, 50, 12)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                //Bottom-Left
                .withComponent(50, new Missile(Missile.CIRCLE, this, 180, new Vector(-96, 106), 32, 32, 8, 8, 16, 16, 10, 12)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withStartingAngle(3*Math.PI/4)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                //Top-Left
                .withComponent(30, new Missile(Missile.CIRCLE, this, 180, new Vector(-96, -124), 32, 32, 8, 8, 16, 16, 30, 12)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withStartingAngle(-3*Math.PI/4)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                //Left
                .withComponent(40, new Missile(Missile.CIRCLE, this, 180, new Vector(-160, -16), 32, 32, 8, 8, 16, 16, 20, 12)
                        .withAcceleration(0.1f)
                        .withInitialSpeed(6.0f)
                        .withStartingAngle(Math.PI)
                        .withTurnRate(0.04)
                        .withChanneled(true, 0)
                        .withChargeUpAnimation(goldenMissileChargeAnimation.clone())
                        .withActiveAnimation(goldenMissileAnimation.clone()))
                .withAnimation("channel", 8);
        
        
        

        addAttackAtTime(91, bigMissiles);
        addAttackAtTime(152, bigMissiles);

        addAttackAtTime(213, bigCircleMissiles);
    }


    @Override
    public void tick(){
        super.tick();
        Camera.duelCamera(this, room.getPlayer());
    }
}
