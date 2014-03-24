package com.aeliseev.androwatch.sound;

import android.content.Context;
import android.util.Log;
import com.aeliseev.androwatch.AndrowatchWidgetProvider;
import com.aeliseev.androwatch.ChainLink;
import com.aeliseev.androwatch.Prefs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by AEliseev on 17.02.2014
 */
public class SoundPlayer {

    private VoicesMap vmap = new VoicesMap();

    public void playSoundChain(Context context, Date date, Prefs prefs, ChainLink callback) {

        try {
            VoiceItem vi = vmap.getVoiceItem(prefs.getVoiceNumber());

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);

            ChainLink hoursCL = vi.getHoursChainLink(cal.get(Calendar.HOUR_OF_DAY));
            hoursCL.setAfterLast(vi.getMinutesChainLink(cal.get(Calendar.MINUTE)));
            hoursCL.setAfterLast(callback);

            ChainLink startupCL = new SDFileChainLink(prefs.getRingtoneURI());
            startupCL.setNext(hoursCL);
            startupCL.doTaskWork(context);
        }
        catch (Throwable thr) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Error while playing sounds: ", thr);
        }
    }
}