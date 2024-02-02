package com.kap.front.controller.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.*;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduExcelDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.sv.sva.SVASurveyRspnMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyRspnScoreDTO;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 메인 페이지
 * </pre>
 * @ClassName		: COMainController.java
 * @Description		: 메인 페이지
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.17	  이옥정	             최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class EBMMypageController
{

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /** 서비스 **/
    public final EBBEpisdService eBBEpisdService;

    private final SVASurveyService sVSurveyService;

    public final MPEPartsCompanyService mpePartsCompanyService;

    /** 부품사 회원정보 서비스 **/
    private final MPAUserService mpaUserService;

    /** 방문교육 서비스 **/
    public final EBCVisitEduService ebcVisitEduService;

    public final EBDSqCertiReqService eBDSqCertiReqService;


    /**
     * 교육/세미나 사업 신청내역 목록/my-page/edu-apply/list
     */
    @GetMapping("/my-page/edu-apply/list")
    public String getApplyList(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try{

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            cdDtlList.add("CLASS_TYPE");
            cdDtlList.add("STDUY_MTHD"); //학습방식
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            //과정분류 - 소분류
            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("CLASS01");
            modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS02");
            modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS03");
            modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

            //마이페이지에서 사용할 쿼리 조건절
            //eBBEpisdDTO.setMypageYn("Y");

            //사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectMypageEduList(eBBEpisdDTO));

            //나의 1:1문의 호출

        }catch (Exception e){
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return "front/eb/ebm/EBMEduApplyList.front";

    }

    /**
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/my-page/edu-apply/select")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String rtnView = "front/eb/ebm/EBMEduListAjax";
        try
        {
            //교육 사업 신청내역 조회
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            //학습중인 과정 호출
            modelMap.addAttribute("rtnData", eBBEpisdService.selectMypageEduList(eBBEpisdDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return rtnView;
    }


    /**
     * 교육/세미나 사업 신청내역 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/qrDetail")
    public void getApplyDetail(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
    {

        //QR 이미지 타고 들어옴
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        EBBPtcptDTO ptcptDto = eBBEpisdService.selectQrPtcptDtl(eBBEpisdDTO);
        eBBEpisdDTO.setPtcptSeq(ptcptDto.getPtcptSeq());

        RequestContextHolder.getRequestAttributes().setAttribute("episdCheck", "Y", RequestAttributes.SCOPE_SESSION);
        response.sendRedirect("/my-page/edu-apply/detail?detailsKey="+eBBEpisdDTO.getDetailsKey()+"&episdYear="+eBBEpisdDTO.getEpisdYear()+"&episdOrd=" + eBBEpisdDTO.getEpisdOrd()+"&ptcptSeq="+eBBEpisdDTO.getPtcptSeq());
    }
    /**
     * 교육/세미나 사업 신청내역 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/detail")
    public String getApplyDetail(EBBEpisdDTO eBBEpisdDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String vwUrl = "";
        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        if("Y".equals(RequestContextHolder.getRequestAttributes().getAttribute("episdCheck", RequestAttributes.SCOPE_SESSION))){
            //QR 이미지 타고 들어옴
            EBBPtcptDTO ptcptDto = eBBEpisdService.selectQrPtcptDtl(eBBEpisdDTO);
            eBBEpisdDTO.setPtcptSeq(ptcptDto.getPtcptSeq());
            //로직 처리
            modelMap.addAttribute("episdCheck", "Y");
        }else{
            modelMap.addAttribute("episdCheck", "N");
        }



        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");


        //사용자 출석정보 호출

        if(rtnDto != null){

            EBBPtcptDTO eBBPtcptDTO = new EBBPtcptDTO();

            EBFEduRoomDetailDTO roomDto = (EBFEduRoomDetailDTO)rtnMap.get("roomDto");//교육장 정보
            List<EBBLctrDTO> lctrDtoList = (List<EBBLctrDTO>) rtnMap.get("lctrDtoList");//온라인교육상세 목록
            List<EBBisttrDTO> isttrList = (List<EBBisttrDTO>) rtnMap.get("isttrList");//온라인교육상세 목록


            eBBPtcptDTO.setPtcptSeq(rtnDto.getPtcptSeq());

            List<EBBPtcptDTO> ptcptList = eBBEpisdService.selectMemAtndcList(eBBPtcptDTO);


            //회원 기본정보 호출
            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtlTab(mpaUserDto);

            if(applicantDto.getMemCd().equals("CP")) {
                mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

                if (originList.getList().size() != 0) {
                    modelMap.addAttribute("rtnInfo", originList.getList().get(0));
                }
                modelMap.addAttribute("applicantInfo", applicantDto);
                modelMap.addAttribute("sqInfoList", mpePartsCompanyService.selectPartsComSQInfo(mpePartsCompanyDTO));
            }


            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));




            //온라인 교육이 아닌경우 출석 유무 로직을 실행한다.
            if(!rtnDto.getStduyMthdCd().equals("STDUY_MTHD02")) {
                //오늘 날짜 호출
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(currentDate);

                //금일 출석 유무 확인로직
                for(EBBPtcptDTO  ptcptDto : ptcptList){


                    if(formattedDate.equals(ptcptDto.getEdctnDt())){

                        if("".equals(ptcptDto.getAtndcDtm()) || ptcptDto.getAtndcDtm() == null){
                            modelMap.addAttribute("nowAtndcYn", "N");//오늘 출석 안함
                        }else{
                            modelMap.addAttribute("nowAtndcYn", "Y");//오늘 출석 함
                        }

                        if(!"".equals(ptcptDto.getLvgrmDtm()) && ptcptDto.getLvgrmDtm() != null){
                            modelMap.addAttribute("nowLvgrmYn", "Y");//오늘 퇴실 함
                        }else{
                            modelMap.addAttribute("nowLvgrmYn", "N");//오늘 퇴실 안함
                        }

                        break;
                    }

                }
            }else{
                modelMap.addAttribute("nowAtndcYn", "F");//온라인교육인 경우는 F로 들어간다
            }

            modelMap.addAttribute("rtnData", rtnDto);
            modelMap.addAttribute("roomDto", roomDto);
            modelMap.addAttribute("lctrDtoList", lctrDtoList);
            modelMap.addAttribute("isttrList", isttrList);
            modelMap.addAttribute("ptcptList", ptcptList);


            vwUrl = "front/eb/ebm/EBMEduApplyDtl.front";
        }else{
            modelMap.addAttribute("msg", "잘못된 접근입니다.");
            modelMap.addAttribute("url", "/");
            vwUrl = "front/COBlank.error";
        }

        return "front/eb/ebm/EBMEduApplyDtl.front";
    }

    /**
     * 교육/세미나 사업 수료증 출력
     */
    @GetMapping("/my-page/edu-apply/cmPtm")
    public String getCmptnPop(EBBEpisdDTO eBBEpisdDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String vwUrl = "front/eb/ebm/EBMCmptnPop";
        try{

            eBBEpisdDTO.setMypageYn("Y");
            eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
            HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

            EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");

            //사용자 출석정보 호출

            if(rtnDto != null){

                if("Y".equals(rtnDto.getCmptnYn())){
                    modelMap.addAttribute("rtnData", rtnDto);

                }else{
                    modelMap.addAttribute("msg", "잘못된 접근입니다.");
                    modelMap.addAttribute("url", "/");
                    vwUrl = "front/COBlank.error";
                }



            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
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





        return vwUrl;
    }

    /**
     * 방문교육 신청내역 상세
     */
    @GetMapping("/my-page/edu-apply/visit-edu-detail")
    public String getVisitEduApplyDetail(EBCVisitEduDTO ebcVisitEduDTO, EBCVisitEduExcelDTO ebcVisitEduExcelDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            ebcVisitEduDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq()); ;
            EBCVisitEduDTO applicantDto = ebcVisitEduService.selectApplicantInfo(ebcVisitEduDTO);

            mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
            MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

            EBCVisitEduDTO visitEduApplyList = ebcVisitEduService.selectVisitEduDtl(ebcVisitEduDTO);

            if (originList.getList().size() != 0) {
                modelMap.addAttribute("rtnInfo", originList.getList().get(0));
            }
            modelMap.addAttribute("applicantInfo", applicantDto);
            modelMap.addAttribute("sqInfoList", originList);
            modelMap.addAttribute("visitEduApplyList", visitEduApplyList);
            modelMap.addAttribute("appctnTypeList", ebcVisitEduService.selectAppctnTypeList(ebcVisitEduDTO));
            modelMap.addAttribute("isttrList", ebcVisitEduService.selectIsttrExcelList(ebcVisitEduExcelDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/eb/ebm/EBMVisitEduApplyDtl.front";
    }

    /**
     * 교육/세미나 사업 교육양도 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/transfer")
    public String getTrnsfDetail(EBBEpisdDTO eBBEpisdDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "";

        COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");

        if(rtnMap !=null){

            if(cOLoginUserDTO.getSeq().equals(rtnDto.getMemSeq()) ){

                //회원 기본정보 호출
                mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
                MPAUserDto applicantDto = mpaUserService.selectUserDtlTab(mpaUserDto);

                if(applicantDto.getMemCd().equals("CP")) {

                    mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                    MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

                    if (originList.getList().size() != 0) {

                        modelMap.addAttribute("rtnInfo", originList.getList().get(0));
                    }

                    modelMap.addAttribute("applicantInfo", applicantDto);
                    modelMap.addAttribute("sqInfoList", originList);
                }

                //회원이 속한 부품사의 회원목록 조회
                MPAUserDto cmmUserDto = eBBEpisdService.selectApplyUserList(applicantDto);

                modelMap.addAttribute("cmmUserData", cmmUserDto);

                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                // 코드 set
                cdDtlList.add("MEM_CD");
                modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

                modelMap.addAttribute("rtnData", rtnDto);


                vwUrl = "front/eb/ebm/EBMEduApplyTrnsf.front";

            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }

        }else{
            modelMap.addAttribute("msg", "잘못된 접근입니다.");
            modelMap.addAttribute("url", "/");
            vwUrl = "front/COBlank.error";
        }

        return vwUrl;

    }

    /**
     * 회원이 속한 부품사의 회원목록 조회
     */
    @GetMapping(value = "/my-page/edu-apply/cmmSelect")
    public String getCmmUserListPageAjax(MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //회원이 속한 부품사의 회원목록 조회
            modelMap.addAttribute("rtnData",  eBBEpisdService.selectApplyUserList(mpaUserDto));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/eb/ebm/EBMEduApplyTrnsfListAjax";
    }


    /**
     * 교육/세미나 온라인 교육 step1
     */
    @GetMapping("/my-page/edu-apply/onlineStep1")
    public String getOnlineStep1(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String vwUrl = "";

        COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        //신청한 교육 과정
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        if(rtnMap !=null){

            EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");

            if(cOLoginUserDTO.getSeq().equals(rtnDto.getMemSeq())){
                EBBLctrDTO eBBLctrDTO = new EBBLctrDTO();

                eBBLctrDTO.setEdctnSeq(rtnDto.getEdctnSeq());
                eBBLctrDTO.setEpisdOrd(rtnDto.getEpisdOrd());
                eBBLctrDTO.setEpisdYear(rtnDto.getEpisdYear());

                //신청한 교육과정의 온라인 목록
                modelMap.addAttribute("lctrList", eBBEpisdService.selectLctrDtlList(eBBLctrDTO));

                modelMap.addAttribute("rtnData", rtnDto);

                vwUrl = "front/eb/ebm/EBMEduApplyOnlineStep1.front";

            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }


        }else{
            modelMap.addAttribute("msg", "잘못된 접근입니다.");
            modelMap.addAttribute("url", "/");
            vwUrl = "front/COBlank.error";
        }

        return vwUrl;
    }

    /**
     * 교육참여자 목록을 호출한다.
     */
    @RequestMapping(value = "/my-page/edu-apply/onlineStep1Select")
    public String getOnlineStep1Ajax(EBBLctrDTO eBBLctrDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBBEpisdService.selectLctrDtlList(eBBLctrDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/eb/ebm/EBMEduApplyOnlineStep1Ajax";
    }

    /**
     * 교육/세미나 온라인 교육 step2
     */
    @GetMapping("/my-page/edu-apply/onlineStep2")
    public String getOnlineStep2(EBBEpisdDTO eBBEpisdDTO, EBBLctrDTO eBBLctrDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String vwUrl = "";

        COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

        eBBEpisdDTO.setDetailsKey(String.valueOf(eBBEpisdDTO.getEdctnSeq()));

        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        if(rtnMap !=null) {



            EBBEpisdDTO rtnDto = (EBBEpisdDTO) rtnMap.get("rtnData");

            if (cOLoginUserDTO.getSeq().equals(rtnDto.getMemSeq())) {

                modelMap.addAttribute("rtnData", rtnDto);
                modelMap.addAttribute("lctrList", eBBEpisdService.selectLctrDtlList(eBBLctrDTO));
                modelMap.addAttribute("nowLctrSeq", eBBLctrDTO.getLctrSeq());


                vwUrl = "front/eb/ebm/EBMEduApplyOnlineStep2.front";

                //수료여부 체크
                EBBEpisdDTO lcnsCnnctCdDto= eBBEpisdService.setCmptnChk(rtnDto);
                System.out.println("@@@ lcnsCnnctCdDto.getLcnsCnnctCd()= " + lcnsCnnctCdDto.getLcnsCnnctCd());
                if(!"LCNS_CNNCT01".equals(lcnsCnnctCdDto.getLcnsCnnctCd())){
                    System.out.println("@@@ SQ 갱신");
                    eBDSqCertiReqService.updateCertiValid(rtnDto.getEdctnSeq());
                }

            } else {
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }

        }

        return vwUrl;

    }

    /**
     * 교육/세미나 온라인 교육 step2
     */
    @RequestMapping(value = "/my-page/edu-apply/onlineStep2Select")
    public String getLcptListPageAjax(EBBPtcptDTO ebbPtcptDTO, EBBLctrDTO eBBLctrDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            EBBLctrDTO ttt = eBBEpisdService.selectLctrDtlList(eBBLctrDTO);

            EBBPtcptDTO setDto = new EBBPtcptDTO();

            setDto.setPtcptSeq(ebbPtcptDTO.getPtcptSeq());
            setDto.setLctrSeq(eBBLctrDTO.getNowLctrSeq());

            setDto.setEdctnSeq(ebbPtcptDTO.getEdctnSeq());
            setDto.setEpisdOrd(ebbPtcptDTO.getEpisdOrd());
            setDto.setEpisdYear(ebbPtcptDTO.getEpisdYear());
            setDto.setEpisdSeq(ebbPtcptDTO.getEpisdSeq());

            eBBEpisdService.setOnlinePtcptInfo(setDto);

            EBBEpisdDTO rtnDto = new EBBEpisdDTO();
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            rtnDto.setPtcptSeq(ebbPtcptDTO.getPtcptSeq());
            rtnDto.setMemSeq(cOLoginUserDTO.getSeq());
            rtnDto.setEpisdYear(ebbPtcptDTO.getEpisdYear());
            rtnDto.setEdctnSeq(ebbPtcptDTO.getEdctnSeq());
            //수료여부 체크
            EBBEpisdDTO lcnsCnnctCdDto= eBBEpisdService.setCmptnChk(rtnDto);
            System.out.println("@@@ lcnsCnnctCdDto.getLcnsCnnctCd()= " + lcnsCnnctCdDto.getLcnsCnnctCd());
            if(!"LCNS_CNNCT01".equals(lcnsCnnctCdDto.getLcnsCnnctCd())){
                System.out.println("@@@ SQ 갱신");
                eBDSqCertiReqService.updateCertiValid(rtnDto.getEdctnSeq());
            }


            modelMap.addAttribute("rtnData", eBBEpisdService.selectLctrDtlList(eBBLctrDTO));
            modelMap.addAttribute("nowLctrSeq", eBBLctrDTO.getNowLctrSeq());
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/eb/ebm/EBMEduApplyOnlineStep2Ajax";
    }



    /**
     * 교육/세미나 온라인 교육 step3
     */
    @GetMapping("/my-page/edu-apply/onlineStep3")
    public String getOnlineStep3(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "";

        COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        if(rtnMap != null){

            EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");

            if (cOLoginUserDTO.getSeq().equals(rtnDto.getMemSeq())) {

                modelMap.addAttribute("rtnData", rtnDto);

                vwUrl = "front/eb/ebm/EBMEduApplyOnlineStep3.front";

            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }



        }else{
            modelMap.addAttribute("msg", "잘못된 접근입니다.");
            modelMap.addAttribute("url", "/");
            vwUrl = "front/COBlank.error";
        }


        return vwUrl;
    }





    /**
     * 교육/세미나 사업 신청내역 상세/my-page/edu-apply/detail
     */
    @GetMapping("/my-page/edu-apply/srvStep1")
    public String getApplySrvStep1(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        eBBEpisdDTO.setMypageYn("Y");
        eBBEpisdDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        HashMap<String, Object> rtnMap = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

        EBBEpisdDTO rtnDto = (EBBEpisdDTO)rtnMap.get("rtnData");


        modelMap.addAttribute("rtnData", rtnDto);


        return "front/eb/ebm/EBMEduApplySrvStep1.front";
    }


    @GetMapping(value="/my-page/edu-apply/srvStep2")
    public String getApplySrvStep2(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/eb/ebm/EBMEduApplySrvStep2.front";
        try{

            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            eBBEpisdDTO.setMemSeq(cOLoginUserDTO.getSeq());
            EBBEpisdSurveyDTO rtnData = eBBEpisdService.selectEpisdDtlCheck(eBBEpisdDTO);

            if (cOLoginUserDTO.getSeq().equals(rtnData.getMemSeq())){

                if (rtnData.getRspnCnt() > 0){
                    modelMap.addAttribute("msg", "정상적인 접근이 아닙니다.");
                    modelMap.addAttribute("url", "/");
                    vwUrl = "front/COBlank.error";
                }else{
                    SVASurveyMstSearchDTO sVASurveyDTO = new SVASurveyMstSearchDTO();
                    sVASurveyDTO.setDetailsKey(Integer.toString(rtnData.getSrvSeq()));

                    sVASurveyDTO.setTypeCd("EDU");
                    sVASurveyDTO.setEpisdSeq(rtnData.getEpisdSeq());
                    SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVSurveyService.selectSurveyTypeEduDtl(sVASurveyDTO);

                    if (sVASurveyMstInsertDTO != null){
                        modelMap.addAttribute("rtnData", rtnData);
                        modelMap.addAttribute("rtnSurveyData", sVASurveyMstInsertDTO);
                    }else{
                        modelMap.addAttribute("msg", "잘못된 접근입니다.");
                        modelMap.addAttribute("url", "/");
                        vwUrl = "front/COBlank.error";
                    }
                }
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
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

        return vwUrl;
    }

    @Operation(summary = "응답 등록", tags = "", description = "응답 마스터, 응답 상세")
    @PostMapping(value="/my-page/edu-apply/insert")
    public String insertSurveyList(@Valid @RequestBody SVASurveyRspnMstInsertDTO sVASurveyRspnMstDTO, HttpServletRequest request , ModelMap modelMap) throws Exception
    {
        try
        {

            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            String regIp = CONetworkUtil.getMyIPaddress(request);
            String regId = cOLoginUserDTO.getId();

            sVASurveyRspnMstDTO.setRegId(regId);
            int respCnt = sVSurveyService.insertSurveyRspnList(sVASurveyRspnMstDTO, request);

            if (respCnt > 0){

                SVASurveyRspnScoreDTO sVASurveyRspnScoreDTO = new SVASurveyRspnScoreDTO();
                sVASurveyRspnScoreDTO.setSrvType("EDU");
                sVASurveyRspnScoreDTO.setTargetSeq(sVASurveyRspnMstDTO.getRfncSeq());
                sVASurveyRspnScoreDTO.setRegId(regId);
                sVASurveyRspnScoreDTO.setRegIp(regIp);

                sVSurveyService.selectSurveyScore(sVASurveyRspnScoreDTO);

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
     * 설문step3
     */
    @GetMapping(value="/my-page/edu-apply/srvStep3")
    public String getApplySrvStep3(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "";
        try
        {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            eBBEpisdDTO.setMemSeq(cOLoginUserDTO.getSeq());

            EBBEpisdSurveyDTO rtnData = eBBEpisdService.selectEpisdDtlCheck(eBBEpisdDTO);
            if (cOLoginUserDTO.getSeq().equals(rtnData.getMemSeq())){
                vwUrl = "front/eb/ebm/EBMEduApplySrvStep3.front";

                        eBBEpisdDTO.setNm(rtnData.getNm());
                modelMap.addAttribute("rtnData", eBBEpisdDTO);
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
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

        return vwUrl;
    }


    /**
     * 마이페이지 > 교육신청 내역 > 방문교육 상세 > 교육신청 취소
     */
    @PostMapping(value = "/my-page/edu-apply/visitEduApplyCancel")
    public String updateVisitEduApplyCancel(@RequestBody EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        try
        {
            ebcVisitEduDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            ebcVisitEduDTO.setModIp(COUserDetailsHelperService.getAuthenticatedUser().getLoginIp());
            ebcVisitEduDTO.setEdctnSttsCd("EBC_VISIT_CD02004");
            ebcVisitEduService.updateVisitEduApplyCancel(ebcVisitEduDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }


    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/my-page")
    public class EBACouseRestController {

        private final EBBEpisdService eBBEpisdService;

        /**
         * 마이페이지 출석체크 진행
         */
        @PostMapping(value = "/edu-apply/updateAtndcInfo")
        public String updateAtndcInfo(@RequestBody EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            String rtnStr = "";
            try
            {
                if(eBBPtcptDTO.getPtcptSeq() != null){
                    eBBEpisdService.updateAtndcInfo(eBBPtcptDTO);

                    EBBEpisdDTO rtnDto = new EBBEpisdDTO();
                    COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
                    rtnDto.setPtcptSeq(eBBPtcptDTO.getPtcptSeq());
                    rtnDto.setMemSeq(cOLoginUserDTO.getSeq());
                    rtnDto.setEpisdYear(eBBPtcptDTO.getEpisdYear());
                    rtnDto.setEdctnSeq(eBBPtcptDTO.getEdctnSeq());

                    //수료여부 체크
                    EBBEpisdDTO lcnsCnnctCdDto= eBBEpisdService.setCmptnChk(rtnDto);
                    System.out.println("@@@ lcnsCnnctCdDto.getLcnsCnnctCd()= " + lcnsCnnctCdDto.getLcnsCnnctCd());
                    if(!"LCNS_CNNCT01".equals(lcnsCnnctCdDto.getLcnsCnnctCd())){
                        System.out.println("@@@ SQ 갱신");
                        eBDSqCertiReqService.updateCertiValid(rtnDto.getEdctnSeq());
                    }


                    rtnStr = "Y";
                }else{
                    rtnStr = "N";
                }

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return rtnStr;
        }

        /**
         * 마이페이지 퇴실체크 진행
         */
        @PostMapping(value = "/edu-apply/updateLvgrmInfo")
        public String updateLvgrmInfo(@RequestBody EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            String rtnStr = "";
            try
            {
                if(eBBPtcptDTO.getPtcptSeq() != null){
                    eBBEpisdService.updateLvgrmInfo(eBBPtcptDTO);
                    rtnStr = "Y";
                }else{
                    rtnStr = "N";
                }
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return rtnStr;
        }

        @Operation(summary = "마이페이지 교육 양도", tags = "교육차수 신청자 등록", description = "")
        @PostMapping(value="/edu-apply/setTrnsf")
        public EBBPtcptDTO setTrnsf(@RequestBody EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap) throws Exception
        {

            //교육차수 신청자를 등록한다. 등록할때 이미 회원이 있으면 취소
            EBBPtcptDTO temoDto = new EBBPtcptDTO();
            try {

                temoDto = eBBEpisdService.setTrnsf(eBBPtcptDTO);

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

        /**
         * 교육신청 취소
         */
        @PostMapping(value = "/edu-apply/applyCancel")
        public String applyCancel(@RequestBody EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            String rtnStr = "";
            try
            {
                eBBEpisdService.updateApplyCancel(eBBPtcptDTO);

                rtnStr = "Y";

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return rtnStr;
        }

        /**
         * 마이페이지 > 교육신청 내역 > 방문교육 상세 > 신청분야 상세 리스트 조회
         */
        @PostMapping(value = "/edu-apply/getAppctnFldCdList")
        public String getAppctnFldCdList(@RequestBody EBCVisitEduDTO ebcVisitEduDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            String rtnStr = "";
            try
            {
                ebcVisitEduService.selectAppctnTypeList(ebcVisitEduDTO);

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return rtnStr;
        }
    }
}
