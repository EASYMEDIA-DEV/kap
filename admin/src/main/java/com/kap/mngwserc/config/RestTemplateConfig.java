package com.kap.mngwserc.config;

import com.kap.service.COSystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * <pre>
 * API RestTemplate 송수신 처리
 * </pre>
 *
 * @ClassName		    : ApiRestTemplateConfig.java
 * @Description		: API RestTemplate 송수신 처리
 * @author 박주석
 * @since 2023.01.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.01.20	  박주석	             최초 생성
 * </pre>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${api.gpc.end-point}")
    private String endPoint;

    /** GPC KEY **/
    @Value("${api.kap.key}")
    private String apiKey;

    /** GPC SECRET **/
    @Value("${api.kap.secret}")
    private String apiSecret;

    /** 서비스  **/
    private final COSystemLogService cOSystemLogService;

    /**
     * api RestTemplate
     * @return
     */
    @Bean
    public RestTemplate gpcRestTemplate() {
        RestTemplate build = restTemplateBuilder.rootUri(endPoint)
                .additionalInterceptors(new RestTemplateClientHttpRequestInterceptor(cOSystemLogService, apiKey, apiSecret))
                .setConnectTimeout(Duration.ofMinutes(3))
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();
        return build;
    }
}
