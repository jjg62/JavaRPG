package com.jac.game.display;

import javax.swing.*;
import java.awt.*;

public class Display {

    //Window
    private JFrame frame;
    private JFrame fullscreenFrame;

    //Canvas on Window
    private Canvas canvas;

    //Window settings
    private int width;
    private int height;
    private String title;

    private float scale = 1.0f;

    public Display(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;

        //Setup the display
        initDisplay();
        //fullscreenMode();
    }

    private void initDisplay(){
        scale = 1.25f;
        initFrame();
        initCanvas();
        frame.add(canvas);
        frame.pack();
    }

    private void initFrame(){
        frame = new JFrame(title);

        frame.setSize((int)(scale*width), (int)(scale*height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //Appear at center of screen
        frame.setResizable(false);
        frame.setVisible(true);
    }


    private void initCanvas(){
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension((int)(scale*width), (int)(scale*height)));
        canvas.setMinimumSize(new Dimension((int)(scale*width), (int)(scale*height)));
        canvas.setMaximumSize(new Dimension((int)(scale*width), (int)(scale*height)));
        canvas.setFocusable(false);
    }

    public void fullscreenMode(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        scale = height / (this.height / 1.0f);
        int borderWidth = (width - (int)(scale*this.width))/2;

        JFrame fullscreenFrame = new JFrame(title);

        fullscreenFrame.setLayout(new FlowLayout());
        fullscreenFrame.getContentPane().setBackground(Color.black);
        fullscreenFrame.setLocationRelativeTo(null);
        fullscreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fullscreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fullscreenFrame.setUndecorated(true);
        fullscreenFrame.setResizable(false);
        fullscreenFrame.setVisible(true);

        initCanvas();

        fullscreenFrame.add(Box.createRigidArea(new Dimension(borderWidth, 0)));
        fullscreenFrame.add(canvas);
        fullscreenFrame.add(Box.createRigidArea(new Dimension(borderWidth, 0)));

        fullscreenFrame.pack();

        frame = fullscreenFrame;
    }

    /*
    Getters
    */
    public Canvas getCanvas(){
        return canvas;
    }

    public Frame getFrame(){
        return frame;
    }

    public float getScale(){
        return scale;
    }
}
