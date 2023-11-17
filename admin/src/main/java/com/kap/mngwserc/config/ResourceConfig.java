package com.kap.mngwserc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;

/**
 * <pre>
 * Data config 설정
 * </pre>
 *
 * @ClassName		    : ApiWebMvcConfig.java
 * @Description		: XSS 필터 config 설정
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${app.file.upload-path}")
    private String fileStorePath;
    //이미지 외부 입력
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///".concat(fileStorePath).concat(File.separator).concat("upload").concat(File.separator))
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
