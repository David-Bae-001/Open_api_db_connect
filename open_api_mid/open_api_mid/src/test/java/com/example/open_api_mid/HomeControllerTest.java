package com.example.open_api_mid;

import com.example.open_api_mid.ApiMidApplication_FULL.Controller.HomeController;
import com.example.open_api_mid.ApiMidApplication_FULL.Repository.WeatherInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherInfoRepository infoRepository;

    @Test
    public void testLoadSave() throws Exception {
        String loc = "서울";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api")
                        .param("loc", loc)
                        .accept(MediaType.TEXT_HTML))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        // Redirected URL 확인
        String redirectedUrl = result.getResponse().getRedirectedUrl();
        assertNotNull(redirectedUrl); // 리다이렉트 URL이 null이 아닌지 확인
        assertTrue(redirectedUrl.contains("/result?loc=109")); // 리다이렉트 URL이 기대한 URL을 포함하는지 확인
    }
}