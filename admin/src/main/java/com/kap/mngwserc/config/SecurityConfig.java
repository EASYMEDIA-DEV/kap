package com.kap.mngwserc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/**" // 임시
        );
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .ignoringAntMatchers("/error/**"
                        ,"/**/*list*"
                        ,"/**/*index*"
                        , "/**/*write*"
                        ,"/mngwsercgateway/login"
                        , "/mngwserc/dext5editor/upload"
                        , "/mngwserc/upload"
                        , "/mngwserc/dropzone/upload"
                        , "/mngwserc/file/list"
                        , "/mngwserc/file/upload"
                        , "/mngwserc/file/view")
                .and()
                .authorizeRequests().antMatchers("/**").permitAll().and()
                .headers().frameOptions().sameOrigin().and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                        if (accessDeniedException instanceof MissingCsrfTokenException || accessDeniedException instanceof InvalidCsrfTokenException) {
                            /*
                            if(!appLogin)
                            {
                                if (accept != null && accept.indexOf("application/json") > -1){
                                    if(COUserDetailsHelper.isAuthenticated()){
                                        //JSON이면
                                        throw new AccessDeniedException("CSRF");
                                    }else{
                                        // 세션이 끊긴 경우
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                    }
                                }
                                else{
                                    throw new AccessDeniedException("CSRF");
                                }
                            }
                            */
                        }
                    }
                }).and().build();
    }


}
