package com.kap.front.controller.mp.mpi;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpi.MPIWthdrwDto;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * <pre>
 * 회원정보 탈퇴 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 회원정보 탈퇴 위한 Controller
 * @author 양현우
 * @since 2023.12.19
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.19		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/member/wthdrw")
public class MPIUserWthdrwController {


    private final MPAUserService mpaUserService;

    //코드 서비스
    private final COCodeService cOCodeService;

    //이메일 발송
    private final COMessageService cOMessageService;

    private final COLgnService coLgnService;

    @Value("${app.site.name}")
    private String siteName;

    /**
     * 정보 수정 비밀번호 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/certification")
    public String getMemberPasswordChk(ModelMap modelMap) throws Exception {
        modelMap.addAttribute("data", "wthdrw");
        return "/front/mp/mph/MPHCertification.front";
    }

    /**
     * 정보 수정 페이지
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/wthdrw-page")
    public String getMemberWthdrw(ModelMap modelMap) throws Exception {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
//        // 코드 set
        cdDtlList.add("MEM_WTHDRW");

        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

        return "/front/mp/mpi/MPIUserWthdrw.front";
    }


    /**
     * 탈퇴 후 이메일 전송 세션 끊기
     *
     */
    @RequestMapping(value="/update-wthdrw")
    public String updatePartsCompany(MPIWthdrwDto mpiWthdrwDto , ModelMap modelMap , HttpSession sesssion) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpiWthdrwDto.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));
            mpiWthdrwDto.setModId(cOUserDetailsDTO.getModId());
            mpiWthdrwDto.setModIp(cOUserDetailsDTO.getModIp());
            mpaUserService.updateUserWthdrw(mpiWthdrwDto);

            //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("["+siteName+"] 회원 탈퇴 완료 안내");

            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(cOUserDetailsDTO.getEmail());
            //이름
            receiverDto.setName("");
            //치환문자1

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String now_dt = format.format(now);

            receiverDto.setNote1(cOUserDetailsDTO.getId());
            receiverDto.setNote2(now_dt);
            //수신자 정보 등록
            cOMailDTO.getReceiver().add(receiverDto);
            cOMessageService.sendMail(cOMailDTO, "EmailWthdrwSuccess.html");


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


    @RequestMapping("/wthdrw-success")
    public String getMemberWthdrwSuccess(MPAUserDto mpaUserDto , ModelMap modelMap , HttpSession sesssion) throws Exception
    {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
        MPAUserDto mpaUserDto1 = mpaUserService.selectUserDtl(mpaUserDto);
        if(mpaUserDto1.getWthdrwYn().equals("Y")) {
            return "redirect:/";
        } else {
            sesssion.invalidate();
            modelMap.addAttribute("rtnData", mpaUserDto1);
            return "/front/mp/mpi/MPIUserWthdrwSuccess.front";
        }

    }

}