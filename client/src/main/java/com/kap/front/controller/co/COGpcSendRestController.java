package com.kap.front.controller.co;

import com.kap.common.utility.RestTemplateUtil;
import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    @GetMapping(value="/sending")
    public Map setApi(COSampleDTO cOSampleDTO) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("detailsKey", 2);
        param.put("name", "개발자");
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        ResponseEntity<String> res = gpcRestTemplate.exchange("/api/receive", HttpMethod.POST, requestEntity, String.class);
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
    @GetMapping(value="/kapedu/verifyUserId")
    public Map GpcKapIdCheck(@RequestBody EBBPtcptDTO eBBPtcptDTO) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("gpcUserId", eBBPtcptDTO.getGpcId());
        //param.put("gpcUserId", "dldhrwjd");
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        //ResponseEntity<String> res = gpcRestTemplate.exchange( endPoint + "/api/kapedu/verifyUserId.ajax", HttpMethod.POST, requestEntity, String.class);
    ResponseEntity<String> res = gpcRestTemplate.exchange( "https://gpc.hyundai.co.kr/api/kapedu/verifyUserId.ajax", HttpMethod.POST, requestEntity, String.class);
        String body = res.getBody();
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
    public Map ChangeKapEduMem(@RequestBody EBBPtcptDTO eBBPtcptDTO) throws Exception
    {
        //파라미터 정리
        HashMap<String, Object> param = new HashMap<String, Object>();
        //param.put("bfrGpcId", eBBPtcptDTO.getGpcId());
        //param.put("aftGpcId", eBBPtcptDTO.getAftGpcId());
        //param.put("kapSeq", eBBPtcptDTO.getEdctnSeq());
        //param.put("kapSchdSeq", eBBPtcptDTO.getEpisdSeq());
        param.put("bfrGpcId", "dldhrwjd");
        param.put("aftGpcId", "easymedia");
        param.put("kapSeq", "123123");
        param.put("kapSchdSeq", "654321");
        HttpEntity<Map> requestEntity = new HttpEntity<>(param);
        ResponseEntity<String> res = gpcRestTemplate.exchange( endPoint + "/api/kapedu/changeEduMem.ajax", HttpMethod.POST, requestEntity, String.class);
        String body = res.getBody();
        //요청 응답값
        Map mapData = RestTemplateUtil.readValue(body, Map.class);
        //데이터 저장
        return mapData;
    }

}