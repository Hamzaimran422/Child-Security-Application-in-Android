package com.example.childsecuritysystem;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.childsecuritysystem.databinding.ActivityCheckFenceBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Check_Fence_Activity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener
        {
            private GoogleMap mMap;
            public static ArrayList<LatLng> points;
            ArrayList<LatLng> markers=new ArrayList<>();
            MaterialButton saveAgain;
            MaterialButton savecredentionalagain;
            MaterialButton draw;
            MaterialButton clear;

    /////////////////////////Initialize Map Polyline/////////////////////////////////////////
    Polyline polyline=null;
    List<LatLng> polylatlnglist=new ArrayList<>();
    List<Marker> markerlatlnglist=new ArrayList<>();

    private ActivityCheckFenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckFenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        saveAgain=findViewById(R.id.btn_Save_Multipolygon);
        savecredentionalagain=findViewById(R.id.btn_add_credentional_again);
        draw=findViewById(R.id.btn_draw_polyline);
        clear=findViewById(R.id.btn_clear_route);
        /////////////////////////////////  DRAW BUTTON  ///////////////////////////////////////////////
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polyline!=null)
                    polyline.remove();
                /////////////////////////// Create polyline option///////////////////////
                PolylineOptions polylineOptions =new PolylineOptions()
                        .addAll(polylatlnglist).clickable(true).color(RED);
                polyline=mMap.addPolyline(polylineOptions);
            }
        });
        ///////////////////////////////////CLEAR POLYLINE ///////////////////////////////////////
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polyline!=null)
                    polyline.remove();
                for (Marker marker:markerlatlnglist)
                    marker.remove();
                polylatlnglist.clear();
                markerlatlnglist.clear();
            }
        });
        saveAgain.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(Check_Fence_Activity.this, "Fence Save Successfully......", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Check_Fence_Activity.this, "Error in saving", Toast.LENGTH_SHORT).show();
                        }
                    }
            }

        });
        savecredentionalagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Check_Fence_Activity.this,Add_Fence_Credential_Activity.class);
                Toast.makeText(Check_Fence_Activity.this, "Goto Fence Credential", Toast.LENGTH_SHORT).show();
                startActivity(intent);
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
        if(points.size()>0)
        {
        PolygonOptions pg = new PolygonOptions();
        for (LatLng latLng : points)
        {
            pg.add(latLng);
            mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Fence Marker"));
        }
        googleMap.addPolygon(pg);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 16));
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

////////////////////////////Create Marker//////////////////////////////////
                MarkerOptions markerOptions=new MarkerOptions().position(latLng).draggable(true)
                        .title("Child");
                Marker marker=mMap.addMarker(markerOptions);
                polylatlnglist.add(latLng);
                markerlatlnglist.add(marker);


                if(!isPointInPolygon(latLng,points))
                    Toast.makeText(Check_Fence_Activity.this, "Child outside the fence.. ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Check_Fence_Activity.this, "Child inside the fence.. ", Toast.LENGTH_LONG).show();
//
//
//                if(isPointInPolygon(latLng,points))
//                    Toast.makeText(Check_Fence_Activity.this, "Child outside the fence.. ", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(Check_Fence_Activity.this, "Child inside the fence.. ", Toast.LENGTH_LONG).show();

            }
        });
    }

    }
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        addMarker(latLng);
        addPolygon(latLng);
    }

    ///////////////////Build in function////////////////////////////////////
    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }


    public boolean pointInPolygon(LatLng point, ArrayList<LatLng> path) {
        // ray casting alogrithm http://rosettacode.org/wiki/Ray-casting_algorithm
        int crossings = 0;
       // List<LatLng> path = polygon.getPoints();
        path.remove(path.size()-1); //remove the last point that is added automatically by getPoints()

        // for each edge
        for (int i=0; i < path.size(); i++) {
            LatLng a = path.get(i);
            int j = i + 1;
            //to close the last edge, you have to take the first point of your polygon
            if (j >= path.size()) {
                j = 0;
            }
            LatLng b = path.get(j);
            if (rayCrossesSegment(point, a, b)) {
                crossings++;
            }
        }

        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    public boolean rayCrossesSegment(LatLng point, LatLng a,LatLng b) {
        // Ray Casting algorithm checks, for each segment, if the point is 1) to the left of the segment and 2) not above nor below the segment. If these two conditions are met, it returns true
        double px = point.longitude,
                py = point.latitude,
                ax = a.longitude,
                ay = a.latitude,
                bx = b.longitude,
                by = b.latitude;
        if (ay > by) {
            ax = b.longitude;
            ay = b.latitude;
            bx = a.longitude;
            by = a.latitude;
        }
        // alter longitude to cater for 180 degree crossings
        if (px < 0 || ax <0 || bx <0) { px += 360; ax+=360; bx+=360; }
        // if the point has the same latitude as a or b, increase slightly py
        if (py == ay || py == by) py += 0.00000001;


        // if the point is above, below or to the right of the segment, it returns false
        if ((py > by || py < ay) || (px > Math.max(ax, bx))){
            return false;
        }
        // if the point is not above, below or to the right and is to the left, return true
        else if (px < Math.min(ax, bx)){
            return true;
        }
        // if the two above conditions are not met, you have to compare the slope of segment [a,b] (the red one here) and segment [a,p] (the blue one here) to see if your point is to the left of segment [a,b] or not
        else {
            double red = (ax != bx) ? ((by - ay) / (bx - ax)) : Double.POSITIVE_INFINITY;
            double blue = (ax != px) ? ((py - ay) / (px - ax)) : Double.POSITIVE_INFINITY;
            return (blue >= red);
        }
    }
    int tag=0;
    int index=-1;
    private void  addMarker(LatLng latLng)
    {
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
        MarkerOptions markerOptions=new MarkerOptions().position(latLng).draggable(true);
        mMap.addMarker(markerOptions).setTag(tag);
        tag++;
    }

    PolygonOptions po = null;
    PolylineOptions pl=null;

    private void addPolygon(LatLng latLng) {
        if (po == null)
            po = new PolygonOptions();
        po.add(latLng);
        markers.add(latLng);

        mMap.addPolygon(po.strokeColor(BLUE).fillColor(TRANSPARENT));

    }
    private void drawPolygon() {

        po = new PolygonOptions();
        for (LatLng latLng:markers
        ) {

            po.add(latLng);
        }
        mMap.addPolygon(po.strokeColor(BLUE).fillColor(TRANSPARENT));

    }

}