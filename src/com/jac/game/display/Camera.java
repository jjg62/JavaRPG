package com.jac.game.display;

import com.jac.game.entities.Entity;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.main.Game;
import com.jac.game.tiles.old.Tile;

import java.awt.*;

public class Camera implements Ticking {

    public static Camera instance;

    private Game game;

    private int xOffset = 0;
    private int yOffset = 0;
    private int screenWidth;
    private int screenHeight;

    private Fader fade;
    private Shaker shaker;
    private Panner panner;

    private boolean lockToPlayer = true;

    public Camera(Game game, int screenWidth, int screenHeight){
        this.game = game;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.fade = new Fader();
        this.shaker = new Shaker();
        this.panner = new Panner();

        this.instance = this;
    }

    private void followEntity(Entity e){
        xOffset = e.getX() + e.getWidth()/2 - screenWidth / 2;
        yOffset = e.getY() + e.getHeight()/2 - screenHeight / 2;
        stayInMap();
    }

    public static void duelCamera(Entity e1, Entity e2){
        instance.xOffset = (e1.getX() + e1.getWidth()/2 + e2.getX() + e2.getWidth()/2)/2 - instance.screenWidth/2;
        instance.yOffset = (e1.getY() + e1.getHeight()/2 + e2.getY() + e2.getHeight()/2)/2 - instance.screenHeight/2;
        instance.stayInMap();
    }


    private void stayInMap(){
        //Top Left
        xOffset = Math.max(xOffset, 0);
        yOffset = Math.max(yOffset, 0);

        //Bottom Right
        xOffset = Math.min(xOffset, game.getRoom().getWidth() * Tile.TILE_WIDTH - screenWidth);
        yOffset = Math.min(yOffset, game.getRoom().getHeight() * Tile.TILE_HEIGHT - screenHeight);
    }


    public int getXOffset() {
        return xOffset + shaker.getxOffset();
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public static void fade(boolean black){
        instance.getFader().fade(black);
    }

    public static void shake(int intensity, int period, int duration, boolean decay){
        instance.getShaker().activate(intensity, period, duration, decay);
    }

    public static void pan(int panX, int panY, int panDuration){
        panX = panX - instance.screenWidth/2;
        panY = panY - instance.screenHeight/2;

        panX = Math.max(Math.min(panX, instance.game.getRoom().getWidth() * Tile.TILE_WIDTH - instance.screenWidth), 0);
        panY = Math.max(Math.min(panY, instance.game.getRoom().getHeight() * Tile.TILE_HEIGHT - instance.screenHeight), 0);

        instance.lockToPlayer = false;
        instance.getPanner().pan(panX, panY, panDuration);
    }

    public static void returnToPlayer(int panDuration){
        pan(instance.game.getPlayer().getX() + instance.game.getPlayer().getWidth()/2,
                instance.game.getPlayer().getY() + instance.game.getPlayer().getHeight()/2, panDuration);

        Scheduler.getInstance().addTimedAction(panDuration, ()->instance.lockToPlayer = true);
    }

    public Fader getFader(){
        return fade;
    }

    public Shaker getShaker(){
        return shaker;
    }

    public Panner getPanner(){
        return panner;
    }

    public boolean isPanning(){
        return panner.isActive();
    }

    @Override
    public void tick() {
        if(lockToPlayer) followEntity(game.getPlayer());
        fade.tick();
        shaker.tick();
        panner.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        fade.render(graphics);
    }


    private class Fader {
        private float alpha = 0;
        private final float alphaRate = 0.1f;
        private boolean on = false;
        private boolean fading = false;

        public void tick(){
            if(fading){
                if(on){
                    alpha = Math.min(1.0f, alpha+alphaRate);
                }else{
                    alpha = Math.max(0.0f, alpha-alphaRate);
                }
            }
        }

        public void fade(boolean black){
            fading = true;
            on = black;
            Scheduler.getInstance().addTimedAction(10, ()->fading = false);
        }

        public void render(GameGraphics graphics){
            graphics.setG2Colour(Color.black);
            graphics.setG2Alpha(alpha);
            graphics.fillStaticRectangle(0, 0, screenWidth, screenHeight);
            graphics.resetG2Alpha();
        }
    }


    private class Shaker {

        private int xOffset;
        private int yOffset;
        private int intensity;
        private int period;
        private int duration;
        private boolean decay;

        private int timer;

        private boolean active;


        public void activate(int intensity, int period, int duration, boolean decay){

            this.intensity = intensity;
            this.period = period;
            this.duration = duration;
            this.decay = decay;

            active = true;

            Scheduler.getInstance().addTimedAction(duration, ()->{
                active = false;
                xOffset = 0;
                timer = 0;
            });
        }

        public void tick(){
            if(active){
                timer++;
                shake();
            }
        }

        private void shake(){
            xOffset = (int)((intensity*Math.sin(timer*(2*Math.PI / period)))/ (decay ? (Math.max(1, 0.1*timer)) : 1));
        }

        public int getxOffset(){
            return xOffset;
        }

    }

    private class Panner {

        /** inner class to handle panning the camera to a specific point manually.
         */

        private boolean active;
        private int panX, panY;
        private float panXSpeed, panYSpeed;
        private float smoothXOffset, smoothYOffset;

        public void pan(int panX, int panY, int panDuration){
            this.panX = panX;
            this.panY = panY;
            this.panXSpeed = (panX - xOffset) / (panDuration / 1.0f);
            this.panYSpeed = (panY - yOffset) / (panDuration / 1.0f);

            smoothXOffset = xOffset;
            smoothYOffset = yOffset;

            active = true;
            Scheduler.getInstance().addTimedAction(panDuration, this::finishPan);
        }

        public void tick(){
            if(active){
                panTick();
            }
        }


        public boolean isActive() {
            return active;
        }

        private void panTick(){
            smoothXOffset += panXSpeed;
            smoothYOffset += panYSpeed;

            xOffset = (int)smoothXOffset;
            yOffset = (int)smoothYOffset;
        }

        private void finishPan(){
            xOffset = panX;
            yOffset = panY;
            active = false;
        }
    }
}
