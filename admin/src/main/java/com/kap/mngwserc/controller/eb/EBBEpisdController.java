package com.kap.mngwserc.controller.eb;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.seed.COBrowserUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.eb.ebb.*;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptMst;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptRspnMst;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육차수관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBBEpisdController.java
 * @Description		: 교육차수관리를 위한 Controller
 * @author 김학규
 * @since 2023.11.02
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
@RequestMapping(value="/mngwserc/eb/ebb")
public class EBBEpisdController {

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    public final COCodeService cOCodeService;

    private final SVASurveyService sVSurveyService;

    /** 평가 서비스 **/
    private final EBEExamService eBEExamService;

    /** 메일 서비스 **/
    private final COMessageService cOMessageService;

    //파일 업로드 경로
    @Value("${app.file.upload-path}")
    private String imgUploadFilePath;
    //사용자 http 경로
    @Value("${app.user-domain}")
    private String appUserDomain;

    /**
     *  교육회차관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getEpisdPage(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간
        cdDtlList.add("RCRMT_MTHD");//학습시간 - 학습시간


        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        return "mngwserc/eb/ebb/EBBEpisdList.admin";
    }

    /**
     * 교육회차관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getEpisdPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
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
        return "mngwserc/eb/ebb/EBBEpisdListAjax";
    }

    /**
     * 교육차수관리 목록을 excel 다운로드한다.
     */
    @GetMapping(value = "/excel-down")
    public void getEpisdPageExcel(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            eBBEpisdDTO.setExcelYn("Y");
            // 목록 조회
            EBBEpisdExcelDTO ebbExcelListDto = eBBEpisdService.selectEpisdExcelList(eBBEpisdDTO);

            //엑셀 생성
            eBBEpisdService.excelDownload1(ebbExcelListDto, response);
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
     * 교육차수관리의 참여자 목록을 excel 다운로드한다.
     */
    @GetMapping(value = "/excel-down2")
    public void getEpisdPtcptPageExcel(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            eBBEpisdDTO.setExcelYn("Y");

            eBBEpisdDTO.setEpisdYear(eBBEpisdDTO.getOrgEpisdYear());
            eBBEpisdDTO.setEpisdOrd(eBBEpisdDTO.getOrgEpisdOrd());
            // 목록 조회
            EBBPtcptDTO ebbExcelListDto = eBBEpisdService.setPtcptList(eBBEpisdDTO);

            //엑셀 생성
            eBBEpisdService.excelDownload2(ebbExcelListDto, response);

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
     * 교육차수관리의 차명자 출석부 목록을 excel 다운로드한다.
     */
    @GetMapping(value = "/excel-down3")
    public void getEpisdPtcptAtndcExcel(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            eBBEpisdDTO.setExcelYn("Y");
            eBBEpisdDTO.setAtndcYn("Y");

            // 목록 조회
            //목록 상단 th에 뿌려줄 날짜 리스트를 구한다.
            EBBPtcptDTO eBBPtcptDTO = eBBEpisdService.setAtndcList(eBBEpisdDTO);

                String getEdctnStrtDtm= eBBEpisdDTO.getEdctnStrtDtm();
                String getEdctnEndDtm= eBBEpisdDTO.getEdctnEndDtm();
                // 시작 날짜와 종료 날짜 설정
                String startDateString = getEdctnStrtDtm.substring(0, 10);
                String endDateString = getEdctnEndDtm.substring(0, 10);

                // 시작 날짜와 종료 날짜를 LocalDate 객체로 변환
                LocalDate startDate = LocalDate.parse(startDateString);
                LocalDate endDate = LocalDate.parse(endDateString);
                // 날짜 출력 형식 지정
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // 반복문을 통해 날짜 출력
                List<EBBPtcptDTO> tableAtndcList = new ArrayList();

                while (!startDate.isAfter(endDate)) {

                    if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {

                    } else {
                        EBBPtcptDTO tableAtndcDto = new EBBPtcptDTO();

                        tableAtndcDto.setEdctnDt(String.valueOf(startDate));
                        tableAtndcList.add(tableAtndcDto);
                    }



                    startDate = startDate.plusDays(1); // 다음 날짜로 이동
                }


            //tableAtndcList;
            //교육 참여자 출석부 th부분 날짜 세팅용 종료

            //엑셀 생성
            eBBEpisdService.excelDownload3(tableAtndcList, eBBPtcptDTO, response);
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
     * 교육회차관리 목록을 조회한다. (교육차수관리 차수변경용 버전)
     */
    @RequestMapping(value = "/selectChangeList")
    public String getEpisdChangePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            System.out.println("@@@ eBBEpisdDTO = " + eBBEpisdDTO);
            eBBEpisdDTO.setChangeListYn("Y");
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
        return "mngwserc/eb/ebb/EBBEpisdChangeListAjax";
    }

    /**
     * 교육차수관리  상세를 조회한다.
     */
    @GetMapping(value="/write")
    public String getEPisdDtl(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");
        EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");//교육장 정보
        List<EBBLctrDTO> lctrDtoList = (List<EBBLctrDTO>) rtnMap.get("lctrDtoList");//온라인교육상세 목록
        List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList");//온라인교육상세 목록
        List<EBBBdgetDTO> bdgetList = (List<EBBBdgetDTO>) rtnMap.get("bdgetList");//예산/지출관리 목록


        EBBSrvRstDTO srvScoreDtl = (EBBSrvRstDTO)rtnMap.get("srvScoreDtl");//만족도 결과 점수
        EBBSrvRstDTO srvRstDtl = (EBBSrvRstDTO)rtnMap.get("srvRstDtl");//만족도 결과 부서, 직급별 인원 통계

        List<EBBPtcptDTO> ptcptList = (List<EBBPtcptDTO>) rtnMap.get("ptcptList");//교육참여자 목록




        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간

        cdDtlList.add("ROUND_CD");//회차정보 공통코드
        cdDtlList.add("CO_YEAR_CD");//연도 공통코드

        cdDtlList.add("RCRMT_MTHD"); //모집방식
        cdDtlList.add("CMPTN_AUTO"); //수료 자동화 여부
        cdDtlList.add("CBSN_CD"); //업종코드
        cdDtlList.add("SYSTEM_HOUR"); //시간

        modelMap.addAttribute("episdCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("ROUND_CD");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

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

        modelMap.addAttribute("rtnData", rtnDto);//교육차수 본문
        modelMap.addAttribute("roomDto", roomDto);//교육장 정보
        modelMap.addAttribute("lctrDtoList", lctrDtoList);//온라인강의
        modelMap.addAttribute("isttrList", isttrList);//강사정보
        modelMap.addAttribute("bdgetList", bdgetList);//예산지출목록
        modelMap.addAttribute("srvScoreDtl", srvScoreDtl);//만족도 결과 점수
        modelMap.addAttribute("srvRstDtl", srvRstDtl);//만족도 결과 부서, 직급별 인원 통계

        modelMap.addAttribute("ptcptList", ptcptList);//교육 참여자 목록


        //교육 참여자 출석부 th부분 날짜 세팅용 시작

        if(rtnDto != null){
            String getEdctnStrtDtm= rtnDto.getEdctnStrtDtm();
            String getEdctnEndDtm= rtnDto.getEdctnEndDtm();
            // 시작 날짜와 종료 날짜 설정
            String startDateString = getEdctnStrtDtm.substring(0, 10);
            String endDateString = getEdctnEndDtm.substring(0, 10);

            // 시작 날짜와 종료 날짜를 LocalDate 객체로 변환
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            // 날짜 출력 형식 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 반복문을 통해 날짜 출력
            List<EBBPtcptDTO> tableAtndcList = new ArrayList();

            while (!startDate.isAfter(endDate)) {


                // 주말인지 판단하여 출력
                if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {

                } else {
                    EBBPtcptDTO tableAtndcDto = new EBBPtcptDTO();

                    tableAtndcDto.setEdctnDt(String.valueOf(startDate));
                    tableAtndcList.add(tableAtndcDto);
                }




                startDate = startDate.plusDays(1); // 다음 날짜로 이동
            }
            modelMap.addAttribute("tableAtndcList", tableAtndcList);
            //교육 참여자 출석부 th부분 날짜 세팅용 종료

            //만족도 조사(설문)
            if (!"".equals(rtnDto.getSrvSeq()) && rtnDto.getSrvSeq() != null){
                SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                sVASurveyDTO.setDetailsKey(Integer.toString(rtnDto.getSrvSeq()));
                sVASurveyDTO.setRfncSeq(rtnDto.getEpisdSeq());
                sVASurveyDTO.setEpisdSeq(rtnDto.getEpisdSeq());
                sVASurveyDTO.setTypeCd("EDU");

                SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeEduDtl(sVASurveyDTO);
                if (sVASurveyMstInsertDTO != null){
                    modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);
                }
            }
        }




        //복사유무
        if(eBBEpisdDTO.getCopyYn().equals("Y")) {

            rtnDto.setCopyYn("Y");
            rtnDto.setExpsYn("N");
            rtnDto.setEpisdSeq(null);
            rtnDto.setEpisdYear(null);
            rtnDto.setEpisdOrd(null);
            rtnDto.setEdctnNtctnFileSeq(null);

            rtnDto.setExpnsPmt(null);
            rtnDto.setExpnsCprtnInsttNm(null);
            rtnDto.setBdgetExpnsYn("N");

            for (EBBLctrDTO temp : lctrDtoList){
                temp.setThnlFileSeq(null);
            }

            modelMap.addAttribute("lctrDtoList", lctrDtoList);//온라인강의
            modelMap.addAttribute("tableAtndcList", null);
            modelMap.addAttribute("rtnSurveyData", null);//설문데이터

            modelMap.addAttribute("bdgetList", null);//예산지출목록
            modelMap.addAttribute("srvRstDtl", null);//만족도 결과 부서, 직급별 인원 통계
            modelMap.addAttribute("ptcptList", null);//교육 참여자 목록


        }


        return "mngwserc/eb/ebb/EBBEpisdWrite.admin";
    }

    /**
     * 교육참여자 목록을 호출한다.
     */
    @PostMapping(value = "/episdPtcptList")
    public String getPtcptListPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //페이징 처리시 episdYear값이 없는경우가 있음, 그래서 org의 값을 넣어줌
            if(eBBEpisdDTO.getEpisdYear() == null){
                eBBEpisdDTO.setEpisdYear(eBBEpisdDTO.getOrgEpisdYear());
            }

            if(eBBEpisdDTO.getEpisdOrd() != null && eBBEpisdDTO.getOrgEpisdOrd() != null) {
                if (!eBBEpisdDTO.getEpisdOrd().equals(eBBEpisdDTO.getOrgEpisdOrd())) {
                    eBBEpisdDTO.setEpisdOrd(eBBEpisdDTO.getOrgEpisdOrd());
                }
            }

            modelMap.addAttribute("rtnData", eBBEpisdService.setPtcptList(eBBEpisdDTO));
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
        return "mngwserc/eb/ebb/EBBEpisdPtcptListAjax";
    }

    /**
     * 교육참여자 시험 평가 상세
     */
    @PostMapping(value = "/getExamUserDtl")
    public String getExamUserDtl(@RequestParam(required = true) int ptcptSeq, @RequestParam(required = true) int memSeq, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //시험 항목 조회
            EXGExamMstSearchDTO eXGExamMstSearchDTO = EXGExamMstSearchDTO.builder()
                                                      .ptcptSeq(ptcptSeq)
                                                      .memSeq(memSeq).build();
            EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(eXGExamMstSearchDTO);
            eXGExamMstSearchDTO.setDetailsKey( String.valueOf(eXGExamEdctnPtcptMst.getExamSeq()) );
            modelMap.addAttribute("rtnData", eXGExamEdctnPtcptMst);
            modelMap.addAttribute("rtnExamData", eBEExamService.selectExamRspnDtl(eXGExamMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/ex/exg/EXGExamUserDtl";
    }

    /**
     * 교육참여자 출석부 목록을 호출한다.
     */
    @GetMapping(value = "/episdAtndcList")
    public String getEpisdAtndcList(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //목록 상단 th에 뿌려줄 날짜 리스트를 구한다.
            eBBEpisdDTO.setAtndcYn("Y");
            EBBPtcptDTO eBBPtcptDTO = eBBEpisdService.setAtndcList(eBBEpisdDTO);

            modelMap.addAttribute("rtnData", eBBPtcptDTO);
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
        return "mngwserc/eb/ebb/EBBEpisdAtndcListAjax";
    }

    /**
     * 교육차수관리 > 신청자 등록 화면을 호출한다.
     */
    @RequestMapping(value = "/ptcpt/write")
    public String ptcptSelect(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            //화차정보
            HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

            EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");//회차정보
            EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");//교육장 정보
            List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList");//온라인교육상세 목록

            modelMap.addAttribute("rtnData", rtnDto);//교육차수 본문
            modelMap.addAttribute("isttrList", isttrList);//교육차수 본문

            modelMap.addAttribute("roomDto", roomDto);//교육장 정보

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebb/EBBPtcptWrite.admin";
    }


    @Operation(summary = "교육차수 신청자 등록", tags = "교육차수 신청자 등록", description = "")
    @PostMapping(value="/ptcpt/setPtcptInfo")
    public String setPtcptInfo(EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap) throws Exception
    {

        //교육차수 신청자를 등록한다. 등록할때 이미 회원이 있으면 취소
        EBBPtcptDTO temoDto = new EBBPtcptDTO();
        try {

            temoDto = eBBEpisdService.setPtcptInfo(eBBPtcptDTO);

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



    /**
     * 교육참여자 개인 출석부를 호출한다.
     */
    @Operation(summary = "교육참여자 개인 출석부를 호출한다.", tags = "", description = "")
    @PostMapping(value="/memAtndcList")
    public String selectMemAtndcList(EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        List<EBBPtcptDTO> eBBPtcptList = new ArrayList();
        try
        {
            //개인별 출석부를 호출한다. 데이터 양식은 리스트지만 화면 출력은 단건임
            eBBPtcptList = eBBEpisdService.selectMemAtndcList(eBBPtcptDTO);
            modelMap.addAttribute("rtnData", eBBPtcptList);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebb/EBBMemAtndcAjax";
    }

    @Operation(summary = "교육내용 메일발송", tags = "", description = "")
    @PostMapping(value="/setInformSendMail")
    public String setInformSendMail(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            List<String> ptcptMailList = eBBEpisdDTO.getPtcptEmailList();

            String infoFormCntn = eBBEpisdDTO.getInformCntn();
            infoFormCntn = COWebUtil.clearXSSMinimum(infoFormCntn);

            for(String ptcptEmail : ptcptMailList){

                //교육내용안내 메일발송 시작
                COMailDTO cOMailDTO = new COMailDTO();
                cOMailDTO.setSubject("[KAP] 교육 내용 안내");

                COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                receiverDto.setEmail(ptcptEmail);

                cOMailDTO.setEditorContents(infoFormCntn);
                cOMailDTO.getReceiver().add(receiverDto);

                cOMessageService.sendMail(cOMailDTO, "EBBInformMail.html");

                //교육신청 메일발송 끝
            }

            modelMap.addAttribute("respCnt", 1);
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
     * 교육 차수 QR 이미지 다운로드
     */
    @Operation(summary = "교육 차수 QR 이미지 다운로드.", tags = "", description = "")
    @GetMapping(value="/qr-image-download")
    public void getQrImageDownload(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        //제목만 필요해서 강제 변경
        eBBEpisdDTO.setSiteGubun("front");
        eBBEpisdDTO.setExpsYn("N"); // 미노출인 경우에도 QR 이미지 다운로드될 수 있도록 수정
        HashMap rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);
        String episdNm = ((EBBEpisdDTO)rtnMap.get("rtnData")).getNm();
        String savePath = imgUploadFilePath + "upload/temp/qr";
        String currentDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        File file = new File(savePath);
        //파일 경로가 없으면 파일 생성
        if (!file.exists()) {
            file.mkdirs();
        }
        //QR 생성 HTTP URL

        String content = appUserDomain + "/episd/qr-image-check?detailsKey="+eBBEpisdDTO.getDetailsKey()+"&episdYear="+eBBEpisdDTO.getEpisdYear()+"&episdOrd=" + eBBEpisdDTO.getEpisdOrd()+"&regDtm="+currentDt;
        //String content = "https://192.168.0.109:9014/episd/qr-image-check?detailsKey="+eBBEpisdDTO.getDetailsKey()+"&episdYear="+eBBEpisdDTO.getEpisdYear()+"&episdOrd=" + eBBEpisdDTO.getEpisdOrd()+"&regDtm="+currentDt;
        String fileName = "";
        File temp = null;
        try
        {
            //QR 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 500, 500);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            //yyyyMMddHHmmss 형식의 날짜 및 시간 정보 파일명에 추가
            fileName = episdNm + "_" + currentDt + ".png";
            //파일 생성
            temp = new File(savePath + "/" + fileName );
            //ImageIO를 사용하여 파일쓰기
            ImageIO.write(bufferedImage, "png", temp);
        }
        catch (IOException | WriterException e)
        {
            e.printStackTrace();
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        long fSize = temp.length();
        log.error("fSize : {}", fSize);
        String userAgent = COWebUtil.removeCRLF(request.getHeader("User-Agent"));
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", COBrowserUtil.getDisposition(fileName, userAgent, "UTF-8"));
        response.setContentLengthLong(fSize);
        try {
            in = new BufferedInputStream(new FileInputStream(temp));
            out = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(in, out);
            out.flush();
        }
        catch (IOException ex) {
            // 다음 Exception 무시 처리
            // Connection reset by peer: socket write error
            // EgovBasiclog.ignore("IO Exception", ex);
            if (ex == null) {
                log.error("IO Exception");
            } else {
                log.error("IO Exception", ex);
            }

        }
        finally {
            in.close();
            out.close();
            // ResourceCloseHelper.close(in, out);
        }
    }



    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/ebb")
    public class EBBEpisdRestController {

        private final COCodeService cOCodeService;

        @Operation(summary = "교육차수 등록", tags = "교육차수 등록", description = "교육차수, 교육장소, 강사, 온라인 강의 목차")
        @PostMapping(value="/insert")
        public EBBEpisdDTO insertEpisd(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            try{

                eBBEpisdService.insertEpisd(eBBEpisdDTO);

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eBBEpisdDTO;
        }

        @Operation(summary = "교육차수 수정", tags = "교육차수 수정", description = "교육차수, 교육장소, 강사, 온라인 강의 목차")
        @PostMapping(value="/update")
        public EBBEpisdDTO updateEpisd(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            try{

                eBBEpisdService.updateEpisd(eBBEpisdDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eBBEpisdDTO;
        }

        @Operation(summary = "교육 답변 주관식 수정", tags = "평가지", description = "교육 답변 등록")
        @PostMapping(value="/sbjct-update")
        public EXGExamEdctnPtcptRspnMst updateExamSbjctReply(@RequestBody @Valid  EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, HttpServletRequest request) throws Exception
        {
            try
            {
                EXGExamMstSearchDTO eXGExamMstSearchDTO = EXGExamMstSearchDTO.builder()
                        .ptcptSeq(eXGExamEdctnPtcptRspnMst.getPtcptSeq())
                        .memSeq(eXGExamEdctnPtcptRspnMst.getMemSeq()).build();
                EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst = eBEExamService.selectUserExamDtl(eXGExamMstSearchDTO);
                //시험 조건
                if(eXGExamEdctnPtcptMst == null){
                    throw new BusinessException(ErrorCode.CANNOT_READ);
                }
                //시험 매핑
                if(eXGExamEdctnPtcptMst.getExamSeq() == null){
                    throw new BusinessException(ErrorCode.CANNOT_READ);
                }
                //채점
                eXGExamEdctnPtcptRspnMst.setRespCnt( eBEExamService.updateEdctnSbjctRspn(eXGExamEdctnPtcptRspnMst, eXGExamEdctnPtcptMst, request) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            return eXGExamEdctnPtcptRspnMst;
        }

        @Operation(summary = "교육차수 차수변경", tags = "교육차수 신청자 등록", description = "")
        @PostMapping(value="/changeEpisd")
        public EBBPtcptDTO changeEpisd(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap) throws Exception
        {

            int rtnCnt = 0;
            //교육차수 신청자를 등록한다. 등록할때 이미 회원이 있으면 취소
            EBBPtcptDTO temoDto = new EBBPtcptDTO();
            try {

                rtnCnt = eBBEpisdService.changeEpisd(eBBEpisdDTO);
                temoDto.setRespCnt(rtnCnt);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return temoDto;
        }

        @Operation(summary = "교육차수 중복체크", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/selectChk")
        public EBBEpisdDTO selectEpisdChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            EBBEpisdDTO tempDto = new EBBEpisdDTO();
            int actCnt = 0;
            try {

                tempDto = eBBEpisdService.selectEpisdChk(eBBEpisdDTO);

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

        @Operation(summary = "교육차수 강제 종강처리", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/endEdu")
        public EBBEpisdDTO setEndEdu(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            EBBEpisdDTO tempDto = new EBBEpisdDTO();
            int actCnt = 0;
            try {

                actCnt = eBBEpisdService.updateEpisdEndEdu(eBBEpisdDTO);

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



        @Operation(summary = "교육차수 신청자 정원체크", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/fxnumChk")
        public EBBEpisdDTO selectFxnumChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {

            EBBEpisdDTO tempDto = new EBBEpisdDTO();

            try {

                tempDto = eBBEpisdService.selectFxnumChk(eBBEpisdDTO);

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

        @Operation(summary = "교육차수 삭제 체크", tags = "교육차수 삭제 체크", description = "")
        @PostMapping(value="/episdDeleteChk")
        public EBBEpisdDTO episdDeleteChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {

            EBBEpisdDTO tempDto = new EBBEpisdDTO();

            try {

                //선택한 차수가 참여자가 있는지 체크한다
                //없으면 삭제 진행
                //있으면 삭제불가 메시지 반환 삭제성공 :S, 삭제불가(참여자존재) : F

                tempDto = eBBEpisdService.deleteEpisd(eBBEpisdDTO);

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

        @Operation(summary = "교육차수 참여자 출석부 수정", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/updateAtndc")
        public int updateAtndcList(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {

            int rtnCnt = 0;

            try {

                rtnCnt = eBBEpisdService.updateAtndcList(eBBEpisdDTO);

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return rtnCnt;
        }

    }


    @Operation(summary = "설문 초기화", tags = "", description = "")
    @PostMapping(value="/deleteSurveyRspn")
    public String deleteSurveyRspn(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", eBBEpisdService.deleteSurveyRspn(eBBEpisdDTO));
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


    @Operation(summary = "설문 개수 조회", tags = "", description = "")
    @PostMapping(value="/checkSurveyCnt")
    public String checkSurveyCnt(@RequestBody EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", eBBEpisdService.checkSurveyCnt(eBBEpisdDTO));
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


    @Operation(summary = "평가 참여 개수 조회", tags = "", description = "")
    @PostMapping(value="/checkExamCnt")
    public String checkExamCnt(@RequestBody EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", eBBEpisdService.checkExamCnt(eBBEpisdDTO));
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

