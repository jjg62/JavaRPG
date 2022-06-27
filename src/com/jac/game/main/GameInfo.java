package com.jac.game.main;

import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.items.Inventory;
import com.jac.game.items.InventoryTab;
import com.jac.game.ui.UIHandler;

import java.util.Random;

public class GameInfo {


    private int coinAmount;
    private int day;
    private int time;
    private Inventory inventory;

    //Game Progress
    public boolean tutorialDone = false;

    public static GameInfo instance;

    public GameInfo(){
        instance = this;
        newGame();
    }

    private void newGame(){
        coinAmount = 1000;
        day = 1;
        time = 9;
        inventory = new Inventory();
    }

    public boolean hasCoins(int amount){
        return coinAmount >= amount;
    }

    public void addCoins(int amount){
        coinAmount += amount;
    }

    public int getCoinAmount(){
        return coinAmount;
    }

    public int getDay(){
        return day;
    }

    public int getTime(){
        return time;
    }

    public void advanceTime(){
        time++;
        QuestlineList.getInstance().progressQuestline(0); //Progress Main Questline
    }

    public void activatePocketwatch(){
        tutorialDone = true;
        UIHandler.getInstance().activatePocketwatch();
    }

    public static GameInfo getInstance(){
        return instance;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public int getCombo(){
        Random rand = new Random();
        return rand.nextInt(3)*1000 + rand.nextInt(4)*100 + rand.nextInt(4)*10 + rand.nextInt(4);
    }
}
