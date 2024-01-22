package com.kap.front.controller.cb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultUpdateDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.CBBManageConsultService;
import com.kap.service.COCodeService;
import com.kap.service.MPEPartsCompanyService;
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
    private final COCodeService cOCodeService;

    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;

    @GetMapping("/index")
    public String getConsultIndexPage(ModelMap modelMap, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
        String url = "";
        if(type.equals("tech")){
            url = "front/cb/cba/CBATechGuidanceIndex.front";
        }else{
            url = "front/cb/cbb/CBBManageConsultIndex.front";
        }

        MPAUserDto mpaUserDto = new MPAUserDto();

        List typeList = new ArrayList();
            typeList.add("MEM_CD03001");
            typeList.add("MEM_CD03002");
        List workCdList = new ArrayList();
            workCdList.add("MEM_CD04001");
        mpaUserDto.setCmssrTypeList(typeList);
        mpaUserDto.setMemCd("CS");
        mpaUserDto.setCmssrWorkList(workCdList);

        modelMap.addAttribute("rtnDto", mPAUserService.selectUserList(mpaUserDto));

        return url;
    }
    @GetMapping("/application")
    public String getConsultApplicationPage(ModelMap modelMap, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
        String url = "";
        try {
            COUserDetailsDTO cOLoginUserDTO = (COUserDetailsDTO) RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);

            if(cOLoginUserDTO != null) {

                if (type.equals("tech")) {
                    url = "front/cb/cba/CBATechGuidanceApplication.front";
                } else {
                    url = "front/cb/cbb/CBBManageConsultApplication.front";
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
            if(type.equals("tech")){
                pCBATechGuidanceInsertDTO.setRegId(cOLoginUserDTO.getId());
                pCBATechGuidanceInsertDTO.setRegIp(cOLoginUserDTO.getLoginIp());
                pCBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOLoginUserDTO.getSeq()));
                pCBATechGuidanceInsertDTO.setRsumeSttsCd("MNGTECH_STATUS_01");
                modelMap.addAttribute("actCnt", cBATechGuidanceService.insertUserTechGuidance(pCBATechGuidanceInsertDTO, multiRequest));
            }else{
                pCBBManageConsultInsertDTO.setRegId(cOLoginUserDTO.getId());
                pCBBManageConsultInsertDTO.setRegIp(cOLoginUserDTO.getLoginIp());
                pCBBManageConsultInsertDTO.setMemSeq(String.valueOf(cOLoginUserDTO.getSeq()));
                pCBBManageConsultInsertDTO.setRsumeSttsCd("MNGCNSLT_STATUS01");
                modelMap.addAttribute("cnstgSeq", cBBManageConsultService.insertUserManageConsult(pCBBManageConsultInsertDTO, multiRequest));
            }

        }catch(Exception e){
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }
    @GetMapping("/consInfoAppl")
    public String getConsultInfoApplicationPage(ModelMap modelMap, @PathVariable("type") String type) throws Exception {
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
            if(type.equals("tech")){
                url = "front/cb/cba/CBATechGuidanceComplete.front";
            }else{
                url = "front/cb/cbb/CBBManageConsultComplete.front";
            }

           /* //이메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("["+siteName+"] 아이디 찾기 결과 안내");
            //인증요청일시
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(coIdFindDto.getEmail());
            //이름
            receiverDto.setName("");
            //치환문자1
            receiverDto.setNote1(coIdFindDto.getName());
            receiverDto.setNote2(coIdFindDto.getId());
            //수신자 정보 등록
            cOMailDTO.getReceiver().add(receiverDto);
            cOMessageService.sendMail(cOMailDTO, "IdEmailEDM.html");*/


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
