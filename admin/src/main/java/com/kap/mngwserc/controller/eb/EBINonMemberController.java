package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.eb.ebb.EBBBdgetDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebi.EBINonMemberDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBINonMemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 비회원 교육 과정 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBINonMemberController.java
 * @Description		: 비회원 교육 과정 관리를 위한 Controller
 * @author 장두석
 * @since 2023.12.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.15		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebi")
public class EBINonMemberController {

    /** 서비스 **/
    public final EBINonMemberService eBINonMemberService;

    public final COCodeService cOCodeService;

    /**
     *  비회원 교육 과정 관리 목록 페이지
     */
    @GetMapping(value="/list")
    public String getNonMemberPage(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", eBINonMemberService.selectNonMemberList(pEBINonMemberDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE"); //과정 분류
        cdDtlList.add("STDUY_DD"); //학습 시간 - 학습일
        cdDtlList.add("STDUY_TIME"); //학습 시간 - 학습 시간


        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        return "mngwserc/eb/ebi/EBINonMemberList.admin";
    }

    /**
     * 비회원 교육 과정 관리 목록 조회
     */
    @RequestMapping(value = "/select")
    public String getNonMemberPageAjax(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBINonMemberService.selectNonMemberList(pEBINonMemberDTO));
            modelMap.addAttribute("EBINonMemberDTO", pEBINonMemberDTO);


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebi/EBINonMemberListAjax";
    }

    /**
     * 비회원 교육 과정 관리 목록 excel 다운로드
     */
    @GetMapping(value = "/excel-down")
    public void getNonMemberExcel(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            pEBINonMemberDTO.setExcelYn("Y");

            // 목록 조회
            EBINonMemberDTO ebbExcelListDto = eBINonMemberService.selectNonMemberExcelList(pEBINonMemberDTO);

            //엑셀 생성
            eBINonMemberService.excelDownload1(ebbExcelListDto, response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 비회원 교육 신청자 목록 excel 다운로드
     */
    @GetMapping(value = "/excel-down2")
    public void getPtcptExcel(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            pEBINonMemberDTO.setExcelYn("Y");
            // 목록 조회
            EBINonMemberDTO ebbExcelListDto = eBINonMemberService.setPtcptList(pEBINonMemberDTO);

            //엑셀 생성
            eBINonMemberService.excelDownload2(ebbExcelListDto, response);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 비회원 교육 과정 관리 상세 조회
     */
    @GetMapping(value="/write")
    public String getEPisdDtl(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap) throws Exception
    {
        HashMap<String, Object> rtnMap = eBINonMemberService.selectNonMemberDtl(pEBINonMemberDTO);

        EBINonMemberDTO rtnDto = (EBINonMemberDTO)rtnMap.get("rtnData");
        EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto"); //교육장 정보
        List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList"); //강사 목록
        List<EBBBdgetDTO> bdgetList = (List<EBBBdgetDTO>) rtnMap.get("bdgetList"); //예산/지출관리 목록
        List<EBINonMemberDTO> ptcptList = (List<EBINonMemberDTO>) rtnMap.get("ptcptList"); //교육참여자 목록
        List<EBINonMemberDTO> rtnTrgtData = (List<EBINonMemberDTO>) rtnMap.get("rtnTrgtData"); //학습 대상 목록


        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();

        // 코드 set
        cdDtlList.add("CO_YEAR_CD");//연도 공통코드
        cdDtlList.add("SYSTEM_HOUR"); //시간
        modelMap.addAttribute("episdCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        cdDtlList.add("CLASS_TYPE"); //과정 분류
        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        cdDtlList.add("STDUY_DD"); //학습 시간 - 학습일
        cdDtlList.add("STDUY_TIME"); //학습 시간 - 학습 시간
        modelMap.addAttribute("studyCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();

        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));
        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));
        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("ED_BDGET_CD01");//예산지출 코드 - 예산
        modelMap.addAttribute("ED_BDGET_CD01", cOCodeService.getCdIdList(cOCodeDTO));
        cOCodeDTO.setCd("ED_BDGET_CD02");//예산지출 코드 - 지출
        modelMap.addAttribute("ED_BDGET_CD02", cOCodeService.getCdIdList(cOCodeDTO));

        modelMap.addAttribute("rtnData", rtnDto); //비회원 교육 과정 본문
        modelMap.addAttribute("roomDto", roomDto); //교육장 정보
        modelMap.addAttribute("isttrList", isttrList); //강사정보
        modelMap.addAttribute("bdgetList", bdgetList); //예산지출목록
        modelMap.addAttribute("ptcptList", ptcptList); //교육 참여자 목록
        modelMap.addAttribute("rtnTrgtData", rtnTrgtData); //학습 대상 목록
        modelMap.addAttribute("edTarget", setEdTargetList("ED_TARGET")); //학습대상 공통코드 호출

        //복사유무
        if(pEBINonMemberDTO.getCopyYn().equals("Y")) {
            rtnDto.setCopyYn("Y");
            rtnDto.setExpsYn("N");
            rtnDto.setEdctnSeq(null);
            rtnDto.setEdctnNtctnFileSeq(null);
            rtnDto.setThnlFileSeq(null);

            rtnDto.setExpnsPmt(null);
            rtnDto.setExpnsCprtnInsttNm(null);
            rtnDto.setBdgetExpnsYn("N");

            modelMap.addAttribute("bdgetList", null);//예산지출목록
            modelMap.addAttribute("ptcptList", null);//교육 참여자 목록
        }

        return "mngwserc/eb/ebi/EBINonMemberWrite.admin";
    }

    /*
    학습대상 공통코드 분류
     */
    private List<EmfMap> setEdTargetList(String arg)
    {
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
     * 교육 신청자 목록 호출
     */
    @GetMapping(value = "/nonMemberPtcptList")
    public String getPtcptListPageAjax(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBINonMemberService.setPtcptList(pEBINonMemberDTO));
            modelMap.addAttribute("EBINonMemberDTO", pEBINonMemberDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebi/EBINonMemberPtcptListAjax";
    }

    /**
     * 비회원 교육 과정 관리 > 신청자 등록 화면 호출
     */
    @RequestMapping(value = "/ptcpt/write")
    public String ptcptSelect(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            //비회원 교육 과정 정보
            HashMap<String, Object> rtnMap = eBINonMemberService.selectNonMemberDtl(pEBINonMemberDTO);

            EBINonMemberDTO rtnDto = (EBINonMemberDTO)rtnMap.get("rtnData");//비회원 교육 과정 정보
            EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");//교육장 정보
            List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList");//강사 목록

            modelMap.addAttribute("rtnData", rtnDto);//비회원 교육 과정 정보
            modelMap.addAttribute("isttrList", isttrList);//강사 정보
            modelMap.addAttribute("roomDto", roomDto);//교육장 정보

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD"); //회원 코드 (직급, 부서 등)
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebi/EBIPtcptWrite.admin";
    }


    @Operation(summary = "비회원 교육 신청자 등록", tags = "비회원 교육 신청자 등록", description = "")
    @PostMapping(value="/ptcpt/setPtcptInfo")
    public String setPtcptInfo(EBINonMemberDTO pEBINonMemberDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        //비회원 교육 신청자를 등록, 등록할 때 이미 회원이 있으면 취소

        EBINonMemberDTO temoDto = new EBINonMemberDTO();

        try {
            temoDto = eBINonMemberService.setPtcptInfo(pEBINonMemberDTO, request);

            modelMap.addAttribute("rtnData", temoDto);
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



    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/ebi")
    public class EBINonMemberRestController {

        private final COCodeService cOCodeService;

        @Operation(summary = "비회원 교육 과정 등록", tags = "비회원 교육 과정 등록", description = "비회원 교육 과정, 교육장소, 강사, 과정내용")
        @PostMapping(value="/insert")
        public int insertNonMember(@RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            try{
                pEBINonMemberDTO.setRespCnt(eBINonMemberService.insertNonMember(pEBINonMemberDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pEBINonMemberDTO.getRespCnt();
        }

        @Operation(summary = "비회원 교육 과정 수정", tags = "비회원 교육 과정 수정", description = "비회원 교육 과정, 교육장소, 강사, 과정내용")
        @PostMapping(value="/update")
        public int updateNonMember(@Valid @RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            try{
                pEBINonMemberDTO.setRespCnt(eBINonMemberService.updateNonMember(pEBINonMemberDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pEBINonMemberDTO.getRespCnt();
        }

        @Operation(summary = "비회원 교육 신청자 정원체크", tags = "비회원 교육 신청 정원체크", description = "")
        @PostMapping(value="/fxnumChk")
        public EBINonMemberDTO selectFxnumChk(@Valid @RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            EBINonMemberDTO tempDto = new EBINonMemberDTO();
            try {
                tempDto = eBINonMemberService.selectFxnumChk(pEBINonMemberDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return tempDto;
        }

        /**
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/classTypeList")
        public List<COCodeDTO> classTypeList(@RequestBody COCodeDTO cOCodeDTO, ModelMap modelMap) throws Exception {
            List<COCodeDTO> detailList = null;
            try {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }

        @Operation(summary = "비회원 교육 과정 삭제 체크", tags = "비회원 교육 과정 삭제 체크", description = "")
        @PostMapping(value="/nonMemberDeleteChk")
        public EBINonMemberDTO nonMemberDeleteChk(@RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {

            EBINonMemberDTO tempDto = new EBINonMemberDTO();

            try {

                //선택한 과정에 신청자가 있는지 체크
                //없으면 삭제 진행
                //있으면 삭제불가 메시지 반환 삭제성공 :S, 삭제불가(참여자존재) : F

                tempDto = eBINonMemberService.deleteNonMember(pEBINonMemberDTO);

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return tempDto;
        }

        @Operation(summary = "비회원 교육 과정 목록 신청자 신청 취소", tags = "비회원 교육 과정 목록 신청자 신청 취소", description = "")
        @PostMapping(value="/updatePtcptList")
        public EBINonMemberDTO updatePtcptList(@RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            try {

                pEBINonMemberDTO.setRespCnt(eBINonMemberService.updatePtcptList(pEBINonMemberDTO));

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pEBINonMemberDTO;
        }

        @Operation(summary = "비회원 교육 과정 신청자 신청 취소", tags = "비회원 교육 과정 신청자 신청 취소", description = "")
        @PostMapping(value="/updatePtcpt")
        public EBINonMemberDTO updatePtcpt(@RequestBody EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception
        {
            try {

                pEBINonMemberDTO.setRespCnt(eBINonMemberService.updatePtcpt(pEBINonMemberDTO, request));

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pEBINonMemberDTO;
        }


    }

}

