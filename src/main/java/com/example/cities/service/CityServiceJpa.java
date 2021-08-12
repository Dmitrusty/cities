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

    /**
     * Looking for a city by its name
     *
     * @param cityName - name of the city to find
     * @return object City from database, or null if absent
     */
    public City searchCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    /**
     * Marks the given city played
     *
     * @param city - the given city
     */
    public void markCity(City city){
        if (city != null) {
            city.setPlayed(true);
            cityRepository.save(city);
        }
    }

    /**
     * Marks all cities in database unplayed
     */
    private void unmarkAllCities(){
        List<City> cities = cityRepository.findAll();
        for(City city : cities) {
            city.setPlayed(false);
        }
        cityRepository.saveAll(cities);
    }

    /**
     * This method is called at the beginning of the game.
     * The method makes all cities unplayed and returns a random city
     *
     * @return random object City from database
     */
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

    /**
     * Looking for a city that has not yet been played, the name of which begins with a given letter
     *
     * @param firstLetter - the first letter in the name of a city
     * @return object City with the required first letter, or null if absent
     */
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
