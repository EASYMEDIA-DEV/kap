package com.kap.front.config;

import com.kap.front.interceptor.COAuthenticInterceptor;
import com.kap.front.interceptor.COViewInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //인터셉터 주소 세팅
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //모바일 체크
        registry.addInterceptor(new DeviceResolverHandlerInterceptor());
        //web, mbl jsp
        registry.addInterceptor(cOViewInterceptor()).addPathPatterns("/**");

        //사용자 권한 체크
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/my-page/**")
                .order(1);
    }

    //관리자 메뉴 적용(인터셉터에서 @Resource로 등록된건 빈으로 등록해주어야 사용가능)
    @Bean
    public COViewInterceptor cOViewInterceptor() {
        return new COViewInterceptor();
    }

    //사용자 권한 체크
    @Bean
    public COAuthenticInterceptor cOAuthenticInterceptor() {
        return new COAuthenticInterceptor();
    }

    //모바일 체크 Device Resolver. 파라미터로 받음.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DeviceHandlerMethodArgumentResolver());
    }

}
