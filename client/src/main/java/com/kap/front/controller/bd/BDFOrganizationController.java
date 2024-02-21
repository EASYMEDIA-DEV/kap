package com.kap.front.controller.bd;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.service.COCodeService;
import com.kap.service.COGCntsService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 재단정보 > 재단소식 > 재단조직 컨트롤러
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 재단조직을 위한 Controller
 * @author 장두석
 * @since 2024.01.05
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.05		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/intro/organization")
public class BDFOrganizationController {

    /** Service **/
    private final MPAUserService mpaUserService;
    private final COCodeService cOCodeService;
    private final COGCntsService cOGCntsService;

    /**
     * 위원 목록 페이지
     */
    @GetMapping(value = "/content")
    public String getPartUserListPage(MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception {

        // get Menu Sequance
        Integer menuSeq = -1;
        String uri = request.getRequestURI();
        List<COMenuDTO> parntMenuList = (List<COMenuDTO>) request.getAttribute("parntMenuList");
        for(COMenuDTO value : parntMenuList) {
            if(uri.equals(value.getUserUrl())) {
                menuSeq = value.getMenuSeq();
            }
        }

        // get CMS Data
        COGCntsDTO pCOGCntsDTO = new COGCntsDTO();
        pCOGCntsDTO.setMenuSeq(menuSeq);
        pCOGCntsDTO.setUserYn("Y");
        COGCntsDTO rtnData = cOGCntsService.selectCntsDtl(pCOGCntsDTO);
        modelMap.addAttribute("rtnData", rtnData);

        COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();
        if(lgnData != null) {
            rtnData.setDetailsKey(lgnData.getSeq().toString());
        }



        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("MEM_CD");
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList, "4"));

        MPAUserDto rtnProDto = new MPAUserDto();
        MPAUserDto rtnAdvDto = new MPAUserDto();

        rtnProDto.setMemCd("CS");
        rtnProDto.setWthdrwYn("Y");
        rtnProDto.setListRowSize(9);
        rtnAdvDto.setMemCd("CS");
        rtnAdvDto.setWthdrwYn("Y");
        rtnAdvDto.setListRowSize(9);

        List<String> cmssrWorkList = new ArrayList<>();
        cmssrWorkList.add("MEM_CD04001");
        rtnProDto.setCmssrWorkList(cmssrWorkList);
        rtnAdvDto.setCmssrWorkList(cmssrWorkList);

        List<String> cmssrTypeList = new ArrayList<>();
        cmssrTypeList.add("MEM_CD03001");
        cmssrTypeList.add("MEM_CD03002");
        cmssrTypeList.add("MEM_CD03003");

        List<String> cmssrTypeList1 = new ArrayList<>();
        cmssrTypeList1.add("MEM_CD03001");
        cmssrTypeList1.add("MEM_CD03002");
        rtnProDto.setCmssrTypeList(cmssrTypeList1);
        MPAUserDto rtnProData = mpaUserService.selectUserList(rtnProDto);


        List<String> cmssrTypeList2 = new ArrayList<>();
        cmssrTypeList2.add("MEM_CD03003");
        rtnAdvDto.setCmssrTypeList(cmssrTypeList2);
        MPAUserDto rtnAdvData = mpaUserService.selectUserList(rtnAdvDto);

        modelMap.addAttribute("rtnProData", rtnProData);
        modelMap.addAttribute("rtnAdvData", rtnAdvData);

        return "/front/bd/bdf/BDFOrganizationList.front";
    }

    /**
     * 위원 목록 조회
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public MPAUserDto selectPartUserListAjax(MPAUserDto mpaUserDto) throws Exception {
        mpaUserDto.setMemCd("CS");
        mpaUserDto.setWthdrwYn("Y");
        mpaUserDto.setListRowSize(9);

        List<String> cmssrWorkList = new ArrayList<>();
        cmssrWorkList.add("MEM_CD04001");
        mpaUserDto.setCmssrWorkList(cmssrWorkList);

        if(mpaUserDto.getCmssrCbsnCd() == null || mpaUserDto.getCmssrCbsnCd().isEmpty()) {
            List<String> cmssrTypeList = new ArrayList<>();
            String cmssrType = mpaUserDto.getCmssrTypeList().get(0);
            if(cmssrType.endsWith("3")) {
                cmssrTypeList.add("MEM_CD03003");
            }
            else {
                cmssrTypeList.add("MEM_CD03001");
                cmssrTypeList.add("MEM_CD03002");
            }
            mpaUserDto.setCmssrTypeList(cmssrTypeList);
        }

//        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));

        return mpaUserService.selectUserList(mpaUserDto);
    }

    /**
     * 위원 목록 상세 팝업
     */
    @PostMapping(value = "/select")
    @ResponseBody
    public MPAUserDto selectPartUserViewAjax(MPAUserDto mpaUserDto) throws Exception {

        return mpaUserService.selectUserDtlTab(mpaUserDto);

    }



    /**
     * 업종/분야 조회
     */
    @PostMapping(value="/cmssrCbsnCd")
    public String selectCmssrCbsnCd(ModelMap modelMap ) throws Exception
    {
        try
        {
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

}
