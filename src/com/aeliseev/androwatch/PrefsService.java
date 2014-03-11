package com.aeliseev.androwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.*;

/**
 * Created by AEliseev on 21.02.2014
 */
public class PrefsService extends SingletonService {

    public final static String GET_PREFS_DISC           = "getPrefs";
    public final static String SAVE_PREFS_DISC          = "savePrefs";
    public final static String ACTIVATE_PREFS_DISC      = "activatePrefs";
    public final static String INACTIVATE_PREFS_DISC    = "inactivatePrefs";

    public final static String PREFS_EXTRA_KEY          = "prefsKey";

    private static final String ALARM_PREFS_NAME = "AlarmFile";

    private static final String IS_ACTIVE_PREFS_KEY         = "is_active";
    private static final String DAYS_ACTIVE_PREFS_KEY       = "days_active";
    private static final String START_HOUR_PREFS_KEY        = "start_hour";
    private static final String START_MINUTE_PREFS_KEY      = "start_minute";
    private static final String DURATION_MINUTES_PREFS_KEY  = "duration";
    private static final String INTERVAL_MINUTES_PREFS_KEY  = "interval";
    private static final String SYSTEM_VOLUME_PREFS_KEY     = "system_volume";
    private static final String VOLUME_PREFS_KEY            = "volume";

    @Override
    public void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        Prefs prefs = (Prefs) extras.get(PREFS_EXTRA_KEY);

        if (GET_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Requesting prefs " + prefs.getAlarmNumber());
            getPrefsFromSettings(extras, prefs.getAlarmNumber());
        }
        else if (SAVE_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Saving prefs to SharedPreferences " + prefs);

            SharedPreferences settings = getSharedPreferences(
                ALARM_PREFS_NAME + prefs.getAlarmNumber(), 0
            );
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(IS_ACTIVE_PREFS_KEY, prefs.isActive());
            editor.putStringSet(DAYS_ACTIVE_PREFS_KEY, prefs.getDaysActive());
            editor.putInt(START_HOUR_PREFS_KEY, prefs.getStartHour());
            editor.putInt(START_MINUTE_PREFS_KEY, prefs.getStartMinute());
            editor.putInt(DURATION_MINUTES_PREFS_KEY, prefs.getDuration());
            editor.putInt(INTERVAL_MINUTES_PREFS_KEY, prefs.getInterval());
            editor.putBoolean(SYSTEM_VOLUME_PREFS_KEY, prefs.isSystemVolume());
            editor.putInt(VOLUME_PREFS_KEY, prefs.getVolume());
            editor.commit();

            // call AlarmService to update alarms
            Intent service = new Intent(getApplicationContext(), AlarmService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.UPDATE_ALARMS_DISC);
            service.putExtra(PREFS_EXTRA_KEY, prefs);
            startService(service);
        }
        else if (ACTIVATE_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Activating prefs " + prefs.getAlarmNumber());
            SharedPreferences settings = getSharedPreferences(
                    ALARM_PREFS_NAME + prefs.getAlarmNumber(), 0
            );
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(IS_ACTIVE_PREFS_KEY, true);
            editor.commit();

            getPrefsFromSettings(extras, prefs.getAlarmNumber());
        }
        else if (INACTIVATE_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Inactivating prefs " + prefs.getAlarmNumber());
            SharedPreferences settings = getSharedPreferences(
                    ALARM_PREFS_NAME + prefs.getAlarmNumber(), 0
            );
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(IS_ACTIVE_PREFS_KEY, false);
            editor.commit();
        }
    }

    private void getPrefsFromSettings(Bundle extras, int alarmNumber) {

        Prefs result = new Prefs(alarmNumber);
        SharedPreferences settings = getSharedPreferences(ALARM_PREFS_NAME + alarmNumber, 0);
        result.setActive(settings.getBoolean(IS_ACTIVE_PREFS_KEY, false));
        result.setDaysActive(settings.getStringSet(DAYS_ACTIVE_PREFS_KEY, Collections.<String>emptySet()));
        result.setStartHour(settings.getInt(START_HOUR_PREFS_KEY, -1));
        result.setStartMinute(settings.getInt(START_MINUTE_PREFS_KEY, -1));
        result.setDuration(settings.getInt(DURATION_MINUTES_PREFS_KEY, -1));
        result.setInterval(settings.getInt(INTERVAL_MINUTES_PREFS_KEY, -1));
        result.setSystemVolume(settings.getBoolean(SYSTEM_VOLUME_PREFS_KEY, true));
        result.setVolume(settings.getInt(VOLUME_PREFS_KEY, -1));

/*
            // test prefs!
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 1);
            result.setActive(true);
            result.setAlarmNumber(extras.getInt(ALARM_NUMBER_EXTRA_KEY));
            result.setDaysActive(new HashSet(Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun")));
            result.setStartHour(cal.get(Calendar.HOUR_OF_DAY));
            result.setStartMinute(cal.get(Calendar.MINUTE));
            result.setDuration(3);
            result.setInterval(1);
            result.setVolume(2);
            result.setSystemVolume(false);
*/

        Bundle data = new Bundle();
        data.putSerializable(PrefsService.PREFS_EXTRA_KEY, result);
        processCallback(extras, data);
    }
}