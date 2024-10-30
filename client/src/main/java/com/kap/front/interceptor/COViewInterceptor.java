package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.service.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private COMainService cOMainService;

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

        //요청 디바이스 구분
        String userAgent = request.getHeader("User-Agent");
        String device = "";
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
                device = "MO";
            }
            else {
                device = "PC";
            }
        }
        request.setAttribute("device", device);

        // 메뉴 목록
        List<COMenuDTO> menuList = null;
        int userMenuSeq = 612;


        //메뉴 목록을 조회한다.
        if (RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION) != null)
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
        }

        HashMap<String, List<COCodeDTO>> cdDtlListMap1 = new HashMap<>();
        HashMap<String, List<COCodeDTO>> cdDtlListMap2 = new HashMap<>();

        if (RequestContextHolder.getRequestAttributes().getAttribute("cdDtlList1", RequestAttributes.SCOPE_SESSION) != null) {
            cdDtlListMap1 = (HashMap<String, List<COCodeDTO>>) RequestContextHolder.getRequestAttributes().getAttribute("cdDtlList1", RequestAttributes.SCOPE_SESSION);
        }else{
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            // 코드 set
            cdDtlList.add("CLASS_TYPE");
            cdDtlList.add("STDUY_MTHD"); //학습방식
            cdDtlListMap1 = cOCodeService.getCmmCodeBindAll(cdDtlList, "2");
            RequestContextHolder.getRequestAttributes().setAttribute("cdDtlList1", cdDtlListMap1, RequestAttributes.SCOPE_SESSION);
        }

        if (RequestContextHolder.getRequestAttributes().getAttribute("cdDtlList2", RequestAttributes.SCOPE_SESSION) != null) {
            cdDtlListMap2 = (HashMap<String, List<COCodeDTO>>) RequestContextHolder.getRequestAttributes().getAttribute("cdDtlList2", RequestAttributes.SCOPE_SESSION);
        }else{
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            // 코드 set
            cdDtlList.add("FRONT_TOTAL_KEYWORD");
            cdDtlListMap2 = cOCodeService.getCmmCodeBindAll(cdDtlList);
            RequestContextHolder.getRequestAttributes().setAttribute("cdDtlList2", cdDtlListMap2, RequestAttributes.SCOPE_SESSION);
        }
        request.setAttribute("classTypeList",  cdDtlListMap1);
        request.setAttribute("cdDtlList",  cdDtlListMap2);

        /*메뉴 구조 확립 되기전까진 호출할때마다 메뉴 생성하도록 변경... 이렇게 안하면 확인할때마다 세션 날려야됨*/
        /*COMenuDTO cOMenuDTO = new COMenuDTO();
        cOMenuDTO.setMenuSeq(userMenuSeq);
        cOMenuDTO.setIsMenu("Y");
        menuList = cOBUserMenuService.getMenuList(cOMenuDTO);
        RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);*/


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


        CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO = new CBATechGuidanceInsertDTO();
        EBBEpisdDTO ebbEpisdDTO = new EBBEpisdDTO();
        MPBBsnSearchDTO mpbBnsSearchDTO = new MPBBsnSearchDTO();
        boolean selectYn = false;

        //1년간 사업 카운팅
        //위원일 경우 카운팅 x
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        if(cOUserDetailsDTO instanceof COUserDetailsDTO) {
            if(cOUserDetailsDTO.getRespCd().equals("0000") || cOUserDetailsDTO.getRespCd().equals("1310")) {
                if (!cOUserDetailsDTO.getAuthCd().equals("CS")) {

                    ebbEpisdDTO.setMemSeq(cOUserDetailsDTO.getSeq());

                    pCBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));

                    Date today = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(today);
                    calendar.add(Calendar.YEAR, -1);

                    Date oneYearAgo = calendar.getTime();

                    String strtDt = df.format(oneYearAgo);
                    String endDt = df.format(today);

                    mpbBnsSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                    mpbBnsSearchDTO.setDateType("2");
                    mpbBnsSearchDTO.setStrtDt(strtDt);
                    mpbBnsSearchDTO.setEndDt(endDt);
                    List<String> status = new ArrayList<>();
                    status.add("1");
                    status.add("2");
                    status.add("3");
                    status.add("4");
                    mpbBnsSearchDTO.setStatusChk(status);

                    selectYn = true; // true인 경우에만 selectMainGroup2에서 3개 항목에 대해 조회
                }
            }
        }

        //양식관리
        SMJFormDTO smjFormDTO = new SMJFormDTO();
        smjFormDTO.setTypeCd("BUSINESS03");

        // 트랜드 리스트 관련
        SMKTrendDTO sMKTrendDTO = new SMKTrendDTO();

        HashMap<String, Object> rtnMap = cOMainService.selectMainGroup2(selectYn, smjFormDTO, sMKTrendDTO, ebbEpisdDTO, pCBATechGuidanceInsertDTO, mpbBnsSearchDTO);

        request.setAttribute("rtnFormDto", rtnMap.get("rtnFormDto"));//양식관리

        request.setAttribute("headerNtfyList", rtnMap.get("headerNtfyList"));//공지사항
        request.setAttribute("quickTrendList", rtnMap.get("quickTrendList"));//트랜드 리스트

        request.setAttribute("eduYearCnt", rtnMap.get("eduYearCnt")); //교육 1년
        request.setAttribute("conYearCnt", rtnMap.get("conYearCnt")); //컨설팅 1년
        request.setAttribute("sanYearCnt", rtnMap.get("sanYearCnt"));


        //과정분류 - 소분류
        List<COCodeDTO>[] arrCdList;
        if (RequestContextHolder.getRequestAttributes().getAttribute("classCdList", RequestAttributes.SCOPE_SESSION) != null) {
            arrCdList = (List<COCodeDTO>[]) RequestContextHolder.getRequestAttributes().getAttribute("classCdList", RequestAttributes.SCOPE_SESSION);
        }else{
            // 공통코드 배열 셋팅
            List<COCodeDTO> tempList = new ArrayList<>();
            String[] classCodes = {"CLASS01", "CLASS02", "CLASS03"};

            for (String code : classCodes) {
                COCodeDTO cOCodeDTO = new COCodeDTO();
                cOCodeDTO.setCd(code);
                tempList.add(cOCodeDTO);
            }

            arrCdList = cOCodeService.getCdIdList(tempList);

            RequestContextHolder.getRequestAttributes().setAttribute("classCdList", arrCdList, RequestAttributes.SCOPE_SESSION);
        }

        for(int i=0; i<arrCdList.length; i++){
            request.setAttribute("cdList"+(i+1), arrCdList[i]);
        }

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
