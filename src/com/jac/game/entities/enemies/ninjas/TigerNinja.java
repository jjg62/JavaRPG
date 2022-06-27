package com.jac.game.entities.enemies.ninjas;

import com.jac.game.abilities.*;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.components.ChannelHitbox;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.fx.VisualEffect;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.awt.event.KeyEvent;

public class TigerNinja extends Mob {

    private Attack attack;
    private Ability dash;

    public TigerNinja(Room room) {
        super(room, 96, 96, 2, "crane_ninja");
        setBoundingBox(24, 48, 48, 48);


        setBehaviour(new FollowAlways(this, room.getPlayer(), 2));
        weight = 2;
    }

    @Override
    protected void initAttacks() {

        attack = new Attack("attack", 0, 96, 60, this)
                .withComponent(0, new ChannelHitbox(this, 1, new Vector(24, -24), 48, 48, 0, 0, 48, 48, 20, 16, true)
                        .withEndLag(16))
                .withAnimation("attack", 6);

        dash = new CompoundAbility("pounce", KeyEvent.VK_RIGHT, 112, 180, this,
                new AbilityDash(KeyEvent.VK_RIGHT, 90, this, 60, 14, 12),
                new Attack("DashAttackDamage", 0, 0, 0, this)
                        .withComponent(0, new ChannelHitbox(this, 1, new Vector(32, -32), 64, 64, 0, 0, 64, 64, 60, 32, false)
                                .withActiveAnimation(new Animation(12, FileUtils.loadAnimationFrames("/textures/fx/tigerAttackTell.png", 64, 64)))
                                .withFX(new VisualEffect(room, "tigerAttackParticle", 4, 64, 64, 16)))
                        .withComponent(0, new ChannelHitbox(this, 1, new Vector(96, -32), 64, 64, 0, 0, 64, 64, 72, 32, true)
                                .withEndLag(16)
                                .withActiveAnimation(new Animation(12, FileUtils.loadAnimationFrames("/textures/fx/tigerAttackTell.png", 64, 64)))
                                .withFX(new VisualEffect(room, "tigerAttackParticle", 4, 64, 64, 16))))
                .withAnimation("claw", 15);
        

        //abilities.add(attack);
        abilities.add(dash);
    }

}
