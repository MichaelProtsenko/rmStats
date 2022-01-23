package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;

public class RoundTracker {
    public RoundInfo[] findAllRounds(String inputPath) throws IOException, ParseException {
        RoundInfo[] allRounds = new RoundInfo[100];
        int roundIndex = 0;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] Teams sind auf diesem Server VERBOTEN und werden mit einem Ban bestraft!")) {
                RoundInfo roundInfo = RoundInfoGetter.getRoundInfo(inputPath);
                allRounds[roundIndex] = roundInfo;
                roundIndex++;
            }
        }
        return allRounds;
    }
}
