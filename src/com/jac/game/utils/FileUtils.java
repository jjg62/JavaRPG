package com.jac.game.utils;

import com.jac.game.main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FileUtils {

    public static File loadFile(String path){
        try {
            return new File(FileUtils.class.getClassLoader().getResource(path).getFile());
        }catch(NullPointerException e){
            return null;
        }
    }

    /** Get a BufferedImage from a file path
     */
    public static BufferedImage loadImage(String path){
        try{
            URL res = Game.class.getResource(path);
            if(res == null){
                return loadImage("/textures/other/missing.png");
            }
            return ImageIO.read(Game.class.getResource(path));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static BufferedImage[] loadAnimationFrames(String path, int width, int height){
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

        BufferedImage spriteSheet = loadImage(path);

        int horFrames = spriteSheet.getWidth() / width;
        int vertFrames = spriteSheet.getHeight() / height;

        for(int i = 0; i < vertFrames; i++){
            for(int j = 0; j < horFrames; j++){
                frames.add(spriteSheet.getSubimage(j*width, i*height, width, height));
            }
        }

        return frames.toArray(new BufferedImage[horFrames * vertFrames]);
    }
}
