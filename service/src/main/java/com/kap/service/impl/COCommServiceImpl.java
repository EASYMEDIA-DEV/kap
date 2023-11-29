package com.kap.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kap.common.utility.CONetworkUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.co.COCompApiResDto;
import com.kap.core.utility.COClassUtil;
import com.kap.service.COCommService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COCommMapper;
import com.kap.service.dao.mp.MPAUserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 공통 Service 구현
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15		이옥정				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COCommServiceImpl implements COCommService {
    /*공통 서비스*/
    private final COCommMapper cOCommMapper;
    /*회원 서비스*/
    private final MPAUserMapper mpaUserMapper;
    /* 관리자 인증번호 로그 시퀀스*/
    private final EgovIdGnrService memModSeqIdgen;

    @Value("${nice.client-id}")
    private String clientId;

    @Value("${nice.client-secret}")
    private String clientSecret;

    @Value("${nice.product-id}")
    private String productId;

    @Value("${nice.url}")
    private String niceApiUrl;

    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq) throws Exception{
        return cOCommMapper.getMemCmpnDtl(memSeq);
    }
    /**
     * 신청자, 부품사 정보 수정
     */
    public int setMemCmpnDtl(COUserCmpnDto cOUserCmpnDto, HttpServletRequest request) throws Exception{
        int rspCnt = 0;
        //데이터 변경 확인
        COUserCmpnDto rtnUserCmpnDto =  cOCommMapper.getMemCmpnDtl(cOUserCmpnDto.getMemMemSeq());
        if(COClassUtil.isChangeOriginData(cOUserCmpnDto, rtnUserCmpnDto)){
            //회원 정보 수정
            cOUserCmpnDto.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            cOUserCmpnDto.setModIp(CONetworkUtil.getMyIPaddress(request));
            cOUserCmpnDto.setCmpnBsnmNo( rtnUserCmpnDto.getCmpnBsnmNo() );
            rspCnt = cOCommMapper.updateUserDtl(cOUserCmpnDto);
            //회원 변경 이력 생성
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
            mpaUserDto.setModCd("AD");
            mpaUserDto.setRegId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            mpaUserDto.setRegIp(CONetworkUtil.getMyIPaddress(request));
            mpaUserDto.setId(rtnUserCmpnDto.getMemId());
            mpaUserMapper.insertUserDtlHistory(mpaUserDto);
            //조회 한 회원의 회원 코드가 회사인 경우 수정
            if("CP".equals( rtnUserCmpnDto.getMemMemCd() )){
                cOCommMapper.updatePartsCompany(cOUserCmpnDto);
            }
        }
        return rspCnt;
    }

    /**
     * 사용자 상단 공지사항
     */
    public List<COFrontHeaderNtfyDTO> getHeaderNtfyList() throws Exception{
        return cOCommMapper.getHeaderNtfyList();
    }

    @Override
    public COCompApiResDto niceChk(String compNum) throws Exception {
        String niceApiAccessToken = getNiceApiAccessToken();
        if (niceApiAccessToken != null) {
            COCompApiResDto niceCompDtl = getNiceCompDtl(niceApiAccessToken, compNum);
//            revokeToken(niceApiAccessToken);
            return niceCompDtl;

        }
        return null;
    }


    private String getNiceApiAccessToken() throws URISyntaxException {
        String plainCredentials = clientId + ":" + clientSecret;
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(base64Credentials);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("scope", "default");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI(niceApiUrl+"/digital/niceid/oauth/oauth/token");
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, request, Map.class);
        if (mapResponseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseMap = mapResponseEntity.getBody();
            Map<String, Object> dataBodyMap = (Map<String, Object>) responseMap.get("dataBody");
            String gwRsltMsg = ((Map<String, String>) responseMap.get("dataHeader")).get("GW_RSLT_MSG");
            if(gwRsltMsg.equals("오류 없음")) {
                return ((Map<String, String>) responseMap.get("dataBody")).get("access_token");
            } else {
                log.info("api 오류");
            }
        } else {
            log.info("access_token_status_x");
        }
        return null;
    }


    private COCompApiResDto getNiceCompDtl(String accessToken , String compNum) throws URISyntaxException, JsonProcessingException {
        String plainCredentials = accessToken + ":" +  (new Date().getTime()/1000)+":"+clientId;
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + base64Credentials);
        headers.set("client_id", clientId);
        headers.set("productID", productId);


        Map<String, Object> dataBody = new HashMap<>();
        dataBody.put("comp_num", compNum);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("dataBody", dataBody);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
             json = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(json, headers);

        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI(niceApiUrl+"/digital/niceid/api/v1.0/comp/check");

        ResponseEntity<String> response = restTemplate.postForEntity(url, stringHttpEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
             String json2 = response.getBody();
            ObjectMapper objectMapperChange = new ObjectMapper();
            JsonNode root = objectMapperChange.readTree(json2);
            JsonNode dataBodyNode = root.path("dataBody");
            String dataBodyJson = dataBodyNode.toString();
            COCompApiResDto dataBodyDto = objectMapperChange.readValue(dataBodyJson, COCompApiResDto.class);
            return dataBodyDto;
        } else {
            log.info("access_token_status_x");
        }
        return null;
    }

    private void revokeToken(String accessToken) throws URISyntaxException {
        String plainCredentials = accessToken + ":" +  (new Date().getTime()/1000)+":"+clientId;
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "bearer " + base64Credentials);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI(niceApiUrl+"/digital/niceid/oauth/oauth/token/revokeById");
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, request, Map.class);
        if (mapResponseEntity.getStatusCode() == HttpStatus.OK) {

        } else {
            log.info("access_token_status_x");
        }
    }


}
