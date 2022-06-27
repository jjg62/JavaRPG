package com.jac.game.entities.structs;

import com.jac.game.display.GameGraphics;

public interface Ticking {
    public abstract void tick();
    public abstract void render(GameGraphics graphics);
}
