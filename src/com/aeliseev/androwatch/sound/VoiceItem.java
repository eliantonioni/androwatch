package com.aeliseev.androwatch.sound;

import android.util.SparseArray;
import com.aeliseev.androwatch.ChainLink;

/**
 * Created by aeliseev on 13.03.2014
 */
public class VoiceItem {

    private SparseArray<ChainLink> hoursMap = new SparseArray<>();
    private SparseArray<ChainLink> minutesMap = new SparseArray<>();
    
    /**
     *   ids[0-20] = id (мужского рода),
     *   ids[21]=30, ids[22]=40, ids[23]=50,
     *   ids[24,25] = одна,две (женского рода)
     *   ids[26] = часов
     *   ids[27] = час
     *   ids[28] = часа
     *   ids[29] = минут
     *   ids[30] = минута
     *   ids[31] = минуты
    **/
    public VoiceItem(int... ids) {
        
        if (ids.length < 32) {
            throw new RuntimeException("VoiceItem unexpected number of voice ids: " + ids.length);
        }

        hoursMap.put( 0, new SoundChainLink(ids[0],  ids[26]));
        hoursMap.put( 1, new SoundChainLink(ids[1],  ids[27]));
        hoursMap.put( 2, new SoundChainLink(ids[2],  ids[28]));
        hoursMap.put( 3, new SoundChainLink(ids[3],  ids[28]));
        hoursMap.put( 4, new SoundChainLink(ids[4],  ids[28]));
        hoursMap.put( 5, new SoundChainLink(ids[5],  ids[26]));
        hoursMap.put( 6, new SoundChainLink(ids[6],  ids[26]));
        hoursMap.put( 7, new SoundChainLink(ids[7],  ids[26]));
        hoursMap.put( 8, new SoundChainLink(ids[8],  ids[26]));
        hoursMap.put( 9, new SoundChainLink(ids[9],  ids[26]));
        hoursMap.put(10, new SoundChainLink(ids[10], ids[26]));
        hoursMap.put(11, new SoundChainLink(ids[11], ids[26]));
        hoursMap.put(12, new SoundChainLink(ids[12], ids[26]));
        hoursMap.put(13, new SoundChainLink(ids[13], ids[26]));
        hoursMap.put(14, new SoundChainLink(ids[14], ids[26]));
        hoursMap.put(15, new SoundChainLink(ids[15], ids[26]));
        hoursMap.put(16, new SoundChainLink(ids[16], ids[26]));
        hoursMap.put(17, new SoundChainLink(ids[17], ids[26]));
        hoursMap.put(18, new SoundChainLink(ids[18], ids[26]));
        hoursMap.put(19, new SoundChainLink(ids[19], ids[26]));
        hoursMap.put(20, new SoundChainLink(ids[20], ids[26]));
        hoursMap.put(21, new SoundChainLink(ids[20], ids[1], ids[27]));
        hoursMap.put(22, new SoundChainLink(ids[20], ids[2], ids[28]));
        hoursMap.put(23, new SoundChainLink(ids[20], ids[3], ids[28]));

        minutesMap.put( 0, new SoundChainLink(ids[0],  ids[29]));
        minutesMap.put( 1, new SoundChainLink(ids[24], ids[30]));
        minutesMap.put( 2, new SoundChainLink(ids[25], ids[31]));
        minutesMap.put( 3, new SoundChainLink(ids[3],  ids[31]));
        minutesMap.put( 4, new SoundChainLink(ids[4],  ids[31]));
        minutesMap.put( 5, new SoundChainLink(ids[5],  ids[29]));
        minutesMap.put( 6, new SoundChainLink(ids[6],  ids[29]));
        minutesMap.put( 7, new SoundChainLink(ids[7],  ids[29]));
        minutesMap.put( 8, new SoundChainLink(ids[8],  ids[29]));
        minutesMap.put( 9, new SoundChainLink(ids[9],  ids[29]));
        minutesMap.put(10, new SoundChainLink(ids[10], ids[29]));
        minutesMap.put(11, new SoundChainLink(ids[11], ids[29]));
        minutesMap.put(12, new SoundChainLink(ids[12], ids[29]));
        minutesMap.put(13, new SoundChainLink(ids[13], ids[29]));
        minutesMap.put(14, new SoundChainLink(ids[14], ids[29]));
        minutesMap.put(15, new SoundChainLink(ids[15], ids[29]));
        minutesMap.put(16, new SoundChainLink(ids[16], ids[29]));
        minutesMap.put(17, new SoundChainLink(ids[17], ids[29]));
        minutesMap.put(18, new SoundChainLink(ids[18], ids[29]));
        minutesMap.put(19, new SoundChainLink(ids[19], ids[29]));
        minutesMap.put(20, new SoundChainLink(ids[20], ids[29]));
        minutesMap.put(21, new SoundChainLink(ids[20], ids[24], ids[30]));
        minutesMap.put(22, new SoundChainLink(ids[20], ids[25], ids[31]));
        minutesMap.put(23, new SoundChainLink(ids[20], ids[3],  ids[31]));
        minutesMap.put(24, new SoundChainLink(ids[20], ids[4],  ids[31]));
        minutesMap.put(25, new SoundChainLink(ids[20], ids[5],  ids[29]));
        minutesMap.put(26, new SoundChainLink(ids[20], ids[6],  ids[29]));
        minutesMap.put(27, new SoundChainLink(ids[20], ids[7],  ids[29]));
        minutesMap.put(28, new SoundChainLink(ids[20], ids[8],  ids[29]));
        minutesMap.put(29, new SoundChainLink(ids[20], ids[9],  ids[29]));
        minutesMap.put(30, new SoundChainLink(ids[21], ids[29]));
        minutesMap.put(31, new SoundChainLink(ids[21], ids[24], ids[30]));
        minutesMap.put(32, new SoundChainLink(ids[21], ids[25], ids[31]));
        minutesMap.put(33, new SoundChainLink(ids[21], ids[3],  ids[31]));
        minutesMap.put(34, new SoundChainLink(ids[21], ids[4],  ids[31]));
        minutesMap.put(35, new SoundChainLink(ids[21], ids[5],  ids[29]));
        minutesMap.put(36, new SoundChainLink(ids[21], ids[6],  ids[29]));
        minutesMap.put(37, new SoundChainLink(ids[21], ids[7],  ids[29]));
        minutesMap.put(38, new SoundChainLink(ids[21], ids[8],  ids[29]));
        minutesMap.put(39, new SoundChainLink(ids[21], ids[9],  ids[29]));
        minutesMap.put(40, new SoundChainLink(ids[22], ids[29]));
        minutesMap.put(41, new SoundChainLink(ids[22], ids[24], ids[30]));
        minutesMap.put(42, new SoundChainLink(ids[22], ids[25], ids[31]));
        minutesMap.put(43, new SoundChainLink(ids[22], ids[3],  ids[31]));
        minutesMap.put(44, new SoundChainLink(ids[22], ids[4],  ids[31]));
        minutesMap.put(45, new SoundChainLink(ids[22], ids[5],  ids[29]));
        minutesMap.put(46, new SoundChainLink(ids[22], ids[6],  ids[29]));
        minutesMap.put(47, new SoundChainLink(ids[22], ids[7],  ids[29]));
        minutesMap.put(48, new SoundChainLink(ids[22], ids[8],  ids[29]));
        minutesMap.put(49, new SoundChainLink(ids[22], ids[9],  ids[29]));
        minutesMap.put(50, new SoundChainLink(ids[23], ids[29]));
        minutesMap.put(51, new SoundChainLink(ids[23], ids[24], ids[30]));
        minutesMap.put(52, new SoundChainLink(ids[23], ids[25], ids[31]));
        minutesMap.put(53, new SoundChainLink(ids[23], ids[3],  ids[31]));
        minutesMap.put(54, new SoundChainLink(ids[23], ids[4],  ids[31]));
        minutesMap.put(55, new SoundChainLink(ids[23], ids[5],  ids[29]));
        minutesMap.put(56, new SoundChainLink(ids[23], ids[6],  ids[29]));
        minutesMap.put(57, new SoundChainLink(ids[23], ids[7],  ids[29]));
        minutesMap.put(58, new SoundChainLink(ids[23], ids[8],  ids[29]));
        minutesMap.put(59, new SoundChainLink(ids[23], ids[9],  ids[29]));
    }

    public ChainLink getHoursChainLink(int hour) {
        return hoursMap.get(hour);
    }

    public ChainLink getMinutesChainLink(int minute) {
        return minutesMap.get(minute);
    }
}
