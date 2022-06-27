package com.jac.game.cutscene;

import com.jac.game.cutscene.events.TextboxEvent;
import com.jac.game.entities.CutsceneActor;
import com.jac.game.rooms.Room;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.ArrayList;

public class Cutscene {

    private Room room;
    private int eventIndex;
    private CutsceneEvent currentEvent;
    private ArrayList<CutsceneEvent> tempEvents;
    private ArrayList<CutsceneEvent> events;
    private ArrayList<CutsceneActor> actors;

    public Cutscene(Room room){
        this.room = room;
        events = new ArrayList<>();
        tempEvents = new ArrayList<>();
        actors = new ArrayList<>();
    }

    public Cutscene withEvent(CutsceneEvent event){
        events.add(event.inCutscene(this));
        return this;
    }

    public void play(){
        eventIndex = -1;
        if(events.size() > 0){
            nextEvent();
            room.startCutscene(this);
        }else{
            System.out.println("that aint no cutscene looool");
        }
    }

    public void tick(){
        currentEvent.tick();
        if(currentEvent.endCondition()){
            nextEvent();
        }
    }

    private void nextEvent(){
        eventIndex++;
        if(eventIndex < events.size()){
            events.get(eventIndex).play();
        }else{
            end();
        }
    }

    public void addResultText(String speaker, String... result){
        newTempEvent(new TextboxEvent(speaker, result));
    }

    public void newTempEvent(CutsceneEvent event){
        tempEvents.add(event.inCutscene(this));
        events.add(eventIndex + 1, event.inCutscene(this));
    }

    public void setCurrentEvent(CutsceneEvent event){
        this.currentEvent = event;
    }

    public void end(){
        for(CutsceneEvent event : tempEvents){
            events.remove(event);
        }
        tempEvents.clear();

        for(CutsceneActor actor : actors){
            room.removeEntity(actor);
        }
        actors.clear();
        room.endCutscene();
    }



    public Room getRoom(){
        return room;
    }

    public void addActor(CutsceneActor actor){
        actors.add(actor);
    }

    public CutsceneActor getActor(int actorID){
        return actors.get(actorID);
    }

    public boolean isFinished(){
        return eventIndex >= events.size();
    }
}

