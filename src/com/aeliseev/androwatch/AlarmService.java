package com.aeliseev.androwatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
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

    public final static String UPDATE_ALARMS_DISC = "updateAlarms";     // start alarm if it is "active" in settings
    public final static String START_ALARMS_DISC = "startAlarms";       // make alarm "active" in settings and start it
    public final static String STOP_ALARMS_DISC = "stopAlarms";         // make alarm "inactive" in settings and stop it
    public final static String DISABLE_ALARMS_DISC = "disableAlarms";   // stop alarm, no change in settings
    public final static String TALK_ALARMS_DISC = "talkAlarms";

    private final static long ONE_SECOND = 1000;
    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long DEFAULT_INTERVAL = ONE_MINUTE * 5;

    private final static String STOP_INTENT_SUFFIX = ".Stop";
    private final static int ALARM_INTENT_SENDER_CODE = 28751303;

    // timer events receiver
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();

                if (action != null) {        // handle timer events
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AlarmService.class.getName());

                    //Acquire the lock
                    wl.acquire();

                    Bundle extras = intent.getExtras();
                    final Prefs prefs = (Prefs) extras.getSerializable(PrefsService.PREFS_EXTRA_KEY);

                    if (action.endsWith(STOP_INTENT_SUFFIX)) {

                        stopAlarm(prefs.getAlarmNumber());

                        // setup next repeating alarm
                        startAlarm(prefs);
                    } else {
                        final int volume = setVolume(prefs.getVolume(), prefs.isSystemVolume());

                        talkNow(new ChainLink() {

                            @Override
                            public void doTaskWork(Context context) {
                                setVolume(volume, prefs.isSystemVolume());
                            }
                        }, prefs);
                    }

                    //Release the lock
                    wl.release();
                }
            } catch (Throwable thr) {
                Log.e(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Error in AlarmService receiver", thr);
            }
        }
    };

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AlarmService.class.getName() + PrefsService.ALARM_PREFS_DEFAULT_NUMBER);
        filter.addAction(AlarmService.class.getName() + PrefsService.ALARM_PREFS_DEFAULT_NUMBER + STOP_INTENT_SUFFIX);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        if (UPDATE_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            updateAlarms((Prefs) extras.getSerializable(PrefsService.PREFS_EXTRA_KEY));
        }
        else if (START_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Intent service = new Intent(getApplicationContext(), PrefsService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.ACTIVATE_PREFS_DISC);
            service.putExtra(PrefsService.PREFS_EXTRA_KEY, new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER));
            service.putExtra(SingletonService.EXTRA_CALLBACK_KEY, new ResultReceiver(new Handler()) {

                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    updateAlarms((Prefs) resultData.getSerializable(PrefsService.PREFS_EXTRA_KEY));
                }
            });

            startService(service);
        }
        else if (STOP_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            // save active = false to prefs
            Intent service = new Intent(getApplicationContext(), PrefsService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.INACTIVATE_PREFS_DISC);
            service.putExtra(PrefsService.PREFS_EXTRA_KEY, new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER));
            startService(service);

            stopAlarm(PrefsService.ALARM_PREFS_DEFAULT_NUMBER);
        }
        else if (DISABLE_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            stopAlarm(PrefsService.ALARM_PREFS_DEFAULT_NUMBER);
        }
        else if (TALK_ALARMS_DISC.equals(extras.getString(INTENT_DISCRIMINATOR))) {

            Intent service = new Intent(getApplicationContext(), PrefsService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_DISC);
            service.putExtra(PrefsService.PREFS_EXTRA_KEY, new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER));
            service.putExtra(SingletonService.EXTRA_CALLBACK_KEY, new ResultReceiver(new Handler()) {

                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    talkNow(null, (Prefs) resultData.getSerializable(PrefsService.PREFS_EXTRA_KEY));
                }
            });

            startService(service);
        }
    }

    private void updateAlarms(Prefs prefs) {
        stopAlarm(prefs.getAlarmNumber());
        startAlarm(prefs);
    }

    private void startAlarm(Prefs prefs) {

        if (checkSanity(prefs)) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Starting alarm " + AlarmService.class.getName() + prefs.getAlarmNumber());

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(AlarmService.class.getName() + prefs.getAlarmNumber());
            intent.putExtra(PrefsService.PREFS_EXTRA_KEY, prefs);
            PendingIntent pi = PendingIntent.getBroadcast(
                    getApplicationContext(), ALARM_INTENT_SENDER_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            long startTime = CalendarHelper.getClosestDate(prefs);
            long stopTime = startTime + prefs.getDuration() * ONE_MINUTE;
            long interval = prefs.getInterval() * ONE_MINUTE;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "    StartTime - " + sdf.format(new Date(startTime)));
            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "    StopTime  - " + sdf.format(new Date(stopTime)));
            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "    SysTime   - " + sdf.format(new Date(System.currentTimeMillis())));

            // setup repeating timer
            am.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval > 0 ? interval : DEFAULT_INTERVAL, pi);

            // and also setup stop timer
            Intent iStop = new Intent(AlarmService.class.getName() + prefs.getAlarmNumber() + STOP_INTENT_SUFFIX);
            iStop.putExtra(PrefsService.PREFS_EXTRA_KEY, prefs);
            PendingIntent piStop = PendingIntent.getBroadcast(
                    getApplicationContext(), ALARM_INTENT_SENDER_CODE, iStop, PendingIntent.FLAG_CANCEL_CURRENT);
            am.set(AlarmManager.RTC_WAKEUP, stopTime, piStop);
        }

        // start UpdateWidgetService
        Intent iUWS = new Intent(getApplicationContext(), UpdateWidgetService.class);
        iUWS.putExtra(PrefsService.PREFS_EXTRA_KEY, prefs);
        startService(iUWS);
    }

    private void stopAlarm(int alarmNumber) {

        Intent si = new Intent(AlarmService.class.getName() + alarmNumber);
        PendingIntent sender = PendingIntent.getBroadcast(
                getApplicationContext(), ALARM_INTENT_SENDER_CODE, si, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Cancelling alarm " + AlarmService.class.getName() + alarmNumber);
        am.cancel(sender);

        Intent siStop = new Intent(AlarmService.class.getName() + alarmNumber + STOP_INTENT_SUFFIX);
        PendingIntent senderStop = PendingIntent.getBroadcast(
                getApplicationContext(), ALARM_INTENT_SENDER_CODE, siStop, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Cancelling alarm " + AlarmService.class.getName() + alarmNumber + STOP_INTENT_SUFFIX);
        am.cancel(senderStop);

        // start UpdateWidgetService with null prefs
        Intent iUWS = new Intent(getApplicationContext(), UpdateWidgetService.class);
        iUWS.putExtra(PrefsService.PREFS_EXTRA_KEY, (Prefs) null);
        startService(iUWS);
    }

    private boolean checkSanity(Prefs prefs) {

        return
                prefs.isActive()
                        && prefs.getStartHour() >= 0
                        && prefs.getDuration() >= 0
                        && !prefs.getDaysActive().isEmpty();
    }

    private void talkNow(ChainLink callback, Prefs prefs) {

        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        Date dt = new Date();
        Toast.makeText(getApplicationContext(), formatter.format(dt), Toast.LENGTH_SHORT).show();

        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "ALARM WORKING!!! " + formatter.format(dt));

        // play sound
        SoundPlayer sp = new SoundPlayer();
//        sp.playSoundChain(getApplicationContext(), dt, prefs.getVoiceNumber(), callback);
        sp.playSoundChain2(getApplicationContext(), dt, prefs.getVoiceNumber(), callback);
    }

    private int setVolume(int volume, boolean isSystemVolume) {

        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (!isSystemVolume) {

            audio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Setting volume to " + volume);
        }

        return result;
    }
}