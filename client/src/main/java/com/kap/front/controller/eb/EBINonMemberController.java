package com.kap.front.controller.eb;

import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.eb.ebi.EBINonMemberDTO;
import com.kap.service.COBUserMenuService;
import com.kap.service.COCodeService;
import com.kap.service.EBINonMemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <pre>
 * 비회원 교육 과정  Controller
 * </pre>
 *
 * @ClassName		: EBINonMemberController.java
 * @Description		: 비회원 교육 과정 Controller
 * @author 장두석
 * @since 2024.01.23
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.23		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/education/apply/non-member")
public class EBINonMemberController {

    /** 서비스 **/
    public final EBINonMemberService eBINonMemberService;

    public final COBUserMenuService cOBUserMenuService;

    public final COCodeService cOCodeService;

    /**
     *  비회원 인증 페이지
     */
    @GetMapping(value="/auth")
    public String getNonMemberAuthPage(COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        return "front/eb/ebi/EBINonMemberAuth.front";
    }

    /**
     *  비회원 교육 과정 신청 목록 페이지
     */
    @PostMapping(value="/list")
    public String selectNonMemberApplyList(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        //modelMap.addAttribute("rtnData", eBINonMemberService.selectNonMemberApplyList(pEBINonMemberDTO));
        pEBINonMemberDTO.setTotalCount(eBINonMemberService.searchPtcptCnt(pEBINonMemberDTO));

        pEBINonMemberDTO.setEmail(COSeedCipherUtil.encrypt(pEBINonMemberDTO.getEmail(), "UTF-8"));
        pEBINonMemberDTO.setName(COSeedCipherUtil.encrypt(pEBINonMemberDTO.getName(), "UTF-8"));
        pEBINonMemberDTO.setHpNo(COSeedCipherUtil.encrypt(pEBINonMemberDTO.getHpNo(), "UTF-8"));

        modelMap.addAttribute("rtnDto", pEBINonMemberDTO);

        return "front/eb/ebi/EBINonMemberApplyList.front";
    }

