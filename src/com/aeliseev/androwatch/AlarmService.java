package com.aeliseev.androwatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import com.aeliseev.androwatch.sound.SoundPlayer;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AEliseev on 21.02.2014
 */
public class AlarmService extends SingletonService {

    public final static String START_ALARMS_DISC = "startAlarms";
    public final static String UPDATE_ALARMS_DISC = "updateAlarms";
    public final static String STOP_ALARMS_DISC = "stopAlarms";

    private final static long ONE_SECOND = 1000;
    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long DEFAULT_INTERVAL = ONE_MINUTE * 5;

    private final static String STOP_INTENT_SUFFIX = ".Stop";

    private Prefs prefs = null;

    // timer events receiver
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action != null) {        // handle timer events
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AlarmService.class.getName());

                //Acquire the lock
                wl.acquire();

                if (action.endsWith(STOP_INTENT_SUFFIX)) {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {

                        stopAlarm(extras.getInt(PrefsService.ALARM_NUMBER_EXTRA_KEY));

                        // setup next repeating alarm
                        if (prefs != null) {
                            startAlarm(prefs);
                        } else {
                            Log.e(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "prefs is null while setting next alarm!");
                        }
                    }
                } else {
                    Format formatter = new SimpleDateFormat("hh:mm:ss a");
                    Date dt = new Date();
                    Toast.makeText(getApplicationContext(), formatter.format(dt), Toast.LENGTH_SHORT).show();

                    Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "ALARM WORKING!!! " + formatter.format(dt));

                    // play sound
                    SoundPlayer sp = new SoundPlayer();
                    sp.playSoundChain(getApplicationContext(), dt);
                }

                //Release the lock
                wl.release();
            }
        }
    };

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AlarmService.class.getName() + "1");
        filter.addAction(AlarmService.class.getName() + "1" + STOP_INTENT_SUFFIX);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        if (START_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Intent service = new Intent(getApplicationContext(), PrefsService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_TO_SET_ALARMS_DISC);
            service.putExtra(PrefsService.ALARM_NUMBER_EXTRA_KEY, 1);
            startService(service);

        } else if (STOP_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            stopAlarm(1);

        } else if (UPDATE_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            prefs = (Prefs) extras.get(PrefsService.PREFS_EXTRA_KEY);
            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "updatingAlarms with " + prefs.toString());

            stopAlarm(prefs.getAlarmNumber());
            startAlarm(prefs);
        }
    }

    private void startAlarm(Prefs prefs) {

        if (prefs.isActive()) {

            if (prefs.getStartHour() >= 0 && prefs.getDuration() >= 0) {

                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Starting alarm " + AlarmService.class.getName() + prefs.getAlarmNumber());

                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AlarmService.class.getName() + prefs.getAlarmNumber());
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                long startTime = CalendarHelper.getClosestDate(prefs);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "StartTime - " + sdf.format(new Date(startTime)));
                long stopTime = startTime + prefs.getDuration() * ONE_MINUTE;
                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "StopTime  - " + sdf.format(new Date(stopTime)));

                Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "SysTime   - " + sdf.format(new Date(System.currentTimeMillis())));

                long interval = prefs.getInterval() * ONE_MINUTE;

                // setup repeating timer
                am.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        startTime,
                        interval > 0 ? interval : DEFAULT_INTERVAL,
                        pi
                );

                // and also setup stop timer
                Intent iStop = new Intent(AlarmService.class.getName() + prefs.getAlarmNumber() + STOP_INTENT_SUFFIX);
                iStop.putExtra(PrefsService.ALARM_NUMBER_EXTRA_KEY, prefs.getAlarmNumber());
                PendingIntent piStop = PendingIntent.getBroadcast(getApplicationContext(), 0, iStop, 0);
                am.set(AlarmManager.RTC_WAKEUP, stopTime, piStop);
            }
        }
    }

    private void stopAlarm(int alarmNumber) {

        Intent si = new Intent(AlarmService.class.getName() + alarmNumber);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, si, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Cancelling alarm " + AlarmService.class.getName() + alarmNumber);
        am.cancel(sender);
    }
}