package com.example.naveen.remaindme;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LockscreenActivity extends AppCompatActivity {

    public Button b1,b2,b3,b4,b5,b6,b7,b8,b9,sub;
    public TextView t1;
    public FrameLayout cl;
    LocationManager lm;
    String pass="",pass1="0",gh;
    private SensorManager sm;
    private float acelVal; // current acceleration including gravity
    private float acelLast; // last acceleration including gravity
    private float shake; // acceleration apart from gravity
    int mode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        gh=getIntent().getStringExtra("type");
        final String name=getIntent().getStringExtra("name");
        final String desc=getIntent().getStringExtra("desc");
        final int id=getIntent().getIntExtra("id",0);
        Toast.makeText(getApplicationContext(),gh, Toast.LENGTH_SHORT).show();
        t1=(TextView)findViewById(R.id.content);
        cl=(FrameLayout)findViewById(R.id.coordlay);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        b6=(Button)findViewById(R.id.b6);
        b7=(Button)findViewById(R.id.b7);
        b8=(Button)findViewById(R.id.b8);
        b9=(Button)findViewById(R.id.b9);
        sub=(Button)findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gh.equals("new")) {
                    if (mode == 0) {
                        if (pass.length() != 4) {
                            Slide slide = new Slide();
                            slide.setSlideEdge(Gravity.LEFT);
                            t1.setText("enter 4 digits from first");
                            TransitionManager.beginDelayedTransition(cl,slide);
                            t1.setVisibility(View.VISIBLE);
                            pass = "";
                        } else {
                            mode++;
                            pass1 = pass;
                            pass = "";
                            Slide slide = new Slide();
                            slide.setSlideEdge(Gravity.LEFT);
                            t1.setText("confirm password again");
                            TransitionManager.beginDelayedTransition(cl,slide);
                            t1.setVisibility(View.VISIBLE);
                        }
                    } else if (mode == 1) {
                        if (pass.length() != 4) {
                            Slide slide = new Slide();
                            slide.setSlideEdge(Gravity.LEFT);
                            t1.setText("enter 4 digits from first");
                            TransitionManager.beginDelayedTransition(cl,slide);
                            t1.setVisibility(View.VISIBLE);
                            pass = "";
                        } else {
                            if (pass.equals(pass1)) {
                                Intent i = new Intent(LockscreenActivity.this, FirstActivity.class);
                                SharedPreferences sp = getSharedPreferences("password", Context.MODE_PRIVATE);
                                SharedPreferences.Editor se = sp.edit();
                                se.putString("forward", pass);
                                //se.putString("reverse",pass);
                                se.commit();
                                startActivity(i);
                            } else {
                                pass = "";
                                pass1 = "";
                                mode = 0;
                                Slide slide = new Slide();
                                slide.setSlideEdge(Gravity.LEFT);
                                t1.setText("enter password twice again");
                                TransitionManager.beginDelayedTransition(cl,slide);
                                t1.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                else if(gh.equals("old")){
                    if (pass.length() != 4) {
                        Slide slide = new Slide();
                        slide.setSlideEdge(Gravity.LEFT);
                        t1.setText("enter 4 digits from first");
                        TransitionManager.beginDelayedTransition(cl,slide);
                        t1.setVisibility(View.VISIBLE);
                        pass = "";
                    }
                    else{
                        SharedPreferences sp = getSharedPreferences("password", Context.MODE_PRIVATE);
                        String ch=sp.getString("forward","");
                        StringBuffer sb=new StringBuffer(ch);
                        if(ch.equals(pass))
                        {
                            Intent i = new Intent(LockscreenActivity.this, FirstActivity.class);
                            startActivity(i);
                        }
                        else if(sb.reverse().equals(pass)){
                            //code to handle emergency
                        }
                        else{
                            Slide slide = new Slide();
                            slide.setSlideEdge(Gravity.LEFT);
                            t1.setText("Incorrect password");
                            TransitionManager.beginDelayedTransition(cl,slide);
                            t1.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else if(gh.equals("alarm")){

                    if (pass.length() != 4) {
                        Slide slide = new Slide();
                        slide.setSlideEdge(Gravity.LEFT);
                        t1.setText("enter 4 digits from first");
                        TransitionManager.beginDelayedTransition(cl,slide);
                        t1.setVisibility(View.VISIBLE);
                        pass = "";
                    }
                    else{
                        SharedPreferences sp = getSharedPreferences("password", Context.MODE_PRIVATE);
                        String ch=sp.getString("forward","");
                        StringBuffer sb=new StringBuffer(ch);
                        if(ch.equals(pass))
                        {
                            Intent ii=new Intent(LockscreenActivity.this,ActionActivity.class);
                            ii.putExtra("name",name);
                            ii.putExtra("desc",desc);
                            ii.putExtra("id",id);
                            startActivity(ii);
                        }
                        else if(sb.reverse().equals(pass)){
                            //code to handle emergency
                        }
                        else{
                            Slide slide = new Slide();
                            slide.setSlideEdge(Gravity.LEFT);
                            t1.setText("Incorrect password");
                            TransitionManager.beginDelayedTransition(cl,slide);
                            t1.setVisibility(View.VISIBLE);
                        }
                    }
                    //code to handle alarm notification
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
              pass=pass.concat("1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("3");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("4");
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("5");
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("6");
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("7");
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("8");
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setVisibility(View.INVISIBLE);
                pass=pass.concat("9");
            }
        });
        if(gh.equals("new"))
        {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            t1.setText("enter 4 Digit pin code");
            TransitionManager.beginDelayedTransition(cl,slide);
            t1.setVisibility(View.VISIBLE);
        }
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        shake = 0.00f;
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;

    }
    private final SensorEventListener sensorListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent se) {
            onSensorChangedCheck(se);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void onSensorChangedCheck(SensorEvent se) {
        //i = 1;
        //Toast.makeText(context, "on sensor changed --"+i, Toast.LENGTH_SHORT).show();
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        acelLast = acelVal;
        acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = acelVal - acelLast;
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        shake = shake * 0.9f + delta; // perform low-cut filter
        String value = "acelval--"+acelVal+"--delta--"+delta+"--shake--"+shake;
        //Toast.makeText(context, value , Toast.LENGTH_SHORT).show();
        if(shake >12)
        {
            sm.unregisterListener(sensorListener);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(getApplicationContext(), "network provider", Toast.LENGTH_LONG).show();
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        String user = getIntent().getExtras().getString("name");
                        LatLng ll = new LatLng(lat, lon);
                        Toast.makeText(getApplicationContext(), "lat=" + String.valueOf(lat) + " long=" + String.valueOf(lon), Toast.LENGTH_LONG).show();
                        Geocoder g = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = g.getFromLocation(lat, lon, 1);
                            String val = addressList.get(0).getLocality();
                            SmsManager smsManager = SmsManager.getDefault();
                            ArrayList<String> messagelist = smsManager.divideMessage("Your Child is not Reached and last found location is "+val+" google map link https://www.google.com/maps/search/?api=1&query="+lat+","+lon);
                            smsManager.sendMultipartTextMessage("8800270681" , null,messagelist, null, null);
                            lm.removeUpdates(this);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }


                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            } else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getApplicationContext(), "gps provider", Toast.LENGTH_LONG).show();
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        LatLng ll = new LatLng(lat, lon);
                        Geocoder g = new Geocoder(getApplicationContext());
                        try {
                            List<android.location.Address> addressList = g.getFromLocation(lat, lon, 1);
                            String val = addressList.get(0).getLocality();
                            SmsManager smsManager = SmsManager.getDefault();
                            ArrayList<String> messagelist = smsManager.divideMessage("Your Child is not Reached and last found location is "+val+" google map link https://www.google.com/maps/search/?api=1&query="+lat+","+lon);
                            smsManager.sendMultipartTextMessage("8800270681", null,messagelist, null, null);
                            lm.removeUpdates(this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }
        }
    }
}
