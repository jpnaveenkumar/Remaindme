package com.example.naveen.remaindme;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

import java.util.Calendar;


public class FirstActivity extends AppCompatActivity {

    public RadioGroup rg;
    public String name,desc;
    public Button b1;
    public EditText e1,e2;
    public Fragment frag;
    String choosen="Daily";
    int hr,mini,day,month,year,count;

    public void setData1(int hour,int minute)
    {
        hr=hour;
        mini=minute;
        //Toast.makeText(getActivity(),hr+" "+mini,Toast.LENGTH_LONG);
    }

    public void setData2(int d,int m,int y)
    {
        day=d;
        month=m;
        year=y;
        //Toast.makeText(getApplicationContext(),day+" "+month+" "+year,Toast.LENGTH_LONG);

    }

    public void setData3(int c)
    {
        count=c;
        //Toast.makeText(getApplicationContext(),count,Toast.LENGTH_LONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        rg=(RadioGroup)findViewById(R.id.type);
        b1=(Button)findViewById(R.id.save);
        e1=(EditText)findViewById(R.id.name1);
        e2=(EditText)findViewById(R.id.desc);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp=getSharedPreferences("alert", Context.MODE_PRIVATE);
                int cur=sp.getInt("current",0);
                SharedPreferences.Editor edit=sp.edit();
                edit.putInt("current",cur+6);
                edit.commit();
                //Intent ii=new Intent(FirstActivity.this,LockscreenActivity.class);
                //startActivity(ii);
                name=e1.getText().toString();
                desc=e2.getText().toString();
                //Intent i=new Intent(FirstActivity.this,AlarmReceive.class);
                Intent i=new Intent("com.example.naveen.remaindme.AlarmReceive");
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                i.putExtra("name",name);
                i.putExtra("desc",desc);
                if(choosen.equals("Daily")){
                    i.putExtra("type","daily");
                    i.putExtra("id",cur);
                    //cal.add(Calendar.HOUR_OF_DAY,hr);
                    //cal.add(Calendar.MINUTE,mini);
                    //cal.add(Calendar.SECOND,0);
                    //cal.add(Calendar.YEAR,2018);
                    //cal.add(Calendar.MONTH,8);
                    //cal.add(Calendar.DAY_OF_MONTH,4);
                    cal.clear();
//.Set(Year, Month, Day, Hour, Minutes, Seconds);
                    Calendar cal1=Calendar.getInstance();
                    int r1=cal1.get(Calendar.YEAR);
                    int r2=cal1.get(Calendar.MONTH);
                    int r3=cal1.get(Calendar.DAY_OF_MONTH);
                    cal.set(r1,r2,r3, hr, mini, 0);
                    SharedPreferences sp1 = getSharedPreferences("password", Context.MODE_PRIVATE);
                    SharedPreferences.Editor se = sp1.edit();
                    se.putInt(String.valueOf(cur),3);
                    se.commit();
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),cur,i,PendingIntent.FLAG_UPDATE_CURRENT);
                   // am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pi);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),cur+1,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()+(1000*60*3),pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),cur+2,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()+(1000*60*6),pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),cur+3,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()+(1000*60*9),pi);
                    //boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), 1233891,
                      //      new Intent(FirstActivity.this,AlarmReceive.class),
                        //    PendingIntent.FLAG_NO_CREATE) != null);

                   // if (alarmUp)
                    {
                     //   Log.d("myTag", "Alarm is already active");
                    }
                    Toast.makeText(getApplicationContext(),hr+" "+mini+" "+r1+" "+r2+" "+r3,Toast.LENGTH_LONG).show();
                }
                else if(choosen.equals("Specific Date")){
                    i.putExtra("type","specific date");
                    i.putExtra("id",cur);
                    cal.clear();
                    //cal.add(Calendar.HOUR_OF_DAY,hr);
                    //cal.add(Calendar.MINUTE,mini);
                    //cal.add(Calendar.SECOND,0);
                   /// cal.add(Calendar.YEAR,year);
                    //cal.add(Calendar.MONTH,month);
                    //cal.add(Calendar.DAY_OF_MONTH,day);
                    cal.set(year,month,day,hr,mini,0);
                    Toast.makeText(getApplicationContext(),day+" "+year+" "+month+" "+hr+" "+mini,Toast.LENGTH_LONG).show();
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),cur,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
                }
                else{
                    i.putExtra("type","frequency");
                    i.putExtra("id",cur);
                    cal.add(Calendar.HOUR_OF_DAY,hr);
                    cal.add(Calendar.MINUTE,mini);
                    cal.add(Calendar.SECOND,0);
                    cal.add(Calendar.YEAR,year);
                    cal.add(Calendar.MONTH,month);
                    cal.add(Calendar.DAY_OF_MONTH,day);
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),cur,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
                }
                //Toast.makeText(getApplicationContext(),day+" "+month+" "+year+" "+count,Toast.LENGTH_LONG).show();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                if(rb.getText().equals("Daily")) {
                    choosen="Daily";
                    frag=new DailyFragment();
                }
                else if(rb.getText().equals("Specific Date")) {
                    choosen="Specific Date";
                    frag=new DateFragment();
                }
                else {
                   choosen="Frequency";
                   frag=new FrequencyFragment();
                }
                ft.replace(R.id.place,frag);
                ft.commit();
            }
        });
    }
}
