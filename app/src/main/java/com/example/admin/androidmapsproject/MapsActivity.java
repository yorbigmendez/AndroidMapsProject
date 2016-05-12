package com.example.admin.androidmapsproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnFinal = (Button)findViewById(R.id.btnFinal);

        btnInicio.setOnClickListener(this);
        btnFinal.setOnClickListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        getLocation();
        locationListener.onLocationChanged(location);
        mapFragment.getMapAsync(this);
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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
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
                    Toast t=Toast.makeText(getApplicationContext(),"No se puedo obtener la localización en este momento",
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private void trazarCamino() {
        if(machete == 0){
            markerActual.remove();
            markerActual =  mMap.addMarker(setMarkerIniFin(marca, "Inicio","Carrera Los pegados"));
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
}
