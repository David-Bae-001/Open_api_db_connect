package com.example.open_api_ex02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class Shortterm {
    @GetMapping("/shortapi")
    public String CallApiHttp(){
        StringBuffer result = new StringBuffer();
        try {

            // 실행 날짜 기준으로 0600 기준으로 세팅
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);

            calendar.set(Calendar.HOUR_OF_DAY, 5);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String base_date = sdf.format(calendar.getTime());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
            String base_time = sdf2.format(calendar.getTime());

            String urlstr = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?"  //  call back url
                    + "ServiceKey=KRs%2BqmKuKoVI076rw1pN20%2BubKrCOuL7iVNyQUwXKjeeS0MfGlR29aAjFsEMBgmgGQPNqGA9UFhRwuuOMrsmXA%3D%3D"
                    //  서비스키(encoding)
                    + "&pageNo=1"  //  페이지 번호
                    + "&numOfRows=10"  //  한 페이지 결과 수
                    + "&dataType=xml"  //  요청자료 형식(XML/JSON)
                    + "&base_date=" + base_date  //  발표일자
                    + "&base_time=" + base_time  //  발표시간(2시, 5시, 8시, 11시, 14시, 17시, 20시, 23시), 1일 8회, 10분 후 api 제공
                    + "&nx=55"  //  예보지점의 X 값
                    + "&ny=127";  //  예보지점의 Y 값
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));

            String returnline;
            result.append("<xmp>");  //  <xmp></xmp> : result의 xml 형태 그대로 브라우저에서 출력
            while((returnline = br.readLine()) != null ){
                result.append(returnline + "\n");
            }

            urlconnection.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
        return result+"</xmp>";  //  <xmp></xmp> : result의 xml 형태 그대로 브라우저에서 출력
    }
}
