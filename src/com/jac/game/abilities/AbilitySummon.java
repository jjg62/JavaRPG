package com.jac.game.abilities;

import com.jac.game.entities.Entity;
import com.jac.game.entities.Mob;

public class AbilitySummon extends Ability{

    private Entity summon;
    private int x;
    private int y;

    public AbilitySummon(String name, int key, int cooldown, Mob owner, int activationDelay, Entity summon, int x, int y) {
        super(name, key, cooldown, owner, activationDelay);
        this.summon = summon;
        this.x = x;
        this.y = y;
    }

    @Override
    public void play() {
        summon.spawn(x, y);
    }

    @Override
    public void cancel(boolean force) {

    }
}
