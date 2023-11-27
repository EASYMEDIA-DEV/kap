package com.kap.mngwserc.interceptor;

import com.easymedia.error.exception.EntityNotFoundException;
import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.exceptionn.UnauthorizedException;
import com.kap.service.COAAdmService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class COAuthenticInterceptor implements HandlerInterceptor{

    //저장된 로그인 세션 조회
    @Autowired
    private COAAdmService cOAAdmService;

    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // log4j2에 사용할 커스텀 map 초기화
        ThreadContext.clearAll();

        //로그인 요청 헤더 체크
        String accept   = COWebUtil.removeCRLF(request.getHeader("accept"));
        if (!COUserDetailsHelperService.isAuthenticated() && !appLogin)
        {
            if (accept != null && accept.indexOf("application/json") > -1)
            {
                //throw new UnauthorizedException("로그인 세션이 유효하지 않습니다.");
                response.sendRedirect("/mngwsercgateway/login");
            }
            else
            {
                //throw new UnauthorizedException("로그인 세션이 유효하지 않습니다.");
                //throw new EntityNotFoundException("화면을");

                response.sendRedirect("/mngwsercgateway/login");
            }
            return false;

        }else{
            //로그인 정보 체크
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            // IP 접근 제한
            String allwIp = cOUserDetailsDTO.getAllwIp();
            if(!"".equals(COStringUtil.nullConvert(allwIp)))
            {
                String[] allwIpArr = allwIp.split(",");
                String clientIp = CONetworkUtil.getMyIPaddress(request);
                boolean isAllw = false;
                for (int i = 0, len = allwIpArr.length; i < len; i++)
                {
                    if (clientIp.equals(allwIpArr[i].trim()))
                    {
                        isAllw = true;
                        break;
                    }
                }
                if (!isAllw)
                {
                    if (accept != null && accept.indexOf("application/json") > -1)
                    {
                        throw new UnauthorizedException("허용되지 않는 IP입니다.");
                    }
                    else
                    {
                        throw new UnauthorizedException("허용되지 않는 IP입니다.");
                    }
                }
            }
            // 중복로그인 검사
            if(!appLogin)
            {
                String lgnSsnId = cOAAdmService.getAdmSessionId(cOUserDetailsDTO.getId());
                if (!COStringUtil.nullConvert(lgnSsnId).equals(cOUserDetailsDTO.getConSessionId()))
                {
                    if (accept != null && accept.indexOf("application/json") > -1)
                    {
                        throw new UnauthorizedException("로그인 세션이 유효하지 않습니다.");
                    }
                    else
                    {
                        throw new UnauthorizedException("로그인 세션이 유효하지 않습니다.");
                    }
                }
            }

            // log4j2에 사용할 커스텀 map 생성
            ThreadContext.put("regId", cOUserDetailsDTO.getId());
            ThreadContext.put("regIp", CONetworkUtil.getMyIPaddress(request));
            try {
                ThreadContext.put("trgtMenuNm", RequestContextHolder.getRequestAttributes().getAttribute("pageIndicator", RequestAttributes.SCOPE_SESSION).toString());
            }catch (Exception e){
                ThreadContext.put("trgtMenuNm", "");
            }
            return true;
        }


    }

}
