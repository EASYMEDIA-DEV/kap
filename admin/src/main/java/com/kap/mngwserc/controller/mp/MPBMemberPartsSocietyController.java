package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.MPAUserDto;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPAUserService;
import com.kap.service.MPBMemberPartsSocietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <pre>
 * 부품사회원 위한 Controller
 * </pre>
 *
 * @ClassName		: MPBMemberPartsSocietyController.java
 * @Description		: 부품사회원 위한 Controller
 * @author 양현우
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		양현우				   최초 생성 //mpb 누가 쓰고 있음
 *
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpb")
public class MPBMemberPartsSocietyController {

    private final MPAUserService mpaUserService;

    private final MPBMemberPartsSocietyService mpbMemberPartsSocietyService;

    private final COCodeService cOCodeService;
    /**
     * 부품사회원 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getPartUserListPage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CP");
        mpaUserDto.setExcelYn("N");
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("COMPANY_TYPE");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyList.admin";
    }

    /**
     * 부품사회원 목록으로 이동한다.
     */
    @PostMapping(value = "/select")
    public String selectPartUserListPageAjax(MPAUserDto mpaUserDto ,
                                         ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CP");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyListAjax";
    }

    /**
     * 부품사회원  상세 페이지
     */
    @PostMapping(value="/dtl")
    public String getPartUserDtlAjax(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CP");
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("MEM_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());

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

        return  "mngwserc/mp/mpb/MPBMemberPartsSocietyTabOneAjax";
    }

    /**
     * 관리자 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getAdmWritePage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception
    {

        try
        {
            mpaUserDto.setMemCd("CP");
            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
            modelMap.addAttribute("rtnData", mpaUserDto);


            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", mpaUserService.selectUserDtl(mpaUserDto));
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

        return "mngwserc/mp/mpb/MPBMemberPartsSocietyWrite.admin";
    }

    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPAUserDto mpaUserDto ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CP");
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
}