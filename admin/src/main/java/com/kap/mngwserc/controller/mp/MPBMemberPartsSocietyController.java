package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.*;
import com.kap.core.dto.MPBBusDto;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.MPBSanDto;
import com.kap.core.dto.mp.mpa.MPAInqrDto;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.mp.mpi.MPIWthdrwDto;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.mp.mpa.MPAUserService;
import com.kap.service.mp.mpb.MPBMemberPartsSocietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
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

    private final MPEPartsCompanyService mpePartsCompanyService;

    /**
     * 부품사회원 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getPartUserListPage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CP");
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("COMPANY_TYPE");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));

        return "mngwserc/mp/mpb/MPBMemberPartsSocietyList.admin";
    }

    /**
     * 부품사회원 목록으로 이동한다.
     */
    @PostMapping(value = "/select")
    public String selectPartUserListPageAjax(MPAUserDto mpaUserDto ,
                                         ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CP");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));

        return "mngwserc/mp/mpb/MPBMemberPartsSocietyListAjax";
    }

    /**
     * 부품사 사업자등록번호 등록여부를 확인한다.
     */
    @RequestMapping(value = "/checkBsnmNo", method= RequestMethod.POST)
    public String checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = 0;
            MPEPartsCompanyDTO rtnData = mpePartsCompanyService.checkBsnmNo(mpePartsCompanyDTO);
            if (rtnData != null) {
                respCnt = 1;
                modelMap.addAttribute("rtnData", rtnData);
                modelMap.addAttribute("cmpnNm", rtnData.getCmpnNm());
                modelMap.addAttribute("rprsntNm", rtnData.getRprsntNm());
                modelMap.addAttribute("ctgryCdNm", rtnData.getCtgryNm());
            }
            modelMap.addAttribute("respCnt", respCnt);
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
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
            modelMap.addAttribute("rtnData", mpaUserDto);


            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", mpaUserService.selectUserDtl(mpaUserDto));
            }

            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_WTHDRW");

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
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

    /**
     * 부품사회원  상세
     */
    @PostMapping(value="/select-tab-one")
    public String getPartUserDtlAjax(MPAUserDto mpaUserDto ,
                                     ModelMap modelMap ) throws Exception
    {
        try
        {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("MEM_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            // 로그인한 계정
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());

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
     * 교육 사업 현황 리스트 조회
     */
    @PostMapping(value = "/select-tab-two")
    public String selectEduListPageTabTwoAjax(MPBEduDto mpbEduDto ,
                                              ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpbMemberPartsSocietyService.selectEduList(mpbEduDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpbEduDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyTabTwoAjax";
    }

    /**
     * 컨설팅 사업 현황 리스트 조회
     */
    @PostMapping(value = "/select-tab-three")
    public String selectEduListPageTabThreeAjax(MPBBusDto mpbBusDto ,
                                                ModelMap modelMap ) throws Exception {

        mpbBusDto.setChkPS("P");
        modelMap.addAttribute("rtnData", mpbMemberPartsSocietyService.selectBusList(mpbBusDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpbBusDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyTabThreeAjax";
    }

    /**
     * 상생 사업 현황 리스트 조회
     * 미래차공모전은 [mpa 사용]
     */
    @PostMapping(value = "/select-tab-four")
    public String selectEduListPageTabFourAjax(MPBSanDto mpbSanDto ,
                                               ModelMap modelMap ) throws Exception {
        mpbSanDto.setChkPS("P");
        modelMap.addAttribute("rtnData", mpbMemberPartsSocietyService.selectSanList(mpbSanDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpbSanDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyTabFourAjax";
    }

    /**
     * 문의 내역 리스트 조회
     */
    @PostMapping(value = "/select-tab-five")
    public String selectUserListPageTabThreeAjax(MPAInqrDto mpaInqrDto ,
                                                 ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectInqrList(mpaInqrDto));
        // 로그인한 계정
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpaInqrDto.setLgnSsnId(cOUserDetailsDTO.getId());
        return "mngwserc/mp/mpb/MPBMemberPartsSocietyTabFiveAjax";
    }

    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPAUserDto mpaUserDto ,HttpServletResponse response) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CP");
            mpaUserDto.setExcelYn("Y");
            //엑셀 생성
            MPAUserDto excelUserDto = mpaUserService.selectUserList(mpaUserDto);
            mpaUserService.excelDownload(excelUserDto, response);
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
     * 이직 가능 여부 확인
     */
    @PostMapping(value = "/confirm-comp")
    public String selectConfirmComp(ModelMap modelMap , MPAUserDto mpaUserDto) throws Exception {
        try {
            mpaUserDto.setMemSeq(Integer.valueOf(mpaUserDto.getDetailsKey()));
            modelMap.addAttribute("data", mpaUserService.selectConfirmComp(mpaUserDto));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }



    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/mp/mpb")
    public class MPBMemberRestController {

        private final MPAUserService mpaUserService;

        /**
         * 관리자 - 부품사회원관리 - 회원 탈퇴
         * @param mpiWthdrwDto
         * @return
         * @throws Exception
         */
        @RequestMapping(value="/update-wthdrw")
        public String updatePartsCompany(@Valid @RequestBody MPIWthdrwDto mpiWthdrwDto) throws Exception
        {
            try
            {
                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                //mpiWthdrwDto.setMemSeq(""); //전달받은 회원 번호
                mpiWthdrwDto.setModId(cOUserDetailsDTO.getModId());
                mpiWthdrwDto.setModIp(cOUserDetailsDTO.getModIp());
                mpaUserService.updateUserWthdrw(mpiWthdrwDto);
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


}