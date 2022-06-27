package com.jac.game.entities.enemies.ninjas;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.rooms.Room;

public class SnakeNinjaClone extends Mob {

    private Ability poisonPool;

    public SnakeNinjaClone(Room room) {
        super(room, 96, 96, 1, "crane_ninja");
        setBehaviour(new FollowAlways(this, room.getPlayer(), 1));
    }

    @Override
    protected void initAttacks() {

        poisonPool = new Attack("poison_pool", 0, 0, 180, this)
                .withComponent(0, new Hitbox(this, 240, new Vector(-64, -64), 128, 128, 0, 0, 128, 128, 0, 0));

        abilities.add(poisonPool);
    }

    @Override
    protected void die(){
        super.die();
        poisonPool.cast();
    }
}
