package com.jac.game.entities.enemies.ninjas;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilitySummon;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.entities.Mob;
import com.jac.game.entities.behaviour.FollowAlways;
import com.jac.game.rooms.Room;

public class SnakeNinja extends Mob {

    private Ability clone;

    public SnakeNinja(Room room) {
        super(room, 96, 96, 3, "crane_ninja");
        setBehaviour(new FollowAlways(this, room.getPlayer(), 1));
    }

    @Override
    protected void initAttacks() {

        clone = new AbilitySummon("clone", 0, 360, this, 60, new SnakeNinjaClone(room), 300, 300);

        abilities.add(clone);
    }
}
