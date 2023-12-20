package com.kap.front.controller.eb;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptMst;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptRspnMst;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBEExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <pre>
 * 교육 과정 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육 과정 Controller
 * @author 김학규
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육사업 > 교육신청")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/education")
public class EBACouseController {

    /** 서비스 **/
    private final EBEExamService eBEExamService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /**
     * 교육과정 신청 목록
     */
    @GetMapping(value="/apply/list")
    public String getEducationApplyList(ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseList.front";
        try
        {
            System.out.println("@@@@@ 온닷!!! 교육과정 신청 목록");
            modelMap.addAttribute("rtnData", "");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

    /**
     * 교육과정 신청 상세
     */
    @GetMapping(value="/apply/detail")
    public String getEducationApplyDtl(ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseDtl.front";
        try
        {
            System.out.println("@@@@@ 온닷!!! 교육과정 신청 상세");
            modelMap.addAttribute("rtnData", "");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

}

