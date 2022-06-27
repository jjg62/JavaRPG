package com.jac.game.rooms;

import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.audio.Music;
import com.jac.game.cutscene.Cutscene;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Player;
import com.jac.game.entities.interact.npc.NPC;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.TickList;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.fx.VisualEffect;
import com.jac.game.items.InventoryDisplay;
import com.jac.game.main.Game;
import com.jac.game.rooms.utils.RoomData;
import com.jac.game.rooms.utils.RoomLoader;
import com.jac.game.tiles.Tile;
import com.jac.game.ui.clash.Clash;

import java.awt.*;
import java.util.Comparator;

public class Room {

    private Game game;
    private String path;
    private boolean peaceful;
    private int width, height;
    private int spawnX, spawnY;
    private Tile[][] tiles;
    private TickList<Ticking> objsToTick;
    private TickList<Entity> entities;

    private TickList<Ticking> activeComponents;
    private TickList<AttackComponent> componentsTop;
    private TickList<AttackComponent> componentsBottom;


    private TickList<VisualEffect> effects;
    private Clash clash;
    private InventoryDisplay inventory;
    private Player player;
    private InteractingManager interactingManager;
    private Music music;

    private boolean inInventory;

    private Cutscene ongoingCutscene;
    private boolean inCutscene;

    private Comparator<Entity> renderSorter = (o1, o2) -> o1.getHurtbox().getY() < o2.getHurtbox().getY() ? -1 : 1;

    public Room(Game game, String path, boolean peaceful){
        init(path);
        this.path = path;
        this.game = game;
        this.peaceful = peaceful;

        objsToTick = new TickList<>();
        activeComponents = new TickList<>();
        componentsBottom = new TickList<>();
        componentsTop = new TickList<>();
        effects = new TickList<>();
        entities = new TickList<>();
        clash = new Clash();

        interactingManager = new InteractingManager(this);
        inventory = new InventoryDisplay(this);

        activeComponents.add(componentsBottom);
        activeComponents.add(componentsTop);

        objsToTick.add(entities);

        player = new Player(this);
        player.spawn(256, 600);

    }

    /** Read data from text file and initialise the room
     * @param path The path of the file to read from
     */
    private void init(String path){
        RoomData roomData = RoomLoader.loadRoom(this, path);
        width = roomData.getWidth();
        height = roomData.getHeight();
        spawnX = roomData.getSpawnX();
        spawnY = roomData.getSpawnY();
        tiles = roomData.generateTiles();

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tiles[i][j].updateIcon(i, j);
            }
        }

    }

    public void tick(){
        interactingManager.tick();
        activeComponents.tick();
        objsToTick.tick();
        effects.tick();
        inventory.tick();

        if(inCutscene) ongoingCutscene.tick();

        entities.sort(renderSorter);
    }

    public void render(GameGraphics graphics){
        graphics.setG2Colour(Color.BLACK);
        graphics.fillStaticRectangle(0, 0, Game.width, Game.height);
        renderTiles(graphics);
        componentsBottom.render(graphics);
        objsToTick.render(graphics);
        componentsTop.render(graphics);
        effects.render(graphics);
        inventory.render(graphics);
    }


    /** Draw the image for each tile object in the room.
     */
    private void renderTiles(GameGraphics graphics){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tiles[i][j].render(graphics, i, j);
            }
        }
    }

    public Tile getTileT(int tX, int tY){
        return tiles[tX][tY];
    }

    public Tile getTile(int x, int y){
        return tiles[x/Tile.TILE_WIDTH][y/Tile.TILE_HEIGHT];
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public void removeEntity(Entity e){
        if(entities.contains(e)){
            entities.remove(e);
        }else if(entities.getAdding().contains(e)){
            entities.getAdding().remove(e);
        }
    }

    public void addComponent(AttackComponent c, boolean top){
        (top ? componentsTop : componentsBottom).add(c);
    }

    public void removeComponent(AttackComponent c, boolean top){
        TickList<AttackComponent> listToCheck = (top ? componentsTop : componentsBottom);
        if(listToCheck.contains(c)){
            listToCheck.remove(c);
        }
    }

    public void addEffect(VisualEffect v){
        effects.add(v);
    }

    public void removeEffect(VisualEffect v){
        if(effects.contains(v)){
            effects.remove(v);
        }else if(effects.getAdding().contains(v)){
            effects.getAdding().remove(v);
        }
        System.out.println(effects.size());
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TickList<Ticking> getTickList() {
        return objsToTick;
    }

    public InteractingManager getInteractingManager() {
        return interactingManager;
    }

    public TickList<VisualEffect> getEffects() {
        return effects;
    }

    public TickList<Entity> getEntities(){
        return entities;
    }

    public void changeRoom(Room room){
        inventory.setActive(false);
        game.changeRoom(room);
    }

    public String toString(){
        return path;
    }

    public void movePlayer(int x, int y){
        player.setX(x);
        player.setY(y);
        player.setXSpeed(0);
        player.setYSpeed(0);
    }

    public boolean isPeaceful() {
        return peaceful;
    }

    public boolean isInCutscene(){
        return inCutscene;
    }

    public Music getMusic(){
        return music;
    }

    public void setMusic(Music music){
        this.music = music;
    }

    public void setPeaceful(boolean peaceful){
        this.peaceful = peaceful;
    }

    public void startCutscene(Cutscene cutscene){
        if(ongoingCutscene != null){
            if(!ongoingCutscene.isFinished()) ongoingCutscene.end();
        }
        inCutscene = true;
        this.ongoingCutscene = cutscene;
    }

    public void endCutscene(){
        inCutscene = false;
        this.ongoingCutscene = null;
    }

    public Game getGame(){
        return game;
    }

    public InventoryDisplay getInventoryDisplay(){
        return inventory;
    }

    public void setInInventory(boolean inInventory){
        this.inInventory = inInventory;
    }

    public boolean isInInventory(){
        return inInventory;
    }

    public void playClash(){
        clash.reset();
        objsToTick.add(clash);
        Scheduler.getInstance().addTimedAction(120, ()->objsToTick.remove(clash));
    }

}
