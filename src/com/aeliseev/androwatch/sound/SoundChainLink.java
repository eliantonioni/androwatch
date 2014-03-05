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
