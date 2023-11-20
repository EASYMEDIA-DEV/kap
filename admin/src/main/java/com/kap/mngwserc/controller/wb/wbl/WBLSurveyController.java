package com.kap.mngwserc.controller.wb.wbl;

import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
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
import javax.validation.Valid;
import java.util.ArrayList;

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


    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSurveyListPage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", wBLSurveyMstSearchDTO);

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
     * 쓰기 페이지
     */
    @GetMapping(value="/write")
    public String getSurveyWritePage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap , @Parameter(description = "상생협력체감조조사 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception{
        try
        {

            modelMap.addAttribute("rtnSrchData", wBLSurveyMstSearchDTO);
            if(detailsKey != null){
//                modelMap.addAttribute("rtnData", sVASurveyService.selectSurveyDtl( sVASurveyDTO ));
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
//    public SVASurveyMstInsertDTO insertSurveyList(@Valid @RequestBody SVASurveyMstInsertDTO sVASurveyMstDTO, HttpServletRequest request ) throws Exception
    public String insertSurveyList(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
//            sVASurveyService.insertSurveyList(sVASurveyMstDTO, request);
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
//        return sVASurveyMstDTO;
        return "jsonView";
    }

    /**
     * 리스트 삭제
     */
    @Operation(summary = "상생협력체감도조사 삭제", tags = "상생협력체감도조사", description = "")
    @PostMapping(value="/delete")
//    public SVASurveyMstSearchDTO deleteSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception
    public String deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO , ModelMap modelMap) throws Exception
    {
        try
        {
//            sVASurveyDTO.setRespCnt(sVASurveyService.deleteSurveyList(sVASurveyDTO) );
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
//        return sVASurveyDTO;
        return "jsonView";
    }
}