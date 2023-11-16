package com.kap.mngwserc.controller.sv;

import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.SVASurveyService;
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
 * @Description		: 설문조사 Controller
 * @author 박준희
 * @since 2023.10.31
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.31		박준희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sv/sva")
public class SVASurveyController<sVASurveyMstDTO> {

    private final COCodeService cOCodeService;

    private final SVASurveyService sVASurveyService;


    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSurveyListPage(SVASurveyMstSearchDTO sVASurveyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("SURVEY_TYPE");
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", sVASurveyDTO);
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList , "2"));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sv/sva/SVASurveyList.admin";
    }

    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectSurveyListPageAjax(SVASurveyMstSearchDTO sVASurveyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", sVASurveyService.selectSurveyList(sVASurveyDTO));
            modelMap.addAttribute("searchDto", sVASurveyDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sv/sva/SVASurveyListAjax";
    }

    /**
     * 쓰기 페이지
     */
    @GetMapping(value="/write")
    public String getSurveyWritePage(SVASurveyMstSearchDTO sVASurveyDTO, ModelMap modelMap , @Parameter(description = "설문 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception{
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdOncDtlList = new ArrayList<String>();
            // 코드 set
            cdOncDtlList.add("SURVEY_TYPE");
            cdOncDtlList.add("QUESTION_TYPE");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("cdOncDtlList", cOCodeService.getCmmCodeBindAll(cdOncDtlList , "2"));
            modelMap.addAttribute("cdTwoDtlList", sVASurveyService.getSurveyTypeList());

            modelMap.addAttribute("rtnSrchData", sVASurveyDTO);
            if(detailsKey != null){
                modelMap.addAttribute("rtnData", sVASurveyService.selectSurveyDtl( sVASurveyDTO ));
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

        return "mngwserc/sv/sva/SVASurveyWrite.admin";
    }


    @Operation(summary = "설문 등록", tags = "", description = "설문 마스터, 질문 상세, 보기 상세 생성")
    @PostMapping(value="/insert")
//    public SVASurveyMstInsertDTO insertSurveyList(@Valid @RequestBody SVASurveyMstInsertDTO sVASurveyMstDTO, HttpServletRequest request ) throws Exception
    public String insertSurveyList(@Valid @RequestBody SVASurveyMstInsertDTO sVASurveyMstDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
//            sVASurveyService.insertSurveyList(sVASurveyMstDTO, request);
            modelMap.addAttribute("respCnt", sVASurveyService.insertSurveyList(sVASurveyMstDTO, request));

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
    @Operation(summary = "설문 삭제", tags = "설문", description = "설문 마스터, 설문 상세, 설문 보기 상세 삭제")
    @PostMapping(value="/delete")
//    public SVASurveyMstSearchDTO deleteSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception
    public String deleteSurveyList(SVASurveyMstSearchDTO sVASurveyDTO , ModelMap modelMap) throws Exception
    {
        try
        {
//            sVASurveyDTO.setRespCnt(sVASurveyService.deleteSurveyList(sVASurveyDTO) );
            modelMap.addAttribute("respCnt", sVASurveyService.deleteSurveyList(sVASurveyDTO));
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