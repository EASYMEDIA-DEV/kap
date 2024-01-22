package com.kap.mngwserc.controller;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.RestTemplateUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.COSmpleSrchDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre> 
 * GPC 연동을 위한 RESTCONTROLLER
 * </pre>
 *
 * @ClassName		: COGpcRestController.java
 * @Description		: 관리자 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/gpc")
public class COGpcSendRestController {
    /* api */
    private final RestTemplate gpcRestTemplate;

    /* GPC URL */
    @Value("${api.gpc.end-point}")
    private String endPoint;

    /**
     * GPC 요청 Rest
     */
    @GetMapping(value="/check/id")
    public Map setApi(COSampleDTO cOSampleDTO) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("detailsKey", 2);
        param.put("name", "개발자");
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        ResponseEntity<String> res = gpcRestTemplate.exchange("/api/check/id", HttpMethod.POST, requestEntity, String.class);
        String body = res.getBody();
        //요청 응답값
        Map mapData = RestTemplateUtil.readValue(body, Map.class);
        //데이터 저장
        return mapData;
    }

    /**
     * GPC 요청 Rest
     * GPC & KAP ID 검증
     */
    @PostMapping(value="/kapedu/verifyUserId")
    public Map GpcKapIdCheck(@RequestParam(required = true) String gpcId, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("gpcUserId", gpcId);
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPoint + "/api/kapedu/verifyUserId.ajax")
                .queryParam("gpcUserId", gpcId);
        ResponseEntity<String> res = gpcRestTemplate.exchange( builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
        String body = res.getBody();
        log.error("body : {}", body);
        //요청 응답값
        Map mapData = RestTemplateUtil.readValue(body, Map.class);
        //데이터 저장
        return mapData;
    }

    /**
     * GPC 요청 Rest
     * GPC 관련 집체 교육 대참 요청
     */
    @GetMapping(value="/kapedu/changeEduMem")
    public Map ChangeKapEduMem(@RequestParam(required = true) String bfrGpcId
            , @RequestParam(required = true) String aftGpcId
            , @RequestParam(required = true) int kapSeq
            , @RequestParam(required = true) int kapSchdSeq
            , EBBPtcptDTO eBBPtcptDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPoint + "/api/kapedu/changeEduMem.ajax")
                .queryParam("bfrGpcId", bfrGpcId)
                .queryParam("aftGpcId", aftGpcId)
                .queryParam("kapSeq", kapSeq)
                .queryParam("kapSchdSeq", kapSchdSeq);
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        ResponseEntity<String> res = gpcRestTemplate.exchange( builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
        String body = res.getBody();
        //요청 응답값
        Map mapData = RestTemplateUtil.readValue(body, Map.class);
        //데이터 저장
        return mapData;
    }

}