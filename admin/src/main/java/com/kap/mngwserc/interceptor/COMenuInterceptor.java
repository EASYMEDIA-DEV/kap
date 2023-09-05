package com.kap.mngwserc.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.exceptionn.UnauthorizedException;
import com.kap.service.COLgnService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class COMenuInterceptor implements HandlerInterceptor{

    //저장된 로그인 세션 조회
    @Autowired
    private COLgnService cOLgnService;

    //강제로그인여부
    @Value("${app.is-login}")
    private Boolean appLogin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //넘어온 파라미터 XSS 체크해서 넘기기
        String strPam = "";
        //현재 경로의 파라미터
        int comma = 0;
        Enumeration<String> params = request.getParameterNames();
        String paramName = "";
        String[] paramValues = null;
        while (params.hasMoreElements())
        {
            if (comma > 0) strPam += "&";
            paramName = (String) params.nextElement();
            paramValues =  request.getParameterValues(paramName);
            for(int q = 0; q < paramValues.length ; q++){
                strPam += COWebUtil.clearXSSMinimum(paramName)+"="+paramValues[q]+"&";
            }
            comma++;
        }
        request.setAttribute("strPam", strPam);
        // 메뉴 목록
        String accept   = COWebUtil.removeCRLF(request.getHeader("accept"));
        List<COMenuDTO> driveMenuList = null;
        List<COMenuDTO> menuList = null;
        COAAdmDTO lngCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        //dirve 메뉴 목록을 조회한다.
        if (RequestContextHolder.getRequestAttributes().getAttribute("driveMenuList", RequestAttributes.SCOPE_SESSION) != null)
        {
            driveMenuList = (List<COMenuDTO>) RequestContextHolder.getRequestAttributes().getAttribute("driveMenuList", RequestAttributes.SCOPE_SESSION);
        }
        else
        {
            driveMenuList = cOLgnService.getDriveMenuList(lngCOAAdmDTO);
            RequestContextHolder.getRequestAttributes().setAttribute("driveMenuList", driveMenuList, RequestAttributes.SCOPE_SESSION);
        }
        //드라이브 순번 확인
        boolean isMenuList = true;
        if (RequestContextHolder.getRequestAttributes().getAttribute("driveMenuSeq", RequestAttributes.SCOPE_SESSION) != null)
        {
            int driveMenuSeq = (int) RequestContextHolder.getRequestAttributes().getAttribute("driveMenuSeq", RequestAttributes.SCOPE_SESSION);
            if(driveMenuSeq != lngCOAAdmDTO.getDriveMenuSeq()){
                isMenuList = false;
            }
            lngCOAAdmDTO.setDriveMenuSeq( driveMenuSeq );
        }
        else
        {
            if(driveMenuList != null && driveMenuList.size() > 0) {
                int driveMenuSeq = driveMenuList.get(0).getMenuSeq();
                lngCOAAdmDTO.setDriveMenuSeq(driveMenuSeq);
                RequestContextHolder.getRequestAttributes().setAttribute("driveMenuSeq", driveMenuSeq, RequestAttributes.SCOPE_SESSION);
            }
        }
        //메뉴 목록을 조회한다.
        if (RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION) != null && isMenuList)
        {
            menuList = (List<COMenuDTO>) RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION);
        }
        else
        {
            menuList = cOLgnService.getMenuList(lngCOAAdmDTO);
            RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);
        }
        //드라이브 조회
        //현재 메뉴 위치 찾기
        String requestURI = request.getRequestURI();
        int pageNo = -1, lftVal=0, rhtVal=0;
        String firstUrl = "", admUrl = "", pageTitle = "";
        //URL체크시 REST방식이라서 마지막 서브폴더는 잘라야 한다.
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            admUrl = "";
            if(!"".equals(COStringUtil.nullConvert(menuList.get(i).getAdmUrl())))
            {
                admUrl = menuList.get(i).getAdmUrl().substring(0, menuList.get(i).getAdmUrl().lastIndexOf("/")+1);
            }
            if ("".equals(firstUrl) && !"".equals(admUrl) && !"".equals(menuList.get(i).getAdmUrl()))
            {
                firstUrl = menuList.get(i).getAdmUrl();
            }
            //현재 페이지의 최 상단 부모 조회
            if(menuList.get(i).getDpth() == 2){
                lftVal    = menuList.get(i).getLftVal();
                rhtVal    = menuList.get(i).getRhtVal();
            }
            if (admUrl != null && !"".equals(admUrl) && requestURI.indexOf(admUrl) > -1)
            {
                pageNo    = menuList.get(i).getMenuSeq();
                pageTitle = menuList.get(i).getMenuNm();
                break;
            }
        }
        // 메뉴 접근 권한
        if (pageNo == -1 && !appLogin)
        {
            if ("".equals(firstUrl))
            {
                firstUrl = "/";
            }
            if (accept != null && accept.indexOf("application/json") > -1)
            {
                throw new UnauthorizedException("잘못된 접근입니다.");
            }
            else
            {
                throw new UnauthorizedException("잘못된 접근입니다.");
            }
        }
        request.setAttribute("firstUrl", firstUrl);
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("pageTitle", pageTitle);
        // 페이지 인디케이트
        List<COMenuDTO> parntMenuList = new ArrayList<COMenuDTO>();
        List<COMenuDTO> lnbMenuList = new ArrayList<COMenuDTO>();
        int menuSeq = -1, parntSeq = -1;
        for (int i = menuList.size() - 1; i >= 0; i--)
        {
            menuSeq = menuList.get(i).getMenuSeq();
            if (pageNo == menuSeq)
            {
                parntSeq = menuList.get(i).getParntSeq();
                parntMenuList.add(menuList.get(i));
            }
            if (parntSeq == menuSeq)
            {
                parntSeq = menuList.get(i).getParntSeq();
                parntMenuList.add(menuList.get(i));
            }

        }
        String pageIndicator = "";
        for (int i = parntMenuList.size() - 1; i >= 0; i--)
        {
            if ("".equals(pageIndicator))
            {
                pageIndicator = parntMenuList.get(i).getMenuNm();
            }
            else
            {
                pageIndicator = pageIndicator + " &gt; " + parntMenuList.get(i).getMenuNm();
            }
        }
        //왼쪽 메뉴
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            //왼쪽메뉴
            if(lftVal <= menuList.get(i).getLftVal() && rhtVal >= menuList.get(i).getRhtVal()){
                lnbMenuList.add(menuList.get(i));
            }
        }
        request.setAttribute("parntMenuList", parntMenuList);
        request.setAttribute("lnbMenuList", lnbMenuList);
        //AOP용
        RequestContextHolder.getRequestAttributes().setAttribute("pageIndicator", pageIndicator, RequestAttributes.SCOPE_SESSION);

        return true;
    }
}
