package com.example.cities.util;

public class CityUtils {
    public static String formatOut(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }


    /**
     * Checks that the city name starts with the given letter
     *
     * @param cityName - the city name to check
     * @param givenLetter  - the given letter
     * @return true if the city name starts with the given letter
     */
    public static boolean isValidCity(String cityName, char givenLetter) {
        return cityName != null && cityName.charAt(0) == givenLetter;
    }
}
