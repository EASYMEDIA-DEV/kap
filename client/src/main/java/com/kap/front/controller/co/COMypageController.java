package com.kap.front.controller.co;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBBEpisdService;
import com.kap.service.MPBCoexistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

            modelMap.addAttribute("coeYearCnt", mpbCoexistenceService.selectApplyCount(mpbBnsSearchDTO));



            //1년간 컨설팅 내역
            CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO = new CBATechGuidanceInsertDTO();
            pCBATechGuidanceInsertDTO.setMemSeq(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq()));
            cBATechGuidanceService.countConsultingApplication(pCBATechGuidanceInsertDTO);

            modelMap.addAttribute("consultingYearCnt", mpbCoexistenceService.selectApplyCount(mpbBnsSearchDTO));


            //나의 1:1문의 호출

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
