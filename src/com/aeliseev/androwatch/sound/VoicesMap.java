package com.aeliseev.androwatch.sound;

import android.util.Log;
import android.util.SparseArray;
import com.aeliseev.androwatch.AndrowatchWidgetProvider;
import com.aeliseev.androwatch.R;

import java.util.ArrayList;

/**
 * Created by AEliseev on 18.02.2014
 */
public class VoicesMap {

    private int currentVoice = 0;
    
    private ArrayList<SparseArray<VoiceItem>> voices = new ArrayList<>();

    public VoicesMap() {
        
        SparseArray<VoiceItem> voice1 = new SparseArray<>();
        voice1.put( 0, new VoiceItem(R.raw.vm1_0,  R.raw.vm1_hours, R.raw.vm1_0,   R.raw.vm1_minut));
        voice1.put( 1, new VoiceItem(R.raw.vm1_1,  R.raw.vm1_hour,  R.raw.vm1_1a,  R.raw.vm1_minuta));
        voice1.put( 2, new VoiceItem(R.raw.vm1_2,  R.raw.vm1_houra, R.raw.vm1_2e,  R.raw.vm1_minutes));
        voice1.put( 3, new VoiceItem(R.raw.vm1_3,  R.raw.vm1_houra, R.raw.vm1_3,   R.raw.vm1_minutes));
        voice1.put( 4, new VoiceItem(R.raw.vm1_4,  R.raw.vm1_houra, R.raw.vm1_4,   R.raw.vm1_minutes));
        voice1.put( 5, new VoiceItem(R.raw.vm1_5,  R.raw.vm1_hours, R.raw.vm1_5,   R.raw.vm1_minut));
        voice1.put( 6, new VoiceItem(R.raw.vm1_6,  R.raw.vm1_hours, R.raw.vm1_6,   R.raw.vm1_minut));
        voice1.put( 7, new VoiceItem(R.raw.vm1_7,  R.raw.vm1_hours, R.raw.vm1_7,   R.raw.vm1_minut));
        voice1.put( 8, new VoiceItem(R.raw.vm1_8,  R.raw.vm1_hours, R.raw.vm1_8,   R.raw.vm1_minut));
        voice1.put( 9, new VoiceItem(R.raw.vm1_9,  R.raw.vm1_hours, R.raw.vm1_9,   R.raw.vm1_minut));
        voice1.put(10, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_10,  R.raw.vm1_minut));
        voice1.put(11, new VoiceItem(R.raw.vm1_11, R.raw.vm1_hours, R.raw.vm1_11,  R.raw.vm1_minut));
        voice1.put(12, new VoiceItem(R.raw.vm1_12, R.raw.vm1_hours, R.raw.vm1_12,  R.raw.vm1_minut));
        voice1.put(13, new VoiceItem(R.raw.vm1_13, R.raw.vm1_hours, R.raw.vm1_13,  R.raw.vm1_minut));
        voice1.put(14, new VoiceItem(R.raw.vm1_14, R.raw.vm1_hours, R.raw.vm1_14,  R.raw.vm1_minut));
        voice1.put(15, new VoiceItem(R.raw.vm1_15, R.raw.vm1_hours, R.raw.vm1_15,  R.raw.vm1_minut));
        voice1.put(16, new VoiceItem(R.raw.vm1_16, R.raw.vm1_hours, R.raw.vm1_16,  R.raw.vm1_minut));
        voice1.put(17, new VoiceItem(R.raw.vm1_17, R.raw.vm1_hours, R.raw.vm1_17,  R.raw.vm1_minut));
        voice1.put(18, new VoiceItem(R.raw.vm1_18, R.raw.vm1_hours, R.raw.vm1_18,  R.raw.vm1_minut));
        voice1.put(19, new VoiceItem(R.raw.vm1_19, R.raw.vm1_hours, R.raw.vm1_19,  R.raw.vm1_minut));
        voice1.put(20, new VoiceItem(R.raw.vm1_20, R.raw.vm1_hours, R.raw.vm1_20,  R.raw.vm1_minut));
        voice1.put(21, new VoiceItem(R.raw.vm1_21, R.raw.vm1_hour,  R.raw.vm1_21a, R.raw.vm1_minuta));
        voice1.put(22, new VoiceItem(R.raw.vm1_22, R.raw.vm1_houra, R.raw.vm1_22e, R.raw.vm1_minutes));
        voice1.put(23, new VoiceItem(R.raw.vm1_23, R.raw.vm1_houra, R.raw.vm1_23,  R.raw.vm1_minutes));
        voice1.put(24, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_24,  R.raw.vm1_minutes));
        voice1.put(25, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_25,  R.raw.vm1_minut));
        voice1.put(26, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_26,  R.raw.vm1_minut));
        voice1.put(27, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_27,  R.raw.vm1_minut));
        voice1.put(28, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_28,  R.raw.vm1_minut));
        voice1.put(29, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_29,  R.raw.vm1_minut));
        voice1.put(30, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_30,  R.raw.vm1_minut));
        voice1.put(31, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_31a, R.raw.vm1_minuta));
        voice1.put(32, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_32e, R.raw.vm1_minutes));
        voice1.put(33, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_33,  R.raw.vm1_minutes));
        voice1.put(34, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_34,  R.raw.vm1_minutes));
        voice1.put(35, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_35,  R.raw.vm1_minut));
        voice1.put(36, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_36,  R.raw.vm1_minut));
        voice1.put(37, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_37,  R.raw.vm1_minut));
        voice1.put(38, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_38,  R.raw.vm1_minut));
        voice1.put(39, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_39,  R.raw.vm1_minut));
        voice1.put(40, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_40,  R.raw.vm1_minut));
        voice1.put(41, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_41a, R.raw.vm1_minuta));
        voice1.put(42, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_42e, R.raw.vm1_minutes));
        voice1.put(43, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_43,  R.raw.vm1_minutes));
        voice1.put(44, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_44,  R.raw.vm1_minutes));
        voice1.put(45, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_45,  R.raw.vm1_minut));
        voice1.put(46, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_46,  R.raw.vm1_minut));
        voice1.put(47, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_47,  R.raw.vm1_minut));
        voice1.put(48, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_48,  R.raw.vm1_minut));
        voice1.put(49, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_49,  R.raw.vm1_minut));
        voice1.put(50, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_50,  R.raw.vm1_minut));
        voice1.put(51, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_51a, R.raw.vm1_minuta));
        voice1.put(52, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_52e, R.raw.vm1_minutes));
        voice1.put(53, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_53,  R.raw.vm1_minutes));
        voice1.put(54, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_54,  R.raw.vm1_minutes));
        voice1.put(55, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_55,  R.raw.vm1_minut));
        voice1.put(56, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_56,  R.raw.vm1_minut));
        voice1.put(57, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_57,  R.raw.vm1_minut));
        voice1.put(58, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_58,  R.raw.vm1_minut));
        voice1.put(59, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_59,  R.raw.vm1_minut));
        
        SparseArray<VoiceItem> voice2 = new SparseArray<>();
        voice2.put( 0, new VoiceItem(R.raw.vm1_0,  R.raw.vm1_hours, R.raw.vm1_0,   R.raw.vm1_minut));
        voice2.put( 1, new VoiceItem(R.raw.vm1_1,  R.raw.vm1_hour,  R.raw.vm1_1a,  R.raw.vm1_minuta));
        voice2.put( 2, new VoiceItem(R.raw.vm1_2,  R.raw.vm1_houra, R.raw.vm1_2e,  R.raw.vm1_minutes));
        voice2.put( 3, new VoiceItem(R.raw.vm1_3,  R.raw.vm1_houra, R.raw.vm1_3,   R.raw.vm1_minutes));
        voice2.put( 4, new VoiceItem(R.raw.vm1_4,  R.raw.vm1_houra, R.raw.vm1_4,   R.raw.vm1_minutes));
        voice2.put( 5, new VoiceItem(R.raw.vm1_5,  R.raw.vm1_hours, R.raw.vm1_5,   R.raw.vm1_minut));
        voice2.put( 6, new VoiceItem(R.raw.vm1_6,  R.raw.vm1_hours, R.raw.vm1_6,   R.raw.vm1_minut));
        voice2.put( 7, new VoiceItem(R.raw.vm1_7,  R.raw.vm1_hours, R.raw.vm1_7,   R.raw.vm1_minut));
        voice2.put( 8, new VoiceItem(R.raw.vm1_8,  R.raw.vm1_hours, R.raw.vm1_8,   R.raw.vm1_minut));
        voice2.put( 9, new VoiceItem(R.raw.vm1_9,  R.raw.vm1_hours, R.raw.vm1_9,   R.raw.vm1_minut));
        voice2.put(10, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_10,  R.raw.vm1_minut));
        voice2.put(11, new VoiceItem(R.raw.vm1_11, R.raw.vm1_hours, R.raw.vm1_11,  R.raw.vm1_minut));
        voice2.put(12, new VoiceItem(R.raw.vm1_12, R.raw.vm1_hours, R.raw.vm1_12,  R.raw.vm1_minut));
        voice2.put(13, new VoiceItem(R.raw.vm1_13, R.raw.vm1_hours, R.raw.vm1_13,  R.raw.vm1_minut));
        voice2.put(14, new VoiceItem(R.raw.vm1_14, R.raw.vm1_hours, R.raw.vm1_14,  R.raw.vm1_minut));
        voice2.put(15, new VoiceItem(R.raw.vm1_15, R.raw.vm1_hours, R.raw.vm1_15,  R.raw.vm1_minut));
        voice2.put(16, new VoiceItem(R.raw.vm1_16, R.raw.vm1_hours, R.raw.vm1_16,  R.raw.vm1_minut));
        voice2.put(17, new VoiceItem(R.raw.vm1_17, R.raw.vm1_hours, R.raw.vm1_17,  R.raw.vm1_minut));
        voice2.put(18, new VoiceItem(R.raw.vm1_18, R.raw.vm1_hours, R.raw.vm1_18,  R.raw.vm1_minut));
        voice2.put(19, new VoiceItem(R.raw.vm1_19, R.raw.vm1_hours, R.raw.vm1_19,  R.raw.vm1_minut));
        voice2.put(20, new VoiceItem(R.raw.vm1_20, R.raw.vm1_hours, R.raw.vm1_20,  R.raw.vm1_minut));
        voice2.put(21, new VoiceItem(R.raw.vm1_21, R.raw.vm1_hour,  R.raw.vm1_21a, R.raw.vm1_minuta));
        voice2.put(22, new VoiceItem(R.raw.vm1_22, R.raw.vm1_houra, R.raw.vm1_22e, R.raw.vm1_minutes));
        voice2.put(23, new VoiceItem(R.raw.vm1_23, R.raw.vm1_houra, R.raw.vm1_23,  R.raw.vm1_minutes));
        voice2.put(24, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_24,  R.raw.vm1_minutes));
        voice2.put(25, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_25,  R.raw.vm1_minut));
        voice2.put(26, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_26,  R.raw.vm1_minut));
        voice2.put(27, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_27,  R.raw.vm1_minut));
        voice2.put(28, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_28,  R.raw.vm1_minut));
        voice2.put(29, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_29,  R.raw.vm1_minut));
        voice2.put(30, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_30,  R.raw.vm1_minut));
        voice2.put(31, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_31a, R.raw.vm1_minuta));
        voice2.put(32, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_32e, R.raw.vm1_minutes));
        voice2.put(33, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_33,  R.raw.vm1_minutes));
        voice2.put(34, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_34,  R.raw.vm1_minutes));
        voice2.put(35, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_35,  R.raw.vm1_minut));
        voice2.put(36, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_36,  R.raw.vm1_minut));
        voice2.put(37, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_37,  R.raw.vm1_minut));
        voice2.put(38, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_38,  R.raw.vm1_minut));
        voice2.put(39, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_39,  R.raw.vm1_minut));
        voice2.put(40, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_40,  R.raw.vm1_minut));
        voice2.put(41, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_41a, R.raw.vm1_minuta));
        voice2.put(42, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_42e, R.raw.vm1_minutes));
        voice2.put(43, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_43,  R.raw.vm1_minutes));
        voice2.put(44, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_44,  R.raw.vm1_minutes));
        voice2.put(45, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_45,  R.raw.vm1_minut));
        voice2.put(46, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_46,  R.raw.vm1_minut));
        voice2.put(47, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_47,  R.raw.vm1_minut));
        voice2.put(48, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_48,  R.raw.vm1_minut));
        voice2.put(49, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_49,  R.raw.vm1_minut));
        voice2.put(50, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_50,  R.raw.vm1_minut));
        voice2.put(51, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_51a, R.raw.vm1_minuta));
        voice2.put(52, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_52e, R.raw.vm1_minutes));
        voice2.put(53, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_53,  R.raw.vm1_minutes));
        voice2.put(54, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_54,  R.raw.vm1_minutes));
        voice2.put(55, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_55,  R.raw.vm1_minut));
        voice2.put(56, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_56,  R.raw.vm1_minut));
        voice2.put(57, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_57,  R.raw.vm1_minut));
        voice2.put(58, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_58,  R.raw.vm1_minut));
        voice2.put(59, new VoiceItem(R.raw.vm1_10, R.raw.vm1_hours, R.raw.vm1_59,  R.raw.vm1_minut));

        voices.add(voice1);
        voices.add(voice2);
    }

    public VoiceItem getVoiceItem(int number) {
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "SOUNDING voice=" + currentVoice + ", item=" + number);
        return voices.get(currentVoice).get(number);
    }

    public void setCurrentVoice(int currentVoice) {

        if (voices.size() >= currentVoice) {
            this.currentVoice = currentVoice;
        }
    }
}
