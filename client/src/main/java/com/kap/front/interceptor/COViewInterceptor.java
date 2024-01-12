package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.service.COBUserMenuService;
import com.kap.service.COCodeService;
import com.kap.service.COCommService;
import com.kap.service.SMJFormService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class COViewInterceptor implements HandlerInterceptor{
    /* 메뉴 서비스 */
    @Autowired
    private COBUserMenuService cOBUserMenuService;
    /* 코드 서비스 */
    @Autowired
    private  COCodeService cOCodeService;

    /* 공통 서비스 */
    @Autowired
    private COCommService cOCommService;

    @Autowired
    private SMJFormService sMJFormService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //넘어온 파라미터 XSS 체크해서 넘기기
        String strPam = "";
        Enumeration<String> params = request.getParameterNames();
        String paramName = "";
        String[] paramValues = null;
        while (params.hasMoreElements())
        {
            paramName = (String) params.nextElement();
            paramValues =  request.getParameterValues(paramName);
            for(int q = 0; q < paramValues.length ; q++){
                strPam += COWebUtil.clearXSSMinimum(paramName)+"="+paramValues[q]+"&";
            }
        }
        request.setAttribute("strPam", strPam);
        // 메뉴 목록
        List<COMenuDTO> menuList = null;
        int userMenuSeq = 612;


        //메뉴 목록을 조회한다.
        /*if (RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION) != null)
        {
            menuList = (List<COMenuDTO>) RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION);
        }
        else
        {
            COMenuDTO cOMenuDTO = new COMenuDTO();
            cOMenuDTO.setMenuSeq(userMenuSeq);
            cOMenuDTO.setIsMenu("Y");
            menuList = cOBUserMenuService.getClientMenuList(cOMenuDTO);
            RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);
        }*/

        /*메뉴 구조 확립 되기전까진 호출할때마다 메뉴 생성하도록 변경... 이렇게 안하면 확인할때마다 세션 날려야됨*/
        COMenuDTO cOMenuDTO = new COMenuDTO();
        cOMenuDTO.setMenuSeq(userMenuSeq);
        cOMenuDTO.setIsMenu("Y");
        menuList = cOBUserMenuService.getMenuList(cOMenuDTO);
        RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);





        //menuLsit 계층으로 출력
        JSONArray gnbMenuList = cOBUserMenuService.getJsonData(menuList, 0, userMenuSeq);
        request.setAttribute("gnbMenuList", gnbMenuList);
        COMenuDTO pageMenuDto = null;
        String requestURI = request.getRequestURI();
        String userUrl = "", folderUrl = "";
        folderUrl = COStringUtil.getUrlFolder(requestURI);
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            userUrl = COStringUtil.nullConvert(menuList.get(i).getUserUrl());
            if (userUrl != null && !"".equals(userUrl) && !"/".equals(folderUrl) )         //requestURI.indexOf(userUrl) > -1
            {
                if(userUrl.startsWith(folderUrl)){
                    pageMenuDto    = menuList.get(i);
                    break;
                }
            }
        }
        request.setAttribute("pageMenuDto", pageMenuDto);
        // 페이지 인디케이트
        List<COMenuDTO> parntMenuList = new ArrayList<COMenuDTO>();
        int menuSeq = -1, parntSeq = -1;
        if(pageMenuDto != null){
            for (int i = menuList.size() - 1; i >= 0; i--)
            {
                menuSeq = menuList.get(i).getMenuSeq();
                if (pageMenuDto.getMenuSeq() == menuSeq)
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
        }
        //부모 메뉴 조회
        //역순 정리
        Collections.reverse(parntMenuList);
        request.setAttribute("parntMenuList", parntMenuList);


        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        //과정분류 공통코드 세팅
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        request.setAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        SMJFormDTO smjFormDTO = new SMJFormDTO();
        smjFormDTO.setTypeCd("BUSINESS03");
        SMJFormDTO rtnFormDto = sMJFormService.selectFormDtl(smjFormDTO);

        request.setAttribute("rtnFormDto", rtnFormDto);

        // 코드 set
        cdDtlList.add("FRONT_TOTAL_KEYWORD");
        request.setAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        //공지사항
        request.setAttribute("headerNtfyList", cOCommService.getHeaderNtfyList());
        // 트랜드 리스트 관련
        SMKTrendDTO sMKTrendDTO = new SMKTrendDTO();
        request.setAttribute("quickTrendList", cOCommService.quickTrendList(sMKTrendDTO));

        //과정분류 - 소분류
        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        request.setAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        request.setAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        request.setAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if (modelAndView != null)
        {
            String viewName = COStringUtil.nullConvert(modelAndView.getViewName());
            Device tmpDevice = DeviceUtils.getCurrentDevice(request);
            if (tmpDevice.isMobile()) {
                //viewName = "mbl/" + viewName;
            } else if (tmpDevice.isTablet()) {
                //viewName = "mbl/" + viewName;
            } else {
                //viewName = "web/" + viewName;
            }
            modelAndView.setViewName(viewName);
        }
    }
}
