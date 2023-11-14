package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.*;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <pre>
 * 일반회원관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 일반회원관리를 위한 Controller
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpa")
public class MPAUserController {

    private final MPAUserService mpaUserService;
    String tableNm = "MEM_MOD_SEQ";

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getUserListPage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserList.admin";
    }

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @PostMapping(value = "/select")
    public String selectUserListPageAjax(MPAUserDto mpaUserDto ,
                                         ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserListAjax";
    }

    /**
     * 미래차공모전 리스트 조회
     */
    @PostMapping(value = "/select-tab-two")
    public String selectUserListPageTabTwoAjax(MPAAttctnDto mpaAttctnDto ,
                                               ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectAttcntList(mpaAttctnDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaAttctnDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserTabTwoAjax";
    }

    /**
     * 미래차공모전 리스트 조회
     */
    @PostMapping(value = "/select-tab-three")
    public String selectUserListPageTabThreeAjax(MPAInqrDto mpaInqrDto ,
                                               ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectInqrList(mpaInqrDto));
        // 로그인한 계정
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        mpaInqrDto.setLgnSsnId(lgnCOAAdmDTO.getId());
        return "mngwserc/mp/mpa/MPAUserTabThreeAjax";
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
            mpaUserDto.setMemCd("CO");
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

        return "mngwserc/mp/mpa/MPAUserWrite.admin";
    }

    /**
     * 관리자 상세 페이지
     */
    @PostMapping(value="/dup-email")
    public String selectDupEmail(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");
            // 로그인한 계정
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());

            String chk;
            if(mpaUserService.selectDupEmail(mpaUserDto) >=1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

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
     * 일반사용자  상세 페이지
     */
    @PostMapping(value="/dtl")
    public String getUserDtlAjax(MPAUserDto mpaUserDto ,
                             ModelMap modelMap ) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");
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

        return  "mngwserc/mp/mpa/MPAUserTabOneAjax";
    }

    /**
     * 일반 사용자 수정
     */
    @PostMapping(value="/update")
    public String insertPartsCompany(MPAUserDto mpaUserDto ,
                                     ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setModCd("AD"); //임의 관리자 cd
            mpaUserDto.setLgnSsnId(lgnCOAAdmDTO.getId());
            mpaUserDto.setRegId( lgnCOAAdmDTO.getId() );
            mpaUserDto.setRegIp( lgnCOAAdmDTO.getLoginIp() );
            mpaUserDto.setModId( lgnCOAAdmDTO.getId() );
            mpaUserDto.setTableNm(tableNm);
            modelMap.addAttribute("respCnt", mpaUserService.updateUserDtl(mpaUserDto));
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
            mpaUserDto.setMemCd("CO");
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