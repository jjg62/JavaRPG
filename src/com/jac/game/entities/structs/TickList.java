package com.jac.game.entities.structs;

import com.jac.game.display.GameGraphics;

public class TickList<T extends Ticking> extends IterableChangingList<T> implements Ticking {

    public TickList(){
        super();
    }

    public void tick(){
        updateContents();
        for(T obj : contents){
            obj.tick();
        }
    }

    public void render(GameGraphics graphics){
        for(T obj : contents){
            obj.render(graphics);
        }
    }
}
