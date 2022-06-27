package com.jac.game.entities.interact.npc;

import com.jac.game.cutscene.Cutscene;

public abstract class NPCStateRequirement extends NPCState{

    private NPCState trueState;
    private NPCState falseState;

    public NPCStateRequirement(NPC npc, NPCState trueState, NPCState falseState) {
        super(npc, 0, 0, falseState.getInteraction());
        this.trueState = trueState;
        this.falseState = falseState;
    }

    @Override
    public void init(){
        if(requirement()){
            trueState.init();
        }else{
            falseState.init();
        }
    }

    @Override
    public Cutscene getInteraction(){
        if(requirement()){
            return trueState.getInteraction();
        }else{
            return falseState.getInteraction();
        }
    }

    @Override
    public NPCState withNPC(NPC npc){
        this.npc = npc;
        this.trueState = trueState.withNPC(npc);
        this.falseState = falseState.withNPC(npc);
        return this;
    }

    protected abstract boolean requirement();
}
