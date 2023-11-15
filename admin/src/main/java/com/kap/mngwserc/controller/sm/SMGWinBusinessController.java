package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMGWinBusinessDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMGWinBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
 * <pre>
 * 상생 사업 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMGWinBusinessController.java
 * @Description		: 상생 사업 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/sm/smg/")
public class SMGWinBusinessController {

    private final SMGWinBusinessService sMGWinBusinessService;

    /**
     * 메인 비주얼 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getMnVslListPage(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessList(sMGWinBusinessDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smg/SMGWinBusinessList.admin";
    }

    /**
     * 메인 비주얼 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectWinBusinessList(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessList(sMGWinBusinessDTO));
            modelMap.addAttribute("mdCd", mdCd);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smg/SMGWinBusinessListAjax";
    }

    /**
     * 메인 비주얼 상세 페이지
     */
    @GetMapping(value = "/write")
    public String getWinBusinessWritePage(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

            if (!"".equals(sMGWinBusinessDTO.getDetailsKey()) && sMGWinBusinessDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessDtl(sMGWinBusinessDTO));
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smg/SMGWinBusinessWrite.admin";
    }

    /**
     * 메인 비주얼 등록
     */
    @PostMapping(value = "/insert")
    public String insertWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMGWinBusinessDTO.setRegId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setRegIp(coaAdmDTO.getLoginIp());
            modelMap.addAttribute("respCnt", sMGWinBusinessService.insertWinBusiness(sMGWinBusinessDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    @PostMapping(value = "/update")
    public String updateWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMGWinBusinessDTO.setRegId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setRegIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", sMGWinBusinessService.updateWinBusiness(sMGWinBusinessDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 메인 비주얼 삭제
     */
    @PostMapping(value = "/delete")
    public String deleteWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMGWinBusinessDTO.setRegId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setRegIp(coaAdmDTO.getLoginIp());
            sMGWinBusinessDTO.setRegName(coaAdmDTO.getName());
            sMGWinBusinessDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            sMGWinBusinessDTO.setModId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setModIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", sMGWinBusinessService.deleteWinBusiness(sMGWinBusinessDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 메인 비주얼 정렬 수정
     */
    @PostMapping(value="/sort")
    public ModelAndView updateOrder(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap) throws Exception {

        ModelAndView mav = new ModelAndView();

        try {
            modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessList(sMGWinBusinessDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMGWinBusinessDTO.setModId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setModIp(coaAdmDTO.getLoginIp());

            sMGWinBusinessService.updateOrder(sMGWinBusinessDTO);
            mav.setViewName("jsonView");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return mav;
    }
}