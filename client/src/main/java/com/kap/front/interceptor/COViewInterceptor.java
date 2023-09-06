package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.exceptionn.UnauthorizedException;
import com.kap.service.COLgnService;
import com.kap.service.COUserDetailsHelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class COViewInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
                viewName = "mbl/" + viewName;
            } else if (tmpDevice.isTablet()) {
                viewName = "mbl/" + viewName;
            } else {
                viewName = "web/" + viewName;
            }
            modelAndView.setViewName(viewName);
        }
    }
}
