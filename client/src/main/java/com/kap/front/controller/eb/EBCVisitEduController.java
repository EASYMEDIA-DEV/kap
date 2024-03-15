package com.kap.front.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBCVisitEduService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value="/education/visit")
public class EBCVisitEduController {

    /** 서비스 **/
    public final EBCVisitEduService ebcVisitEduService;

    //코드 서비스
    private final COCodeService cOCodeService;

    /** 부품사 서비스 **/
    public final MPEPartsCompanyService mpePartsCompanyService;

    /** 부품사 회원정보 서비스 **/
    private final MPAUserService mpaUserService;

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;

    //파일 업로드 확장자
    @Value("${app.file.fileExtns}")
    private String imgUploadFileExtns;

    /**
     * 방문교육 신청 페이지로 이동한다.
     */
    @RequestMapping(value="/index")
    public String getVisitEduIndexPage(MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "/front/eb/ebc/EBCVisitEduMain.front";
        MPAUserDto applicantDto = null;
        try
        {
            if(COUserDetailsHelperService.getAuthenticatedUser() != null) {
                mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
                applicantDto = mpaUserService.selectUserDtl(mpaUserDto);
                modelMap.addAttribute("loginYn", "Y");

            } else {
                modelMap.addAttribute("loginYn", "N");
            }
            modelMap.addAttribute("rtnInfo", applicantDto);
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
     * 방문교육 신청 1단계(기본정보) 페이지로 이동한다.
     */
    @RequestMapping(value="/step1")
    public String getVisitEduApplyOneStepPage(MPEPartsCompanyDTO mpePartsCompanyDTO, EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "/front/eb/ebc/EBCVisitEduApplyOneStep.front";

        try
        {
            ebcVisitEduDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq()); ;
            EBCVisitEduDTO applicantDto = ebcVisitEduService.selectApplicantInfo(ebcVisitEduDTO);
            System.err.print(applicantDto.getMemCd());
            if(applicantDto.getMemCd().equals("CP")) {
                mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

                if (originList.getList().size() != 0) {
                    modelMap.addAttribute("rtnInfo", originList.getList().get(0));
                }
                modelMap.addAttribute("applicantInfo", applicantDto);
                modelMap.addAttribute("sqInfoList", mpePartsCompanyService.selectPartsComSQInfo(mpePartsCompanyDTO));
            } else if(applicantDto.getMemCd().equals("CO")){

                modelMap.addAttribute("msg", "방문교육 신청은 부품사 회원만 신청 가능합니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";

            } else if(applicantDto.getMemCd().equals("CS")){
                modelMap.addAttribute("msg", "위원 계정은 해당 서비스를 이용할 수 없습니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
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

    /**
     * 방문교육 신청 2단계(신청내용입력) 페이지로 이동한다.
     */
    @RequestMapping(value="/step2")
    public String getVisitEduApplyTwoStepPage(EBCVisitEduDTO ebcVisitEduDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap) throws Exception
    {
        String vwUrl = "/front/eb/ebc/EBCVisitEduApplyTwoStep.front";

        try
        {

            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtl(mpaUserDto);

            if(applicantDto.getMemCd().equals("CP")) {
                mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

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

                if (originList.getList().size() != 0) {
                    ebcVisitEduDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
                    ebcVisitEduDTO.setAppctnBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                    ebcVisitEduDTO.setZipcode(originList.getList().get(0).getZipcode());
                    ebcVisitEduDTO.setBscAddr(originList.getList().get(0).getBscAddr());
                    ebcVisitEduDTO.setDtlAddr(originList.getList().get(0).getDtlAddr());
                }
                modelMap.addAttribute("rtnInfo", ebcVisitEduDTO);
            } else {
                vwUrl = "redirect:./index";
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

    /**
     * 방문교육 정보를 수정한다.
     *
     */
    @PostMapping(value="/insert")
    public String applyVisitEduInfo(EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception
    {
        try{
            ebcVisitEduDTO.setEdctnSttsCd("EBC_VISIT_CD02001"); // 교육상태 코드를 '신청'으로 지정
            ebcVisitEduDTO.setRegId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            ebcVisitEduDTO.setRegIp(COUserDetailsHelperService.getAuthenticatedUser().getLoginIp());

            int respCnt = ebcVisitEduService.applyVisitEduInfo(ebcVisitEduDTO, multiRequest, request);
            modelMap.addAttribute("respCnt", respCnt);

            // 방문신청여부 세션값에 저장
            HttpSession session = request.getSession();
            session.setAttribute("applyCompleteYn", "Y");
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
     * 방문교육 신청 완료 페이지로 이동한다.
     */
    @RequestMapping(value="/complete")
    public String getVisitEduApplyCompletePage(EBCVisitEduDTO ebcVisitEduDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "/front/eb/ebc/EBCVisitEduApplyComplete.front";

        try
        {
            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtl(mpaUserDto);

            if(applicantDto.getMemCd().equals("CP")) {

                // 방문신청여부 세션값 가져오기
                HttpSession session = request.getSession();
                String applyCompleteYn = (String) session.getAttribute("applyCompleteYn");

                if(applyCompleteYn == null || !applyCompleteYn.equals("Y")) {
                    vwUrl = "redirect:./index";
                } else {
                    ebcVisitEduDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
                    modelMap.addAttribute("rtnData", ebcVisitEduService.selectVisitEduApplyInfo(ebcVisitEduDTO));
                }
            } else {
                vwUrl = "redirect:./index";
            }

            // 방문신청여부 세션값 삭제
            HttpSession session = request.getSession();
            session.removeAttribute("applyCompleteYn");
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
     * 신청분야별 체크박스값 호출
     */
    @PostMapping(value = "/step2/changeAppctnFldCd")
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

