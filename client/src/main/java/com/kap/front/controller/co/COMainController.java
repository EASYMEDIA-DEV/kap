package com.kap.front.controller.co;

import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;
import com.kap.core.dto.sm.smb.SMBMainVslDTO;
import com.kap.core.dto.sm.smg.SMGWinBusinessDTO;
import com.kap.service.*;
import com.kap.core.dto.sm.smc.SMCMnPopDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 메인 페이지
 * </pre>
 * @ClassName		: COMainController.java
 * @Description		: 메인 페이지
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.17	  이옥정	             최초 생성
 * 	  2024.01.05      이옥정              이메일 중복 검사 추가
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COMainController
{

    /** 메인 비주얼 서비스 **/
    private final SMBMnVslService sMBMnVslService;

    /** 메인 팝업 서비스 **/
    private final SMCMnPopService sMCMnPopService;

    /** 상생사업 관리 서비스 **/
    private final SMGWinBusinessService sMGWinBusinessService;

    /** 공지사항 서비스 **/
    private final BDANoticeService bDANoticeService;

    /** FAQ 서비스 **/
    private final BDCFaqService bDCFaqService;

    /** 재단소식 서비스 **/
    private final BDBCompanyNewsService bDBCompanyNewsService;

    /** 교육 과정 서비스 **/
    private final EBBEpisdService eBBEpisdService;

    @GetMapping("/")
    public String getMainPage(ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        Device device = DeviceUtils.getCurrentDevice(request);
        /*System.out.println("======================1" + device.isMobile());
        System.out.println("======================2" + device.isTablet());
        System.out.println("======================3" + device.isNormal());*/

        try {
            //메인 비주얼 DTO
            SMBMainVslDTO sMBMainVslDTO = new SMBMainVslDTO();
            //메인 팝업 DTO
            SMCMnPopDTO sMCMnPopDTO = new SMCMnPopDTO();
            //상생사업관리 DTO
            SMGWinBusinessDTO sMGWinBusinessDTO = new SMGWinBusinessDTO();
            //공지사항 DTO
            BDANoticeDTO bDANoticeDTO = new BDANoticeDTO();
            //FAQ DTO
            BDCFaqDTO bDCFaqDTO = new BDCFaqDTO();
            //재단소식 DTO
            BDBCompanyNewsDTO bDBCompanyNewsDTO = new BDBCompanyNewsDTO();

            //상생사업 관리 리스트
            modelMap.addAttribute("winData", sMGWinBusinessService.selectWinBusinessList(sMGWinBusinessDTO));
            //공지사항 리스트
            bDANoticeDTO.setMainYn("Y");
            modelMap.addAttribute("noticeData", bDANoticeService.selectNoticeList(bDANoticeDTO));
            //FAQ 리스트
            bDCFaqDTO.setMainYn("Y");
            modelMap.addAttribute("faqData", bDCFaqService.selectFaqList(bDCFaqDTO));
            //메인여부
            modelMap.addAttribute("mainYn", "Y");
            //재단소식 메인여부
            bDBCompanyNewsDTO.setMainYn("Y");

            if(device.isNormal() == true || device.isTablet() == true){
                sMBMainVslDTO.setMdCd("pc");
                sMCMnPopDTO.setMdCd("pc");
                bDBCompanyNewsDTO.setDeviceGubun("pc");
                //메인 웹 비주얼 리스트
                modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
                //메인 웹 팝업 리스트
                modelMap.addAttribute("popData", sMCMnPopService.selectMnPopList(sMCMnPopDTO));
                //재단소식 웹 리스트
                modelMap.addAttribute("companyData", bDBCompanyNewsService.selectCompanyNewsList(bDBCompanyNewsDTO));
            }else{
                sMBMainVslDTO.setMdCd("mobile");
                sMCMnPopDTO.setMdCd("mobile");
                bDBCompanyNewsDTO.setDeviceGubun("mobile");
                //메인 모바일 비주얼 리스트
                modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
                //메인 모바일 팝업 리스트
                modelMap.addAttribute("popData", sMCMnPopService.selectMnPopList(sMCMnPopDTO));
                //재단소식 모바일 리스트
                modelMap.addAttribute("companyData", bDBCompanyNewsService.selectCompanyNewsList(bDBCompanyNewsDTO));
            }
        }catch (Exception e){
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/co/COMainDtl.front";
    }

    /**
     * 교육과정 목록을 조회한다.(집체교육 + 집체 및 온라인교육)
     */
    @RequestMapping(value = "/apply/groupselect")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String rtnView = "front/co/COMainCouseListAjax";
        try
        {
            List<String> stduyList = new ArrayList<>();
            stduyList.add("STDUY_MTHD01");
            stduyList.add("STDUY_MTHD03");
            eBBEpisdDTO.setMainYn("Y");
            eBBEpisdDTO.setApplyListYn("Y");
            eBBEpisdDTO.setStduyMthdCdList(stduyList);
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return rtnView;
    }

    /**
     * 교육과정 목록을 조회한다.(온라인교육 + 집체 및 온라인교육)
     */
    @RequestMapping(value = "/apply/onlineselect")
    public String getCouseOnlinePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String rtnView = "front/co/COMainOnlineCouseListAjax";
        try
        {
            List<String> stduyList = new ArrayList<>();
            stduyList.add("STDUY_MTHD02");
            stduyList.add("STDUY_MTHD03");
            eBBEpisdDTO.setMainYn("Y");
            eBBEpisdDTO.setStduyMthdCdList(stduyList);
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return rtnView;
    }


    /**
     * @ClassName		: COMainController.java
     * @Description		: 사용자 메인 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2024.01.08			이옥정			 		최초생성
     * </pre>
     */
    @Tag(name = "사용자 메인 API", description = "메인 뉴스레터 이메일 중복 검증 및 등록")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/")
    public class COMainRestController  {
        /** 뉴스레터 서비스 **/
        public final MPHNewsLetterService mphNewsLetterService;

        @Operation(summary = "이메일 중복체크", tags = "뉴스레터", description = "이메일 중복체크")
        @PostMapping(value = "/newsletter/dup-email")
        public MPHNewsLetterDTO selectDupEmail(@Valid @RequestBody MPHNewsLetterDTO mPHNewsLetterDTO ) throws Exception {

            try {
                mPHNewsLetterDTO.setRespCnt(mphNewsLetterService.selectDupEmail(mPHNewsLetterDTO));

            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return mPHNewsLetterDTO;
        }

        @Operation(summary = "뉴스레터 신청", tags = "뉴스레터", description = "뉴스레터 신청")
        @PostMapping(value = "/newsletter/insert")
        public MPHNewsLetterDTO insertNewsletter(@Valid @RequestBody MPHNewsLetterDTO mPHNewsLetterDTO, HttpServletRequest request ) throws Exception {

            try {
                mphNewsLetterService.insertNewsletter(mPHNewsLetterDTO, request);

            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return mPHNewsLetterDTO;
        }

    }
}
