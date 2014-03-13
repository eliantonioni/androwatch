package com.aeliseev.androwatch.sound;

import android.content.Context;
import android.media.MediaPlayer;
import com.aeliseev.androwatch.ChainLink;

/**
 * Created by AEliseev on 18.02.2014
 */
public class SoundChainLink extends ChainLink {

    private int soundID = 0;

    public SoundChainLink(int soundID) {
        this.soundID = soundID;
    }

    public SoundChainLink(int... soundIDs) {

        if (soundIDs.length > 0) {

            this.soundID = soundIDs[0];

            if (soundIDs.length > 1) {

                ChainLink[] arr = new ChainLink[soundIDs.length-1];
                for (int i=1; i<soundIDs.length; i++) {

                    arr[i-1] = new SoundChainLink(soundIDs[i]);
                }
                setupChain(arr);
            }
        }
    }

    public void doTaskWork(final Context context) {

        MediaPlayer player = MediaPlayer.create(context, soundID);

        if (soundID > 0 && next != null) {
            player.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next.doTaskWork(context);
                    }
                }
            );
        }

        player.start();
    }
}
