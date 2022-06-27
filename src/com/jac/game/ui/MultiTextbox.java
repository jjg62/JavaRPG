package com.jac.game.ui;

import com.jac.game.entities.structs.IAction;
import com.jac.game.rooms.InteractingManager;

import java.util.ArrayList;

public class MultiTextbox {

    private Textbox[] textboxes;
    private String speaker;
    private InteractingManager interactingManager;
    private int index = 0;

    public MultiTextbox(InteractingManager interactingManager, IAction finishTextAction, String speaker, String... textList){
        this.interactingManager = interactingManager;
        this.speaker = speaker;
        initTextboxes(textList, finishTextAction);
    }

    private void initTextboxes(String[] textList, IAction finishTextAction){
        ArrayList<Textbox> textboxesBuilder = new ArrayList<>();
        for(int i = 0; i < textList.length - 1; i++){
            textboxesBuilder.add(new Textbox(interactingManager, speaker, textList[i], () -> showNext()));
        }
        //Final textbox
        textboxesBuilder.add(new Textbox(interactingManager, speaker, textList[textList.length - 1], () -> {
            interactingManager.finishInteracting();
            finishTextAction.run();
        }));
        textboxes = textboxesBuilder.toArray(new Textbox[textList.length]);
    }

    private void showNext(){
        index++;
        interactingManager.showTextbox(textboxes[index]);
    }

    public void start(){
        interactingManager.showTextbox(textboxes[0]);
    }
}
