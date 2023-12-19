package com.kap.front.controller.wb.wbl;

import com.easymedia.error.ErrorCode;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.SVASurveyService;
import com.kap.service.WBLSurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 설문조사 Controller
 * </pre>
 *
 * @ClassName		: SVASurveyController.java
 * @Description		: 상생협력체감도조사 Controller
 * @author 박준희
 * @since 2023.10.31
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.19		박준희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/coexistence/survey")
public class WBLSurveyController{

    private final WBLSurveyService wLSurveyService;

    /**
     * 메인
     */
    @GetMapping(value="/index")
    public String getSurveyIndex(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/wb/wbl/WBLSurveyIndex.front";
        try
        {

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }


    /**
     * 인증번호확인
     */
    @PostMapping(value = "/authCheck")
    public String selectSurveyAuthcheck(@Valid @RequestBody WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String vwUrl = "front/COBlank.error";
        try
        {
            int respCnt = 0;
            wBLSurveyMstSearchDTO.setDetailsKey(wBLSurveyMstSearchDTO.getCrtfnNo());
            WBLSurveyMstInsertDTO rtnData = wLSurveyService.selectSurveyDtl(wBLSurveyMstSearchDTO);

            if (rtnData != null){
                RequestContextHolder.getRequestAttributes().setAttribute("crtfnNo", wBLSurveyMstSearchDTO.getCrtfnNo(), RequestAttributes.SCOPE_SESSION);
                respCnt = 1;
            }

            modelMap.addAttribute("respCnt", respCnt);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }


    /**
     * 메인
     */
    @GetMapping(value="/step1")
    public String getSurveyStep1(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/wb/wbl/WBLSurveyStep1.front";
        try
        {
            if (RequestContextHolder.getRequestAttributes().getAttribute("crtfnNo", RequestAttributes.SCOPE_SESSION) == null){
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }else{
                String crtfnNo = String.valueOf( RequestContextHolder.getRequestAttributes().getAttribute("crtfnNo", RequestAttributes.SCOPE_SESSION));
                wBLSurveyMstSearchDTO.setDetailsKey(crtfnNo);
                wBLSurveyMstSearchDTO.setCrtfnNo(crtfnNo);
                WBLSurveyMstInsertDTO rtnData = wLSurveyService.selectSurveyDtl(wBLSurveyMstSearchDTO);
                modelMap.addAttribute("rtnData", rtnData);
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }
}