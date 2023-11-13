package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.MPEPartsCompanyDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPEPartsCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", mpePartsCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));

        return "mngwserc/mp/mpe/MPEPartsCompanyList.admin";
    }

    /**
     * 부품사 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPartsComListPageAjax(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", mpePartsCompanyService.selectPartsCompanyList(mpePartsCompanyDTO));
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
    @RequestMapping(value="/write")
    public String getPsnIfWritePage(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");

            LocalDate currentDate = LocalDate.now();
            List<Integer> yearList = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                int year = currentDate.getYear() - i;
                yearList.add(year);
            }

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("yearList", yearList);
            modelMap.addAttribute("rtnInfo", mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO));
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
     * 부품사를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpePartsCompanyDTO.setRegId(coaAdmDTO.getId());
            mpePartsCompanyDTO.setRegIp(coaAdmDTO.getLoginIp());

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
     * 팝업을 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpePartsCompanyDTO.setModId(coaAdmDTO.getId());
            mpePartsCompanyDTO.setModIp(coaAdmDTO.getLoginIp());

            mpePartsCompanyDTO.setBsnmNo(mpePartsCompanyDTO.getDetailsKey());
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
}