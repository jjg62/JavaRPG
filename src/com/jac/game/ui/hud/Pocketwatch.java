package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;

import java.awt.*;

public class Pocketwatch extends HUDElement{

    private int day;
    private int time;

    public Pocketwatch(Game game, int width, int height) {
        super(game, width, height);
    }

    @Override
    public void tick() {
        day = GameInfo.getInstance().getDay();
        time = GameInfo.getInstance().getTime();
    }

    @Override
    public void render(GameGraphics graphics) {
        String timeString;
        if(time > 12){
            timeString = (time - 12) + "PM";
        }else{
            timeString = time + "AM";
        }
        graphics.drawString("Day " + day, 32, 32, 14, Color.WHITE);
        graphics.drawString(timeString, 32, 48, 14, Color.WHITE);

        //Debug mode
        graphics.drawString(player.getX() + ", " + player.getY(), 32, 64, 14, Color.WHITE);
     }
}
