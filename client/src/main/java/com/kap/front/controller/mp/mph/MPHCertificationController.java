package com.kap.front.controller.mp.mph;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpa.MPJoinDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


/**
 * <pre>
 * 회원정보 수정를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 회원정보 수정를 위한 Controller
 * @author 양현우
 * @since 2023.12.12
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.12		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/member/intrduction")
public class MPHCertificationController {

    private final COUserLgnService coUserLgnService;

    private final MPAUserService mpaUserService;

    private final COCodeService cOCodeService;

    private final MPEPartsCompanyService mpePartsCompanyService;



    /**
     * 정보 수정 비밀번호 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/certification")
    public String getMemberPasswordChk(ModelMap modelMap) throws Exception {
        modelMap.addAttribute("data", "modify");

        return "/front/mp/mph/MPHCertification.front";

    }

    /**
     * 정보 수정 페이지
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/modify-page")
    public String getMemberJoinChk(ModelMap modelMap) throws Exception {
        String url = "";
        MPAUserDto mpaUserDto = new MPAUserDto();
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));

        if(cOUserDetailsDTO.getRespCd().equals("1310")) {
            coUserLgnService.setLastLgnDtm(cOUserDetailsDTO);
        }

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("COMPANY_TYPE");
        cdDtlList.add("");
        cdDtlList.add("MEM_CD");


        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));


        if (!"".equals(mpaUserDto.getDetailsKey())) {
            MPAUserDto mpaUserDtos = mpaUserService.selectUserDtlTab(mpaUserDto);
            String[] split = mpaUserDtos.getEmail().split("@");
            mpaUserDtos.setEmailName(split[0]);
            mpaUserDtos.setEmailAddr(split[1]);
            modelMap.addAttribute("rtnDtl", mpaUserDtos);
        }

        return "/front/mp/mph/MPHUserModify.front";

    }

    /**
     * 비밀번호 체크
     * @param cOLoginDTO
     * @param modelMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/confirm-password")
    public String selectDupEmail(COLoginDTO cOLoginDTO,
                                 ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cOLoginDTO.setId(cOUserDetailsDTO.getId());
            modelMap.addAttribute("data", coUserLgnService.getPasswordChk(cOLoginDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }

    /**
     * 회원 수정
     * @param mpJoinDto
     * @param req
     * @param modelMap
     * @return
     * @throws Exception
     */
    @PostMapping(value="/update")
    public String updateUser(MPJoinDto mpJoinDto ,
                            HttpServletRequest req ,
                            ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            MPAUserDto mpaUserDto = mpJoinDto.getMpaUserDto();
            mpaUserDto.setRegIp(cOUserDetailsDTO.getLoginIp());
            mpaUserDto.setRegId(cOUserDetailsDTO.getId());
            mpaUserDto.setModId(cOUserDetailsDTO.getId());
            mpaUserDto.setModIp(cOUserDetailsDTO.getLoginIp());
            mpaUserDto.setMemSeq(cOUserDetailsDTO.getSeq());
            mpaUserDto.setId(cOUserDetailsDTO.getId());
            mpaUserDto.setModCd("CO");
            String emailYn = "N";

            String fndnNtfyRcvYn = mpaUserDto.getFndnNtfyRcvYn();
            String fndnChk = "N";

            if(fndnNtfyRcvYn.equals("Y")) {
                if(mpaUserDto.getNtfyEmailRcvYn().equals('N') && mpaUserDto.getNtfySmsRcvYn().equals('N')) {
                    fndnChk = "Y";
                    mpaUserDto.setFndnNtfyRcvYn("N");
                }
            }
            if(fndnNtfyRcvYn.equals("N")) {
                if(mpaUserDto.getNtfyEmailRcvYn().equals('Y') || mpaUserDto.getNtfySmsRcvYn().equals('Y')) {
                    fndnChk = "Y";
                    mpaUserDto.setFndnNtfyRcvYn("Y");
                }
            }
            mpaUserDto.setChngFndn(fndnChk);

            if(!mpaUserDto.getOldEmailRcv().equals(String.valueOf(mpaUserDto.getNtfyEmailRcvYn()))) {
                emailYn = "Y";
            }
            mpaUserDto.setChngEmail(emailYn);

            String smsYn = "N";
            if(!mpaUserDto.getOldSmsRcv().equals(String.valueOf(mpaUserDto.getNtfySmsRcvYn()))) {
                smsYn = "Y";
            }
            mpaUserDto.setChngSms(smsYn);


            MPEPartsCompanyDTO mpePartsCompanyDTO = mpJoinDto.getMpePartsCompanyDTO();
            if(mpaUserDto.getMemCd().equals("CP")){
                mpaUserDto.setWorkBsnmNo(mpJoinDto.getBsnmNo());
                mpePartsCompanyDTO.setBsnmNo(mpJoinDto.getBsnmNo());
                mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
                mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
                mpePartsCompanyDTO.setModId(cOUserDetailsDTO.getId());
                mpePartsCompanyDTO.setModIp(cOUserDetailsDTO.getLoginIp());
            }


            mpaUserService.updateUser(mpaUserDto , mpePartsCompanyDTO , mpJoinDto);

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
     * @param mpJoinDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/update-company")
    public String updatePartsCompany(MPJoinDto mpJoinDto ,ModelMap modelMap) throws Exception
    {
        try
        {
            MPEPartsCompanyDTO mpePartsCompanyDTO = mpJoinDto.getMpePartsCompanyDTO();
            mpePartsCompanyDTO.setBsnmNo(mpJoinDto.getBsnmNo());
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
     * 부품사를 이직한다.
     * @param mpJoinDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/update-company-chg")
    public String updateUserCompanyChg(MPJoinDto mpJoinDto ,ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            MPAUserDto mpaUserDto = mpJoinDto.getMpaUserDto();
            mpaUserDto.setRegIp(cOUserDetailsDTO.getLoginIp());
            mpaUserDto.setRegId(cOUserDetailsDTO.getId());
            mpaUserDto.setModId(cOUserDetailsDTO.getId());
            mpaUserDto.setModIp(cOUserDetailsDTO.getLoginIp());
            mpaUserDto.setMemSeq(cOUserDetailsDTO.getSeq());
            mpaUserDto.setId(cOUserDetailsDTO.getId());
            mpaUserDto.setModCd("CO");
            mpaUserDto.setWorkBsnmNo(mpJoinDto.getBsnmNo());
            MPEPartsCompanyDTO mpePartsCompanyDTO = mpJoinDto.getMpePartsCompanyDTO();
            mpePartsCompanyDTO.setBsnmNo(mpJoinDto.getBsnmNo());
            mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
            mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            mpePartsCompanyDTO.setModId(cOUserDetailsDTO.getId());
            mpePartsCompanyDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            mpaUserService.updateUserCompanyChg(mpaUserDto , mpePartsCompanyDTO , mpJoinDto);
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
     * 부품사 상세 조회
     * @param bsnmNo
     * @param modelMap
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value="/{bsnmNo}")
    public String getPartsCompanyWritePage(@PathVariable String bsnmNo, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();
            mpePartsCompanyDTO.setBsnmNo(bsnmNo);
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }
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

        return "jsonView";

    }

    /**
     * 이직 가능 여부 확인
     * @param modelMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/confirm-comp")
    public String selectConfirmComp(ModelMap modelMap) throws Exception {
        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setMemSeq(cOUserDetailsDTO.getSeq());
            modelMap.addAttribute("data", mpaUserService.selectConfirmComp(mpaUserDto));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }

}
