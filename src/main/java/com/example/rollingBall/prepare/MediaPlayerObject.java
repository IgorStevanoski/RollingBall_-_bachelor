package com.example.rollingBall.prepare;

import com.example.rollingBall.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerObject {

    private Media media;
    private MediaPlayer mediaPlayer;
    private Media mediaMusic;
    private MediaPlayer mediaPlayerMusic;

    private static Media musicFile = new Media(Main.class.getClassLoader().getResource("music.mp3").toExternalForm());

    public MediaPlayerObject() {
        //if (file.exists()){
            //mediaMusic = new Media(file.toURI().toString());
            mediaPlayerMusic = new MediaPlayer(musicFile);
            mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE);
            if (Main.isMusic_on()) playMusic();
        //}
    }

    public void playMedia() {}

    /** Za klik na dugme. */
    public void playButtonSound() {
        playSound("Button1.mp3");
    }
    public void playButton2Sound() {
        playSound("Button2.mp3");
    }
    /** Za gresku pri kliku na dugme. */
    public void playButton3Sound() {
        playSound("Remove.mp3");
    }
    /** Za combobox. */
    public void playButton4Sound() {
        playSound("Button4.mp3");
    }
    public void playPutSound() {
        playSound("Put.mp3");
    }
    public void playRemoveSound() {
        playSound("Remove.mp3");
    }
    public void playPauseSound() {
        playSound("Pause.mp3");
    }
    public void playLevelDoneSound() {
        playSound("levelDone2.mp3");
    }
    public void playGameDoneSound() {
        playSound("GameDone.mp3");
    }
    public void playCoinSound() {
        playSound("Coin.mp3");
    }
    public void playBonusBallSound() {
        playSound("BonusBall.mp3");
    }
    public void playStopwatchSound() {
        playSound("Stopwatch.mp3");
    }
    public void playIceSound() {
        playSound("Ice.mp3");
    }
    public void playWallHitSound() {
        playSound("WallHit.mp3");
    }
    public void playInHoleSound() {
        playSound("InHole.mp3");
    }
    public void playInBadHoleSound() {
        playSound("BadHole.mp3");
    }
    public void playBounceSound() {
        playSound("Bounce.mp3");
    }

    public void playSound(String path){
//        File file = new File(path);
//        if (file.exists()){
            if (!Main.isSound_on()) return;
//            media = new Media(file.toURI().toString());
        try {
            media = new Media(Main.class.getClassLoader().getResource(path).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(Main.getSound_volume() * 0.01);
            mediaPlayer.play();
        } catch (NullPointerException e) {

        }
//        }
    }

    public void playMusic(){
        mediaPlayerMusic.setVolume(Main.getMusic_volume() * 0.01);
        mediaPlayerMusic.play();
    }
    public void stopMusic(){
        mediaPlayerMusic.stop();
    }
    public void changeMusicVolume(){
        mediaPlayerMusic.setVolume(Main.getMusic_volume() * 0.01);
    }

    public void playStopMusic(boolean play){
        if (play){
            mediaPlayerMusic.play();
        } else {
            mediaPlayerMusic.stop();
        }
    }

}
