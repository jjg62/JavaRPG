package com.jac.game.ui;

import com.jac.game.audio.Sound;
import com.jac.game.control.Controller;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.IAction;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.main.Game;
import com.jac.game.rooms.InteractingManager;
import com.jac.game.ui.speakers.Speaker;
import com.jac.game.ui.speakers.SpeakerList;
import com.jac.game.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Textbox {

    public static BufferedImage panel = FileUtils.loadImage("/textures/ui/textbox.png");
    private static Sound beep = new Sound("beep.wav");

    protected InteractingManager talkingManager;
    private String text;
    private IAction finishTextAction;
    protected String speaker;
    private double charactersToShow;

    private char emotion = 'n';
    private boolean playSound;
    private boolean showImage;
    private BufferedImage image;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int border;

    private final int unclosableTime = 15;
    private static final int lineGap = 14;
    protected boolean closable = false;
    private boolean skippable = false;
    private boolean changingClosable = false;

    public Textbox(InteractingManager talkingManager, String speaker, String text, IAction finishTextAction){
        this.talkingManager = talkingManager;
        this.text = text;
        this.speaker = speaker;
        unwrapText();
        this.finishTextAction = finishTextAction;

        //Dimensions
        border = 32;
        width = Game.width - border*2;
        height = 128;
        x = border;
        y = Game.height - height - border;

    }

    private void unwrapText() {
        if(text.charAt(0) == '/'){
            if(text.charAt(1) == '/'){
                playSound = true;
                emotion = text.charAt(2);
                text = text.substring(3);
            }else{
                emotion = text.charAt(1);
                text = text.substring(2);
            }
        }
        Speaker speakerObj = SpeakerList.speakerList.get(speaker);

        if(speakerObj != null) {
            image = speakerObj.getImage(emotion);
            showImage = true;
        }
    }

    public void open(){
        beep.play();
        closable = false;
        skippable = false;
        Scheduler.getInstance().addTimedAction(unclosableTime, () -> skippable = true);
        if(playSound) SpeakerList.speakerList.get(speaker).playDialogueSound(emotion);
        charactersToShow = 0;
    }

    public void tick(){
        checkClose();
        checkSkip();
    }

    private void checkSkip(){
        if(skippable && Controller.talk()){
            charactersToShow = text.length();
        }
    }

    private void checkClose(){
        if(closable && Controller.talk()){
            close();
        }
    }

    protected void close(){
        finishTextAction.run();
    }

    public void render(GameGraphics graphics){

        /*
        graphics.setGColour(Color.white);
        graphics.fillStaticRectangle(x, y, width, height);
        graphics.setGColour(Color.black);
        graphics.drawStaticRectangle(x, y, width, height);
        */


        graphics.drawStatic(panel, x, y, width, height);
        if(showImage) graphics.drawStatic(image, x + 20, y + 44, 64, 64);
        renderText(graphics);
    }

    protected void renderText(GameGraphics graphics){

        //Name
        graphics.drawString(speaker,x + 96 - speaker.length() * 4, y + 20, 14, Color.WHITE);

        String lines[] = text.substring(0, (int)charactersToShow).split("\n");
        for(int line = 0; line < lines.length; line++){
            graphics.drawString(lines[line], x + 96, y + 48 + line * lineGap, 14, Color.WHITE);
        }

        if(closable){
            graphics.drawString("Press E to Continue", x + width*13/16, y + height*7/8, 14, Color.WHITE);
        }

        if(charactersToShow < text.length()){
            charactersToShow += 1;
        }else{
            if(!changingClosable){
                changingClosable = true;
                Scheduler.getInstance().addTimedAction(unclosableTime, ()->closable = true);
            }

        }
    }
}
