package com.aeliseev.androwatch;

import java.util.Calendar;

/**
 * Created by AEliseev on 20.02.2014
 */
public class CalendarHelper {

    public enum DayOfWeek {
        mon(2),
        tue(3),
        wed(4),
        thu(5),
        fri(6),
        sat(7),
        sun(1);

        private int value;

        DayOfWeek(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static long getClosestDate(Prefs prefs) {

        Calendar cal = Calendar.getInstance();
        int currentDow = cal.get(Calendar.DAY_OF_WEEK);

        Calendar result = Calendar.getInstance();
        result.add(Calendar.DATE, 8);

        for (String alarmDay: prefs.getDaysActive()) {
            int aDow = DayOfWeek.valueOf(alarmDay).getValue();

            int days_addition = aDow - currentDow;
            if (days_addition < 0) {
                days_addition = 7 + days_addition;
            }

            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(cal.getTimeInMillis());
            c2.add(Calendar.DATE, days_addition);

            c2.set(Calendar.HOUR_OF_DAY, prefs.getStartHour());
            c2.set(Calendar.MINUTE, prefs.getStartMinute() > 0 ? prefs.getStartMinute() : 0);
            c2.set(Calendar.SECOND, 0);

            if (c2.before(cal)) {
                c2.add(Calendar.DATE, 7);
            }

            if (result.after(c2)) {
                result.setTimeInMillis(c2.getTimeInMillis());
            }
        }

        return result.getTimeInMillis();
    }
}
