package com.jac.game.entities.interact;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.statics.Torch;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;

public class ChangeTorch extends Interactable {

    private Torch torch;
    private int colour;

    public ChangeTorch(Torch torch) {
        super(torch.getRoom(), torch.getWidth(), torch.getHeight());
        this.torch = torch;
        colour = torch.getColour();
    }

    @Override
    public void interaction(InteractingManager manager) {
        colour = Math.floorMod(colour+1, 4);
        torch.updateAnimations(colour);
        manager.finishInteracting();
    }

    @Override
    public void spawn(int x, int y){
        torch.spawn(x, y);
        super.spawn(x, y);
    }

    @Override
    public void render(GameGraphics graphics) {
        torch.render(graphics);
    }

    public int getColour(){
        return colour;
    }
}
