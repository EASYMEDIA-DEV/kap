package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMGWinBusinessDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMGWinBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * <pre>
 * 상생 사업 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMGWinBusinessController.java
 * @Description		: 상생 사업 관리를 위한 Controller
 * @author 임서화
 * @since 2023.10.25
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2023.10.25	 	임서화				    최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/smg/")
public class SMGWinBusinessController {

    private final SMGWinBusinessService sMGWinBusinessService;

    /**
     * 상생 사업 목록 조회
     */
    @RequestMapping(value = "/list")
    public String getWinBusinessListPage(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, @PathVariable("langCd") String langCd) throws Exception {
        try {
            sMGWinBusinessDTO.setLangCd(langCd);
            modelMap.addAttribute("langCd", langCd);
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
     * 상생 사업 등록
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, @PathVariable("langCd") String langCd) throws Exception {
        try {
            System.err.println("dddddd");
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, @PathVariable("langCd") String langCd) throws Exception {
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
     * 상생 사업 삭제
     */
    @PostMapping(value = "/delete")
    public String deleteWinBusiness(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap, @PathVariable("langCd") String langCd) throws Exception {
        try {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            sMGWinBusinessDTO.setRegId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setRegIp(coaAdmDTO.getLoginIp());
            sMGWinBusinessDTO.setRegName(coaAdmDTO.getName());
            sMGWinBusinessDTO.setRegDeptNm(coaAdmDTO.getDeptNm());
            sMGWinBusinessDTO.setModId(coaAdmDTO.getId());
            sMGWinBusinessDTO.setModIp(coaAdmDTO.getLoginIp());
            sMGWinBusinessDTO.setLangCd(langCd);

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
     * 상생 사업 정렬 수정
     */
    @RequestMapping(value="/sort", method=RequestMethod.POST)
    public ModelAndView updateOrder(SMGWinBusinessDTO sMGWinBusinessDTO, ModelMap modelMap,@PathVariable("langCd") String langCd) throws Exception {

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