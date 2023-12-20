package com.kap.mngwserc.config;

import com.kap.mngwserc.interceptor.COApiValidInterceptor;
import com.kap.mngwserc.interceptor.COAuthenticInterceptor;
import com.kap.mngwserc.interceptor.COMenuInterceptor;
import com.kap.service.COUserDetailsHelperService;
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
        //관리자 권한 체크
        registry.addInterceptor(cOAuthenticInterceptor()).addPathPatterns("/mngwserc/**")
                .order(1)
                .excludePathPatterns("/mngwserc/dext5editor/upload")
                .excludePathPatterns("/mngwserc/upload")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/**/*.*");
        //관리자 메뉴 적용
        registry.addInterceptor(cOMenuInterceptor()).addPathPatterns("/mngwserc/**")
                .order(2)
                .excludePathPatterns("/mngwserc/**/logout")
                .excludePathPatterns("/mngwserc/dext5editor/upload")
                .excludePathPatterns("/mngwserc/dropzone/upload")
                .excludePathPatterns("/mngwserc/nice/**")
                .excludePathPatterns("/mngwserc/file/list")
                .excludePathPatterns("/mngwserc/file/upload")
                .excludePathPatterns("/mngwserc/upload")
                .excludePathPatterns("/mngwserc/drive/*")
                .excludePathPatterns("/mngwserc/**/*insert*")
                .excludePathPatterns("/mngwserc/**/*update*")
                .excludePathPatterns("/mngwserc/**/*delete*")
                .excludePathPatterns("/mngwserc/file/view")
                .excludePathPatterns("/mngwserc/editor-image/upload")
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/mngwserc/co/coa/pwd-check");

        //API 권한 체크
        registry.addInterceptor(cOApiValidInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/sending")
                .order(1);
    }

    //관리자 권한 체크(인터셉터에서 @Resource로 등록된건 빈으로 등록해주어야 사용가능)
    @Bean
    public COAuthenticInterceptor cOAuthenticInterceptor() {
        return new COAuthenticInterceptor();
    }

    //관리자 메뉴 적용(인터셉터에서 @Resource로 등록된건 빈으로 등록해주어야 사용가능)
    @Bean
    public COMenuInterceptor cOMenuInterceptor() {
        return new COMenuInterceptor();
    }

    //API header 검정
    @Bean
    public COApiValidInterceptor cOApiValidInterceptor() {
        return new COApiValidInterceptor();
    }

    //모바일 체크 Device Resolver. 파라미터로 받음.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DeviceHandlerMethodArgumentResolver());
    }

}
