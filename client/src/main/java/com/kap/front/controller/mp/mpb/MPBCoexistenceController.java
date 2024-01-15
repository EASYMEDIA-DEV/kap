package com.kap.front.controller.mp.mpb;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpb.MPBBsnMstDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyDtlDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 홈 > 마이페이지 > 상생 사업 신청내역 Controller
 * </pre>
 *
 * @ClassName		: MPBCoexistenceController.java
 * @Description		: 마이페이지 상생 사업 신청내역 Controller
 * @author 김태훈
 * @since 2024.01.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/coexistence")
public class MPBCoexistenceController {

    /* 서비스 */
    private final COCodeService cOCodeService;
    public final MPBCoexistenceService mpbCoexistenceService;
    public final WBBBCompanyService wbbbCompanyService;
    public final WBHACalibrationService wbhaCalibrationService;
    public final WBIBSupplyCompanyService wBIBSupplyCompanyService;
    public final WBJBAcomListService wBJBAcomListService;


    /**
     * 마이페이지 상생 사업 신청내역 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getCoexistenceList(MPBBsnSearchDTO mpbBsnSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {

        modelMap.addAttribute("rtnData", mpbCoexistenceService.selectApplyList(mpbBsnSearchDTO,"init"));

        return "front/mp/mpb/MPBCoexistenceList.front";
    }

    /**
     * 마이페이지 상생 사업 신청내역 더보기
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(MPBBsnSearchDTO mpbBsnSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", mpbCoexistenceService.selectApplyList(mpbBsnSearchDTO,"add"));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpb/MPBCoexistenceListAjax";
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/view")
    public String getCoexistenceView(MPBBsnSearchDTO mpbBsnSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {

        String vwUrl = "front/mp/mpb/MPBCoexistenceView.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpbBsnSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
            mpbBsnSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            RequestContextHolder.getRequestAttributes().setAttribute("appctnSeq", mpbBsnSearchDTO.getAppctnSeq(), RequestAttributes.SCOPE_SESSION);

            //공통사업 + 각사업에 대한 페이지 서비스 분기
            //공통사업 여부
            String businessYn = mpbCoexistenceService.getBusinessYn(mpbBsnSearchDTO);

            if("Y".equals(businessYn)) {
                /*TO-DO 공통사업 진행*/
                WBBACompanySearchDTO wbbaCompanySearchDTO = new WBBACompanySearchDTO();
                wbbaCompanySearchDTO.setBsnCd(mpbBsnSearchDTO.getBsnCd());
                wbbaCompanySearchDTO.setDetailsKey(String.valueOf(mpbBsnSearchDTO.getAppctnSeq()));

