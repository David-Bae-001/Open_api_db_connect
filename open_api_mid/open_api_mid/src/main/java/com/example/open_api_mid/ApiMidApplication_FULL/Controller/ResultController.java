package com.example.open_api_mid.ApiMidApplication_FULL.Controller;

import com.example.open_api_mid.ApiMidApplication_FULL.Entity.WeatherInfo;
import com.example.open_api_mid.ApiMidApplication_FULL.Repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultController {
    @Autowired
    WeatherInfoRepository infoRepository;

    @RequestMapping("/result")
    public List<WeatherInfo> result(Model model, @RequestParam(required = true) String loc){
        List<WeatherInfo> infoList= infoRepository.findByName(loc);
        if(infoList.isEmpty()==true) System.out.println("empty list");

        return infoList;
    }
}
