package com.jac.game.cutscene;

import com.jac.game.audio.Music;
import com.jac.game.audio.Sound;
import com.jac.game.audio.SoundHandler;
import com.jac.game.cutscene.events.*;
import com.jac.game.cutscene.events.actors.MoveActorEvent;
import com.jac.game.cutscene.events.actors.PlayActorAnimationEvent;
import com.jac.game.display.Camera;
import com.jac.game.entities.interact.Door;
import com.jac.game.entities.interact.npc.NPCState;
import com.jac.game.entities.interact.npc.Questline;
import com.jac.game.entities.interact.npc.QuestlineList;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.items.ItemList;
import com.jac.game.main.Game;
import com.jac.game.main.GameInfo;
import com.jac.game.rooms.RoomList;
import com.jac.game.ui.decision.Decision;

public class CutsceneList {

    public static Cutscene spaghetti;
    public static Cutscene haven_door;
    public static Cutscene assistant_intro;
    public static Cutscene labyrinth_briefing;
    public static Cutscene door_tutorial;
    public static Cutscene first_craft_false;
    public static Cutscene first_craft_true;
    public static Cutscene girl_intro;
    public static Cutscene rita_intro;
    public static Cutscene reminder_comeback;
    public static Cutscene girl_item_request;
    public static Cutscene join_shop;
    public static Cutscene girl_bracelet_found;
    public static Cutscene rita_fire;
    public static Cutscene sherlock_lights;

