package com.example.open_api_mid.ApiMidApplication_FULL.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class WeatherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 2000)
    private String wfSv;

    public WeatherInfo() {
    }

    public WeatherInfo(Integer id, String wfSv) {
        this.id = id;
        this.wfSv = wfSv;
    }
}
