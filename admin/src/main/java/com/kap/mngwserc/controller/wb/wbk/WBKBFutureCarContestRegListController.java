package com.kap.mngwserc.controller.wb.wbk;


import com.kap.core.dto.*;
import com.kap.core.dto.wb.wbk.*;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <pre>
 * 미래차공모전 신청팀관리를 위한 controller
 * </pre>
 *
 * @ClassName		: WBKBFutureCarContestListController.java
 * @Description		: 미래차공모전 신청팀관리를 위한 Controller
 * @author 민윤기
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		민윤기				   최초 생성
 * </pre>
 *
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbkb")
public class WBKBFutureCarContestRegListController {

    /** 1서비스 **/
    public final WBKBRegisterService wBKBRegisterService;
    // 아래 없앨 코드
    public final WBKBFutureCarContestListRegService wBKBFutureCarContestListRegService;
    private final WBFBRegisterCompanyService wBFBRegisterCompanyService;
    public final WBKAFutureCarContestListService wbkaFutureCarContestListService;
    public final COCodeService cOCodeService;

    /**
     *  미래차공모전 신청팀 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getFutureCarContestRegList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        ArrayList<String> cdList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("WBK_AWD");
        cdDtlList.add("WBK_PTN");
        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        // 신청자 관련 코드 셋팅
        COCodeDTO cOCodeDTO = new COCodeDTO();
        cOCodeDTO.setCd("WBKB_REG_CTG");
        modelMap.addAttribute("wbkbRegCtg",  cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("WBKB_REG_FRT");
        modelMap.addAttribute("wbkbRegFrt",  cOCodeService.getCdIdList(cOCodeDTO));

        cOCodeDTO.setCd("WBKB_REG_LRT");
        modelMap.addAttribute("wbkbRegLrt",  cOCodeService.getCdIdList(cOCodeDTO));

        wBKBRegisterSearchDTO.setBsnCd("BNS11");
        //modelMap.addAttribute("rtnYear", wbkaFutureCarContestListService.selectYearDtl(wBFutureCarContestSearchDTO));
        modelMap.addAttribute("optYearList", wBKBRegisterService.getOptYearList(wBKBRegisterSearchDTO));
        modelMap.addAttribute("rtnData", wBKBRegisterSearchDTO);

        //modelMap.addAttribute("rtnData", wBKBFutureCarContestListRegService.selectFutureCarContestRegList(wBFutureCarContestSearchDTO));

        return "mngwserc/wb/wbk/WBKBFutureCarContestRegList.admin";
    }

    /**
     * 탄소배출저감 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getFutureCarContestRegListPageAjax( WBKBRegisterSearchDTO wBKBRegisterSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            wBKBRegisterSearchDTO.setBsnCd("BNS11");
            modelMap.addAttribute("rtnData", wBKBRegisterService.getRegisterList(wBKBRegisterSearchDTO));
            System.out.println("rtnData==" + modelMap.getAttribute("rtnData"));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            /*throw new Exception(e.getMessage());*/
            e.printStackTrace();
        }
        return "mngwserc/wb/wbk/WBKBFutureCarContestRegListAjax";
    }

    /**
     * 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBKBRegisterSearchDTO wBKBRegisterSearchDTO, ModelMap modelMap
            , @Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("WBK_AWD");
            cdDtlList.add("WBK_PTN");

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("WBKB_REG_CTG");
            modelMap.addAttribute("wbkbRegCtg",  cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("GRD_CD");
            modelMap.addAttribute("grdCd",  cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("WBKB_REG_FRT");
            modelMap.addAttribute("partStateCd",  cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("WBKB_REG_LRT");
            modelMap.addAttribute("adminStateCd",  cOCodeService.getCdIdList(cOCodeDTO));

            wBKBRegisterSearchDTO.setBsnCd("BNS11");
            //modelMap.addAttribute("rtnYear", wbkaFutureCarContestListService.selectYearDtl(wBFutureCarContestSearchDTO));
            modelMap.addAttribute("optYearList", wBKBRegisterService.getOptYearList(wBKBRegisterSearchDTO));


           if(wBKBRegisterSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBKBRegisterService.selectFutureCarConRegDtl(wBKBRegisterSearchDTO));
            }

            System.out.printf("rtnInfo=====" +  modelMap.getAttribute("rtnInfo"));


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbk/WBKBFutureCarContestRegWrite.admin";
    }

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getEplisds")
    public String getEpisdListAjax(WBKBRegisterSearchDTO wBKBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBKBRegisterSearchDTO.setBsnCd("BNS11");
            modelMap.addAttribute("optEpisdList", wBKBRegisterService.getOptEpisdList(wBKBRegisterSearchDTO));
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
     * 선택 연도/회차 값에 따른
     * 과제명/사업유형 SELECT Ajax
     */
    @PostMapping(value="/getEpisdSeq")
    public String getOptnListAjax(WBKBRegisterSearchDTO wBKBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBKBRegisterSearchDTO.setBsnCd("BNS11");
            // 공통코드 배열 셋팅
            modelMap.addAttribute("episdSeqList", wBKBRegisterService.getEpisdSeq(wBKBRegisterSearchDTO));
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

    @PostMapping(value="/insert")
    public String putRegisterCompany(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();

            wBKBRegisterDTO.setRegId( coaAdmDTO.getId() );
            wBKBRegisterDTO.setRegName( coaAdmDTO.getName() );
            wBKBRegisterDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBKBRegisterDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBKBRegisterDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBKBRegisterDTO.setModId( coaAdmDTO.getId() );
            wBKBRegisterDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBKBRegisterDTO.setBsnCd("BNS11"); /* 미래차공모전 */
            //wBKBRegisterDTO.setRegUserSame("BNS11"); /* 미래차공모전 */

            COCodeDTO cOCodeDTO = new COCodeDTO();

            /* 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd("WBKB_REG_FRT");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBKBRegisterDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 신청진행상세 진행 정렬 초기 값 */
            wBKBRegisterDTO.setRsumeOrd(1);

            if(wBKBRegisterDTO.getPartName() != null && !wBKBRegisterDTO.getPartName().isEmpty()) {

                /* 팀원 정보 WBKBRegisterDTO.partList Set */
                List<String> partNameList = Arrays.asList(wBKBRegisterDTO.getPartName().split(","));
                List<String> partHpNoList = Arrays.asList(wBKBRegisterDTO.getPartHpNo().split(","));
                List<String> partEmailList = Arrays.asList(wBKBRegisterDTO.getPartEmail().split(","));
                List<String> partSchlNmList = Arrays.asList(wBKBRegisterDTO.getPartSchlNm().split(","));
                List<String> partGrdList = Arrays.asList(wBKBRegisterDTO.getPartGrd().split(","));
                List<String> partGrdCdList = Arrays.asList(wBKBRegisterDTO.getPartGrdCd().split(","));

                List<WBKBRegPartDTO> partDTOList = new ArrayList<>();

                for (int i = 0; i < partNameList.size(); i++) {
                    WBKBRegPartDTO wBKBRegPartDTO = new WBKBRegPartDTO();

                    wBKBRegPartDTO.setName(partNameList.get(i));
                    wBKBRegPartDTO.setHpNo(partHpNoList.get(i));
                    wBKBRegPartDTO.setEmail(partEmailList.get(i));
                    wBKBRegPartDTO.setSchlNm(partSchlNmList.get(i));
                    wBKBRegPartDTO.setGrd((partGrdList.get(i)));
                    wBKBRegPartDTO.setGrdCd(partGrdCdList.get(i));

                    partDTOList.add(wBKBRegPartDTO);

                }

                wBKBRegisterDTO.setPartList(partDTOList);

            }

            modelMap.addAttribute("respCnt", wBKBRegisterService.putRegisterCompany(wBKBRegisterDTO));
        }catch (Exception e)
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
     * 미래차 신청팀 수정
     */
    @PostMapping(value="/update")
    @ResponseBody
    public WBKBRegisterDTO FutureCarContestUpdate(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();

            wBKBRegisterDTO.setRegId( coaAdmDTO.getId() );
            wBKBRegisterDTO.setRegName( coaAdmDTO.getName() );
            wBKBRegisterDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBKBRegisterDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBKBRegisterDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBKBRegisterDTO.setModId( coaAdmDTO.getId() );
            wBKBRegisterDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBKBRegisterDTO.setBsnCd("BNS11"); /* 미래차공모전 */

            if(wBKBRegisterDTO.getPartName() != null && !wBKBRegisterDTO.getPartName().isEmpty()) {

                List<String> partNameList = Arrays.asList(wBKBRegisterDTO.getPartName().split(","));
                List<String> partHpNoList = Arrays.asList(wBKBRegisterDTO.getPartHpNo().split(","));
                List<String> partEmailList = Arrays.asList(wBKBRegisterDTO.getPartEmail().split(","));
                List<String> partSchlNmList = Arrays.asList(wBKBRegisterDTO.getPartSchlNm().split(","));
                List<String> partGrdList = Arrays.asList(wBKBRegisterDTO.getPartGrd().split(","));
                List<String> partGrdCdList = Arrays.asList(wBKBRegisterDTO.getPartGrdCd().split(","));

                List<WBKBRegPartDTO> partDTOList = new ArrayList<>();

                for (int i = 0; i < partNameList.size(); i++) {
                    WBKBRegPartDTO wBKBRegPartDTO = new WBKBRegPartDTO();

                    wBKBRegPartDTO.setName(partNameList.get(i));
                    wBKBRegPartDTO.setHpNo(partHpNoList.get(i));
                    wBKBRegPartDTO.setEmail(partEmailList.get(i));
                    wBKBRegPartDTO.setSchlNm(partSchlNmList.get(i));
                    wBKBRegPartDTO.setGrd((partGrdList.get(i)));
                    wBKBRegPartDTO.setGrdCd(partGrdCdList.get(i));

                    partDTOList.add(wBKBRegPartDTO);
                }
                wBKBRegisterDTO.setPartList(partDTOList);
            }
            modelMap.addAttribute("respCnt", wBKBRegisterService.updRegisterCompany(wBKBRegisterDTO));

        }
        catch (Exception e)
        {
            /*if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());*/
            e.printStackTrace();
        }

        return wBKBRegisterDTO;
    }

    /**
     * 미래차 신청팀 심사 스템 수정
     */
    @PostMapping(value="/updateStep")
    @ResponseBody
    public WBKBRegisterDTO Test(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBKBRegisterService.updRsumeStep(wBKBRegisterDTO));

        }
        catch (Exception e)
        {
            /*if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());*/
            e.printStackTrace();
        }

        return wBKBRegisterDTO;
    }
    /**
     * 미래차 신청팀 삭제
     */
    @PostMapping(value="/delete")
    public String carbonDelete(WBKBRegisterDTO wBKBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBKBRegisterDTO.setRegId(cOUserDetailsDTO.getId());
            wBKBRegisterDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBKBRegisterService.deleteRegFutureCarContest(wBKBRegisterDTO));
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


    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(WBKBRegisterSearchDTO wBKBRegisterSearchDTO ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {

            wBKBRegisterSearchDTO.setExcelYn("Y");
            wBKBRegisterSearchDTO.setBsnCd("BNS11");
            WBKBRegisterSearchDTO excelDataList = wBKBRegisterService.getRegisterList(wBKBRegisterSearchDTO);

            //엑셀 생성
            wBKBRegisterService.excelDownload(excelDataList, response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }



}
