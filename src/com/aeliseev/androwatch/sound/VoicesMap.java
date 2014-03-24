package com.aeliseev.androwatch.sound;

import android.util.Log;
import android.util.SparseArray;
import com.aeliseev.androwatch.AndrowatchWidgetProvider;
import com.aeliseev.androwatch.R;

/**
 * Created by aeliseev on 13.03.2014
 */
public class VoicesMap {

    private SparseArray<VoiceItem> voices = new SparseArray<>();

    public VoicesMap() {
        voices.put(0, new VoiceItem(
                R.raw.vm1_0,  R.raw.vm1_1,  R.raw.vm1_2,  R.raw.vm1_3,  R.raw.vm1_4,
                R.raw.vm1_5,  R.raw.vm1_6,  R.raw.vm1_7,  R.raw.vm1_8,  R.raw.vm1_9,
                R.raw.vm1_10, R.raw.vm1_11, R.raw.vm1_12, R.raw.vm1_13, R.raw.vm1_14,
                R.raw.vm1_15, R.raw.vm1_16, R.raw.vm1_17, R.raw.vm1_18, R.raw.vm1_19, R.raw.vm1_20,
                R.raw.vm1_30, R.raw.vm1_40, R.raw.vm1_50,
                R.raw.vm1_1a, R.raw.vm1_2e,
                R.raw.vm1_hours, R.raw.vm1_hour, R.raw.vm1_houra,
                R.raw.vm1_minut, R.raw.vm1_minuta, R.raw.vm1_minutes
        ));

        voices.put(1, new VoiceItem(
                R.raw.vm2_0,  R.raw.vm2_1,  R.raw.vm2_2,  R.raw.vm2_3,  R.raw.vm2_4,
                R.raw.vm2_5,  R.raw.vm2_6,  R.raw.vm2_7,  R.raw.vm2_8,  R.raw.vm2_9,
                R.raw.vm2_10, R.raw.vm2_11, R.raw.vm2_12, R.raw.vm2_13, R.raw.vm2_14,
                R.raw.vm2_15, R.raw.vm2_16, R.raw.vm2_17, R.raw.vm2_18, R.raw.vm2_19, R.raw.vm2_20,
                R.raw.vm2_30, R.raw.vm2_40, R.raw.vm2_50,
                R.raw.vm2_1a, R.raw.vm2_2e,
                R.raw.vm2_hours, R.raw.vm2_hour, R.raw.vm2_houra,
                R.raw.vm2_minut, R.raw.vm2_minuta, R.raw.vm2_minutes
        ));
    }

    public VoiceItem getVoiceItem(int voice) {
        int realVoice = voices.size() > voice ? voice : 0;
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "SOUNDING voice=" + realVoice);
        return voices.get(realVoice);
    }
}
