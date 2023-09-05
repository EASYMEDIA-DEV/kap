package com.kap.mngwserc.config;

import com.kap.common.utility.COWebUtil;
import com.kap.service.COUserDetailsHelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig  {

    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests().antMatchers("/**").permitAll().and()
                .headers().frameOptions().sameOrigin().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .formLogin().disable() //기본으로 제공하는 formLogin설정 사용안함
                .httpBasic().disable() //기본으로 제공하는 httpBasic설정 사용안함
                .csrf()
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
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                        if (accessDeniedException instanceof MissingCsrfTokenException || accessDeniedException instanceof InvalidCsrfTokenException) {
                            if(!appLogin)
                            {
                                String accept   = COWebUtil.removeCRLF(request.getHeader("accept"));
                                System.out.println("ForbiddenException");
                                if (accept != null && accept.indexOf("application/json") > -1){
                                    if(COUserDetailsHelperService.isAuthenticated()){
                                        //JSON이면
                                        new Exception("CSRF");
                                    }else{
                                        // 세션이 끊긴 경우
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                    }
                                }
                                else{
                                    new Exception("CSRF");
                                }
                            }
                        }
                    }
                }).and().build();
    }


}
