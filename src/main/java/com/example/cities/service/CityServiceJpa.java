package com.example.cities.service;

import com.example.cities.model.City;
import com.example.cities.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CityServiceJpa {
    private final CityRepository cityRepository;
    private static final Random random = new Random();

    public CityServiceJpa(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City searchCityByName(String cityName) {
        City city = cityRepository.findByName(cityName);
        markCity(city);
        return city;
    }

    public void markCity(City city){
        city.setPlayed(true);
        cityRepository.save(city);
    }

    private void unmarkAllCities(){
        List<City> cities = cityRepository.findAll();
        for(City city : cities) {
            city.setPlayed(false);
        }
        cityRepository.saveAll(cities);
    }

    public City getFirstCity(){
        unmarkAllCities();

        List<City> allCities = cityRepository.findAll();

        City firstCity = null;
        if (!allCities.isEmpty()) {
            firstCity = allCities.get(random.nextInt(allCities.size()));
            markCity(firstCity);
        }

        return firstCity;
    }

    public City getNextCity(char firstLetter){
        List<City> suitableCities = cityRepository.findByNameStartsWithAndPlayedFalse(String.valueOf(firstLetter));

        City nextCity = null;
        if (!suitableCities.isEmpty()) {
            nextCity = suitableCities.get(random.nextInt(suitableCities.size()));
            markCity(nextCity);
        }

        return nextCity;
    }
}
