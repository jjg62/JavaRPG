package com.jac.game.tiles.old;

import com.jac.game.display.GameGraphics;
import com.jac.game.tiles.TileList;

import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    private BufferedImage icon;
    private boolean solid;


    public Tile(int id, BufferedImage icon){
        this(id, icon, false);
    }

    public Tile(int id, BufferedImage icon, boolean solid){
        this.icon = icon;
        this.solid = solid;

        //TileList.tiles[id] = this;
    }

    /** Render this tile's image at a tileX and tileY
     * @param graphics The graphics object
     * @param tX The x of the position to render this tile
     * @param tY The y of the position to render this tile
     */
    public void render(GameGraphics graphics, int tX, int tY){
        graphics.draw(icon, tX * TILE_WIDTH, tY * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
    }

    public boolean isSolid() {
        return solid;
    }
}
