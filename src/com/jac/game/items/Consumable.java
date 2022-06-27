package com.jac.game.items;

import com.jac.game.abilities.Ability;
import com.jac.game.entities.Mob;

import java.awt.image.BufferedImage;

public abstract class Consumable extends Item{

    private BufferedImage usedIcon;

    public Consumable(String name, String title, String description, BufferedImage icon, BufferedImage usedIcon) {
        super(name, title, description, icon);
        this.usedIcon = usedIcon;
    }

    public abstract void consumeEffect(Mob owner);

    public BufferedImage getUsedIcon(){
        return usedIcon;
    }
}
