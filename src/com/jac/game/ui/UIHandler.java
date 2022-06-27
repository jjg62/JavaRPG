package com.jac.game.ui;

import com.jac.game.display.GameGraphics;
import com.jac.game.encounters.Encounter;
import com.jac.game.entities.structs.TickList;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.items.Consumable;
import com.jac.game.items.Item;
import com.jac.game.items.Skill;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.RoomList;
import com.jac.game.ui.hud.*;

import java.awt.image.BufferedImage;

public class UIHandler implements Ticking {

    private static UIHandler instance;
    private TickList<HUDElement> combatHUD;
    private TickList<HUDElement> peacefulHUD;
    private int width, height;
    private Game game;

    private Healthbar healthbar;
    private EquippedItemSlot itemSlot;
    private EquippedItemSlot skillSlot;
    private UseConsumable combatItemSlot;
    private UseSkill combatSkillSlot;

    public UIHandler(Game game, int width, int height){
        instance = this;
        this.game = game;
        this.width = width;
        this.height = height;
        combatHUD = new TickList<>();
        peacefulHUD = new TickList<>();

        healthbar = new Healthbar(game, width, height);
        combatHUD.add(healthbar);

        itemSlot = new EquippedItemSlot(game, width, height, width-96, "Consumable");
        peacefulHUD.add(itemSlot);

        skillSlot = new EquippedItemSlot(game, width, height, width-192, "Fluxtech");
        peacefulHUD.add(skillSlot);

        combatItemSlot = new UseConsumable(game, width, height, width-96);
        combatHUD.add(combatItemSlot);

        combatSkillSlot = new UseSkill(game, width, height, width - 192);
        combatHUD.add(combatSkillSlot);

        updateItemImage();
        updateSkillImage();
    }

    public void startEncounter(Encounter encounter){
        add(false, new BossHealthbar(game, encounter));
        healthbar.playOpening();


        Consumable equippedConsumable = GameInfo.getInstance().getInventory().getEquippedConsumable();
        Skill equppedSkill = GameInfo.getInstance().getInventory().getEquippedSkill();
        combatItemSlot.setItem(equippedConsumable);
        combatSkillSlot.setItem(equppedSkill);
    }

    public void endEncounter(Encounter encounter){
        healthbar.playEnding();
    }

    public void updateItemImage(){
        Consumable equipped = GameInfo.getInstance().getInventory().getEquippedConsumable();
        itemSlot.setItem(equipped);
    }

    public void updateSkillImage(){
        Skill equipped = GameInfo.getInstance().getInventory().getEquippedSkill();
        skillSlot.setItem(equipped);
    }

    public void activatePocketwatch(){
        add(true, new Pocketwatch(game, width, height));
        add(false, new Pocketwatch(game, width, height));
    }

    public void updatePlayer(){
        for(HUDElement e : peacefulHUD){
            e.updatePlayerInstance();
        }

        for(HUDElement e : combatHUD){
            e.updatePlayerInstance();
        }
    }

    public void add(boolean peaceful, HUDElement e){
       if(peaceful){
           peacefulHUD.add(e);
       }else{
           combatHUD.add(e);
       }
    }

    public void remove(boolean peaceful, HUDElement e){
        if(peaceful){
            peacefulHUD.remove(e);
        }else{
            combatHUD.remove(e);
        }
    }

    @Override
    public void tick() {
        combatHUD.tick();
        peacefulHUD.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        if(game.getRoom().isPeaceful()) {
            peacefulHUD.render(graphics);
        }else{
            combatHUD.render(graphics);
        }
        game.getRoom().getInteractingManager().render(graphics);
    }

    public static UIHandler getInstance(){
        return instance;
    }

}
