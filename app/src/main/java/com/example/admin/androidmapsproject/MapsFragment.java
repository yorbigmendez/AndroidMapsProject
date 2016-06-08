package com.example.admin.androidmapsproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback  {
    private static View view;
    private static Double latitude, longitude;
    private GoogleMap mMap;
    private int machete = 0;
    private static LatLng marca;
    private boolean marcadorInicial = true;
    private Marker markerActual;
    private Marker markerAnterior;
    private double latitud;
    private double longitud;
    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private AlertDialog alert = null;
    private Button btnInicio;
    private Button btnFinal;
    private Handler h = new Handler();
    private Runnable test;
    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        try {
            view = (View) inflater.inflate(R.layout.fragment_maps, container, false);
            // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        }catch(InflateException e){
            Log.e("Managing map fragment",e.getMessage());
        }
        btnInicio = (Button) view.findViewById(R.id.btnInicio);
        btnFinal = (Button)view.findViewById(R.id.btnFinal);
        btnInicio.setOnClickListener(this);
        btnFinal.setOnClickListener(this);
        //latitude = 26.78;
        //longitude = 72.56;
        getLocation();
        locationListener.onLocationChanged(location);
        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnInicio:
                final int delay = 30000; //milliseconds
                test=new Runnable() {
                    public void run() {
                        trazarCamino();
                        h.postDelayed(this, delay);
                    }
                };
                h.postDelayed(test, delay);

                break;
            case R.id.btnFinal:
                h.removeCallbacks(test);
                break;
            default:
                break;
        }
    }
    private void trazarCamino() {
        Log.d("Estoy en trazar camino", (String.valueOf(marcadorInicial)));
        if(machete == 0){
            markerActual.remove();
            markerActual =  mMap.addMarker(setMarkerIniFin(marca, "My Home","Home Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marca));
            machete++;
        }
        else{
            markerActual = mMap.addMarker(setMarkerRun(marca, "Mi posicón"));
        }
        if (marcadorInicial == false) {
            //Guardar en la base de datos aquí
            markerAnterior.remove();
            Log.d("marcador inicial", (String.valueOf(marcadorInicial)));
        }
        if (markerAnterior != null) {
            PolylineOptions options = new PolylineOptions()
                    .add(markerActual.getPosition())
                    .add(markerAnterior.getPosition());
            mMap.addPolyline(options);
            marcadorInicial = false;
        }
        markerAnterior = markerActual;
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

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        markerActual = mMap.addMarker(setMarkerIniFin(marca, "Inicio", "Carrera Los pegados"));
        mMap.moveCamera(CameraUpdateFactory.zoomBy(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marca));
        //mMap.setMyLocationEnabled(true);
    }
    private MarkerOptions setMarkerIniFin(LatLng position, String titulo, String info) {
        MarkerOptions myMaker2 = new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)  //Agregar info
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        return myMaker2;
    }

    private MarkerOptions setMarkerRun(LatLng position, String titulo) {
        // Agregamos marcadores para indicar sitios de interéses.
        MarkerOptions myMaker2 =new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.running_man));
        return myMaker2;
    }

    private void getLocation(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String mejorProveedor=locationManager.getBestProvider(criteria,true);
        location = locationManager.getLastKnownLocation(mejorProveedor);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null){
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    marca = new LatLng(latitud,longitud);
                }
                else{
                    Toast t=Toast.makeText(getActivity().getApplicationContext(),"No se puedo obtener la localización en este momento",
                            Toast.LENGTH_LONG);
                    t.show();
                    marca = new LatLng(0,0);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.location_map)).getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null){
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private void setUpMap() {
        // For showing a move to my loction button
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request the permision
        }
        //mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map

        markerActual = mMap.addMarker(setMarkerIniFin(marca,"Actual","Running"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marca, 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            SupportMapFragment myMapFragment=  ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.location_map)); // getMap is deprecated
            myMapFragment.getMapAsync(this);
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }
/*
    *** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ***
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            ScreenSlideActivity.fragmentManager.beginTransaction()
                        .remove(ScreenSlideActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
            mMap = null;
        }
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
