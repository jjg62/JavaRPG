package com.jac.game.ui.decision;

import com.jac.game.entities.structs.IAction;

public class Decision {

    private String text;
    private IAction result;

    public Decision(String text, IAction result){
        this.text = text;
        this.result = result;
    }

    public void decide(){
        result.run();
    }

    public String getText(){
        return text;
    }



}
