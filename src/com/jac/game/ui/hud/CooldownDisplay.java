package com.jac.game.ui.hud;

import com.jac.game.abilities.attacks.Attack;
import com.jac.game.display.GameGraphics;
import com.jac.game.main.Game;
import com.jac.game.ui.hud.HUDElement;

import java.awt.*;

/**
 * Class needs work - but as a prototype it works pretty well
 */
public class CooldownDisplay extends HUDElement {

    private Attack attack;
    private double cooldownRatio;
    private int cooldownBarWidth = 32;
    private int cooldownBarHeight = 64;

    public CooldownDisplay(Game game, int width, int height) {
        super(game, width, height);
        updatePlayerInstance();
    }

    public void updatePlayerInstance(){
        super.updatePlayerInstance();
        //attack = player.getSpecial();
    }

    @Override
    public void tick() {
        if(attack != null) cooldownRatio = attack.getCooldownRatio();
    }

    @Override
    public void render(GameGraphics graphics) {
        //Border
        graphics.g.setColor(Color.BLUE);
        graphics.g.drawRect(width - cooldownBarWidth - 16, height - cooldownBarHeight - 16, cooldownBarWidth, cooldownBarHeight);

        //Meter
        int fillHeight = (int) (cooldownRatio * cooldownBarHeight);
        graphics.g.fillRect(width - cooldownBarWidth - 16, height - fillHeight - 16, cooldownBarWidth, fillHeight);
    }
}
