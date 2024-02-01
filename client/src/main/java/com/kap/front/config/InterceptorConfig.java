package com.kap.front.config;

import com.kap.front.interceptor.COAuthenticInterceptor;
import com.kap.front.interceptor.COBackCookieDeleteInterceptor;
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

        //사용자 권한 체크
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/my-page/**")
                .excludePathPatterns("/my-page/**/*.*")
                .order(1);

        //교육신청 로그인체크
        /*registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/education/apply/detail/**")
                .order(1);*/
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/education/apply/step1/**")
                .order(1);

        //방문교육신청 로그인체크
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/education/visit/**")
                .order(1);

        //상생 로그인체크
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/coexistence/**")
                .excludePathPatterns("/coexistence/**/**/content")
                .excludePathPatterns("/coexistence/**/content")
                .excludePathPatterns("/coexistence/**/applyChecked")
                .excludePathPatterns("/coexistence/**/**/applyChecked")
                .excludePathPatterns("/coexistence/survey/**")
                .excludePathPatterns("/coexistence/**/addRoundMore")
                .order(1);

        //web, mbl jsp(header, footer)
        registry.addInterceptor(cOViewInterceptor()).addPathPatterns("/**").order(2)
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/**/**/*.*")
                .excludePathPatterns("/my-page/sq-license/complete/insert")
                .excludePathPatterns("/file/upload")
                .excludePathPatterns("/error")
                .excludePathPatterns("/gpc/**");

        //뒤로가기 쿠키 삭제
        registry.addInterceptor(cOBackCookieDeleteInterceptor())
                .addPathPatterns("/my-page/education/exam/complete")
                .order(3);
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

    //뒤로가기 Cache 삭제
    @Bean
    public COBackCookieDeleteInterceptor cOBackCookieDeleteInterceptor() {
        return new COBackCookieDeleteInterceptor();
    }

    //모바일 체크 Device Resolver. 파라미터로 받음.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DeviceHandlerMethodArgumentResolver());
    }

}
