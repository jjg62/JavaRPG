package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.items.Consumable;
import com.jac.game.items.Item;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class EquippedItemSlot extends HUDElement{

    private static final BufferedImage IMG_NONE = FileUtils.loadImage("/textures/ui/none.png");
    private BufferedImage imageLocked = FileUtils.loadImage("/textures/ui/item_locked.png");
    private BufferedImage itemIcon;
    private String text;

    private int x;

    public EquippedItemSlot(Game game, int width, int height, int x, String text) {
        super(game, width, height);
        this.x = x;
        this.text = text;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.drawStatic(imageLocked, x, 16, 80, 80);
        graphics.drawStatic(itemIcon, x + 8, 24, 64, 64);
        graphics.drawString(text, x, 108, 11, Color.WHITE);
    }

    public void setItem(Item item){
        if(item == null){
            itemIcon = IMG_NONE;
        }else {
            this.itemIcon = item.getIcon();
        }
    }
}
