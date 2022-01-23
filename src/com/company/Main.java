package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class Main {
    static int alltimeKills;
    static int alltimeDeaths;
    static double alltimeKd;
    static int alltimeHighestKillstreak;
    static int alltimeHighestDeathstreak;
    static int[] alltimeBoostFreq = {0,0,0,0,0};
    public static void main(String[] args) throws IOException, ParseException, InterruptedException {


        File inputDir = new File(args[0]);
        File[] listOfLogs = inputDir.listFiles();
        // Top5.main(inputDir);

        String playerList = args[1];
        BufferedReader br = new BufferedReader(new FileReader(playerList));
        // listFilesForFolder(new File(String.valueOf(inputDir)));
        String playerName;
        KDagainstPlayer player = new KDagainstPlayer();
        KDagainstPlayer.countKDAgainstPlayer(inputDir, "scompi", "rm");
        // System.out.println("Highest amount of kills in 1h: "+KDagainstPlayer.highestKillsPerHour(inputDir));

/*
        while ((playerName = br.readLine()) != null) {
            // countKDAgainstPlayer(inputDir, playerName);
            String gamemode = "rm";
            player.countKDAgainstPlayer(inputDir,playerName, gamemode);
        }
        // countKillstreakPercentage(inputDir);
        player.highestDeathstreak(inputDir);

/*
        for(File file : listOfLogs) {

            PlayerStats playerStats = PlayerStatsGetter.getPlayerStats(String.valueOf(file));

            System.out.println("Kills for session: "+playerStats.kills);
            System.out.println("Deaths for session: "+playerStats.deaths);
            System.out.println("KD for session: "+playerStats.kd);
            System.out.println("Highest killstreak for session: "+playerStats.highestKillstreak);
            System.out.println("Highest deathstreak for session: "+playerStats.highestDeathstreak);
            System.out.println("Boost frequency for session: "+ Arrays.toString(playerStats.boostFreq));
            System.out.println("");

            alltimeKills += playerStats.kills;
            alltimeDeaths += playerStats.deaths;
            alltimeKd = (float)alltimeKills/(float)alltimeDeaths;
            if(playerStats.highestKillstreak > alltimeHighestKillstreak) {
                alltimeHighestKillstreak = playerStats.highestKillstreak;
            }
            if(playerStats.highestDeathstreak > alltimeHighestDeathstreak) {
                alltimeHighestDeathstreak = playerStats.highestDeathstreak;
            }
            for(int i = 0; i < 5; i++) {
                alltimeBoostFreq[i] += playerStats.boostFreq[i];
            }
        }
        System.out.println("Alltime Kills: "+alltimeKills);
        System.out.println("Alltime Deaths: "+alltimeDeaths);
        System.out.println("Alltime KD: "+alltimeKd);
        System.out.println("Alltime highest killstreak: "+alltimeHighestKillstreak);
        System.out.println("Alltime highest deathstreak: "+alltimeHighestDeathstreak);
        System.out.println("Alltime boost frequency: "+ Arrays.toString(alltimeBoostFreq));

        // String outputPath = args[1];
        // RoundInfo[] roundTracker = new RoundTracker().findAllRounds(inputPath);
        // for(RoundInfo roundInfo : roundTracker){
           // System.out.println("Played from "+ roundInfo.roundStart+" to "+ roundInfo.roundEnd+" -> "+ roundInfo.roundLength);
        // }
*/
    }
}
