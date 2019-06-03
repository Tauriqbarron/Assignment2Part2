package com.example.assignment2part2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class MainActivity extends AppCompatActivity {
    private static int Request_Location = 99;
    private Button btnGetLocation;
    private String TAG = "Map App";
    private String Lat,Lng;
    private TextView txtLatitude,txtLongitude;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLatitude =(TextView) findViewById(R.id.txtLatitude);
        txtLongitude =(TextView) findViewById(R.id.txtLongitude);
        displayLocation(this);
    }
    public void displayLocation(Context context){

        // This IF statement checks if permissions for the app to use location services has been granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},Request_Location);
        }
        btnGetLocation = findViewById(R.id.btnGetLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        // This function takes the last know location and displays the latitude and longitude on the
        // activity
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Task<Location> currentLocation =fusedLocationClient.getLastLocation();
                    currentLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                         Lat = Double.toString(currentLocation.getResult().getLatitude());
                         Lng = Double.toString(currentLocation.getResult().getLongitude());
                         txtLatitude.setText(Lat);
                         txtLongitude.setText(Lng);
                        }
                    });
                }catch (SecurityException e){
                    Log.e(TAG,"Get Location: Security Exception: "+e.getMessage());
                }
            }
        });
    }
}
