package com.kap.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kap.common.utility.CONetworkUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.co.*;

import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.core.utility.COClassUtil;
import com.kap.service.COCommService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COCommMapper;
import com.kap.service.dao.mp.MPAUserMapper;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 공통 Service 구현
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15		이옥정				   최초 생성
 *   2024.01.11     이옥정             사용자 퀵메뉴 트랜드 리스트 추가
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

    @Value("${nice.enc-product-id}")
    private String encProductId;


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

    /**
     * 사용자 퀸메뉴 트랜드리스트
     */
    public SMKTrendDTO quickTrendList(SMKTrendDTO sMKTrendDTO) throws Exception{
        sMKTrendDTO.setList(cOCommMapper.quickTrendList(sMKTrendDTO));
        return sMKTrendDTO;
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

    @Override
    public COCNiceServiceDto idnttvrfct(HttpServletRequest request, COCNiceReqEncDto cocNiceReqEncDto , String returnUrl) throws Exception {
        String niceApiAccessToken = getNiceApiAccessToken();
        if (niceApiAccessToken != null) {
            String[] niceEncToken = getNiceEncToken(niceApiAccessToken);
            if (niceEncToken.length != 0) {
                String keyIv = setDchngEnc(niceEncToken[0]); //키생성
                String reqData = newCocReqEncDto(niceEncToken[1], request ,cocNiceReqEncDto ,returnUrl); //요청 데이터 생성
                String encData = setJSONDchngEnc(reqData, keyIv); //요청 데이터 암호화

                HttpSession session = request.getSession();
                session.setAttribute("key", keyIv);
                session.setMaxInactiveInterval(60*60);
                String integrity_value = Base64.getEncoder().encodeToString(setHMacChk(keyIv.substring(0, 32).getBytes(), encData.getBytes()));

                COCNiceServiceDto cocNiceServiceDto = new COCNiceServiceDto(niceEncToken[2], encData, integrity_value);

                return cocNiceServiceDto;
            }
        }
        return null;
    }


    /**
     * 암호화 토큰 생성
     * @param accessToken
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    private String[] getNiceEncToken(String accessToken) throws URISyntaxException, JsonProcessingException {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomNumber = new StringBuilder();
        String[] returnVal = new String[3];

        // 30자리의 랜덤 숫자 생성
        for (int i = 0; i < 30; i++) {
            int digit = secureRandom.nextInt(10); // 0부터 9까지의 랜덤 숫자
            randomNumber.append(digit);
        }

        Date currentDate = new Date();
        long current_timestamp = currentDate.getTime()/1000;
        String plainCredentials = accessToken + ":" +  current_timestamp+":"+clientId;
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + base64Credentials);
        headers.set("client_id", clientId);
        headers.set("productID", encProductId);
        headers.set("CNTY_CD", "ko");


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");        //yyyyMMddHHmmss
        String formattedDateTime = now.format(formatter);

        Map<String, Object> dataBody = new HashMap<>();
        dataBody.put("req_dtim", formattedDateTime);
        dataBody.put("req_no", randomNumber.toString());
        dataBody.put("enc_mode", "1");

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
        URI url = new URI(niceApiUrl+"/digital/niceid/api/v1.0/common/crypto/token");

        ResponseEntity<String> response = restTemplate.postForEntity(url, stringHttpEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String json2 = response.getBody();
            ObjectMapper objectMapperChange = new ObjectMapper();
            JsonNode root = objectMapperChange.readTree(json2);
            JsonNode dataBodyNode = root.path("dataBody");
            String dataBodyJson = dataBodyNode.toString();
            COCNiceResDto cocNiceResDto = objectMapperChange.readValue(dataBodyJson, COCNiceResDto.class);

            returnVal[0] = formattedDateTime.trim() + randomNumber.toString().trim() + cocNiceResDto.getToken_val().trim(); //대칭키
            returnVal[1] = cocNiceResDto.getSite_code(); //사이트 코드
            returnVal[2] = cocNiceResDto.getToken_version_id(); //버전
            return returnVal ;
        } else {
            log.info("access_token_status_x");

        }
        return null;
    }

    /**
     * 대칭키 생성 및 암호화
     * @param dchngKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String setDchngEnc(String dchngKey) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dchngKey.getBytes());
        byte[] arrHashValue = md.digest();
        return Base64.getEncoder().encodeToString(arrHashValue);

    }

    /**
     * json데이터 생성
     *
     * @param siteCode
     * @param request
     * @return
     */
    private String newCocReqEncDto(String siteCode, HttpServletRequest request, COCNiceReqEncDto cocNiceReqEncDtoRe , String returnUrl) {
        COCNiceReqEncDto cocNiceReqEncDto = new COCNiceReqEncDto();
        SecureRandom secureRandom = new SecureRandom();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        String formattedDate = sdf.format(now);

        StringBuilder randomNumber = new StringBuilder();
        String[] returnVal = new String[3];

        // 30자리의 랜덤 숫자 생성
        for (int i = 0; i < 10; i++) {
            int digit = secureRandom.nextInt(10); // 0부터 9까지의 랜덤 숫자
            randomNumber.append(digit);
        }

        cocNiceReqEncDto.setRequestno("REQ" + formattedDate + randomNumber);
        cocNiceReqEncDto.setReturnurl(returnUrl);
        cocNiceReqEncDto.setSitecode(siteCode);
        cocNiceReqEncDto.setMethodtype("post");
        cocNiceReqEncDto.setReceivedata(cocNiceReqEncDtoRe.getReceivedata());
        cocNiceReqEncDto.setPopupyn("Y");

        HttpSession session = request.getSession();
        session.setAttribute("requestno", cocNiceReqEncDto.getRequestno());
        session.setMaxInactiveInterval(60*60);

        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            json = objectMapper.writeValueAsString(cocNiceReqEncDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * json 데이터 암호화
     * @param reqData
     * @param keyIv
     * @return
     * @throws Exception
     */
    private String setJSONDchngEnc(String reqData, String keyIv) throws Exception {
        String key = keyIv.substring(0, 16);
        String iv = keyIv.substring(keyIv.length() - 16);

        SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(reqData.trim().getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 데이터 무결성 값 생성
     * @param secretKey
     * @param message
     * @return
     */
    private byte[] setHMacChk(byte[] secretKey , byte[] message) {
        byte[] hmac256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec sks = new SecretKeySpec(secretKey , "HmacSHA256");
            mac.init(sks);
            hmac256 = mac.doFinal(message);
            return hmac256;
        } catch (Exception e) {
            throw new RuntimeException("Faield to generate HMACSHA25 encrypt");
        }
    }

    /**
     * 나이스로 받아온 데이터 복호화 후 return
     * @param params
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public COCNiceMyResDto idnttvrfctConfirm(String params , HttpServletRequest request) throws Exception {
        /** decode **/
        HttpSession session = request.getSession();
        COCNiceMyResDto cocNiceMyResDto = decodeParams((String) session.getAttribute("key"), (String) session.getAttribute("requestno"), params);
        session.removeAttribute("key");
        session.removeAttribute("requestno");

        return cocNiceMyResDto;
    }

    /**
     * 나이스로 받아온 데이터 복호화 메소드
     * @param keyIv
     * @param requestno
     * @param params
     * @return
     * @throws Exception
     */
    private COCNiceMyResDto decodeParams(String keyIv, String requestno, String params) throws Exception {
        String key1 = keyIv.substring(0, 16);
        String key2 = keyIv.substring(keyIv.length() - 16);

        SecretKey secretKey = new SecretKeySpec(key1.getBytes(), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(key2.getBytes()));
        byte[] decode = Base64.getDecoder().decode(params);

        String returnData = new String(c.doFinal(decode), "euc-kr");

        ObjectMapper mapper = new ObjectMapper();
        COCNiceMyResDto cocNiceMyResDto = mapper.readValue(returnData, COCNiceMyResDto.class);

        String receivedata = cocNiceMyResDto.getReceivedata();

        COCNiceMyResDto.ReceiveDatas receiveDatas = new COCNiceMyResDto.ReceiveDatas();

        String[] split = receivedata.split("&amp;");

        if(split.length ==0) {
            receiveDatas.setRedirectUrl(receivedata);
        } else {
            receiveDatas.setRedirectUrl(split[0]);
            if(split.length >=2) {
                receiveDatas.setParamsOne(split[1]);
                if(split.length >=3) {
                    receiveDatas.setParamsTwo(split[2]);
                    if(split.length >=4) {
                        receiveDatas.setParamsThree(split[3]);
                        if(split.length >=5) {
                            receiveDatas.setParamsFour(split[4]);
                            if(split.length >=6) {
                                receiveDatas.setParamsFive(split[5]);
                            }
                        }
                    }
                }
            }
        }

        cocNiceMyResDto.setReceivedatass(receiveDatas);

        if(!requestno.equals(cocNiceMyResDto.getRequestno())) {
            log.info("requestno 가 일치 하지 않음");
            throw new Exception();
        }

        return cocNiceMyResDto;
    }

    /**
     * 기관 인증 토큰
     * @return
     * @throws URISyntaxException
     */
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

    /**
     * 사업자 등록 번호 조회 nice
     * @param accessToken
     * @param compNum
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
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
