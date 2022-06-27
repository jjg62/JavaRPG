package com.jac.game.items;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.AbilityDash;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.CompoundAbility;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.entities.Mob;
import com.jac.game.utils.FileUtils;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ItemList {

    private static final BufferedImage IMG_POTION_FULL = FileUtils.loadImage("/textures/ui/potion.png");
    private static final BufferedImage IMG_POTION_EMPTY = FileUtils.loadImage("/textures/ui/potion_empty.png");



    public static final Item HEALTH_POTION = new Consumable("health_potion", "Potion of Health", "Restores 5 HP.\n\n'A mysterious glowing vial found only \nin the Labyrinth. Its smell is\nindiscernible, yet warm and inviting.'", IMG_POTION_FULL, IMG_POTION_EMPTY) {
        @Override
        public void consumeEffect(Mob owner) {
            owner.setHealth(Math.min(owner.getHealth() + 5, owner.getMaxHealth()));
        }
    };

    public static final Item SPEED_POTION = new Consumable("speed_potion", "Scuttle Juice", "Makes you run fast", IMG_POTION_FULL, IMG_POTION_EMPTY) {
        @Override
        public void consumeEffect(Mob owner) {
            System.out.println("Bruh");
        }
    };

    public static final Item LABYRINTH_FLUX = new Item("labyrinth_flux", "Labyrinth Flux", "Used as a Crafting Material.\n\n'Speculated to be the source of\npower for the variety of magic-like\nabilities usable only in the Labyrinth.'", IMG_POTION_FULL);


    public static final Item SKILL_JUMP = new Skill("jump", "Vaulting Crush", "The caster channels Flux to their feet,\nallowing them to jump a long distance,\nand then land with immense power.", IMG_POTION_FULL) {
        @Override
        public Ability ability(Mob owner) {
            Attack stompAttack = new Attack("stompattack", 0, 192, 120, owner)
                    .withComponent(0, new Hitbox(owner, 12, new Vector(48, -80), 160, 160, 16, 16, 128, 128, 40, 24)
                            .withChanneled(true, 24));

            return new CompoundAbility("stomp", KeyEvent.VK_UP, 192, 120, owner, stompAttack, new AbilityDash(0, 0, owner, 16, 16, 8))
                    .withAnimation("jump", 8, true);
        }
    };


    public static final Item BRACELET = new Item("bracelet", "Charm Bracelet", "Belongs to somebody who needs it.\n\nA heavy bracelet with 9 distinct charms attached.\nIt has clearly been crafted with incredible attention\nto detail.", IMG_POTION_FULL);
}
