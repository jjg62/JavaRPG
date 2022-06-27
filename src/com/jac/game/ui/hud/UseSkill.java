package com.jac.game.ui.hud;

import com.jac.game.items.Skill;
import com.jac.game.main.Game;
import com.jac.game.utils.FileUtils;

public class UseSkill extends UseConsumable{

    public UseSkill(Game game, int width, int height, int x) {
        super(game, width, height, x);
        indicator = FileUtils.loadImage("/textures/ui/up_arrow.png");
    }

    public void setItem(Skill item){
        if(item != null){
            this.itemReady = item.getIcon();
            this.itemUsed = item.getIcon();
            ability = player.getSkill();
            this.empty = false;
        }else{
            this.itemReady = IMG_NONE;
            this.itemUsed = IMG_NONE;
            this.empty = true;
        }
    }
}
