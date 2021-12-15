package com.example.bikesstations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
public class WebServices {

    @Autowired
    private StationsDAO stationDao;

    @GetMapping("/HelloWorld")
    public String HelloWord() {
        System.out.println("/HelloWorld");
        return "Hello World";
    }

    @GetMapping("/GetStations")
    public List<BikesStationsBean> GetStations() {
        List<BikesStationsBean> json = null;
        System.out.println("/GetStations");
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
        System.out.println("/GetStationsBDD");
        resultat = stationDao.count();
        return resultat;
    }

    @GetMapping("/getBikesInToulouse")
    public List<StationsBean> getBikesInToulouse() {
        System.out.println("/getBikesInToulouse");
        return stationDao.findAll();
    }
}


