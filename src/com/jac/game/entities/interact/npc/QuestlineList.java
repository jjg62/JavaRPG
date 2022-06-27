package com.jac.game.entities.interact.npc;

import com.jac.game.cutscene.Cutscene;
import com.jac.game.cutscene.CutsceneList;
import com.jac.game.cutscene.events.CustomEvent;
import com.jac.game.cutscene.events.DecisionEvent;
import com.jac.game.cutscene.events.Pause;
import com.jac.game.cutscene.events.TextboxEvent;
import com.jac.game.items.Item;
import com.jac.game.items.ItemList;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.RoomList;
import com.jac.game.textures.Animation;
import com.jac.game.ui.decision.Decision;
import com.jac.game.utils.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class QuestlineList {

    private static Animation alainIdle = new Animation(12, FileUtils.loadAnimationFrames("/textures/entities/npcs/alain.png", 64, 64));
    private static Animation bomesIdle = new Animation(30, FileUtils.loadAnimationFrames("/textures/entities/npcs/bomes.png", 64, 64));
    private static Animation edgeIdle = new Animation(24, FileUtils.loadAnimationFrames("/textures/entities/npcs/edge.png", 64, 64));
    private static Animation ritaIdle = new Animation(24, FileUtils.loadAnimationFrames("/textures/entities/rita/idle/rita_idle_s.png", 64, 64));

    private Questline[] questlines;

    private ArrayList<NPC> npcs;

    private static QuestlineList instance;

    public NPC shopkeeper;
    public NPC rita;

    public QuestlineList() {
        instance = this;
        initQuestlines();
    }

    private void initQuestlines(){
        questlines = new Questline[64];

        questlines[0] = new Questline(); //Main Game Progress
        questlines[1] = new Questline(); //Door Man
        questlines[2] = new Questline(); //Alain
        questlines[3] = new Questline(); //Shopkeeper
        questlines[4] = new Questline(); //Rita
        questlines[5] = new Questline(); //Sherlock Bones

        initNPCs();

        questlines[1].start();
        questlines[2].start();
        questlines[3].start();
        questlines[4].start();
    }

    private void initNPCs() {
        npcs = new ArrayList<>();

        //Door Man
        npcs.add(new NPC(questlines[1], 96, 96, edgeIdle)
               .withOneTimeState(0, 544, 288, CutsceneList.door_tutorial,
                      new Cutscene(RoomList.room_r1_door)
                             .withEvent(new TextboxEvent("Curious Contestant", "Man, how crazy is this place...\nWe're trying to get to the end, and we can't even understand how the doors work!", "Maybe we're the crazy ones after all...", "/h...nice hairstyle, by the way...")))
                .withOneTimeState(1, 1080, 540, new Cutscene(RoomList.room_r6_zigzag).withEvent(new TextboxEvent("Curious Contestant", "Hey there. Don't take this as a threat or anything, but could I ask\nyou a quick question?", "You're not a very experienced Fluxcaster are you? It's pretty obvious. In fact\nI wouldn't be surprised if you've never heard of Flux before today, right?", "/hDon't worry, some people are like that. Especially some people who live in Villages that are far\naway from the Labyrinth. Well, that's my theory, anyway.", "You see - us humans have this innate ability to manipulate the Flux contained in this Labyrinth,\nbut some can do it much easier than others, since they have a higher sensitivity to it.", "To me, it makes sense that the residents of the Villages closest to the Labyrinth, the\nsource of the Flux, might have evolved to be able to control it more effectively.", "But that's not to say that Fluxcasting is just a talent - it can be trained too. I'm sure you'll be able to\nfind some people who are willing to upgrade your Flux Sensitivity, provided you pay the right price.", "//hAnd trust me, I think you in particular should keep an eye out for that. Most other people I've seen\naround here seem to be able to handle themselves.",  "However skilled you are with that dagger, you probably won't be chosen by the\nLabyrynthian without some decent Fluxcasting skills on your side.")).withEvent(new CustomEvent(()->progressQuestline(1))),
                        new Cutscene(RoomList.room_r6_zigzag).withEvent(new TextboxEvent( "Curious Contestant", "Fluxcasting isn't just a talent - it can be trained too. I'm sure you'll be able to find some people\nwho are willing to upgrade your Flux Sensitivity, provided you pay the right price.", "//hAnd trust me, I think you in particular should keep an eye out for that. Most other people I've seen\naround here seem to be able to handle themselves.",  "However skilled you are with that dagger, you probably won't be chosen by the\nLabyrynthian without some decent Fluxcasting skills on your side."))));


        //Craft Man
        npcs.add(new NPC(questlines[2], 96, 96, alainIdle)
                .withState(0, new NPCStateOneTime(null, 0,
                        new NPCStateRequirement(null, new NPCState(null, 320, 152, CutsceneList.first_craft_true), new NPCState(null, 320, 152, CutsceneList.first_craft_false)) {
                            @Override
                            protected boolean requirement() {
                                return GameInfo.getInstance().getInventory().containsAtLeast(ItemList.LABYRINTH_FLUX, 2);
                            }
                        },
                        new NPCState(null, 320, 152, new Cutscene(RoomList.room_r3_double_door).withEvent(new TextboxEvent("Alain", "I hope that Potion is of good use to you, brother!", "Feel free to drop by my Haven, just north of here, whenever you please.\nIf you bring rare ingredients, I can make you some powerful concoctions!", "/hAnd thus begins our symbiotic friendship - my favourite kind!"))))));


        //Shopkeeper
        shopkeeper = new NPC(questlines[3], 96, 96, edgeIdle)
                .withState(0, new NPCStateOneTime(null, 0,
                        new NPCStateRequirement(null, new NPCState(null, 420, 420, CutsceneList.join_shop),
                                new NPCState(null, 420, 420, new Cutscene(RoomList.room_r10_last)
                                        .withEvent(new TextboxEvent("Milo", "Hey, you're one of the new Contestants huh? Boy have you found yourself\nthe deal of a lifetime!", "My name is Milo, and I own the best store in the entire Labyrinth:\nMilo's Emporium for Awesome Individuals!", "...The name's a work in progress. But that doesn't change that I sell some awesome\nproducts, no doubt!", "Before you can enter, we just gotta check that you truly are an Awesome Individual.\nWe charge a membership fee - and I know what you're thinking; 'That's a rip-off'\n, right? WRONG!", "For the low, low price of 1000 Coins, I'll give you lifetime\n access to the store! Plus I'll throw in this Charm Bracelet that I found for no extra cost!", "Oh, seems like you don't have the money. Come back when you're ready to start shopping smart!")))){
                            @Override
                            protected boolean requirement() {
                                return GameInfo.getInstance().getCoinAmount() >= 1000;
                            }
                        }, null));
        npcs.add(shopkeeper);

        //Rita
        rita = new NPC(questlines[4], 96, 96, ritaIdle)
                .withState(0, new NPCStateRequirement(null, new NPCState(null, 420, 420, CutsceneList.girl_bracelet_found),
                        new NPCState(null, 420, 420, new Cutscene(RoomList.room_l3_corridor)
                                .withEvent(new TextboxEvent("Girl", "Hey, did you find it?", "...no? Darn! I'm really sorry but we need to find that Charm Bracelet, before it gets stolen...", "Maybe I should retrace my steps... Let me see - \nafter I bumped into you, I headed up to that horseshoe-shaped room with four doors. I took the one leading South-East....", "...did a couple of Combat Trials, then came back here...")))) {
                    @Override
                    protected boolean requirement() {
                        return GameInfo.getInstance().getInventory().containsAtLeast(ItemList.BRACELET, 1);
                    }
                });
        npcs.add(rita);

        npcs.add(new NPC(questlines[5], 96, 96, bomesIdle)
                .withState(0, new NPCState(null, 256, 256, new Cutscene(RoomList.room_r1_door)
                        .withEvent(new TextboxEvent("Bomes", "I say, this here pipe solves the entire mystery of the Labyrinth! How trivial eh, my dear?"))
                        .withEvent(new TextboxEvent("You", "(...is she talking to me?)"))
                        .withEvent(new TextboxEvent("Bomes", "Any person with an ounce of competence could see this pipe and deduce one key fact -\nThe Labyrinth must have multiple floors!", "And see how to Flux flows from below the floor to above the ceiling.\nWe can assume that the Flux is travelling to a Room that powers the entire Labyrinth.", "From that, one conclusion is clear - the place where the Labyrinthian dwells is directly above here!"))))
                .withState(1, new NPCState(null, 420, 192, CutsceneList.sherlock_lights)));

    }

    private int getMainQuestProgress(int day, int time){
        return (day - 1) * 6 + time - 10;
    }

    public void startQuestline(int id){
        questlines[id].start();
    }

    public void progressQuestline(int id){
        questlines[id].progressQuestline();
    }

    public void progressQuestlineFrom(int id, int from){
        questlines[id].progressQuestlineFrom(from);
    }

    //To be called each hour
    public void updateNPCs(){
        for(NPC npc : npcs){
            npc.updateState();
        }
    }

    public static QuestlineList getInstance(){
        return instance;
    }
}
