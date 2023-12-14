package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBEExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 교육과정관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육 시험 평가 페이지", description = "교육 시험 평가 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebe")
public class EBEExamController {

    /** 서비스 **/
    private final EBEExamService eBEExamService;
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
            modelMap.addAttribute("rtnData", eBEExamService.selectExamList(EXGExamMstSearchDTO));
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

            // 코드 set
            cdDtlList.add("CLASS_TYPE");

            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            if(detailsKey != null){
                //상세조회
                modelMap.addAttribute("rtnData", eBEExamService.selectExamDtl( eXGExamMstSearchDTO ));
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
    @RequestMapping("/mngwserc/eb/ebe")
    public class EXGExamRestController  {
        /** 서비스 **/
        private final EBEExamService eBEExamService;

        @Operation(summary = "교육회차마스터 매핑 여부", tags = "평가지", description = "교육회차마스터 매핑 여부 확인")
        @PostMapping(value="/getExamEdctnEpisdCnt")
        public EXGExamMstSearchDTO getExamEdctnEpisdCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception
        {
            try
            {
                EXGExamMstSearchDTO.setRespCnt( eBEExamService.getExamEdctnEpisdCnt(EXGExamMstSearchDTO) );
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
                EXGExamMstSearchDTO.setRespCnt( eBEExamService.deleteExamList(EXGExamMstSearchDTO) );
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
                eBEExamService.insertExamList(eXGExamMstTO, request);
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
                eBEExamService.updateExamList(eXGExamMstTO, request);
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

        /**
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/classTypeList")
        public List<COCodeDTO> classTypeList(@RequestBody COCodeDTO cOCodeDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            List<COCodeDTO> detailList = null;
            try
            {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }

    }

}

