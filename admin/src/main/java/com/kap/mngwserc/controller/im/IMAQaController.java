package com.kap.mngwserc.controller.im;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.IMAQaDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 1:1 문의 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: IMAQaController.java
 * @Description		: 1:1 문의 관리를 위한 Controller
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.01		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/im/ima")
public class IMAQaController {

    //1:1 문의 서비스
    private final IMAQaService iMAQaService;
    //코드 서비스
    private final COCodeService cOCodeService;

    /**
     * 1:1 문의 목록 페이지
     */
    @GetMapping(value="/list")
    public String getQaListPage(IMAQaDTO iMAQaDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");

            modelMap.addAttribute("langCd", langCd);

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", iMAQaDTO);
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/im/ima/IMAQaList.admin";
    }

    /**
     * 1:1 문의 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectQaListPageAjax(IMAQaDTO iMAQaDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            iMAQaDTO.setLangCd(langCd);

            modelMap.addAttribute("rtnData", iMAQaService.selectQaList(iMAQaDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/im/ima/IMAQaListAjax";
    }

    /**
     * 1:1 문의 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getQaWritePage(IMAQaDTO iMAQaDTO, ModelMap modelMap, COAAdmDTO cOAAdmDTO) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(iMAQaDTO.getDetailsKey())){
                modelMap.addAttribute("rtnQaInfo", iMAQaService.selectQaDtl(iMAQaDTO));
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/im/ima/IMAQaWrite.admin";
    }

    /**
     * 1:1 문의 등록 페이지
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String boardInsertPage(IMAQaDTO iMAQaDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

            iMAQaDTO.setRegId(coaAdmDTO.getId());
            iMAQaDTO.setRegIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", iMAQaService.insertQa(iMAQaDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

}