package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.service.COUserDetailsHelperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Slf4j
public class COAuthenticInterceptor implements HandlerInterceptor{

    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // log4j2에 사용할 커스텀 map 초기화
        ThreadContext.clearAll();
        //로그인 요청 헤더 체크
        if (!COUserDetailsHelperService.isAuthenticated() && !appLogin)
        {
            // URL 확장자가 ajax 또는 content-type이 json이면 403에러를 내밷고 공통JS 에서 받아서 후 처리
            String requestURI = request.getRequestURI();
            String accept     = COWebUtil.removeCRLF(request.getHeader("accept"));
            if (requestURI.endsWith(".ajax") || (accept != null && accept.indexOf("application/json") > -1))
            {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            else
            {
                ModelAndView modelAndView = new ModelAndView("blank.error");
                if ("".equals(COStringUtil.nullConvert(requestURI)))
                {
                    modelAndView.addObject("url", "/login");
                }
                else
                {
                    String trgtUrl = requestURI, strPam = COStringUtil.nullConvert(request.getQueryString());
                    if (!"".equals(strPam))
                    {
                        trgtUrl += "?" + strPam;
                    }
                    modelAndView.addObject("url", "/login?rtnUrl=" + URLEncoder.encode(trgtUrl, "UTF-8"));
                }
                throw new ModelAndViewDefiningException(modelAndView);
            }
        }
        return true;
    }
}
