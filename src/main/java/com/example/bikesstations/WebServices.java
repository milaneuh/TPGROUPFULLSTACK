package com.example.bikesstations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class WebServices {

    @Autowired
    private StationsDAO stationDao;

    @GetMapping("/HelloWord")
    public String HelloWord() {
        return "Hello Word";
    }

    @GetMapping("/GetStations")
    public String GetStations() {
        String json="";
        try {
            json = OkHttpUtils.getStation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("/GetStationsBDD")
    public Long GetStationsBDD() {
        Long resultat;
        resultat = stationDao.count();
        return resultat;
    }

}
    /*@GetMapping("/getBikesInToulouse")
    public List<StationsBean> getBikesInToulouse() throws Exception {
        if (!stationDao.findAll().isEmpty() || stationDao.findAll()!=null){
            return stationDao.findAll();
        }
        else {
            throw new Exception("Liste vide");
        }
    }

    public static void refreshData(){
        try {
            ArrayList<StationsBean> listeStation = gson.fromJson(OkHttpUtils.sendGetOkHttpRequest(url),new TypeToken<ArrayList<StationsBean>>() {
            }.getType());

            if (!listeStation.isEmpty() || listeStation!=null){
                stationDao.deleteAll();
                stationDao.saveAll(listeStation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

