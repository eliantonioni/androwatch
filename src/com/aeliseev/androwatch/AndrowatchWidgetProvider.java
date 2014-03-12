package com.aeliseev.androwatch;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.RemoteViews;

/**
 * Created by AEliseev on 21.02.2014
 */
public class AndrowatchWidgetProvider extends AppWidgetProvider {

    public static String WIDGET_LOG_TAG = "AndroWatch";

    public static String ACTION_START_ALARM = "ActionStart";
    public static String ACTION_STOP_ALARM = "ActionStop";
    public static String ACTION_TALK_ALARM = "ActionTalk";

    @Override
    public void onEnabled(final Context context) {

        Intent service = new Intent(context, PrefsService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, PrefsService.GET_PREFS_DISC);
        service.putExtra(PrefsService.PREFS_EXTRA_KEY, new Prefs(PrefsService.ALARM_PREFS_DEFAULT_NUMBER));
        service.putExtra(SingletonService.EXTRA_CALLBACK_KEY, new ResultReceiver(new Handler()) {

            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Intent as = new Intent(context, AlarmService.class);
                as.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.UPDATE_ALARMS_DISC);
                as.putExtra(PrefsService.PREFS_EXTRA_KEY, resultData.getSerializable(PrefsService.PREFS_EXTRA_KEY));
                context.startService(as);
            }
        });
        context.startService(service);
    }

    @Override
    public void onDisabled(Context context) {

        Intent service = new Intent(context, AlarmService.class);
        service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.DISABLE_ALARMS_DISC);
        context.startService(service);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Создаем новый RemoteViews
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_androwatch_layout);

        Intent int1 = new Intent(context, AndrowatchWidgetProvider.class);
        int1.setAction(ACTION_START_ALARM);
        PendingIntent piStart = PendingIntent.getBroadcast(context, 0, int1, 0);

        Intent int2 = new Intent(context, AndrowatchWidgetProvider.class);
        int2.setAction(ACTION_STOP_ALARM);
        PendingIntent piStop = PendingIntent.getBroadcast(context, 0, int2, 0);

        Intent int3 = new Intent(context, AndrowatchWidgetProvider.class);
        int3.setAction(ACTION_TALK_ALARM);
        PendingIntent piTalk = PendingIntent.getBroadcast(context, 0, int3, 0);

        Intent saIntent = new Intent(context, SetAlarmsActivity.class);
        PendingIntent piSettings = PendingIntent.getActivity(context, 0, saIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.startButton, piStart);
        remoteViews.setOnClickPendingIntent(R.id.stopButton, piStop);
        remoteViews.setOnClickPendingIntent(R.id.talkTimeButton, piTalk);
        remoteViews.setOnClickPendingIntent(R.id.settingsButton, piSettings);

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (ACTION_START_ALARM.equals(action)) {

            Intent service = new Intent(context, AlarmService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.START_ALARMS_DISC);
            context.startService(service);
        }
        else if (ACTION_STOP_ALARM.equals(action)) {

            Intent service = new Intent(context, AlarmService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.STOP_ALARMS_DISC);
            context.startService(service);
        }
        else if (ACTION_TALK_ALARM.equals(action)) {

            Intent service = new Intent(context, AlarmService.class);
            service.putExtra(SingletonService.INTENT_DISCRIMINATOR, AlarmService.TALK_ALARMS_DISC);
            context.startService(service);
        }

        super.onReceive(context, intent);
    }
}
