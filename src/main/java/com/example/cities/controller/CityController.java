package com.example.cities.controller;

import com.example.cities.model.City;
import com.example.cities.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping(value = "/begin")
    public String begin(Model model) {
        City city = cityService.getFirstCity();

        if (city == null){
            model.addAttribute("message", "Извините, не могу найти подходящий город.");
            return "end";
        } else {
            model.addAttribute("firstCityName", formatOut(city.getName()));
            return "begin";
        }
    }

    @PostMapping(value = "/next")
    public String next(Model model, @RequestParam String userCityName, @RequestParam String pcCityNamePrevious) {
        String userCityNameLow = userCityName.toLowerCase();
        City userCity = cityService.searchCityByName(userCityNameLow);

        if(userCity == null){
            // city from user is already played or unknown
            model.addAttribute("error", "Город " + formatOut(userCityNameLow) + " уже сыгран или неизвестен. Пожалуйста, назовите другой город.");
            model.addAttribute("pcCityName", pcCityNamePrevious);
            model.addAttribute("userCityNamePrevious", formatOut(userCityNameLow));
            return "next";
        } else {
            char lastLetterPc = pcCityNamePrevious.charAt(pcCityNamePrevious.length() - 1);

            if (!cityService.isValidCity(userCityNameLow, lastLetterPc)) {
                // city from user starts from wrong letter
                model.addAttribute("error", "Название города должно начинаться на букву "
                        + lastLetterPc + ". Попробуйте снова.");
                model.addAttribute("pcCityName", formatOut(pcCityNamePrevious));
                model.addAttribute("userCityNamePrevious", formatOut(userCityNameLow));
                return "next";
            } else {
                // normal next step
                cityService.markCity(userCity);

                char lastLetterUser = userCityNameLow.toLowerCase().charAt(userCityNameLow.length() - 1);
                City pcCity = cityService.getNextCity(lastLetterUser);
                if (pcCity == null){
                    // it`s no unplayed cities for the required letter, user wins
                    model.addAttribute("message", "Не могу подобрать город. Поздравляю с победой!");
                    return "end";
                }

                model.addAttribute("pcCityName", formatOut(pcCity.getName()));
                model.addAttribute("userCityNamePrevious", formatOut(userCityNameLow));
                return "next";
            }
        }
    }

    @GetMapping(value = "/giveUp")
    public String giveUp(Model model){
        model.addAttribute("message", "Вы проиграли.");
        return "end";
    }

    @GetMapping(value = "/end")
    public String end(){
        return "end";
    }

    private String formatOut(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
