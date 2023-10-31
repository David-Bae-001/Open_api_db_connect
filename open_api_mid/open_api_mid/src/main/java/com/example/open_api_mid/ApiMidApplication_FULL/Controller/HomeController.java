package com.example.open_api_mid.ApiMidApplication_FULL.Controller;


import com.example.open_api_mid.ApiMidApplication_FULL.Entity.WeatherInfo;
import com.example.open_api_mid.ApiMidApplication_FULL.Repository.WeatherInfoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {

    // HashMap을 사용하여 지역과 해당 숫자 매핑
    private static final Map<String, String> locMap = new HashMap<>();

    static {
        locMap.put("강원도", "105");
        locMap.put("전국", "108");
        locMap.put("서울", "109");
        locMap.put("인천", "109");
        locMap.put("경기도", "109");
        locMap.put("충청북도", "131");
        locMap.put("대전", "133");
        locMap.put("세종", "133");
        locMap.put("충청남도", "133");
        locMap.put("전라북도", "146");
        locMap.put("전라남도", "156");
        locMap.put("광주", "156");
        locMap.put("대구", "143");
        locMap.put("경상북도", "143");
        locMap.put("부산", "159");
        locMap.put("울산", "159");
        locMap.put("경상남도", "159");
        locMap.put("제주도", "184");
    }

    @Autowired
    private WeatherInfoRepository infoRepository;

    @GetMapping("/api")
    public String index(){
        return "index";
    }

    @PostMapping("/api")
    public String load_save(@RequestParam("loc") String loc, Model model){
        String result = "";
        loc = locMap.get(loc);

        try {

            // 실행 날짜 기준으로 0600 기준으로 세팅
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);

            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String tmFc = sdf.format(calendar.getTime());

            URL url = new URL("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst?"
                    + "ServiceKey=KRs%2BqmKuKoVI076rw1pN20%2BubKrCOuL7iVNyQUwXKjeeS0MfGlR29aAjFsEMBgmgGQPNqGA9UFhRwuuOMrsmXA%3D%3D"
                    + "&pageNo=1"  //  페이지 번호
                    + "&numOfRows=10"  //  한 페이지 결과 수
                    + "&dataType=json"  //  요청자료 형식(XML/JSON)
                    + "&stnId=" + loc  //  지점번호(108 전국, 109 서울)
                    + "&tmFc=" + tmFc  //  발표시각(YYYYMMDD0600, 또는 1800), 최근 24시간 자료만 제공
                    );
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONObject jsonObject = new JSONObject(result);
            JSONObject response = (JSONObject)jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArr = (JSONArray) items.get("item");

            for(int i=0;i<itemArr.length();i++){
                JSONObject tmp = itemArr.getJSONObject(i);
                WeatherInfo infoObj=new WeatherInfo(
                        i,
                        tmp.getString("wfSv")
                );
                System.out.println(infoObj);
                infoRepository.deleteAll();
                infoRepository.save(infoObj);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/result?loc=" + loc;
    }
}