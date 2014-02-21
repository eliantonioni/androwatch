package com.aeliseev.androwatch;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import com.aeliseev.androwatch.sound.SoundPlayer;

/**
 * Created by AEliseev on 17.02.2014
 */
public class Alarm extends BroadcastReceiver {

    final static private long ONE_SECOND = 1000;
    final static private long ONE_MINUTE = ONE_SECOND * 60;
    final static private long DEFAULT_INTERVAL = ONE_MINUTE * 5;

    final static private String STOP_INTENT_SUFFIX = ".Stop";
    final static private String ALARM_NUMBER_EXTRA = "alarmNumberExtra";

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Alarm.class.getName());

        //Acquire the lock
        wl.acquire();

        final String action = intent.getAction();

        if (action.endsWith(STOP_INTENT_SUFFIX)) {

            Bundle extras = intent.getExtras();
            if (extras != null) {

                Intent si = new Intent(Alarm.class.getName() + extras.getInt(ALARM_NUMBER_EXTRA));
                PendingIntent sender = PendingIntent.getBroadcast(context, 0, si, 0);
                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Log.d(WordWidget.WIDGET_LOG_TAG, "cancelling alarm " + Alarm.class.getName() + extras.getInt(ALARM_NUMBER_EXTRA));
                am.cancel(sender);

                // TODO: setup next alarm
                if (prefs != null) {
                    setAlarm(context, prefs);
                }
                else {
                    Log.e(WordWidget.WIDGET_LOG_TAG, "prefs is null while setting next alarm!");
                }
            }
        }
        else {

            Format formatter = new SimpleDateFormat("hh:mm:ss a");
            Date dt = new Date();
            Toast.makeText(context, formatter.format(dt), Toast.LENGTH_SHORT).show();

            Log.d(WordWidget.WIDGET_LOG_TAG, "ALARM WORKING!!! " + formatter.format(dt));

            // play sound
            SoundPlayer sp = new SoundPlayer();
            sp.playSoundChain(context, dt);
        }

        //Release the lock
        wl.release();
    }

    public void setAlarm(Context context, Prefs prefs) {

        if (prefs.isActive()) {

            if (prefs.getStartHour() >= 0 && prefs.getDuration() >= 0) {

                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Alarm.class.getName() + prefs.getAlarmNumber());
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

                long startTime = CalendarHelper.getClosestDate(prefs);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Log.d(WordWidget.WIDGET_LOG_TAG, "StartTime - " + sdf.format(new Date(startTime)));
                long stopTime = startTime + prefs.getDuration() * ONE_MINUTE;
                Log.d(WordWidget.WIDGET_LOG_TAG, "StopTime  - " + sdf.format(new Date(stopTime)));

                Log.d(WordWidget.WIDGET_LOG_TAG, "SysTime   - " + sdf.format(new Date(System.currentTimeMillis())));

                long interval = prefs.getInterval() * ONE_MINUTE;

                // setup repeating timer
                am.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    startTime,
                    interval > 0 ? interval : DEFAULT_INTERVAL,
                    pi
                );

                // and also setup stop timer
                Intent iStop = new Intent(Alarm.class.getName() + prefs.getAlarmNumber() + STOP_INTENT_SUFFIX);
                iStop.putExtra(ALARM_NUMBER_EXTRA, prefs.getAlarmNumber());
                PendingIntent piStop = PendingIntent.getBroadcast(context, 0, iStop, 0);
                am.set(AlarmManager.RTC_WAKEUP, stopTime, piStop);
            }
        }
    }

    public void cancelAllAlarms(Context context, Set<Integer> alarms) {
        for (Integer alarm: alarms) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(Alarm.class.getName() + alarm);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
            am.cancel(sender);

            Intent intent2 = new Intent(Alarm.class.getName() + alarm + STOP_INTENT_SUFFIX);
            PendingIntent sender2 = PendingIntent.getBroadcast(context, 0, intent2, 0);
            am.cancel(sender2);

            Log.d(WordWidget.WIDGET_LOG_TAG, "cancelled alarm " + Alarm.class.getName() + alarm);
        }
    }
}