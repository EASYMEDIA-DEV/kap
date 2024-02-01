package com.kap.mngwserc.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.co.COGpcEdctnDTO;
import com.kap.core.dto.co.COGpcEpisdDTO;
import com.kap.core.dto.co.COGpcPtcptDTO;
import com.kap.service.COAAdmService;
import com.kap.service.COGpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    private final COGpcService cOGpcService;

    private final COAAdmService cOAAdmService;

    //사용자 http 경로
    @Value("${app.user-domain}")
    private String appUserDomain;

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
     * GPC KAP 등록 된 교육 과정 정보 호출
     */
    @PostMapping(value="/eduMaster")
    public COGpcEdctnDTO getApiEduMaster(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);

        COGpcEdctnDTO gpcEdctnDto = new COGpcEdctnDTO();

        // 명세서의 내용대로 날짜값을 받음 어제 날짜 예시 ( 2024-01-29 )
        gpcEdctnDto.setDate(emfMap.getString("date"));
        COGpcEdctnDTO rtnData = cOGpcService.selectGpcEdctnList(gpcEdctnDto);

        return rtnData;
    }

    /**
     * GPC KAP 등록 된 교육 과정의 상세 차수 정보 호출
     */
    @PostMapping(value="/eduEpisdList")
    public COGpcEpisdDTO getApiEduEpisdList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);

        COGpcEpisdDTO gpcEpisdDto = new COGpcEpisdDTO();

        gpcEpisdDto.setKapSeq(Integer.parseInt(emfMap.getString("kapSeq")));

        COGpcEpisdDTO rtnData = cOGpcService.selectGpcEpisdList(gpcEpisdDto);

        return rtnData;
    }

    /**
     * GPC KAP 교육 신청자 정보 호출
     */
    @PostMapping(value="/eduPtctpList")
    public COGpcPtcptDTO getApiEduPtctpList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);

        COGpcPtcptDTO ptcptDto = new COGpcPtcptDTO();

        ptcptDto.setKapSchdSeq(Integer.parseInt(emfMap.getString("kapSchdSeq")));

        // 명세서의 내용대로 kapSchdSeq <== 회차순번을 받음
        COGpcPtcptDTO rtnPtcptDto = cOGpcService.selectGpcPtcptList(ptcptDto);
        return rtnPtcptDto;
    }

    /**
     * GPC KAP 인터페이스 교육 회차에 따른 신청자 교육 완료 평가 정보 관련(Interceptor에서 처리)
     */
    @PostMapping(value="/eduPtctpCmpltList")
    public COGpcPtcptDTO getApiEduPtctpCmpltList(@RequestBody @MapData EmfMap emfMap, HttpServletRequest request) throws Exception
    {
        //응답할 데이터
        log.error("getApi cOSampleDTO : {}", emfMap);

        COGpcPtcptDTO ptcptDto = new COGpcPtcptDTO();

        ptcptDto.setKapSchdSeq(Integer.parseInt(emfMap.getString("kapSchdSeq")));

        // 명세서의 내용대로 kapSchdSeq <== 회차순번을 받음
        COGpcPtcptDTO rtnPtcptDto = cOGpcService.selectGpcPtcptExamList(ptcptDto);
        return rtnPtcptDto;
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
        // 명세서의 내용대로 kapSchdSeq, KAPID <== 회차순번을 받음
        //cOAAdmDTO.setAuthCd(emfMap.getString("detailsKey"));
        COAAdmDTO rtnCOAAdmDTO = cOAAdmService.selectAdmList(cOAAdmDTO);
        return rtnCOAAdmDTO;
    }




}