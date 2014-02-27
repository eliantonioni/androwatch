package com.aeliseev.androwatch;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by AEliseev on 21.02.2014
 */
public class AndrowatchWidgetProvider extends AppWidgetProvider {

    public static String WIDGET_LOG_TAG = "AndroWatch";

    public static String ACTION_START_ALARM = "ActionStart";
    public static String ACTION_STOP_ALARM = "ActionStop";

    @Override
    public void onDisabled(Context context) {
        Log.d( WIDGET_LOG_TAG, "inside onDisabled()");
//        cancelAlarmIntent(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Создаем новый RemoteViews
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_androwatch_layout);

        Intent int1 = new Intent(context, AndrowatchWidgetProvider.class);
        int1.setAction(ACTION_START_ALARM);
        PendingIntent piStart = PendingIntent.getBroadcast(context, 0, int1, 0);

        Intent int2 = new Intent(context, AndrowatchWidgetProvider.class);
        int2.setAction(ACTION_STOP_ALARM);
        PendingIntent piStop = PendingIntent.getBroadcast(context, 0, int2, 0);

        Intent saIntent = new Intent(context, SetAlarmsActivity.class);
        PendingIntent saPendingIntent = PendingIntent.getActivity(context, 0, saIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget_button1, piStart);
        remoteViews.setOnClickPendingIntent(R.id.widget_button2, piStop);
        remoteViews.setOnClickPendingIntent(R.id.widget_button3, saPendingIntent);

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "onReceive " + action);

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

        super.onReceive(context, intent);
    }
}
