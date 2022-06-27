package com.jac.game.rooms;

import com.jac.game.control.Controller;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.Interactable;
import com.jac.game.entities.structs.IAction;
import com.jac.game.entities.structs.IterableChangingList;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.TickList;
import com.jac.game.textures.Animation;
import com.jac.game.ui.MultiTextbox;
import com.jac.game.ui.Textbox;
import com.jac.game.utils.FileUtils;

public class InteractingManager {

    private Room room;
    private boolean canStartInteracting = true;
    private boolean currentlyTalking = false;
    private Interactable interactTarget;
    private IterableChangingList<Interactable> interactables;
    private Textbox activeTextbox;

    private Animation indicatorAnimation = new Animation(false, 60, FileUtils.loadAnimationFrames("/textures/ui/Interact.png", 16, 16));

    public InteractingManager(Room room){
        this.room = room;
        this.interactables = new TickList<>();
    }

    public void add(Interactable i){
        interactables.add(i);
    }

    public void remove(Interactable i){
        if(interactables.contains(i)){
            interactables.remove(i);
        }else if(interactables.getAdding().contains(i)){
            interactables.getAdding().remove(i);
        }

    }

    private void updateInteractTarget(){
        if(!canStartInteracting || !room.isPeaceful() || room.getPlayer().frozen()){
            interactTarget = null;
            return;
        }
        int smallest = 999999999;
        for(Interactable i : interactables){
            if(i.isInRange()){
                if(i.getDistanceToPlayer() < smallest) {
                    if(interactTarget != null) interactTarget.setTargeted(false);
                    interactTarget = i;
                    smallest = i.getDistanceToPlayer();
                    interactTarget.setTargeted(true);
                }
            }
        }
    }

    private void checkInteract(){
        if(interactTarget != null && Controller.talk()){
            startInteracting();
        }
    }

    private void startInteracting(){

        interactTarget.interaction(this);

        //Disable interact targeting
        interactTarget.setTargeted(false);
        interactTarget = null;
        canStartInteracting = false;
    }

    public void finishInteracting(){
        currentlyTalking = false;
        canStartInteracting = false;
        Scheduler.getInstance().addTimedAction(20, () -> {
            canStartInteracting = true;
        });
    }

    public void tick(){
        updateInteractTarget();
        checkInteract();
        interactables.updateContents();
        if(currentlyTalking) activeTextbox.tick();
    }

    public void showTextbox(Textbox tb){
        this.activeTextbox = tb;
        currentlyTalking = true;
        activeTextbox.open();
    }

    public void showTextboxSequence(IAction finishTextbox, String speaker, String... textList){
        MultiTextbox tb = new MultiTextbox(this, finishTextbox, speaker, textList);
        tb.start();
    }

    public void showTextboxSequence(String speaker, String... textList){
        MultiTextbox tb = new MultiTextbox(this, ()->{}, speaker, textList);
        tb.start();
    }

    public void render(GameGraphics graphics){
        if(currentlyTalking) activeTextbox.render(graphics);
        if(interactTarget != null){
            graphics.draw(indicatorAnimation.getFrame(), interactTarget.getX() + (interactTarget.getWidth() - 16)/2, interactTarget.getY() - 16, 16, 16);
        }
    }

    public boolean isCurrentlyTalking() {
        return currentlyTalking;
    }

    public void setCurrentlyTalking(boolean currentlyTalking) {
        this.currentlyTalking = currentlyTalking;
    }

    public void resetInteractTarget(){
        interactTarget = null;
        updateInteractTarget();
    }
}
