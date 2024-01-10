package com.kap.front.controller.wb.wbn;

import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbk.WBKBRegisterDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * 공통사업 Controller
 * </pre>
 *
 * @author 박준희
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 공통사업 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		오병호				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/nominees")
public class WBNNomineesController {
    /**
     * 서비스
     **/
    public final WBJBAcomListService wBJBAcomListService;
    public final WBKBRegisterService wBKBRegisterService;
    public final COGCntsService pCOGCntsService;
    public final COCodeService cOCodeService;


    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBJAcomDTO wBJAcomDTO, WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbn/WBNNomineesIndex.front";
        try {
            modelMap.addAttribute("rtnData", wBJBAcomListService.selectWinerList(wBJAcomDTO));
            modelMap.addAttribute("FuCarData", wBKBRegisterService.selectWinerList(wBKBRegisterDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

}
