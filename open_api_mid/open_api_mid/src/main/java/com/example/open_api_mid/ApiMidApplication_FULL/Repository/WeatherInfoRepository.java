package com.example.open_api_mid.ApiMidApplication_FULL.Repository;

import com.example.open_api_mid.ApiMidApplication_FULL.Entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo,String> {
    @Query(value = "SELECT * FROM weather_info u WHERE u.wf_Sv != :loc", nativeQuery = true)
    List<WeatherInfo> findByName(@Param("loc") String loc);
}
