package com.kap.mngwserc.controller.cb;

import com.kap.core.dto.CBATechGuidanceDTO;
import com.kap.core.dto.COAAdmDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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
           // modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceDTO));
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
        //    modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceList(cBATechGuidanceDTO));
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
    public String getTechGuidanceWritePage(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            ArrayList<String> cdDtlList = new ArrayList<String>();
            ArrayList<String> cdAppCtnList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("TEC_GUIDE_INDUS"); // 업종
            cdDtlList.add("TEC_GUIDE_APPCTN"); // 직종 코드
            cdDtlList.add("MEM_CD"); // 직급, 부서 코드
            cdDtlList.add("COMPANY_TYPE"); //부품사 구분 코드
            cdDtlList.add("CO_YEAR_CD"); // 연도 코드 (2021년 ~ 2024년)
            cdDtlList.add("COMPANY_TYPE"); // 스타등급
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if (!"".equals(cBATechGuidanceDTO.getDetailsKey()) && cBATechGuidanceDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceDTO));
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
    @PostMapping(value = "/insert")
    public String insertTechGuidance(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceDTO.setRegId(coaAdmDTO.getId());
            cBATechGuidanceDTO.setRegIp(coaAdmDTO.getLoginIp());

            System.err.println("cBATechGuidanceDTO:::"+cBATechGuidanceDTO);

            modelMap.addAttribute("respCnt", cBATechGuidanceService.insertTechGuidance(cBATechGuidanceDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    @PostMapping(value = "/update")
    public String updateTechGuidance(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceDTO.setRegId(coaAdmDTO.getId());
            cBATechGuidanceDTO.setRegIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", cBATechGuidanceService.updateTechGuidance(cBATechGuidanceDTO));
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
    public String deleteTechGuidance(CBATechGuidanceDTO cBATechGuidanceDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceDTO.setRegId(coaAdmDTO.getId());
            cBATechGuidanceDTO.setRegIp(coaAdmDTO.getLoginIp());
            cBATechGuidanceDTO.setRegName(coaAdmDTO.getName());
            cBATechGuidanceDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            cBATechGuidanceDTO.setModId(coaAdmDTO.getId());
            cBATechGuidanceDTO.setModIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", cBATechGuidanceService.deleteTechGuidance(cBATechGuidanceDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

}