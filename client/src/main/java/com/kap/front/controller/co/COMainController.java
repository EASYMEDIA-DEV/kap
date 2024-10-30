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
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashMap;
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
    /** 교육 과정 서비스 **/
    private final EBBEpisdService eBBEpisdService;

    /** 메인 서비스 **/
    private final COMainService cOMainService;

    @GetMapping("/")
    public String getMainPage(ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        Device device = DeviceUtils.getCurrentDevice(request);

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

            boolean isNomal = true;
            if(device.isNormal() == true || device.isTablet() == true){
                isNomal = true;
            }else{
                isNomal = false;
            }

            long startTime = System.currentTimeMillis();




            HashMap<String, Object> rtnMap = cOMainService.selectMainGroup(isNomal, sMBMainVslDTO, sMCMnPopDTO, sMGWinBusinessDTO, bDANoticeDTO, bDCFaqDTO, bDBCompanyNewsDTO);

            modelMap.addAttribute("winData", rtnMap.get("winData"));
            //공지사항 리스트
            modelMap.addAttribute("noticeData", rtnMap.get("noticeData"));
            //FAQ 리스트
            modelMap.addAttribute("faqData", rtnMap.get("faqData"));
            //메인여부
            modelMap.addAttribute("mainYn", "Y");

            if(isNomal){
                //메인 웹 비주얼 리스트
                modelMap.addAttribute("rtnData", rtnMap.get("rtnData"));
                //메인 웹 팝업 리스트
                modelMap.addAttribute("popData", rtnMap.get("popData"));
                //재단소식 웹 리스트
                modelMap.addAttribute("companyData", rtnMap.get("companyData"));
            }else{
                modelMap.addAttribute("rtnData", rtnMap.get("rtnData"));
                //메인 모바일 팝업 리스트
                modelMap.addAttribute("popData", rtnMap.get("popData"));
                //재단소식 모바일 리스트
                modelMap.addAttribute("companyData", rtnMap.get("companyData"));
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime; // 실행 시간 (밀리초)

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

        @PostMapping(value="/searchMemory")
        public HashMap<String, Object> memorySearch(){

            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

            long usedMemory = heapMemoryUsage.getUsed();
            long maxMemory = heapMemoryUsage.getMax();

            //바이트
            long usedMemory2= (usedMemory/1024);//킬로
            long usedMemory3= (usedMemory2/1024);//메가
            long usedMemory4= (usedMemory3/1024);//기가

            long maxMemory2= (maxMemory/1024);//킬로
            long maxMemory3= (maxMemory2/1024);//메가
            long maxMemory4= (maxMemory3/1024);//기가

            double usedPercent = (double) usedMemory3 / maxMemory3 * 100.0;

            //out.println("Heap Memory Usage: Used = " + usedMemory3 + " Mega, Max = " + maxMemory3 + " Mega, Used Percent = " + String.format("%.2f", usedPercent) + "%<br>");
            System.out.println("@@ usedPercent = " + usedPercent);
            // 임계값 설정 (90% 이상)
            if (usedPercent > 90.0) {
                //out.println("<p style='color: red;'>Memory usage is over 90% - Running Garbage Collection</p>");
                //System.gc(); // 강제로 가비지 컬렉션 실행
            }

            HashMap<String, Object> tempMap = new HashMap();

            tempMap.put("nowMemory", usedMemory3);
            tempMap.put("maxMemory", maxMemory3);


            return tempMap;
        }


        @PostMapping(value="/clearMemory")
        public HashMap<String, Object> clearMemory(){
            HashMap<String, Object> tempMap = new HashMap();

            System.gc(); // 강제로 가비지 컬렉션 실행


            return tempMap;
        }

    }
}
