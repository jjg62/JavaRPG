package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Player;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.main.Game;

public abstract class HUDElement implements Ticking {

    protected Game game;
    protected Player player;
    protected int width, height;

    public HUDElement(Game game, int width, int height){
        this.game = game;
        this.width = width;
        this.height = height;
        updatePlayerInstance();
    }

    public void updatePlayerInstance(){
        player = game.getRoom().getPlayer();
    }

}
