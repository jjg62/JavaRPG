package com.jac.game.items;

import com.jac.game.entities.structs.Scheduler;
import com.jac.game.ui.UIHandler;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<InventoryTab> tabs;
    private InventoryTab consumables;
    private InventoryTab materials;
    private InventoryTab skills;

    private Skill equippedSkill;
    private Consumable equippedConsumable;

    public Inventory(){
        consumables = new InventoryTab();
        materials = new InventoryTab();
        skills = new InventoryTab();

        tabs = new ArrayList<>();
        tabs.add(consumables);
        tabs.add(materials);
        tabs.add(skills);

        //addItem(ItemList.BRACELET);
    }

    public ArrayList<Item> getInventoryTab(int index){
        return tabs.get(index).getItems();
    }

    public Consumable getEquippedConsumable(){
        return equippedConsumable;
    }

    public void setEquippedConsumable(Consumable consumable){
        if(equippedConsumable == consumable){
            equippedConsumable = null;
        }else{
            this.equippedConsumable = consumable;
        }
        UIHandler.getInstance().updateItemImage();
    }

    public Skill getEquippedSkill(){
        return equippedSkill;
    }

    public void setEquippedSkill(Skill skill){
        if(equippedSkill == skill){
            equippedSkill = null;
        }else{
            this.equippedSkill = skill;
        }
        UIHandler.getInstance().updateSkillImage();
    }

    public void addItem(Item item){
        if(item instanceof Consumable){
            consumables.addItem(item);
        }else if(item instanceof Skill) {
            skills.addItem(item);
        }else{
            materials.addItem(item);
        }
    }

    public void removeItem(Item item){
        if(item instanceof Consumable){
            consumables.removeItem(item);
        }else if(item instanceof Skill) {
            skills.removeItem(item);
        }else{
            materials.removeItem(item);
        }
    }


    public boolean containsAtLeast(Item item, int quantity){
        if(item instanceof Consumable){
            return consumables.containsAtLeast(item, quantity);
        }else if(item instanceof Skill) {
            return skills.containsAtLeast(item, quantity);
        }else{
            return materials.containsAtLeast(item, quantity);
        }
    }

    public Consumable consume(){
        Consumable temp = equippedConsumable;
        if(equippedConsumable != null) {
            removeItem(equippedConsumable);
        }
        return temp;
    }
}
