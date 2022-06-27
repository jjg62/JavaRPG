package com.jac.game.tiles;

import com.jac.game.display.GameGraphics;
import com.jac.game.rooms.Room;
import com.jac.game.textures.TileTextures;

import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    private String name;

    private Room room;
    private BufferedImage icon;
    private boolean solid;
    private TileSheet sheet;

    public Tile(String name, boolean solid){
        this.name = name;
        this.solid = solid;
        this.sheet = TileTextures.tileSheets.get(name);
    }

    public Tile(String name, boolean solid, Room room){
        this.name = name;
        this.solid = solid;
        this.room = room;
        this.sheet = TileTextures.tileSheets.get(name);
    }

    /** Clone a new instance with the same properties as this one.
     * The clones are the ones rendered in the actual game, originals are used for reference.
     * @return The clone
     */
    public Tile copyOf(Room room){
        return new Tile(name, solid, room);
    }

    /** The tile checks surrounding tiles and calculates a number which is used to find the respective direction texture.
     */
    public void updateIcon(int tX, int tY){
        int direction = (isSameTile(tX, tY - 1) ? 1 : 0) +
                (isSameTile(tX - 1, tY) ? 2 : 0) +
                (isSameTile(tX + 1, tY) ? 4 : 0) + (isSameTile(tX, tY + 1) ? 8 : 0);
        icon = sheet.getTileTexture(direction);
    }

    /** Helper function that checks if a tile at a position is of the same type.
     */
    private boolean isSameTile(int tX, int tY){
        if(tX < 0 || tX >= room.getWidth() || tY < 0 || tY >= room.getHeight()){
            return true;
        }else{
            return room.getTileT(tX, tY).getName() == name;
        }
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

    public String getName(){
        return name;
    }

}
