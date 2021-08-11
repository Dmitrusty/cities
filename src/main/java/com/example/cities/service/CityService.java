package com.example.cities.service;

import com.example.cities.model.City;
import com.example.cities.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// @Service
public class CityService {
    private static final Random random = new Random();
    private static final String SELECT_ALL_CITIES = "SELECT * FROM cities ORDER BY id";
    private static final String SELECT_NEXT_CITIES = "SELECT * FROM cities WHERE ((`isPlayed` = false ) and (city like ?))";
    private static final String SELECT_CITY_BY_NAME = "SELECT * FROM cities WHERE (city like ?)";
    private static final String UPDATE_CITY = "UPDATE `cities` SET `isPlayed` = true WHERE (`id` = ?)";
    private static final String UNMARK_ALL_CITIES = "UPDATE `cities` SET `isPlayed` = false ";
    private final DatabaseConnection databaseConnection;

    public CityService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Marks the given city played
     *
     * @param city - the given city
     */
    public void markCity(City city) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY)) {

            preparedStatement.setInt(1, city.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Marks all cities in database unplayed
     */
    private void unmarkAllCities() {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UNMARK_ALL_CITIES)) {

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method is called at the beginning of the game.
     * The method makes all cities unplayed and returns a random city
     *
     * @return random object City from database
     */
    public City getFirstCity() {
        City firstCity = null;

        unmarkAllCities();

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CITIES)) {

            List<City> allCities = new ArrayList<>();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("city"));
                city.setPlayed(resultSet.getBoolean("isPlayed"));
                allCities.add(city);
            }

            if (!allCities.isEmpty()) {
                firstCity = allCities.get(random.nextInt(allCities.size()));
                markCity(firstCity);
                firstCity.setPlayed(true);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return firstCity;
    }

    /**
     * Looking for a city that has not yet been played, the name of which begins with a given letter
     *
     * @param firstLetter - the first letter in the name of a city
     * @return object City with the required first letter, or null if absent
     */
    public City getNextCity(char firstLetter) {
        List<City> suitableCities = new ArrayList<>();
        City nextCity = null;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEXT_CITIES)) {

            preparedStatement.setString(1, firstLetter + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("city"));
                city.setPlayed(resultSet.getBoolean("isPlayed"));
                suitableCities.add(city);
            }

            if (!suitableCities.isEmpty()) {
                nextCity = suitableCities.get(random.nextInt(suitableCities.size()));
                markCity(nextCity);
            }

            resultSet.close();
            return nextCity;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Looking for a city by its name
     *
     * @param cityName - name of the city to find
     * @return object City from database, or null if absent
     */
    public City searchCityByName(String cityName) {
        City userCity = null;

        if(cityName != null) {
            try (Connection connection = databaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CITY_BY_NAME)) {

                preparedStatement.setString(1, cityName.toLowerCase());
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    userCity = new City();
                    userCity.setId(resultSet.getInt("id"));
                    userCity.setName(resultSet.getString("city"));
                    userCity.setPlayed(resultSet.getBoolean("isPlayed"));
                    markCity(userCity);
                }

                resultSet.close();
                return userCity;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;

    }

    /**
     * Checks that the city name starts with the given letter
     *
     * @param cityName - the city name to check
     * @param givenLetter  - the given letter
     * @return true if the city name starts with the given letter
     */
    public boolean isValidCity(String cityName, char givenLetter) {
        return cityName != null && cityName.charAt(0) == givenLetter;
    }

}