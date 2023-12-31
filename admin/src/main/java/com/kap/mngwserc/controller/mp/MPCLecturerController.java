package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBBEpisdService;
import com.kap.service.MPCLecturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 강사 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPCLecturerController.java
 * @Description		: 강사 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpc")
public class MPCLecturerController {

    /** 서비스 **/
    public final MPCLecturerService mpcLecturerService;

    /** 교육회차관리 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    /**
     * 강사 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getLecturerListPage(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", mpcLecturerService.selectLecturerList(mpcLecturerDTO));
        return "mngwserc/mp/mpc/MPCLecturerList.admin";
    }

    /**
     * 강사 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPartsComListPageAjax(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", mpcLecturerService.selectLecturerList(mpcLecturerDTO));
            modelMap.addAttribute("lecturerDto", mpcLecturerDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/mp/mpc/MPCLecturerListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getLecturerWritePage(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", mpcLecturerService.selectLecturerDtl(mpcLecturerDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/mp/mpc/MPCLecturerWrite.admin";
    }

    /**
     *  교육 사업 현황 리스트 조회
     */
    @GetMapping(value = "/selectEduList")
    public String selectTabTwoEduListAjax(EBBEpisdDTO eBBEpisdDTO, MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception {

        eBBEpisdDTO.setIsttrSeq(mpcLecturerDTO.getIsttrSeq());
        modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
        return "mngwserc/mp/mpc/MPCLecturerEduListAjax";
    }

    /**
     *  상생 사업 현황 리스트 조회
     */
    @GetMapping(value = "/selectWinBusinessList")
    public String selectTabTwoWinBusinessListAjax(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception {

        modelMap.addAttribute("rtnData", mpcLecturerService.selectWinBusinessList(mpcLecturerDTO));
        return "mngwserc/mp/mpc/MPCLecturerWinBusinessListAjax";
    }

    /**
     * 강사 이메일 중복 확인
     */
    @PostMapping(value="/dup-email")
    public String selectDupEmail(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            String chk;
            if(mpcLecturerService.selectDupEmail(mpcLecturerDTO) >= 1) {
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
     * 강사를 등록한다.
     */
    @PostMapping(value="/insert")
    public String insertLecturer(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpcLecturerDTO.setRegId(cOUserDetailsDTO.getId());
            mpcLecturerDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            int respCnt = mpcLecturerService.insertLecturer(mpcLecturerDTO);
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
     * 강사를 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updateLecturer(MPCLecturerDTO mpcLecturerDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpcLecturerDTO.setModId(cOUserDetailsDTO.getId());
            mpcLecturerDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            mpcLecturerDTO.setIsttrSeq(mpcLecturerDTO.getIsttrSeq());
            modelMap.addAttribute("respCnt", mpcLecturerService.updateLecturer(mpcLecturerDTO));
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
     * 강사를 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deleteLecturer(MPCLecturerDTO mpcLecturerDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(mpcLecturerDTO.getDetailsKey())){
                mpcLecturerDTO.setIsttrSeq(Integer.valueOf(mpcLecturerDTO.getDetailsKey()));
            }
            int respCnt = mpcLecturerService.deleteLecturer(mpcLecturerDTO);
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
     * 교육 사업 현황 리스트 엑셀 다운로드
     */
    @GetMapping(value = "/excel-down")
    public void selectEpisdListExcel(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            eBBEpisdDTO.setExcelYn("Y");
            // 목록 조회
            EBBEpisdDTO newEBBEpisdDTO = eBBEpisdService.selectEpisdList(eBBEpisdDTO);
            //엑셀 생성
            mpcLecturerService.excelDownload(newEBBEpisdDTO, response);
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
