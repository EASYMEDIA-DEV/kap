package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.wbg.WBGAExamSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 부품사 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyController.java
 * @Description		: 부품사 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpe")
public class MPEPartsCompanyController {

    /** 서비스 **/
    public final MPEPartsCompanyService mpePartsCompanyService;

    //코드 서비스
    private final COCodeService cOCodeService;

    /** 교육회차관리 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    /* 상생사업 관리 서비스 */
    public final WBGAExamService wBGAExamService;

    /**
     * 부품사 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getPartsCompanyListPage(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("COMPANY_TYPE");
        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", mpePartsCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));

        return "mngwserc/mp/mpe/MPEPartsCompanyList.admin";
    }

    /**
     * 부품사 목록을 조회한다.
     */
    @GetMapping(value = "/select")
    public String selectPartsComListPageAjax(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", mpePartsCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));
            modelMap.addAttribute("partsComDto", mpePartsCompanyDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/mp/mpe/MPEPartsCompanyListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @GetMapping(value="/write")
    public String getPartsCompanyWritePage(EBBEpisdDTO eBBEpisdDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");

            mpePartsCompanyDTO.setBsnmNo(mpePartsCompanyDTO.getBsnmNo());
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }

            modelMap.addAttribute("sqInfoList", originList);
            modelMap.addAttribute("eduCnt", mpePartsCompanyService.selectEduCnt(mpePartsCompanyDTO));
            modelMap.addAttribute("consultCnt", mpePartsCompanyService.selectConsultingCnt(mpePartsCompanyDTO));
            modelMap.addAttribute("winBusinessCnt", mpePartsCompanyService.selectWinBusinessCnt(mpePartsCompanyDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/mp/mpe/MPEPartsCompanyWrite.admin";
    }

    /**
     * 부품사 검색 레이어 구분, 규모를 조회한다.
     */
    @GetMapping(value = "/codeSelect")
    public String selectPartsComSelectAjax(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/mp/mpe/MPEPartsCompanySelectAjax";
    }

    /**
     * 부품사 사업자등록번호 등록여부를 확인한다.
     */
    @RequestMapping(value = "/checkBsnmNo", method= RequestMethod.POST)
    public String checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = 0;
            MPEPartsCompanyDTO rtnData = mpePartsCompanyService.checkBsnmNo(mpePartsCompanyDTO);
            if (rtnData != null) {
                respCnt = 1;
                modelMap.addAttribute("rtnData", rtnData);
                modelMap.addAttribute("cmpnNm", rtnData.getCmpnNm());
                modelMap.addAttribute("rprsntNm", rtnData.getRprsntNm());
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
     *  부품사 실적정보 조회 - 교육참여 연도별 개수 조회
     */
    @PostMapping(value = "/selectEduStatisticsCntList")
    public String selectEduStatisticsCntListAjax(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap) throws Exception {

        List<MPEPartsCompanyDTO> totalEduList = mpePartsCompanyService.selectEduStatisticsCntList(mpePartsCompanyDTO).getList();
        modelMap.addAttribute("eduList1", totalEduList.get(0));
        modelMap.addAttribute("eduList2", totalEduList.get(1));
        modelMap.addAttribute("eduList3", totalEduList.get(2));
        modelMap.addAttribute("eduList4", totalEduList.get(3));

        return "mngwserc/mp/mpe/MPEPartsCompanyEduListAjax";
    }

    /**
     *  부품사 실적정보 조회 - 기술지도 목록 조회
     */
    @PostMapping(value = "/selectTechGuidanceList")
    public String selectTechGuidanceListAjax(CBATechGuidanceInsertDTO cbaTechGuidanceInsertDTO, ModelMap modelMap) throws Exception {
        cbaTechGuidanceInsertDTO.setCnstgCd("CONSULT_GB01");
        modelMap.addAttribute("techGuidanceList", mpePartsCompanyService.selectTechGuidanceList(cbaTechGuidanceInsertDTO));
        return "mngwserc/mp/mpe/MPEPartsCompanyTechGuidanceListAjax";
    }

    /**
     *  부품사 실적정보 조회 - 경영컨설팅 목록 조회
     */
    @PostMapping(value = "/selectManageConsultList")
    public String selectManageConsultListAjax(CBBManageConsultListDTO cbbManageConsultListDTO, ModelMap modelMap) throws Exception {
        cbbManageConsultListDTO.setCnstgCd("CONSULT_GB02");
        modelMap.addAttribute("manageConsultList", mpePartsCompanyService.selectManageConsultList(cbbManageConsultListDTO));
        return "mngwserc/mp/mpe/MPEPartsCompanyManageConsultListAjax";
    }

    /**
     *  부품사 실적정보 조회 - 자금지원 목록 조회(시험계측장비, 검교정, 공급망안정화)
     */
    @PostMapping(value = "/selectFundingList")
    public String selectFundingListAjax(WBGAExamSearchDTO wbgaExamSearchDTO, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("fundingList", mpePartsCompanyService.selectFundingList(wbgaExamSearchDTO));
        return "mngwserc/mp/mpe/MPEPartsCompanyFundingListAjax";
    }

    /**
     *  부품사 실적정보 조회 - 자동자부품산업대상 목록 조회
     */
    @PostMapping(value = "/selectCarTargetList")
    public String selectCarTargetListAjax(WBJAcomSearchDTO wbjAcomSearchDTO, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("carTargetList", mpePartsCompanyService.selectCarTargetList(wbjAcomSearchDTO));
        return "mngwserc/mp/mpe/MPEPartsCompanyCarTargetListAjax";
    }

    /**
     * 부품사를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
            mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            int respCnt = mpePartsCompanyService.insertPartsCompany(mpePartsCompanyDTO);
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
     * 부품사를 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            mpePartsCompanyDTO.setBsnmNo(mpePartsCompanyDTO.getBsnmNo());
            modelMap.addAttribute("respCnt", mpePartsCompanyService.updatePartsCompany(mpePartsCompanyDTO));
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
    }

    /**
     * 부품사를 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(mpePartsCompanyDTO.getDetailsKey())){
                mpePartsCompanyDTO.setBsnmNo(String.valueOf(Integer.valueOf(mpePartsCompanyDTO.getDetailsKey())));
            }
            int respCnt = mpePartsCompanyService.deletePartsCompany(mpePartsCompanyDTO);
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
     * 엑셀 다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectPartsCompanyListExcel(MPEPartsCompanyDTO mpePartsCompanyDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            mpePartsCompanyDTO.setExcelYn("Y");
            // 목록 조회
            MPEPartsCompanyDTO newMpePartsCompanyDTO = mpePartsCompanyService.selectPartsCompanyList(mpePartsCompanyDTO);
            //엑셀 생성
            mpePartsCompanyService.excelDownload(newMpePartsCompanyDTO, response);
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

    /**
     * 등록된 부품사 회원이 있는지 체크
     */
    @PostMapping(value="/member-exists-check")
    public String selectMemberPartsSocietyExistsCheck(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            String chk;
            if(mpePartsCompanyService.selectMemberPartsSocietyExistsCheck(mpePartsCompanyDTO) >= 1) {
                chk = "Y";
            } else {
                chk = "N";
            }
            modelMap.addAttribute("existsChk", chk);
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
}
