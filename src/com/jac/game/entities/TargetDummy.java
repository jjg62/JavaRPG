package com.jac.game.entities;

import com.jac.game.abilities.*;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.ChannelHitbox;
import com.jac.game.abilities.attacks.components.projectiles.ChannelProjectile;
import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.behaviour.CampY;
import com.jac.game.entities.behaviour.projectiles.Tornado;
import com.jac.game.rooms.Room;

import java.awt.event.KeyEvent;

public class TargetDummy extends Mob{

    //Attacks
    private Attack skadoosh;
    private Attack tornado;
    private Attack tripleTornado;

    public TargetDummy(Room room) {
        super(room, 96, 96, 10, "crane_ninja");
        setBehaviour(new CampY(this, room.getPlayer(), 4));
    }

    @Override
    protected void initAttacks() {
        skadoosh = new Attack("Skadoosh", KeyEvent.VK_RIGHT, 256, 420, this)
                .withComponent(0, new ChannelHitbox(this, 4, new Vector(-96, -96), 192, 192, 0, 0, 192, 192, 60, 40, true));

        tornado = new Attack("Tornado", 0, 500, 120, this)
                .withComponent(0, new ChannelProjectile(this, 240, new Vector(32, -32), 64, 64, 16, 16, 32, 32, 40, 28, new Tornado(4, 2)));


        //attacks.add(skadoosh);
        abilities.add(tornado);
    }

    public void render(GameGraphics graphics){
        renderShadow(graphics);
        super.render(graphics);
    }

    public void tick(){
        super.tick();
        Camera.duelCamera(this, room.getPlayer());
    }
}
