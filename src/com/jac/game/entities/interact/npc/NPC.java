package com.jac.game.entities.interact.npc;

import com.jac.game.cutscene.Cutscene;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.Interactable;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.textures.TileTextures;
import com.jac.game.utils.FileUtils;

import java.util.HashMap;

public class NPC extends Interactable {

    private HashMap<Integer, NPCState> states;
    private NPCState state;
    private NPCState nextState;
    private Questline questline;

    private Animation animation;

    public NPC(Questline questline, int width, int height, Animation animation) {
        super(null, width, height);
        this.questline = questline;
        questline.addSubscriber(this);
        states = new HashMap<>();
        setBoundingBox(width/4, height/2, width/2, height/2);

        this.animation = animation;
    }

    public NPC withState(int questState, int spawnX, int spawnY, Cutscene interaction){
        this.states.put(questState, new NPCState(this, spawnX, spawnY, interaction));
        return this;
    }

    public NPC withOneTimeState(int questState, int spawnX, int spawnY, Cutscene interaction, Cutscene afterInteraction){
        this.states.put(questState, new NPCStateOneTime(this, questState, new NPCState(this, spawnX, spawnY, interaction), new NPCState(this, spawnX, spawnY, afterInteraction)));
        return this;
    }


    public NPC withState(int questState, NPCState state){
        this.states.put(questState, state.withNPC(this));
        return this;
    }

    public void notify(int gameState){
        changeNPCState(gameState);
    }

    private void changeNPCState(int questState){
        this.nextState = states.get(questState);
    }

    public void updateState(){
        state = nextState;
        if(room != null){
            room.getInteractingManager().remove(this);
            room.removeEntity(this);
        }
        if(state != null){
            state.init();
        }
    }

    @Override
    public void interaction(InteractingManager manager) {
        state.getInteraction().play();
    }

    @Override
    public void render(GameGraphics graphics) {
        renderShadow(graphics);
        animation.tick();
        graphics.draw(animation.getFrame(), x, y, width, height);
    }

    public Questline getQuestline(){
        return questline;
    }
}
