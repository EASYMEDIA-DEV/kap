package com.kap.front.controller.mp.mpc;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.sv.sva.SVASurveyRspnMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyRspnScoreDTO;
import com.kap.service.*;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.cb.cbb.CBBManageConsultMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 홈 > 마이페이지 > 컨설팅 사업 신청내역 관리 Controller
 * </pre>
 *
 * @ClassName		: MPConsultingController.java
 * @Description		: 컨설팅 사업 신청내역 관리 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.29		임서화				   최초 생성
 * </pre>
 */
@Tag(name = "컨설팅 사업 신청내역", description = "컨설팅 사업 신청내역")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/consulting")
public class MPConsultingController {
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /** 서비스 **/
    private final CBATechGuidanceService cBATechGuidanceService;
    private final CBBManageConsultService cBBManageConsultService;
    private final MPEPartsCompanyService mPEPartsCompanyService;
    private final CBATechGuidanceMapper cBATechGuidanceMapper;

    private final MPConsultingService mPConsultingService;



    private final SVASurveyService sVSurveyService;

    /**
     *  목록
     */
    @GetMapping(value="/list")
    public String getConsultingList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));

            COPaginationUtil page = new COPaginationUtil();
            page.setCurrentPageNo(cBATechGuidanceInsertDTO.getPageIndex());
            page.setRecordCountPerPage(cBATechGuidanceInsertDTO.getListRowSize());
            page.setPageSize(cBATechGuidanceInsertDTO.getPageRowSize());
            cBATechGuidanceInsertDTO.setPageRowSize(10);
            cBATechGuidanceInsertDTO.setFirstIndex(page.getFirstRecordIndex());
            cBATechGuidanceInsertDTO.setRecordCountPerPage(page.getRecordCountPerPage());

            CBATechGuidanceInsertDTO rtnDto = mPConsultingService.selectConsultingList(cBATechGuidanceInsertDTO);

            modelMap.addAttribute("rtnData", rtnDto);

            /*  modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());
            modelMap.addAttribute("param", cBATechGuidanceInsertDTO);*/
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpc/MPConsultingList.front";
    }

    /**
     * 마이페이지 상생 사업 필터
     */
    @RequestMapping(value = "/listAjax")
    public String listAjax(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));

            CBATechGuidanceInsertDTO rtnDto = mPConsultingService.selectConsultingList(cBATechGuidanceInsertDTO);

            modelMap.addAttribute("rtnData", rtnDto.getList());
            modelMap.addAttribute("totalCount", rtnDto.getTotalCount());


            /*modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());*/
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpc/MPConsultingListAjax";
    }

    /**
     *  컨설팅 신청 상세 조회
     */
    @GetMapping(value="/detail")
    public String getConsultingDtl(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "";
        /*try
        {*/
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            CBATechGuidanceInsertDTO tmpDto = cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO);

            if(String.valueOf(cOUserDetailsDTO.getSeq()).equals(tmpDto.getMemSeq())){
                CBATechGuidanceInsertDTO rtnData = cBATechGuidanceService.selectTechGuidanceDtlCheck(cBATechGuidanceInsertDTO);
                int rspnCnt = rtnData.getRspnCnt();
                int srvCnt = rtnData.getSrvCnt();
                cBATechGuidanceInsertDTO.setMemSeq(rtnData.getMemSeq());

                MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
                mPEPartsCompanyDTO.setBsnmNo(tmpDto.getBsnmNo().replace("-", ""));

                if(tmpDto.getCtgryCd().equals("COMPANY01001")){

                    modelMap.addAttribute("cmpnInfo", mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO));
                }

                if(tmpDto.getCnstgCd().equals("CONSULT_GB01")){
                    modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO));
                    modelMap.addAttribute("appctnTypeList", tmpDto.getAppctnTypeList());
                }else{
                    CBBManageConsultInsertDTO cBBManageConsultInsertDTO = new CBBManageConsultInsertDTO();
                    cBBManageConsultInsertDTO.setBsnmNo(cBATechGuidanceInsertDTO.getBsnmNo());
                    cBBManageConsultInsertDTO.setCbsnSeq(cBATechGuidanceInsertDTO.getDetailsKey());
                    cBBManageConsultInsertDTO.setDetailsKey(cBATechGuidanceInsertDTO.getDetailsKey());

                    modelMap.addAttribute("picInfo", cBBManageConsultService.selectOneCnstgPicInfo(cBBManageConsultInsertDTO));
                    modelMap.addAttribute("rtnDto", cBBManageConsultService.selectManageConsultDtl(cBBManageConsultInsertDTO));
                }
                modelMap.addAttribute("rspnCnt", rspnCnt);
                modelMap.addAttribute("srvCnt", srvCnt);

                vwUrl = "front/mp/mpc/MPConsultingDtl.front";

            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }
       /* }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }*/
        return vwUrl;
    }

    /**
     *  컨설팅 신청 상세 조회
     */
    @GetMapping(value="/surveyIndex")
    public String getConsultingSurveyIndex(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("survInfo", cBATechGuidanceService.selectTechGuidanceDtlCheck(cBATechGuidanceInsertDTO));
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));
//            modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpc/MPConsultingSurveyIndex.front";
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/my-page/consulting")
    public class MPConsultingRestController {

        private final CBATechGuidanceMapper cBATechGuidanceMapper;
        private final CBBManageConsultMapper cBBManageConsultMapper;
        /**
         *  신청 분야 상세
         */
        @PostMapping(value = "/appctnType")
        @ResponseBody
        public List<CBATechGuidanceInsertDTO> selectAppctnType(@RequestBody CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception {
            List<CBATechGuidanceInsertDTO> detailList = new ArrayList<>();
            String temp = "";
            try {
            int cnstgSeq = cBATechGuidanceInsertDTO.getCnstgSeq();
            String cnstgCd = cBATechGuidanceInsertDTO.getCnstgCd();
            if(cnstgCd.equals("CONSULT_GB01")){
                detailList = cBATechGuidanceMapper.selectCnstgAppctnType(cnstgSeq);
            }
                for(int i = 0; i<detailList.size(); i++){
                    temp += "/"+detailList.get(i).getAppctnTypeNm();
                }
                detailList.get(0).setAppctnTypeCd(temp);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }
    }


    /**
     * 설문step2
     */
    @GetMapping(value="/surveyStep2")
    public String getSurveyStep2(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/mp/mpc/MPConsultingSurveyStep2.front";
        try{

            CBATechGuidanceInsertDTO rtnData = cBATechGuidanceService.selectTechGuidanceDtlCheck(cBATechGuidanceInsertDTO);
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

            if (Integer.toString(cOLoginUserDTO.getSeq()).equals(rtnData.getMemSeq())){

                if (rtnData.getRspnCnt() > 0 ){
                    modelMap.addAttribute("msg", "이미 제출한 설문입니다.");
                    modelMap.addAttribute("url", "/");
                    vwUrl = "front/COBlank.error";
                }else{
                    SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                    sVASurveyDTO.setDetailsKey(cBATechGuidanceInsertDTO.getDetailsKey());

                    sVASurveyDTO.setTypeCd("CON");
                    SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeConDtl(sVASurveyDTO);

                    if (sVASurveyMstInsertDTO != null){
                        modelMap.addAttribute("rtnData", rtnData);
                        modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);
                    }else{
                        modelMap.addAttribute("msg", "잘못된 접근입니다.");
                        modelMap.addAttribute("url", "/");
                        vwUrl = "front/COBlank.error";
                    }
                }
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }
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

    @Operation(summary = "응답 등록", tags = "", description = "응답 마스터, 응답 상세")
    @PostMapping(value="/insert")
    public String insertSurveyList(@Valid @RequestBody SVASurveyRspnMstInsertDTO sVASurveyRspnMstDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {

            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            String regIp = CONetworkUtil.getMyIPaddress(request);
            String regId = cOLoginUserDTO.getId();

            sVASurveyRspnMstDTO.setRegId(regId);
            sVASurveyRspnMstDTO.setName(sVASurveyRspnMstDTO.getPtcptName());
            sVASurveyRspnMstDTO.setEmail(sVASurveyRspnMstDTO.getPtcptEmail());
            int respCnt = sVSurveyService.insertSurveyRspnList(sVASurveyRspnMstDTO, request);

            if (respCnt > 0){


                SVASurveyRspnScoreDTO sVASurveyRspnScoreDTO = new SVASurveyRspnScoreDTO();
                sVASurveyRspnScoreDTO.setSrvType("CON");
                sVASurveyRspnScoreDTO.setPtcptName(sVASurveyRspnMstDTO.getPtcptName());
                sVASurveyRspnScoreDTO.setPtcptTelno(sVASurveyRspnMstDTO.getPtcptTelno());
                sVASurveyRspnScoreDTO.setPtcptPstn(sVASurveyRspnMstDTO.getPtcptPstn());
                sVASurveyRspnScoreDTO.setTargetSeq(sVASurveyRspnMstDTO.getRfncSeq());
                sVASurveyRspnScoreDTO.setRegId(regId);
                sVASurveyRspnScoreDTO.setRegIp(regIp);

                sVSurveyService.selectSurveyScore(sVASurveyRspnScoreDTO);

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

    /**
     * 설문step2
     */
    @GetMapping(value="/surveyStep3")
    public String getSurveyStep3(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "";
        try
        {
            CBATechGuidanceInsertDTO rtnData = cBATechGuidanceService.selectTechGuidanceDtlCheck(cBATechGuidanceInsertDTO);
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            if (Integer.toString(cOLoginUserDTO.getSeq()).equals(rtnData.getMemSeq())){
                vwUrl = "front/mp/mpc/MPConsultingSurveyStep3.front";
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }

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

}

