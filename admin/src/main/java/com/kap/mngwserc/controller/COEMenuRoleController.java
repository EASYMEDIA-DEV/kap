package com.kap.mngwserc.controller;

import com.kap.core.dto.COEMenuRoleDTO;
import com.kap.service.COEMenuRoleService;
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
 * 메뉴권한변경 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COEMenuRoleController.java
 * @Description		: 메뉴권한변경 관리를 위한 Controller
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.14		신혜정				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/coe")
public class COEMenuRoleController {

    private final COEMenuRoleService cOEMenuRoleService;


    /**
     * 메뉴권한변경 목록
     * */
    @GetMapping(value = "/list")
    public String getMenuRoleLogList(COEMenuRoleDTO pCoMenuRoleLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", pCoMenuRoleLogDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coe/COEMenuRoleList.admin";
    }

    /**
     * 접속로그 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectMenuRoleLogListPageAjax(COEMenuRoleDTO pCoMenuRoleLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOEMenuRoleService.selectMenuRoleLogList( pCoMenuRoleLogDTO ));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coe/COEMenuRoleListAjax";
    }

    /**
     * 메뉴권한변경 상세
     * */
    @GetMapping(value = "/write")
    public String getMenuRoleLogWrite(COEMenuRoleDTO pCoMenuRoleLogDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", cOEMenuRoleService.selectMenuRoleLogWrite( pCoMenuRoleLogDTO ));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coe/COEMenuRoleWrite.admin";
    }

    /**
     * 접속로그 목록 엑셀다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectMenuRoleLogListExcel(COEMenuRoleDTO pCoMenuRoleLogDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            pCoMenuRoleLogDTO.setExcelYn("Y");

            // 목록 조회
            COEMenuRoleDTO newCoMenuRoleLogDTO = cOEMenuRoleService.selectMenuRoleLogList( pCoMenuRoleLogDTO );
            //엑셀 생성
            cOEMenuRoleService.excelDownload(newCoMenuRoleLogDTO, response);
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
