package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduExcelDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBCVisitEduService;
import com.kap.service.MPEPartsCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 방문교육 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBCVisitEduController.java
 * @Description		: 방문교육 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebc")
public class EBCVisitEduController {

    /** 서비스 **/
    public final EBCVisitEduService ebcVisitEduService;

    //코드 서비스
    private final COCodeService cOCodeService;

    /** 부품사 서비스 **/
    public final MPEPartsCompanyService mpePartsCompanyService;

    /**
     * 방문교육 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getVisitEduListPage(EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("EBC_VISIT_CD");
        cdDtlList.add("COMPANY_TYPE");
        cdDtlList.add("ADDR_CD");

        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", ebcVisitEduService.selectVisitEduList(ebcVisitEduDTO));

        return "mngwserc/eb/ebc/EBCVisitEduList.admin";
    }

    /**
     * 방문교육 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectVisitEduListPageAjax(EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", ebcVisitEduService.selectVisitEduList(ebcVisitEduDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebc/EBCVisitEduListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getVisitEduWritePage(MPEPartsCompanyDTO mpePartsCompanyDTO, EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ADDR_CD");
            cdDtlList.add("EBC_VISIT_CD");
            cdDtlList.add("SYSTEM_HOUR");

            mpePartsCompanyDTO.setBsnmNo(ebcVisitEduDTO.getAppctnBsnmNo());
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            // 방문결과 관련 코드 조회
            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("EBC_VISIT_CD03");
            modelMap.addAttribute("totalCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03001");
            modelMap.addAttribute("eduCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03002");
            modelMap.addAttribute("fieldCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03003");
            modelMap.addAttribute("positionCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03004");
            modelMap.addAttribute("attendCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03005");
            modelMap.addAttribute("satisfactionCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03006");
            modelMap.addAttribute("eduTimeCdList", cOCodeService.getCdIdList(cOCodeDTO));


            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }
            modelMap.addAttribute("rtnInfo", ebcVisitEduService.selectVisitEduDtl(ebcVisitEduDTO));
            modelMap.addAttribute("isttrList", ebcVisitEduService.selectIsttrList(ebcVisitEduDTO));
            modelMap.addAttribute("resultOpList", ebcVisitEduService.selectResultOpList(ebcVisitEduDTO));
            modelMap.addAttribute("sqInfoList", originList);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/eb/ebc/EBCVisitEduWrite.admin";
    }

    /**
     * 부품사 추가정보의 신청부품업종을 조회한다.
     */
    @GetMapping(value = "/codeSelect")
    public String selectVisitEduCheckBoxAjax(EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));


            System.err.println(cOCodeService.getCmmCodeBindAll(cdDtlList));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebc/EBCVisitEduWriteCheckBoxAjax";
    }

    /**
     * 신청분야별 체크박스값 호출
     */
    @PostMapping(value = "/changeAppctnFldCd")
    public String changeAppctnFldCd(COCodeDTO cOCodeDTO, EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        List<COCodeDTO> detailList = null;
        try
        {
            if(ebcVisitEduDTO.getAppctnFldCd().equals("")) {
                cOCodeDTO.setCd(null);
            } else {
                cOCodeDTO.setCd(ebcVisitEduDTO.getAppctnFldCd());
            }
            detailList = cOCodeService.getCdIdList(cOCodeDTO);
            modelMap.addAttribute("cdDtlList", detailList);
            modelMap.addAttribute("cdName", cOCodeDTO.getCd());

            //신청분야상세코드 목록
            modelMap.addAttribute("appctnTypeList", ebcVisitEduService.selectAppctnTypeList(ebcVisitEduDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebc/EBCVisitEduWriteCheckBoxAjax";
    }

    /**
     * 방문교육 정보를 수정한다.
     *
     *//*
    @RequestMapping(value="/update")
    public String updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            ebcVisitEduDTO.setModId(cOUserDetailsDTO.getId());
            ebcVisitEduDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            ebcVisitEduDTO.setAppctnBsnmNo(ebcVisitEduDTO.getAppctnBsnmNo());
            modelMap.addAttribute("respCnt", ebcVisitEduService.updateVisitEdu(ebcVisitEduDTO));
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }*/

    /**
     * 방문교육 정보를 수정한다.
     *
     */
    @PostMapping(value="/update")
    public String updateVisitEdu(@RequestBody EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap) throws Exception
    {
        try{
            modelMap.addAttribute("rtnData", ebcVisitEduService.updateVisitEdu(ebcVisitEduDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            System.err.println("에러메시지1 = " + e);
            System.err.println("에러메시지2 = " + e.getMessage());

            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 엑셀 다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectVisitEduListExcel(EBCVisitEduExcelDTO ebcVisitEduExcelDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            ebcVisitEduExcelDTO.setExcelYn("Y");
            // 목록 조회
            EBCVisitEduExcelDTO newEbcVisitEduExcelDTO = ebcVisitEduService.selectExcelList(ebcVisitEduExcelDTO);
            //엑셀 생성
            ebcVisitEduService.excelDownload(newEbcVisitEduExcelDTO, response);
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

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/ebc")
    public class EBCVisitEduRestController {

        private final COCodeService cOCodeService;

        /**
         * 소재지역 분류 3뎁스 호출
         */
        @PostMapping(value = "/classTypeList")
        public List<COCodeDTO> classTypeList(@RequestBody COCodeDTO cOCodeDTO, EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            List<COCodeDTO> detailList = null;
            try
            {
                if (cOCodeDTO.getCd().equals("")) {
                    cOCodeDTO.setCd("ADDR_CD");
                }
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }
    }
}

