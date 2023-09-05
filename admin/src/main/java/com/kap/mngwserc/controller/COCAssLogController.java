package com.kap.mngwserc.controller;

import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.COCAssLogService;
import com.kap.service.COCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <pre>
 * 접속로그 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COCAssLogController.java
 * @Description		: 접속로그 관리를 위한 Controller
 * @author 신혜정
 * @since 2022.04.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.08		신혜정				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/coc")
public class COCAssLogController  {

    //코드 서비스
    private final COCodeService cOCodeService;

    //서비스
    private final COCAssLogService cOCAssLogService;

    /**
     * 접속로그 목록
     * */
    @GetMapping(value = "/list")
    public String getAssLogList(COSystemLogDTO pCoSystemLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("SYSTEM_LOG");
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", pCoSystemLogDTO);
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
        return "mngwserc/co/coc/COCAssLogList.admin";
    }

    /**
     * 접속로그 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectAssLogListPageAjax(COSystemLogDTO pCoSystemLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOCAssLogService.selectAssLogList( pCoSystemLogDTO ));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coc/COCAssLogListAjax";
    }

    /**
     * 접속로그 목록 엑셀다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectAssLogListExcel(COSystemLogDTO pCoSystemLogDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            pCoSystemLogDTO.setExcelYn("Y");
            // 목록 조회
            COSystemLogDTO newSystemLogDTO = cOCAssLogService.selectAssLogList( pCoSystemLogDTO );
            //엑셀 생성
            cOCAssLogService.excelDownload(newSystemLogDTO, response);
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