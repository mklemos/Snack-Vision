package com.example.archi.mapwithvolley;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.Toast;

import org.w3c.dom.Text;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

public class MapsActivity extends FragmentActivity implements OnMapLongClickListener, OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    public void onMapLongClick(LatLng point) {
        shootVolley();
       /* MarkerOptions marker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker())
                .position(
                        new LatLng(point.latitude, point.longitude)).title("interlinked");
        mMap.addMarker(marker);
        Toast.makeText(MapsActivity.this,"Within cells interlinked.",Toast.LENGTH_LONG).show();*/
    }

    public void shootVolley(){
        final TextView mTextView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8000/marker/?format=json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 20 characters of the response string.
                        Toast.makeText(MapsActivity.this,"In a cell:"+response.substring(0,20),Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast.makeText(MapsActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        LatLng sydney = new LatLng(40, -120);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in The States"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(this);
    }
}
