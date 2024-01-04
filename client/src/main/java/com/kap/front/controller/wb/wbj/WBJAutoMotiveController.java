package com.kap.front.controller.wb.wbj;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
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
@RequestMapping(value = "/coexistence/automotive")
public class WBJAutoMotiveController {
    /**
     * 서비스
     **/
    public final WBJARoundListService wBJARoundListService;
    public final MPEPartsCompanyService mpePartsCompanyService;
    public final WBBARoundService wbbaRoundService;
    public final WBBBCompanyService wbbbCompanyService;
    public final WBIASupplyListService wBIASupplyListService;
    public final WBJBAcomListService wBJBAcomListService;
    public final WBIBSupplyCompanyService wBIBSupplyCompanyService;
    public final COGCntsService pCOGCntsService;
    public final COCodeService cOCodeService;


    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbj/WBJAutoMotiveIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            wBRoundMstSearchDTO.setBsnCd(String.valueOf(725));

            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, wBRoundMstSearchDTO.getBsnCd(), "N"));
            modelMap.addAttribute("rtnData", wBJARoundListService.selectEpisdDtl(wBRoundMstSearchDTO));
            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBJARoundListService.getRoundDtl(wBRoundMstSearchDTO));
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
        String vwUrl = "front/wb/wbj/WBJAutoMotiveStep1.front";
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
    public String getStep2Page(WBBACompanySearchDTO wbbCompanySearchDTO, WBRoundMstSearchDTO wBRoundMstSearchDTO,
                               MPEPartsCompanyDTO mpePartsCompanyDTO, WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbj/WBJAutoMotiveStep2.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            cdDtlList.add("MEM_CD");

            COPaginationUtil page = new COPaginationUtil();

            page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
            page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());

            page.setPageSize(mpePartsCompanyDTO.getPageRowSize());

            mpePartsCompanyDTO.setFirstIndex(page.getFirstRecordIndex());
            mpePartsCompanyDTO.setRecordCountPerPage(page.getRecordCountPerPage());
            modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("optPrizeList", wBJBAcomListService.getPrizeList(wBJAcomSearchDTO));
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnRoundDtl", wBJARoundListService.getRoundDtl(wBRoundMstSearchDTO));
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
    public String insert(WBJAcomDTO wBJAcomDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {

            wBJAcomDTO.setBsnCd("INQ07010"); /* 자동차부품산업대상 코드 */

            COCodeDTO cOCodeDTO = new COCodeDTO();
            /* 자동차부품산업대상 구축 - 신청 코드 값*/
            cOCodeDTO.setCd("PRO_TYPE05");
            String rsumeSttsCd = cOCodeService.getCdIdList(cOCodeDTO).get(0).getCd();
            wBJAcomDTO.setRsumeSttsCd(rsumeSttsCd);

            /* 자동차부품산업대상 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd(rsumeSttsCd + "_01");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBJAcomDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 자동차부품산업대상 신청 관리자 최초 상태값 - 미확인 */
            cOCodeDTO.setCd(rsumeSttsCd + "_02");
            getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBJAcomDTO.setMngSttsCd(getCode.get(0).getCd());

            modelMap.addAttribute("actCnt", wBJBAcomListService.insertApply(wBJAcomDTO, request));
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
    public String getCompletePage(WBBACompanySearchDTO wbbCompanySearchDTO, WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbj/WBJAutoMotiveComplete.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBJBAcomListService.selectRecent(wBJAcomSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 부품사 정보 가져온다.
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbj/WBJAutoMotiveLayerAjax";
    }
}
