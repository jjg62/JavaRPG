package com.jac.game.rooms;

import com.jac.game.audio.Music;
import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.CutsceneList;
import com.jac.game.cutscene.events.*;
import com.jac.game.encounters.BHPhase;
import com.jac.game.encounters.Encounter;
import com.jac.game.encounters.MobPhase;
import com.jac.game.entities.LocationTrigger;
import com.jac.game.entities.enemies.dweller.MeleeDweller;
import com.jac.game.entities.enemies.RangedDweller;
import com.jac.game.entities.enemies.assistant.Assistant;
import com.jac.game.entities.enemies.assistant.AssistantBH;
import com.jac.game.entities.enemies.assistant.AssistantPhase2;
import com.jac.game.entities.enemies.dweller.MeleeDwellerBHAvalanche;
import com.jac.game.entities.interact.*;
import com.jac.game.entities.interact.debug.DoorUnlocker;
import com.jac.game.entities.interact.debug.Talking;
import com.jac.game.entities.interact.merchant.Merchant;
import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.entities.statics.Firepit;
import com.jac.game.entities.statics.Pipe;
import com.jac.game.entities.statics.Torch;
import com.jac.game.items.ItemList;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;

public class RoomList {

    //Rooms
    public static Room room1;
    public static Room room2;

    public static Room room_haven;
    public static Room room_assistant;

    //Right Side
    public static Room room_r1_door;
    public static Room room_r2_arena;
    public static Room room_r3_double_door;
    public static Room room_r4_treasure;
    public static Room room_r5_arena_2;
    public static Room room_r6_zigzag;
    public static Room room_r7_alain;
    public static Room room_r8_arena_3;
    public static Room room_r9_arena_4;
    public static Room room_r10_last;
    public static Room room_r11_shop;

    //Left Side
    public static Room room_l1_miniboss_entrance;
    public static Room room_l2_arena;
    public static Room room_l3_corridor;
    public static Room room_l4_rita_haven;
    public static Room room_l5_resevoir;
    public static Room room_l6_mysterydoor;

    //Doors
    public static Door door_haven;
    public static Door door_r1_n;
    public static Door door_r2_treasure;
    public static Door door_haven_w;
    public static Door door_shop;
    public static Door door_rita;

    //Triggers
    public static LocationTrigger girlTrigger1;

    //other
    public static Firepit firepit;
    private static Torch torch1;
    private static Torch torch2;
    private static Torch torch3;
    private static Torch torch4;

    public static ChangeTorch comboTorch1;
    public static ChangeTorch comboTorch2;
    public static ChangeTorch comboTorch3;
    public static ChangeTorch comboTorch4;

    public static void initRooms(Game game){

        room1 = new Room(game, "room_test.txt", true);
        room2 = new Room(game, "room_3ninjas.txt", true);
        actualRooms(game);

        Talking friendlyNPC = new Talking(room1, 32, 32);
        //friendlyNPC.spawn(464, 258);
        friendlyNPC.setText("ayyyyyyy", "I'm walking heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere");

        Door door = new Door(room1, room2, 332, 464, 'N', true);
        door.spawn(496, 32);
        new DoorUnlocker(room1, door).spawn(332, 600);

        //new Merchant(room1, 32, 32)
         //       .withPurchase("Top Secret", 200)
         //       .spawn(464, 350);



        //new RoomChanger(room2, room1, 500, 196, false).spawn(364, 464);
        Door escapeDoor = new Door(room2, room1, 500, 128, 'N', true);
        escapeDoor.spawn(512, 32);
        //Encounter

    }

