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


}