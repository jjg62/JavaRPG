package com.jac.game.ui.hud;

import com.jac.game.abilities.Ability;
import com.jac.game.display.GameGraphics;
import com.jac.game.items.Consumable;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;

public class UseConsumable extends HUDElement{

    protected static final BufferedImage IMG_NONE = FileUtils.loadImage("/textures/ui/none.png");
    private BufferedImage imageLocked = FileUtils.loadImage("/textures/ui/item_locked.png");
    private BufferedImage imageUnlocked = FileUtils.loadImage("/textures/ui/item_unlocked.png");

    protected BufferedImage itemReady;
    protected BufferedImage itemUsed;
    protected BufferedImage indicator = FileUtils.loadImage("/textures/ui/down_arrow.png");

    private int x;

    private boolean used;
    protected boolean empty;
    private double cooldownRatio;
    protected Ability ability;

    public UseConsumable(Game game, int width, int height, int x) {
        super(game, width, height);
        this.x = x;
        ability = player.getConsume();
    }

    @Override
    public void tick() {
        cooldownRatio = ability.getCooldownRatio();
        used = cooldownRatio != 1.0 || empty;
    }

    @Override
    public void render(GameGraphics graphics) {
        if(used){
            graphics.drawStatic(imageLocked, x, 16, 80, 80);
            graphics.drawStatic(itemUsed, x + 8, 24, 64, 64);
            if(!empty) {
                graphics.setGColour(Color.blue);
                graphics.fillStaticRectangle(x, 96, (int) (80 * cooldownRatio), 8);
                graphics.setGColour(Color.black);
            }
        }else{
            graphics.drawStatic(imageUnlocked, x, 16, 80, 80);
            graphics.drawStatic(itemReady, x + 8, 24, 64, 64);
            graphics.drawStatic(indicator, x + 24, 96, 32, 32);
        }
    }

    public void setItem(Consumable item){
        if(item != null){
            this.itemReady = item.getIcon();
            this.itemUsed = item.getUsedIcon();
            this.empty = false;
        }else{
            this.itemReady = IMG_NONE;
            this.itemUsed = IMG_NONE;
            this.empty = true;
        }
    }

    @Override
    public void updatePlayerInstance(){
        super.updatePlayerInstance();
        ability = player.getConsume();
    }

}
