package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.main.Game;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Healthbar extends HUDElement {

    private int maxHealth;
    private int health;
    private Color playerHealthColour = new Color(37, 158, 3);
    private boolean showBar;

    private Animation animation;
    private Animation panel = new Animation(false, 10, FileUtils.loadImage("/textures/ui/hp_panel.png"));
    private Animation panel_charged = new Animation(8, FileUtils.loadAnimationFrames("/textures/ui/hp_panel_charged.png", 304, 65));


    private int progress;
    private boolean rising;


    public Healthbar(Game game, int width, int height) {
        super(game, width, height);
        animation = panel;
    }


    public void playOpening(){
        progress = 0;
        rising = true;
    }


    public void playEnding(){
        progress = 64;
        rising = false;
    }


    @Override
    public void tick() {
        maxHealth = player.getMaxHealth();
        health = player.getHealth();

        progress = Math.max(Math.min(64, progress + (rising ? 2 : -2)), 0);

        if(player.getMomentum() < 600*0.9){
            animation = panel;
        }else{
            animation = panel_charged;
            animation.tick();
        }
    }

    @Override
    public void render(GameGraphics graphics) {

        graphics.drawStatic(animation.getFrame(), 0, height - progress, 304 , 64);

        graphics.setGColour(Color.black);
        graphics.fillStaticRectangle(24, height + 18 - progress, 216, 12);

        double healthFraction = health / (maxHealth / 1.0);
        graphics.setGColour(Color.red);
        graphics.fillStaticRectangle(24, height + 18 - progress, (int) (healthFraction * (216)), 12);

        graphics.setGColour(Color.black);
        graphics.drawStaticRectangle(24, height + 18 - progress, 216, 12);

        graphics.setGColour(Color.white);
        graphics.drawString("Health", 24, height + 17 - progress, 10, Color.WHITE);


        //Momentum
        graphics.drawString("Momentum", 24, height + 39 - progress, 10, Color.WHITE);

        graphics.setGColour(Color.black);
        graphics.fillStaticRectangle(24, height + 40 - progress, 216, 12);

        double momentumFraction = player.getMomentum() / 600.0;
        graphics.setGColour(Color.green);
        graphics.fillStaticRectangle(24, height + 40 - progress, (int) (momentumFraction * (216)), 12);

        graphics.setGColour(Color.white);
        graphics.drawStaticRectangle(24, height + 40 - progress, 216, 12);

        //Markers
        graphics.fillStaticRectangle(24 + 108, height + 40 - progress, 2, 12);
        graphics.fillStaticRectangle(24 + 195, height + 40 - progress, 2, 12);
    }
}
