package com.kap.front.controller.eb;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBLctrDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sm.smi.SMISmsCntnDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육 과정 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육 과정 Controller
 * @author 김학규
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육사업 > 교육신청")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/education")
public class EBACouseController {

    /** 서비스 **/
    private final EBACouseService eBACouseService;

    private final EBBEpisdService eBBEpisdService;

    private final SMJFormService sMJFormService;

    public final MPEPartsCompanyService mpePartsCompanyService;

    /** 부품사 회원정보 서비스 **/
    private final MPAUserService mpaUserService;

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /** 메일 서비스 **/
    private final COMessageService cOMessageService;

    // SMS 내용 관리 서비스
    private final SMISmsCntnService smiSmsCntnService;

    /**
     * 교육과정 신청 목록
     */
    @GetMapping(value="/apply/list")
    public String getEducationApplyList(ModelMap modelMap, EBBEpisdDTO eBBEpisdDTO) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseList.front";
        try
        {
            eBBEpisdDTO.setPageRowSize(null);
            EBBEpisdDTO rtnList  = eBBEpisdService.selectEpisdList(eBBEpisdDTO);
            SMJFormDTO smjFormDTO = new SMJFormDTO();
            smjFormDTO.setTypeCd("BUSINESS03");
            SMJFormDTO rtnFormDto = sMJFormService.selectFormDtl(smjFormDTO);

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

            modelMap.addAttribute("rtnData", rtnList);
            modelMap.addAttribute("rtnFormDto", rtnFormDto);
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);

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
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/apply/select")
    public String getCousePageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        String rtnView = "front/eb/eba/EBACouseListAjax";
        try
        {
            eBBEpisdDTO.setApplyListYn("Y");
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        if(eBBEpisdDTO.getCnnctCd() != null){
            rtnView = "front/eb/eba/EBACouseRelListAjax";
        }

        return rtnView;
    }

    /**
     * 교육회차관리 목록을 조회한다.
     */
    @RequestMapping(value = "/apply/episdSelect")
    public String getEpisdPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            eBBEpisdDTO.setSrchOrder(5); // 회차 목록 - 접수 마감일 최신순으로 정렬
            modelMap.addAttribute("rtnData", eBBEpisdService.selectCouseChildEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/eb/eba/EBACouseChildEpisdListAjax";
    }

