package com.jac.game.entities.interact.merchant;

import com.jac.game.entities.structs.IAction;
import com.jac.game.main.GameInfo;

public class Purchase {

    private GameInfo gameInfo;

    private Merchant merchant;
    private String name;
    private int cost;
    private boolean purchased;
    private IAction result;

    public Purchase(Merchant merchant, String name, int cost, IAction result){
        this.gameInfo = GameInfo.getInstance();

        this.merchant = merchant;
        this.name = name;
        this.cost = cost;
        this.purchased = false;
        this.result = result;
    }

    public void buy(){
        gameInfo.addCoins(-cost);
        purchased = true;
        result.run();
    }

    public int getCost() {
        return cost;
    }

    public String getName(){
        return name;
    }

    public boolean isPurchased(){
        return purchased;
    }
}
