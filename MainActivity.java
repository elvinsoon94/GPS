package com.example.nkwm87.gps;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.provider.Settings;


public class MainActivity extends AppCompatActivity{

    private Button GPSButton;
    public TextView coordinate;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GPSButton = (Button)findViewById(R.id.GPSButton);

        GPSButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        gps = new GPSTracker(MainActivity.this);

                        if(gps.canGetLocation()){

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            coordinate = (TextView)findViewById(R.id.coordinate);
                            coordinate.setText("Latitude = " +latitude+ "Longitude = " +longitude);
                        }else{
                            coordinate = (TextView)findViewById(R.id.coordinate);
                            coordinate.setText("Error");
                        }
                    }
                }
        );
    }
}
