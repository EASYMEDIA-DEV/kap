package com.kap.mngwserc.controller.wb.wbl;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.SVASurveyService;
import com.kap.service.WBLSurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 설문조사 Controller
 * </pre>
 *
 * @ClassName		: SVASurveyController.java
 * @Description		: 상생협력체감도조사 Controller
 * @author 박준희
 * @since 2023.10.31
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		박준희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbl")
public class WBLSurveyController<sVASurveyMstDTO> {

    private final COCodeService cOCodeService;

    private final WBLSurveyService wLSurveyService;

    private final SVASurveyService sVSurveyService;


    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSurveyListPage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PTCPT_CD");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", wBLSurveyMstSearchDTO);
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
            List<String> typeCdList = Arrays.asList(new String[]{ "WIN" });
            List<String> expsYnList = Arrays.asList(new String[]{ "Y" });
            sVASurveyDTO.setTypeCdList(typeCdList);
            sVASurveyDTO.setExpsYnList(expsYnList);

            modelMap.addAttribute("rtnSurveyData", sVSurveyService.selectSurveyList(sVASurveyDTO));

            WBLEpisdMstDTO wBLEpisdMstDTO = new WBLEpisdMstDTO();
            modelMap.addAttribute("rtnEpisdSurveyData", wLSurveyService.selectEpisdSurveyList(wBLEpisdMstDTO));


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/wb/wbl/WBLSurveyList.admin";
    }

    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectSurveyListPageAjax(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wLSurveyService.selectSurveyList(wBLSurveyMstSearchDTO));
            modelMap.addAttribute("searchDto", wBLSurveyMstSearchDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbl/WBLSurveyListAjax";
    }


    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/selectEpisd")
    public String selectEpisdListPageAjax(WBLEpisdMstDTO wBLEpisdMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnEpisdData", wLSurveyService.selectEpisdList(wBLEpisdMstDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbl/WBLEpisdLayerAjax";
    }
    /**
     * 쓰기 페이지
     */
    @GetMapping(value="/write")
    public String getSurveyWritePage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap , @Parameter(description = "상생협력체감조조사 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception{
        try
        {

            modelMap.addAttribute("rtnSrchData", wBLSurveyMstSearchDTO);

            WBLEpisdMstDTO wBLEpisdMstDTO = new WBLEpisdMstDTO();
            modelMap.addAttribute("rtnEpisdSurveyData", wLSurveyService.selectEpisdSurveyList(wBLEpisdMstDTO));


            if(detailsKey != null){

                WBLSurveyMstInsertDTO WBLSurveyMstInsertDTO = wLSurveyService.selectSurveyDtl(wBLSurveyMstSearchDTO);
                modelMap.addAttribute("rtnData", WBLSurveyMstInsertDTO);

                if (!"".equals(WBLSurveyMstInsertDTO.getSrvSeq()) && WBLSurveyMstInsertDTO.getSrvSeq() != null){
                    SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                    sVASurveyDTO.setDetailsKey(Integer.toString(WBLSurveyMstInsertDTO.getSrvSeq()));
                    sVASurveyDTO.setSrvRspnSeq(WBLSurveyMstInsertDTO.getSrvRspnSeq());

                    sVASurveyDTO.setTypeCd("WIN");
                    SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeWinDtl(sVASurveyDTO);
                    if (sVASurveyMstInsertDTO != null){
                        modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);
                    }
                }
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

        return "mngwserc/wb/wbl/WBLSurveyWrite.admin";
    }

    @Operation(summary = "상생협력체감도조사 등록", tags = "", description = "")
    @PostMapping(value="/insert")
    public String insertSurveyList(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.insertSurveyList(wBLSurveyMstInsertDTO, request));

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
     * 리스트 삭제
     */
    @Operation(summary = "상생협력체감도조사 삭제", tags = "상생협력체감도조사", description = "")
    @PostMapping(value="/delete")
    public String deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.deleteSurveyList(wBLSurveyMstSearchDTO));
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
     * 리스트 삭제
     */
    @Operation(summary = "회차 삭제", tags = "상생협력체감도조사", description = "")
    @PostMapping(value="/deleteEpisd")
    public String deleteEpisdList(@Valid @RequestBody WBLEpisdMstDTO wBLEpisdMstDTO , ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.deleteEpisdList(wBLEpisdMstDTO,request));
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

    @Operation(summary = "회차 등록", tags = "", description = "")
    @PostMapping(value="/insertEpisd")
    public String insertEpisdList(@Valid @RequestBody WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.insertEpisdList(wBLEpisdMstDTO, request));

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
     * 샘플 엑셀 생성
     */
    @GetMapping(value = "/excelSampleDownload")
    public void excelSampleDownload(COAAdmDTO cOAAdmDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wLSurveyService.excelSampleDownload(response);
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


    @Operation(summary = "대상자 등록", tags = "", description = "")
    @PostMapping(value="/insertExcel")
    public String insertExcelWinAplnList(@Valid @ModelAttribute WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.insertSurveyExcelList(wBLSurveyMstInsertDTO, request ));

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

    @Operation(summary = "응답 초기화", tags = "", description = "")
    @PostMapping(value="/updateSurveyRspn")
    public String updateSurveyRspn(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.updateSurveyRspn(wBLSurveyMstInsertDTO, request ));

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

    @GetMapping(value = "/excel-down")
    public void getSurveyListExcel(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            wBLSurveyMstSearchDTO.setExcelYn("Y");
            //엑셀 생성
            wLSurveyService.excelDownload(wLSurveyService.selectSurveyList(wBLSurveyMstSearchDTO), response);
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