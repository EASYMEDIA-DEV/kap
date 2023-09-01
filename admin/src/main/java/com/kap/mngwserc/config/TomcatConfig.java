package com.kap.mngwserc.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
public class TomcatConfig {

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new CustomTomcatServletWebServerFactory();
    }

    static class CustomTomcatServletWebServerFactory extends TomcatServletWebServerFactory {

        @Override
        protected void postProcessContext(Context context) {
            Set<String> pattern = new LinkedHashSet<>();

            pattern.add("jaxb*.jar");
            pattern.add("jaxws*.jar");
            pattern.add("ha-api*.jar");

            StandardJarScanFilter filter = new StandardJarScanFilter();
            filter.setTldSkip(StringUtils.collectionToCommaDelimitedString(pattern));

            ((StandardJarScanner) context.getJarScanner()).setJarScanFilter(filter);
        }
    }
}