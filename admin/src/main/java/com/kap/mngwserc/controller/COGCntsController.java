package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.service.COGCntsService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value="/mngwserc/contentsid/{menuSeq}")
public class COGCntsController {

    //CMS 서비스
    private final COGCntsService pCOGCntsService;


    /**
     * CMS 목록 페이지
     */
    @GetMapping(value="/list")
    public String getCntsListPage(COGCntsDTO pCOGCntsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", pCOGCntsDTO);

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
    public String selectCntsListPageAjax(COGCntsDTO pCOGCntsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", pCOGCntsService.selectCntsList(pCOGCntsDTO));
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
    @GetMapping(value="/write")
    public String getCntsWritePage(COGCntsDTO pCOGCntsDTO, ModelMap modelMap, @PathVariable int menuSeq) throws Exception
    {
        try
        {
            pCOGCntsDTO.setMenuSeq(menuSeq);
            
            if(pCOGCntsDTO.getDetailsKey() != null && !pCOGCntsDTO.getDetailsKey().isEmpty()){
                modelMap.addAttribute("rtnInfo", pCOGCntsService.selectCntsDtl(pCOGCntsDTO));
            }
            else {
                modelMap.addAttribute("rtnInfo", pCOGCntsService.selectNewVer(pCOGCntsDTO));
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
     * 컨텐츠 등록
     */
    @PostMapping(value="/insert")
    public String CntsInsert(COGCntsDTO pCOGCntsDTO, ModelMap modelMap, @PathVariable int menuSeq) throws Exception
    {
        try
        {
            pCOGCntsDTO.setMenuSeq(menuSeq);
            modelMap.addAttribute("respCnt", pCOGCntsService.insertCnts(pCOGCntsDTO));
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
     * 컨텐츠 수정
     */
    @PostMapping(value="/update")
    public String updateCnts(COGCntsDTO pCOGCntsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", pCOGCntsService.updateCnts(pCOGCntsDTO));
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
     * 컨텐츠 삭제
     */
    @PostMapping(value="/delete")
    public String deleteCnts(COGCntsDTO pCOGCntsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", pCOGCntsService.deleteCnts(pCOGCntsDTO));
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
     * CMS 즉시 배포 시 게시글 상태 값 체크
     */
    @PostMapping(value="/state-check")
    public String getPrcsCdAjax(COGCntsDTO pCOGCntsDTO, ModelMap modelMap, @PathVariable("menuSeq") int menuSeq) throws Exception
    {
        try
        {
            pCOGCntsDTO.setMenuSeq(menuSeq);

            COGCntsDTO rtnData = pCOGCntsService.selectCntsDtl(pCOGCntsDTO);

            String prcsCd = rtnData.getPrcsCd();

            if(prcsCd != null && !"".equals(prcsCd))
            {
                modelMap.addAttribute("prcsCd", prcsCd);
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

        return "jsonView";
    }

    /**
     * 컨텐츠 배포
     */
    @PostMapping(value="/aprvl-update")
    public String updateCntsAprvl(COGCntsDTO pCOGCntsDTO, ModelMap modelMap, @PathVariable("menuSeq") int menuSeq) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            pCOGCntsDTO.setModId(coaAdmDTO.getId());
            pCOGCntsDTO.setModIp(coaAdmDTO.getLoginIp());

            if ("99".equals(coaAdmDTO.getAuthCd()))
            {
                pCOGCntsDTO.setMenuSeq(menuSeq);

                pCOGCntsService.updateCntsAprvl(pCOGCntsDTO);

                modelMap.addAttribute("status", "Y");
            }
            else
            {
                modelMap.addAttribute("status", "N");
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

        return "jsonView";
    }

    /**
     * 컨텐츠 복사
     */
    @PostMapping(value="/copy")
    public String insertCntsCopy(COGCntsDTO pCOGCntsDTO, ModelMap modelMap, @PathVariable("menuSeq") int menuSeq) throws Exception
    {
        try
        {
            pCOGCntsDTO.setMenuSeq(menuSeq);

            pCOGCntsService.insertCntsCopy(pCOGCntsDTO);

            modelMap.addAttribute("msg", "복사되었습니다.");
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