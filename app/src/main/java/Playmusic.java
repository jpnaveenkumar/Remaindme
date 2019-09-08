import android.content.Context;
import android.media.MediaPlayer;

import com.example.naveen.remaindme.R;

/**
 * Created by Naveen on 12-09-2018.
 */

public class Playmusic
{
    MediaPlayer mp;
    public void start(Context context)
    {
        mp=MediaPlayer.create(context, R.raw.aalaporaan);
        mp.start();
    }
    public void stop()
    {
        mp.stop();
    }
}
