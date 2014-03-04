package com.aeliseev.androwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Collections;

/**
 * Created by AEliseev on 21.02.2014
 */
public class PrefsService extends SingletonService {

    public final static String GET_PREFS_DISC = "getPrefs";
    public final static String SAVE_PREFS_DISC = "savePrefs";

    public final static String PREFS_EXTRA_KEY          = "prefsKey";
    public final static String ALARM_NUMBER_EXTRA_KEY   = "alarmNumber";

    private static final String ALARM_PREFS_NAME = "AlarmFile";

    private static final String IS_ACTIVE_PREFS_KEY          = "is_active";
    private static final String DAYS_ACTIVE_PREFS_KEY        = "days_active";
    private static final String START_HOUR_PREFS_KEY         = "start_hour";
    private static final String START_MINUTE_PREFS_KEY       = "start_minute";
    private static final String DURATION_MINUTES_PREFS_KEY   = "duration";
    private static final String INTERVAL_MINUTES_PREFS_KEY   = "interval";

    @Override
    public void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        if (GET_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Prefs result = new Prefs();

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Requesting prefs " + extras.getInt(ALARM_NUMBER_EXTRA_KEY));

            SharedPreferences settings = getSharedPreferences(ALARM_PREFS_NAME + extras.getInt(ALARM_NUMBER_EXTRA_KEY), 0);

            result.setActive(settings.getBoolean(IS_ACTIVE_PREFS_KEY, false));
            result.setAlarmNumber(extras.getInt(ALARM_NUMBER_EXTRA_KEY));
            result.setDaysActive(settings.getStringSet(DAYS_ACTIVE_PREFS_KEY, Collections.<String>emptySet()));
            result.setStartHour(settings.getInt(START_HOUR_PREFS_KEY, -1));
            result.setStartMinute(settings.getInt(START_MINUTE_PREFS_KEY, -1));
            result.setDuration(settings.getInt(DURATION_MINUTES_PREFS_KEY, -1));
            result.setInterval(settings.getInt(INTERVAL_MINUTES_PREFS_KEY, -1));

            Bundle data = new Bundle();
            data.putSerializable(PrefsService.PREFS_EXTRA_KEY, result);
            processCallback(extras, data);
        }
        else if (SAVE_PREFS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Prefs prefs = (Prefs) extras.get(PREFS_EXTRA_KEY);

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
            editor.commit();

            // call AlarmService to update alarms
            Intent service = new Intent(getApplicationContext(), AlarmService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.UPDATE_ALARMS_DISC);
            service.putExtra(PREFS_EXTRA_KEY, prefs);
            startService(service);
        }
    }
}