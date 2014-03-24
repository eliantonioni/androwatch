package com.aeliseev.androwatch.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import com.aeliseev.androwatch.AndrowatchWidgetProvider;
import com.aeliseev.androwatch.ChainLink;

/**
 * Created by aeliseev on 24.03.2014
 */
public class SDFileChainLink extends ChainLink {

    private String filePath = null;

    public SDFileChainLink(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void doTaskWork(final Context context) {

        MediaPlayer player = MediaPlayer.create(context,Uri.parse(filePath));
        Log.d(AndrowatchWidgetProvider.WIDGET_LOG_TAG, "SOUNDING " + filePath);

        if (!filePath.isEmpty()) {
            if (next != null) {
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
        else {
            if (next != null) {
                next.doTaskWork(context);
            }
        }
    }
}
