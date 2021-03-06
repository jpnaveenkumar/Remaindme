package com.example.naveen.remaindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateFragment extends Fragment {
    public DateFragment() {
        // Required empty public constructor
    }
    public Button timer,timer1;
    int hr,mi,dayy,monthh,yearr;
    TextView tv,tv2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Calendar cal=Calendar.getInstance();
        final int hour=cal.get(Calendar.HOUR);
        final int minute=cal.get(Calendar.MINUTE);
        final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_date, container, false);
        timer=(Button)rootview.findViewById(R.id.date);
        timer1=(Button)rootview.findViewById(R.id.date1);
        tv=(TextView)rootview.findViewById(R.id.time2);
        tv2=(TextView)rootview.findViewById(R.id.time3);
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
                        tv.setText("");
                        tv.setHint("");
                        tv.setText(ans);
                        FirstActivity fa= (FirstActivity) getActivity();
                        fa.setData1(i,i1);
                    }
                },hour,minute,false);
                tpd.show();
            }
        });
        timer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        dayy=i2;
                        monthh=i1;
                        yearr=i;
                        String ans=i+"/";
                        if(i1<10)
                            ans=ans+"0"+i1+"/";
                        else
                            ans=ans+i1+"/";
                        if(i2<10)
                            ans=ans+"0"+i2;
                        else
                            ans=ans+i2;
                        tv2.setText(ans);
                        FirstActivity fa= (FirstActivity) getActivity();
                        fa.setData2(i2,i1,i);
                    }
                },year,month,day);
                dpd.show();
            }
        });
        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
}
