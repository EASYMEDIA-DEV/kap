package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBCVisitEduService;
import com.kap.service.MPEPartsCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

            mpePartsCompanyDTO.setBsnmNo(mpePartsCompanyDTO.getBsnmNo());
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("twoDpthCdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            modelMap.addAttribute("fourDpthCdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "4"));

            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("COMPANY01001");
            modelMap.addAttribute("oneCtgryCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("COMPANY01002");
            modelMap.addAttribute("twoCtgryCdList", cOCodeService.getCdIdList(cOCodeDTO));

            // 방문결과 관련 코드 조회
            cOCodeDTO.setCd("EBC_VISIT_CD03001");
            modelMap.addAttribute("oneCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03002");
            modelMap.addAttribute("twoCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03003");
            modelMap.addAttribute("threeCdList", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("EBC_VISIT_CD03004");
            modelMap.addAttribute("fourCdList", cOCodeService.getCdIdList(cOCodeDTO));


            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }
            modelMap.addAttribute("rtnInfo", ebcVisitEduService.selectVisitEduDtl(ebcVisitEduDTO));
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
     */
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

