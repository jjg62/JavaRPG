package com.jac.game.ui.speakers;

import java.util.HashMap;

public class SpeakerList {

    public static HashMap<String, Speaker> speakerList = new HashMap<>(){{
        put("Caretaker", new Speaker("assistant"));
        put("Alain", new Speaker("alain"));
        put("Girl", new Speaker("rita"));
        put("Bomes", new Speaker("jones"));
        put("Curious Contestant", new Speaker("edge"));
    }};


}
