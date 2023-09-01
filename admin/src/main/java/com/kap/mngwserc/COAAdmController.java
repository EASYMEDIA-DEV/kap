package com.kap.mngwserc;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EmfMap;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre> 
 * 관리자 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc")
@RequiredArgsConstructor
public class COAAdmController {

    /** 서비스 **/
    private final COAAdmService cOAAdmService;

    /**
     * 관리자 목록 페이지
     */
    @GetMapping(value="/list")
    public String getAdmListPage(ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
    {
        log.error("request : {}", request.getParameter("aaa"));
        log.error("emfMap : {}", emfMap);
        cOAAdmService.selectAdmList(COAAdmDTO.builder().build());
        return "mngwserc/COAAdmList.admin";
    }

}