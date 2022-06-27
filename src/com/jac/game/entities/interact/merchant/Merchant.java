package com.jac.game.entities.interact.merchant;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.interact.debug.Talking;
import com.jac.game.items.InventoryTab;
import com.jac.game.items.ItemList;
import com.jac.game.items.ShopInventory;
import com.jac.game.main.Game;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.rooms.Room;
import com.jac.game.ui.decision.MerchantBox;

import java.util.ArrayList;

public class Merchant extends Talking {

    private ArrayList<Purchase> purchases;

    private boolean showingWindow;
    private ShopInventory window;
    private InventoryTab inventory;

    private String[] openingText = {"I have the finest wares in the land"};
    private String[] closingText = {"bye come again soon"};

    public Merchant(Room room, int width, int height) {
        super(room, width, height);
        purchases = new ArrayList<>();
        inventory = new InventoryTab();
        inventory.addItem(ItemList.HEALTH_POTION);
        inventory.addItem(ItemList.HEALTH_POTION);
        inventory.addItem(ItemList.SPEED_POTION);

        window = new ShopInventory(this);
    }

    public Merchant withPurchase(String name, int cost){
        this.purchases.add(new Purchase(this, name, cost, () -> System.out.println(name)));
        return this;
    }

    @Override
    public void tick(){
        super.tick();
        if(showingWindow){
            window.tick();
        }
    }

    @Override
    public void render(GameGraphics graphics){
        super.render(graphics);
        if(showingWindow){
            window.render(graphics);
        }
    }

    @Override
    public void interaction(InteractingManager manager){
        manager.showTextboxSequence(()->openWindow(manager), "Merchant", openingText);
    }

    public void openWindow(InteractingManager manager){
        window.open(manager);
        showingWindow = true;
        this.room.setInInventory(true);
    }

    public void close(InteractingManager manager){
        showingWindow = false;
        manager.showTextboxSequence(()->{}, "Merchant", closingText);
        this.room.setInInventory(false);
    }

    public InventoryTab getInventory(){
        return inventory;
    }
}