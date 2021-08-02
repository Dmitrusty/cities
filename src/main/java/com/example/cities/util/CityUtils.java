package com.example.cities.util;

public class CityUtils {
    public static String formatOut(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
