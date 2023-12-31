package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpg.MPGWthdrwDto;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.mp.mpg.MPGWthdrwService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 탈퇴회원관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 탈퇴회원관리를 위한 Controller
 * @author 양현우
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpg")
public class MPGWthdrwController {

    private final MPGWthdrwService mpgWthdrwService;

    /**
     * 탈퇴 회원 리스트 조회
     * @param mpgWthdrwDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String getUserListPage(MPGWthdrwDto mpgWthdrwDto ,
                                  ModelMap modelMap ) throws Exception {
        modelMap.addAttribute("rtnData", mpgWthdrwService.selectWthdrwList(mpgWthdrwDto));

        return "mngwserc/mp/mpg/MPGWthdrwList.admin";
    }

    /**
     * 탈퇴 회원 조회
     */
    @PostMapping(value = "/select")
    public String selectWthdrwListPageAjax(MPGWthdrwDto mpgWthdrwDto ,
                                         ModelMap modelMap ) throws Exception {
        modelMap.addAttribute("rtnData", mpgWthdrwService.selectWthdrwList(mpgWthdrwDto));

        return "mngwserc/mp/mpg/MPGWthdrwListAjax";
    }

    /**
     * 엑셀 다운
     * @param mpgWthdrwDto
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPGWthdrwDto mpgWthdrwDto ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            mpgWthdrwService.excelDownload(mpgWthdrwService.selectWthdrwList(mpgWthdrwDto), response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }
}
