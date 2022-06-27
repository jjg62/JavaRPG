package com.jac.game.entities.interact;

import com.jac.game.display.GameGraphics;
import com.jac.game.items.Item;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

public class Chest extends Interactable{

    private static Animation chestClosed = new Animation(12, FileUtils.loadAnimationFrames("/textures/entities/interacts/chest_closed.png", 64, 64));

    private String[] openText = {"You found an item in the chest!"};
    private Item[] items;

    private Animation animation;
    private boolean opened;

    public Chest(Room room, boolean opened, Item... items) {
        super(room, 96, 96);
        animation = chestClosed.clone();
        this.opened = opened;
        this.items = items;
        setBoundingBox(7, 30, 81, 45);
    }

    public Chest withOpenText(String... openText){
        this.openText = openText;
        return this;
    }

    @Override
    public void interaction(InteractingManager manager) {
        if(!opened) {
            opened = true;
            for (Item item : items) {
                GameInfo.getInstance().getInventory().addItem(item);
            }
            manager.showTextboxSequence("Info", openText);
        }else{
            manager.finishInteracting();
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        animation.tick();
        graphics.draw(animation.getFrame(), x, y, width, height);
    }
}
