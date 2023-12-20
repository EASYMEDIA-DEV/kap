package com.kap.mngwserc.controller;

import com.easymedia.error.ErrorCode;
import com.easymedia.error.exception.BusinessException;
import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.RestTemplateUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.COSmpleSrchDTO;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value="/api")
public class COGpcSendRestController {
    /* api */
    private final RestTemplate gpcRestTemplate;
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

}