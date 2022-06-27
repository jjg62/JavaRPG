package com.jac.game.ui.decision;

import com.jac.game.control.Controller;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.ui.Textbox;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class DecisionBox extends Textbox {

    private int decisionSpacing = 96;
    private boolean changable = true;
    private int selectedOption;
    private ArrayList<Decision> decisions;

    public DecisionBox(InteractingManager talkingManager, String speaker, String question) {
        super(talkingManager, speaker, question, talkingManager::finishInteracting);
        this.decisions = new ArrayList<>();
    }

    public DecisionBox withDecision(Decision decision){
        this.decisions.add(decision);
        return this;
    }

    public DecisionBox withDecisions(Decision... decisions){
        Collections.addAll(this.decisions, decisions);
        return this;
    }

    @Override
    public void tick(){
        super.tick();
        checkChangeDecision();
    }

    @Override
    public void render(GameGraphics graphics) {
        super.render(graphics);
        renderDecisions(graphics);
    }

    @Override
    protected void close(){
        super.close();
        decisions.get(selectedOption).decide();
    }

    private void renderDecisions(GameGraphics graphics){
        for(int i = 0; i < decisions.size(); i++){
            if(i == selectedOption) {
                graphics.setGColour(Color.red);
                graphics.drawString(">", x + 96 + i * decisionSpacing, y + 64);
            }
            graphics.drawString(decisions.get(i).getText(), x + 108 + i * decisionSpacing, y + 64);
            graphics.setGColour(Color.black);
        }
    }

    private void checkChangeDecision(){
        if(changable){
            if(Controller.right()){
                change(1);
            }else if(Controller.left()){
                change(-1);
            }
        }

    }

    private void change(int amount){
        selectedOption += amount;
        selectedOption = Math.floorMod(selectedOption, decisions.size());
        changable = false;
        Scheduler.getInstance().addTimedAction(12, ()->changable = true);
    }

}
