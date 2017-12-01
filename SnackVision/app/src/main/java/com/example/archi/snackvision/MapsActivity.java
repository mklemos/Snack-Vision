/*
MapsActivity.java is the home of our main, as well as the listeners, onclick event and context menu calls.
The capabilities are: open app to Arcata. Move around the map environment. Longclick anywhere to open a context menu.
    Context menu gives you three choices: Apple Tree, Blackberry Bush, Garden. Choosing one will place appropriate marker
    where the context menu was opened.

Authors: Sam Alston, Billy Crossman
*/
package com.example.archi.snackvision;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapLongClickListener,OnMapReadyCallback  {

    private GoogleMap mMap;
    private  String type="default";
    private float markerColor;
    private LatLng placementPoint;
    RelativeLayout r1;

    //link views and attach context menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        r1=(RelativeLayout)findViewById(R.id.activity_main);
        registerForContextMenu(r1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v, menuInfo);
        menu.setHeaderTitle("Select one:");
        menu.setHeaderIcon(android.R.drawable.btn_star_big_off);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.context_menu,menu);
    }

    //Choose menu item and place appropriately colored marker
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch(id){
            case R.id.item1:{
                Toast.makeText(MapsActivity.this,"Apple tree placed",Toast.LENGTH_LONG).show();
                type="Apple Tree";
                markerColor= 120;
            }
            break;
            case R.id.item2:{
                Toast.makeText(MapsActivity.this,"Blackberry bush placed",Toast.LENGTH_LONG).show();
                type="Blackberry Bush";
                markerColor=300;
            }
            break;
            case R.id.item3:{
                Toast.makeText(MapsActivity.this,"Garden placed",Toast.LENGTH_LONG).show();
                type="Garden";
                markerColor=45;
            }
        }
        MarkerOptions marker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                .position(
                        new LatLng(placementPoint.latitude, placementPoint.longitude)).title(type);
        mMap.addMarker(marker);
        return true;
    }

    //create context menu for dropping marker
    @Override
    public void onMapLongClick(LatLng point) {
        openContextMenu(findViewById(R.id.activity_main));
        placementPoint=point;
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
        // Add a marker in Sydney and move the camera
        LatLng arcata = new LatLng(40.8665, -124.0828);
        mMap.addMarker(new MarkerOptions().position(arcata).title("Marker in Arcata"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(arcata));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setMinZoomPreference(12);
    }
}