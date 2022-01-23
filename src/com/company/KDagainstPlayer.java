package com.company;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class KDagainstPlayer {

/*
    public static void checkForPlayer(String playerName, File inputDir) throws FileNotFoundException {
        File[] logs = inputDir.listFiles();
        for (File file : logs) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String textLine = null;
            if (textLine.contains(playerName)) {
                System.out.println(textLine);
                System.out.println(file.getName());
            }
        }
    }

 */
    public static void checkPlayerKillstreak(String playerName, File inputDir) throws IOException {
        File[] logs = inputDir.listFiles();
        int killstreak = 0;
        int highestStreak = 0;
        for (File file : logs) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String textLine;
            while ((textLine = br.readLine()) != null) {
                if (textLine.contains(playerName)) {
                    if (((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du hast " + playerName)) || textLine.contains("killed " + playerName))) {
                        killstreak++;
                    }
                    if (((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du wurdest von " + playerName)) || textLine.contains("killed by "+playerName))) {
                        killstreak = 0;
                    }
                    if (killstreak > highestStreak) {
                        highestStreak = killstreak;
                    }
                }
            }
        }
        System.out.println("Highest streak against "+playerName+": "+highestStreak);
    }

    public static void highestDeathstreak(File inputDir) throws IOException {
        File[] logs = inputDir.listFiles();
        int deathStreak = 0;
        int highestStreak = 0;
        String logName = "";
        for (File file : logs) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String textLine;
            while ((textLine = br.readLine()) != null) {
                if (((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du wurdest von")) || textLine.contains("killed by"))) {
                    deathStreak++;
                }
                if (((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du hast ")) || textLine.contains("killed "))) {
                    deathStreak = 0;
                }
                if (deathStreak > highestStreak) {
                    highestStreak = deathStreak;
                    logName = file.getName();
                }
            }
        }

        System.out.println(highestStreak);
        System.out.println(logName);
    }

    public static void countKDAgainstPlayer(File inputDir, String playerName, String gamemode) throws IOException {
        File[] logs = inputDir.listFiles();
        int kills = 0;
        int deaths = 0;
        DecimalFormat df = new DecimalFormat("####0.000");

        for (File file : logs) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String textLine;

            if(gamemode == "rm") {
                while ((textLine = br.readLine()) != null) {
                    if ((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du hast " + playerName) || textLine.contains("killed " + playerName))) {
                        kills++;
                    }
                    if ((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du wurdest von " + playerName) || textLine.contains("killed by " + playerName))) {
                        deaths++;
                    }
                }
            }
            if(gamemode == "oitc") {

                while ((textLine = br.readLine()) != null) {
                    if ((textLine.contains("OneInTheChamber") || textLine.contains("OITC")) && (textLine.contains("Du hast " + playerName) || textLine.contains("killed " + playerName))) {
                        kills++;
                    }
                    if ((textLine.contains("OneInTheChamber") || textLine.contains("OITC")) && (textLine.contains("Du wurdest von " + playerName) || textLine.contains("killed by " + playerName))) {
                        deaths++;
                    }
                }
            }
        }
        if (deaths != 0) {
            double kd = kills / (double) deaths;
            System.out.println(playerName + ": " + kills + " Kills, " + deaths + " Tode - KD: " + df.format(kd));
        } else {
            double kd = kills;
            System.out.println(playerName + ": " + kills + " Kills, " + deaths + " Tode - KD: " + df.format(kd));
        }
    }
    public static int highestKillsPerHour(File inputDir) throws IOException, ParseException, InterruptedException {
        File[] logs = inputDir.listFiles();
        int killCounter;
        int highestKillsPerHour = 0;

        for (File file : logs) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String textLine;
            ArrayList<String> currentHourKills = new ArrayList<>();

            while ((textLine = br.readLine()) != null) {
                if ((textLine.contains("RageMode") || textLine.contains("RM")) && (textLine.contains("Du hast ") || textLine.contains("killed "))) {
                    currentHourKills.add(textLine);
                    System.out.println("Added line: "+textLine);
                    updateList(textLine, currentHourKills);
                    System.out.println(currentHourKills.size());
                    killCounter = currentHourKills.size();

                    if(killCounter > highestKillsPerHour) {
                        highestKillsPerHour = killCounter;
                    }
                }
            }
        }
        return highestKillsPerHour;
    }

    public static void updateList(String textLine, ArrayList<String> currentHourKills) throws IOException, ParseException {

        int hours = Integer.parseInt(textLine.substring(1, 3));
        int minutes = Integer.parseInt(textLine.substring(4, 6));
        int seconds = Integer.parseInt(textLine.substring(7, 9));

        String pattern = "hh:mm:ss";
        SimpleDateFormat timeFormat = new SimpleDateFormat(pattern);
        Date timeHourAgo = timeFormat.parse(hours-1+":"+minutes+":"+seconds);
        ArrayList<String> toRemove = new ArrayList<>();

        for (String kill : currentHourKills) {
            Date killTime = timeFormat.parse(kill.substring(1,9));
            if(killTime.before(timeHourAgo)){
                toRemove.add(kill);
            }
        }
        for(String killLine : toRemove){
            currentHourKills.remove(killLine);
        }
    }

    public static void countKillstreakPercentage(File inputDir) throws IOException {
        File[] logs = inputDir.listFiles();
        DecimalFormat df = new DecimalFormat("####0.000");
        int threeStreak = 0;
        int fiveStreak = 0;
        int nineStreak = 0;
        int tenStreak = 0;
        int fifteenStreak = 0;
        int twentyStreak = 0;
        int twentyfiveStreak = 0;
        int thirtyStreak = 0;
        int fourtyStreak = 0;
        int fiftyStreak = 0;
        int hundredStreak = 0;

        for (File file : logs) {
            String textLine = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((textLine = br.readLine()) != null) {

                if (textLine.contains("Du hast eine Landmine") || textLine.contains("Land mine")) {
                    threeStreak++;
                }
                if (textLine.contains("5 Kills in Folge!") || textLine.contains("5 kills without death!")) {
                    fiveStreak++;
                }
                if (textLine.contains("Hundestaffel") || textLine.contains("group of dogs")) {
                    nineStreak++;
                }
                if (textLine.contains("10 Kills in Folge!") && (!textLine.contains("hat")) || (textLine.contains("10 kills without death!") && (!textLine.contains("has")))) {
                    tenStreak++;
                }
                if (textLine.contains("Sturmfeuer") || textLine.contains("quick-fire")) {
                    fifteenStreak++;
                }
                if (textLine.contains("20 Kills in Folge!") && (!textLine.contains("hat")) || (textLine.contains("20 kills without death!") && (!textLine.contains("has")))) {
                    twentyStreak++;
                }
                if (textLine.contains("25 Sekunden lang") || (textLine.contains("You can fly for 25 seconds"))) {
                    twentyfiveStreak++;
                }
                if (textLine.contains("30 Kills in Folge!") && (!textLine.contains("hat")) || (textLine.contains("30 kills without death!") && (!textLine.contains("has")))) {
                    thirtyStreak++;
                }
                if (textLine.contains("40 Kills in Folge!") && (!textLine.contains("hat")) || (textLine.contains("40 kills without death!") && (!textLine.contains("has")))) {
                    fourtyStreak++;
                }
                if (textLine.contains("50 Kills in Folge!") && (!textLine.contains("hat")) || (textLine.contains("50 kills without death!") && (!textLine.contains("has")))) {
                    fiftyStreak++;


                }
            }
        }

        int totalKillstreaks = threeStreak + fiveStreak + nineStreak + tenStreak + fifteenStreak + twentyStreak + twentyfiveStreak + thirtyStreak + fourtyStreak + fiftyStreak;
        System.out.println("Gesamte Killstreaks: " + totalKillstreaks);
        System.out.println("3er Streaks: " + threeStreak + " - " + df.format(threeStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("5er Streaks: " + fiveStreak + " - " + df.format(fiveStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("9er Streaks: " + nineStreak + " - " + df.format(nineStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("10er Streaks: " + tenStreak + " - " + df.format(tenStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("15er Streaks: " + fifteenStreak + " - " + df.format(fifteenStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("20er Streaks: " + twentyStreak + " - " + df.format(twentyStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("25er Streaks: " + twentyfiveStreak + " - " + df.format(twentyfiveStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("30er Streaks: " + thirtyStreak + " - " + df.format(thirtyStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("40er Streaks: " + fourtyStreak + " - " + df.format(fourtyStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("50er Streaks: " + fiftyStreak + " - " + df.format(fiftyStreak / (double) totalKillstreaks * 100) + "%");
        System.out.println("100er Streaks: " + hundredStreak + " - " + df.format(hundredStreak / (double) totalKillstreaks * 100) + "%");
    }

    public static void listFilesForFolder(final File inputDir) {
        for (final
        File fileEntry : inputDir.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
}
