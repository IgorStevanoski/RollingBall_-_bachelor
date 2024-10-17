package com.example.rollingBall.prepare;

// sluzi da se ucitaju ispravni podaci za pocetak igre
public class MiscObject {

    public String currentPlayer;
    public String currentTerrain;
    public double screen_width;
    public double screen_height;
    public double screen_positionX;
    public double screen_positionY;
    public boolean fullscreen;
    public boolean maximized;

    public MiscObject(String currentPlayer, String currentTerrain, double screen_width, double screen_height,
                      double screen_positionX ,double screen_positionY, boolean fullscreen, boolean maximized) {
        this.currentPlayer = currentPlayer;
        this.currentTerrain = currentTerrain;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.screen_positionX = screen_positionX;
        this.screen_positionY = screen_positionY;
        this.fullscreen = fullscreen;
        this.maximized = maximized;
    }

}
