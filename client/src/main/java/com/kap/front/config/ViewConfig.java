package com.kap.front.config;

import com.kap.service.COUserDetailsHelperService;
import com.kap.service.impl.CODummyUserDetailsServiceImpl;
import com.kap.service.impl.COUserDetailsSessionServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class ViewConfig {

    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;

    //view Resolver
    @Bean(name="viewResolver")
    public BeanNameViewResolver beanNameViewResolver() {
        final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }

    @Bean(name="tilesViewResolver")
    public TilesViewResolver tilesViewResolver() {
        final TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setOrder(1);
        return tilesViewResolver;
    }
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer configurer = new TilesConfigurer();
        String[] defs = {"classpath*:/tiles.xml" };
        configurer.setDefinitions(defs);
        configurer.setCheckRefresh(true);
        return configurer;
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(2);
        return resolver;
    }

    //jsonView 빈네임 설정을 통해, jsonView가 리턴될 때, JSON형태의 데이터로 매핑
    @Bean(name="jsonView")
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setContentType("application/json;charset=UTF-8");
        return jsonView;
    }

    //강제 로그인 세션 Bean 등록
    @Bean(name="cOUserDetailsHelperService")
    public COUserDetailsHelperService cOUserDetailsHelper() {
        COUserDetailsHelperService helper = new COUserDetailsHelperService();
        if(appLogin)
        {
            //강제 로그인
            helper.setEgovUserDetailsService(new CODummyUserDetailsServiceImpl());
        }
        else
        {
            //세션 로그인
            helper.setEgovUserDetailsService(new COUserDetailsSessionServiceImpl());
        }
        return helper;
    }



}
