package com.example.bikesstations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
        @Autowired
        private StationsDAO stationDao;

        @Scheduled(fixedRate = 600000)
        public void refreshData() throws Exception {
            System.out.println("refreshData()");
            List<BikesStationsBean> listeStation = OkHttpUtils.getStation();
            ArrayList<StationsBean> listeStationBDD = new ArrayList<>() ;

            for (var i=0;i<listeStation.size();i++){
                String nameBDD = listeStation.get(i).getName();
                String addressBDD = listeStation.get(i).getAddress();
                int availableBikesStandsBDD = listeStation.get(i).getAvailable_bike_stands();
                int availableBikesBDD = listeStation.get(i).getAvailable_bikes();
                boolean statusBDD=false;
                if (listeStation.get(i).getStatus().equals("OPEN")){
                    statusBDD = true;
                }
                Double latBDD = listeStation.get(i).getPosition().getLat();
                Double lngBDD = listeStation.get(i).getPosition().getLng();
                StationsBean station = new StationsBean(nameBDD,addressBDD,availableBikesStandsBDD, availableBikesBDD, statusBDD,latBDD,lngBDD );
                listeStationBDD.add(station);
            }

            if (!listeStationBDD.isEmpty()){
                stationDao.deleteAll();
                stationDao.saveAll(listeStationBDD);
            }
            else {
                throw new Exception("Liste vide BDD non mise à jour");
            }
        }
}
