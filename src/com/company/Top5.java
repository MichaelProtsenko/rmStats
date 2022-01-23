package com.company;

import java.io.*;


public class Top5 {

    public static void main(File inputDir) throws IOException {
        File[] directories = new File(inputDir.toString()).listFiles(File::isDirectory);
        PrintWriter writer = new PrintWriter("top5.txt");

        for (File directory : directories) {
            writeTop(directory, writer);
        }
    }
    public static int getKillsFromLine(String textLine){
        int kills = 0;
        if(textLine.contains("#")) {
            String secondNumberOccurence = textLine.substring(textLine.indexOf("(") + 1);
            String finalKillString = secondNumberOccurence.replaceAll("\\D+","");
            kills = Integer.parseInt(finalKillString);
        }
        return kills;
    }
    public static void writeTop(File inputDir, PrintWriter writer) {
        File[] listOfLogs = inputDir.listFiles();


        for (File log : listOfLogs) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(log));
                String textLine;
                String previousFirstPlace = "";
                while ((textLine = br.readLine()) != null) {
                    if (textLine.contains("[CHAT] [OneInTheChamber] Die Top 5 Spieler sind") || textLine.contains("[CHAT] [RageMode] Die Top 5 Spieler (insgesamt)")) {

                        String logNameDate = log.getName().substring(0,10);
                        String time = textLine.substring(1,9);
                        System.out.println("Found top5 on " + log.getName() + ".");

                        String firstPlace = br.readLine().substring(42);
                        String secondPlace = br.readLine().substring(42);
                        String thirdPlace = br.readLine().substring(42);
                        String fourthPlace = br.readLine().substring(42);
                        String fifthPlace = br.readLine().substring(42);

                        if(getKillsFromLine(firstPlace) < getKillsFromLine(previousFirstPlace)){
                            writer.println(logNameDate+" | "+time);
                            writer.println(firstPlace);
                            writer.println(secondPlace);
                            writer.println(thirdPlace);
                            writer.println(fourthPlace);
                            writer.println(fifthPlace);
                            writer.println("");
                            writer.flush();
                        }
                        if(previousFirstPlace == ""){
                            previousFirstPlace = firstPlace;
                            writer.println(logNameDate+" | "+time);
                            writer.println(firstPlace);
                            writer.println(secondPlace);
                            writer.println(thirdPlace);
                            writer.println(fourthPlace);
                            writer.println(fifthPlace);
                            writer.println("");
                            writer.flush();
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
