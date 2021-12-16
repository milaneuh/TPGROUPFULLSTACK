package com.example.bikesstations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
public class WebServices {

    @Autowired
    private StationsDAO stationDao;
    @Autowired
    private UserDAO userDAO;

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

    @GetMapping("/getPassword")
    public void getPassword() {
        System.out.println("/getPassword");
        OkHttpUtils.hashPass("password");
    }

    @PostMapping("/login")
    public String login(@RequestBody UserBean user) throws Exception {
        List<UserBean> userBDD = userDAO.findAll();
        String userAPI="";
        Random rand = new Random();
        for (int i=0; i<userBDD.size();i++){
            if (OkHttpUtils.verifUser(user.getUser_login(), userBDD.get(i).getUser_login(), user.getUser_pwd(), userBDD.get(i).getUser_pwd())){
                return userBDD.get(i).getUser_api_key();
            }
        }
        // boucle pour générer une clé API
        for(int i = 0 ; i < 20 ; i++){
            char c = (char)(rand.nextInt(26) + 97);
            userAPI += c;
        }
        //sauvegarde du nouveau user en BDD
        user.setUser_api_key(userAPI);
        String pwd = user.getUser_pwd();
        user.setUser_pwd(OkHttpUtils.hashPass(pwd));
        userDAO.save(user);
        return userAPI;
    }

    //http://localhost:8080/getBikesInToulouseWithKey?user_api_key=toto
    @GetMapping("/getBikesInToulouseWithKey")
    public List<StationsBean> getBikesInToulouseWithKey(String user_api_key) throws Exception{
        List<UserBean> userBDD = userDAO.findAll();
        for (int i=0; i<userBDD.size();i++){
            if (user_api_key.equals(userBDD.get(i).getUser_api_key())){
                return stationDao.findAll();
            }
        }
        return null;
    }
}


