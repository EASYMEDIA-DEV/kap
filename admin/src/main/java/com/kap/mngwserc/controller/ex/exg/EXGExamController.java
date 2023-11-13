package com.kap.mngwserc.controller.ex.exg;

import com.kap.core.dto.ex.exg.EXGExamMstDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.service.COCodeService;
import com.kap.service.EXGExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String getExamList(EXGExamMstDTO eXGExamMstDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", eXGExamMstDTO);
        return "mngwserc/ex/exg/EXGExamList.admin";
    }
    /**
     * 시험지 목록 AJAX
     */
    @GetMapping(value="/select")
    public String getExamAjaxList(EXGExamMstDTO eXGExamMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eXGExamService.selectExamList(eXGExamMstDTO));
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
    public String getWrite(EXGExamMstDTO eXGExamMstDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EXG");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", eXGExamMstDTO);
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
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/mngwserc/{langCd}/ex/exg")
    public class EXGExamRestController  {
        /** 서비스 **/
        private final EXGExamService eXGExamService;
        /**
         * 교육회차마스터 매핑 여부
         */
        @PostMapping(value="/getExamEdctnEpisdCnt")
        public EXGExamMstDTO getExamEdctnEpisdCnt(EXGExamMstDTO eXGExamMstDTO) throws Exception
        {
            try
            {
                eXGExamMstDTO.setRespCnt( eXGExamService.getExamEdctnEpisdCnt(eXGExamMstDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eXGExamMstDTO;
        }
        /**
         * 리스트 삭제
         */
        @PostMapping(value="/delete")
        public EXGExamMstDTO deleteExamList(EXGExamMstDTO eXGExamMstDTO) throws Exception
        {
            try
            {
                eXGExamMstDTO.setRespCnt( eXGExamService.deleteExamList(eXGExamMstDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eXGExamMstDTO;
        }

        @PostMapping(value="/insert")
        public EXGExamMstDTO insertExamList(@Valid @RequestBody EXGExamMstInsertDTO eXGExamMstTO, HttpServletRequest request) throws Exception
        {
            EXGExamMstDTO eXGExamMstDTO = null;
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
            return eXGExamMstDTO;
        }

    }
}