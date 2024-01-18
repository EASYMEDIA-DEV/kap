package com.kap.front.config;

import com.kap.service.COSystemLogService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * API RestTemplate 인증 처리
 * </pre>
 *
 * @ClassName		    : ApiEraRestTemplateClientHttpRequestInterceptor.java
 * @Description		: API RestTemplate 인증 처리
 * @author 박주석
 * @since 2023.01.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.01.20	  박주석	             최초 생성
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final COSystemLogService cOSystemLogService;
    private final String  apiKey;
    private final String  apiSecret;

    //현재 profile 확인
    private final String profile = System.getProperty("spring.profiles.active");

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    /**
     * 인증 처리
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @NonNull
    @Override
    public ClientHttpResponse intercept(@NonNull final HttpRequest request,
                                        @NonNull final byte[] body,
                                        @NonNull final ClientHttpRequestExecution execution) throws IOException {
        try
        {
            Date date= new Date();
            Long timestamp = (date.getTime() / 1000);
            String token = getAuthorization(apiKey, apiSecret, timestamp);
            request.getHeaders().add("Authorization", token);
        }
        catch (Exception e)
        {
            log.error("e : {}", e.getCause());
        }
        request.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if("local, dev, prod".contains(profile)) {
            disableSslVerification();
        }
        final ClientHttpResponse response = execution.execute(request, body);
        final HttpStatus statusCode = response.getStatusCode();
        log.error("statusCode : {}", statusCode);
        if(statusCode == HttpStatus.UNAUTHORIZED || statusCode == HttpStatus.FORBIDDEN){
            new Exception("접근이 거부되었습니다.");
        }
        return response;
    }

    /**
     * HEADER의 Authorization 정보
     *
     * @param
     * @return String header의 auth정보
     */
    private String getAuthorization(String key, String secret, Long timestamp) throws Exception
    {
        String auth = "";
        String signature = null;
        try
        {
            String toBeHashed = key + secret + timestamp;
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(toBeHashed.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            signature = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        auth = "key=" + key +  ",signature=" + signature + ",timestamp=" + timestamp;
        return auth;
    }


    /**
     * ip 조회
     * @return
     */
    private static String getRequestIp() {
        HttpServletRequest request;
        try {
            request = getRequest();
        } catch (Exception e) {
            return "0.0.0.0";
        }
        List<String> ipHeaders = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");
        String requestIp = "";

        for (String ipHeader : ipHeaders) {
            if (!isUnknown(requestIp)) {
                break;
            }
            requestIp = removeCRLF(request.getHeader(ipHeader));
        }

        if (isUnknown(requestIp)) {
            requestIp = removeCRLF(request.getRemoteAddr());
        }

        if (StringUtils.isBlank(requestIp)) {
            return "0.0.0.0";
        }

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(requestIp);
        if (!matcher.find()) {
            return "0.0.0.0";
        }

        return requestIp;
    }


    /**
     * 요청
     * @return
     */
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * ip 없을 경우 unknown 처리
     * @param requestIp
     * @return
     */
    private static boolean isUnknown(String requestIp) {
        return StringUtils.isBlank(requestIp) || StringUtils.equalsIgnoreCase(requestIp, "unknown");
    }

    /**
     * "\r", "\n" 제거
     * @param str
     * @return
     */
    private static String removeCRLF(String str) {
        return StringUtils.replaceEach(str, new String[]{"\r", "\n"}, new String[]{"", ""});
    }

    /**
     * Disable SSL Verification
     */
    private void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
