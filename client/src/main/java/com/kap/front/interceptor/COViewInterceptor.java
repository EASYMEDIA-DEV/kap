package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COMenuDTO;
import com.kap.service.COBMenuService;
import com.kap.service.COBUserMenuService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
public class COViewInterceptor implements HandlerInterceptor{
    /* 메뉴 서비스 */
    @Autowired
    private COBUserMenuService cOBUserMenuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 메뉴 목록
        List<COMenuDTO> menuList = null;
        //메뉴 목록을 조회한다.
        if (RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION) != null)
        {
            menuList = (List<COMenuDTO>) RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION);
        }
        else
        {
            COMenuDTO cOMenuDTO = new COMenuDTO();
            cOMenuDTO.setMenuSeq(612);
            cOMenuDTO.setIsMenu("Y");
            menuList = cOBUserMenuService.getMenuList(cOMenuDTO);
            RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);
        }
        COMenuDTO pageMenuDto = null;
        String requestURI = request.getRequestURI();
        String userUrl = "", menuNm = "";
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            userUrl = COStringUtil.nullConvert(menuList.get(i).getUserUrl());
            if (userUrl != null && !"".equals(userUrl) && requestURI.equals(userUrl))         //requestURI.indexOf(userUrl) > -1
            {
                if(menuNm.equals("") || userUrl.equals(menuList.get(i-1).getUserUrl())){
                    menuNm = menuList.get(i).getMenuNm();
                    pageMenuDto    = menuList.get(i);
                }
            }
        }
        request.setAttribute("pageMenuDto", pageMenuDto);
        //GNB 메뉴 조회
        List<COMenuDTO> gnbMenuList = new ArrayList<COMenuDTO>();
        String bfrGnbYn = "";
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            // 상위 gnb 노출 여부 따라가기기 (그룹은 2depth 까지만 gnb에 노출)
            if(menuList.get(i).getDpth() == 3 ){
                bfrGnbYn = menuList.get(i).getGnbYn();
            }else if((menuList.get(i).getDpth() == 4 ) && "Y".equals(bfrGnbYn)){
                bfrGnbYn = menuList.get(i).getGnbYn();
            }else if((menuList.get(i).getDpth() == 5 ) && "Y".equals(bfrGnbYn)){
                bfrGnbYn = menuList.get(i).getGnbYn();
            }
            if("Y".equals(bfrGnbYn) &&  "Y".equals(menuList.get(i).getGnbYn())){

                // 상위 메뉴 미노출여부 따라가기
                if(i != 0 &&
                        (menuList.get(i-1).getChildcnt() == 0 &&  menuList.get(i).getChildcnt() == 0) &&
                        ((gnbMenuList.get(gnbMenuList.size()-1).getDpth() == menuList.get(i).getDpth() && gnbMenuList.get(gnbMenuList.size()-1).getParntSeq() != menuList.get(i).getParntSeq()) ||
                                (menuList.get(i).getPstn() == 0 && menuList.get(i-1).getParntSeq() != menuList.get(i).getParntSeq())
                        ))
                {
                    continue;
                }
                gnbMenuList.add(menuList.get(i));
            }
        }
        request.setAttribute("gnbMenuList", gnbMenuList);
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
        return true;
    }

    private int setMenuChildList(COMenuDTO gnbMenu, List<COMenuDTO> menuList, int i) throws Exception{
        return 0;
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
