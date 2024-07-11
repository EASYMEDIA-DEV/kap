package com.kap.mngwserc.controller.wb.wbl;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.COSmsDTO;
import com.kap.core.dto.sm.smi.SMISmsCntnDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 설문조사 Controller
 * </pre>
 *
 * @ClassName		: SVASurveyController.java
 * @Description		: 상생협력체감도조사 Controller
 * @author 박준희
 * @since 2023.10.31
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		박준희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbl")
public class WBLSurveyController<sVASurveyMstDTO> {

    private final COCodeService cOCodeService;

    private final WBLSurveyService wLSurveyService;

    private final SVASurveyService sVSurveyService;

    /** 메일 서비스 **/
    private final COMessageService cOMessageService;

    // SMS 내용 관리 서비스
    private final SMISmsCntnService smiSmsCntnService;

    private final String profile = System.getProperty("spring.profiles.active");


    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSurveyListPage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PTCPT_CD");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", wBLSurveyMstSearchDTO);
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
            List<String> typeCdList = Arrays.asList(new String[]{ "WIN" });
            List<String> expsYnList = Arrays.asList(new String[]{ "Y" });
            sVASurveyDTO.setTypeCdList(typeCdList);
            sVASurveyDTO.setExpsYnList(expsYnList);

            modelMap.addAttribute("rtnSurveyData", sVSurveyService.selectSurveyList(sVASurveyDTO));

            WBLEpisdMstDTO wBLEpisdMstDTO = new WBLEpisdMstDTO();
            modelMap.addAttribute("rtnEpisdSurveyData", wLSurveyService.selectEpisdSurveyList(wBLEpisdMstDTO));


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/wb/wbl/WBLSurveyList.admin";
    }

    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectSurveyListPageAjax(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wLSurveyService.selectSurveyList(wBLSurveyMstSearchDTO));
            modelMap.addAttribute("searchDto", wBLSurveyMstSearchDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbl/WBLSurveyListAjax";
    }


    /**
     * 게시판 목록 조회
     */
    @PostMapping(value = "/selectEpisd")
    public String selectEpisdListPageAjax(WBLEpisdMstDTO wBLEpisdMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnEpisdData", wLSurveyService.selectEpisdList(wBLEpisdMstDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbl/WBLEpisdLayerAjax";
    }
    /**
     * 쓰기 페이지
     */
    @GetMapping(value="/write")
    public String getSurveyWritePage(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap , @Parameter(description = "상생협력체감조조사 상세 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception{
        try
        {

            modelMap.addAttribute("rtnSrchData", wBLSurveyMstSearchDTO);

            WBLEpisdMstDTO wBLEpisdMstDTO = new WBLEpisdMstDTO();
            modelMap.addAttribute("rtnEpisdSurveyData", wLSurveyService.selectEpisdSurveyList(wBLEpisdMstDTO));


            if(detailsKey != null && detailsKey != ""){

                WBLSurveyMstInsertDTO WBLSurveyMstInsertDTO = wLSurveyService.selectSurveyDtl(wBLSurveyMstSearchDTO);
                modelMap.addAttribute("rtnData", WBLSurveyMstInsertDTO);

                if (!"".equals(WBLSurveyMstInsertDTO.getSrvSeq()) && WBLSurveyMstInsertDTO.getSrvSeq() != null){
                    SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                    sVASurveyDTO.setDetailsKey(Integer.toString(WBLSurveyMstInsertDTO.getSrvSeq()));
                    sVASurveyDTO.setSrvRspnSeq(WBLSurveyMstInsertDTO.getSrvRspnSeq());

                    sVASurveyDTO.setTypeCd("WIN");
                    SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeWinDtl(sVASurveyDTO);
                    if (sVASurveyMstInsertDTO != null){
                        modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);

                        modelMap.addAttribute("profile", profile);


                    }
                }
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

        return "mngwserc/wb/wbl/WBLSurveyWrite.admin";
    }

    @Operation(summary = "상생협력체감도조사 등록", tags = "", description = "")
    @PostMapping(value="/insert")
    public String insertSurveyList(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.insertSurveyList(wBLSurveyMstInsertDTO, request));

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
     * 리스트 삭제
     */
    @Operation(summary = "상생협력체감도조사 삭제", tags = "상생협력체감도조사", description = "")
    @PostMapping(value="/delete")
    public String deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.deleteSurveyList(wBLSurveyMstSearchDTO));
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
     * 리스트 삭제
     */
    @Operation(summary = "회차 삭제", tags = "상생협력체감도조사", description = "")
    @PostMapping(value="/deleteEpisd")
    public String deleteEpisdList(@Valid @RequestBody WBLEpisdMstDTO wBLEpisdMstDTO , ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.deleteEpisdList(wBLEpisdMstDTO,request));
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

    @Operation(summary = "회차 등록", tags = "", description = "")
    @PostMapping(value="/insertEpisd")
    public String insertEpisdList(@Valid @RequestBody WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.insertEpisdList(wBLEpisdMstDTO, request));

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
     * 샘플 엑셀 생성
     */
    @GetMapping(value = "/excelSampleDownload")
    public void excelSampleDownload(COAAdmDTO cOAAdmDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wLSurveyService.excelSampleDownload(response);
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


    @Operation(summary = "대상자 등록", tags = "", description = "")
    @PostMapping(value="/insertExcel")
    public String insertExcelWinAplnList(@Valid @ModelAttribute WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, @RequestParam("wblListExcel") MultipartFile excelFile, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            HashMap<String, Object> rtnMap = wLSurveyService.insertSurveyExcelList(wBLSurveyMstInsertDTO, request , excelFile );

            modelMap.addAttribute("rtnMap", rtnMap);

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

    @Operation(summary = "응답 초기화", tags = "", description = "")
    @PostMapping(value="/updateSurveyRspn")
    public String updateSurveyRspn(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wLSurveyService.updateSurveyRspn(wBLSurveyMstInsertDTO, request ));

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

    @GetMapping(value = "/excel-down")
    public void getSurveyListExcel(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            wBLSurveyMstSearchDTO.setExcelYn("Y");
            //엑셀 생성
            wLSurveyService.excelDownload(wLSurveyService.selectSurveyList(wBLSurveyMstSearchDTO), response);

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


    //2024-07-08 추가개발 ppt 4 발송일 업데이트
    /**
     * 상생협력체감도조사 인증번호 발송
     */
    @PostMapping(value = "/submitCrtfnNo")
    public ResponseEntity<WBLSurveyMstInsertDTO> submitCrtfnNo(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletResponse response) throws Exception {
        try {
            /* 2024-07-09 추가개발 ppt 13 수정 s */
            //상생협력체감도조사 메일발송 시작

            //메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("[KAP] 이메일 인증번호 안내");

            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            receiverDto.setEmail(wBLSurveyMstInsertDTO.getEmail());
//            receiverDto.setName(wBLSurveyMstInsertDTO.getPicNm());
            receiverDto.setNote1(wBLSurveyMstInsertDTO.getCrtfnNo());
            receiverDto.setNote2(wBLSurveyMstInsertDTO.getPartCmpnNm2());

            cOMailDTO.getReceiver().add(receiverDto);

            cOMessageService.sendMail(cOMailDTO, "WBLSurveySrtfnNo.html");

            //상생협력체감도조사 메일발송 끝

            //SMS 발송 시작
            //휴대폰 번호는 필수값이 아니기에 없으면 안보냄
            if(wBLSurveyMstInsertDTO.getTelNo() != null && !"".equals(wBLSurveyMstInsertDTO.getTelNo())){
                receiverDto.setMobile(wBLSurveyMstInsertDTO.getTelNo());
                COSmsDTO smsDto = new COSmsDTO();

                SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();
                smiSmsCntnDTO.setSmsCntnCd("SMS10"); //교육신청 완료 코드
                smiSmsCntnDTO.setSmsCntnSeq(10);
                smsDto.getReceiver().add(receiverDto);
                smsDto.setMessage(COStringUtil.replaceHTML(smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO).getCntn()));

                cOMessageService.sendSms(smsDto, "");
            }
            //SMS 발송 끝
            /* 2024-07-09 추가개발 ppt 13 수정 e */

            //2024-07-08 추가개발 ppt 4 발송일 업데이트
            wBLSurveyMstInsertDTO.setRespCnt(wLSurveyService.updateSendDtm(wBLSurveyMstInsertDTO));

            return ResponseEntity.ok(wBLSurveyMstInsertDTO);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            //2024-07-08 추가개발 ppt 4 발송일 업데이트
//            throw new Exception(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //2024-07-11 추가개발 ppt 11 추가
    /**
     * 상생협력체감도조사 인증번호 발송
     */
    @PostMapping(value = "/submitCrtfnNoList")
    public ResponseEntity<WBLSurveyMstInsertDTO> submitCrtfnNoList(@Valid @RequestBody WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletResponse response) throws Exception {
        int rtnCnt = 0;

        try {
            if(!wBLSurveyMstInsertDTO.getSendList().isEmpty()) {
                List<WBLSurveyMstInsertDTO> sendList = wBLSurveyMstInsertDTO.getSendList();

                for(WBLSurveyMstInsertDTO send : sendList) {
                    //메일 발송 시작
                    COMailDTO cOMailDTO = new COMailDTO();
                    cOMailDTO.setSubject("[KAP] 이메일 인증번호 안내");

                    COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                    receiverDto.setEmail(send.getEmail());
                    receiverDto.setNote1(send.getCrtfnNo());
                    receiverDto.setNote2(send.getPartCmpnNm2());

                    cOMailDTO.getReceiver().add(receiverDto);

                    cOMessageService.sendMail(cOMailDTO, "WBLSurveySrtfnNo.html");
                    // 메일 발송 끝

                    //SMS 발송 시작
                    //휴대폰 번호는 필수값이 아니기에 없으면 안보냄
                    if (send.getTelNo() != null && !send.getTelNo().isEmpty()) {
                        receiverDto.setMobile(send.getTelNo());
                        COSmsDTO smsDto = new COSmsDTO();

                        SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();
                        smiSmsCntnDTO.setSmsCntnCd("SMS10"); //교육신청 완료 코드
                        smiSmsCntnDTO.setSmsCntnSeq(10);
                        smsDto.getReceiver().add(receiverDto);
                        smsDto.setMessage(COStringUtil.replaceHTML(smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO).getCntn()));

                        cOMessageService.sendSms(smsDto, "");
                    }
                    //SMS 발송 끝

                    rtnCnt += wLSurveyService.updateSendDtm(send);
                }
            }

            wBLSurveyMstInsertDTO.setRespCnt(rtnCnt);

            return ResponseEntity.ok(wBLSurveyMstInsertDTO);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}