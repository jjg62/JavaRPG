package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.encounters.BHPhase;
import com.jac.game.encounters.Encounter;
import com.jac.game.main.Game;
import com.jac.game.ui.UIHandler;

import java.awt.*;
import java.util.ArrayList;

public class BossHealthbar extends HUDElement{

    private Encounter encounter;
    private int xBorder = 128;
    private int yBorder = 32;
    private Color barColour;

    private String name;
    private int maxHealth;
    private int health;

    public BossHealthbar(Game game, Encounter encounter){
        super(game, Game.width, 128);
        this.encounter = encounter;
        this.barColour = Color.red;
        this.maxHealth = encounter.getTotalHealth();
        name = encounter.getName();
    }

    @Override
    public void tick() {
        health = encounter.getTotalHealth();
        if(encounter.getCurrentPhase() instanceof BHPhase){
            barColour = Color.gray;
        }else{
            barColour = Color.red;
        }

        if(health <= 0){
            UIHandler.getInstance().remove(false, this);
        }
    }

    @Override
    public void render(GameGraphics graphics) {

        graphics.setGColour(Color.white);
        graphics.drawString(name, xBorder, 24);

        graphics.setGColour(Color.black);
        graphics.fillStaticRectangle(xBorder, yBorder, (Game.width-2*xBorder - 96), 16);

        double healthFraction = health / (maxHealth / 1.0);
        graphics.setGColour(barColour);
        graphics.fillStaticRectangle(xBorder, yBorder, (int)(healthFraction*(Game.width-2*xBorder - 96)), 16);

        graphics.setGColour(Color.black);
        graphics.drawStaticRectangle(xBorder, yBorder, Game.width-2*xBorder - 96, 16);
    }
}
