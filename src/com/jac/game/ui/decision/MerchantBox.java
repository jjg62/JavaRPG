package com.jac.game.ui.decision;

import com.jac.game.entities.interact.merchant.Purchase;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.ui.Textbox;

import java.util.ArrayList;

public class MerchantBox extends DecisionBox {

    private ArrayList<Purchase> purchases;

    public MerchantBox(InteractingManager talkingManager, String speaker, ArrayList<Purchase> purchases){
        super(talkingManager, speaker, "");
        for(Purchase purchase : purchases){
            withDecision(new Decision(purchase.getName(), ()-> confirmBuy(purchase)));
        }
    }

    private void confirmBuy(Purchase purchase){
        if(!purchase.isPurchased()) {
            if (GameInfo.getInstance().hasCoins(purchase.getCost())) {
                talkingManager.showTextbox(new DecisionBox(talkingManager, speaker, "Are you sure you want to purchase " + purchase.getName() + "?")
                        .withDecision(new Decision("Yes", () -> {
                            purchase.buy();
                            returnToMenu();
                        }))
                        .withDecision(new Decision("No", () -> returnToMenu())));
            } else {
                talkingManager.showTextbox(new Textbox(talkingManager, speaker, "You do not have enough coins.", () -> returnToMenu()));
            }
        }else{
            talkingManager.showTextbox(new Textbox(talkingManager, speaker, "Out of stock.", () -> returnToMenu()));
        }
    }



    private void returnToMenu(){
        talkingManager.showTextbox(this);
    }
}
