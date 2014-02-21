package com.aeliseev.androwatch.sound;

/**
 * Created by AEliseev on 18.02.2014
 */
public class VoiceItem {

    private int hourNumber;
    private int hourWord;
    private int minuteNumber;
    private int minuteWord;

    public VoiceItem(int hourNumber, int hourWord, int minuteNumber, int minuteWord) {
        this.hourNumber = hourNumber;
        this.hourWord = hourWord;
        this.minuteNumber = minuteNumber;
        this.minuteWord = minuteWord;
    }

    public int getHourNumber() {
        return hourNumber;
    }

    public int getHourWord() {
        return hourWord;
    }

    public int getMinuteNumber() {
        return minuteNumber;
    }

    public int getMinuteWord() {
        return minuteWord;
    }
}
