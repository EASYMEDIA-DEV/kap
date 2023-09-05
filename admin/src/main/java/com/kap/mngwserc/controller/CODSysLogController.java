package com.kap.mngwserc.controller;

import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.COCodeService;
import com.kap.service.CODSysLogService;
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
 * 이용 로그 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: CODSysLogController.java
 * @Description		: 이용 로그 관리를 위한 Controller
 * @author 손태주
 * @since 2022.02.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.02.21		손태주				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/cod")
public class CODSysLogController  {

    //코드 서비스
    private final COCodeService cOCodeService;

    // 시스템 로그 서비스
    private final CODSysLogService cODSysLogService;

    /**
     * 이용 로그 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSysLogListPage(ModelMap modelMap, COSystemLogDTO pCOSystemLogDTO) throws Exception {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("SYSTEM_LOG");
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", pCOSystemLogDTO);
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
        return "mngwserc/co/cod/CODSysLogList.admin";
    }

    /**
     * 이용로그 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectSysLogListPageAjax(COSystemLogDTO pCOSystemLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cODSysLogService.selectSysLogList(pCOSystemLogDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/cod/CODSysLogListAjax";
    }

    /**
     * 이용로그 목록 엑셀다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectSysLogListExcel(COSystemLogDTO pCOSystemLogDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            pCOSystemLogDTO.setExcelYn("Y");
            // 목록 조회
            COSystemLogDTO newSystemLogDTO = cODSysLogService.selectSysLogList( pCOSystemLogDTO );
            //엑셀 생성
            cODSysLogService.excelDownload(newSystemLogDTO, response);
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
