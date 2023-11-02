package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COSeqGnrDTO;
import com.kap.service.COGCntsService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * CMS 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COGCntsController.java
 * @Description		: CMS 관리를 위한 Controller
 * @author 임서화
 * @since 2023.09.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.07		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/contentsid/{menuSeq}")
public class COGCntsController {

    //CMS 서비스
    private final COGCntsService cogCntsService;


    /**
     * CMS 목록 페이지
     */
    @GetMapping(value="/list")
    public String getCntsListPage(COGCntsDTO cogCntsDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("rtnData", cogCntsDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/co/cog/COGCntsList.admin";
    }

    /**
     * CMS 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectCntsListPageAjax(COGCntsDTO cogCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cogCntsService.selectCntsList(cogCntsDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/cog/COGCntsListAjax";
    }

    /**
     * CMS 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getCntsWritePage(COGCntsDTO cOGCntsDTO, ModelMap modelMap, COAAdmDTO cOAAdmDTO, @PathVariable int menuSeq) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cOGCntsDTO.setMenuSeq(menuSeq);
            if(!"".equals(cOGCntsDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", cogCntsService.selectCntsDtl(cOGCntsDTO));
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

        return "mngwserc/co/cog/COGCntsWrite.admin";
    }

    /**
     * CMS 등록 페이지
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String CntsInsertPage(COGCntsDTO cogCntsDTO, ModelMap modelMap, @PathVariable int menuSeq, COSeqGnrDTO cOSeqGnrDTO) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cogCntsDTO.setMenuSeq(menuSeq);

            cogCntsDTO.setRegId(coaAdmDTO.getId());
            cogCntsDTO.setRegIp(coaAdmDTO.getLoginIp());
            cogCntsDTO.setModId(coaAdmDTO.getId());
            cogCntsDTO.setModIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", cogCntsService.insertCnts(cogCntsDTO));
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

    /**
     * CMS를 수정한다.
     */
    @PostMapping(value="/update")
    public String updateCnts(COGCntsDTO cogCntsDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

            cogCntsDTO.setRegId(coaAdmDTO.getId());
            cogCntsDTO.setRegIp(coaAdmDTO.getLoginIp());
            cogCntsDTO.setModId(coaAdmDTO.getId());
            cogCntsDTO.setModIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", cogCntsService.updateCnts(cogCntsDTO));
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
    
    /**
     * CMS를 삭제한다.
     */
    @PostMapping(value="/delete")
    public String deleteCnts(COGCntsDTO cogCntsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", cogCntsService.deleteCnts(cogCntsDTO));
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