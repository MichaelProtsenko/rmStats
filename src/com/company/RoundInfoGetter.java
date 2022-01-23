package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;

public class RoundInfoGetter {
    public static RoundInfo getRoundInfo(String inputPath) throws IOException, ParseException {
        RoundInfo roundInfo = new RoundInfo();
        roundInfo.roundStart = getRoundStart(inputPath);
        roundInfo.roundEnd = getRoundEnd(inputPath);
        roundInfo.roundLength = getRoundLength(inputPath);

        return roundInfo;
    }

    public static LocalTime getRoundStart(String inputPath) throws IOException {
        LocalTime roundStart = null;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[CHAT] Teams sind auf diesem Server VERBOTEN und werden mit einem Ban bestraft!")) {
                String roundStartString = textLine.substring(1, 9);
                roundStart = LocalTime.parse(roundStartString);
            }
        }
        return roundStart;
    }
    public static LocalTime getRoundEnd(String inputPath) throws IOException {
        LocalTime roundEnd = null;
        File file = new File(inputPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String textLine;
        while ((textLine = br.readLine()) != null) {
            if (textLine.contains("[RageMode]")) {
                String roundEndString = textLine.substring(1, 9);
                roundEnd = LocalTime.parse(roundEndString);
            }
        }
        return roundEnd;
    }
    public static String getRoundLength(String inputPath) throws IOException {
        Duration diff = Duration.between(getRoundStart(inputPath), getRoundEnd(inputPath));
        String hms = String.format("%d:%02d:%02d",
                diff.toHours(),
                diff.toMinutesPart(),
                diff.toSecondsPart());
        return hms;
    }
}
