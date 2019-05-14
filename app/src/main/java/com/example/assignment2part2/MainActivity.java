package com.example.assignment2part2;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtLatitude =(TextView) findViewById(R.id.txtLatitude);
        final TextView txtLongitude =(TextView) findViewById(R.id.txtLongitude);
        final Button btnGetLocation = (Button) findViewById(R.id.btnGetLocation);
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Task<Location> location = client.getLastLocation();

                    location.addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            txtLatitude.setText(Double.toString(task.getResult().getLatitude()));
                            txtLongitude.setText((Double.toString(task.getResult().getLongitude())));
                            System.err.println(task.getResult().getLatitude());
                        }
                    });
                }catch(SecurityException ex){
                    ex.printStackTrace();
                }
            }
        });

        LocationRequest req = new LocationRequest();
        req.setInterval(2000);
        req.setFastestInterval(500);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



    }
}
