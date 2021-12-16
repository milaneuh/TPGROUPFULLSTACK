package com.tpgroup.vitemontransport.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

import com.google.gson.reflect.TypeToken;
import com.tpgroup.vitemontransport.model.StationsBean;
import com.tpgroup.vitemontransport.model.SubwaysBean;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    /*************************     REQUETES EN GET     ****************************/

    public static String sendCetOkHttpRequest(String url) throws Exception {
        //Toujours effectuer un contrôle d’url en l’affichant en console
        System.out.println("Url : " + url);
        OkHttpClient client = new OkHttpClient();
        //Création de la requête
        Request request = new Request.Builder().url(url).build();
        //Exécution de la requête
        Response response = client.newCall(request).execute();
        //Analyse du code retour
        if (response.code() < 200 || response.code() > 299) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            //Résultat de la requête
            return response.body().string();
        }
    }

    /*************************     LOCALISATION     ****************************/

    public static Location getLastKnownLocation(Context context) {
        //Contrôle de la permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Location bestLocation = null;
        //on teste chaque provider(réseau, GPS...) et on garde la localisation avec la meilleure précision
        for (String provider : lm.getProviders(true)) {
            Location l = lm.getLastKnownLocation(provider);
            if (l != null && (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    /*************************     WEBSERVICES     ****************************/

    public static ArrayList<StationsBean> loadBikes() throws Exception {
        //on effectue la requete de parsing
        String myJson = Utils.sendCetOkHttpRequest(Constantes.URL_WS_STATIONS);
        //on recupere un json de type ArrayList<StationsBean>
        ArrayList<StationsBean> stationsBean = Constantes.myGson.fromJson(myJson, new TypeToken<ArrayList<StationsBean>>() {
        }.getType());
        //controle avant de retourner la liste
        if (stationsBean.isEmpty()) {
            throw new Exception("Aucune donnée");
        }

        return stationsBean;
    }

    public static ArrayList<SubwaysBean> loadSubway() throws Exception {
        //on effectue la requete de parsing
        String myJson = Utils.sendCetOkHttpRequest(Constantes.URL_WS_SUBWAY);
        //on recupere un json de type ArrayList<StationsBean>
        ArrayList<SubwaysBean> subwaysBean = Constantes.myGson.fromJson(myJson, new TypeToken<ArrayList<SubwaysBean>>() {
        }.getType());
        //controle avant de retourner la liste
        if (subwaysBean.isEmpty()) {
            throw new Exception("Aucune donnée");
        }

        return subwaysBean;
    }
}
