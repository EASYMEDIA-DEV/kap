package com.kap.front.interceptor;

import com.kap.common.utility.COStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
