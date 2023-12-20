package com.kap.mngwserc.interceptor;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.exceptionn.UnauthorizedException;
import com.kap.service.COLgnService;
import com.kap.service.COUserDetailsHelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class COApiValidInterceptor implements HandlerInterceptor{
    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;
    /** GPC KEY **/
    @Value("${api.kap.key}")
    private String gpcApiKey;
    /** GPC SECRET **/
    @Value("${api.kap.secret}")
    private String gpcApiSecret;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //수신 header 검증
        String strAuthorization     = COWebUtil.removeCRLF(String.valueOf( request.getHeader("Authorization") ));
        log.error("strAuthorization : {}", strAuthorization);
        String[] authorizationList = strAuthorization.split(",");
        String receiveApiKey = "";
        String receiveApiSignature = "";
        String receiveApiTimestamp = "";
        if(authorizationList != null && authorizationList.length > 2)
        {
            receiveApiKey       = authorizationList[0].replace("key=", "").trim();
            if(!gpcApiKey.equals(receiveApiKey)){
                throw new BusinessException(ErrorCode.ACCESS_DENIED);
            }
            receiveApiSignature = authorizationList[1].replace("signature=", "").trim();
            receiveApiTimestamp = authorizationList[2].replace("timestamp=", "").trim();
            if(!isAuthorization(gpcApiKey, receiveApiSignature, receiveApiTimestamp, gpcApiSecret)){
                throw new BusinessException(ErrorCode.ACCESS_DENIED);
            }
        }
        else
        {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        return true;
    }

    /**
     * 수신 Authorization 체크
     *
     * @param
     * @return String header의 auth정보
     */
    private boolean isAuthorization(String apiKey, String signature, String strTimestamp, String secretKey) throws Exception
    {
        boolean isAuthorization = false;
        //apikey로 회사 조회 후 secret 확인
        String authSignature = COWebUtil.getIfToken(apiKey, secretKey, strTimestamp);
        Date date= new Date();
        //초 단위 설정
        Long timestamp = (date.getTime() / 1000);
        //1시간 유효
        if((signature.equals(authSignature) && (timestamp - Long.valueOf(strTimestamp) < (60*60))) )
        {
            isAuthorization = true;
        }
        return isAuthorization;
    }
}
