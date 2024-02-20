package com.kap.front.controller.co;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 마이페이지 서브 메인 페이지
 * </pre>
 * @ClassName		: COMypageController.java
 * @Description		: 마이페이지 서브 메인 페이지
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.01.09	  김학규	             최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COMypageController
{

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    public final MPBCoexistenceService mpbCoexistenceService;

    public final CBATechGuidanceService cBATechGuidanceService;

    private final IMAQaService iMAQaService;


    @GetMapping("/my-page/main")
    public String getMainPage(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try{

            //마이페이지에서 사용할 쿼리 조건절
            eBBEpisdDTO.setMypageYn("Y");

            //사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));



            //1년간 교육/세미나 사업신청내역 호출
            modelMap.addAttribute("eduYearCnt", eBBEpisdService.selectMypageEduCnt(eBBEpisdDTO));

            //1년간 상생 사업신청내역 호출
            MPBBsnSearchDTO mpbBnsSearchDTO = new MPBBsnSearchDTO();
            mpbBnsSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            Date today = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.YEAR, -1);

            Date oneYearAgo = calendar.getTime();

            String strtDt = df.format(oneYearAgo);
            String endDt = df.format(today);

            mpbBnsSearchDTO.setDateType("2");
            mpbBnsSearchDTO.setStrtDt(strtDt);
            mpbBnsSearchDTO.setEndDt(endDt);
            List<String> status = new ArrayList<>();
            status.add("1");
            status.add("2");
            status.add("3");
            status.add("4");
            mpbBnsSearchDTO.setStatusChk(status);

            modelMap.addAttribute("coeYearCnt", mpbCoexistenceService.selectApplyCount(mpbBnsSearchDTO));



            //1년간 컨설팅 내역
            CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO = new CBATechGuidanceInsertDTO();
            pCBATechGuidanceInsertDTO.setMemSeq(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq()));


            modelMap.addAttribute("consultingYearCnt", cBATechGuidanceService.countConsultingApplication(pCBATechGuidanceInsertDTO));


            //나의 1:1문의 호출
//            LocalDate currentDate = LocalDate.now();
//            LocalDate threeMonthsAgo = currentDate.minusMonths(3);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String currentDateString = currentDate.format(formatter);
//            String threeMonthsAgoDateString = threeMonthsAgo.format(formatter);
            IMAQaDTO pIMAQaDTO = new IMAQaDTO();
            pIMAQaDTO.setMypageYn("Y");
            pIMAQaDTO.setMypageMainYn("Y");
            pIMAQaDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
//            pIMAQaDTO.setSrchDate("1");
//            pIMAQaDTO.setStrtDt();
            modelMap.addAttribute("myQaData", iMAQaService.selectQaList(pIMAQaDTO));

        }catch (Exception e){
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }







        return "front/co/COMypage.front";

    }

    /**
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/my-page/main/select")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String rtnView = "front/co/COPtcptEduListAjax";
        try
        {
            System.out.println("여기");
            eBBEpisdDTO.setSiteGubun("admin");
            //사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //마이페이지에서 사용할 쿼리 조건절
            eBBEpisdDTO.setMypageYn("Y");
            eBBEpisdDTO.setMypageMainYn("Y");


            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));

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
}