    public static void initCutscenes(){
        spaghetti = new Cutscene(RoomList.room1)
                .withEvent(new Pause(60))
                .withEvent(new SpawnEntityEvent("crane_ninja", 500, 650, 96, 96))
                .withEvent(new MoveActorEvent(0, 0, -330, 1))
                .withEvent(new TextboxEvent("Yes mate"))
                .withEvent(new Pause(120))
                .withEvent(new TextboxEvent("Do you like spaghetti?"))
                .withEvent(new DecisionEvent("Your reply (Spaghetti Joe will remember this)", "Spaghetti",
                        new Decision("Yes", ()->spaghetti.addResultText("Spaghetti", "I rate you bro")),
                        new Decision("No", ()->spaghetti.addResultText("Spaghetti", "Are you mental?"))))
                .withEvent(new PlaySoundEvent(new Sound("entities/crane_ninja/ability/crane_ninja_ability_pounce.wav")))
                .withEvent(new PlayActorAnimationEvent(0, "channel", 9, 36))
                .withEvent(new MoveActorEvent(0, 350, 0, 10));


        haven_door = new Cutscene(RoomList.room_haven)
                .withEvent(new PlaySoundEvent(new Sound("temp/static.wav")))
                .withEvent(new ShakeCameraEvent(4, 12, 180, false))
                .withEvent(new Pause(180))
                //.withEvent(new ChangeMusicEvent(Music.MYSTERY))
                .withEvent(new TextboxEvent("???", "This message is being played to the resident of Haven D-93.", "You have been selected to partake in the Labyrinthian's Trials.", "???: If you can outlast the other 63 contestants, you will get your freedom", "???: If you fail, you will be locked in your Haven forever..."))
                .withEvent(new PanCameraEvent(560, 196, 90))
                .withEvent(new TextboxEvent("???", "If you are not willing to put your life on the line for your Freedom, do not leave your Haven when the door\nopens.", "Otherwise, proceed through to attempt your Initiation.", "If you cannot pass this simple test, we will prevent you from competing in the Trials for your own safety."))
                .withEvent(new PlaySoundEvent(new Sound("temp/static.wav")))
                .withEvent(new ShakeCameraEvent(4, 12, 120, false))
                .withEvent(new Pause(120))
                .withEvent(new TextboxEvent("???",  "Your Haven's door will open shortly.", "The rest is up to you."))
                .withEvent(new PanCameraEvent(40))
                .withEvent(new CustomEvent(()-> Scheduler.getInstance().addTimedAction(420,
                        ()-> playDoorUnlockCutscene(RoomList.door_haven))));


        assistant_intro = new Cutscene(RoomList.room_assistant)
                .withEvent(new CustomEvent(()-> SoundHandler.resumeRoomMusic()))
                .withEvent(new SpawnEntityEvent("assistant", 464, 464, 96, 96))
                .withEvent(new Pause(60))
                .withEvent(new PanCameraEvent(512, 512, 40))
                .withEvent(new TextboxEvent("Caretaker", "//nMy, would you look at that! You're just on time!", "Another one in search of the Surface, I suppose? You're very fortunate indeed.", "You see - each year we populate the Labyrinth with 64 hopefuls - each with their own ideals and dreams of the Surface.", "The Labyrinth then does its job in weeding out the 63 with the weakest wills, transporting them \nback to their Village when they inevitably fail to triumph in her Trials.", "Thus, leaving only one to realise that ideal of theirs.", "This year, we happen to have filled 63 slots, meaning...", "You are in for a chance at attempting to conquer the Labyrinth! How delightful for you.", "Here's the catch - we don't let any old time-waster into the Labyrinth. Although I admire your courage in diving into the unknown...", "//c...courage can often be misplaced.", "To ensure that you are worthy of that final place in this year's tournament...", "//cI will now commence your examination!"))
                //.withEvent(new ChangeMusicEvent(Music.EPIC))
                .withEvent(new PanCameraEvent(40))
                .withEvent(new TextboxEvent("Info", "While moving with WASD, use the RIGHT ARROW KEY to Dash away from the Caretaker's\nattacks and build Momentum!", "When you see an opening, use the LEFT ARROW KEY to Attack - but be wary that\nboth Attacking and getting Attacked will lower your Momentum.", "Attacking with Maximum Momentum will cause more Knockback and will stagger opponents who are in\nthe middle of casting their abilities!"));

        labyrinth_briefing = new Cutscene(RoomList.room_assistant)
                .withEvent(new SpawnEntityEvent("assistant", 464, 320, 96, 96))
                .withEvent(new Pause(60))
                .withEvent(new PanCameraEvent(512, 416, 40))
                .withEvent(new TextboxEvent("Caretaker", "In order to outlast the other contestants, you will need to get stronger. To do so, you should utilise the Labyrinth's intricate design to train yourself.", "Shortly you will be permitted to exit this room and go there, but before then I should make one thing clear.", "The Labyrinth, as well as holding the entrance to the Surface, is a valuable resource in itself.", "You will strengthen yourself in her Combat Trials, acquire useful items, and also crucial information about other contestants", "But like all valuable resources, it is finite. You may not simply roam the Labyrinth at will.", "These entrances will only open between 10AM and 6PM every day. \nWhich reminds me - you're going to have to keep an eye on the time while you're here.", "Very well, in that case..."))
                .withEvent(new MoveActorEvent(0, 0, 128, 3))
                .withEvent(new CustomEvent(()-> GameInfo.getInstance().activatePocketwatch()))
                .withEvent(new TextboxEvent("Info", "You received the Pocketwatch!"))
                .withEvent(new TextboxEvent("Caretaker", "When its hands show 6pm, be sure to return to this room, lest you miss the Grand Trial, and be disqualified.", "You see, this room is now your Haven - you need not worry about other contestants or surprises the Labyrinth has to offer while you stay here.", "You should note that all other contestants have their own rooms in the Labyrinth too.\nIf you come across a contestant, be free to share your knowledge with them - you all share the same objective after all!", "Talk to them and I'm sure they will help you in return... \n...most of them, anyway.", "But don't get too attached - those Grand Trials I mentioned earlier happen at the end of each day, \nand will pit you against another contestant in a one-on-one duel!", "The loser is exiled from the Labyrinth, and the winner gets to remain here for one more day"))
                .withEvent(new Pause(60))
                .withEvent(new TextboxEvent( "Caretaker", "Well I think that should end your briefing. As I said earlier, I have confidence in you, so there's no need for me to babysit you any further.", "And would you look at that - it's just about 10AM.", "The Labyrinth will open any time now. Do what you must to prepare for the duel tonight.", "//cI'm counting on you.", "Oh, and if you have any questions about the Labyrinth, I will ensure to answer precisely all that I am allowed to disclose. \nI will be waiting in this room until the Grand Trial tonight."))
                .withEvent(new PanCameraEvent(40));

        door_tutorial = new Cutscene(RoomList.room_r1_door)
                .withEvent(new TextboxEvent("Curious Contestant", "/hHey man! You're a contestant too right? I can tell since you\ndon't hide your face like the other creeps...", "Wait, could I ask you something real quick?"))
                .withEvent(new PanCameraEvent(482, 0, 40))
                .withEvent(new TextboxEvent("Curious Contestant", "Is that door flashing red for you?"))
                .withEvent(new DecisionEvent("What should I say?", "You",
                        new Decision("Yes", ()->{
                            door_tutorial.addResultText("Curious Contestant", "/hI thought that might be the case... that would usually mean that the \ndoor is locked, right?", "But I just literally walked straight through that door moments ago. It opened no problem.", "On top of that, it doesn't even appear to be flashing to me...\nThrough my eyes it just looks like any other door.", "My guess is that the Labyrinth itself is somehow deciding who gets to go where...\nSpooky, right?", "Hey, maybe if you go investigate, you might find out why it won't open for you.");
                            door_tutorial.addResultText("You", "Yeah, it is.");
                            QuestlineList.getInstance().progressQuestline(1);
                        }), new Decision("No", ()->{
                            door_tutorial.addResultText("Curious Contestant", "Oh, wait really? I see...\nIn that case, I'd guess that you've already completed the required Trial.", "If it starts flashing, let me know. I need to figure out how this Labyrinth place works!");
                            door_tutorial.addResultText("You", "No, it looks fine to me.");
                }))).withEvent(new PanCameraEvent(40));


        first_craft_false = new Cutscene(RoomList.room_r3_double_door)
                .withEvent(new TextboxEvent("Alain", "Ah hello, brother! \nThe name's Alain - a Contestant, just like you.", "You seem like the intellectual type. We'll get along brilliantly!\nI'm an Alchemist you see - and this Labyrinth is filled with delightful ingredients!", "I'll tell you what - If you can bring me 2 vials of Labyrinthian Flux, \nI can make an amazing potion for you!", "Once you see what Alchemy can do, you won't be able to resist being my friend!"));

        first_craft_true = new Cutscene(RoomList.room_r3_double_door)
                .withEvent(new TextboxEvent("Alain", "Ah hello, brother! \nThe name's Alain - a Contestant, just like you.", "//hYou seem like the intellectual type. We'll get along brilliantly!\nI'm an Alchemist you see - and this Labyrinth is filled with delightful ingredients!", "Oh my, and that scent - you've acquired some Labyrinth Flux, haven't you?\nIf you'd like, I could make that into a fantastic Potion for your troubles!"))
                .withEvent(new DecisionEvent("Give Alain 2x Labyrinthian Flux?", "You",
                        new Decision("Yes", ()->{
                            first_craft_true.addResultText("Alain", "I haven't finished this yet!");
                            GameInfo.getInstance().getInventory().addItem(ItemList.HEALTH_POTION);
                            GameInfo.getInstance().getInventory().removeItem(ItemList.LABYRINTH_FLUX);
                            GameInfo.getInstance().getInventory().removeItem(ItemList.LABYRINTH_FLUX);
                            QuestlineList.getInstance().progressQuestline(2);
                        }), new Decision("No", ()->{
                            first_craft_true.addResultText("Alain", "My my, that is a shame. And here I thought\nI'd found someone that I could share my beautiful craft with.", "If you have a change of heart, please let me know. I could \nshow you all kinds of whimsical recipes!");
                })));

        girl_intro = new Cutscene(RoomList.room_r1_door)
                .withEvent(new ChangeMusicEvent(Music.RITA_ENCOUNTER))
                .withEvent(new SpawnEntityEvent("rita", 544, 396, 96, 96))
                .withEvent(new MoveActorEvent(0, 396, 0, 4))
                .withEvent(new MoveActorEvent(0, 0, -64, 2))
                .withEvent(new MoveActorEvent(0, 128, 0, 4))
                .withEvent(new MoveActorEvent(0, 0, 32, 4))
                .withEvent(new ShakeCameraEvent(8, 12, 36, true))
                .withEvent(new PlayActorAnimationEvent(0, "channel", 6, 24))
                .withEvent(new MoveActorEvent(0, 0, -33, 2))
                .withEvent(new MoveActorEvent(0, 0, 1, 1))
                .withEvent(new TextboxEvent("Girl", "Oh my gosh, I'm so sorry! Are you okay?", "That's the second time that's happened today now...\nI get so clumsy when I'm nervous."))
                .withEvent(new TextboxEvent("You", "That's alright, I'm fine. More importantly, are you okay? \nYou should be more careful - especially in a place like this"))
                .withEvent(new TextboxEvent("Girl", "I-I know... I'm really sorry. But I really need to get to the Sur-", "Hold on a second!"))
                .withEvent(new MoveActorEvent(0, 0, 32, 2))
                .withEvent(new TextboxEvent("Girl", "Your eyes... They're GREEN?!", "That's amazing! That's like super rare nowadays!"))
                .withEvent(new TextboxEvent("You", "I guess it is - my family are the only ones with green eyes from my village.\nBut is it really that big of a deal?"))
                .withEvent(new TextboxEvent("Girl", "Yes it is! And hey, maybe it's a sign or something!\nThey say that on the surface, the whole floor is green, right?"))
                .withEvent(new TextboxEvent("You", "Uh, yeah, I've heard that too but-", "...", "Ahem..."))
                .withEvent(new TextboxEvent("Girl", "Ah! Sorry I didn't mean to stare..."))
                .withEvent(new MoveActorEvent(0, 0, -33, 3))
                .withEvent(new MoveActorEvent(0, 0, 1, 1))
                .withEvent(new TextboxEvent("Girl", "If the Surface is covered with green as they say, then it must be\nbeautiful up there!", "I feel even more motivated to win now!", "Oh - my name is Rita by the way. You seem like a decent person.", "There's a lot of intense people around here so it's nice to bump into someone more my pace!"))
                .withEvent(new TextboxEvent("You", "You certainly did bump into me - a bit more literally than I would've liked..."))
                .withEvent(new TextboxEvent("Girl", "Ahahah! You're a funny guy!", "Hey, so my Haven is to the West of here.", "If you ever get access to that area, come give me a visit!\nWe can share our plans to escape this place, or something!", "See ya around!"))
                .withEvent(new MoveActorEvent(0, -620, 0, 4))
                .withEvent(new MoveActorEvent(0, 0, -196, 4))
                .withEvent(new ChangeMusicEvent(null))
                .withEvent(new TextboxEvent("You", "...phew...", "She's right about this place being full of intense people, that's for sure.", "That other door leading out of my Haven, to the West... \nThat would probably take me towards hers. But last I checked, it was locked.", "Maybe I should do a bit more exploring first."))
                .withEvent(new ChangeMusicEvent());

        rita_intro = new Cutscene(RoomList.room_r2_arena)
                .withEvent(new TextboxEvent("Yu", "Hmm? Where did he go? I guess that means... I passed?"))
                .withEvent(new TextboxEvent("Rita", "That's right! Don't be so shocked!"))
                .withEvent(new SpawnEntityEvent("rita", 800, 544, 96, 96))
                .withEvent(new PanCameraEvent(432, 544, 40))
                .withEvent(new MoveActorEvent(0, -256, 0, 4))
                .withEvent(new TextboxEvent("Rita", "Although, I gotta admit, I am too. For someone like you to get through such a tough first combat trial. It's commendable."));

        girl_item_request = new Cutscene(RoomList.room_l3_corridor)
                .withEvent(new SpawnEntityEvent("rita", 336, 208, 96, 96))
                .withEvent(new TextboxEvent("Girl", "Ah, you came!"))
                .withEvent(new ChangeMusicEvent(Music.RITA_ENCOUNTER))
                .withEvent(new PanCameraEvent(0, 384, 40))
                .withEvent(new MoveActorEvent(0, 0, 196, 3))
                .withEvent(new TextboxEvent("Girl", "Good to see you again! I hope getting here wasn't too much of a struggle for you!", "I doubt that very much though - my instinct tells me you're more than capable.", "My Haven is just through here - let's go!"))
                .withEvent(new MoveActorEvent(0, 0, -128, 3))
                .withEvent(new Pause(60))
                .withEvent(new ChangeMusicEvent(null))
                .withEvent(new TextboxEvent("Girl", "...", "Wait a second... Where did I put that...", "..Oh no. This is bad. I must've dropped it somewhere in the Labyrinth..."))
                .withEvent(new MoveActorEvent(0, 0, 64, 5))
                .withEvent(new TextboxEvent("Girl", "Hey, listen...", "I have this Bracelet - it's got 9 charms on it. To say that it's\nimportant to me would be an understatement...", "I don't have time to explain, but... I can't see myself getting to the Surface without it.", "Could you do me a huge favour and help me look for it?", "Sorry to ask so much, but I really need that back - before some scavenger picks it up.", "I'll look around, but I won't go far from here. If you find it... please...\nbring it to me."))
                .withEvent(new PanCameraEvent(40))
                .withEvent(new CustomEvent(()->QuestlineList.getInstance().progressQuestline(4)));

        reminder_comeback = new Cutscene(RoomList.room_r5_arena_2)
                .withEvent(new SpawnEntityEvent("assistant", 176, 568, 96, 96))
                .withEvent(new MoveActorEvent(0, 164, -100, 3))
                .withEvent(new MoveActorEvent(0, 128, -128, 3))
                .withEvent(new TextboxEvent("Assistant", "Ah, here you are. I must say - I'm impressed\nThat you've made it this far already.", "I thought I should let you know that the door on the West side of our Haven is now open.\nI recommend you to go explore there. You should be making the most out of your time here after all.", "Oh and also, I assume you've noticed that many of the Contestants, as well as us Labyrinth Dwellers,\nhave the ability to perform some powerful abilities.", "They're known as Fluxcasting Techinques, or Fluxtechs for short, named after the Flux\nused to power them, which flows around the Labyrinth.", "I think you're ready to learn a very basic Fluxtech, developed by yours truly!\nHere - we'll start with the basics:"))
                .withEvent(new CustomEvent(()-> {
                    Camera.fade(true);
                    GameInfo.getInstance().getInventory().addItem(ItemList.SKILL_JUMP);
                    RoomList.door_haven_w.unlock();
                }))
                .withEvent(new Pause(120))
                .withEvent(new CustomEvent(()-> Camera.fade(false)))
                .withEvent(new Pause(40))
                .withEvent(new TextboxEvent("Info", "You learned the 'Vaulting Crush' Fluxtech!", "You may browse and equip your known Fluxtechs in the third tab of your Inventory.", "Then, you may cast your equipped Fluxtech in battle by pressing the UP ARROW KEY.", "Experiment with different Fluxtechs to get the advantage over different opponents!"))
                .withEvent(new Pause(40))
                .withEvent(new TextboxEvent("Assistant", "Right then, I must get back to keeping watch over the Haven.", "If you need advice, as always feel free to approach me.", "As hard as you may find it to believe, I am on your side, after all."));

        join_shop = new Cutscene(RoomList.room_r10_last)
                .withEvent(new TextboxEvent("Milo", "Hey, you're one of the new Contestants huh? Boy have you found yourself\nthe deal of a lifetime!", "My name is Milo, and I own the best store in the entire Labyrinth:\nMilo's Emporium for Awesome Individuals!", "...The name's a work in progress. But that doesn't change that I sell some awesome\nproducts, no doubt!", "Before you can enter, we just gotta check that you truly are an Awesome Individual.\nWe charge a membership fee - and I know what you're thinking; 'That's a rip-off'\n, right? WRONG!", "For the low, low price of 1000 Coins, I'll give you lifetime\n access to the store! Plus I'll throw in this Charm Bracelet that I found for no extra cost!", "Oh, seems like you don't have the money. Come back when you're ready to start shopping smart!"))
                .withEvent(new DecisionEvent("Pay 1000 to become an 'Awesome Individual' (and receive the Charm Bracelet)?", "You",
                        new Decision("Yes", ()->{
                            join_shop.newTempEvent(new MoveActorEvent(0, 256, 396, 4));
                            join_shop.addResultText("Milo", "Great! Pleasure doing business with you. Here's your weird bracelet thingy.\nI'll tinker with the door to make sure you're allowed in from now on.", "See ya inside!");
                            join_shop.newTempEvent(new SpawnEntityEvent("milo", 420, 420, 96, 96));
                            GameInfo.getInstance().getInventory().addItem(ItemList.BRACELET);
                            QuestlineList.getInstance().progressQuestline(3);
                            QuestlineList.getInstance().shopkeeper.updateState();
                            RoomList.door_shop.unlock();
                        }),
                        new Decision("No", ()->{
                            join_shop.addResultText("Milo", "No? That's a shame. If you change your mind, please come back!\nYou never know, you might need that Bracelet someday!");
                })));

        girl_bracelet_found = new Cutscene(RoomList.room_l3_corridor)
                .withEvent(new TextboxEvent("Girl", "Hey, did you find it?", "...you did? Thank goodness... or rather, thank YOU! I owe you big time.\nIf you ever have a problem, feel free to come tell me. ", "I'll let my assistant know to let you into my Haven from now on.\nIn fact, let's go in now - as I promised earlier! There's something I'd like to discuss,\nbut I haven't found anyone trustworthy enough to talk to yet.", "...until now!"))
                .withEvent(new Pause(40))
                .withEvent(new TextboxEvent("Info", "You and Rita are now Friends!\nYou may now visit each other's Havens."))
                .withEvent(new Pause(40))
                .withEvent(new SpawnEntityEvent("rita", 420, 420, 96, 96))
                .withEvent(new CustomEvent(()->{
                    QuestlineList.getInstance().progressQuestline(4);
                    QuestlineList.getInstance().rita.updateState();
                    RoomList.door_rita.unlock();
                }))
                .withEvent(new TextboxEvent("Girl", "See ya inside!"))
                .withEvent(new MoveActorEvent(0, -128, -256, 3));

        rita_fire = new Cutscene(RoomList.room_l4_rita_haven)
                .withEvent(new ChangeMusicEvent(null))
                .withEvent(new CustomEvent(()->RoomList.room_l4_rita_haven.removeEntity(RoomList.room_l4_rita_haven.getPlayer())))
                .withEvent(new SpawnEntityEvent("rita", 340, 424, 96, 96))
                .withEvent(new SpawnEntityEvent("player", 340, 544, 96, 96))
                .withEvent(new TextboxEvent("Girl", "Well, here we are - my humble abode! I've been told it's a bit\nsmaller than usual, but I think it's pretty cosy!", "Ooh that reminds me - watch this!"))
                .withEvent(new MoveActorEvent(0, 96, -64, 3))
                .withEvent(new MoveActorEvent(0, -96, -64, 3))
                .withEvent(new TextboxEvent("Girl", "Take a seat - right there!"))
                .withEvent(new MoveActorEvent(1, 0, -96, 3))
                .withEvent(new PanCameraEvent(0, 384, 30))
                .withEvent(new TextboxEvent("Girl", "You might already be aware, but there are some people who are\nborn into this world with a heightened sensitivity to Flux.", "Those people can use powerful Fluxtechs from anywhere, rather than just in the Labyrinth,\nand the techniques they use are usually unique and difficult for anyone else to perform.", "I happen to be one of those people.", "Watch this -"))
                .withEvent(new CustomEvent(()->RoomList.firepit.ignite()))
                .withEvent(new TextboxEvent("Girl", "Yep - since I can remember, I've been able to generate this electric-like energy.\nPretty cool, right?", "I thought so too at first - it's definitely useful for self-defense and all that.", "But if you were given such an amazing gift, without even working for it - wouldn't you feel\nlike you have to make the most of it?"))
                .withEvent(new ChangeMusicEvent(Music.BIG_SAD))
                .withEvent(new PlayActorAnimationEvent(0, "sit", 100))
                .withEvent(new Pause(150))
                .withEvent(new TextboxEvent("Girl", "It took me a long time to decide to come here. I love everyone in my Village so much,\nand I know that if things go badly, I might not see them again.", "Last year, they suddenly stopped introducing new residents to our Village, and so there were\nonly 80 of us.And despite that, we still couldn't get enough supplies.", "The Labyrinthians would only send us one small drink a day each, and 2 meals. Little did we\nknow that things would only get worse...", "They started skipping days. We would eagerly wait by the Portal for our supplies, just to\nreceive nothing at all. That Winter, 80 dropped to 76.", "The hardened, family-man Graham who gave up his water to his children...\nThe calm and spiritual Rhyn, who smiled through it all...", "Hana - One of the leaders of the Village, who got too selfless when rationing the food...", "...and my Granddad, Rodney, who gave me this shirt for my 19th Birthday...\nThey were all stolen away from us.", "Every day we would beg for more supplies to be distributed to our Village...\nbut those rotten Labyrinth Dwellers didn't move a muscle.", "It's like they wanted us to disappear."))
                .withEvent(new Pause(120))
                .withEvent(new TextboxEvent("Girl", "Hey... why do you think the worst, most heartless people have been put in charge of the\nlives of everyone in the entire Undersystem?", "Why is it that they get to decide who goes free, to the Surface where food and water are\nso plentiful?", "It's completely unfair, right?! They're below human! Yet we worship them and rely on them for\neverything in our lives!", "My village in particular - due to our circumstances, we can't help but suck up to them, and I\nbet that's exactly what they want!", "And that's why, when I was born, the villagers all found a new hope. They'd never seen anyone\nthat had such an aptitude for Fluxcasting before. I was like a good omen to them.", "'That girl could easily go to the Labyrinth, reach the Surface, and save us!' \nI knew that that was what they were thinking whenever they'd smile at me.", "And that's what brought me here, I suppose. The duty I have been born with.\nAnd on top of that...", "My hatred of those masked idiots who stand around here comfortably while others die.\nAnd the worst one of all, the Labrynthian, who gives them all their orders.", "He's the one that set up this cruel game. Probably just because he doesn't want scum like us\nruining his precious view up there."))
                .withEvent(new Pause(120))
                .withEvent(new PlayActorAnimationEvent(0))
                .withEvent(new Pause(30))
                .withEvent(new ChangeMusicEvent(null))
                .withEvent(new TextboxEvent("Girl", "Phew... I'm sorry about that, but it feels good to get off my chest. You're a really good person for hearing me out, you know.", "I'm sure you've got a good reason for being here too, but I won't pry.\nLet's just get through this together, eh?", "We'll help each other get stronger, and meet each other on the Surface!\nThen we can work together to take down this rotten system!", "Anyway - we've only got a couple of hours left until they 'Assess' us, whatever that means...\nWe better use that time carefully, and explore the Labyrinth while we can.", "Good luck to you, my friend! I'll see you around."))
                .withEvent(new CustomEvent(()->RoomList.room_l4_rita_haven.addEntity(RoomList.room_l4_rita_haven.getPlayer())))
                .withEvent(new PanCameraEvent(40))
                .withEvent(new ChangeMusicEvent());

            sherlock_lights = new Cutscene(RoomList.room_r8_arena_3)
                .withEvent(new TextboxEvent("Bomes", "My, this arrangement of colours must be intentional.\nFour lights in a row... My dear friend, what do you think this represents?"))
                .withEvent(new DecisionEvent("What do the lights represent?", "You", new Decision("A Warning", ()->{
                    sherlock_lights.addResultText("Bomes", "An interesting take, but not what I would conclude.\nIf you have any other ideas, please return to me.");
                }), new Decision("A Code", ()->{
                    sherlock_lights.addResultText("Bomes", "Perhaps you will be of great use to me after all, Watson!", "If I were you, I would take some time to memorise this code.\nWe don't know if it's meant for us, or if it's a way for the Dwellers to send signals...", "...but either way, it has to be important to solving the mystery of the Labyrinth!");
                    sherlock_lights.addResultText("You", "That's not my name, it's -");
                    sherlock_lights.addResultText("Bomes", "...that's it! I knew you would know the answer, my dear Watson!");
                }), new Decision("Decoration", ()->{
                    sherlock_lights.addResultText("Bomes", "Unfortunately, that theory falls apart as soon as you consider one simple fact -\nAlmost every other torch we've seen so far has has a purple flame - we can assume that they use Flux for fuel...", "So why would they change that for this case only?\nIf it is decoration, they would have terrible taste.");
                })));

        }



    public static void playDoorUnlockCutscene(Door door){
        new Cutscene(door.getRoom())
                .withEvent(new PanCameraEvent(door.getX() + door.getWidth()/2, door.getY() + door.getHeight()/2, 30))
                .withEvent(new Pause(40))
                .withEvent(new CustomEvent(()->door.unlock()))
                .withEvent(new PlaySoundEvent(new Sound("entities/crane_ninja/hit/crane_ninja_hit_1.wav")))
                .withEvent(new Pause(60))
                .withEvent(new PanCameraEvent(40)).play();
    }
}
