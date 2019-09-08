package com.example.naveen.remaindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends AppCompatActivity
{
    SwipeButton sb1,sb2;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        sb1=(SwipeButton)findViewById(R.id.snooze);
        sb2=(SwipeButton)findViewById(R.id.end);
        sb1.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                //Playmusic pm=new Playmusic();
                //pm.stop();
                Playmusic.mp.stop();
                final String name=getIntent().getStringExtra("name");
                final String desc=getIntent().getStringExtra("desc");
                final int id=getIntent().getIntExtra("id",0);
                SharedPreferences sp1 = getSharedPreferences("password", Context.MODE_PRIVATE);
                int r=sp1.getInt(String.valueOf(id),0);
                if(r>0){
                    SharedPreferences.Editor se = sp1.edit();
                    se.putInt(String.valueOf(id),r-1);
                    se.commit();
                    if(r==1) {
                        //do some action
                    }
                }
                else{

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
                                    String val = addressList.get(0).getAddressLine(1)+","+addressList.get(0).getLocality();
                                    SmsManager smsManager = SmsManager.getDefault();
                                    ArrayList<String> messagelist = smsManager.divideMessage("Your Child is not Reached and last found location is "+val+" google map link https://www.google.com/maps/search/?api=1&query="+lat+","+lon);
                                    smsManager.sendMultipartTextMessage("8124943075", null,messagelist, null, null);
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
                                    smsManager.sendMultipartTextMessage("8124943075", null,messagelist, null, null);
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
               // SharedPreferences sp = getSharedPreferences("password", Context.MODE_PRIVATE);
                Toast.makeText(getApplicationContext(),"you clicked snooze",Toast.LENGTH_LONG).show();

            }
        });

        sb2.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                Toast.makeText(getApplicationContext(),"you clicked end alert",Toast.LENGTH_LONG).show();
                Playmusic.mp.stop();
                final String name=getIntent().getStringExtra("name");
                final String desc=getIntent().getStringExtra("desc");
                final int id=getIntent().getIntExtra("id",0);
                SharedPreferences sp1 = getSharedPreferences("password", Context.MODE_PRIVATE);
                int r=sp1.getInt(String.valueOf(id),0);
                Intent i=new Intent("com.example.naveen.remaindme.AlarmReceive");
                AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
                if(r==3){
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),id+1,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),id+2,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),id+3,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                }
                else if(r==2){
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),id+2,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                    pi=PendingIntent.getBroadcast(getApplicationContext(),id+3,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);

                }
                else if(r==1){
                    PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),id+3,i,PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                }
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> messagelist = smsManager.divideMessage("Your Child is Reached");
                smsManager.sendMultipartTextMessage("8190013422", null,messagelist, null, null);
                //Intent smsIntent = new Intent(Intent.ACTION_SEND);
                //smsIntent.setData(Uri.parse("smsto:"));
               // Uri uri = Uri.parse("smsto:" + "6381108152");
// Create intent with the action and data
                //Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
// smsIntent.setData(uri); // We just set the data in the constructor above
// Set the message
                //smsIntent.putExtra("sms_body","Your child is Reached");

                //startActivity(smsIntent);
                //startActivity(smsIntent);
            }
        });
    }
}
