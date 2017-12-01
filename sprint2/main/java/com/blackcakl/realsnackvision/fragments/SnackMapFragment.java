package com.blackcakl.realsnackvision.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blackcakl.realsnackvision.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*public class SnackMapFragment extends Fragment implements OnMapReadyCallback
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_gmaps, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(29.702182, -98.124561)).title("marker"));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

}*/

public class SnackMapFragment extends Fragment implements GoogleMap.OnMapLongClickListener,OnMapReadyCallback {

    private GoogleMap mMap;
    //type of marker being placed
    private  String type="default";
    private float markerColor;
    private LatLng placementPoint;
    RelativeLayout r1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_gmaps, container, false);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v, menuInfo);


        menu.setHeaderTitle("Select one:");
        menu.setHeaderIcon(android.R.drawable.btn_star_big_off);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch(id){
            case R.id.item1:{
                Toast.makeText(getActivity(),"Apple tree placed",Toast.LENGTH_LONG).show();
                type="Apple Tree";
                markerColor= 120;
            }
            break;
            case R.id.item2:{
                Toast.makeText(getActivity(),"Blackberry bush placed",Toast.LENGTH_LONG).show();
                type="Blackberry Bush";
                markerColor=300;
            }
            break;
            case R.id.item3:{
                Toast.makeText(getActivity(),"Garden placed",Toast.LENGTH_LONG).show();
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


    @Override
    public void onMapLongClick(LatLng point) {
        getActivity().openContextMenu(getView().findViewById(R.id.activity_main));
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
        LatLng sydney = new LatLng(-34, 151);
        LatLng arcata = new LatLng(40.8665, -124.0828);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(arcata).title("Marker in Arcata"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(arcata));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setMinZoomPreference(12);
    }
}