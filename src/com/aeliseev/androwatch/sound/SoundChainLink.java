package com.aeliseev.androwatch.sound;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by AEliseev on 18.02.2014
 */
public class SoundChainLink {

    // The next element in the chain of responsibility
    protected SoundChainLink next;

    private int soundID = 0;

    public SoundChainLink(int soundID) {
        this.soundID = soundID;
    }

    public void setNext(SoundChainLink nextChainLink) {
        next = nextChainLink;
    }

    public void playSound(final Context context) {

        MediaPlayer player = MediaPlayer.create(context, soundID);

        if (soundID > 0 && next != null) {
            player.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next.playSound(context);
                    }
                }
            );
        }
        player.start();
    }
}
