package com.jac.game.abilities.attacks;

import com.jac.game.entities.Player;

public class MomentumAttack extends Attack{

    public MomentumAttack(String name, int key, int range, int cooldown, Player owner) {
        super(name, key, range, cooldown, owner);
    }

    @Override
    public void play(){
        super.play();
        ((Player)owner).setMomentum(Math.max(((Player)owner).getMomentum() - 300, 0));
    }


}
