package com.example.bikesstations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class OkHttpUtils {

    public static List<BikesStationsBean> getStation() throws Exception {
        //Toujours effectuer un contrôle d’url en l’affichant en console
        String url = "https://api.jcdecaux.com/vls/v1/stations?contract=Toulouse&apiKey=2a1b07b2a523f81188fe34e348206a57ffa6f2a7";
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
            Gson g = new Gson();
            List<BikesStationsBean> maListeStations = g.fromJson(response.body().string(), new TypeToken<List<BikesStationsBean>>() {
            }.getType());
            //Résultat de la requête
            return maListeStations;
        }
    }


}
