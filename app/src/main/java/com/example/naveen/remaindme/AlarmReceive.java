package com.example.naveen.remaindme;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Naveen on 03-09-2018.
 */

public class AlarmReceive extends WakefulBroadcastReceiver {
    MediaPlayer mp;
    public AlarmReceive()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences sp=context.getSharedPreferences("alert",Context.MODE_PRIVATE);
        int count=sp.getInt("12331",0);
        //if(count>0){
            Toast.makeText(context,"came",Toast.LENGTH_LONG).show();
            Log.d("came","working");
            String type=intent.getStringExtra("type");
            String name,desc,month,day,year;
            name=intent.getStringExtra("name");
            desc=intent.getStringExtra("desc");
            int id=intent.getIntExtra("id",0);
            Notification noti=new Notification.Builder(context)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(name)
                    .setContentText(desc)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1011,noti);
            PowerManager pm=(PowerManager)context.getSystemService(Context.POWER_SERVICE);
           // mp=MediaPlayer.create(context, R.raw.aalaporaan);
            //mp.start();
            Playmusic pmm=new Playmusic();
            pmm.start(context);
            boolean ison=pm.isScreenOn();
            if(ison==false)
            {
                PowerManager.WakeLock wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,"MyLock");
                wl.acquire(10000);
                PowerManager.WakeLock wl_cpu=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
                wl_cpu.acquire(10000);
            }
            intent=new Intent(context,LockscreenActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("desc",desc);
            intent.putExtra("type","alarm");
            intent.putExtra("id",id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
      /*  }
        else{

        }*/

    }
}
