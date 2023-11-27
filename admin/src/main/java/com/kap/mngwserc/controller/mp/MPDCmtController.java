package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.*;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.MPBBusDto;
import com.kap.core.dto.MPBSanDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.service.*;
import com.kap.service.MPAUserService;
import com.kap.service.MPBMemberPartsSocietyService;
import com.kap.service.MPDCmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <pre>
 * 위원 관리를 위한 컨트롤러
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 위원 관리를 위한 Controller
 * @author 양현우
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpd")
public class MPDCmtController {

    private final MPAUserService mpaUserService;

    private final COCodeService cOCodeService;


    private final MPDCmtService mpdCmtService;

    private final MPBMemberPartsSocietyService mpbMemberPartsSocietyService;

    @Value("${app.file.imageExtns}")
    private String imgType;

    /**
     * 위원 목록 조회
     * @param mpaUserDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String getPartUserListPage(MPAUserDto mpaUserDto ,
                                      ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CS");
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("MEM_CD");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtList.admin";
    }

    /**
     * 위원 관리 목록 조회.
     */
    @PostMapping(value = "/select")
    public String selectPartUserListPageAjax(MPAUserDto mpaUserDto ,
                                             ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CS");
        MPAUserDto mpaUserDto1 = mpaUserService.selectUserList(mpaUserDto);
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtListAjax";
    }



    /**
     * 업종/분야 조회
     */
    @PostMapping(value="/cmssrCbsnCd")
    public String selectCmssrCbsnCd(ModelMap modelMap ) throws Exception
    {
        try
        {

            // 로그인한 계정
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
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

        return "jsonView";
    }

    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPAUserDto mpaUserDto ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CS");
            mpaUserDto.setExcelYn("Y");
            //엑셀 생성
            mpaUserService.excelDownload(mpaUserService.selectUserList(mpaUserDto), response);
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


    @GetMapping(value = "/excel-ken-down")
    public void selectUserKenListExcel(MPDKenDto mpdKenDto ,
                                       HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            if(mpdKenDto.getExcelType().equals("D")) {
                mpdCmtService.excelDownload(mpdCmtService.selectKenList(mpdKenDto), response);
            } else  {
                mpdCmtService.excelDownload(mpdCmtService.selectKenMonthList(mpdKenDto), response);

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
    }


    /**
     * 위원 등록 페이지
     */
    @RequestMapping(value="/write")
    public String getCmtInsertPage(HttpServletResponse response , ModelMap modelMap) throws Exception
    {
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("MEM_CD");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("imgType", imgType);
        return "mngwserc/mp/mpd/MPDCmtWrite.admin";
    }


    /**
     * 위원 등록
     */
    @PostMapping(value="/insert")
    public String insertCmt(MPAUserDto mpaUserDto ,
                            ModelMap modelMap) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setModCd("AD"); //임의 관리자 cd
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
            mpaUserDto.setRegId( cOUserDetailsDTO.getId() );
            mpaUserDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
            mpaUserDto.setModId( cOUserDetailsDTO.getId() );
            mpaUserDto.setModIp( cOUserDetailsDTO.getLoginIp() );
            modelMap.addAttribute("respCnt", mpdCmtService.insertCmt(mpaUserDto));
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
     * 위원 삭제
     */
    @PostMapping(value="/delete")
    public String deleteCmt(MPAUserDto mpaUserDto ,
                            ModelMap modelMap) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setModCd("AD"); //임의 관리자 cd
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
            mpaUserDto.setRegId( cOUserDetailsDTO.getId() );
            mpaUserDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
            mpaUserDto.setModId( cOUserDetailsDTO.getId() );
            mpaUserDto.setModIp( cOUserDetailsDTO.getLoginIp() );
            modelMap.addAttribute("respCnt", mpdCmtService.deleteCmt(mpaUserDto));
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
     * 위원 상세 페이지
     */
    @RequestMapping(value="/dtl-write")
    public String getCmtDtlPage(MPAUserDto mpaUserDto ,
                                ModelMap modelMap) throws Exception
    {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
        modelMap.addAttribute("rtnData", mpaUserDto);
        if(!"".equals(mpaUserDto.getDetailsKey())){
            modelMap.addAttribute("rtnInfo", mpaUserService.selectUserDtl(mpaUserDto));
        }
        return "mngwserc/mp/mpd/MPDCmtDtlWrite.admin";
    }



    /**
     * 위원  상세
     */
    @PostMapping(value="/select-tab-one")
    public String getUserDtlAjax(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            // 로그인한 계정
            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());

            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("imgType", imgType);

            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnDtl", mpaUserService.selectUserDtlTab(mpaUserDto));
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

        return  "mngwserc/mp/mpd/MPDCmtTabOneAjax";
    }

    /**
     * 컨설팅 리스트 조회
     */
    @PostMapping(value = "/select-tab-two")
    public String selectUserListPageTabTwoAjax(MPBBusDto mpbBusDto ,
                                               ModelMap modelMap ) throws Exception {

        mpbBusDto.setChkPS("S");
        modelMap.addAttribute("rtnData", mpbMemberPartsSocietyService.selectBusList(mpbBusDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpbBusDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtTabTwoAjax";
    }

    /**
     * 상생 사업 현황 리스트 조회
     * 미래차공모전은 [mpa 사용]
     */
    @PostMapping(value = "/select-tab-three")
    public String selectEduListPageTabFourAjax(MPBSanDto mpbSanDto ,
                                               ModelMap modelMap ) throws Exception {
        mpbSanDto.setChkPS("S");
        modelMap.addAttribute("rtnData", mpbMemberPartsSocietyService.selectSanList(mpbSanDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpbSanDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtTabThreeAjax";
    }

    /**
     * 근태 리스트 조회
     */
    @PostMapping(value = "/select-tab-four")
    public String selectEduListPageTabFourAjax(MPDKenDto mpdKenDto ,
                                               ModelMap modelMap ) throws Exception {
        modelMap.addAttribute("rtnData", mpdCmtService.selectKenList(mpdKenDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
        mpdKenDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtTabFourAjax";
    }


    /**
     * 근태 월리스트 테이블 월 조회
     */
    @PostMapping(value = "/ken-month-table")
    public String selectKenMonthTableAjax(MPDKenDto mpdKenDto ,
                                          ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpdCmtService.selectKenMonthTableList(mpdKenDto));

        return "jsonView";
    }

    /**
     * 근태 월리스트 조회
     */
    @PostMapping(value = "/ken-month")
    public String selectKenMonthAjax(MPDKenDto mpdKenDto ,
                                     ModelMap modelMap ) throws Exception {
        modelMap.addAttribute("rtnData", mpdCmtService.selectKenMonthList(mpdKenDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
        mpdKenDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpd/MPDCmtKenMonthAjax";
    }

}
