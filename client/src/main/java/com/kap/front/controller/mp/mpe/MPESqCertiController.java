package com.kap.front.controller.mp.mpe;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.common.utility.CONetworkUtil;
import com.kap.core.annotation.MapData;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COCodeService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBDSqCertiReqService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 홈 > 마이페이지 > SQ평가원 자격증 관리 Controller
 * </pre>
 *
 * @ClassName		: MPESqCertiController.java
 * @Description		: SQ평가원 자격증 관리 Controller
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
@Tag(name = "SQ평가원 자격증 신청관리", description = "SQ평가원 자격증 신청관리")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/sq-license")
public class MPESqCertiController {
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /** 서비스 **/
    private final EBDSqCertiReqService eBDSqCertiReqService;
    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;
    /**
     *  목록
     */
    @GetMapping(value="/list")
    public String getSqList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
            //자격증 신청 가능 확인
            //자격증이 없고 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 경우
            //SQ 평가원 자격증 신청 조건(자격증 연계를 수료하였고 평가원을 신청하지 않아야함)
            eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
            //SQ자격증 보기 버튼
            //SQ자격증이 중지 상태이면 아무것도 할 수 없다
            EBGExamAppctnMstDTO sqCertiMst = eBDSqCertiReqService.selectExamAppctnMst(eBDSqCertiSearchDTO);
            modelMap.addAttribute("sqCertiMst", sqCertiMst);
            if(sqCertiMst != null){
                //업종 조회
                eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
                eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
                modelMap.addAttribute("rtnCompletePrcsList", eBDSqCertiReqService.getEducationCompleteList(eBDSqCertiSearchDTO));
            }
            //SQ자격증 신청 버튼
            modelMap.addAttribute("posibleSqCertiCnt", eBDSqCertiReqService.getPosibleSqCertiCnt(eBDSqCertiSearchDTO));
            modelMap.addAttribute("educationCompleteListCnt", eBDSqCertiReqService.selectEducationCompleteListCnt(eBDSqCertiSearchDTO));

            modelMap.addAttribute("imageExtns", imageExtns);
            modelMap.addAttribute("atchUploadMaxSize", atchUploadMaxSize);

            modelMap.addAttribute("rtnData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpe/MPESqCertiList.front";
    }
    /**
     * 수료 교육 목록 AJAX
     */
    @GetMapping(value="/complete/list")
    public String getEducationCompleteListAjax(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //수료 교육
            eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
            eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
            modelMap.addAttribute("rtnData", eBDSqCertiReqService.getEducationCompleteList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("searchDto", eBDSqCertiSearchDTO);
            modelMap.addAttribute("searchType", "complete");
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpe/MPESqCertiListAjax";
    }
    /**
     * 보수 교육 목록 AJAX
     */
    @GetMapping(value="/repair/list")
    public String getEducationRepairListAjax(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //보수 교육
            eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT03");
            eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
            modelMap.addAttribute("rtnData", eBDSqCertiReqService.getEducationCompleteList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("searchDto", eBDSqCertiSearchDTO);
            modelMap.addAttribute("searchType", "repair");
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpe/MPESqCertiListAjax";
    }

    /**
     * SQ평가원 자격증 신청 폼
     */
    @GetMapping(value="/complete/apply")
    public String setEducationCompleteApply(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO coUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
            //자격증 신청 가능 확인
            //자격증이 없고 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 경우
            //SQ 평가원 자격증 신청 조건(자격증 연계를 수료하였고 평가원을 신청하지 않아야함)
            eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
            //SQ자격증 신청 버튼
            int posibleSqCertiCnt = eBDSqCertiReqService.getPosibleSqCertiCnt(eBDSqCertiSearchDTO);
            if("CP".equals(coUserDetailsDTO.getAuthCd()) && posibleSqCertiCnt == 1)
            {
                modelMap.addAttribute("rtnData", eBDSqCertiReqService.getEducationCompleteList(eBDSqCertiSearchDTO));
            }
            else
            {
                throw new Exception("NOT PERMIT");
            }

        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpe/MPESqCertiApplyAjax";
    }
    /**
     * <pre>
     * 홈 > 마이페이지 > SQ평가원 자격증 관리 RestController
     * </pre>
     *
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2020.10.20			허진영			 		최초생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/my-page/sq-license")
    public class MPESqCertiRestController {
        /** 서비스 **/
        private final EBDSqCertiReqService eBDSqCertiReqService;
        /**
         * SQ평가원 자격증 신청
         */
        @PostMapping(value="/complete/insert")
        public EBDSqCertiSearchDTO setSqCertiInsert(@Valid EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception
        {
            EBDSqCertiSearchDTO eBDSqCertiSearchDTO = null;
            try
            {
                eBDSqCertiSearchDTO = new EBDSqCertiSearchDTO();
                eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
                COUserDetailsDTO coUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                eBDSqCertiSearchDTO.setMemSeq(coUserDetailsDTO.getSeq());
                //조건 체크
                int posibleSqCertiCnt = eBDSqCertiReqService.getPosibleSqCertiCnt(eBDSqCertiSearchDTO);
                if("CP".equals(coUserDetailsDTO.getAuthCd()) && posibleSqCertiCnt == 1)
                {
                    eBDSqCertiSearchDTO.setRespCnt(eBDSqCertiReqService.insert(eBGExamAppctnMstDTO, request));
                }
                else
                {
                    throw new BusinessException(ErrorCode.ACCESS_DENIED);
                }
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                /** Restcontroller는 BusinessException **/
                ErrorCode eErrorCode = ErrorCode.INTERNAL_SERVER_ERROR;
                if(e instanceof ValidationException)
                {
                    eErrorCode = ErrorCode.INVALID_INPUT_VALUE;
                }
                throw new BusinessException(eErrorCode);
            }
            return eBDSqCertiSearchDTO;
        }

        /**
         * SQ평가원 자격증 신청
         */
        @PostMapping(value="/complete/update")
        public EBDSqCertiSearchDTO setSqCertiUpdate(@Valid EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception
        {
            EBDSqCertiSearchDTO eBDSqCertiSearchDTO = null;
            try
            {
                eBDSqCertiSearchDTO = new EBDSqCertiSearchDTO();
                eBDSqCertiSearchDTO.setRespCnt(eBDSqCertiReqService.updateCerti(eBGExamAppctnMstDTO, request));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                /** Restcontroller는 BusinessException **/
                ErrorCode eErrorCode = ErrorCode.INTERNAL_SERVER_ERROR;
                if(e instanceof ValidationException)
                {
                    eErrorCode = ErrorCode.INVALID_INPUT_VALUE;
                }
                throw new BusinessException(eErrorCode);
            }
            return eBDSqCertiSearchDTO;
        }
    }
}

