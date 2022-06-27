package com.jac.game.entities.interact;

import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.encounters.Encounter;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class Gong extends Interactable{

    private BufferedImage image;
    private Encounter encounter;
    private String speaker = "Yu";
    private String[] text;

    public Gong(Room room, Encounter encounter, String... text) {
        super(room, 96, 96);
        this.encounter = encounter;
        image = FileUtils.loadImage("/textures/entities/interacts/gong.png");
        setBoundingBox(0, 48, 96, 48);
        this.text = text;
    }

    @Override
    public void interaction(InteractingManager manager) {
        //Camera.shake(16, 20, 80, true);
        if(text.length > 0){
            manager.showTextboxSequence(()->encounter.start(), speaker, text);
        }else{
            encounter.start();
            manager.finishInteracting();
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        if(targeted){
            graphics.draw(image, x, y, 96, 96);
        }else{
            graphics.draw(image, x, y, 96, 96);
        }
    }
}
