package com.example.rollingBall.entities;

public class Leaderboard {

    public static final int ENTRY_CNT = 10;

    private LeaderboardEntry[] entries;
    private int cnt;

    public Leaderboard() {
        this.entries = new LeaderboardEntry[ENTRY_CNT];
        this.cnt = 0;
    }

    public LeaderboardEntry[] getEntries() {
        return entries;
    }

    // false ako nije dodat, true ako jeste
    public boolean addEntry(LeaderboardEntry entry) {
        boolean retVal = false;
        int index = checkEntryExists(entry);
        if (index >= 0){
            if (entry.getScore() <= entries[index].getScore()){ // provera da li za datog igraca vec postoji rekord u leaderbordu
                return retVal; // ako postoji veci skor onda se nista ne radi
            } else {
                moveEntries(index); // ako postoji manji skor onda se on brise (svi manji skorovi se pomeraju za mesto unapred), pa se
            }                       // ubacuje novi rezultat
        }
        if (cnt == 0) {
            entries[cnt++] = entry;
            retVal = true;
        } else {
            for (int i = cnt - 1; i >= 0; i--) {
                int oldE = entries[i].getScore();
                int newE = entry.getScore();
                if (oldE < newE){
                    if (i != ENTRY_CNT - 1) {
                        entries[i + 1] = entries[i];
                    }
                    entries[i] = entry;
                    retVal = true;
                } else {
                    if (cnt < ENTRY_CNT) {
                        if (entries[i + 1] == null){
                            entries[i + 1] = entry;
                            retVal = true;
                        }
                    }
                }
            }
            if (cnt < ENTRY_CNT) cnt++;
        }
        return retVal;
    }

    // vraca poziciju ako postoji, -1 ako ne postoji
    public int checkEntryExists(LeaderboardEntry entry){
        for (int i = 0; i < cnt; i++) {
            if (entries[i].getPlayerName().equals(entry.getPlayerName())){
                return i;
            }
        }
        return -1;
    }
    // pomera sve od index pozicije jedno mesto unapred
    public void moveEntries(int index){
        for (int i = index; i < cnt; i++) {
            if (i == cnt - 1){
                entries[i] = null;
                cnt--;
                return;
            }
            entries[i] = entries[i + 1];
        }
    }
}