                WBBAApplyMstDTO wbbApplyMstDTO = new WBBAApplyMstDTO();
                wbbApplyMstDTO = wbbbCompanyService.selectCompanyDtl(wbbaCompanySearchDTO);
                modelMap.addAttribute("rtnInfo", wbbApplyMstDTO);
            } else {
                if ("코드".equals(mpbBsnSearchDTO.getBsnCd())) {

                } else if ("BSN06".equals(mpbBsnSearchDTO.getBsnCd())) {
                    //스마트공장
                    WBHACalibrationSearchDTO wbhaCalibrationSearchDTO = new WBHACalibrationSearchDTO();
                    wbhaCalibrationSearchDTO.setDetailsKey(String.valueOf(mpbBsnSearchDTO.getAppctnSeq()));
                    
                } else if ("BSN08".equals(mpbBsnSearchDTO.getBsnCd())) {
                    //검교정
                    WBHACalibrationSearchDTO wbhaCalibrationSearchDTO = new WBHACalibrationSearchDTO();
                    wbhaCalibrationSearchDTO.setDetailsKey(String.valueOf(mpbBsnSearchDTO.getAppctnSeq()));
                    modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCompanyDtl(wbhaCalibrationSearchDTO));
                } else if ("BSN09".equals(mpbBsnSearchDTO.getBsnCd())) {
                    //공급망
                    WBIBSupplySearchDTO wBIBSupplySearchDTO = new WBIBSupplySearchDTO();
                    wBIBSupplySearchDTO.setDetailsKey(String.valueOf(mpbBsnSearchDTO.getAppctnSeq()));
                    modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.selectSupplyDtl(wBIBSupplySearchDTO));
                } else if ("BSN10".equals(mpbBsnSearchDTO.getBsnCd())) {
                    //자동차부품
                    ArrayList<String> cdDtlList = new ArrayList<String>();
                    cdDtlList.add("MEM_CD");
                    WBJAcomSearchDTO wBJAcomSearchDTO = new WBJAcomSearchDTO();
                    modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
                    wBJAcomSearchDTO.setDetailsKey(String.valueOf(mpbBsnSearchDTO.getAppctnSeq()));

                    modelMap.addAttribute("rtnData", wBJBAcomListService.selectAcomDtl(wBJAcomSearchDTO));
                    modelMap.addAttribute("rtnAppctnRsume", wBJBAcomListService.selectAppctnRsumeDtl(wBJAcomSearchDTO));
                }
            }

            modelMap.addAttribute("businessYn", businessYn);
            modelMap.addAttribute("rtnBsnData", mpbCoexistenceService.getBsnDetail(mpbBsnSearchDTO));
            modelMap.addAttribute("rtnCompany", mpbCoexistenceService.selectCompanyUserDtl(mpbBsnSearchDTO));
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
     * 사업진행상황을 수정한다.
     */
    @PostMapping(value="/update")
    public String update(MPBBsnMstDTO mpbBsnMstDTO, MultipartHttpServletRequest multiRequest, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = 0;
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            
            //신청자 순번 세션체크 없으면 수정불가
            if (RequestContextHolder.getRequestAttributes().getAttribute("appctnSeq", RequestAttributes.SCOPE_SESSION) == null){
                respCnt = 100;
            }else{
                String appctnSeq = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("appctnSeq", RequestAttributes.SCOPE_SESSION));
                mpbBsnMstDTO.setAppctnSeq(Integer.valueOf(appctnSeq));

                //각 사업단계 업데이트를 위한 현재단계 조회
                MPBBsnSearchDTO mpbBsnSearchDTO = new MPBBsnSearchDTO();
                mpbBsnSearchDTO.setBsnCd(mpbBsnMstDTO.getBsnCd());
                mpbBsnSearchDTO.setAppctnSeq(mpbBsnMstDTO.getAppctnSeq());
                mpbBsnSearchDTO = mpbCoexistenceService.getBsnDetail(mpbBsnSearchDTO);

                //공통사업 + 각사업에 대한 페이지 서비스 분기
                //공통사업 여부
                String businessYn = mpbCoexistenceService.getBusinessYn(mpbBsnSearchDTO);

                if("Y".equals(businessYn)) {

                    WBBAApplyDtlDTO wbbApplyDtlDTO = new WBBAApplyDtlDTO();
                    wbbApplyDtlDTO.setBsnCd(mpbBsnSearchDTO.getBsnCd());
                    wbbApplyDtlDTO.setRsumeSeq(mpbBsnSearchDTO.getRsumeSeq());
                    wbbApplyDtlDTO.setRsumeOrd(mpbBsnSearchDTO.getRsumeOrd());
                    wbbApplyDtlDTO.setAppctnSttsCd(mpbBsnSearchDTO.getAppctnSttsCd());
                    wbbApplyDtlDTO.setAppctnSeq(mpbBsnMstDTO.getAppctnSeq());

                    respCnt = wbbbCompanyService.updateInfo(wbbApplyDtlDTO, multiRequest, request);

                } else {
                    if ("코드".equals(mpbBsnSearchDTO.getBsnCd())) {

                    } else if ("BSN08".equals(mpbBsnSearchDTO.getBsnCd())) {
                        //검교정
                    } else if ("BNS09".equals(mpbBsnSearchDTO.getBsnCd())) {
                        //공급망
                        WBIBSupplyDTO wBIBSupplyDTO = new WBIBSupplyDTO();
                        wBIBSupplyDTO.setBsnCd(mpbBsnSearchDTO.getBsnCd());
                        wBIBSupplyDTO.setRsumeSeq(mpbBsnSearchDTO.getRsumeSeq());
                        wBIBSupplyDTO.setRsumeOrd(mpbBsnSearchDTO.getRsumeOrd());
                        wBIBSupplyDTO.setAppctnSttsCd(mpbBsnSearchDTO.getAppctnSttsCd());
                        wBIBSupplyDTO.setAppctnSeq(mpbBsnMstDTO.getAppctnSeq());

                        respCnt = wBIBSupplyCompanyService.updateInfo(wBIBSupplyDTO, multiRequest, request);
                    }
                }
            }


            modelMap.addAttribute("respCnt", respCnt);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());

        }
        return "jsonView";
    }
}