    /**
     *  비회원 교육 과정 신청 목록 조회
     */
    @PostMapping(value="/listAjax")
    public String selectNonMemberApplyListAjax(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
//        pCOMenuDTO.setMenuSeq(613);

//        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        pEBINonMemberDTO.setEmail(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getEmail().replace(" ", "+"), "UTF-8"));
        pEBINonMemberDTO.setName(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getName().replace(" ", "+"), "UTF-8"));
        pEBINonMemberDTO.setHpNo(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getHpNo().replace(" ", "+"), "UTF-8"));

        EBINonMemberDTO tempList = eBINonMemberService.selectNonMemberApplyList(pEBINonMemberDTO);

        for(EBINonMemberDTO temp : tempList.getList()){
            temp.setEnEdctnSeq(COSeedCipherUtil.encrypt(String.valueOf(temp.getEdctnSeq()), "UTF-8"));
            temp.setEnPtcptSeq(COSeedCipherUtil.encrypt(String.valueOf(temp.getPtcptSeq()), "UTF-8"));
        }

        modelMap.addAttribute("rtnData", tempList);

        return "front/eb/ebi/EBINonMemberApplyListAjax";
    }

    /**
     *  비회원 교육 과정 신청 내역 상세
     */
    @GetMapping(value="/ptcptDetail")
    public String selectNonMemberApplyDetail(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        //pEBINonMemberDTO.setEdctnSeq(Integer.parseInt(pEBINonMemberDTO.getDetailsKey()));

        pEBINonMemberDTO.setEdctnSeq(Integer.parseInt(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getEnEdctnSeq().replace(" ", "+"), "UTF-8")));
        pEBINonMemberDTO.setDetailsKey(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getEnEdctnSeq().replace(" ", "+"), "UTF-8"));
        pEBINonMemberDTO.setPtcptSeq(Integer.parseInt(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getEnPtcptSeq().replace(" ", "+"), "UTF-8")));

        pEBINonMemberDTO.setEmail(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getEmail().replace(" ", "+"), "UTF-8"));
        pEBINonMemberDTO.setName(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getName().replace(" ", "+"), "UTF-8"));
        pEBINonMemberDTO.setHpNo(COSeedCipherUtil.decrypt(pEBINonMemberDTO.getHpNo().replace(" ", "+"), "UTF-8"));



        modelMap.addAttribute("rtnInfo", eBINonMemberService.selectNonMemberDtl(pEBINonMemberDTO));

        modelMap.addAttribute("ptcptData", eBINonMemberService.selectNonMemberApplyPtcptDtl(pEBINonMemberDTO));

        modelMap.addAttribute("rtnDto", pEBINonMemberDTO);

        return "front/eb/ebi/EBINonMemberApplyDetail.front";
    }

    /**
     *  비회원 교육 과정 상세
     */
    @GetMapping(value="/detail")
    public String getNonMemberDetial(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        DeviceResolver deviceResolver = new LiteDeviceResolver();
        Device device = deviceResolver.resolveDevice(request);

        if (!device.isMobile() && !device.isTablet()) {
            modelMap.addAttribute("device", "P");
        } else {
            modelMap.addAttribute("device", "M");
        }

        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        pEBINonMemberDTO.setEdctnSeq(Integer.parseInt(pEBINonMemberDTO.getDetailsKey()));

        modelMap.addAttribute("rtnInfo", eBINonMemberService.selectNonMemberDtl(pEBINonMemberDTO));

        return "front/eb/ebi/EBINonMemberDetail.front";
    }

    /**
     *  비회원 교육 과정 신청 step1
     */
    @GetMapping(value="/step1")
    public String getNonMemberStep1(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        pEBINonMemberDTO.setEdctnSeq(Integer.parseInt(pEBINonMemberDTO.getDetailsKey()));

        modelMap.addAttribute("rtnInfo", eBINonMemberService.selectNonMemberDtl(pEBINonMemberDTO));

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("MEM_CD"); //회원 코드 (직급, 부서 등)
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        return "front/eb/ebi/EBINonMemberStep1.front";
    }

    /**
     *  비회원 교육 과정 신청 step2 (완료)
     */
    @GetMapping(value="/step2")
    public String getNonMemberStep2(EBINonMemberDTO pEBINonMemberDTO, COMenuDTO pCOMenuDTO, ModelMap modelMap) throws Exception
    {
        pCOMenuDTO.setMenuSeq(613);

        modelMap.addAttribute("parntMenuList", cOBUserMenuService.getMenuList(pCOMenuDTO));

        pEBINonMemberDTO.setDetailsKey(pEBINonMemberDTO.getEdctnSeq().toString());

        modelMap.addAttribute("rtnData", eBINonMemberService.selectNonMemberChk(pEBINonMemberDTO));
        modelMap.addAttribute("ptcptData", eBINonMemberService.selectNonMemberApplyPtcptDtl(pEBINonMemberDTO));

        return "front/eb/ebi/EBINonMemberStep2.front";
    }





    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/education/apply/non-member")
    public class EBINonMemberRestController {

        private final COCodeService cOCodeService;

        //비회원 교육 신청 내역 조회
        @PostMapping(value="/search")
        public EBINonMemberDTO searchPtcptCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            try{
                pEBINonMemberDTO.setRespCnt(eBINonMemberService.searchPtcptCnt(pEBINonMemberDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pEBINonMemberDTO;
        }

        @Operation(summary = "비회원 교육 신청자 정원체크", tags = "비회원 교육 신청 정원체크", description = "")
        @PostMapping(value="/fxnumChk")
        public EBINonMemberDTO selectFxnumChk(@Valid @RequestBody EBINonMemberDTO pEBINonMemberDTO) throws Exception
        {
            EBINonMemberDTO tempDto = new EBINonMemberDTO();
            try {
                tempDto = eBINonMemberService.selectFxnumChk(pEBINonMemberDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return tempDto;
        }

        @Operation(summary = "비회원 교육 과정 신청자 신청 취소", tags = "비회원 교육 과정 신청자 신청 취소", description = "")
        @PostMapping(value="/updatePtcpt")
        public EBINonMemberDTO updatePtcpt(EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception
        {
            try {

                pEBINonMemberDTO.setRespCnt(eBINonMemberService.updatePtcpt(pEBINonMemberDTO, request));

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pEBINonMemberDTO;
        }

        @Operation(summary = "비회원 교육 신청자 등록", tags = "비회원 교육 신청자 등록", description = "")
        @PostMapping(value="/setPtcptInfo")
        public EBINonMemberDTO setPtcptInfo(EBINonMemberDTO pEBINonMemberDTO, /*ModelMap modelMap,*/ HttpServletRequest request) throws Exception
        {
            //비회원 교육 신청자를 등록, 등록할 때 이미 회원이 있으면 취소

            EBINonMemberDTO temoDto = new EBINonMemberDTO();

            try {
                pEBINonMemberDTO.setEdctnSeq(Integer.parseInt(pEBINonMemberDTO.getDetailsKey()));

                temoDto = eBINonMemberService.setPtcptInfo(pEBINonMemberDTO, request);

//                modelMap.addAttribute("rtnData", temoDto);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return temoDto;
        }


    }

}

