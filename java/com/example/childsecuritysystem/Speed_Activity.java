package com.example.childsecuritysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.childsecuritysystem.databinding.ActivitySpeed2Binding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Speed_Activity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener{
    ArrayList<LatLng> routes;
    private GoogleMap mMap;
    private ActivitySpeed2Binding binding;
    Button callspeed;
    TextView vspeed;
    MaterialButton ButtonViewspeedgraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routes = new ArrayList<>();
        binding = ActivitySpeed2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        callspeed=findViewById(R.id.calspeed);
        vspeed=findViewById(R.id.viewspeed);
        ButtonViewspeedgraph=findViewById(R.id.btnviewgraph);
        callspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateSpeed(view);
            }
        });
        ButtonViewspeedgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Speed_Activity.this,"View Speed Limit Graph ", Toast.LENGTH_SHORT).show();
                Intent intentgraph=new Intent(Speed_Activity.this,SpeedGraph_Activity.class);
                startActivity(intentgraph);
            }

        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng MurreeRoad = new LatLng(33.656761, 73.082737);
        mMap.addMarker(new MarkerOptions().position(MurreeRoad).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MurreeRoad));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MurreeRoad,15));

        /////////////////////////////////Call map function////////////////////////
        mMap.setOnMapLongClickListener(this);
    }


@Override
    public void onMapLongClick(@NonNull LatLng latLng)
    {
        routes.add(latLng);
        addMarker(latLng);
    }


    public void addMarker(LatLng latLng)
    {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).draggable(true);
        mMap.addMarker(markerOptions);
    }
 String speeding ="50";
    public void calculateSpeed(View view){
        double totalDistance = 0;
        LatLng point1 = null;
        for (LatLng point:routes
             ) {
            if(point1==null)
                point1 = point;

            totalDistance += Utility.distance(point.latitude,point.longitude,point1.latitude,point1.longitude);
            point1 = point;
        }

        int totalTime = (routes.size()-1)*2;
        double speed = totalDistance/totalTime;
        String speed2=Double.toString(speed);
        if(speed2 == speeding)
        {
            vspeed.setText(speed2 +"km/h");
            Toast.makeText(Speed_Activity.this,"Over Speeding", Toast.LENGTH_SHORT).show();
        }
        else
        {
            vspeed.setText(speed2 +"km/h");
            Toast.makeText(Speed_Activity.this,"Your Child Maintain Their Speed Limit", Toast.LENGTH_SHORT).show();
        }

//        vspeed.setText(speed2);

//        Intent intent=new Intent(getApplicationContext(),SpeedGraph_Activity.class);
//        intent.putExtra("Speed",speed2);
//        startActivity(intent);
    }
}