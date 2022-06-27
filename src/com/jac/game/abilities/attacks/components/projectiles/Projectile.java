package com.jac.game.abilities.attacks.components.projectiles;

import com.jac.game.abilities.ProjectileBehaviour;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Mob;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;

public class Projectile extends Hitbox {

    protected ProjectileBehaviour guide;

    public Projectile(Mob owner, int duration, Vector displacement, int width, int height, int hitboxXOffset, int hitboxYOffset, int hitboxWidth, int hitboxHeight, int activationDelay, int knockbackScalar, ProjectileBehaviour guide) {
        super(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar);
        this.guide = guide;
    }

    @Override
    public void tick(){
        super.tick();
        if(active) {
            Vector movements = guide.moveCommand();
            x += movements.x;
            y += movements.y;
            hitbox.x += movements.x;
            hitbox.y += movements.y;
        }
    }

    @Override
    protected AttackComponent copy(){
        return new Projectile(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar, guide.copy())
                .withDamage(damage)
                .withActiveAnimation(activeAnimation)
                .withChargeUpAnimation(chargeUpAnimation)
                .withFX(vfx)
                .withChanneled(channeled, endLag);
    }

    @Override
    protected void initInfo(AttackComponent created, int x, int y, double angle){
        super.initInfo(created, x, y, angle);
        ((Projectile)created).guide.startProjectile((Projectile)created);
    }
}
