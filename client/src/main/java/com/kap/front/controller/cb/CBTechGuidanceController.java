package com.kap.front.controller.cb;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultUpdateDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.sm.smi.SMISmsCntnDTO;
import com.kap.service.*;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.cb.cbb.CBBManageConsultMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 메인 페이지
 * </pre>
 * @ClassName		: CBATechGuidanceController.java
 * @Description		: 컨설팅 페이지
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.12.20	  임서화	             최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/consulting/{type:tech|manage}")
public class CBTechGuidanceController {

    /** 코드 서비스 **/

    /** 서비스 **/
    private final CBATechGuidanceService cBATechGuidanceService;
    private final CBBManageConsultService cBBManageConsultService;
    private final MPAUserService mPAUserService;
    private final MPEPartsCompanyService mPEPartsCompanyService;
    private final COCodeService cOCodeService;
    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    private final CBBManageConsultMapper cBBManageConsultMapper;
    public final COGCntsService pCOGCntsService;

    // SMS 내용 관리 서비스
    private final SMISmsCntnService smiSmsCntnService;

    //이메일 발송
    private final COMessageService cOMessageService;

    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;

    @Value("${app.site.name}")
    private String siteName;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
        String url = "";
        try {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            cBATechGuidanceInsertDTO.setFirstIndex(0);
            cBATechGuidanceInsertDTO.setRecordCountPerPage(3);

            MPAUserDto mpaUserDto = new MPAUserDto();
            List typeList = new ArrayList();
            List workCdList = new ArrayList();
            workCdList.add("MEM_CD04001");
            mpaUserDto.setMemCd("CS");
            mpaUserDto.setCmssrWorkList(workCdList);

            if(type.equals("tech")){
                url = "front/cb/cba/CBATechGuidanceIndex.front";
                modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsConsultingDtl(pCOGCntsDTO, "699", "N"));
                typeList.add("MEM_CD03001");
                typeList.add("MEM_CD03002");
            }else{
                url = "front/cb/cbb/CBBManageConsultIndex.front";
                modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsConsultingDtl(pCOGCntsDTO, "700", "N"));
                typeList.add("MEM_CD03003");

            }
            mpaUserDto.setCmssrTypeList(typeList);
            cBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectConsultingFilePath(cBATechGuidanceInsertDTO);
            mpaUserDto.setExcelYn("Y");
            modelMap.addAttribute("rtnDto", mPAUserService.selectUserList(mpaUserDto));
            modelMap.addAttribute("fileData", cBATechGuidanceInsertDTO);
            modelMap.addAttribute("loginMap", cOLoginUserDTO);

            RequestContextHolder.getRequestAttributes().removeAttribute("contentAuth", RequestAttributes.SCOPE_SESSION);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return url;
    }

    @GetMapping("/application")
    public String getConsultApplicationPage(ModelMap modelMap, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
        String url = "";
        try {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

            if(cOLoginUserDTO != null) {
                String authCd = cOLoginUserDTO.getAuthCd();
                if(authCd.equals("CP")){

                    MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();
                    mpePartsCompanyDTO.setBsnmNo( cOLoginUserDTO.getBsnmNo());

                    mpePartsCompanyDTO = mPEPartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);

                    String ctgryCd = mpePartsCompanyDTO.getList().get(0).getCtgryCd();

                    if(ctgryCd.equals("COMPANY01002") || ctgryCd.equals("COMPANY01001")){
                        if (type.equals("tech")) {
                            url = "front/cb/cba/CBATechGuidanceApplication.front";
                        } else {
                            url = "front/cb/cbb/CBBManageConsultApplication.front";
                        }
                    }else{
                        modelMap.addAttribute("msg", "1, 2차 부품사만 신청 가능합니다.");
                        modelMap.addAttribute("url", "/");
                        url = "front/COBlank.error";
                    }
                }else if(authCd.equals("CS")){
                    modelMap.addAttribute("msg", "위원회원은 해당 서비스를 이용할 수 없습니다.");
                    modelMap.addAttribute("url", "/");
                    url = "front/COBlank.error";
                }else if(authCd.equals("CO")){
                    modelMap.addAttribute("msg", "해당 사업은 부품사 회원만 신청 가능합니다.");
                    modelMap.addAttribute("url", "/");
                    url = "front/COBlank.error";
                }
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                url = "front/COBlank.error";
            }
        }catch(Exception e){
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return url;
    }

    @PostMapping("/insert")
    public String insertConsultInfoApplicationPage(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO,  MultipartHttpServletRequest multiRequest, ModelMap modelMap, @PathVariable("type") String type) throws Exception {
        try {

            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            //SMS 발송
            COSmsDTO smsDto = new COSmsDTO();
                cOMailDTO.setSubject("["+siteName+"] 컨설팅사업 신청 완료 안내");
                //수신자 정보
                COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                if(type.equals("tech")){
                    pCBATechGuidanceInsertDTO.setRegId(cOLoginUserDTO.getId());
                    pCBATechGuidanceInsertDTO.setRegIp(cOLoginUserDTO.getLoginIp());
                    pCBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOLoginUserDTO.getSeq()));
                    pCBATechGuidanceInsertDTO.setRsumeSttsCd("MNGTECH_STATUS_01");
                    modelMap.addAttribute("actCnt", cBATechGuidanceService.insertUserTechGuidance(pCBATechGuidanceInsertDTO, multiRequest));
                    pCBATechGuidanceInsertDTO.setDetailsKey(String.valueOf(pCBATechGuidanceInsertDTO.getCnstgSeq()));
                    pCBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceInsertDTO);
                    //이메일
                    receiverDto.setEmail(cOLoginUserDTO.getEmail());
                    //이름
                    receiverDto.setName(cOLoginUserDTO.getName());
                    //사업명(치환문자1)
                    receiverDto.setNote1("2024 상주기술지도");
                    //신청분야(치환문자2)
                    String cbsnCdNm = "";
                    if(pCBATechGuidanceInsertDTO.getCbsnCd().contains("METAL")){
                        cbsnCdNm = "금속분야"+"/"+pCBATechGuidanceInsertDTO.getCbsnNm();
                    }else if(pCBATechGuidanceInsertDTO.getCbsnCd().contains("NON")){
                        cbsnCdNm = "비금속분야"+"/"+pCBATechGuidanceInsertDTO.getCbsnNm();
                    }else if(pCBATechGuidanceInsertDTO.getCbsnCd().contains("INDUS")){
                        cbsnCdNm = "기타"+"/"+pCBATechGuidanceInsertDTO.getEtcNm();
                    }
                    receiverDto.setNote2(cbsnCdNm);
                    //부품사명(치환문자3)
                    receiverDto.setNote3(pCBATechGuidanceInsertDTO.getCmpnNm());
                    //수신자 번호
                    receiverDto.setMobile(pCBATechGuidanceInsertDTO.getHpNo());
                }else{
                    pCBBManageConsultInsertDTO.setRegId(cOLoginUserDTO.getId());
                    pCBBManageConsultInsertDTO.setRegIp(cOLoginUserDTO.getLoginIp());
                    pCBBManageConsultInsertDTO.setMemSeq(String.valueOf(cOLoginUserDTO.getSeq()));
                    pCBBManageConsultInsertDTO.setRsumeSttsCd("MNGCNSLT_STATUS01");
                    modelMap.addAttribute("cnstgSeq", cBBManageConsultService.insertUserManageConsult(pCBBManageConsultInsertDTO, multiRequest));
                    pCBBManageConsultInsertDTO.setDetailsKey(String.valueOf(pCBBManageConsultInsertDTO.getCnstgSeq()));
                    pCBBManageConsultInsertDTO = cBBManageConsultMapper.selectManageConsultDtl(pCBBManageConsultInsertDTO);

                    //이메일
                    receiverDto.setEmail(cOLoginUserDTO.getEmail());
                    //이름
                    receiverDto.setName(cOLoginUserDTO.getName());
                    //사업명(치환문자1)
                    receiverDto.setNote1("2024 상주경영컨설팅");
                    //신청분야(치환문자2)
                    receiverDto.setNote2(pCBBManageConsultInsertDTO.getAppctnFldNm());
                    //부품사명(치환문자3)
                    receiverDto.setNote3(pCBBManageConsultInsertDTO.getCmpnNm());
                    //수신자 번호
                    receiverDto.setMobile(pCBBManageConsultInsertDTO.getHpNo());
                }
                //신청일
                String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
                //신청일(치환문자4)
                receiverDto.setNote4(field2);
                //수신자 정보 등록
                cOMailDTO.getReceiver().add(receiverDto);
                cOMessageService.sendMail(cOMailDTO, "CBTechGuidanceEmail.html");

            //문자 발송
            smsDto.setTitle("컨설팅사업 신청 완료 안내");
            smsDto.getReceiver().add(receiverDto);
            SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();
            smiSmsCntnDTO.setSmsCntnCd("SMS03"); // 컨설팅 신청 완료 구분 코드
            smsDto.setMessage(COStringUtil.replaceHTML(smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO).getCntn()));
            cOMessageService.sendSms(smsDto, "");

        }catch(Exception e){
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }
    @GetMapping("/consInfoApply")
    public String getConsultInfoApplicationPage(ModelMap modelMap, CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, @PathVariable("type") String type) throws Exception {
        String url = "";

        try {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            if(cOLoginUserDTO != null){

                if(type.equals("tech")){
                    url = "front/cb/cba/CBATechGuidanceWrite.front";
                }else{
                    url = "front/cb/cbb/CBBManageConsultWrite.front";
                }

                ArrayList<String> cdDtlList = new ArrayList<String>();
                cdDtlList.add("ADDR_CD"); // 소재지 코드
                cdDtlList.add("APPCTN_RSN_CD"); // 신청사유 코드
                cdDtlList.add("TEC_GUIDE_INDUS"); // 업종
                cdDtlList.add("TEC_GUIDE_APPCTN"); // 직종 코드
                cdDtlList.add("COMPANY_TYPE"); //부품사 구분 코드
                cdDtlList.add("MNGCNSLT_APP_AREA"); // 신청 분야 코드
                modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
                cBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectConsultingFilePath(cBATechGuidanceInsertDTO);
                modelMap.addAttribute("fileData", cBATechGuidanceInsertDTO);

            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                url = "front/COBlank.error";
            }
        }catch(Exception e){
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return url;
    }
    @GetMapping("/complete")
    public String getConsultInfoCompletePage(ModelMap modelMap, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
        String url = "";
        try {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
            int cnstgSeq = Integer.parseInt(request.getParameter("cnstgSeq"));
            int memSeq = cBATechGuidanceMapper.selectWriteMemSeq(cnstgSeq);

            if(cOLoginUserDTO != null) {
                if(memSeq == cOLoginUserDTO.getSeq()){
                    if (type.equals("tech")) {
                        url = "front/cb/cba/CBATechGuidanceComplete.front";
                    } else {
                        url = "front/cb/cbb/CBBManageConsultComplete.front";
                    }
                }else{
                    modelMap.addAttribute("msg", "잘못된 접근입니다.");
                    modelMap.addAttribute("url", "/");
                    url = "front/COBlank.error";
                }
            }else{
                modelMap.addAttribute("msg", "잘못된 접근입니다.");
                modelMap.addAttribute("url", "/");
                url = "front/COBlank.error";
            }
        }catch(Exception e){
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return url;
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="consulting/{type:tech|manage}")
    public class CBTechGuidanceRestController {

        private final MPAUserService mPAUserService;
        private final MPEPartsCompanyService mPEPartsCompanyService;
        private final CBBManageConsultService cBBManageConsultService;
        private final CBATechGuidanceService cBATechGuidanceService;
        private final COCodeService cOCodeService;

        /**
         * 멤버 키로 상세 정보 검색
         */
        @PostMapping(value = "/selectDtlInfo")
        @ResponseBody
        public MPAUserDto selectDtlInfo(@Valid @RequestBody MPAUserDto mpaUserDto) throws Exception {

            return mPAUserService.selectUserDtlTab(mpaUserDto);
        }

        /**
         * 멤버 키로 상세 정보 검색
         */
        @PostMapping(value = "/checkPartsCompany")
        @ResponseBody
        public MPEPartsCompanyDTO checkPartsCompany(@Valid @RequestBody MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
            return mPEPartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);
        }

        /**
         * 사업자 번호로 회사 정보 찾기
         */
        @PostMapping(value = "/bsnmNoSearch")
        @ResponseBody
        public MPEPartsCompanyDTO bsnmNoSearch(@Valid @RequestBody MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
            return mPEPartsCompanyService.selectPartsCompanyDtl(mpePartsCompanyDTO);
        }

        /**
         *  지역 정보로 서브 지역 정보 찾기
         */
        @PostMapping(value = "/subAddrSelect")
        @ResponseBody
        public List<COCodeDTO> selectSubAddr(@RequestBody COCodeDTO cOCodeDTO) throws Exception {
            List<COCodeDTO> detailList = null;
            try {
                detailList = cOCodeService.getCdIdList(cOCodeDTO);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }

        /**
         * 담당임원 정보 입력
         */
        @PostMapping(value = "/cmssrInfo")
        @ResponseBody
        public int insertCmssrInfo(@RequestBody CBBManageConsultInsertDTO cBBManageConsultInsertDTO) throws Exception {
            try {
                cBBManageConsultService.insertUserManageCmssrInfoConsult(cBBManageConsultInsertDTO);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return cBBManageConsultService.insertUserManageCmssrInfoConsult(cBBManageConsultInsertDTO);
        }

        /**
         * 신청 완료 페이지 정보
         */
        @PostMapping(value = "/completeInfo")
        @ResponseBody
        public CBATechGuidanceInsertDTO selectCompleteInfo(@RequestBody CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, @PathVariable("type") String type) throws Exception {
            return cBATechGuidanceService.selectCompleteInfo(cBATechGuidanceInsertDTO);
        }
    }
}
