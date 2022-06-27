package com.jac.game.ui.hud;

import com.jac.game.control.Controller;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.items.Item;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;

import java.awt.*;

public class TempInventory extends HUDElement{

    private boolean displaying = true;

    private boolean canToggleDisplay = true;

    public TempInventory(Game game, int width, int height) {
        super(game, width, height);
    }

    @Override
    public void tick() {
        if(canToggleDisplay && Controller.inventory()){
            canToggleDisplay = false;
            displaying = !displaying;
            Scheduler.getInstance().addTimedAction(60, ()->canToggleDisplay = true);
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        /*
        if(displaying) {
            int y = 32;
            graphics.drawString("INVENTORY:\n", width - 120, 16, 14, Color.WHITE);
            for (Item item : GameInfo.getInstance().getInventory().getItems()) {
                graphics.drawString(item.getName() + " x" + item.getQuantity() + "\n", width - 120, y, 14, Color.WHITE);
                y += 16;
            }
        }
         */
    }
}
