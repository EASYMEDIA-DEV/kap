package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.*;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBBEpisdService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육차수관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBBEpisdController.java
 * @Description		: 교육차수관리를 위한 Controller
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebb")
public class EBBEpisdController {

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    public final COCodeService cOCodeService;

    /**
     *  교육회차관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getEpisdPage(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("CLASS_TYPE");
        cdDtlList.add("STDUY_MTHD"); //학습방식
        cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간
        cdDtlList.add("RCRMT_MTHD");//학습시간 - 학습시간


        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("CLASS01");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS02");
        modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("CLASS03");
        modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

        return "mngwserc/eb/ebb/EBBEpisdList.admin";
    }

    /**
     * 교육회차관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getEpisdPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebb/EBBEpisdListAjax";
    }

    /**
     * 교육과정관리  상세를 조회한다.
     */
    @GetMapping(value="/write")
    public String getCouseDtl(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");
        EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("ROUND_CD");//회차정보 공통코드
        cdDtlList.add("CO_YEAR_CD");//연도 공통코드

        cdDtlList.add("RCRMT_MTHD"); //모집방식
        cdDtlList.add("CMPTN_AUTO"); //수료 자동화 여부
        cdDtlList.add("CBSN_CD"); //업종코드
        cdDtlList.add("SYSTEM_HOUR"); //시간 00~23

        //cdDtlList.add("STDUY_MTHD"); //학습방식
        //cdDtlList.add("STDUY_DD");//학습시간 - 학습일
        //cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간
        //cdDtlList.add("RCRMT_MTHD");//학습시간 - 학습시간


        modelMap.addAttribute("episdCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("ROUND_CD");
        modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

        modelMap.addAttribute("rtnData", rtnDto);//교육차수 본문
        modelMap.addAttribute("roomDto", roomDto);//교육장 정보
        //설문정보
        //만족도 조사
        //강사정보
        //온라인강의



        return "mngwserc/eb/ebb/EBBEpisdWrite.admin";
    }

    /*@Operation(summary = "교육차수 등록", tags = "교육차수 등록", description = "교육차수, 교육장소, 강사, 온라인 강의 목차")
    @PostMapping(value="/insert")
    public String insertEpisd(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap) throws Exception
    {
        int respCnt = 0;
        try
        {
            System.out.println("eBBEpisdDTO = " + eBBEpisdDTO);
            respCnt = eBBEpisdService.insertEpisd(eBBEpisdDTO);
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
    }*/

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/ebb")
    public class EBBEpisdRestController {

        private final COCodeService cOCodeService;

        @Operation(summary = "교육차수 등록", tags = "교육차수 등록", description = "교육차수, 교육장소, 강사, 온라인 강의 목차")
        @PostMapping(value="/insert")
        public EBBEpisdDTO insertEpisd(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            try{



                eBBEpisdService.insertEpisd(eBBEpisdDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return eBBEpisdDTO;
        }

        @Operation(summary = "교육차수 중복체크", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/selectChk")
        public EBBEpisdDTO selectEpisdChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {
            int actCnt = 0;
            /*try
            {*/

                EBBEpisdDTO tempDto = eBBEpisdService.selectEpisdChk(eBBEpisdDTO);
            System.out.println("@@@@ tempDto= " + tempDto);

            /*}
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }*/
            return tempDto;
        }

    }





}

