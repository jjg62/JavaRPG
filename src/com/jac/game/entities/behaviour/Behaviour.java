package com.jac.game.entities.behaviour;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.Vector;
import com.jac.game.entities.structs.Facing;

public interface Behaviour extends Facing {

    public Vector moveCommand();

    public boolean abilityCommand(Ability a);

}
