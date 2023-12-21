package com.kap.front.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBCVisitEduService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 방문교육을 위한 Controller
 * </pre>
 *
 * @ClassName		: EBCVisitEduController.java
 * @Description		: 방문교육을 위한 Controller
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
@RequestMapping(value="/education/visit/")
public class EBCVisitEduController {

    /** 서비스 **/
    public final EBCVisitEduService ebcVisitEduService;

    //코드 서비스
    private final COCodeService cOCodeService;

    /** 부품사 서비스 **/
    public final MPEPartsCompanyService mpePartsCompanyService;

    /** 부품사 회원정보 서비스 **/
    private final MPAUserService mpaUserService;


    /**
     * 방문교육 신청 1단계(기본정보) 페이지로 이동한다.
     */
    @RequestMapping(value="/apply/one-step")
    public String getVisitEduApplyOneStepPage(MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());

            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtl(mpaUserDto);
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);


            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }
            modelMap.addAttribute("applicantInfo", applicantDto);
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

        return "/front/eb/ebc/EBCVisitEduApplyOneStep.front";
    }

    /**
     * 방문교육 신청 2단계(신청내용입력) 페이지로 이동한다.
     */
    @RequestMapping(value="/apply/two-step")
    public String getVisitEduApplyTwoStepPage(ModelMap modelMap) throws Exception
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

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/eb/ebc/EBCVisitEduApplyTwoStep.front";
    }

    /**
     * 신청분야별 체크박스값 호출
     */
    @PostMapping(value = "/changeAppctnFldCd")
    public String changeAppctnFldCd(@RequestBody EBCVisitEduDTO ebcVisitEduDTO, COCodeDTO cOCodeDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
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
        return "front/eb/ebc/EBCVisitEduApplyTwoStepCheckBoxAjax";
    }
}

