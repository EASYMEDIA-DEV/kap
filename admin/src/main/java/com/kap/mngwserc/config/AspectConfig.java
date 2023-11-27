package com.kap.mngwserc.config;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <pre>
 * 시스템 로그 수집
 * </pre>
 *
 * @ClassName		: AspectConfig.java
 * @Description		: 시스템 로그 수집
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AspectConfig {
    /** 서비스  **/
    private final COSystemLogService cOSystemLogService;
    /**
     * 서비스 조회 로그 수집
    */
    @AfterReturning(value = "execution(public * com.kap..service.*Service.select*(..))", returning = "returnValue")
    public void aspectSelectSystemLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        //logging
        //returnValue 는 해당 메서드의 리턴객체를 그대로 가져올 수 있다.
        try
        {
            logRecord(joinPoint, "S");
        }
        catch (Exception e)
        {
            if(log.isDebugEnabled()){
                log.debug(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 서비스 등록 로그 수집
     */
    @AfterReturning(value = "execution(public * com.kap..service.*Service.insert*(..))", returning = "returnValue")
    public void aspectInsertSystemLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        //logging
        //returnValue 는 해당 메서드의 리턴객체를 그대로 가져올 수 있다.
        try
        {
            logRecord(joinPoint, "C");
        }
        catch (Exception e)
        {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 서비스 수정 로그 수집
    */
    @AfterReturning(value = "execution(public * com.kap..service.*Service.update*(..))", returning = "returnValue")
    public void aspectUpdateSystemLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        //logging
        //returnValue 는 해당 메서드의 리턴객체를 그대로 가져올 수 있다.
        try
        {
            logRecord(joinPoint, "U");
        }
        catch (Exception e)
        {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 서비스 삭제 로그 수집
    */
    @AfterReturning(value = "execution(public * com.kap..service.*Service.delete*(..))", returning = "returnValue")
    public void aspectDeleteSystemLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        //logging
        //returnValue 는 해당 메서드의 리턴객체를 그대로 가져올 수 있다.
        try
        {
            logRecord(joinPoint, "D");
        }
        catch (Exception e)
        {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 쿼리 로그 수집
     */
    @AfterReturning(value = "execution(public * com.kap..service.dao.*Mapper.*(..))", returning = "returnValue")
    public void aspectQueryLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
        //쿼리 로그
        try
        {
            logQuery(joinPoint);
        }
        catch (Exception e)
        {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 서비스 에러 로그 수집
    */
    @AfterThrowing(value="execution(public * com.kap..service.impl.*Impl.*(..))",throwing="ex")
    public void aspectErrorSystemLog(JoinPoint joinPoint, Exception ex) throws Throwable
    {
        try
        {
            logException(joinPoint, ex);
        }
        catch (Exception e)
        {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 시스템 행위 로그 DB 등록
     */
    private void logQuery(JoinPoint joinPoint) throws Exception
    {
        String mybatisSql = "";
        if (RequestContextHolder.getRequestAttributes() != null && RequestContextHolder.getRequestAttributes().getAttribute("mybatisSql", RequestAttributes.SCOPE_REQUEST) != null)
        {
            mybatisSql = (String) RequestContextHolder.getRequestAttributes().getAttribute("mybatisSql", RequestAttributes.SCOPE_REQUEST);
        }
        if(!"".equals(mybatisSql) && RequestContextHolder.getRequestAttributes().getAttribute("pageIndicator", RequestAttributes.SCOPE_SESSION) != null)
        {
            COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
            cOSystemLogDTO.setSrvcNm(joinPoint.getTarget().getClass().getName());
            cOSystemLogDTO.setFncNm(joinPoint.getSignature().getName());
            String trgtMenuNm = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("pageIndicator", RequestAttributes.SCOPE_SESSION));
            if (!"".equals(COStringUtil.nullConvert(trgtMenuNm)))
            {
                cOSystemLogDTO.setTrgtMenuNm(trgtMenuNm);
            }
            /* Authenticated */
            if (COUserDetailsHelperService.isAuthenticated())
            {
                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

                cOSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
                cOSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            }
            cOSystemLogDTO.setQuryCntn(mybatisSql);
            //cOSystemLogService.logInsertSysQuery(cOSystemLogDTO);
        }
    }
    /**
     * 시스템 행위 로그 DB 등록
    */
    private void logRecord(JoinPoint joinPoint, String prcsCd) throws Exception
    {
        //인터셉터에서 세션에 저장된 메뉴명
        COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
        cOSystemLogDTO.setPrcsCd(prcsCd);
        cOSystemLogDTO.setSrvcNm(joinPoint.getTarget().getClass().getName());
        cOSystemLogDTO.setFncNm(joinPoint.getSignature().getName());
        String trgtMenuNm = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("pageIndicator", RequestAttributes.SCOPE_SESSION));

        /* Authenticated */
        if (COUserDetailsHelperService.isAuthenticated())
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            cOSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
            cOSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        }
        if(RequestContextHolder.getRequestAttributes().getAttribute("COAAdmId", RequestAttributes.SCOPE_SESSION) != null){
            trgtMenuNm = trgtMenuNm + " ("+RequestContextHolder.getRequestAttributes().getAttribute("COAAdmId", RequestAttributes.SCOPE_SESSION)+")";
            RequestContextHolder.getRequestAttributes().setAttribute("COAAdmId", null, RequestAttributes.SCOPE_SESSION);
        }
        if (!"".equals(COStringUtil.nullConvert(trgtMenuNm)))
        {
            cOSystemLogDTO.setTrgtMenuNm(trgtMenuNm);
        }
        cOSystemLogService.logInsertSysLog(cOSystemLogDTO);
    }

    /**
     * 시스템 에러 로그 DB 등록
     */
    private void logException(JoinPoint joinPoint, Exception exception) throws Exception
    {
        //인터셉터에서 세션에 저장된 메뉴명
        COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
        cOSystemLogDTO.setSrvcNm(joinPoint.getTarget().getClass().getName());
        cOSystemLogDTO.setFncNm(joinPoint.getSignature().getName());
        String trgtMenuNm = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("pageIndicator", RequestAttributes.SCOPE_SESSION));
        if (!"".equals(COStringUtil.nullConvert(trgtMenuNm)))
        {
            cOSystemLogDTO.setTrgtMenuNm(trgtMenuNm);
        }
        String errCdCntn = getKey(exception);
        if (null != errCdCntn)
        {
            if (errCdCntn.indexOf("ORA-") > -1)
            {
                cOSystemLogDTO.setErrCd(getOraMessage(errCdCntn));
            }
            else if (errCdCntn.indexOf("JDBC-") > -1)
            {
                cOSystemLogDTO.setErrCd(getTiberoMessage(errCdCntn));
            }
            else if (errCdCntn.indexOf("MariaDB") > -1)
            {
                cOSystemLogDTO.setErrCd(getTiberoMessage(errCdCntn));
            }
        }
        cOSystemLogDTO.setErrCntn(errCdCntn);
        /* Authenticated */
        if (COUserDetailsHelperService.isAuthenticated())
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cOSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
            cOSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        }
        cOSystemLogService.logInsertErrLog(cOSystemLogDTO);
    }
    /**
     * 오라클 메시지 출력
     *
     */
    private String getOraMessage(String sExce)
    {
        String[] splitStr = sExce.split("\n");
        String result_msg = null;
        if (splitStr.length > 0)
        {
            for (int i = 0; i < splitStr.length; i++)
            {
                if (splitStr[i].contains("ORA-"))
                {
                    int start_length = splitStr[i].indexOf("ORA-");

                    result_msg = splitStr[i].substring(start_length, start_length + 9);
                }
            }
        }
        return result_msg;
    }
    /**
     * 티베로 메시지 출력
     *
    */
    private String getTiberoMessage(String sExce)
    {
        String[] splitStr = sExce.split("\n");
        String result_msg = null;

        if (splitStr.length > 0)
        {
            for (int i = 0; i < splitStr.length; i++)
            {
                if (splitStr[i].contains("JDBC-"))
                {
                    int start_length = splitStr[i].indexOf("JDBC-");
                    result_msg = splitStr[i].substring(start_length, start_length + 10);
                    result_msg = result_msg.replace(":", "");
                }
            }
        }
        return result_msg;
    }
    /**
     * 익셉션의 내용을 가져온다.
    */
    private String getKey(Exception e)
    {
        String rMsg = "";
        String local_ip = "";
        String sServerIp = "";
        StringBuffer suf = new StringBuffer();
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            local_ip = address.getHostAddress();
        }
        catch (UnknownHostException ue)
        {
            local_ip = "unknownhost";
        }
        suf.append("[").append(local_ip).append("] ");
        sServerIp = suf.toString();
        StringWriter strW = new StringWriter();
        e.printStackTrace(new PrintWriter(strW));
        String tempLog = strW.toString();
        rMsg = sServerIp + tempLog.substring(0, 3900) + "...";
        return rMsg;
    }
}

