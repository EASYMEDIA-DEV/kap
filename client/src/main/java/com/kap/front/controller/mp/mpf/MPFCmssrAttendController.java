package com.kap.front.controller.mp.mpf;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.core.dto.mp.mpi.MPIWthdrwDto;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import com.kap.service.mp.mpd.MPDCmtService;
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
import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 * 근태 관리 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 근태 관리  위한 Controller
 * @author 양현우
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/member/attend")
public class MPFCmssrAttendController {

    //코드 서비스
    private final COCodeService cOCodeService;

    private final MPDCmtService mpdCmtService;



    /**
     * 근태 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/attend-page")
    public String getAttendPage(ModelMap modelMap) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        if(!cOUserDetailsDTO.getAuthCd().equals("CS")) {
            return "redirect:/";
        }

        Date now = new Date();
        String now_dt = format.format(now);
        String[] split = now_dt.split("-");
        HashMap<String, Object> dtMap = new HashMap<>();
        dtMap.put("date", split);

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
//        // 코드 set
        cdDtlList.add("CMSSR_ATTEND");

        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

        MPDKenDto mpdKenDto = new MPDKenDto();
        mpdKenDto.setYear(split[0]);
        mpdKenDto.setMnth(split[1]);
        mpdKenDto.setDd(split[2]);
        mpdKenDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("now_dt", dtMap);
        modelMap.addAttribute("rtnData", mpdCmtService.selectKenMonthCnt(mpdKenDto));

        modelMap.addAttribute("rtnDataCmpn", mpdCmtService.selectKenCmpnDtl(mpdKenDto));
        modelMap.addAttribute("cmpnData", mpdCmtService.selectKenCmpnList(mpdKenDto));

        return "/front/mp/mpf/MPFCmssrAttend.front";
    }


    /**
     * 근태  입력
     *
     */
    @RequestMapping(value="/insert-attend")
    public String insertAtendc(MPDKenDto mpdKenDto) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String now_dt = format.format(now);
            String[] split = now_dt.split("-");
            mpdKenDto.setYear(split[0]);
            mpdKenDto.setMnth(split[1]);
            mpdKenDto.setDd(split[2]);
            mpdKenDto.setAtndcDt(now_dt);
            mpdKenDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
            mpdKenDto.setRegId(cOUserDetailsDTO.getId());
            mpdKenDto.setRegIp(cOUserDetailsDTO.getLoginIp());
            mpdKenDto.setModId(cOUserDetailsDTO.getId());
            mpdKenDto.setModIp(cOUserDetailsDTO.getLoginIp());

            if(mpdKenDto.getAtndcCd().equals("CMSSR_ATTEND_001")) {
                if(mpdKenDto.getGuidePartCmpn1()!= "") {
                    String[] gudiePartCmpn1Split = mpdKenDto.getGuidePartCmpn1().split("/");
                    mpdKenDto.setGuidePartCmpn1(gudiePartCmpn1Split[0]);
                    mpdKenDto.setRgns1(gudiePartCmpn1Split[1]);
                    mpdKenDto.setCnstgSeq1(gudiePartCmpn1Split[2]);
                }
                if(mpdKenDto.getGuidePartCmpn2()!= "") {
                    String[] gudiePartCmpn2Split = mpdKenDto.getGuidePartCmpn2().split("/");
                    mpdKenDto.setGuidePartCmpn2(gudiePartCmpn2Split[0]);
                    mpdKenDto.setRgns2(gudiePartCmpn2Split[1]);
                    mpdKenDto.setCnstgSeq2(gudiePartCmpn2Split[2]);
                }
            }

            mpdCmtService.insertAtend(mpdKenDto);

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



}