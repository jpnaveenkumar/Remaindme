package com.example.naveen.remaindme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        Thread td=new Thread(){
            public void run()
            {
                try {
                    sleep(5000);
                    Intent i=new Intent(FlashActivity.this,LockscreenActivity.class);
                    SharedPreferences sp1=getSharedPreferences("login", Context.MODE_PRIVATE);
                    String user=sp1.getString("user","new");
                    if(user.equals("new")) {
                        i.putExtra("type","new");
                        SharedPreferences.Editor ed=sp1.edit();
                        ed.putString("user","old");
                        ed.commit();
                    }
                    else{
                        i.putExtra("type","old");
                    }
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        td.start();
    }
}
