package com.jac.game.abilities.attacks.components;

import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.entities.structs.Scheduler;

public class TimedComponent {

    private Attack attack;
    public int delay;
    public AttackComponent component;

    public TimedComponent(Attack attack, int delay, AttackComponent component){
        this.attack = attack;
        this.delay = delay;
        this.component = component;
    }

    public void createAt(int x, int y, double angle){
        Scheduler.getInstance().addTimedAction(delay, ()->{
            if(!attack.isCancelled()) attack.addCurrentlyActive(component.createAt(x, y, angle));
        });
    }
}
