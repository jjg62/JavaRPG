package com.jac.game.abilities.attacks;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.TimedComponent;
import com.jac.game.entities.Mob;
import com.jac.game.entities.enemies.assistant.Assistant;
import com.jac.game.utils.DirectionUtils;

import java.awt.*;
import java.util.ArrayList;

public class Attack extends Ability {
    
    private ArrayList<TimedComponent> components;
    private int range;
    private boolean cancelled;
    private ArrayList<AttackComponent> currentlyActiveComponents;

    public Attack(String name, int key, int range, int cooldown, Mob owner){
        super(name, key, cooldown, owner);
        components = new ArrayList<>();
        currentlyActiveComponents = new ArrayList<>();
        this.range = range;
    }

    public Attack withComponent(int time, AttackComponent c){
        components.add(new TimedComponent(this, time, c));
        return this;
    }

    @Override
    public Attack withAnimation(String type, int speed){
        return (Attack)super.withAnimation(type, speed);
    }

    @Override
    public void play(){
        cancelled = false;
        currentlyActiveComponents.clear();

        Rectangle hurtbox = owner.getHurtbox();
        int xStart = hurtbox.x + hurtbox.width/2;
        int yStart = hurtbox.y + hurtbox.height/2;
        for (TimedComponent component : components) {
            component.createAt(xStart, yStart, DirectionUtils.vectorToRadians(directionVector));
        }
    }

    @Override
    public void cancel(boolean force){
        if(!cancelled) {
            super.cancel(force);
            ArrayList<AttackComponent> componentsToRemove = new ArrayList<>();

            for (AttackComponent component : currentlyActiveComponents) {
                if (component.attemptCancel(force)) {
                    componentsToRemove.add(component);
                }
            }

            for (AttackComponent component : componentsToRemove) {
                currentlyActiveComponents.remove(component);
            }
            cancelled = true;
        }

    }

    public int getRange() {
        return range;
    }

    @Override
    public boolean shouldOwnerChannel(){
        boolean channel = false;
        for(AttackComponent component : currentlyActiveComponents){
            channel |= component.shouldOwnerChannel();
        }

        return channel;
    }

    public void addCurrentlyActive(AttackComponent a){
        currentlyActiveComponents.add(a);
    }

    public boolean isCancelled(){
        return cancelled;
    }

}
