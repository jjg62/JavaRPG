package com.jac.game.entities.interact.npc;

import java.util.ArrayList;

public class Questline {

    private int progress = -1;
    private ArrayList<NPC> subscribers;

    public Questline(){
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(NPC subscriber){
        subscribers.add(subscriber);
    }

    public void progressQuestline(){
        progress++;
        notifySubscribers();
    }

    public void progressQuestlineFrom(int from){
        if(progress == from){
            progressQuestline();
        }
    }

    private void notifySubscribers(){
        for(NPC npc : subscribers){
            npc.notify(progress);
        }
    }

    public int getProgress(){
        return progress;
    }

    public void start(){
        progress = 0;
        notifySubscribers();
    }
}
