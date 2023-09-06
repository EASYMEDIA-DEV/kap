package com.kap.front.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSmpleSrchDTO;
import com.kap.core.dto.EmfMap;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
     * <pre>
     * 코드 관리 API Controller
     * </pre>
     *
     * @ClassName		: COFCodeRestController.java
     * @Description		: 코드 관리 API Controller
     * @author 허진영
     * @since 2020.10.20
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2020.10.20			허진영			 		최초생성
     * </pre>
     */
    @RestController
    @RequestMapping("/front")
    public class COFCodeRestController {
        /**
         * 코드 조회
         */
        /**
         * 샘플 VALID 목록 페이지
         */
        @GetMapping(value="/list/valid")
        public COAAdmDTO getAdmListValidPage(@Valid COSmpleSrchDTO cOSmpleSrchDTO, ModelMap modelMap, @MapData EmfMap emfMap, Device device, HttpServletRequest request) throws Exception
        {
            log.error("cOAAdmDTO : {}", cOSmpleSrchDTO.toString());
            log.error("device : {}_{}", device.isMobile(), device.isTablet());
            Device tmpDevice = DeviceUtils.getCurrentDevice(request);
            if (tmpDevice.isMobile()) {
                log.info("Hello mobile user!");
            } else if (tmpDevice.isTablet()) {
                log.info("Hello tablet user!");
            } else {
                log.info("Hello desktop user!");
            }
            return cOAAdmService.selectAdmList(COAAdmDTO.builder().build());
        }
    }
}