package com.aeliseev.androwatch;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Created by AEliseev on 18.02.2014
 */
public class Prefs implements Serializable {

    private boolean isActive = false;
    private Set<String> daysActive = Collections.emptySet();
    private int startHour = -1;
    private int startMinute = -1;
    private int duration = -1;
    private int interval = -1;
    private String ringtoneURI = "";

    private int voiceNumber = 0;

    private int volume;
    private boolean systemVolume;

    private int alarmNumber = 1;

    public Prefs(int alarmNumber) {
        this.alarmNumber = alarmNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Set<String> getDaysActive() {
        return daysActive;
    }

    public void setDaysActive(Set<String> daysActive) {
        this.daysActive = daysActive;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(int alarmNumber) {
        this.alarmNumber = alarmNumber;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isSystemVolume() {
        return systemVolume;
    }

    public void setSystemVolume(boolean systemVolume) {
        this.systemVolume = systemVolume;
    }

    public int getVoiceNumber() {
        return voiceNumber;
    }

    public void setVoiceNumber(int voiceNumber) {
        this.voiceNumber = voiceNumber;
    }

    public String getRingtoneURI() {
        return ringtoneURI;
    }

    public void setRingtoneURI(String ringtoneURI) {
        this.ringtoneURI = ringtoneURI;
    }

    @Override
    public String toString() {
        return "Prefs{" +
                "isActive=" + isActive +
                ", daysActive=" + daysActive +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", duration=" + duration +
                ", interval=" + interval +
                ", volume=" + volume +
                ", systemVolume=" + systemVolume +
                ", voiceNumber=" + voiceNumber +
                ", alarmNumber=" + alarmNumber +
                ", ringtoneURI=" + ringtoneURI +
                '}';
    }
}
