package com.jac.game.display;

import com.jac.game.entities.structs.Ticking;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class GameGraphics {

    //Scale
    private float scale;

    //Graphics objects
    public Graphics g;
    public Graphics2D g2;

    //Camera
    private Camera cam;

    //Font
    private Font textboxFont;

    //Alpha
    private Composite defaultComposite;

    public GameGraphics(BufferStrategy bs, Camera cam, Display display){
        g = bs.getDrawGraphics();
        g2 = (Graphics2D)g;

        this.scale = display.getScale();
        this.defaultComposite = g2.getComposite();
        this.cam = cam;

        textboxFont = new Font("TimesRoman", Font.PLAIN, scaled(14));
        g.setFont(textboxFont);
    }

    public void draw(BufferedImage icon, int x, int y, int width, int height){
        g.drawImage(icon, scaled(x - cam.getXOffset()), scaled(y - cam.getYOffset()), scaled(width), scaled(height), null);
    }

    public void drawAtAngle(BufferedImage icon, int x, int y, int width, int height, double angle, int pivotX, int pivotY){
        AffineTransform old = g2.getTransform();
        g2.rotate(angle, scaled(x  + pivotX - cam.getXOffset()), scaled(y + pivotY - cam.getYOffset()));
        g2.drawImage(icon, scaled(x - cam.getXOffset()), scaled(y - cam.getYOffset()), scaled(width), scaled(height), null);
        g2.setTransform(old);
    }

    public void drawStatic(BufferedImage icon, int x, int y, int width, int height){
        g.drawImage(icon, scaled(x), scaled(y), scaled(width), scaled(height), null);
    }

    public void drawRectangle(int x, int y, int width, int height){
        g.drawRect(scaled(x - cam.getXOffset()), scaled(y - cam.getYOffset()), scaled(width), scaled(height));
    }

    public void fillRectangle(int x, int y, int width, int height){
        g.fillRect(scaled(x - cam.getXOffset()), scaled(y - cam.getYOffset()), scaled(width), scaled(height));
    }

    public void drawStaticRectangle(int x, int y, int width, int height){
        g.drawRect(scaled(x), scaled(y), scaled(width), scaled(height));
    }

    public void fillStaticRectangle(int x, int y, int width, int height){
        g.fillRect(scaled(x), scaled(y), scaled(width), scaled(height));
    }


    public void drawShadow(int x, int y, double size){
        Composite old = g2.getComposite();
        AlphaComposite shadow = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2.setComposite(shadow);
        g2.fillOval(scaled(x - cam.getXOffset()), scaled(y - cam.getYOffset()), scaled((int)(32*size)), scaled((int)(16*size)));
        g2.setComposite(old);
    }

    public void setG2Alpha(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void resetG2Alpha(){
        g2.setComposite(defaultComposite);
    }

    public void drawString(String string, int x, int y){
        g.drawString(string, scaled(x), scaled(y));
    }

    public void drawString(String string, int x, int y, int size, Color color){
        Color temp = g.getColor();
        g.setColor(color);
        g.setFont(new Font("TimesRoman", Font.PLAIN, scaled(size)));
        g.drawString(string, scaled(x), scaled(y));
        g.setColor(temp);
    }

    public void setGColour(Color colour){
        g.setColor(colour);
    }

    public void setG2Colour(Color colour){
        g2.setColor(colour);
    }

    public void dispose(){
        g.dispose();
        g2.dispose();
    }

    private int scaled(int num){
        return (int)(num*scale);
    }


}
