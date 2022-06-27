package com.jac.game.tiles;


import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class TileSheet {

    private BufferedImage sheet;
    private BufferedImage[] tiles;

    public TileSheet(String name){
        sheet = FileUtils.loadImage("/textures/tiles/" + name + ".png");
        initTileTextures();
    }

    private void initTileTextures(){
        tiles = new BufferedImage[16];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                tiles[i*4 + j] = sheet.getSubimage(j*32, i*32, 32, 32);
            }
        }
    }

    public BufferedImage getTileTexture(int direction){
        return tiles[direction];
    }
}
