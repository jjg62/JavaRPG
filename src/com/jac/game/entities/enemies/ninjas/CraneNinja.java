package com.jac.game.entities.enemies.ninjas;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.projectiles.ChannelProjectile;
import com.jac.game.abilities.Vector;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.entities.behaviour.projectiles.Tornado;
import com.jac.game.rooms.Room;

public class CraneNinja extends Mob {

    private Ability tornado;

    public CraneNinja(Room room) {
        super(room, 96, 96, 2, "crane_ninja");
        setBehaviour(new FollowAlways(this, room.getPlayer(), 1));
    }

    @Override
    protected void initAttacks() {
        tornado = new Attack("Tornado", 0, 500, 120, this)
                .withComponent(0, new ChannelProjectile(this, 240, new Vector(32, -32), 64, 64, 16, 16, 32, 32, 40, 28, new Tornado(10, 2)))
                .withAnimation("channel", 10);

        abilities.add(tornado);
    }

}
