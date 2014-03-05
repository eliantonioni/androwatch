package com.aeliseev.androwatch.sound;

import android.content.Context;
import com.aeliseev.androwatch.ChainLink;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by AEliseev on 17.02.2014
 */
public class SoundPlayer {

    private VoicesMap vmap = new VoicesMap();

    public void playSoundChain(Context context, Date date, ChainLink callback) {

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
    }
}
