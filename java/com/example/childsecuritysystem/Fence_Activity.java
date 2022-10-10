package com.example.childsecuritysystem;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.BLUE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fence_Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {


    private GoogleMap mMap;
    //  private ActivityMapsBinding binding;
    private GeofencingClient geofencingClient;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private float GEOFENCE_RADIUS = 100;
    private String Geofence_Id = "Some_Geofence_ID";
    private GeoFenceHelper geoFenceHelper;
    MaterialButton save;
    //MaterialButton clear;

    ArrayList<LatLng> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_fence);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        save = findViewById(R.id.btn_Save_polygon);
//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Fence_Activity.this, "CLEAR Map ", Toast.LENGTH_SHORT).show();
//            }
//        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fenceId = 0;
                Gson gson = new Gson();
                fenceId = getIntent().getIntExtra("FenceId", 0);
                int length = markers.size();
                for (int i = 0; i < length; i++) {
                    LatLng s = markers.get(i);
                    Point point = new Point();
                    point.Latitude = s.latitude;
                    point.Longitude = s.longitude;
                    point.F_ID = fenceId;

                    String jsonString = gson.toJson(point);

                    CallResult result = WCFHandler.PostJsonResult("User/AddPoints", jsonString, "");
                    if (result.StatusCode == 200) {
                        Toast.makeText(Fence_Activity.this, "Fence Save Successfully......", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Fence_Activity.this, "Error in saving.........", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        geofencingClient = LocationServices.getGeofencingClient(this);
        geoFenceHelper = new GeoFenceHelper(this);
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

        // Add a marker in MureeRoad and move the camera
        LatLng MurreeRoad = new LatLng(33.643115, 73.0668849);
        mMap.addMarker(new MarkerOptions().position(MurreeRoad).title("Murree Road"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MurreeRoad));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MurreeRoad, 14));

        //Call function
        enableUserLocation();
        mMap.setOnMapLongClickListener(this);
    }
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //SHOW DIALOG TO A USER SIDE
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        FINE_LOCATION_ACCESS_REQUEST_CODE);
            }

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //we have a permission
                mMap.setMyLocationEnabled(true);
            } else {
                //we don't have a permission

            }
        }
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        //  mMap.clear();
        addMarker(latLng);
        //addCircle(latLng ,GEOFENCE_RADIUS );
        //addGeofence(latLng ,GEOFENCE_RADIUS);
        addPolygon(latLng);


    }

    int tag=0;
    int index =-1;
    private void addMarker(LatLng latLng) {
        Marker moveablemarker;
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker moveablemarker) {
                // TODO Auto-generated method stub
                index=-1;
                int counter = -1;
                Log.d("System out", "onMarkerDragStart..."+moveablemarker.getPosition().latitude+"..."+moveablemarker.getPosition().longitude);
                for (LatLng m:markers
                     ) {
                    counter++;
                    if(m.latitude==moveablemarker.getPosition().latitude && m.longitude==moveablemarker.getPosition().longitude){
                        index=counter;
                        break;
                    }
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker moveablemarker) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..."+moveablemarker.getPosition().latitude+"..."+moveablemarker.getPosition().longitude);

                index= Integer.parseInt(moveablemarker.getTag().toString());
                if(index!=-1){
                    markers.set(index,moveablemarker.getPosition());
                    drawPolygon();
                     }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(moveablemarker.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker moveablemarker) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });
    MarkerOptions markerOptions = new MarkerOptions().position(latLng).draggable(true);
        mMap.addMarker(markerOptions).setTag(tag);
        tag++;

    }


    @SuppressLint("MissingPermission")
    private void addGeofence(LatLng latLng, float radius) {
        //GeoFenceHelper geoFenceHelper = null;
        Geofence geofence = geoFenceHelper.getGeofence(Geofence_Id, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geoFenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geoFenceHelper.getPendingIntent();
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: GeoFencing Added......");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geoFenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure:" + errorMessage);

                    }
                });

    }

    PolygonOptions po = null;

    private void addPolygon(LatLng latLng) {
        if (po == null)
            po = new PolygonOptions();
        po.add(latLng);
        markers.add(latLng);
        mMap.addPolygon(po.strokeColor(BLUE).fillColor(BLUE));

    }

//////////////////////////// Recreate a function to draw again fence after updating/////////////////////////////
    private void drawPolygon() {

            po = new PolygonOptions();
        for (LatLng latLng:markers
             ) {

            po.add(latLng);
        }
        mMap.addPolygon(po.strokeColor(BLUE).fillColor(BLUE));

    }

}







