package com.kap.front.controller.wb.wbi;

import com.kap.common.utility.CONetworkUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * 공통사업 Controller
 * </pre>
 *
 * @author 박준희
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 공통사업 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김태훈				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/supplyChain")
public class WBISupplyController {
    /**
     * 서비스
     **/
    public final WBBARoundService wbbaRoundService;
    public final WBBBCompanyService wbbbCompanyService;
    public final WBIASupplyListService wBIASupplyListService;
    public final WBIBSupplyCompanyService wBIBSupplyCompanyService;
    public final COGCntsService pCOGCntsService;
    public final COCodeService cOCodeService;


    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbi/WBISupplyIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            wBRoundMstSearchDTO.setBsnCd(String.valueOf(723));

            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, wBRoundMstSearchDTO.getBsnCd(), "N"));
            modelMap.addAttribute("rtnData", wBIASupplyListService.selectSupplyList(wBRoundMstSearchDTO));
            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBIASupplyListService.getRoundDtl(wBRoundMstSearchDTO));
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
            modelMap.addAttribute("rtnData", wBIASupplyListService.selectSupplyList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbi/WBISupplyIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wbbaRoundService.getApplyChecked(wBRoundMstSearchDTO));

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
    public String getStep1Page(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbi/WBISupplyStep1.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("episd", wbbCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wbbbCompanyService.selectCompanyUserDtl(wbbCompanySearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbi/WBISupplyStep2.front";
        try {
            modelMap.addAttribute("rtnRoundDtl", wBIASupplyListService.getRoundDtl(wBRoundMstSearchDTO));
            wBRoundMstSearchDTO.setStageOrd(1);
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
    public String insert(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request, ModelMap modelMap) throws Exception {
        try {

            wBIBSupplyDTO.setBsnCd("BUSUNESS_TYPE09"); /* 공급망 */

            COCodeDTO cOCodeDTO = new COCodeDTO();
            /* 스마트 공장 구축 - 신청 코드 값*/
            cOCodeDTO.setCd("PRO_TYPE06");
            String rsumeSttsCd = cOCodeService.getCdIdList(cOCodeDTO).get(0).getCd();
            wBIBSupplyDTO.setRsumeSttsCd(rsumeSttsCd);

            /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd(rsumeSttsCd + "_01");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBIBSupplyDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 스마트 신청 관리자 최초 상태값 - 미확인 */
            cOCodeDTO.setCd(rsumeSttsCd + "_02");
            getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBIBSupplyDTO.setMngSttsCd(getCode.get(0).getCd());

            modelMap.addAttribute("actCnt", wBIBSupplyCompanyService.insertApply(wBIBSupplyDTO, wBIBSupplyMstDTO, request));
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
    @RequestMapping(value = "/complete")
    public String getCompletePage(WBBACompanySearchDTO wbbCompanySearchDTO, WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbi/WBISupplyComplete.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.selectRecent(wBIBSupplySearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }
}
