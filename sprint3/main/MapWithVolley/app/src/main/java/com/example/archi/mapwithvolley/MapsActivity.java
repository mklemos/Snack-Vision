package com.example.archi.mapwithvolley;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapLongClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    RelativeLayout r1;
    private LatLng placementPoint;
    private float markerColor;
    private String markerType="default";


    @Override
    public void onMapLongClick(LatLng point) {
        placementPoint=point;
        openContextMenu(findViewById(R.id.activity_main));
    }

    //send request via volley for all nodes on DB
    //successful response will add markers
    public void shootVolley(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = "http://black-cakl.appspot.com/new_node/?format=json";

        String url = "http://10.0.2.2:8000/marker/?format=json";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject obj = response.getJSONObject(0);

                            addMarkers(response);
                            //Toast.makeText(MapsActivity.this,obj.getString("type"),Toast.LENGTH_LONG).show();
                        } catch ( Exception error) {
                            Toast.makeText(MapsActivity.this,"Response.getString:"+error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this,"Bad response:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "admin:password123";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }



    public void postVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://black-cakl.appspot.com/new_node/";
        String url = "http://10.0.2.2:8000/marker/?format=json";

        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Toast.makeText(MapsActivity.this,"Good Response",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Response", "Bad response in putVolley:"+error.toString());
                        Toast.makeText(MapsActivity.this,"Bad response in putVolley:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("type", markerType);
                params.put("lon", Double.toString(placementPoint.latitude));
                params.put("lat",Double.toString(placementPoint.longitude));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "admin:password123";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                //not passing JSON data, so ignore this line.
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(putRequest);
    }


    //parse the jsonarry in a loop until each marker has been added to the map, set the camera to the last marker added
    //called in shootvolley()
    public void addMarkers(JSONArray jsonArray) {
        try{
            JSONObject jsonMarker;
            LatLng markerLatLon = new LatLng(40, -120);

            for(int i=0; i < jsonArray.length(); i++){
                jsonMarker = jsonArray.getJSONObject(i);

                String markerName = jsonMarker.getString("type");
                double lat = Double.parseDouble(jsonMarker.getString("lat"));
                double lon = Double.parseDouble(jsonMarker.getString("lon"));

                markerLatLon = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(markerLatLon).title(markerName));
            }

            markerLatLon = new LatLng(40.866516d, -124.082840d);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLon));
            mMap.animateCamera( CameraUpdateFactory.zoomTo(12));
        } catch (Exception error ) {
            Toast.makeText(MapsActivity.this,"Trouble grabbing JSON data in addMarkers: "+error.toString(),Toast.LENGTH_LONG).show();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        r1=(RelativeLayout)findViewById(R.id.activity_main);
        registerForContextMenu(r1);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);

        menu.setHeaderTitle("Select one:");
        menu.setHeaderIcon(android.R.drawable.btn_star_big_off);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch(id) {
            case R.id.item1:{
                Toast.makeText(MapsActivity.this,"Apple tree placed",Toast.LENGTH_LONG).show();
                markerType="Apple Tree";
                markerColor= 120;
            }
            break;
            case R.id.item2:{
                Toast.makeText(MapsActivity.this,"Blackberry bush placed",Toast.LENGTH_LONG).show();
                markerType="Blackberry Bush";
                markerColor=300;
            }
            break;
            case R.id.item3:{
                Toast.makeText(MapsActivity.this,"Garden placed",Toast.LENGTH_LONG).show();
                markerType="Garden";
                markerColor=45;
            }
        }

        MarkerOptions markerOps = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                .position(
                        new LatLng(placementPoint.latitude, placementPoint.longitude)).title(markerType);
        mMap.addMarker(markerOps);
        postVolley();

        return true;
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
        mMap.setOnMapLongClickListener(this);
        //call shootVolley to load markers from DB
        shootVolley();
    }
}
