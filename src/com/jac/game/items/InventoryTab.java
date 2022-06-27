package com.jac.game.items;

import com.jac.game.abilities.ConditionAbility;
import com.jac.game.main.GameInfo;
import com.jac.game.ui.UIHandler;

import java.util.ArrayList;

public class InventoryTab {

    private ArrayList<Item> items;

    public InventoryTab(){
        items = new ArrayList<>();
    }

    /** Check if the player has at least 1 item with a certain name
     * @param item
     * @return The item, or null if not found.
     */
    public Item contains(Item item){
        for(Item i : items){
            if(i.getName().equals(item.getName())){
                return i;
            }
        }
        return null;
    }

    /** Check if the player has at least a certain number of items
     *
     */
    public boolean containsAtLeast(Item item, int quantity){
        Item itemFound = contains(item);
        if(itemFound == null){
            return false;
        }else{
            return itemFound.getQuantity() >= quantity;
        }
    }

    /** Add an item to the inventory, or if the player already owns one of the same name, increment its quantity
     * @param item
     */
    public void addItem(Item item){
        Item foundItem = contains(item);
        if(foundItem != null){
            foundItem.incrementQuantity();
        }else{
            System.out.println(item.getName());
            items.add(item.newStack());
        }
    }

    /** Decrement the quantity of an item, and if it has been depleted, remove it from the inventory.
     * @param item the item to remove
     */
    public void removeItem(Item item){
        Item foundItem = contains(item);
        if(foundItem != null){
            foundItem.decrementQuantity();
            if(foundItem.getQuantity() <= 0){
                items.remove(foundItem);
                if(GameInfo.getInstance().getInventory().getEquippedConsumable() == foundItem){
                    GameInfo.getInstance().getInventory().setEquippedConsumable(null);
                }
            }
        }
    }

    public ArrayList<Item> getItems(){
        return items;
    }


}
