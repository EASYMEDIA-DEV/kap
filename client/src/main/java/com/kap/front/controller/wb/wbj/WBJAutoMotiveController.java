package com.kap.front.controller.wb.wbj;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 자동차부품산업대상 Controller
 * </pre>
 *
 * @author 오병호
 * @version 1.0
 * @ClassName : WBJAutoMotiveController.java
 * @Description : 자동차부품산업대상 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		오병호				   최초 생성
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
            RequestContextHolder.getRequestAttributes().removeAttribute("contentAuth", RequestAttributes.SCOPE_SESSION);
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
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wbbCompanySearchDTO.getEpisdSeq()))) {
                vwUrl = "redirect:./content";
            }else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("episd", wbbCompanySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wbbbCompanyService.selectCompanyUserDtl(wbbCompanySearchDTO));

                RequestContextHolder.getRequestAttributes().setAttribute("step1Auth", wbbCompanySearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
            }
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
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBRoundMstSearchDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                modelMap.addAttribute("msg", "정상적인 접근이 아닙니다.");
            } else {
                wBRoundMstSearchDTO.setStageOrd(1);

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

                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().setAttribute("step2Auth", wBRoundMstSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
            }
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
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBJAcomDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                return "redirect:/";
            } else {
                wBJAcomDTO.setBsnCd("BUSUNESS_TYPE10"); /* 자동차부품산업대상 코드 */

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

                RequestContextHolder.getRequestAttributes().setAttribute("complete", "Y", RequestAttributes.SCOPE_SESSION);
            }
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
            if (RequestContextHolder.getRequestAttributes().getAttribute("complete", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
                return "redirect:/";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBJBAcomListService.selectRecent(wBJAcomSearchDTO));
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
            }
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
