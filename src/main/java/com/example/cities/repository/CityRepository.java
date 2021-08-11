package com.example.cities.repository;

import com.example.cities.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);

    List<City> findByNameStartsWithAndPlayedFalse(String name);
}
