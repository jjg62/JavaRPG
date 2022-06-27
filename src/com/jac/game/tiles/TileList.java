package com.jac.game.tiles;

import com.jac.game.rooms.Room;
import com.jac.game.textures.TileTextures;

public class TileList {

    public static Tile[] tiles = new Tile[256];

    public static void initTiles(){
        tiles[0] = new Tile("cave_floor_path", false);
        tiles[1] = new Tile("cave_wall_top", true);
        tiles[2] = new Tile("cave_wall_front", true);
        tiles[3] = new Tile("cave_floor_bruh", false);
        tiles[4] = new Tile("flux", true);
    }

    public static Tile getCopy(Room room, int id){
        return tiles[id].copyOf(room);
    }
}
