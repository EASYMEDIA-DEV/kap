package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.*;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBACouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육과정관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/eba")
public class EBACouseController {

    /** 서비스 **/
    public final EBACouseService eBACouseService;

    public final COCodeService cOCodeService;

    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCouseList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        return "mngwserc/eb/eba/EBACouseList.admin";
    }



    /**
     * 교육과정관리 목록을 조회한다.
     */
    @GetMapping(value = "/select")
    public String getCouseListPageAjax(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));
            modelMap.addAttribute("eBACouseDTO", eBACouseDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/eba/EBACouseListAjax";
    }


    /**
     * 교육과정관리  상세를 조회한다.
     */
    @GetMapping(value="/write")
    public String getCouseDtl(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);

        EBACouseDTO rtnDto = (EBACouseDTO)rtnMap.get("rtnData");
        List<EBACouseDTO> rtnTrgtData = (List<EBACouseDTO>) rtnMap.get("rtnTrgtData");

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

        //복사유무
        if(eBACouseDTO.getCopyYn().equals("Y")){

            rtnDto.setCopyYn("Y");
            rtnDto.setExpsYn("N");
            rtnDto.setEdctnSeq(null);
            rtnDto.setThnlFileSeq(null);
        }


        System.out.println("@@@ relList = " + relList);

        modelMap.addAttribute("rtnData", rtnDto);
        modelMap.addAttribute("rtnTrgtData", rtnTrgtData);
        modelMap.addAttribute("relList", relList);


        //학습대상 공통코드 호출
        modelMap.addAttribute("edTarget", setEdTargetList("ED_TARGET"));

        return "mngwserc/eb/eba/EBACouseWrite.admin";
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


    /**
     * 교육과정관리를 등록한다.
     */
    @PostMapping(value="/insert")
    public String getCouseInsert(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            modelMap.addAttribute("respCnt", eBACouseService.insertCouse(eBACouseDTO));
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

    /**
     * 교육과정관리를 수정한다.
     */
    @PostMapping(value="/update")
    public String updateBoard(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {*/
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            modelMap.addAttribute("respCnt", eBACouseService.updateCouse(eBACouseDTO));
        /*}
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }

            throw new Exception(e.getMessage());
        }*/


        return "jsonView";
    }

    /**
     * 교육과정관리 삭제 전 차수 체크
     */
    @PostMapping(value="/deleteChk")
    public String deleteChk(@RequestBody EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            System.out.println("eBACouseDTO" + eBACouseDTO);

            //현재 등록된 교육과정에 종속된 교육차수 체크
            eBACouseDTO.setRespCnt(eBACouseService.selectEpisdListChk(eBACouseDTO));
            modelMap.addAttribute("respCnt", eBACouseDTO.getRespCnt());
            System.out.println("문제 없음1");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        System.out.println("문제 없음2");
        return "jsonView";
    }

    /**
     * 교육과정관리를  삭제한다.
     */
    @PostMapping(value="/delete")
    public String getCouseDelete(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", eBACouseService.deleteCouse(eBACouseDTO));
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


    /**
     * 교육과정관리를 복사한다.
     */
    @GetMapping(value="/copy")
    public EBACouseDTO getCouseCopy(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            //eBACouseDTO.setRespCnt( eBACouseService.copyCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정 검색 팝업
     */



    /**
     * 교육과정 회차정보 조회
     */
    @PostMapping(value="/sessionList")
    public String getSessionList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        return "EBACouseEpisdListAjax";
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/eba")
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

