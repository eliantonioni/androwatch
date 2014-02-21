package com.aeliseev.androwatch;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

public class WordWidget extends AppWidgetProvider {

    private Alarm alarm;

    public static String WIDGET_LOG_TAG = "AndroWatch";

    public static String ACTION_START_ALARM = "ActionStart";
    public static String ACTION_STOP_ALARM = "ActionStop";
    public static String ACTION_SAVE_PREFS = "ActionSavePrefs";

    public static final String ALARM_PREFS_NAME = "AlarmFile";

    public static final String IS_ACTIVE_PREFS_KEY          = "is_active";
    public static final String DAYS_ACTIVE_PREFS_KEY        = "days_active";
    public static final String START_HOUR_PREFS_KEY         = "start_hour";
    public static final String START_MINUTE_PREFS_KEY       = "start_minute";
    public static final String DURATION_MINUTES_PREFS_KEY   = "duration";
    public static final String INTERVAL_MINUTES_PREFS_KEY   = "interval";

    @Override
    public void onDisabled(Context context) {
        cancelAlarmIntent(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Создаем новый RemoteViews
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_word);

        Intent int1 = new Intent(context, WordWidget.class);
        int1.setAction(ACTION_START_ALARM);
        PendingIntent piStart = PendingIntent.getBroadcast(context, 0, int1, 0);

        Intent int2 = new Intent(context, WordWidget.class);
        int2.setAction(ACTION_STOP_ALARM);
        PendingIntent piStop = PendingIntent.getBroadcast(context, 0, int2, 0);

        Intent int3 = new Intent(context, WordWidget.class);
        int3.setAction(ACTION_SAVE_PREFS);
        PendingIntent piSave = PendingIntent.getBroadcast(context, 0, int3, 0);

        //регистрируем наше событие
        remoteViews.setOnClickPendingIntent(R.id.widget_button1, piStart);
        remoteViews.setOnClickPendingIntent(R.id.widget_button2, piStop);
        remoteViews.setOnClickPendingIntent(R.id.widget_button3, piSave);

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (ACTION_START_ALARM.equals(action)) {

            // start alarming
            try {
                checkAlarm().setAlarm(context, getPrefs(context, 1));
            }
            catch (Throwable thr) {
                Log.e(WIDGET_LOG_TAG, "AN start err", thr);
            }
        }
        else if (ACTION_STOP_ALARM.equals(action)) {

            cancelAlarmIntent(context);
        }
        else if (ACTION_SAVE_PREFS.equals(action)) {

            SharedPreferences settings = context.getSharedPreferences(ALARM_PREFS_NAME + "1", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(IS_ACTIVE_PREFS_KEY, true);

            // TODO: save real preferences from settings view
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Log.d(WIDGET_LOG_TAG, "Device time " + sdf.format(cal.getTime()));

            editor.putStringSet(DAYS_ACTIVE_PREFS_KEY, new HashSet<>(Arrays.asList("mon", "thu", "sun")));
            editor.putInt(START_HOUR_PREFS_KEY, cal.get(Calendar.HOUR_OF_DAY));
            editor.putInt(START_MINUTE_PREFS_KEY, cal.get(Calendar.MINUTE) + 1);
            editor.putInt(DURATION_MINUTES_PREFS_KEY, 2);
            editor.putInt(INTERVAL_MINUTES_PREFS_KEY, 1);
            editor.commit();
        }

        super.onReceive(context, intent);
    }

    private void cancelAlarmIntent(Context context) {
        try {
            checkAlarm().cancelAllAlarms(context, new HashSet<>(Arrays.asList(1)));
        }
        catch (Throwable thr) {
            Log.e(WIDGET_LOG_TAG, "AN stop err", thr);
        }
    }

    private Alarm checkAlarm() {

        if (alarm == null) {
            alarm = new Alarm();
            Log.e(WIDGET_LOG_TAG, "Alarm was null");
        }

        return alarm;
    }

    private Prefs getPrefs(Context context, int alarmNumber) {
        Prefs result = new Prefs();
        SharedPreferences settings = context.getSharedPreferences(ALARM_PREFS_NAME + alarmNumber, 0);

        result.setActive(settings.getBoolean(IS_ACTIVE_PREFS_KEY, false));
        result.setAlarmNumber(alarmNumber);

        if (result.isActive()) {
            result.setDaysActive(settings.getStringSet(DAYS_ACTIVE_PREFS_KEY, Collections.<String>emptySet()));
            result.setStartHour(settings.getInt(START_HOUR_PREFS_KEY, -1));
            result.setStartMinute(settings.getInt(START_MINUTE_PREFS_KEY, -1));
            result.setDuration(settings.getInt(DURATION_MINUTES_PREFS_KEY, -1));
            result.setInterval(settings.getInt(INTERVAL_MINUTES_PREFS_KEY, -1));
        }

        Log.d(WIDGET_LOG_TAG, result.toString());

        return result;
    }
}
