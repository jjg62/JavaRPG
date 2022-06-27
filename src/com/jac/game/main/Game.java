package com.jac.game.main;

import com.jac.game.audio.Music;
import com.jac.game.audio.SoundHandler;
import com.jac.game.control.KeyListener;
import com.jac.game.cutscene.CutsceneList;
import com.jac.game.display.Camera;
import com.jac.game.display.Display;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Player;
import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.Room;
import com.jac.game.rooms.RoomList;
import com.jac.game.tiles.TileList;
import com.jac.game.ui.UIHandler;

import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    //Main game thread
    private Thread gameThread;
    private boolean running;

    //Dimensions of Window
    public static final int width = 768;
    public static final int height = 512;

    //Window and Canvas
    private Display display;

    //KeyListener
    private KeyListener keyListener;

    //Rooms
    private Room room;

    //Scheduler
    private Scheduler scheduler = Scheduler.getInstance();

    //Camera
    private Camera cam;

    //UI
    private UIHandler uiHandler;

    //Audio
    private SoundHandler audio;

    //PlayerInfo
    private GameInfo gameInfo;

    public Game(){
        this.keyListener = new KeyListener();
        gameThread = new Thread(this);
    }

    public void run(){
        display = new Display(width, height, "Game");

        initGame();
        gameLoop();
    }

    //For things that need initiating before the gameloop starts.
    private void initGame(){
        display.getFrame().addKeyListener(keyListener);
        audio = new SoundHandler();
        gameInfo = new GameInfo();

        TileList.initTiles();
        RoomList.initRooms(this);
        CutsceneList.initCutscenes();
        new QuestlineList();


        room = RoomList.room_haven;
        cam = new Camera(this, width, height);
        uiHandler = new UIHandler(this, width, height);


        //Temp - skip tutorial
        //GameInfo.getInstance().tutorialDone = true;
        //GameInfo.getInstance().activatePocketwatch();
        //GameInfo.getInstance().advanceTime();

    }

    private void gameLoop(){

        long lastTime = System.nanoTime();
        long now;
        double delta = 0; //Amount of ticks passed
        int fps = 60;
        long timer = 0;
        int ticks = 0;

        double timePerTick = 1000000000 / fps; //Amount of nanoseconds in one frame

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime)/timePerTick; //Calculate amount of ticks since last iteration
            timer += (now - lastTime);
            lastTime = now;

            if(delta >= 1){ //When a full tick is reached, run a tick and render
                delta--; //And reset delta

                ticks++;
                tick();
                render();
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }

        }
        //If running gets set to false, make sure it has been done so correctly:
        stop();
    }


    private void tick(){
        cam.tick();
        scheduler.tick();
        room.tick();
        uiHandler.tick();
    }

    private void render(){
        //Get Buffer Strategy
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3); //Make a 3-layered Buffer Strat if there isnt one already
            return;
        }

        //Init Graphics
        //Graphics
        GameGraphics graphics = new GameGraphics(bs, cam, display);
        graphics.g.clearRect(0, 0, width, height);

        //Start Drawing

        room.render(graphics);
        uiHandler.render(graphics);
        cam.render(graphics);

        //End of Drawing
        bs.show();
        graphics.dispose();
    }


    /*
    Thread Managing Methods
    */

    public synchronized void start(){
        if(running) return; //Does nothing if game is already running.

        running = true;

        //Start all threads
        gameThread.start();
    }

    public synchronized void stop(){
        if(!running) return; //Does nothing if game is not running.

        running = false;

        try{
            //Join all threads
            gameThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public Room getRoom() {
        return room;
    }


    public void changeRoom(Room room){
        //Collect player info
        int health = this.room.getPlayer().getHealth();

        //Change Music
        SoundHandler.changeRoomMusic(room.getMusic());

        //Change Room
        this.room = room;
        uiHandler.updatePlayer();

        //Preserve information
        room.getPlayer().setHealth(health);
    }


    public Player getPlayer(){
        return room.getPlayer();
    }
}
