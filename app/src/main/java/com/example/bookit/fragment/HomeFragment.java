package com.example.bookit.fragment;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.bookit.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location lostLocation;
    private String CustomerId;
    private LocationRequest locationRequest;
    private LatLng customerPuckupLocation;
    private DatabaseReference referenceDriver;
     private int redius=1;
     private String driverFoundId;
     private Boolean driverFound=false;
     private Marker driverMarker;

    //    private ViewPager viewPager;
    private String  TAG="error";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        CustomerId = FirebaseAuth.getInstance().getUid();

//        DatabaseReference   reference = FirebaseDatabase.getInstance().getReference().child("userRequest");
//        GeoFire geoFire= new GeoFire(reference);
//
//        geoFire.setLocation(CustomerId,new GeoLocation(lostLocation.getLatitude(), lostLocation.getLongitude()), new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error!=null)
//                {
//
//                    Log.e("error","Can't go Active");
//                }
//
//                Log.e("error","You are Active");
//            }
//        });
//        //get location in onclickLisnar
//        customerPuckupLocation= new LatLng(lostLocation.getLatitude(),lostLocation.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(customerPuckupLocation).title("pickup Costume for hire"));
//        getClosestDriverCab();



        return view;
    }

    private void getClosestDriverCab() {
        referenceDriver= FirebaseDatabase.getInstance().getReference();
        GeoFire geoFire= new GeoFire(referenceDriver.child("DriversAvailable"));
        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(customerPuckupLocation.latitude, customerPuckupLocation.longitude),redius);
       geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

                if (!driverFound)
                {
                    driverFound=true;
                    driverFoundId=key;

                    HashMap driverMap=new HashMap();
                    driverMap.put("customerRideID",CustomerId);
                    referenceDriver.child("OnlineUsers").child("Drivers")
                            .child(driverFoundId).updateChildren(driverMap);

                    //show in the driver Location
                    gettingDriverLocation();

                    //set Notify to customer about wating



                }


            }



            @Override
            public void onGeoQueryReady() {

                if (!driverFound){
                    redius++;
                    getClosestDriverCab();
                }

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }


            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void gettingDriverLocation() {

        referenceDriver.child("Driver Working").child(driverFoundId)
                .child("l").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    List<Object> driverLocationMap = ( List<Object>) dataSnapshot.getValue();
                    double loactionLat=0;
                    double loacationLng=0;
                    // set alert text to the customer;

                    if (driverLocationMap.get(0)!=null){
                         loactionLat=Double.parseDouble(driverLocationMap.get(0).toString());


                    }
                    if (driverLocationMap.get(1)!=null){
                        loacationLng=Double.parseDouble(driverLocationMap.get(0).toString());

                    }

                    LatLng driverLng= new LatLng(loactionLat,loacationLng);
                    if (driverMarker!=null)
                    {driverMarker.remove();}


                // to clc the distance
                    Location locationcustomer=new Location("");
                    locationcustomer.setLatitude(customerPuckupLocation.latitude);
                    locationcustomer.setLongitude(customerPuckupLocation.longitude);

                    Location locationDriver=new Location("");
                    locationDriver.setLatitude(driverLng.latitude);
                    locationDriver.setLongitude(driverLng.longitude);
                    // to clc the distance
                    float distance= locationcustomer.distanceTo(locationDriver);








                    driverMarker=mMap.addMarker(new MarkerOptions().position(driverLng).title("your driver is here"));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // The Wearable API is unavailable
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lostLocation=location;
        LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

//        SharedPreferences sp1=getContext().getSharedPreferences("userLogin", MODE_PRIVATE);

//        String unm=sp1.getString("Unm", null);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();


        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }
        // Position the map's camera

        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera




    }




    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient= new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
}
