package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COGBoardDTO;
import com.kap.service.COCodeService;
import com.kap.service.COGBoardService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 게시판 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COGBoardController.java
 * @Description		: 게시판 관리를 위한 Controller
 * @author 임서화
 * @since 2023.09.18
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.18		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/co/cog/board/{typeCd}")
public class COGBoardController {

    //게시판 서비스
    private final COGBoardService cOGBoardService;
    //코드 서비스
    private final COCodeService cOCodeService;

    /**
     * 게시판 목록 페이지
     */
    @GetMapping(value="/list")
    public String getBoardListPage(COGBoardDTO cogBoardDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable int typeCd, @PathVariable String langCd) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("BOARD_TYPE_CD");

            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("typeCd", typeCd);

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cogBoardDTO);
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

        return "mngwserc/co/cog/COGBoardList.admin";
    }

    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectBoardListPageAjax(COGBoardDTO cogBoardDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable int typeCd, @PathVariable String langCd) throws Exception
    {
        try
        {
            cogBoardDTO.setLangCd(langCd);
            cogBoardDTO.setTypeCd(typeCd);

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOGBoardService.selectBoardList(cogBoardDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/cog/COGBoardListAjax";
    }

    /**
     * 게시판 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getBoardWritePage(COGBoardDTO cogBoardDTO, ModelMap modelMap, COAAdmDTO cOAAdmDTO, @PathVariable int typeCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cogBoardDTO.setTypeCd(typeCd);
            if(typeCd == 30) {
                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                // 코드 set
                cdDtlList.add("FAQ_TYPE_CD");

                // 정의된 코드id값들의 상세 코드 맵 반환
                modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            }
            if(!"".equals(cogBoardDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", cOGBoardService.selectBoardDtl(cogBoardDTO));
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

        return "mngwserc/co/cog/COGBoardWrite.admin";
    }

    /**
     * 게시판 등록 페이지
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String boardInsertPage(COGBoardDTO cogBoardDTO, ModelMap modelMap, @PathVariable int typeCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cogBoardDTO.setTypeCd(typeCd);

            cogBoardDTO.setRegId(coaAdmDTO.getId());
            cogBoardDTO.setRegIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", cOGBoardService.insertBoard(cogBoardDTO));
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
     * 게시판 글을 수정한다.
     */
    @PostMapping(value="/update")
    public String updateBoard(COGBoardDTO cogBoardDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, COFileDTO coFileDTO) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cogBoardDTO.setModId(coaAdmDTO.getId());
            cogBoardDTO.setModIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", cOGBoardService.updateBoard(cogBoardDTO));
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
     * 게시판 글을 삭제한다.
     */
    @PostMapping(value="/delete")
    public String deleteBoard(COGBoardDTO cogBoardDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", cOGBoardService.deleteBoard(cogBoardDTO));
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