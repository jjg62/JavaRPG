package com.jac.game.entities.interact.npc;

import com.jac.game.cutscene.Cutscene;

public class NPCStateOneTime extends NPCState{

    private int usualProgress;

    private NPCState beforeState;
    private NPCState afterState;

    public NPCStateOneTime(NPC npc, int usualProgress, NPCState beforeState, NPCState afterState) {
        super(npc, 0, 0, beforeState.getInteraction());
        this.beforeState = beforeState;
        this.afterState = afterState;
        this.usualProgress = usualProgress;
    }

    @Override
    public void init(){
        boolean done = npc.getQuestline().getProgress() != usualProgress;
        if(done){
            afterState.init();
        }else{
            beforeState.init();
        }
    }

    @Override
    public Cutscene getInteraction(){
        boolean done = npc.getQuestline().getProgress() != usualProgress;
        if(done){
            return afterState.getInteraction();
        }else{
            return beforeState.getInteraction();
        }
    }

    @Override
    public NPCState withNPC(NPC npc){
        this.npc = npc;
        if(beforeState != null) this.beforeState = beforeState.withNPC(npc);
        if(afterState != null) this.afterState = afterState.withNPC(npc);
        return this;
    }
}
