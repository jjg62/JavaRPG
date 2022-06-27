package com.jac.game.audio;

import com.jac.game.utils.FileUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Optional;

public class SoundHandler {

    public static SoundHandler instance;

    private Music roomMusic;
    private Music music;
    private boolean playingEventMusic;

    public static Clip loadClip(String s) {
        try {
            Optional<File> fileOptional = Optional.ofNullable(FileUtils.loadFile(s));
            if (fileOptional.isPresent()) {

                File file = fileOptional.get();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();

                clip.open(ais);
                return clip;

            } else {
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SoundHandler() {
        instance = this;
    }

    public static void play(Clip clip){
        instance.playSound(clip);
    }

    public void playSound(Clip clip){
        if(clip == null) return;
        clip.setFramePosition(0);
        clip.start();
    }

    public static void changeMusic(Music music){
        stopMusic();
        instance.music = music;
        if(music != null) music.play();
        instance.playingEventMusic = true;
    }

    public static void resumeRoomMusic(){
        changeMusic(instance.roomMusic);
        instance.playingEventMusic = false;
    }

    public static void changeRoomMusic(Music music){
        if(music != instance.roomMusic){
            instance.roomMusic = music;
            if(!instance.playingEventMusic) resumeRoomMusic();
        }
    }

    public static void stopMusic(){
        if(instance.music != null) instance.music.stop();
    }

}
