package com.jac.game.items;

import com.jac.game.entities.interact.merchant.Merchant;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.ui.decision.Decision;
import com.jac.game.ui.decision.DecisionBox;

import java.util.ArrayList;

public class ShopInventory extends InventoryDisplay{

    private InteractingManager manager;
    private Merchant merchant;

    private boolean confirming;

    public ShopInventory(Merchant merchant) {
        super(merchant.getRoom());
        this.merchant = merchant;
        tabCount = 1;
    }

    public void tick(){
        if(!confirming) {
            super.tick();
            if (!active) {
                merchant.close(manager);
            }
        }
    }

    public void open(InteractingManager manager){
        this.active = true;
        selectedIndex = 0;
        confirming = false;
        loadInventory();
        canToggleEquip = false;
        Scheduler.getInstance().addTimedAction(30, ()->canToggleEquip = true);
        this.manager = manager;
    }

    @Override
    protected void attemptEquip(){
        //Buy instead of equip
        if(currentlyOpenTab.size() == 0) return;
        confirming = true;
        manager.showTextbox(new DecisionBox(manager, "Info", "Are you sure you want to purchase this?")
                .withDecisions(new Decision("Yes", ()->{
                    buy();
                    open(manager);
                }), new Decision("No", ()->{
                    System.out.println("no");
                    open(manager);
                })));
    }

    private void buy(){
        Item purchasing = merchant.getInventory().getItems().get(selectedIndex);

        merchant.getInventory().removeItem(purchasing);
        GameInfo.getInstance().getInventory().addItem(purchasing.newStack());
    }

    @Override
    protected void loadInventory() {
        currentlyOpenTab = merchant.getInventory().getItems();
    }
}
