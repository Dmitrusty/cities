package com.example.cities.util;

import com.example.cities.model.City;

public class CityUtils {
    /**
     * Set cityName` first letter to uppercase and others to lowercase
     * @param cityName
     * @return new formatted String cityName
     */
    public static String formatOut(String cityName){
        return cityName.substring(0,1).toUpperCase() + cityName.substring(1).toLowerCase();
    }

    /**
     * Checks that the city name starts with the given letter
     * @param cityName
     * @param givenLetter
     * @return true if the city name starts with the given letter
     */
    public static boolean isWrongFirstLetter(String cityName, char givenLetter){
        return cityName.charAt(0) != givenLetter;
    }
}
