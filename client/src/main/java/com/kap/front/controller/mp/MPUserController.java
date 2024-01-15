package com.kap.front.controller.mp;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpa.MPJoinDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * <pre>
 * 회원관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 일반회원관리를 위한 Controller
 * @author 양현우
 * @since 2023.12.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.07		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/member")
public class MPUserController {

    @Value("${app.site.name}")
    private String siteName;

    private final COUserLgnService cOUserLgnService;

    private final MPAUserService mpaUserService;

    //이메일 발송
    private final COMessageService cOMessageService;

    private final MPEPartsCompanyService mpePartsCompanyService;

    //코드 서비스
    private final COCodeService cOCodeService;


    /**
     * 회원가입 체크 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/join")
    public String getMemberJoinChk(HttpSession session) throws Exception {
        session.removeAttribute("niceSession");
        return "/front/mp/MPUserJoinChk.front";

    }

    /**
     * 회원가입 페이지
     * @param modelMap
     * @param type
     * @return
     * @throws Exception
     */
    @GetMapping("/verification/{type}")
    public String getPwdFind(ModelMap modelMap, @PathVariable String type , HttpSession session) throws Exception {
        session.removeAttribute("niceSession");
        modelMap.addAttribute("rtnData", type);

        return "/front/mp/MPUserVerification.front";

    }

    /**
     * 회원가입 동의 페이지
     * @param modelMap
     * @param coIdFindDto
     * @return
     * @throws Exception
     */
    @RequestMapping( "/agreement")
    public String getAgreement(ModelMap modelMap, COIdFindDto coIdFindDto , HttpSession session , HttpServletResponse response) throws Exception {
        if(session.getAttribute("niceSession")== null) {
            authNotAllowed(response);
        } else {
            modelMap.addAttribute("data", cOUserLgnService.getIdFind(coIdFindDto));
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setCi(coIdFindDto.getCi());
            modelMap.addAttribute("data", mpaUserService.selectCiCnt(mpaUserDto));
            modelMap.addAttribute("verificationData", coIdFindDto);


        }
        return "/front/mp/MPUserAgreement.front";
    }

