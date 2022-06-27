package com.jac.game.entities.interact.debug;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.Interactable;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.TileTextures;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class Talking extends Interactable {

    private String[] text = {"You can talk to me!\nAlso I hope this works"};

    private BufferedImage image;

    public Talking(Room room, int width, int height) {
        super(room, width, height);
        image = FileUtils.loadImage("/textures/entities/interacts/assistant.png");
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.draw(image, x, y, 96, 96);

    }

    public void setText(String... text){
        this.text = text;
    }

    public String[] getText(){
        return text;
    }

    @Override
    public void interaction(InteractingManager manager){
        manager.showTextboxSequence(()->{}, "Talking", text);
    }
}
