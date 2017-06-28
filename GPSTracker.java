package com.example.nkwm87.gps;

/**
 * Created by NKWM87 on 6/28/2017.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.IBinder;


public class GPSTracker extends Service implements LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    private final Context mContext;
    Location location;
    double latitude, longitude;
    boolean canGetLocation = false;

    protected LocationManager locationManager;

    public GPSTracker(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            locationManager  = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            }else{
                this.canGetLocation = true;
                if (isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this );
                    if (locationManager !=null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location !=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null){
                            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                                return location;
                            }

                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){}
        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {
        try {
            float bestAccuracy = -1f;
            if (location.getAccuracy() != 0.0f && (location.getAccuracy() < bestAccuracy) || bestAccuracy == -1f) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the 01user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    // return ;
                }
                locationManager.removeUpdates(this);
            }
            bestAccuracy = location.getAccuracy();
        } catch (NullPointerException e) {
            System.out.print("Caught the NullPointerException");
        }
    }

    @Override
    public void onProviderDisabled(String provider){
    }

    @Override
    public void onProviderEnabled(String provider){
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    public float getAccuracy(){
        return location.getAccuracy();
    }

}
