package com.kap.front.controller.mp.mph;

import com.kap.core.dto.COLoginDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpa.MPJoinDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.COUserLgnService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value="/my-page/member")
public class MPHCertificationController {

    private final COUserLgnService coUserLgnService;

    private final MPAUserService mpaUserService;


    /**
     * 정보 수정 비밀번호 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/certification")
    public String getMemberJoinChk() throws Exception {

        return "/front/mp/mph/MPHCertification.front";
    }

    /**
     * 정보 수정 페이지
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/modify-page/{type}")
    public String getMemberJoinChk(@PathVariable String type, ModelMap modelMap) throws Exception {
        String url = "";
        MPAUserDto mpaUserDto = new MPAUserDto();
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));


        if (!"".equals(mpaUserDto.getDetailsKey())) {
            MPAUserDto mpaUserDtos = mpaUserService.selectUserDtlTab(mpaUserDto);
            String[] split = mpaUserDtos.getEmail().split("@");
            mpaUserDtos.setEmailName(split[0]);
            mpaUserDtos.setEmailAddr(split[1]);
            modelMap.addAttribute("rtnDtl", mpaUserDtos);
        }

        if (type.equals("CO")) {
            url = "/front/mp/mph/MPHUserModify.front";
        } else {
            url = "/front/mp/mph/MPHCertification.front"; //TODO 양현우 CP로 변경
        }
        return url;
    }

    /**
     * 비밀번호 체크
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
     * 회원 등록
     */
    @PostMapping(value="/update")
    public String insertCmt(MPJoinDto mpJoinDto ,
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

}