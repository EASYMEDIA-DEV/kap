package com.kap.front.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBACouseService;
import com.kap.service.EBBEpisdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육 과정 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육 과정 Controller
 * @author 김학규
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육사업 > 교육신청")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/education")
public class EBACouseController {

    /** 서비스 **/
    private final EBACouseService eBACouseService;

    private final EBBEpisdService eBBEpisdService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /**
     * 교육과정 신청 목록
     */
    @GetMapping(value="/apply/list")
    public String getEducationApplyList(ModelMap modelMap, EBBEpisdDTO eBBEpisdDTO) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseList.front";
        try
        {
            EBBEpisdDTO rtnList  = eBBEpisdService.selectEpisdList(eBBEpisdDTO);

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

            modelMap.addAttribute("rtnData", rtnList);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

    /**
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/apply/select")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String rtnView = "front/eb/eba/EBACouseListAjax";
        try
        {
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
        System.out.println("@@@ == " + eBBEpisdDTO.getCnnctCd() );
        if(eBBEpisdDTO.getCnnctCd() != null){
            rtnView = "front/eb/eba/EBACouseRelListAjax";
        }

        return rtnView;
    }

    /**
     * 교육회차관리 목록을 조회한다.
     */
    @RequestMapping(value = "/apply/episdSelect")
    public String getEpisdPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            System.out.println("@@@@@온다");


            modelMap.addAttribute("rtnData", eBBEpisdService.selectCouseChildEpisdList(eBBEpisdDTO));
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
        return "front/eb/eba/EBACouseChildEpisdListAjax";
    }



    /**
     * 교육과정 신청 상세
     */
    @GetMapping(value="/apply/detail")
    public String getEducationApplyDtl(EBACouseDTO eBACouseDTO, ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseDtl.front";
        try
        {
            HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);

            EBACouseDTO rtnDto = (EBACouseDTO)rtnMap.get("rtnData");
            List<EBACouseDTO> rtnEpisdList = (List<EBACouseDTO>) rtnMap.get("rtnEpisdList");//과저엥 소속된 차수 목록
            List<EBACouseDTO> rtnTrgtData = (List<EBACouseDTO>) rtnMap.get("rtnTrgtData");//학습대상 목록

            //교육과정연계 상세 조회
            List<EBACouseDTO> relList = eBACouseService.selectEdctnRelList(rtnDto);

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            cdDtlList.add("CLASS_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            //과정분류 - 소분류
            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("CLASS01");
            modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS02");
            modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS03");
            modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

            cdDtlList.add("STDUY_MTHD"); //학습방식
            cdDtlList.add("CMPTN_STND");//수료 기준 - 출석
            cdDtlList.add("CMPTN_JDGMT");//수료 기준 - 평가
            cdDtlList.add("STDUY_DD");//학습시간 - 학습일
            cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간
            cdDtlList.add("LCNS_CNNCT");//자격증연계코드

            cdDtlList.add("EDCTN_REL");//교욱과정 연계(선수, 후속)

            modelMap.addAttribute("studyCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

            //과정의 대분류 코드를 호출한다
            if(rtnDto != null && rtnDto.getNm() != null){
                cOCodeDTO.setCd(rtnDto.getCtgryCd());
                List<COCodeDTO> tempDTO = cOCodeService.getCdIdPrntList(cOCodeDTO);

                String prntCd = "";
                for(COCodeDTO a : tempDTO){
                    if(a.getDpth() == 2){
                        prntCd = a.getCd();
                        break;
                    }
                }
                rtnDto.setPrntCd(prntCd);
            }

            modelMap.addAttribute("rtnData", rtnDto);//과정 기본정보
            modelMap.addAttribute("rtnEpisdList", rtnEpisdList);//과정에 소속된 차수목록
            modelMap.addAttribute("rtnTrgtData", rtnTrgtData);//학습 대상 목록
            modelMap.addAttribute("relList", relList);//과정 연계 목록

            modelMap.addAttribute("relList1", rtnMap.get("relList1"));//과정 연계 목록 - 선수목록
            modelMap.addAttribute("relList2", rtnMap.get("relList2"));//과정 연계 목록 - 후속목록

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

    /*
    학습대상 공통코드 분류
     */
    private List<EmfMap> setEdTargetList(String arg){

        List<EmfMap> targetList = new ArrayList<>();

        try{

            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            // 코드 set
            cdDtlList.add(arg);

            HashMap<String, List<COCodeDTO>> temp =  cOCodeService.getCmmCodeBindAll(cdDtlList);

            List<COCodeDTO> tempList = temp.get(arg);

            for(COCodeDTO a : tempList){

                if(a.getDpth() == 2){
                    EmfMap targetMap = new EmfMap();

                    List<EmfMap> dpth3List = new ArrayList<>();
                    for(COCodeDTO b : tempList){
                        if(b.getCd().contains(a.getCd())){
                            EmfMap map = new EmfMap();

                            map.put("cd", b.getCd());
                            map.put("cdNm", b.getCdNm());
                            map.put("dpth", b.getDpth());
                            dpth3List.add(map);
                            targetMap.put("edList", dpth3List);
                        }
                    }
                    targetList.add(targetMap);
                }


            }


        }catch (Exception e){

        }


        return targetList;

    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/education")
    public class EBACouseRestController {

        private final COCodeService cOCodeService;

        /**
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/classTypeList")
        public List<COCodeDTO> classTypeList(@RequestBody COCodeDTO cOCodeDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            List<COCodeDTO> detailList = null;
            try
            {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }

    }

}

