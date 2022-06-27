package com.jac.game.ui.hud;

import com.jac.game.display.GameGraphics;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.ui.hud.HUDElement;

public class CoinDisplay extends HUDElement {

    public CoinDisplay(Game game, int width, int height) {
        super(game, width, height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.g.drawString(Integer.toString(GameInfo.getInstance().getCoinAmount()), width - 64, 16);
    }
}
