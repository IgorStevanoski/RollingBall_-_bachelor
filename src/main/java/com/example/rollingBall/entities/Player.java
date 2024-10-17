package com.example.rollingBall.entities;

import com.example.rollingBall.Main;

public class Player {
    private String name;
    private Terrain currentTerrain; // teren na kojem se igra trenutna igra
    private boolean activeGame;
    private int difficulty;
    private double musicVolume;
    private double soundVolume;
    private boolean music_on;
    private boolean sound_on;

    public void advanceLevel() {
        if (currentTerrain.advanceLevel() == 0) {
            for (int i = 0; i < Main.getTerrains().size(); i++) {
                if (Main.getTerrains().get(i).getName().equals(currentTerrain.getName())){
                    Terrain terrain = new Terrain(Main.getTerrains().get(i));
                    terrain.setPoints(currentTerrain.getPoints());
                    terrain.setRemainingLifePoints(currentTerrain.getRemainingLifePoints());
                    terrain.setRemainingTime(currentTerrain.getRemainingTime());
                    terrain.setStageFactor(currentTerrain.getStageFactor());
                    this.currentTerrain = terrain;
                }
            }
        }
    }

    public Player(String name) {
        this(name, null);
    }

    public Player(String name, Terrain terrain) {
        this.name = name;
        this.currentTerrain = terrain;
        this.activeGame = false;
        this.difficulty = 0;
        this.musicVolume = 50;
        this.soundVolume = 50;
        this.music_on = true;
        this.sound_on = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Terrain getCurrentTerrain() {
        return currentTerrain;
    }

    public void setCurrentTerrain(Terrain currentTerrain) {
        this.currentTerrain = currentTerrain;
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
    }

    public double getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(double soundVolume) {
        this.soundVolume = soundVolume;
    }

    public boolean isMusic_on() {
        return music_on;
    }

    public void setMusic_on(boolean music_on) {
        this.music_on = music_on;
    }

    public boolean isSound_on() {
        return sound_on;
    }

    public void setSound_on(boolean sound_on) {
        this.sound_on = sound_on;
    }
}
