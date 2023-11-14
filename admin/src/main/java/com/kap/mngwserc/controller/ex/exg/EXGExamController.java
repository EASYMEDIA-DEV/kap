package com.kap.mngwserc.controller.ex.exg;

import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.service.COCodeService;
import com.kap.service.EXGExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @ClassName		: EXGExamController.java
 * @Description		: 교육사업관리 > 시험관리
 * @author 김학규
 * @since 2023.11.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.08		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육 시험 평가 페이지", description = "교육 시험 평가 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/ex/exg")
public class EXGExamController {
    /** 서비스 **/
    private final EXGExamService eXGExamService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /**
     * 시험지 조회
     */
    @GetMapping(value="/list")
    public String getExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", EXGExamMstSearchDTO);
        return "mngwserc/ex/exg/EXGExamList.admin";
    }
    /**
     * 시험지 목록 AJAX
     */
    @GetMapping(value="/select")
    public String getExamAjaxList(EXGExamMstSearchDTO EXGExamMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eXGExamService.selectExamList(EXGExamMstSearchDTO));
            modelMap.addAttribute("searchDto", EXGExamMstSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/ex/exg/EXGExamListAjax";
    }
    /**
     * 시험지 작성
     */
    @GetMapping(value="/write")
    public String getWrite(EXGExamMstSearchDTO eXGExamMstSearchDTO, ModelMap modelMap
                         , @Parameter(description = "교육 시험 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EXG");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnSrchData", eXGExamMstSearchDTO);
            if(detailsKey != null){
                //상세조회
                modelMap.addAttribute("rtnData", eXGExamService.selectExamDtl( eXGExamMstSearchDTO ));
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
        return "mngwserc/ex/exg/EXGExamWrite.admin";
    }
    /**
     * @ClassName		: EXGExamRestController.java
     * @Description		: 교육사업관리 > 시험관리 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.11.10			김학규			 		최초생성
     * </pre>
     */
    @Tag(name = "교육 시험 평가 API", description = "교육 시험 평가 생성,수정,삭제")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/mngwserc/{langCd}/ex/exg")
    public class EXGExamRestController  {
        /** 서비스 **/
        private final EXGExamService eXGExamService;

        @Operation(summary = "교육회차마스터 매핑 여부", tags = "평가지", description = "교육회차마스터 매핑 여부 확인")
        @PostMapping(value="/getExamEdctnEpisdCnt")
        public EXGExamMstSearchDTO getExamEdctnEpisdCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception
        {
            try
            {
                EXGExamMstSearchDTO.setRespCnt( eXGExamService.getExamEdctnEpisdCnt(EXGExamMstSearchDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return EXGExamMstSearchDTO;
        }
        /**
         * 리스트 삭제
         */
        @Operation(summary = "교육 시험 삭제", tags = "평가지", description = "교육 마스터, 질문 상세, 보기 상세 삭제")
        @PostMapping(value="/delete")
        public EXGExamMstSearchDTO deleteExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception
        {
            try
            {
                EXGExamMstSearchDTO.setRespCnt( eXGExamService.deleteExamList(EXGExamMstSearchDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return EXGExamMstSearchDTO;
        }

        @Operation(summary = "교육 시험 등록", tags = "평가지", description = "교육 마스터, 질문 상세, 보기 상세 생성")
        @PostMapping(value="/insert")
        public EXGExamMstInsertDTO insertExamList(@Valid @RequestBody EXGExamMstInsertDTO eXGExamMstTO, HttpServletRequest request) throws Exception
        {
            try
            {
                eXGExamService.insertExamList(eXGExamMstTO, request);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eXGExamMstTO;
        }

        @Operation(summary = "교육 시험 수정", tags = "평가지", description = "교육 마스터, 질문 상세, 보기 상세 생성")
        @PostMapping(value="/update")
        public EXGExamMstInsertDTO updateExamList(@Valid @RequestBody EXGExamMstInsertDTO eXGExamMstTO, HttpServletRequest request) throws Exception
        {
            try
            {
                eXGExamService.updateExamList(eXGExamMstTO, request);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eXGExamMstTO;
        }

    }
}