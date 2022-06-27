package com.jac.game.rooms.utils;

import com.jac.game.main.Game;
import com.jac.game.rooms.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class RoomLoader {

    public static RoomData loadRoom(Room room, String path){
        String file = loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        //Metadata
        int width = parseInt(tokens[0]);
        int height = parseInt(tokens[1]);

        int spawnX = parseInt(tokens[2]);
        int spawnY = parseInt(tokens[3]);

        int[][] tiles = new int[width][height];

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                tiles[x][y] = parseInt(tokens[(x + y * width) + 4]); //+4 for the metadata
            }
        }

        return new RoomData(room, width, height, spawnX, spawnY, tiles);
    }


    private static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();

        try{
            URL res = Game.class.getClassLoader().getResource(path);
            BufferedReader br = new BufferedReader(new FileReader(res.getFile())); //File reader
            String line;

            while((line = br.readLine()) != null){ //While the next line isn't empty
                builder.append(line + "\n"); //Add it to the string.
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }


        return builder.toString();
    }


    private static int parseInt(String number){
        try{
            return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}
