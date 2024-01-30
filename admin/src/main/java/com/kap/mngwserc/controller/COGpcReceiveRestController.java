package com.kap.mngwserc.controller;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.RestTemplateUtil;
import com.kap.core.annotation.MapData;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.COSmpleSrchDTO;
import com.kap.core.dto.EmfMap;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * <pre> 
 * GPC 연동을 위한 RESTCONTROLLER
 * </pre>
 *
 * @ClassName		: COGpcRestController.java
 * @Description		: GPC 호출에 따른 KAP 응답 인터페이스 관련
 * @author 이옥정
 * @since 2024.01.25
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.25		이옥정				   최초 생성
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api")
public class COGpcReceiveRestController {
    /** 서비스 **/
    private final COAAdmService cOAAdmService;
    /**
     * GPC 응답 Rest(Interceptor에서 처리)
     */
    @PostMapping(value="/receive")
    public COAAdmDTO getApi(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        //cOAAdmDTO.setDetailsKey(emfMap.getString("detailsKey"));
        cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        //COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmDtl(cOAAdmDTO);
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 교육 과정 응답 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduMaster")
    public COAAdmDTO getApiEduMaster(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 날짜값을 받음 어제 날짜 예시 ( 2024-01-29 )
        //cOAAdmDTO.setDetailsKey(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 교육 회차 응답 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduEpisdList")
    public COAAdmDTO getApiEduEpisdList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 kapSeq <== 과정순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 교육 회차에 따른 신청자 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduPtctpList")
    public COAAdmDTO getApiEduPtctpList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 kapSchdSeq <== 회차순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 교육 회차에 따른 신청자 교육 완료 평가 정보 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduPtctpCmpltList")
    public COAAdmDTO getApiEduPtctpCmpltList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 kapSchdSeq <== 회차순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 설문 문항 데이터 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduSrvyList")
    public COAAdmDTO getApiEduSrvyList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 kapSeq <== 과정순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }

    /**
     * GPC KAP 인터페이스 설문 참여 데이터 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduSrvyPtctpList")
    public COAAdmDTO getApiEduSrvyPtctpList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);
        COAAdmDTO cOAAdmDTO = new COAAdmDTO();
        // 명세서의 내용대로 kapSchdSeq <== 회차순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }




}