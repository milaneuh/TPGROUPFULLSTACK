package com.tpgroup.vitemontransport.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.tpgroup.vitemontransport.R;
import com.tpgroup.vitemontransport.model.StationsBean;
import com.tpgroup.vitemontransport.model.SubwaysBean;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    private View v;

    //private MarkerStationInfoMenuBinding binding;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {


        //on adapte la popup selon le type de marker
        try {
            Object result = marker.getTag();
            //si le marker represente une station de velo
            if (result instanceof StationsBean) {
                //on charge les composants graphiques
                v = ((Activity) context).getLayoutInflater().inflate(R.layout.marker_station_info_menu, null);
                TextView title = (TextView) v.findViewById(R.id.tv_title);
                TextView bikeStands = (TextView) v.findViewById(R.id.tv_ligne);
                TextView address = (TextView) v.findViewById(R.id.tv_adress);
                TextView status = (TextView) v.findViewById(R.id.tv_type);

                //on renseigne les textView avec les donnees de la station de velo
                StationsBean stationsBean = (StationsBean) marker.getTag();
                System.out.println(stationsBean.getName());
                title.setText(stationsBean.getName());
                bikeStands.setText(stationsBean.getAvailable_bikes() + "/" + stationsBean.getAvailable_bike_stands());
                address.setText(stationsBean.getAddress());

                if (stationsBean.isStatus()) {
                    status.setText("OPEN");
                } else {
                    status.setText("CLOSED");
                    status.setTextColor(Color.parseColor("#FF0000"));
                }
                return v;
            } else { //si le marker represente une station de metro/tramway
                //on charge les composants graphiques
                v = ((Activity) context).getLayoutInflater().inflate(R.layout.marker_subway_info_menu, null);
                TextView title = (TextView) v.findViewById(R.id.tv_title);
                TextView type = (TextView) v.findViewById(R.id.tv_type);
                TextView ligne = (TextView) v.findViewById(R.id.tv_ligne);

                //on renseigne les textView avec les donnees de la station de metro/tramway
                SubwaysBean subwaysBean = (SubwaysBean) marker.getTag();
                System.out.println(subwaysBean.getNom());
                title.setText(subwaysBean.getNom());
                type.setText(subwaysBean.getType());
                ligne.setText(subwaysBean.getLigne());
                return v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;

        //View v = ((Activity) context).getLayoutInflater().inflate(R.layout.marker_info_menu, null);
        //binding = MarkerInfoMenuBinding.bind(v);

        //try {
        //StationsBean stationsBean = (StationsBean) marker.getTag();
        //System.out.println(stationsBean.getName());
        //String[] splitedName = stationsBean.getName().split("  -  ");
        //binding.tvTitle.setText(splitedName[1]);
        //binding.tvBikeStand.setText(stationsBean.getAvailable_bikes() + "/" + stationsBean.getAvailable_bike_stands());
        //binding.tvAdress.setText(stationsBean.getAddress());

        //if (stationsBean.isStatus()) {
        //    binding.tvStatus.setText("OPEN");
        //} else {
        //    binding.tvStatus.setText("CLOSED");
        //    binding.tvStatus.setTextColor(Color.parseColor("#FF0000"));
        //}
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        //return v;
    }
}
