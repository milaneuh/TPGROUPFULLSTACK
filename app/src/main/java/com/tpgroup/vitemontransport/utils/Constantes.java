package com.tpgroup.vitemontransport.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class Constantes {

    /*************************     ATTRIBUTS     ****************************/

    public final static Gson myGson = new Gson();
    public final static String URL_WS_STATIONS = "http://176.191.236.103/getBikesInToulouseWithKey?user_api_key=Azerty77";
    public final static String URL_WS_SUBWAY = "http://82.65.175.159:8384/getAllStationsWithConnection?idSession=C8805F82DF1C9D329216C9E9BD0221E7";
    public final static LatLng TOULOUSE_POS = new LatLng(43.600309, 1.441424);
    //public final static StationsBean EXEMPLE_STATIONS_BEAN = new StationsBean(0, "Paul Sabatier", "rue de Pech", 8, 7, 43.56096,1.4629353, true);
    //public final static SubwaysBean EXEMPLE_SUBWAYS_BEAN = new SubwaysBean("SAOUZELONG", "B", 1.45893145722, 43.579792375, "métro");
}
