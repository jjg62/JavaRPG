package com.jac.game.ui.clash;

import com.jac.game.audio.Sound;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.awt.*;

public class Clash implements Ticking {

    private static Sound gong = new Sound("gong.wav");

    private Animation lAnimation;
    private Animation rAnimation;

    private Animation yu;
    private Animation opponent;
    private int progress = 0;

    public Clash(){
        lAnimation = new Animation(false, 12, FileUtils.loadAnimationFrames("/textures/ui/clash/clash_blankL.png", 404, 128));
        rAnimation = new Animation(false, 12, FileUtils.loadAnimationFrames("/textures/ui/clash/clash_blankR.png", 404, 128));

        opponent = new Animation(false, 12, FileUtils.loadAnimationFrames("/textures/ui/clash/clash_dweller.png", 128, 64));
        yu = new Animation(false, 12, FileUtils.loadAnimationFrames("/textures/ui/clash/clash_yu.png", 128, 64));
    }

    public void reset(){
        progress = 0;
        lAnimation.reset();
        rAnimation.reset();
        opponent.reset();
        yu.reset();
        gong.play();
    }

    @Override
    public void tick() {
        progress = Math.min(404, (int) ((progress + 1) * 1.4));
        if(progress >= 404) {
            lAnimation.tick();
            rAnimation.tick();
            opponent.tick();
            yu.tick();
        }
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.drawStatic(lAnimation.getFrame(), -404 + progress, 192, 404, 128);
        graphics.drawStatic(yu.getFrame(), -300 + progress, 192, 256, 128);
        //graphics.drawString("YUU VS", -128 + progress, 336, 14, Color.white);

        graphics.drawStatic(rAnimation.getFrame(), 767 - progress, 192, 404, 128);
        graphics.drawStatic(opponent.getFrame(), 800 - progress, 192, 256, 128);
        //graphics.drawString("LABYRINTH DWELLER", 864 - progress, 336, 14, Color.white);
    }
}
