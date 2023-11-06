package com.kap.front.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.*;
import com.kap.service.COAAdmService;
import com.kap.service.SMBMnVslService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <pre> 
 * 샘플 Controller
 * </pre>
 *
 * @ClassName		: COCAdmController.java
 * @Description		: 관리자 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/front")
public class COASmpleController {

    /** 서비스 **/
    private final COAAdmService cOAAdmService;

    private final SMBMnVslService sMBMnVslService;

    /**
     * 샘플 목록 페이지
     */
    @GetMapping(value="/list")
    public String getAdmListPage(COSmpleSrchDTO cOSmpleSrchDTO, ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
    {
        log.error("request : {}", request.getParameter("titl"));
        log.error("emfMap : {}", emfMap);
        log.error("cOAAdmDTO : {}", cOSmpleSrchDTO.toString());
        cOAAdmService.selectAdmList(COAAdmDTO.builder().build());
        return "co/coa/COAAdmList.front";
    }

    /**
     * 샘플 목록 페이지
     */
    @GetMapping(value="/dtoTest")
    public String getMnVslListPage(SMBMainVslFrontDTO sMBMainVslFrontDTO, ModelMap modelMap) throws Exception
    {
        try {
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslFrontDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "co/coa/COAdtoTestList.front";
    }

}