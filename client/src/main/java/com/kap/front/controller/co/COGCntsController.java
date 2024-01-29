package com.kap.front.controller.co;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.service.COGCntsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * 컨텐츠(CMS) 페이지 Controller
 * </pre>
 *
 * @ClassName		: COGCntsController.java
 * @Description		: 컨텐츠(CMS) 페이지 Controller
 * @author 장두석
 * @since 2024.01.03
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2024.01.03			장두석			 		최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COGCntsController {

    /** Service **/
    private final COGCntsService cOGCntsService;


    /**
     * 컨텐츠(CMS) 페이지
     */
    @GetMapping("/**/content")
    public String getCntsPage(HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            // get Menu Sequance
            Integer menuSeq = -1;
            String uri = request.getRequestURI();
            List<COMenuDTO> parntMenuList = (List<COMenuDTO>) request.getAttribute("parntMenuList");
            for(COMenuDTO value : parntMenuList) {
                if(uri.equals(value.getUserUrl())) {
                    menuSeq = value.getMenuSeq();
                }
            }

            // get CMS Data
            COGCntsDTO pCOGCntsDTO = new COGCntsDTO();
            pCOGCntsDTO.setMenuSeq(menuSeq);
            pCOGCntsDTO.setUserYn("Y");
            COGCntsDTO rtnData = cOGCntsService.selectCntsDtl(pCOGCntsDTO);
            modelMap.addAttribute("rtnData", rtnData);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/co/COGCntsView.front";
    }

}
