package com.jac.game.rooms.utils;

import com.jac.game.rooms.Room;
import com.jac.game.tiles.Tile;
import com.jac.game.tiles.TileList;

//Class used for encapsulating all data from a file about a room.
public class RoomData {

    private Room room;

    private int width, height, spawnX, spawnY;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int[][] getTiles() {
        return tiles;
    }

    private int[][] tiles;

    public RoomData(Room room, int width, int height, int spawnX, int spawnY, int[][] tiles){
        this.room = room;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.tiles = tiles;
    }

    /** Turn the int array of tile IDs to an array of Tiles.
     * @return A 2D array of Tiles from the file.
     */
    public Tile[][] generateTiles(){
        Tile[][] tileObjs = new Tile[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tileObjs[i][j] = TileList.getCopy(room, tiles[i][j]);
            }
        }
        return tileObjs;
    }
}
