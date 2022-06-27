package com.jac.game.items;

import com.jac.game.abilities.Ability;
import com.jac.game.entities.Mob;

import java.awt.image.BufferedImage;

public abstract class Skill extends Item{
    public Skill(String name, String title, String description, BufferedImage icon) {
        super(name, title, description, icon);
    }

    public abstract Ability ability(Mob owner);
}
