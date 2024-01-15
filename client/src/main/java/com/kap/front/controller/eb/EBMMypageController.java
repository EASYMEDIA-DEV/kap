package com.kap.front.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBLctrDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBBEpisdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class EBMMypageController
{

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    /**
     * 교육/세미나 사업 신청내역 목록/my-page/edu-apply/list
     */
    @GetMapping("/my-page/edu-apply/list")
    public String getApplyList(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try{

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            cdDtlList.add("CLASS_TYPE");
            cdDtlList.add("STDUY_MTHD"); //학습방식
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            //과정분류 - 소분류
            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("CLASS01");
            modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS02");
            modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS03");
            modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

            //마이페이지에서 사용할 쿼리 조건절
            //eBBEpisdDTO.setMypageYn("Y");

            //사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectMypageEduList(eBBEpisdDTO));

            //나의 1:1문의 호출

        }catch (Exception e){
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return "front/eb/ebm/EBMEduApplyList.front";

    }

    /**
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/my-page/edu-apply/select")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String rtnView = "front/eb/ebm/EBMEduListAjax";
        try
        {
            System.out.println("여기 eBBEpisdDTO= " +eBBEpisdDTO);
            //교육 사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectMypageEduList(eBBEpisdDTO));

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
     * 교육/세미나 사업 신청내역 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/detail")
    public String getApplyDetail(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");
        EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");//교육장 정보
        List<EBBLctrDTO> lctrDtoList = (List<EBBLctrDTO>) rtnMap.get("lctrDtoList");//온라인교육상세 목록
        List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList");//온라인교육상세 목록




        modelMap.addAttribute("rtnData", rtnDto);
        modelMap.addAttribute("roomDto", roomDto);
        modelMap.addAttribute("lctrDtoList", lctrDtoList);
        modelMap.addAttribute("isttrList", isttrList);


        return "front/eb/ebm/EBMEduApplyDtl.front";
    }

    /**
     * 교육/세미나 사업 신청내역 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/srvStep1")
    public String getApplySrvStep1(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");


        modelMap.addAttribute("rtnData", rtnDto);


        return "front/eb/ebm/EBMEduApplySrvStep1.front";
    }

}
