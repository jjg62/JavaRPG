package com.jac.game.items;

import com.jac.game.abilities.Ability;

import java.awt.image.BufferedImage;

public class Item {

    protected String name;
    protected String title;
    protected String description;

    private int quantity;
    private BufferedImage icon;
    private int sellPrice;

    public Item(String name, String title, String description, BufferedImage icon){
        this.name = name;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.quantity = 1;
        this.sellPrice = 10;
    }

    public String getName(){
        return name;
    }

    public void incrementQuantity(){
        quantity++;
    }

    public void decrementQuantity(){
        quantity--;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public BufferedImage getIcon(){
        return icon;
    }

    public Item newStack(){
        quantity = 1;
        return this;
    }
}