    //The actual game
    private static void actualRooms(Game game){


        //Rooms
        room_haven = new Room(game, "rooms/room_haven.txt", true);
        room_assistant = new Room(game, "rooms/room_labyrinth_entry.txt", true);
        room_r1_door = new Room(game, "rooms/room_r1_door.txt", true);
        room_r2_arena = new Room(game, "rooms/room_r2_arena.txt", true);
        room_r3_double_door = new Room(game, "rooms/room_r3_double_door.txt", true);
        room_r4_treasure = new Room(game, "rooms/room_r4_treasure.txt", true);
        room_r5_arena_2 = new Room(game, "rooms/room_r5_arena_2.txt", true);
        room_r6_zigzag = new Room(game, "rooms/room_r6_zigzag.txt", true);
        room_r7_alain = new Room(game, "rooms/room_r7_alain.txt", true);
        room_r8_arena_3 = new Room(game, "rooms/room_r8_arena_3.txt", true);
        room_r9_arena_4 = new Room(game, "rooms/room_r9_arena_4.txt", true);
        room_r10_last = new Room(game, "rooms/room_r10_last.txt", true);
        room_r11_shop = new Room(game, "rooms/room_r11_shop.txt", true);
        room_r11_shop.setMusic(Music.BOUNCE_BACK);

        room_l1_miniboss_entrance = new Room(game, "rooms/left/room_l1_miniboss_entry.txt", true);
        room_l2_arena = new Room(game, "rooms/left/room_l2_arena.txt", true);
        room_l3_corridor = new Room(game, "rooms/left/room_l3_corridor.txt", true);
        room_l4_rita_haven = new Room(game, "rooms/left/room_l4_rita_haven.txt", true);
        room_l5_resevoir = new Room(game, "rooms/left/room_l5_resevoir.txt", true);
        room_l6_mysterydoor = new Room(game, "rooms/left/room_l6_mysterydoor.txt", true);

        int combo = GameInfo.getInstance().getCombo();

        //HAVEN
        //Intro Cutscene
        new LocationTrigger(room_haven, 128, 128, ()-> CutsceneList.haven_door.play()).spawn(512, 352);
        //Northern Door
        door_haven = new Door(room_haven, room_assistant, 466, 768, 'N', true); //Haven Door
        door_haven.spawn(528, 32);

        //LABYRINTH ENTRANCE
        //Encounter
        Encounter bossTutorial = new Encounter(room_assistant, 464, 464, "Labyrinth Caretaker") {
            @Override
            protected Cutscene endCutscene(int endingX, int endingY) {
                    return new Cutscene(room)
                            .withEvent(new SpawnEntityEvent("assistant", endingX, endingY, 96, 96))
                            .withEvent(new PanCameraEvent(endingX + 48, endingY + 48, 20))
                            .withEvent(new TextboxEvent("Caretaker", "/h...", "Well, it is now apparent that you were invited here for a reason.", "Very well. As promised, you will be allowed to compete in the Labyrinthian's Trials.", "But worry not, as you will not be alone.", "No doubt, you will need guidance on your quest to traverse the Labyrinth, and to that end...", "...I will be your marvelous assistant.", "All other contestants will be working with their own assistants too, but if I may drop formalities for a moment...", "...you're lucky you got me.", "Let's just say that I wasn't exactly showing you my entire arsenal. Otherwise, no man would pass my examination.", "And after seeing how you fight, I think you just might be on my wavelength.", "I'm looking forward to working with you."));
            }

            @Override
            protected void makeChangesToRoom() {
                room_assistant.getPlayer().setX(464);
                room_assistant.getPlayer().setY(528);
                CutsceneList.labyrinth_briefing.play();
            }
        }
                .withPhase(new MobPhase().withMob(0, 0, new Assistant(room_assistant)))
                .withPhase(new BHPhase(360, new AssistantBH(room_assistant), 464, 464, 5))
                .withPhase(new MobPhase().withMob(0, 0, new AssistantPhase2(room_assistant)));


        //Intiation Cutscene
        if(!GameInfo.getInstance().tutorialDone){
            new LocationTrigger(room_assistant, 96, 96, ()->CutsceneList.assistant_intro.withEvent(new CustomEvent(()->bossTutorial.start())).play()).spawn(466, 768);
        }

        //Another door
        new Door(room_assistant, room_haven, 320, 320, 'S', false).spawn(466, 800);
        new Door(room_assistant, room_r1_door, 96, 640, 'E', false).spawn(876, 494);

        door_haven_w = new Door(room_assistant, room_l1_miniboss_entrance, 1004, 232, 'W', true);
        door_haven_w.spawn(52, 492);

        //Torches
        new Torch(room_assistant).spawn(224, 224);
        new Torch(room_assistant).spawn(736, 224);

        //FIRST RIGHT ROOM - DOORMAN
        //Northern Door
        door_r1_n = new Door(room_r1_door, room_r3_double_door, 128, 384, 'N', true).withLockedText("The Door is Locked...", "You feel a presence above you. It whispers:\n'Prove your worth in a nearby Trial'");
        door_r1_n.spawn(432, 32);

        //Door to Arena
        new Door(room_r1_door, room_r2_arena, 1072, 160, 'S', false).spawn(1072, 428);
        new Door(room_r1_door, room_assistant, 876, 494, 'W', false).spawn(52, 616);

        //Torches
        new Torch(room_r1_door).spawn(128, 480);
        new Torch(room_r1_door).spawn(896, 224);

        girlTrigger1 = new LocationTrigger(room_r1_door, 128, 128, ()->CutsceneList.girl_intro.play());

        //Pipe
        new Pipe(room_r1_door).spawn(256, 192);


        //SECOND RIGHT ROOM - ARENA
        //Doors
        new Door(room_r2_arena, room_r1_door, 1072, 408, 'N', false).spawn(1072, 32);
        door_r2_treasure = new Door(room_r2_arena, room_r4_treasure, 256, 320, 'N', true);
        door_r2_treasure.spawn(624, 288);

        //Encounter
        Encounter r2 = new Encounter(room_r2_arena, 428 ,480, "Labyrinth Dweller Cadet") {
            @Override
            protected Cutscene endCutscene(int endingX, int endingY) {
                return new Cutscene(room_r2_arena).withEvent(new Pause(96));
            }

            @Override
            protected void makeChangesToRoom() {
                GameInfo.getInstance().getInventory().addItem(ItemList.LABYRINTH_FLUX);
                GameInfo.getInstance().getInventory().addItem(ItemList.LABYRINTH_FLUX);
                QuestlineList.getInstance().progressQuestlineFrom(1, 0);
                QuestlineList.getInstance().startQuestline(5);
                door_r1_n.unlock();
                door_r2_treasure.unlock();
                room_r2_arena.getPlayer().setX(316);
                room_r2_arena.getPlayer().setY(544);
                girlTrigger1.spawn(1072, 408);
                //CutsceneList.rita_intro.play();
            }
        }
            .withPhase(new MobPhase()
                    .withMob(-256, 128, new MeleeDweller(room_r2_arena, 8)))
                .withPhase(new BHPhase(370, new MeleeDwellerBHAvalanche(room_r2_arena), 426, 480, 5))
                .withPhase(new MobPhase()
                        .withMob(0, 0, new MeleeDweller(room_r2_arena, 8).withDeathAnimation()));

        new Gong(room_r2_arena, r2, "(Okay, I guess this is where I start the Combat Trial... Let me just take a couple deep breaths.)", "(I should remember that attacking when I've got high Momentum can DISARM and enemy in the\nmiddle of their attack. I won't get be able to engage close-ranged opponents otherwise.)", "(...I've got this.)").spawn(208, 320);

        //Torches
        new Torch(room_r2_arena).spawn(480, 288);

        //THIRD RIGHT ROOM - DOUBLE DOOR
        //Doors
        new Door(room_r3_double_door, room_r1_door, 448, 96, 'S', false).spawn(176, 492);
        new Door(room_r3_double_door, room_r5_arena_2, 128, 608, 'N', false).spawn(1008, 96);
        new Door(room_r3_double_door, room_r6_zigzag, 560, 864, 'N', true).spawn(176, 96);

        //Torches
        new Torch(room_r3_double_door).spawn(320, 96);
        new Torch(room_r3_double_door).spawn(896, 96);

        new Pipe(room_r3_double_door).spawn(640, 64);

        //FOURTH RIGHT ROOM - TREASURE ROOM

        //Doors
        new Door(room_r4_treasure, room_r2_arena, 512, 320, 'S', false).spawn(336, 364);

        //Chest
        new Chest(room_r4_treasure, false, ItemList.HEALTH_POTION).withOpenText("You found a Health Potion!", "Open your Inventory with Q to Equip it.\nThen you can use it during a Combat Trial with the DOWN ARROW KEY.").spawn(336, 144);

        //FIFTH RIGHT ROOM - SECOND ARENA
        //Doors
        new Door(room_r5_arena_2, room_r3_double_door, 960, 256, 'S', false).spawn(176, 620);
        new Door(room_r5_arena_2, room_r6_zigzag, 1472, 384, 'W', false).spawn(244, 176);
        new Door(room_r5_arena_2, room_r9_arena_4, 116, 176, 'E', false).spawn(620, 176);

        //Encounter
        Encounter r5 = new Encounter(room_r5_arena_2, 464 ,464, "Ranged Labyrinth Dweller") {
            @Override
            protected Cutscene endCutscene(int endingX, int endingY) {
                return new Cutscene(room_r5_arena_2).withEvent(new PanCameraEvent(40));
            }

            @Override
            protected void makeChangesToRoom() {
                room_r5_arena_2.getPlayer().setX(256);
                room_r5_arena_2.getPlayer().setY(256);
                CutsceneList.reminder_comeback.play();
            }
        }
                .withPhase(new MobPhase()
                        .withMob(0, 0, new RangedDweller(room_r5_arena_2, 12)));

        new Gong(room_r5_arena_2, r5).spawn(736, 416);

        //Torches
        new Torch(room_r5_arena_2).spawn(160, 352);
        new Torch(room_r5_arena_2).spawn(320, 32);
        new Torch(room_r5_arena_2).spawn(564, 32);

        //SIXTH RIGHT ROOM - ZIGZAG

        //Doors
        new Door(room_r6_zigzag, room_r5_arena_2, 256, 128, 'E', false).spawn(1580, 368);
        new Door(room_r6_zigzag, room_r3_double_door, 256, 196, 'S', false).spawn(560, 876);
        new Door(room_r6_zigzag, room_r7_alain, 684, 140, 'W', false).spawn(180, 180);

        new Chest(room_r6_zigzag, false, ItemList.LABYRINTH_FLUX, ItemList.LABYRINTH_FLUX).withOpenText("You found 2x Labyrinthian Flux!\nThey were added to the Materials tab in your Inventory").spawn(564, 80);

        //SEVENTH RIGHT ROOM - ALAIN

        new Door(room_r7_alain, room_r6_zigzag, 180, 180, 'E', false).spawn(684, 140);
        new Door(room_r7_alain, room_r8_arena_3, 924, 416, 'W', false).spawn(116, 140);

        //EIGHTH RIGHT ROOM - 3rd arena

        //Doors
        new Door(room_r8_arena_3, room_r7_alain, 116, 140, 'E', false).spawn(940, 428);

        //Torches
        torch1 = new Torch(room_r8_arena_3, 4);
        torch2 = new Torch(room_r8_arena_3, 4);
        torch3 = new Torch(room_r8_arena_3, 4);
        torch4 = new Torch(room_r8_arena_3, 4);

        torch1.spawn(320, 96);
        torch2.spawn(416, 96);
        torch3.spawn(512, 96);
        torch4.spawn(608, 96);

        //Encounter
        Encounter r8 = new Encounter(room_r8_arena_3, 172 ,336, "Cadet Twins") {
            @Override
            protected Cutscene endCutscene(int endingX, int endingY) {
                return new Cutscene(room_r8_arena_3)
                        .withEvent(new PanCameraEvent(448, 0, 40))
                        .withEvent(new CustomEvent(()->torch1.updateAnimations(combo/1000)))
                        .withEvent(new Pause(10))
                        .withEvent(new CustomEvent(()->torch2.updateAnimations((combo % 1000)/100)))
                        .withEvent(new Pause(10))
                        .withEvent(new CustomEvent(()->torch3.updateAnimations((combo%100)/10)))
                        .withEvent(new Pause(10))
                        .withEvent(new CustomEvent(()->torch4.updateAnimations(combo%10)))
                        .withEvent(new Pause(60))
                        .withEvent(new PanCameraEvent(40));
            }

            @Override
            protected void makeChangesToRoom() {
                QuestlineList.getInstance().progressQuestline(5);
            }
        }
                .withPhase(new MobPhase()
                        .withMob(0, 0, new MeleeDweller(room_r8_arena_3, 3))
                        .withMob(472, 0, new MeleeDweller(room_r8_arena_3, 3)));

        new Gong(room_r8_arena_3, r8).spawn(176, 148);

        //NINTH ROOM - 4th arena
        new Door(room_r9_arena_4, room_r5_arena_2, 620, 176, 'W', false).spawn(116, 176);
        new Door(room_r9_arena_4, room_r10_last, 180, 556, 'E', false).spawn(748, 624);


        //TENTH ROOM - Last room
        new Door(room_r10_last, room_r9_arena_4, 748, 624, 'W', false).spawn(180, 556);
        door_shop = new Door(room_r10_last, room_r11_shop, 144, 80, 'S', true).withLockedText("The door is locked...", "Hmm? There seems to be some text scrawled in crayon:\n'You are not awesome enough! Speak to Milo to become a new, awesomer you!'");
        door_shop.spawn(688, 940);


        //ELEVENTH ROOM - Shop
        new Door(room_r11_shop, room_r10_last, 688, 928, 'N', false).spawn(144, 32);

        //Test Merchant
        new Merchant(room_r11_shop, 96, 96).spawn(436, 224);


        //LEFT
        //FIRST ROOM - Miniboss Entrance
        new Door(room_l1_miniboss_entrance, room_l2_arena, 748, 368, 'W', false).spawn(116, 812);
        new Door(room_l1_miniboss_entrance, room_assistant, 420, 420, 'E', false).spawn(1004, 232);


        //SECOND ROOM - Arena 1
        new Door(room_l2_arena, room_l3_corridor, 336, 736, 'N', false).spawn(368, 32);
        new Door(room_l2_arena, room_l1_miniboss_entrance, 116, 812, 'E', false).spawn(748, 368);

        //THIRD ROOM - Corridor
        new Door(room_l3_corridor, room_l2_arena, 368, 80, 'S', false).spawn(336, 748);
        door_rita = new Door(room_l3_corridor, room_l4_rita_haven, 344, 544, 'N', true);
        door_rita.spawn(336, 32);
        new Door(room_l3_corridor, room_l5_resevoir, 940, 1024, 'W', false).spawn(116, 428);

        //Cutscene
        new LocationTrigger(room_l3_corridor, 512, 64, ()->CutsceneList.girl_item_request.play()).spawn(128, 512);

        //FOURTH ROOM - Rita Haven
        new Door(room_l4_rita_haven, room_l3_corridor, 336, 80, 'S', false).spawn(344, 556);
        new Door(room_l4_rita_haven, room_l6_mysterydoor, 1072, 544, 'N', false).spawn(336, 160);
        new LocationTrigger(room_l4_rita_haven, 96, 96, ()->CutsceneList.rita_fire.play()).spawn(344, 556);

        firepit = new Firepit(room_l4_rita_haven);
        firepit.spawn(352, 368);

        new Torch(room_l4_rita_haven).spawn(256, 160);
        new Torch(room_l4_rita_haven).spawn(448, 160);

        //FIFTH ROOM - Resevoir
        new Door(room_l5_resevoir, room_l3_corridor, 116, 428, 'E', false).spawn(940, 1088);
        new Door(room_l5_resevoir, room_l6_mysterydoor, 176, 544, 'N', true).spawn(400, 32);

        //SIXTH ROOM - Mystery Door
        new Door(room_l6_mysterydoor, room_l5_resevoir, 400, 80, 'S', true).spawn(176, 556);
        new Door(room_l6_mysterydoor, room_l4_rita_haven, 400, 196, 'S', false).spawn(1072, 556);

        new MysteryDoor(room_l6_mysterydoor, room_l6_mysterydoor, 400, 400, 'N', false)
                .withCombo(combo, RoomList.room_assistant, 400, 400)
                .spawn(608, 224);
        //Four Torches
        comboTorch1 = new ChangeTorch(new Torch(room_l6_mysterydoor));
        comboTorch2 = new ChangeTorch(new Torch(room_l6_mysterydoor));
        comboTorch3 = new ChangeTorch(new Torch(room_l6_mysterydoor));
        comboTorch4 = new ChangeTorch(new Torch(room_l6_mysterydoor, 1));

        comboTorch1.spawn(336, 224);
        comboTorch2.spawn(464, 224);
        comboTorch3.spawn(784, 224);
        comboTorch4.spawn(912, 224);




    }
}
