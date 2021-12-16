package com.tpgroup.vitemontransport.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tpgroup.vitemontransport.R;
import com.tpgroup.vitemontransport.databinding.ActivityMainBinding;
import com.tpgroup.vitemontransport.model.StationsBean;
import com.tpgroup.vitemontransport.model.SubwaysBean;
import com.tpgroup.vitemontransport.utils.Constantes;
import com.tpgroup.vitemontransport.utils.Utils;
import com.tpgroup.vitemontransport.view.InfoWindowAdapter;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    /*************************     ATTRIBUTS     ****************************/
    //composants graphiques
    private ActivityMainBinding binding;
    //outils
    private GoogleMap mMap;
    //donnees
    private ArrayList<StationsBean> bikesList = new ArrayList<>();
    private ArrayList<SubwaysBean> subwaysList = new ArrayList<>();

    /*************************     METHODES     ****************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //si je n'ai pas la permission je la demande
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        //abonnement aux check des chechBox
        binding.checkBox.setOnCheckedChangeListener(this);
        binding.checkBox2.setOnCheckedChangeListener(this);
        binding.checkBox3.setOnCheckedChangeListener(this);

        //Thread secondaire qui lance les requetes
        new Thread() {
            @Override
            public void run() {
                try {
                    //on recupere la liste des stations de velos de Toulouse
                    bikesList = Utils.loadBikes();
                    //recupere la liste des stations de metros/tram de Toulouse
                    subwaysList = Utils.loadSubway();
                    if (bikesList.size() == 0 || subwaysList.size() == 0) {
                        showErrorMsg("Echec de récupération des données du serveur");
                        Log.w("debug", "Liste(s) récupérée(s) vide(s)");
                    }
                    Log.w("debug", "Succès de la communication avec le serveur");
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorMsg("Echec de communication avec le serveur");
                    Log.w("debug", "Serveur hors service");
                }
            }
        }.start();
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

        InfoWindowAdapter adapter = new InfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(adapter);

        refreshScreen();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.w("debug", "Demande de permission");
        refreshScreen();
    }

    /*************************     METHODES DEPORTEES     ****************************/

    private void refreshScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //est ce que j ai une carte
                if (mMap == null) {
                    return;
                }
                //est ce que j ai une localisataion
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    Log.w("debug", "Localisation activée");
                }
                //effacer les markers existants
                mMap.clear();
                //zoom sur Toulouse
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Constantes.TOULOUSE_POS, 12));
                //gerer les checkbox
                setListenerCheckBox();
            }
        });
    }

    private void setListenerCheckBox() {
        //si metro and tram est coche
        if (binding.checkBox.isChecked()) {
            showSubway();
            Log.w("debug", "Appel showSubway");
        }

        //si velo est coche
        if (binding.checkBox2.isChecked()) {
            showBikes();
            Log.w("debug", "Appel showBike");
        }

        try {
            //on recupere la localisation
            Location location = Utils.getLastKnownLocation(this);
            //si la localisation n est pas detectee on grise la case autour de soi
            if (location == null) {
                binding.checkBox3.setEnabled(false);
            }
            //si autour de soi est coche
            if (binding.checkBox3.isChecked()) {
                //on recupere la latitude et longitude de notre localisation
                double lng = location.getLongitude();
                double lat = location.getLatitude();
                LatLng userPos = new LatLng(lat, lng);
                //on zoome sur la position de l utilisateur
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPos, 16));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMsg("Localisation du téléphone non détectée");
            Log.w("debug", "Echec de détection de la localisation");
        }
    }

    //affiche les markers de stations metros/tram sur la carte
    public void showSubway() {
        binding.tvError.setVisibility(View.GONE);
        showProgressBar(true);

        //on parcourt la liste de stations velos et on met à jour les markers sur la carte de Toulouse
        for (int i = 0; i < subwaysList.size(); i++) {
            LatLng markerPos = new LatLng(subwaysList.get(i).getGeoPointLat(), subwaysList.get(i).getGeoPointLong());
            Marker marker = mMap.addMarker(new MarkerOptions().position(markerPos).title(subwaysList.get(i).getNom()).icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_subway)));
            marker.setTag(subwaysList.get(i));
        }

        showProgressBar(false);
    }

    //on affiche les markers de stations velos sur la carte
    public void showBikes() {
        binding.tvError.setVisibility(View.GONE);
        showProgressBar(true);

        //on parcourt la liste de stations velos et on met à jour les markers sur la carte de Toulouse
        for (int i = 0; i < bikesList.size(); i++) {
            LatLng markerPos = new LatLng(bikesList.get(i).getLat(), bikesList.get(i).getLng());
            Marker marker = mMap.addMarker(new MarkerOptions().position(markerPos).title(bikesList.get(i).getName()).icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_station)));
            marker.setTag(bikesList.get(i));
        }

        showProgressBar(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        refreshScreen();
    }

    private void showProgressBar(boolean visible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visible) {
                    binding.pb.setVisibility(View.VISIBLE);
                } else {
                    binding.pb.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void showErrorMsg(String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.VISIBLE);
                //affiche dans l'appli le msg d'erreur
                binding.tvError.setText(errorMsg);
            }
        });
    }

    //recupere et interprete notre image vectorielle puis la transforme en un drawable pour en convertir en Bitmap
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}