package com.kap.mngwserc.controller.wb.wbb;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBBARoundService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * <pre>
 * 공통 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBBARoundController.java
 * @Description		: 공통 회차관리를 위한 Controller
 * @author 김태훈
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.24		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wbb/wbba/{bsnCd}")
public class WBBARoundController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBBARoundService wBBARoundService;

    /**
     * 공통 회차 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");

        modelMap.addAttribute("classTypeList", cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("rtnData", wBBARoundService.selectRoundList(wBRoundMstSearchDTO));
        modelMap.addAttribute("bsnCd", wBRoundMstSearchDTO.getBsnCd());

        return "mngwserc/wb/wbb/WBBARoundList.admin";
    }

    /**
     * 공통 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getRoundListPageAjax(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wBBARoundService.selectRoundList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
           throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbb/WBBARoundListAjax";
    }

    /**
     * 공통 회차 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getRoundWritePage(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap
            , @Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("ROUND_CD");


            modelMap.addAttribute("classTypeList", cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            modelMap.addAttribute("rtnYear", wBBARoundService.selectYearDtl(wBRoundMstSearchDTO));
            modelMap.addAttribute("bsnCd", wBRoundMstSearchDTO.getBsnCd());

            if (wBRoundMstSearchDTO.getDetailsKey() != null) {
                modelMap.addAttribute("rtnInfo", wBBARoundService.selectRoundDtl(wBRoundMstSearchDTO));
            }

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbb/WBBARoundWrite.admin";
    }

    /**
     * 공통 회차 등록
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    public WBRoundMstDTO roundInsert(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            wBBARoundService.insertRound(wBRoundMstDTO, request);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBRoundMstDTO;
    }

    /**
     * 공통 회차 수정
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public WBRoundMstDTO roundUpdate(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            wBBARoundService.updateRound(wBRoundMstDTO, request);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return wBRoundMstDTO;
    }

    /**
     * 탄소배출저감 회차 삭제
     */
    @PostMapping(value = "/delete")
    public String roundDelete(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBBARoundService.deleteRound(wBRoundMstDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }
}
