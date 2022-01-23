package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class PlayerStatsGetter {
    public static PlayerStats getPlayerStats(String inputPath) throws IOException {
        PlayerStats playerStats = new PlayerStats();
        playerStats.kills = PlayerStatsGetter.getKills(inputPath);
        playerStats.deaths = PlayerStatsGetter.getDeaths(inputPath);
        playerStats.kd = PlayerStatsGetter.getKd(inputPath);
        playerStats.highestKillstreak = PlayerStatsGetter.getHighestKillstreak(inputPath);
        playerStats.highestDeathstreak = PlayerStatsGetter.getHighestDeathstreak(inputPath);
        playerStats.boostFreq = PlayerStatsGetter.getBoostFrequency(inputPath);
        return playerStats;
    }

    public static int getKills(String inputPath) throws IOException {
        int killCounter = 0;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Du hast")) {
                killCounter++;
            }
        }
        return killCounter;
    }

    public static int getDeaths(String inputPath) throws IOException {
        int deathCounter = 0;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Du wurdest von") || textLine.contains("[CHAT] [RageMode] Du bist gestorben")) {
                deathCounter++;
            }
        }
        return deathCounter;
    }

    public static double getKd(String inputPath) throws IOException {
        double kd;
        if(getDeaths(inputPath) == 0){
            kd = getKills(inputPath);
        }
        else{
            kd = (double)getKills(inputPath) / (double)getDeaths(inputPath);
        }
        return kd;
    }

    public static int getHighestKillstreak(String inputPath) throws IOException {
        int highestKillstreak = 0;
        int currentKillstreak = 0;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Du hast") && textLine.contains("get")) {
                currentKillstreak++;
            }
            if (textLine.contains("[CHAT] [RageMode] Du wurdest von")) {
                currentKillstreak = 0;
            }
            if (currentKillstreak > highestKillstreak) {
                highestKillstreak = currentKillstreak;
            }
        }
        return highestKillstreak;
    }

    public static int getHighestDeathstreak(String inputPath) throws IOException {
        int highestDeathstreak = 0;
        int currentDeathstreak = 0;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Du wurdest von")) {
                currentDeathstreak++;
            }
            if (textLine.contains("[CHAT] [RageMode] Du hast") && textLine.contains("get")) {
                currentDeathstreak = 0;
            }
            if (currentDeathstreak > highestDeathstreak) {
                highestDeathstreak = currentDeathstreak;
            }
        }
        return highestDeathstreak;
    }

    public static int[] getBoostFrequency(String inputPath) throws IOException {
        int speedCounter = 0, dogCounter = 0, minigunCounter = 0, flyCounter = 0, nukeCounter = 0;

        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Deine Geschwindigkeit wurde")) {
                speedCounter++;
            }
            if (textLine.contains("[CHAT] [RageMode] Eine Hundestaffel")) {
                dogCounter++;
            }
            if (textLine.contains("[CHAT] [RageMode] Dein RageBow")) {
                minigunCounter++;
            }
            if (textLine.contains("[CHAT] [RageMode] Du kannst 25 Sekunden lang fliegen")) {
                flyCounter++;
            }
            if (textLine.contains("LEGENDE IN DER RUNDE")) {
                nukeCounter++;
            }
        }
        int[] boostFrequency = {speedCounter, dogCounter, minigunCounter, flyCounter, nukeCounter};
        return boostFrequency;
    }

    public static int rageModeTimePlayed(String inputPath) throws IOException {
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] [RageMode] Deine Geschwindigkeit wurde für 20 Sekunden stark erhöht")) {

            }
        }
        return 2;
    }
}
