package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.display.Camera;

public class PanCameraEvent extends CutsceneEvent {

    private boolean toPlayer = true;
    private int panX, panY;
    private int panSpeed;

    public PanCameraEvent(int panSpeed){
        this.panSpeed = panSpeed;
    }

    public PanCameraEvent(int panX, int panY, int panSpeed){
        this.panX = panX;
        this.panY = panY;
        this.toPlayer = false;
        this.panSpeed = panSpeed;
    }

    @Override
    protected void onStart() {
        if(toPlayer){
            Camera.returnToPlayer(panSpeed);
        }else{
            Camera.pan(panX, panY, panSpeed);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return !Camera.instance.isPanning();
    }
}
