package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.rooms.InteractingManager;

public class TextboxEvent extends CutsceneEvent {

    protected String speaker;
    private String[] text;
    protected InteractingManager manager;

    public TextboxEvent(String speaker, String... text){
        this.speaker = speaker;
        this.text = text;
    }

    @Override
    protected void onStart() {
        manager = cutscene.getRoom().getInteractingManager();
        manager.showTextboxSequence(()->{}, speaker, text);
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return !manager.isCurrentlyTalking();
    }
}
