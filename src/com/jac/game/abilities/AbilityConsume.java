package com.jac.game.abilities;

import com.jac.game.entities.Mob;
import com.jac.game.items.Consumable;
import com.jac.game.main.GameInfo;

public class AbilityConsume extends Ability{

    public AbilityConsume(int key, Mob owner) {
        super("consume", key, 1, owner, 0);
    }

    @Override
    public void play() {
        Consumable consumable = GameInfo.getInstance().getInventory().consume();
        if(consumable != null){
            consumable.consumeEffect(owner);
        }
    }

    @Override
    public void cancel(boolean force) {

    }

    @Override
    protected void attemptCooldown(){

    }

    public void refreshCooldown(){
        isOnCooldown = true;
    }
}
