package com.example.naveen.remaindme;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DailyFragment extends Fragment {

    public Button timer;
    public int hr,mi;
    public TextView tv;
    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Calendar cal=Calendar.getInstance();
        final int hour=cal.get(Calendar.HOUR);
        final int minute=cal.get(Calendar.MINUTE);
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_daily, container, false);
        timer=(Button)rootview.findViewById(R.id.time);
        tv=(TextView)rootview.findViewById(R.id.time1);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tpd=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hr=i;
                        mi=i1;
                        String ans="";
                        if(i<12){
                            if(i>=10)
                            ans=i+" : "+i1+" AM";
                            else
                                ans="0"+i+" : "+i1+" AM";
                        }
                        else{
                            ans=i+" : "+i1+" PM";
                        }
                        //tv.setText("");
                        //tv.setHint("");
                        tv.setText(ans);
                        FirstActivity fa= (FirstActivity) getActivity();
                        fa.setData1(i,i1);
                    }
                },hour,minute,false);
                tpd.show();
            }
        });
        return rootview;
    }
}
