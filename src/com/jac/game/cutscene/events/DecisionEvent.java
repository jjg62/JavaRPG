package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.ui.decision.Decision;
import com.jac.game.ui.decision.DecisionBox;

public class DecisionEvent extends TextboxEvent {

    private String question;
    private Decision[] decisions;

    public DecisionEvent(String question, String speaker, Decision... decisions){
        super(speaker, question);
        this.question = question;
        this.decisions = decisions;
    }

    @Override
    protected void onStart() {
        manager = cutscene.getRoom().getInteractingManager();
        manager.showTextbox(new DecisionBox(manager, speaker, question).withDecisions(decisions));
    }
}
