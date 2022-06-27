# JavaRPG
Unfinished top-down RPG made in Java from scratch, over the summer of 2020.
I also had a go at making the sprites and music!

[GAMEPLAY VIDEO](https://www.youtube.com/watch?v=0yfHsKgaNKY)

Code is in src/com/jac/game, entry point at main/Launcher.java.
Regrettably the code is messy at parts (as it was just a fun side-project) and needs more commenting, I plan to clean things up at some point in the future.

## Engine
The engine supports
- Enemies with custom attack patterns and behaviours (see entities/enemies/dweller/MeleeDweller.java for example)
- Cutscenes (see cutscene/CutsceneList.java for examples)
- Rooms and Combat Encounters with multiple phases (see rooms/RoomList.java for examples: line 209-234 shows an example combat encounter with 3 phases)
- NPCc, Merchants and Questlines (see entities/interact/npc/QuestlineList.java for examples)
- RPG-style inventory with multiple tabs


## Credits
