package com.jac.game.entities.enemies.dweller;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.projectiles.missiles.Missile;
import com.jac.game.display.Camera;
import com.jac.game.entities.enemies.bh.BHSequence;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class MeleeDwellerBHAvalanche extends BHSequence {

    private Ability rockFall;

    public MeleeDwellerBHAvalanche(Room room) {
        super(room, "melee_dweller", 96, 96);
    }


    @Override
    public void spawn(int x, int y){
        super.spawn(x, y);
        Scheduler.getInstance().addTimedAction(126, ()-> Camera.shake(16, 30, 30, true));
        Scheduler.getInstance().addTimedAction(186, ()-> Camera.shake(16, 30, 30, true));
        Scheduler.getInstance().addTimedAction(264, ()-> Camera.shake(32, 30, 60, true));
    }

    @Override
    protected void initAttacks() {

        Animation rockTellAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/rock_tell.png", 32, 32));
        Animation rockAnimation = new Animation(0, FileUtils.loadAnimationFrames("/textures/attacks/rock.png", 32, 32));


        Ability stompMotion = new CompoundAbility("big_stomp", 0, 0, 0, this,
                new AbilityDash(0, 0, this, 12, 8, 4).withFixedVector(new Vector(0, -1)),
                new AbilityDash(0, 0, this, 32, 8, 4).withFixedVector(new Vector(0, 1)));

        Ability longStompMotion = new CompoundAbility("big_stomp", 0, 0, 0, this,
                new AbilityDash(0, 0, this, 18, 8, 6).withFixedVector(new Vector(0, -1)),
                new AbilityDash(0, 0, this, 48, 8, 6).withFixedVector(new Vector(0, 1)));

        Ability stomp = new CompoundAbility("big_stomp", 0, 0, 0, this, stompMotion, new Attack("burh", 0, 0, 0, this)
                .withComponent(0, new AttackComponent(this, 0, new Vector(0, 0), 0, 0, 0)
                        .withChanneled(true, 54))).withAnimation("jump", 6, true);

        Ability longStomp = new CompoundAbility("big_stomp", 0, 0, 0, this, longStompMotion, new Attack("burh", 0, 0, 0, this)
                .withComponent(0, new AttackComponent(this, 0, new Vector(0, 0), 0, 0, 0)
                        .withChanneled(true, 81))).withAnimation("jump", 9, true);



        float rockTellSpeed = 3.0f;
        float rockTellAccel = 0.4f;

        float rockSpeed = 3.0f;
        float rockAccel = 0.2f;

        rockFall = new Attack("rockfall", 0, 0, 0, this)
                        .withComponent(0, new Missile(Missile.STRAIGHT, this, 240, new Vector(-288, -336), 64, 64, 0, 0, 0, 0, 28, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(40, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(70, new Missile(Missile.STRAIGHT, this, 240, new Vector(-200, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(88, new Missile(Missile.STRAIGHT, this, 240, new Vector(180, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(108, new Missile(Missile.STRAIGHT, this, 240, new Vector(64, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(120, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(145, new Missile(Missile.STRAIGHT, this, 240, new Vector(-196, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(170, new Missile(Missile.STRAIGHT, this, 240, new Vector(-96, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))
                        .withComponent(196, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 0, 0, 0, 0, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockTellSpeed).withAcceleration(rockTellAccel)
                                .withActiveAnimation(rockTellAnimation.clone()))



                        .withComponent(0 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-288, -336), 64, 64, 16, 16, 32, 32, 28, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(40 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(60 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-32, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(70 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-200, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(80 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(96, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(88 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(180, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(104 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-96, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(108 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(64, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(108 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-288, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(120 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                    .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(132 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(148, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(140 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(0, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(145 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-196, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(158 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(96, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(170 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-96, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(188 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(-288, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()))
                        .withComponent(196 + 16, new Missile(Missile.STRAIGHT, this, 240, new Vector(224, -336), 64, 64, 16, 16, 32, 32, 0, 10)
                                .withStartingAngle(Math.PI/2)
                                .withInitialSpeed(rockSpeed).withAcceleration(rockAccel)
                                .withActiveAnimation(rockAnimation.clone()));


        addAttackAtTime(90, stomp);
        addAttackAtTime(150, stomp);
        addAttackAtTime(210, longStomp);
        addAttackAtTime(89, rockFall);
        //addAttackAtTime(209, rockFall);
    }

    @Override
    public void setChannelAnimation(String type, String direction, int speed){
        super.setChannelAnimation(type, direction, speed);
    }
}
