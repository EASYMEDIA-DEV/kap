package com.kap.mngwserc.controller.em;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.EBBEpisdDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBBEpisdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 교육차수관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBBEpisdController.java
 * @Description		: 교육차수관리를 위한 Controller
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebb")
public class EBBEpisdController {

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    public final COCodeService cOCodeService;

    /**
     *  교육회차관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getEpisdPage(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        return "mngwserc/eb/ebb/EBBEpisdList.admin";
    }

    /**
     * 교육회차관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebb/EBBEpisdListAjax";
    }


}

