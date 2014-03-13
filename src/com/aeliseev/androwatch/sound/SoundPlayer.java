package com.aeliseev.androwatch.sound;

import android.content.Context;
import android.util.Log;
import com.aeliseev.androwatch.AndrowatchWidgetProvider;
import com.aeliseev.androwatch.ChainLink;
import com.aeliseev.androwatch.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by AEliseev on 17.02.2014
 */
public class SoundPlayer {

    private VoicesMap vmap = new VoicesMap();
    private VoicesMap2 vmap2 = new VoicesMap2();

    public void playSoundChain(Context context, Date date, int voice, ChainLink callback) {

        try {
            vmap.setCurrentVoice(voice);

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);

            VoiceItem hoursVI = vmap.getVoiceItem(cal.get(Calendar.HOUR_OF_DAY));
            VoiceItem minutesVI = vmap.getVoiceItem(cal.get(Calendar.MINUTE));

            SoundChainLink hoursNumber = new SoundChainLink(hoursVI.getHourNumber());
            SoundChainLink hoursWord = new SoundChainLink(hoursVI.getHourWord());

            SoundChainLink minutesNumber = new SoundChainLink(minutesVI.getMinuteNumber());
            SoundChainLink minutesWord = new SoundChainLink(minutesVI.getMinuteWord());

            hoursNumber.setNext(hoursWord);
            hoursWord.setNext(minutesNumber);
            minutesNumber.setNext(minutesWord);
            minutesWord.setNext(callback);

            hoursNumber.doTaskWork(context);
        } catch (Throwable thr) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Error while playing sounds: ", thr);
        }
    }

    public void playSoundChain2(Context context, Date date, int voice, ChainLink callback) {

        try {
            vmap2.setCurrentVoice(voice);

            VoiceItem2 vi = vmap2.getVoiceItem(voice);

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);

            ChainLink hoursCL = vi.getHoursChainLink(cal.get(Calendar.HOUR_OF_DAY));
            hoursCL.setAfterLast(vi.getMinutesChainLink(cal.get(Calendar.MINUTE)));
            hoursCL.setAfterLast(callback);

            hoursCL.doTaskWork(context);
        }
        catch (Throwable thr) {

            Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "Error while playing sounds: ", thr);
        }
    }
}
