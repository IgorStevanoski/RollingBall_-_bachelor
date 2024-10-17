package com.example.rollingBall.entities;

import com.example.rollingBall.Const;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Terrain {

    private String      name;
    private String      playerName; // Creator
    private Integer     points;
    private Double      remainingTime;
    private Double      remainingFreezeTime;
    private Integer     remainingLifePoints;
    private Leaderboard leaderboard;
    private LocalDate   date;

    private List<Level> levels = new ArrayList<>();
    private Level       currentLevel;
    private int         currentLevelNUM;
    private int         stageFactor;

    public Terrain(Terrain terrain) { // duboka kopija
        this.name = terrain.name;
        this.playerName = terrain.playerName;
        this.leaderboard = terrain.leaderboard;
        this.date = terrain.date;
        this.currentLevel = null;
        this.currentLevelNUM = 0;
        this.stageFactor = 0;
        this.points = 0;
        this.remainingLifePoints = Const.MAX_LIFE_POINTS;
        this.remainingTime = Const.TIME_LIMIT;
        this.remainingFreezeTime = terrain.remainingFreezeTime;
        for (Level l : terrain.levels) {
            addLevel(new Level(l));
        }
    }

    public Terrain(String name) {
        this.name = name;
        this.leaderboard = new Leaderboard();
        this.date = LocalDate.now();
        this.currentLevel = null;
        this.currentLevelNUM = 0;
        this.stageFactor = 0;
        this.points = 0;
        this.remainingLifePoints = Const.MAX_LIFE_POINTS;
        this.remainingTime = Const.TIME_LIMIT;
        this.remainingFreezeTime = 0.;
    }

    public int advanceLevel() {
        if (currentLevelNUM++ == levels.size() - 1){
            currentLevelNUM = 0;
            stageFactor++;
        }
        remainingTime = Const.TIME_LIMIT;
        if (stageFactor > 0) {
            remainingTime *= Math.pow(Const.STAGE_TIME_FACTOR, stageFactor);
            remainingTime = (double)((remainingTime.intValue() / 5) * 5); // da se zaokruzi vreme
        }
        currentLevel = levels.get(currentLevelNUM);
        return currentLevelNUM;
    }

    public int getStageFactor() {
        return stageFactor;
    }

    public void setStageFactor(int stageFactor) {
        this.stageFactor = stageFactor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayer() {
        return playerName;
    }

    public void setPlayer(String player) {
        this.playerName = player;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return date.getDayOfMonth() + ". " + date.getMonthValue() + ". " + date.getYear() + ".";
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCurrentLevelNUM() {
        return currentLevelNUM;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
        for (int i = 0; i < levels.size(); i++) {
            if (currentLevel == levels.get(i)){
                currentLevelNUM = i;
            }
        }
    }

//    public void setCurrentLevel(int index) {
//        currentLevel = levels.get(index);
//        currentLevelNUM = index;
//    }

    public void deleteLevel(int index) {
        levels.remove(index);
        currentLevel = levels.get(0);
        currentLevelNUM = 0;
    }

    public void addLevel(Level level) {
        if (levels.size() == 0) {
            this.currentLevel = level;
            this.currentLevelNUM = 0;
        }
        this.levels.add(level);
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRemainingLifePoints() {
        return remainingLifePoints;
    }

    public void setRemainingLifePoints(Integer remainingLifePoints) {
        this.remainingLifePoints = remainingLifePoints;
    }

    public Double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Double getRemainingFreezeTime() {
        return remainingFreezeTime;
    }

    public void setRemainingFreezeTime(Double remainingFreezeTime) {
        this.remainingFreezeTime = remainingFreezeTime;
    }

    public List<Level> getLevels() {
        return levels;
    }
}

// Za testiranje
//        if (!name.equals("Test")){
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player1 " + name), 1));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player2 " + name), 3));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player3 " + name), 2));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player4 " + name), 4));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player5 " + name), 10));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player6 " + name), 5));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player7 " + name), 8));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player8 " + name), 6));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player9 " + name), 7));
//                this.leaderboard.addEntry(new LeaderboardEntry(new Player("Player10 " + name), 9));
//                }
//---------------------------------