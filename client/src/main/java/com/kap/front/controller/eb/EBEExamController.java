package com.kap.front.controller.eb;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptMst;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptRspnMst;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBBEpisdService;
import com.kap.service.EBEExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <pre>
 * 교육 시험 평가 Controller
 * </pre>
 *
 * @ClassName		: EBEExamController.java
 * @Description		: 교육 시험 평가 Controller
 * @author 김학규
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "MY-PAGE > 교육시험평가", description = "교육 시험 평가")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/education")
public class EBEExamController {

    /** 서비스 **/
    private final EBEExamService eBEExamService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    public final EBBEpisdService eBBEpisdService;
    /**
     * 시험 상세
     */
    @GetMapping(value="/exam")
    public String getUserExamDtl(@RequestParam(required = true) int ptcptSeq, ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/EBEExamPage.front";
        try
        {
            EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(ptcptSeq);
            //시험 조건
            if(eXGExamEdctnPtcptMst == null || (eXGExamEdctnPtcptMst != null && (eXGExamEdctnPtcptMst.getExamSeq() == null || eXGExamEdctnPtcptMst.getPtcptCnt() > 0))){
                vwUrl = "front/COBlank.error";
                modelMap.addAttribute("msg", ErrorCode.CANNOT_READ.getMessage());
                modelMap.addAttribute("url", "/my-page/main");
                return vwUrl;
            }
            modelMap.addAttribute("rtnData", eXGExamEdctnPtcptMst);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

    /**
     * 시험 참여
     */
    @GetMapping(value="/exam/participation")
    public String getUserExamParticipation(@RequestParam(required = true) int ptcptSeq, ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/EBEExamDtl.front";
        try
        {
            EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(ptcptSeq);
            //시험 조건
            if(eXGExamEdctnPtcptMst == null || (eXGExamEdctnPtcptMst != null && (eXGExamEdctnPtcptMst.getExamSeq() == null || eXGExamEdctnPtcptMst.getPtcptCnt() > 0))){
                vwUrl = "front/COBlank.error";
                modelMap.addAttribute("msg", ErrorCode.CANNOT_READ.getMessage());
                modelMap.addAttribute("url", "/my-page/main");
                return vwUrl;
            }
            //시험 항목 조회
            EXGExamMstSearchDTO eXGExamMstSearchDTO = new EXGExamMstSearchDTO();
            eXGExamMstSearchDTO.setDetailsKey( String.valueOf(eXGExamEdctnPtcptMst.getExamSeq()) );
            modelMap.addAttribute("rtnData", eXGExamEdctnPtcptMst);
            modelMap.addAttribute("rtnExamData", eBEExamService.selectExamDtl(eXGExamMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }
    /**
     * 시험 참여 완료
     */
    @GetMapping(value="/exam/complete")
    public String getUserExamParticipationComplete(@RequestParam(required = true) int ptcptSeq, ModelMap modelMap, HttpServletResponse response) throws Exception
    {
        String vwUrl = "front/eb/EBEExamComplete.front";
        try
        {

            EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(ptcptSeq);
            //시험 항목 조회
            EXGExamMstSearchDTO eXGExamMstSearchDTO = new EXGExamMstSearchDTO();
            eXGExamMstSearchDTO.setDetailsKey( String.valueOf(eXGExamEdctnPtcptMst.getExamSeq()) );

            EBBEpisdDTO rtnDto = new EBBEpisdDTO();
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            rtnDto.setPtcptSeq(ptcptSeq);
            rtnDto.setMemSeq(cOLoginUserDTO.getSeq());
            //수료여부 체크
            eBBEpisdService.setCmptnChk(rtnDto);


            modelMap.addAttribute("rtnData", eXGExamEdctnPtcptMst);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }
    /**
     * @ClassName		: EBEExamRestController.java
     * @Description		: MY-PAGE > 시험 답변 등록
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.11.10			김학규			 		최초생성
     * </pre>
     */
    @Tag(name = "교육 시험 평가 응답 API", description = "교육 답변 등록")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/my-page/education/exam")
    public class EBEExamRestController  {
        /** 서비스 **/
        private final EBEExamService eBEExamService;
        @Operation(summary = "교육 답변 등록", tags = "평가지", description = "교육 답변 등록")
        @PostMapping(value="/insert")
        public EXGExamEdctnPtcptRspnMst insertExamSbjctReply(@RequestBody @Valid  EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, HttpServletRequest request) throws Exception
        {
            try
            {
                EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(eXGExamEdctnPtcptRspnMst.getPtcptSeq());
                //시험 조건
                if(eXGExamEdctnPtcptMst == null){
                    throw new BusinessException(ErrorCode.CANNOT_READ);
                }
                //시험 매핑
                if(eXGExamEdctnPtcptMst.getExamSeq() == null){
                    throw new BusinessException(ErrorCode.CANNOT_READ);
                }
                //시험 여부
                if(eXGExamEdctnPtcptMst.getPtcptCnt() > 0){
                    throw new BusinessException(ErrorCode.CANNOT_READ);
                }
                //채점
                eXGExamEdctnPtcptRspnMst.setRespCnt( eBEExamService.insertEdctnRspn(eXGExamEdctnPtcptRspnMst, eXGExamEdctnPtcptMst, request) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            return eXGExamEdctnPtcptRspnMst;
        }



    }
}