    /**
     * 회원가입 페이지
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/mp-user-join")
    public String getMemberJoin(ModelMap modelMap , HttpSession session , HttpServletResponse response) throws Exception {
        if(session.getAttribute("niceSession")== null) {
            authNotAllowed(response);
        } else {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        }
        return "/front/mp/MPUserJoin.front";
    }


    /**
     * id 중복 체크
     * @param mpaUserDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/dup-id")
    public String selectDupId(MPAUserDto mpaUserDto,
                              ModelMap modelMap) throws Exception {
        try {
            String chk;
            if (mpaUserService.selectDupId(mpaUserDto) >= 1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }


    /**
     * 이메일 중복 체크
     * @param mpaUserDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/dup-email")
    public String selectDupEmail(MPAUserDto mpaUserDto,
                                 ModelMap modelMap) throws Exception {
        try {

            String chk;
            if (mpaUserService.selectDupEmail(mpaUserDto) >= 1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }

    /**
     * 이메일 인증 번호 전송
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/email-auth-number")
    @ResponseBody
    public String idEmailTran(COIdFindDto coIdFindDto) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomNumber = new StringBuilder();
        try {
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setEmail(coIdFindDto.getEmail());
            int cnt = mpaUserService.selectDupEmail(mpaUserDto);
            if (cnt >= 1) {
                return "duplication-email";
            }
            // 10자리 랜덤 숫자 생성
            for (int i = 0; i < 6; i++) {
                int digit = secureRandom.nextInt(10); // 0부터 9까지의 랜덤 숫자
                randomNumber.append(digit);
            }

            //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("[" + siteName + "] 인증 번호 결과 안내");
            //인증요청일시
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(coIdFindDto.getEmail());
            //이름
            receiverDto.setName("");
            //치환문자1
            receiverDto.setNote1(coIdFindDto.getName());
            receiverDto.setNote2(randomNumber.toString());
            //수신자 정보 등록
            cOMailDTO.getReceiver().add(receiverDto);
            cOMessageService.sendMail(cOMailDTO, "EmailCertificationEDM.html");
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return randomNumber.toString();

    }

    /**
     * 회원 등록
     * @param mpJoinDto
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value="/insert")
    public String insertUser(MPJoinDto mpJoinDto ,
                            HttpServletRequest req,
                             HttpServletResponse response,
                             HttpSession session) throws Exception
    {
        try
        {  if(session.getAttribute("niceSession")== null) {
            authNotAllowed(response);
        } else {
            MPAUserDto mpaUserDto = mpJoinDto.getMpaUserDto();
            String clientIp = getClientIp(req);
            mpaUserDto.setRegIp(clientIp);
            mpaUserDto.setRegId(mpaUserDto.getId());
            mpaUserDto.setModCd("CO");
            mpaUserDto.setWorkBsnmNo(mpJoinDto.getBsnmNo());
            MPEPartsCompanyDTO mpePartsCompanyDTO = mpJoinDto.getMpePartsCompanyDTO();
            mpePartsCompanyDTO.setBsnmNo(mpJoinDto.getBsnmNo());
            mpePartsCompanyDTO.setRegId(mpaUserDto.getId());
            mpePartsCompanyDTO.setRegIp(clientIp);
            mpePartsCompanyDTO.setModId(mpaUserDto.getId());
            mpePartsCompanyDTO.setModIp(clientIp);
            mpaUserService.insertUser(mpaUserDto, mpePartsCompanyDTO, mpJoinDto);

            //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("[" + siteName + "] 회원 가입 완료 안내");
            //인증요청일시
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(mpaUserDto.getEmail());
            //이름
            receiverDto.setName("");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String now_dt = format.format(now);

            receiverDto.setNote1(mpaUserDto.getName());
            receiverDto.setNote2(now_dt);
            receiverDto.setNote3(mpaUserDto.getNtfyEmailRcvYn() == 'Y' ? "동의" : "미동의");
            receiverDto.setNote4(mpaUserDto.getNtfySmsRcvYn() == 'Y' ? "동의" : "미동의");
            //수신자 정보 등록
            cOMailDTO.getReceiver().add(receiverDto);
            cOMessageService.sendMail(cOMailDTO, "UserJoinSuccess.html");
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

        return "jsonView";

    }

    /**
     * 가입 성공 페이지
     * @param mpaUserDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/join-success" , method = RequestMethod.POST)
    public String getMemberJoinSuccess(MPAUserDto mpaUserDto , ModelMap modelMap,HttpSession session) throws Exception
    {
        session.removeAttribute("niceSession");
        MPAUserDto mpaUserDto2 = mpaUserService.selectUserDtlId(mpaUserDto);
        modelMap.addAttribute("rtnData", mpaUserDto2);

        return "/front/mp/MPUserJoinSuccess.front";

    }

    /**
     * 부품사 가입 페이지
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/mp-member-parts-join")
    public String getMemberPartsJoin(ModelMap modelMap , HttpSession session , HttpServletResponse response) throws Exception
    {
        if(session.getAttribute("niceSession") == null) {
            authNotAllowed(response);
        } else {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));


        }
        // 공통코드 배열 셋팅

        return "/front/mp/MPMemberPartsJoin.front";
    }



    /**
     * 부품사 조회
     * @param modelMap
     * @param bsnmNo
     * @return
     * @throws Exception
     */
    @GetMapping("/bsnm-select/{bsnmNo}")
    public String getCmpnSelect(ModelMap modelMap, @PathVariable String bsnmNo) throws Exception {

        MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();
        mpePartsCompanyDTO.setBsnmNo(bsnmNo);
        modelMap.addAttribute("rtnData", mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO));

        return "jsonView";

    }

    //인증 체크
    private static void authNotAllowed(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out= response.getWriter();
        out.println("<script>alert('인증시간이 만료 되었습니다.\\n다시 인증을 해주세요.'); window.location.href='/member/join';</script>");
        out.flush();
    }

    private static String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("X-Real-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("X-ReallP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("REMOTE_ADDR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        return ip;

    }

}
