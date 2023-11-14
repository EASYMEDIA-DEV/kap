package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMBMainVslDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMBMnVslService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
 * <pre>
 * 메인 비주얼 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMGWinBusinessController.java
 * @Description		: 메인 비주얼 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/sm/smb/{mdCd:pc|mobile}")
public class SMBMnVslController {

    private final SMBMnVslService sMBMnVslService;

    /**
     * 메인 비주얼 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getMnVslListPage(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            sMBMainVslDTO.setMdCd(mdCd);
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
            modelMap.addAttribute("mdCd", mdCd);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslList.admin";
    }

    /**
     * 메인 비주얼 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectMnVslList(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            sMBMainVslDTO.setMdCd(mdCd);
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
            modelMap.addAttribute("mdCd", mdCd);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslListAjax";
    }

    /**
     * 메인 비주얼 상세 페이지
     */
    @GetMapping(value = "/write")
    public String getMnVslWritePage(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslDTO.setMdCd(mdCd);
            modelMap.addAttribute("mdCd", sMBMainVslDTO.getMdCd());

            if (!"".equals(sMBMainVslDTO.getDetailsKey()) && sMBMainVslDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslDtl(sMBMainVslDTO));
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslWrite.admin";
    }

    /**
     * 메인 비주얼 등록
     */
    @PostMapping(value = "/insert")
    public String insertMnVsl(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslDTO.setMdCd(mdCd);
            sMBMainVslDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslDTO.setRegIp(coaAdmDTO.getLoginIp());
            System.err.println("sMBMainVslDTO111::"+sMBMainVslDTO);
            modelMap.addAttribute("respCnt", sMBMnVslService.insertMnVsl(sMBMainVslDTO));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    @PostMapping(value = "/update")
    public String updateMnVsl(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslDTO.setMdCd(mdCd);
            sMBMainVslDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslDTO.setRegIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", sMBMnVslService.updateMnVsl(sMBMainVslDTO));
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
    public String deleteMnVsl(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslDTO.setRegId(coaAdmDTO.getId());
            sMBMainVslDTO.setRegIp(coaAdmDTO.getLoginIp());
            sMBMainVslDTO.setRegName(coaAdmDTO.getName());
            sMBMainVslDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            sMBMainVslDTO.setModId(coaAdmDTO.getId());
            sMBMainVslDTO.setModIp(coaAdmDTO.getLoginIp());
            sMBMainVslDTO.setMdCd(mdCd);

            modelMap.addAttribute("respCnt", sMBMnVslService.deleteMnVsl(sMBMainVslDTO));
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
    public ModelAndView updateOrder(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception {

        ModelAndView mav = new ModelAndView();

        try {
            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMBMainVslDTO.setModId(coaAdmDTO.getId());
            sMBMainVslDTO.setModIp(coaAdmDTO.getLoginIp());
            sMBMainVslDTO.setMdCd(mdCd);

            sMBMnVslService.updateOrder(sMBMainVslDTO);
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