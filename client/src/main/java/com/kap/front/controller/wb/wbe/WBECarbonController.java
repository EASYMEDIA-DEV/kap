package com.kap.front.controller.wb.wbe;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 탄소배출저감 Controller
 * </pre>
 *
 * @author 김대성
 * @version 1.0
 * @ClassName : WBECarbonController.java
 * @Description : 탄소배출저감 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김대성				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/emissions/carbon")
public class WBECarbonController {
    /**
     * 서비스
     **/
    public final WBEACarbonListService wBEACarbonListService;
    public final WBEBCarbonCompanyService wBEBCarbonCompanyService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbe/WBECarbonIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            wBRoundMstSearchDTO.setBsnCd("BUSUNESS_TYPE05");

            //CMS
            pCOGCntsDTO.setMenuSeq(718);

            String menuSeq = "718";
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, menuSeq, "N"));
            modelMap.addAttribute("rtnData", wBEACarbonListService.selectCarbonList(wBRoundMstSearchDTO));

            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBEACarbonListService.getRoundDtl(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wBEACarbonListService.selectCarbonList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbe/WBECarbonIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wBEACarbonListService.getApplyChecked(wBRoundMstSearchDTO));

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step1")
    public String getStep1Page(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbe/WBECarbonStep1.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBEBCarbonCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("episd", wBEBCarbonCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCompanyUserDtl(wBEBCarbonCompanySearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbe/WBECarbonStep2.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBEBCarbonCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("episd", wBEBCarbonCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCompanyUserDtl(wBEBCarbonCompanySearchDTO));

            //사업접수 하단플로팅 영역용
            WBRoundMstSearchDTO wBRoundMstSearchDTO = new WBRoundMstSearchDTO();
            wBRoundMstSearchDTO.setBsnCd("BUSUNESS_TYPE05");
            modelMap.addAttribute("rtnRoundDtl", wBEACarbonListService.getRoundDtl(wBRoundMstSearchDTO));

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 신청
     */
    @RequestMapping(value = "/insert")
    public String insert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
           modelMap.addAttribute("actCnt", wBEBCarbonCompanyService.carbonUserInsert(wBEBCarbonCompanyMstInsertDTO,request));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/complet")
    public String getCompletPage(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbe/WBECarbonComplet.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBEBCarbonCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("episd", wBEBCarbonCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCompanyUserDtl(wBEBCarbonCompanySearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

}
