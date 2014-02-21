package com.aeliseev.androwatch;

import java.util.Collections;
import java.util.Set;

/**
 * Created by AEliseev on 18.02.2014
 */
public class Prefs {

    private boolean isActive = false;
    private Set<String> daysActive = Collections.emptySet();
    private int startHour = -1;
    private int startMinute = -1;
    private int duration = -1;
    private int interval = -1;

    private int alarmNumber = 1;

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

    @Override
    public String toString() {
        return "Prefs{" +
                "isActive=" + isActive +
                ", daysActive=" + daysActive +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", duration=" + duration +
                ", interval=" + interval +
                ", alarmNumber=" + alarmNumber +
                '}';
    }
}