    /**
     * 교육과정 신청 상세
     */
    @GetMapping(value="/apply/detail")
    public String getEducationApplyDtl(EBACouseDTO eBACouseDTO, EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseDtl.front";
        try
        {
            HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);

            EBACouseDTO rtnDto = (EBACouseDTO)rtnMap.get("rtnData");
            List<EBACouseDTO> rtnEpisdList = (List<EBACouseDTO>) rtnMap.get("rtnEpisdList");//과정에 소속된 차수 목록
            List<EBACouseDTO> rtnTrgtData = (List<EBACouseDTO>) rtnMap.get("rtnTrgtData");//학습대상 목록

            //교육과정연계 상세 조회
            List<EBACouseDTO> relList = eBACouseService.selectEdctnRelList(rtnDto);

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            cdDtlList.add("CLASS_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            //과정분류 - 소분류
            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("CLASS01");
            modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS02");
            modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS03");
            modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

            cdDtlList.add("STDUY_MTHD"); //학습방식
            cdDtlList.add("CMPTN_STND");//수료 기준 - 출석
            cdDtlList.add("CMPTN_JDGMT");//수료 기준 - 평가
            cdDtlList.add("STDUY_DD");//학습시간 - 학습일
            cdDtlList.add("STDUY_TIME");//학습시간 - 학습시간
            cdDtlList.add("LCNS_CNNCT");//자격증연계코드

            cdDtlList.add("EDCTN_REL");//교욱과정 연계(선수, 후속)

            modelMap.addAttribute("studyCdList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

            //과정의 대분류 코드를 호출한다
            if(rtnDto != null && rtnDto.getNm() != null){
                cOCodeDTO.setCd(rtnDto.getCtgryCd());
                List<COCodeDTO> tempDTO = cOCodeService.getCdIdPrntList(cOCodeDTO);

                String prntCd = "";
                for(COCodeDTO a : tempDTO){
                    if(a.getDpth() == 2){
                        prntCd = a.getCd();
                        break;
                    }
                }
                rtnDto.setPrntCd(prntCd);
            }

            Device device = DeviceUtils.getCurrentDevice(request);

            // Device (web, mobile) 구분
            String deviceType = "web";

            if (device != null && (device.isMobile() || device.isTablet()))
            {
                deviceType = "mbl";
            }

            modelMap.addAttribute("rtnData", rtnDto);//과정 기본정보
            modelMap.addAttribute("rtnEpisdList", rtnEpisdList);//과정에 소속된 차수목록
            modelMap.addAttribute("rtnTrgtData", rtnTrgtData);//학습 대상 목록
            modelMap.addAttribute("relList", relList);//과정 연계 목록

            modelMap.addAttribute("relList1", rtnMap.get("relList1"));//과정 연계 목록 - 선수목록
            modelMap.addAttribute("relList2", rtnMap.get("relList2"));//과정 연계 목록 - 후속목록
            modelMap.addAttribute("deviceType", deviceType);//디바이스 체크
            modelMap.addAttribute("paramDto", eBBEpisdDTO);//


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
     * 교육참여자 목록을 호출한다.
     */
    @RequestMapping(value = "/apply/episdLctrDtlList")
    public String getLcptListPageAjax(EBBLctrDTO eBBLctrDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
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
        return "front/eb/eba/EBAEduLctrListAjax";
    }

    /*
    학습대상 공통코드 분류
     */
    private List<EmfMap> setEdTargetList(String arg) throws Exception{

        List<EmfMap> targetList = new ArrayList<>();

        try{

            ArrayList<String> cdDtlList = new ArrayList<String>();
            //과정분류 공통코드 세팅
            // 코드 set
            cdDtlList.add(arg);

            HashMap<String, List<COCodeDTO>> temp =  cOCodeService.getCmmCodeBindAll(cdDtlList);

            List<COCodeDTO> tempList = temp.get(arg);

            for(COCodeDTO a : tempList){

                if(a.getDpth() == 2){
                    EmfMap targetMap = new EmfMap();

                    List<EmfMap> dpth3List = new ArrayList<>();
                    for(COCodeDTO b : tempList){
                        if(b.getCd().contains(a.getCd())){
                            EmfMap map = new EmfMap();

                            map.put("cd", b.getCd());
                            map.put("cdNm", b.getCdNm());
                            map.put("dpth", b.getDpth());
                            dpth3List.add(map);
                            targetMap.put("edList", dpth3List);
                        }
                    }
                    targetList.add(targetMap);
                }


            }


        }catch (Exception e){
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return targetList;

    }

    /**
     * 교육과정 신청 상세
     */
    @GetMapping(value="/apply/step1")
    public String getApplyStep(EBBEpisdDTO eBBEpisdDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseStep1.front";

        try{

            //회원 기본정보 호출
            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtlTab(mpaUserDto);

            if(applicantDto != null){

                if(applicantDto.getMemCd().equals("CP")){

                    EBACouseDTO eBACouseDTO = new EBACouseDTO();
                    eBACouseDTO.setDetailsKey(eBBEpisdDTO.getDetailsKey());
                    //선택한 과정정보 호출
                    HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);
                    EBACouseDTO rtnDto = (EBACouseDTO)rtnMap.get("rtnData");

                    COPaginationUtil page = new COPaginationUtil();

                    page.setCurrentPageNo(eBBEpisdDTO.getPageIndex());
                    page.setRecordCountPerPage(eBBEpisdDTO.getListRowSize());

                    page.setPageSize(eBBEpisdDTO.getPageRowSize());

                    eBBEpisdDTO.setFirstIndex( page.getFirstRecordIndex() );
                    eBBEpisdDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

                    HashMap<String, Object> episdDto = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

                    if(applicantDto.getMemCd().equals("CP")) {
                        mpePartsCompanyDTO.setBsnmNo(COUserDetailsHelperService.getAuthenticatedUser().getBsnmNo());
                        MPEPartsCompanyDTO originList = mpePartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

                        if (originList.getList().size() != 0) {
                            modelMap.addAttribute("rtnInfo", originList.getList().get(0));
                        }
                        modelMap.addAttribute("applicantInfo", applicantDto);
                        modelMap.addAttribute("sqInfoList", mpePartsCompanyService.selectPartsComSQInfo(mpePartsCompanyDTO));
                    }
                    //회원 부품사정보 호출
                    modelMap.addAttribute("rtnData", rtnDto);
                    modelMap.addAttribute("episdDto", episdDto.get("rtnData"));

                }else if(applicantDto.getMemCd().equals("CO")){

                    modelMap.addAttribute("msg", "교육신청은 부품사 회원만 신청 가능합니다.");
                    modelMap.addAttribute("url", "/");
                    vwUrl = "front/COBlank.error";

                }else if(applicantDto.getMemCd().equals("CS")){
                    modelMap.addAttribute("msg", "위원 계정은 해당 서비스를 이용할 수 없습니다.");
                    modelMap.addAttribute("url", "/");
                    vwUrl = "front/COBlank.error";
                }

            }else{
                modelMap.addAttribute("msg", "여기서 알럿뜨는건지 확인222222.");
                modelMap.addAttribute("url", "/");
                vwUrl = "front/COBlank.error";
            }

        }catch (Exception e){

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return vwUrl;
    }

    @Operation(summary = "교육차수 신청자 가능여부 체크", tags = "교육차수 신청자 등록", description = "")
    @PostMapping(value="/apply/setPtcptInfoCheck")
    public String setPtcptInfoCheck(EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap) throws Exception
    {

        //교육차수 신청자를 등록한다. 등록할때 이미 회원이 있으면 취소
        EBBPtcptDTO temoDto = new EBBPtcptDTO();
        try {
            temoDto = eBBEpisdService.setPtcptInfoCheck(eBBPtcptDTO);

            modelMap.addAttribute("rtnData", temoDto);

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

    @Operation(summary = "교육차수 신청자 등록", tags = "교육차수 신청자 등록", description = "")
    @PostMapping(value="/apply/setPtcptInfo")
    public String setPtcptInfo(EBBPtcptDTO eBBPtcptDTO, ModelMap modelMap) throws Exception
    {

        //교육차수 신청자를 등록한다. 등록할때 이미 회원이 있으면 취소
        EBBPtcptDTO temoDto = new EBBPtcptDTO();
        try {
            temoDto = eBBEpisdService.setPtcptInfo(eBBPtcptDTO);

            modelMap.addAttribute("rtnData", temoDto);

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

    @GetMapping(value="/apply/step2")
    public String getApplyStep2(EBBEpisdDTO eBBEpisdDTO, MPEPartsCompanyDTO mpePartsCompanyDTO, MPAUserDto mpaUserDto, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseStep2.front";

        try{
            EBBPtcptDTO eBBPtcptDTO = new EBBPtcptDTO();
            eBBPtcptDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());

            eBBPtcptDTO.setEdctnSeq(Integer.parseInt(eBBEpisdDTO.getDetailsKey()));
            eBBPtcptDTO.setEpisdOrd(eBBEpisdDTO.getEpisdOrd());
            eBBPtcptDTO.setEpisdYear(eBBEpisdDTO.getEpisdYear());


            //신청전
            EBBPtcptDTO rtnPtcptDto = eBBEpisdService.selectPtcptDtl(eBBPtcptDTO);
            //신청후



            EBACouseDTO eBACouseDTO = new EBACouseDTO();

            eBACouseDTO.setDetailsKey(eBBEpisdDTO.getDetailsKey());
            //선택한 과정정보 호출
            HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);

            EBACouseDTO rtnDto = (EBACouseDTO)rtnMap.get("rtnData");

            //회원 기본정보 호출
            mpaUserDto.setDetailsKey(String.valueOf(COUserDetailsHelperService.getAuthenticatedUser().getSeq())) ;
            MPAUserDto applicantDto = mpaUserService.selectUserDtlTab(mpaUserDto);

            //교육신청 메일발송 시작

            HashMap<String, Object> episdDto = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);
            EBBEpisdDTO tempEpisdDTO = (EBBEpisdDTO)episdDto.get("rtnData");
            //메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("[KAP] 교육신청 완료 안내");

            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            receiverDto.setEmail(applicantDto.getEmail());
            receiverDto.setName(applicantDto.getName());
            receiverDto.setNote1(rtnDto.getNm());
            receiverDto.setNote2(tempEpisdDTO.getPlaceNm());
            receiverDto.setNote3(CODateUtil.convertDate(tempEpisdDTO.getEdctnStrtDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "-"));
            receiverDto.setNote4(CODateUtil.convertDate(tempEpisdDTO.getEdctnEndDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "-"));
            receiverDto.setNote5(applicantDto.getCmpnNm());
            cOMailDTO.getReceiver().add(receiverDto);

            cOMessageService.sendMail(cOMailDTO, "EBBEduApplyStep3.html");

            //교육신청 메일발송 끝

            //SMS 발송 시작
            //SMS 발송
            receiverDto.setMobile(applicantDto.getHpNo());
            COSmsDTO smsDto = new COSmsDTO();

            SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();
            smiSmsCntnDTO.setSmsCntnCd("SMS01"); //교육신청 완료 코드
            smiSmsCntnDTO.setSmsCntnSeq(3);
            smsDto.getReceiver().add(receiverDto);
            smsDto.setMessage(COStringUtil.getHtmlStrCnvr(smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO).getCntn()));

            cOMessageService.sendSms(smsDto, "");
            //SMS 발송 끝

            modelMap.addAttribute("rtnPtcptDto", rtnPtcptDto);
            modelMap.addAttribute("rtnData", rtnDto);

        }catch (Exception e){

            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 통합검색 탭 교육/세미나 목록 조회
     */
    @RequestMapping(value = "/apply/select/{menuType:education}")
    public String getCouseTabPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            eBBEpisdDTO.setApplyListYn("Y");
            eBBEpisdDTO.setSrchYn("Y");
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
            modelMap.addAttribute("episdCnt", eBBEpisdService.selectEpisdListCnt(eBBEpisdDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/eb/eba/EBACouseListAjax";
    }


    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/education")
    public class EBACouseRestController {

        private final COCodeService cOCodeService;

        /**
         * 교육과정 분류 3뎁스 호출
         */
        @PostMapping(value = "/classTypeList")
        public List<COCodeDTO> classTypeList(@RequestBody COCodeDTO cOCodeDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
            List<COCodeDTO> detailList = null;
            try
            {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }

        /**
         * 교육과정 목록을 조회한다.(전체 레이어팝업)
         */
        @PostMapping(value = "/apply/selectEduList")
        public EBBEpisdDTO getCouseListLayerAjax(@RequestBody EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
        {

            EBBEpisdDTO rtnMap = new EBBEpisdDTO();
            try
            {
                rtnMap = eBBEpisdService.selectEpisdList(eBBEpisdDTO);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return rtnMap;
        }

        /**
         * 교육과정 신청 상세
         */
        @PostMapping(value="/apply/detailLayer")
        public HashMap<String, Object> getEducationApplyDtl(@RequestBody EBACouseDTO eBACouseDTO, ModelMap modelMap) throws Exception {
            HashMap<String, Object> resultMap;


            try {

                resultMap = new HashMap<>();

                HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);

                EBACouseDTO rtnDto = (EBACouseDTO) rtnMap.get("rtnData");
                EBBEpisdDTO ebbEpisdDTO = new EBBEpisdDTO();
                ebbEpisdDTO.setEdctnSeq(rtnDto.getEdctnSeq());
                ebbEpisdDTO.setEpisdYear(eBACouseDTO.getEpisdYear());

                EBBEpisdDTO rtnEpisd = eBACouseService.selectEpisdLayerList(ebbEpisdDTO);//과정에 소속된 차수 목록

                resultMap.put("rtnDto", rtnDto);
                resultMap.put("rtnEpisdList", rtnEpisd.getList());
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return resultMap;
        }

        @Operation(summary = "교육차수 신청 전 교육 정상체크", tags = "교육차수 신청 전 교육 정상체크", description = "")
        @PostMapping(value="/apply/EpisdChk")
        public String selectEpisdDtlChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {

            /*
                A : 교육대기 (이 경우에만 신청이 가능함)
                B : 교육중
                C : 교육완료
                D : 폐강됨


            */
            String rtnStr = "";
            HashMap<String, Object> tempDto = new HashMap<>();

            try {

                tempDto = eBBEpisdService.selectEpisdDtl(eBBEpisdDTO);

                if(tempDto ==null || tempDto.isEmpty()){

                }else if(tempDto.get("rtnData") !="" && tempDto.get("rtnData") !=null ){
                    EBBEpisdDTO episdDto = (EBBEpisdDTO)tempDto.get("rtnData");

                    //폐강
                    if( !episdDto.getEdctnSttsCd().equals("EDCTN_STTS_CD01")   ){
                        rtnStr = "D";
                    }else{
                        if(episdDto.getEdctnStatusNm().equals("교육대기")){
                            rtnStr = "A";
                        }

                        if(episdDto.getEdctnStatusNm().equals("교육중")){
                            rtnStr = "B";
                        }

                        if(episdDto.getEdctnStatusNm().equals("교육완료")){
                            rtnStr = "C";
                        }
                    }

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

            return rtnStr;
        }

        @Operation(summary = "교육차수 신청자 정원체크", tags = "교육차수 중복체크", description = "")
        @PostMapping(value="/apply/fxnumChk")
        public EBBEpisdDTO selectFxnumChk(@Valid @RequestBody EBBEpisdDTO eBBEpisdDTO) throws Exception
        {

            EBBEpisdDTO tempDto = new EBBEpisdDTO();

            try {

                tempDto = eBBEpisdService.selectFxnumChk(eBBEpisdDTO);

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



    }

}

