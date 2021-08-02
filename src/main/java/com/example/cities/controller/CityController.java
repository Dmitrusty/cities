package com.example.cities.controller;

import com.example.cities.model.City;
import com.example.cities.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.cities.util.CityUtils.formatOut;

@Controller
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "*")
    public String defaultRoute() {
        return "redirect:/begin";
    }

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
    public String next(Model model, @RequestParam String userCityName, @RequestParam String serverCityNamePrevious) {
        userCityName = userCityName.toLowerCase();
        City userCity = cityService.searchCityByName(userCityName);

        if(userCity == null){
            model.addAttribute("error", "Город " + formatOut(userCityName) + " уже сыгран или неизвестен. Пожалуйста, назовите другой город.");
            model.addAttribute("serverCityName", serverCityNamePrevious);
        } else {
            char serverCityLastLetter = serverCityNamePrevious.charAt(serverCityNamePrevious.length() - 1);

            if (!cityService.isValidCity(userCityName, serverCityLastLetter)) {
                model.addAttribute("error", "Название города должно начинаться на букву "
                        + serverCityLastLetter + ". Попробуйте снова.");
                model.addAttribute("serverCityName", formatOut(serverCityNamePrevious));
            } else {
                cityService.markCity(userCity);

                char lastLetterUser = userCityName.toLowerCase().charAt(userCityName.length() - 1);
                City serverCity = cityService.getNextCity(lastLetterUser);
                if (serverCity == null){
                    model.addAttribute("message", "Не могу подобрать город. Поздравляю с победой!");
                    return "end";
                }

                model.addAttribute("serverCityName", formatOut(serverCity.getName()));
            }
        }
        model.addAttribute("userCityNamePrevious", formatOut(userCityName));
        return "next";
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
}
