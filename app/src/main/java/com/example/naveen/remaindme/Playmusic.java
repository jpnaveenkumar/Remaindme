package com.example.naveen.remaindme;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Naveen on 12-09-2018.
 */

public class Playmusic
{
    public static MediaPlayer mp;
    public void start(Context context)
    {
        mp=MediaPlayer.create(context, R.raw.aalaporaan1);
        mp.start();
    }
    public void stop()
    {
        mp.stop();
    }
}
