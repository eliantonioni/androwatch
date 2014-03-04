package com.aeliseev.androwatch;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AEliseev on 28.02.2014
 */
public class UpdateWidgetService extends SingletonService {

    @Override
    public void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        ComponentName thisWidget = new ComponentName(getApplicationContext(), AndrowatchWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        String nDate = "не установлено";

        try {
            Prefs prefs = (Prefs) intent.getExtras().getSerializable(PrefsService.PREFS_EXTRA_KEY);
            if (prefs != null && prefs.isActive()) {

                long startTime = CalendarHelper.getClosestDate(prefs);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
                nDate = sdf.format(new Date(startTime));
            }
        }
        catch (Throwable e) {
            Log.e(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Error on UWS: ", e);
        }

        for (int widgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                    R.layout.widget_androwatch_layout);
            remoteViews.setTextViewText(R.id.alarmsTextView, "Будильник: [" + nDate + "]");

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Updating widget [widgetId=" + widgetId + "] alarm = " + nDate);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
