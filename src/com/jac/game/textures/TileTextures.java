package com.jac.game.textures;

import com.jac.game.tiles.TileSheet;
import com.jac.game.utils.FileUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TileTextures {

    public static BufferedImage floor = FileUtils.loadImage("/textures/tiles/cave_floor.png");
    public static BufferedImage wall = FileUtils.loadImage("/textures/tiles/grass.png");


    public static HashMap<String, TileSheet> tileSheets = new HashMap<>(){{
        put("cave_floor", new TileSheet("cave_floor"));
        put("cave_wall_top", new TileSheet("cave_wall_top"));
        put("cave_wall_front", new TileSheet("cave_wall_front"));
        put("cave_floor_path", new TileSheet("cave_floor_path"));
        put("cave_floor_bruh", new TileSheet("cave_floor_bruh"));
        put("flux", new TileSheet("flux"));
    }};
}
