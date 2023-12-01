package com.kap.mngwserc.controller.cb.cba;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
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

    /**
     * 컨설팅 사업 기술 지도 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getTechGuidanceListPage(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceDTO));
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
    public String selectTechGuidanceList(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceDTO));
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
        /*try
        {*/
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
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if (!"".equals(cBATechGuidanceInsertDTO.getDetailsKey()) && cBATechGuidanceInsertDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO));
            }
        /*} catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/


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

            System.err.println("cBATechGuidanceDTO:::"+cBATechGuidanceInsertDTO);

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
       /* try {*/
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setRegId(cOUserDetailsDTO.getId());
            cBATechGuidanceInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", cBATechGuidanceService.updateTechGuidance(cBATechGuidanceInsertDTO, cBATechGuidanceUpdateDTO));
       /* } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/

        return "jsonView";
    }

    /**
     * 컨설팅 사업 기술 지도 삭제
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteTechGuidance(@Valid @RequestBody CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceDTO.setRegId(cOUserDetailsDTO.getId());
            cBATechGuidanceDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            cBATechGuidanceDTO.setRegName(cOUserDetailsDTO.getName());
            cBATechGuidanceDTO.setRegDeptNm(cOUserDetailsDTO.getDeptNm());
            cBATechGuidanceDTO.setModId(cOUserDetailsDTO.getId());
            cBATechGuidanceDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", cBATechGuidanceService.deleteTechGuidance(cBATechGuidanceDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }


    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/cb/cba")
    public class CBATechGuiadnceRestController {

        private final CBATechGuidanceService cBATechGuidanceService;
        private final COCodeService cOCodeService;

        /**
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/bsnmNoSearch")
        @ResponseBody
        public List<MPEPartsCompanyDTO> bsnmNoSearch(@Valid @RequestBody MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
            List<MPEPartsCompanyDTO> list = new ArrayList<>();
            try {
                System.err.println("MPEPartsCompanyDTO"+mpePartsCompanyDTO);
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
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/subAddrSelect")
        @ResponseBody
        public List<COCodeDTO> selectSubAddr(@RequestBody COCodeDTO cOCodeDTO) throws Exception {
            List<COCodeDTO> detailList = null;
            try {
                System.err.println("cOCodeDTO"+cOCodeDTO);
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



