package com.example.bikesstations;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationsDAO extends JpaRepository<StationsBean, Long>{


}
