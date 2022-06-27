package com.jac.game.items;

import com.jac.game.control.Controller;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.entities.structs.Ticking;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.Room;
import com.jac.game.ui.UIHandler;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryDisplay implements Ticking {

    protected Room room;
    private static final int LINE_SPACING = 14;

    protected boolean active = false;
    private boolean canToggleDisplay = true;
    protected boolean canToggleEquip = true;

    private int scrollPos = 0;
    private boolean canScrollUp = true;
    private boolean canScrollDown = true;
    protected int selectedIndex;

    protected int tabCount = 3;
    private int tabIndex = 0;
    private boolean canScrollRight = true;
    private boolean canScrollLeft = true;
    protected ArrayList<Item> currentlyOpenTab;

    public InventoryDisplay(Room room){
        this.room = room;
    }

    @Override
    public void tick() {
        //Scroll
        if(active){
            if(Controller.up() && canScrollUp){
                selectedIndex = Math.max(selectedIndex-1, 0);
                canScrollUp = false;
                Scheduler.getInstance().addTimedAction(10, ()->canScrollUp = true);
            }else if(Controller.down() && canScrollDown){
                selectedIndex = Math.min(selectedIndex+1, Math.max(currentlyOpenTab.size() - 1, 0));
                canScrollDown = false;
                Scheduler.getInstance().addTimedAction(10, ()->canScrollDown = true);
            }else if(Controller.right() && canScrollRight) {
                tabIndex = Math.floorMod(tabIndex + 1, tabCount);
                loadInventory();
                canScrollRight= false;
                Scheduler.getInstance().addTimedAction(30, ()->canScrollRight = true);
            }else if(Controller.left() && canScrollLeft) {
                tabIndex = Math.floorMod(tabIndex - 1, tabCount);
                loadInventory();
                canScrollLeft = false;
                Scheduler.getInstance().addTimedAction(30, ()->canScrollLeft = true);
            }else if(Controller.talk() && canToggleEquip){
                attemptEquip();
                canToggleEquip = false;
                Scheduler.getInstance().addTimedAction(30, ()->canToggleEquip = true);
            }
        }


        //Open Inventory
        if(Controller.inventory() && canToggleDisplay  && room.isPeaceful() && !(room.getPlayer().frozen() && !active)){
            loadInventory();
            selectedIndex = 0;
            canToggleDisplay = false;
            active = !active;
            room.setInInventory(!room.isInInventory());
            canScrollLeft = canScrollRight = false;
            Scheduler.getInstance().addTimedAction(30, ()->{
                canToggleDisplay = true;
                canScrollLeft = true;
                canScrollRight = true;
            });
        }
    }


    protected void attemptEquip(){
        if(tabIndex == 1 || currentlyOpenTab.size() == 0) return;
        Item selected = currentlyOpenTab.get(selectedIndex);
        if(selected instanceof Consumable) {
            GameInfo.getInstance().getInventory().setEquippedConsumable((Consumable)selected);
        }else if(selected instanceof Skill){
            GameInfo.getInstance().getInventory().setEquippedSkill((Skill)selected);
        }
    }

    protected void loadInventory(){
        currentlyOpenTab = GameInfo.getInstance().getInventory().getInventoryTab(tabIndex);
    }

    @Override
    public void render(GameGraphics graphics) {
        if(active) {
            //BKG
            graphics.setGColour(Color.white);
            graphics.fillStaticRectangle(3 * Game.width / 4, Game.height/3, Game.width/4, 2*Game.height/3);
            graphics.setGColour(Color.black);
            graphics.drawStaticRectangle(3 * Game.width / 4, Game.height/3, Game.width/4, 2*Game.height/3);
            graphics.drawString(Integer.toString(tabIndex), 3*Game.width/4, Game.height/3);


            //Inventory Contents
            if(currentlyOpenTab.size() == 0){
                graphics.drawString("[Empty]", 3*Game.width/4 + 32, Game.height/3 + 32);
            }else {
                for (int i = scrollPos; i < Math.min(scrollPos + 6, currentlyOpenTab.size()); i++) {
                    int order = i - scrollPos;
                    int y = order * 32;
                    Item item = currentlyOpenTab.get(i);

                    String name = item == GameInfo.getInstance().getInventory().getEquippedConsumable()
                            || item == GameInfo.getInstance().getInventory().getEquippedSkill() ?
                            item.getTitle() + "  (E)" : item.getTitle();

                    Color textColour = i == selectedIndex ? Color.RED : Color.black;
                    graphics.drawString(name, 3 * Game.width / 4 + 32, Game.height / 3 + y + 32, 14, textColour);
                    graphics.drawString(Integer.toString(item.getQuantity()), Game.width - 32, Game.height / 3 + y + 32, 14, textColour);
                }
            }

            //Description
            if(currentlyOpenTab.size() != 0) {
                Item selectedItem = currentlyOpenTab.get(selectedIndex);
                graphics.drawStaticRectangle(3 * Game.width / 4, 3 * Game.height / 4, Game.width / 4, 1 * Game.height / 4);

                String lines[] = selectedItem.getDescription().split("\n");
                for(int line = 0; line < lines.length; line++){
                    graphics.drawString(lines[line], 3 * Game.width / 4 + 8, 3 * Game.height / 4 + 16 + line * LINE_SPACING, 11, Color.BLACK);
                }
            }

        }
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
