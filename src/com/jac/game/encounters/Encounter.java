package com.jac.game.encounters;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.audio.Music;
import com.jac.game.audio.SoundHandler;
import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.CutsceneList;
import com.jac.game.cutscene.events.*;
import com.jac.game.cutscene.events.actors.MoveActorEvent;
import com.jac.game.display.Camera;
import com.jac.game.entities.Mob;
import com.jac.game.entities.enemies.bh.BHSequence;
import com.jac.game.entities.interact.Door;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.Room;
import com.jac.game.rooms.RoomList;
import com.jac.game.ui.UIHandler;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Encounter {

    protected Room room;
    private int startingX, startingY;

    private ArrayList<Phase> phases;
    private int phaseNumber = 0;
    private int currentPhaseProgress;
    private int totalHealth;
    private Phase currentPhase;
    private String name;

    public Encounter(Room room, int startingX, int startingY, String name){
        this.room = room;
        this.phases = new ArrayList<>();
        this.startingX = startingX;
        this.startingY = startingY;
        this.name = name;
    }

    public Encounter withPhase(Phase phase){
        this.phases.add(phase.inEncounter(this));
        if (phase instanceof MobPhase){
            for(Mob mob : ((MobPhase)phase).getMobs()) {
                totalHealth += mob.getHealth();
            }
        }
        return this;
    }


    public void start(){
        room.setPeaceful(false);

        room.getPlayer().refreshConsume();
        room.getPlayer().updateSkill();
        room.getPlayer().setMomentum(0);

        phaseNumber = currentPhaseProgress = 0;
        currentPhase = phases.get(0);
        updateTotalHealth();
        UIHandler.getInstance().startEncounter(this);
        SoundHandler.changeMusic(Music.BATTLE);
        room.playClash();
        Camera.pan(startingX + 48, startingY + 48, 50);

        Scheduler.getInstance().addTimedAction(120, ()->currentPhase.start(startingX, startingY));
    }

    public void end(int endingX, int endingY){
        endCutscene(endingX, endingY)
                .withEvent(new PanCameraEvent(40))
                .withEvent(new CustomEvent(()->UIHandler.getInstance().endEncounter(this)))
                .withEvent(new TextboxEvent("You win!", "You earned 400 Coins."))
                .withEvent(new CustomEvent(()-> Camera.fade(true)))
                .withEvent(new Pause(60))
                .withEvent(new CustomEvent(()->{
                    Camera.fade(false);
                    room.setPeaceful(true);
                    makeChangesToRoom();
                    GameInfo.getInstance().advanceTime();
                    SoundHandler.resumeRoomMusic();
                })).play();
    }

    protected abstract Cutscene endCutscene(int endingX, int endingY);

    protected abstract void makeChangesToRoom();

    public void progressPhase(int endingX, int endingY){
        currentPhaseProgress++;
        if(currentPhaseProgress >= phases.get(phaseNumber).getProgressRequired()) {
            phaseNumber++;
            if (phaseNumber >= phases.size()) {
                end(endingX, endingY);
            } else {
                currentPhase = phases.get(phaseNumber);
                currentPhase.start(endingX, endingY);
            }
        }
    }

    public int getTotalHealth(){
        return totalHealth;
    }

    public Phase getCurrentPhase(){
        return currentPhase;
    }

    public void updateTotalHealth(){
        totalHealth = 0;
        for(Phase phase : phases){
            if(phase instanceof MobPhase){
                for(Mob mob : ((MobPhase)phase).getMobs()) {
                    totalHealth += mob.getHealth();
                }
            }
        }
    }

    public String getName(){
        return name;
    }
}
