package com.kap.mngwserc.controller.wb.wba;


import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManageInsertDTO;
import com.kap.core.dto.wb.wba.WBAManageSearchDTO;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 상생사업 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBAManagementController.java
 * @Description		: 상생사업 관리를 위한 Controller
 * @author 김태훈
 * @since 2023.11.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.10		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wba")
public class WBAManagementController {

    /** 서비스 **/
    private final WBAManagementService wbaManagementService;
    public final COCodeService cOCodeService;

    //서비스
    private final COBUserMenuService cOBUserMenuService;

    /**
     * 상생사업관리 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getManagementListPage(WBAManageSearchDTO wbaManageSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("BM_CODE");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", wbaManagementService.selectManagementList(wbaManageSearchDTO));

        return "mngwserc/wb/wba/WBAManagementList.admin";
    }

    /**
     * 상생사업관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectManagementPageAjax(WBAManageSearchDTO wbaManageSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbaManagementService.selectManagementList(wbaManageSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wba/WBAManagementListAjax";
    }

    /**
     * 등록/수정 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getManagementWritePage(WBAManageSearchDTO wbaManageSearchDTO, ModelMap modelMap
            ,@Parameter(description = "상생 사업 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("BM_CODE");
            cdDtlList.add("INQUIRY_TYPE");

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(detailsKey != null){
                //상세조회
                modelMap.addAttribute("rtnData", wbaManagementService.selectManagementDtl( wbaManageSearchDTO ));
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

        return "mngwserc/wb/wba/WBAManagementWrite.admin";
    }


    @Operation(summary = "상생 사업 마스터 중복체크", tags = "상생 사업 마스터 중복체크", description = "상생단계 마스터 중복체크")
    @PostMapping(value="/duplication")
    @ResponseBody
    public int duplication(@Valid WBAManageSearchDTO wbaManageSearchDTO) throws Exception {

        int rtnCnt = 0;

        try {

            rtnCnt = wbaManagementService.selectManagementCnt(wbaManageSearchDTO);

        } catch (Exception e) {

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return rtnCnt;
    }

    @Operation(summary = "상생 사업 신청자 여부체크", tags = "상생 사업 신청자 여부체크", description = "상생 사업 신청자 여부체크")
    @PostMapping(value="/applyCount")
    @ResponseBody
    public int applyCount(@Valid WBAManageSearchDTO wbaManageSearchDTO) throws Exception {

        int rtnCnt = 0;

        try {

            rtnCnt = wbaManagementService.selecApplyCnt(wbaManageSearchDTO);

        } catch (Exception e) {

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return rtnCnt;
    }

    @Operation(summary = "상생 사업 마스터 등록", tags = "상생 사업 마스터 등록", description = "상생단계 마스터 생성")
    @PostMapping(value="/insert")
    @ResponseBody
    public WBAManageInsertDTO insert(@Valid @RequestBody WBAManageInsertDTO wbaManageInsertDTO, HttpServletRequest request) throws Exception {

        try {

            wbaManageInsertDTO = wbaManagementService.insertManagement(wbaManageInsertDTO, request);

        } catch (Exception e) {

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wbaManageInsertDTO;
    }

    @Operation(summary = "상생 사업 마스터 수정", tags = "상생 사업 마스터 수정", description = "상생단계 마스터 수정")
    @PostMapping(value="/update")
    @ResponseBody
    public WBAManageInsertDTO update(@Valid @RequestBody WBAManageInsertDTO wbaManageInsertDTO, HttpServletRequest request) throws Exception {

        try {

            wbaManageInsertDTO = wbaManagementService.updateManagement(wbaManageInsertDTO, request);

        } catch (Exception e) {

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wbaManageInsertDTO;
    }

    @Operation(summary = "상생 사업 삭제", tags = "상생 사업 삭제", description = "상생사업 마스터, 단계 상세, 옵션 상세 삭제")
    @PostMapping(value="/delete")
    @ResponseBody
    public WBAManageInsertDTO delete(WBAManageInsertDTO wbaManageInsertDTO) throws Exception
    {
        try
        {
            wbaManageInsertDTO.setRespCnt( wbaManagementService.deleteManagement(wbaManageInsertDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wbaManageInsertDTO;
    }

    @Operation(summary = "상생 사업 파일 연결", tags = "상생 사업 파일 연결", description = "상생 사업 파일 연결")
    @PostMapping(value="/updateFile")
    @ResponseBody
    public WBAManageInsertDTO updateFileInfo(WBAManageInsertDTO wbaManageInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            wbaManagementService.updateFileInfo(wbaManageInsertDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wbaManageInsertDTO;
    }

    @RequestMapping(value="/select", method=RequestMethod.POST)
    public void getMenuList(COMenuDTO cOMenuDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            cOMenuDTO.setIsChkd("N");
            List<COMenuDTO> menuList = cOBUserMenuService.getMenuList(cOMenuDTO);
            int startNum = 0, paramSeq = cOMenuDTO.getMenuSeq();
            JSONArray jSONArray = cOBUserMenuService.getJsonData(menuList, startNum, paramSeq);
            out.print(jSONArray);
            jSONArray = null;
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        finally
        {
            out.close();
        }
    }
}
