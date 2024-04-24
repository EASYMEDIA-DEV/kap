package com.kap.front.controller.wb.wbk;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbk.WBKBRegisterDTO;
import com.kap.service.*;
import com.kap.service.dao.sm.SMJFormMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 상생사업 미래차 공모전 Controller
 * </pre>
 *
 * @author 민윤기
 * @version 1.0
 * @ClassName : WBKManagementController.java
 * @Description : 상생사업 미래차 공모전  Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.02		민윤기				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/futureCarContest")
public class WBKManagementController {
    /**
     * 서비스
     **/
    public final WBKAFutureCarContestListService wBKAFutureCarContestListService;
    public final WBKBRegisterService wBKBRegisterService;
    public final COGCntsService pCOGCntsService;
    public final COCodeService cOCodeService;
    private final MPAUserService mpaUserService;

    public final WBBARoundService wbbaRoundService;

    private final SMJFormMapper sMJFormMapper;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbk/WBKManagementIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, "726", "N"));
            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBKAFutureCarContestListService.getRoundDtl(wBRoundMstSearchDTO));
            //신청서 양식
            SMJFormDTO smjFormDTO = new SMJFormDTO();
            smjFormDTO.setTypeCd("BUSINESS02");
            SMJFormDTO formFile = sMJFormMapper.selectFormDtl(smjFormDTO);
            modelMap.addAttribute("formFileSeq", formFile.getFtreCarAppctnFileSeq());
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 신청자 공모전 신청 여부 확인
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wBKAFutureCarContestListService.getApplyChecked(wBRoundMstSearchDTO));

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
    public String getStep1Page(WBBACompanySearchDTO wbbaCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbk/WBKManagementStep1.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
            //wbbaCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            if (!"".equals(mpaUserDto.getDetailsKey())) {
                MPAUserDto mpaUserDtos = mpaUserService.selectUserDtlTab(mpaUserDto);
                String[] split = mpaUserDtos.getEmail().split("@");
                mpaUserDtos.setEmailName(split[0]);
                mpaUserDtos.setEmailAddr(split[1]);
                modelMap.addAttribute("rtnDtl", mpaUserDtos);
            }

            modelMap.addAttribute("episdSeq", wbbaCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);

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
    public String getStep2Page(WBRoundMstSearchDTO wbRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbk/WBKManagementStep2.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            wbRoundMstSearchDTO.setStageOrd(1);

            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBKAFutureCarContestListService.getRoundDtl(wbRoundMstSearchDTO));

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("WBK_PTN");

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("WBKB_REG_CTG");
            modelMap.addAttribute("wbkbRegCtg",  cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("GRD_CD");
            modelMap.addAttribute("grdCd",  cOCodeService.getCdIdList(cOCodeDTO));


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
    @PostMapping(value = "/insert")
    public String insert(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        try {
            System.err.println("공모전 신청 완료!");

            /* 신청자 최초 상태값 - 접수완료 */
            wBKBRegisterDTO.setBsnCd("BSN11");
            wBKBRegisterDTO.setRsumeOrd(1);
            wBKBRegisterDTO.setAppctnSttsCd("WBKB_REG_FRT001");

            System.err.println("사용자 WBKBRegisterDTO == " + wBKBRegisterDTO);

            modelMap.addAttribute("actCnt", wBKBRegisterService.insertApply(wBKBRegisterDTO,multiRequest,request));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 사업신청 완료
     */
    @RequestMapping(value = "/complete")
    public String complete(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbk/WBKManagementComplete.front";
        try {
            modelMap.addAttribute("rtnData", wBKBRegisterService.getApplyDtl(wBKBRegisterDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

}
