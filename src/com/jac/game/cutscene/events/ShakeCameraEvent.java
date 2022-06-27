package com.jac.game.cutscene.events;

import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.display.Camera;

public class ShakeCameraEvent extends CutsceneEvent {

    private int intensity, period, duration;
    private boolean decay;

    public ShakeCameraEvent(int intensity, int period, int duration, boolean decay){
        this.intensity = intensity;
        this.period = period;
        this.duration = duration;
        this.decay = decay;
    }

    @Override
    protected void onStart() {
        Camera.shake(intensity, period ,duration, decay);
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return true;
    }

}
