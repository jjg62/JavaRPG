package com.jac.game.encounters;

import com.jac.game.abilities.Vector;
import com.jac.game.audio.Music;
import com.jac.game.entities.Mob;

import java.util.ArrayList;

public class MobPhase extends Phase{

    private ArrayList<Integer> maxHealthValues;
    private ArrayList<Mob> mobs;
    private ArrayList<Vector> offsets;

    public MobPhase(){
        super(0);
        mobs = new ArrayList<>();
        maxHealthValues = new ArrayList<>();
        offsets = new ArrayList<>();
    }

    public MobPhase withMob(int xOffset, int yOffset, Mob mob){
        this.mobs.add(mob);
        this.maxHealthValues.add(mob.getHealth());
        this.offsets.add(new Vector(xOffset, yOffset));
        this.progressRequired++;
        return this;
    }

    @Override
    public void start(int x, int y){
        for(int i = 0; i < mobs.size(); i++) {
            Mob mob = mobs.get(i);
            mob.setHealth(maxHealthValues.get(i));
            Vector offset = offsets.get(i);
            mob.inEncounter(encounter).spawn(x + offset.x, y + offset.y);
        }
    }

    public ArrayList<Mob> getMobs(){
        return mobs;
    }
}
