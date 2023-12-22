package com.kap.mngwserc.controller.cb.cba;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBAConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SVASurveyService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 컨설팅 기술 지도를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: CBATechGuidanceController.java
 * @Description		: 컨설팅 기술 지도를 위한 Controller
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/cb/cba")
public class CBATechGuidanceController {

    private final CBATechGuidanceService cBATechGuidanceService;

    private final COCodeService cOCodeService;

    private final SVASurveyService sVSurveyService;

    /**
     * 컨설팅 사업 기술 지도 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getTechGuidanceListPage(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        try {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("COMPANY_TYPE"); //부품사 구분 코드
            cdDtlList.add("MNGTECH_STATUS"); // 기술지도 진행 상태값
            cdDtlList.add("TEC_GUIDE_INDUS"); // 업종
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceInsertDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/cb/cba/CBATechGuidanceList.admin";
    }

    /**
     * 컨설팅 사업 기술 지도 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectTechGuidanceList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceInsertDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/cb/cba/CBATechGuidanceListAjax";
    }

    /**
     * 컨설팅 사업 기술 지도 상세 페이지
     */
    @GetMapping(value = "/write")
    public String getTechGuidanceWritePage(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        try
        {
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("TEC_GUIDE_INDUS"); // 업종
        cdDtlList.add("TEC_GUIDE_APPCTN"); // 직종 코드
        cdDtlList.add("MEM_CD"); // 직급, 부서 코드
        cdDtlList.add("COMPANY_TYPE"); //부품사 구분 코드
        cdDtlList.add("CO_YEAR_CD"); // 연도 코드 (2021년 ~ 2024년)
        cdDtlList.add("COMPANY_TYPE"); // 스타등급
        cdDtlList.add("APPCTN_RSN_CD"); // 신청사유 코드
        cdDtlList.add("ADDR_CD"); // 소재지 코드
        cdDtlList.add("BF_JDGMT_RSLT"); // 사전심사결과 코드
        cdDtlList.add("GUIDE_TYPE_CD"); // 지도 구분 코드
        cdDtlList.add("GUIDE_PSCND"); // 지도 현황 코드
        cdDtlList.add("INIT_VST_RSLT"); // 초도방문결과
        cdDtlList.add("MNGTECH_STATUS"); // 기술지도 진행 상태값
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        if (!"".equals(cBATechGuidanceInsertDTO.getDetailsKey()) && cBATechGuidanceInsertDTO.getDetailsKey() != null) {

            CBATechGuidanceInsertDTO rtnData = cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO);
            modelMap.addAttribute("rtnData", rtnData);

            if ( rtnData.getRsumeList().size() > 0){

                SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                sVASurveyDTO.setDetailsKey(cBATechGuidanceInsertDTO.getDetailsKey());

                sVASurveyDTO.setTypeCd("CON");
                SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeConDtl(sVASurveyDTO);
                if (sVASurveyMstInsertDTO != null){
                    modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);
                }

            }


        }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return "mngwserc/cb/cba/CBATechGuidanceWrite.admin";
    }

    /**
     * 컨설팅 사업 기술 지도 등록
     */
    @RequestMapping(value = "/insert", method= RequestMethod.POST)
    public String insertTechGuidance(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setRegId(cOUserDetailsDTO.getId());
            cBATechGuidanceInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", cBATechGuidanceService.insertTechGuidance(cBATechGuidanceInsertDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    @RequestMapping(value = "/update", method= RequestMethod.POST)
    public String updateTechGuidance(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, CBATechGuidanceUpdateDTO cBATechGuidanceUpdateDTO, ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setRegId(cOUserDetailsDTO.getId());
            cBATechGuidanceInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            cBATechGuidanceUpdateDTO.setBsnmNo(cBATechGuidanceUpdateDTO.getBsnmNo().replace("-", ""));

            modelMap.addAttribute("respCnt", cBATechGuidanceService.updateTechGuidance(cBATechGuidanceInsertDTO, cBATechGuidanceUpdateDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 컨설팅 사업 기술 지도 삭제
     */
    @PostMapping(value = "/delete")
    public String deleteTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("respCnt", cBATechGuidanceService.deleteTechGuidance(pCBATechGuidanceInsertDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 컨설팅 이관 내역을 조회한다.
     */
    @GetMapping(value = "/trsfList")
    public String getTrsfListPageAjax(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTrsfGuidanceList(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("CBATechGuidanceInsertDTO", cBATechGuidanceInsertDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/cb/cba/CBATechGuidanceTrsfListAjax";
    }

    /**
     * 컨설팅 사업 경영컨설팅 만족도 종합결과 목록 조회
     */
    @GetMapping(value = "/selectSurveyRslt")
    public String selectConsultSuveyRsltList(CBAConsultSuveyRsltListDTO cBAConsultSuveyRsltListDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            cBAConsultSuveyRsltListDTO.setRtnBsnGubun("CONSULT_GB01");
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectConsultSuveyRsltList(cBAConsultSuveyRsltListDTO));
            modelMap.addAttribute("searchDto", cBAConsultSuveyRsltListDTO);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/cb/cba/CBAConsultSurveyRsltLayerAjax";
    }

    /**
     * 컨설팅 사업 경영컨설팅 엑셀다운로드 관련
     */
    @GetMapping(value = "/excel-down")
    public void selectManageConsultListExcel(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            cBATechGuidanceInsertDTO.setExcelYn("Y");
            //엑셀 생성
            cBATechGuidanceService.excelDownload(cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceInsertDTO), response);
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

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/cb/cba")
    public class CBATechGuiadnceRestController {

        private final CBATechGuidanceService cBATechGuidanceService;
        private final MPAUserService mPAUserService;
        private final COCodeService cOCodeService;

        /**
         * 사업자 번호로 회사 정보 찾기
         */
        @PostMapping(value = "/bsnmNoSearch")
        @ResponseBody
        public List<MPEPartsCompanyDTO> bsnmNoSearch(@Valid @RequestBody MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
            List<MPEPartsCompanyDTO> list = new ArrayList<>();
            try {
                list = cBATechGuidanceService.selectPartsCompanyDtl(mpePartsCompanyDTO);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return list;
        }

        /**
         *  지역 정보로 서브 지역 정보 찾기
         */
        @PostMapping(value = "/subAddrSelect")
        @ResponseBody
        public List<COCodeDTO> selectSubAddr(@RequestBody COCodeDTO cOCodeDTO) throws Exception {
            List<COCodeDTO> detailList = null;
            try {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }
    }
}



