package com.example.rollingBall.entities;

public class LeaderboardEntry {

    private String player;
    private int score;

    public LeaderboardEntry(Player player, int score) {
        this.player = player.getName();
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayerName() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
