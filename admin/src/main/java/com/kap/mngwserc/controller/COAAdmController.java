package com.kap.mngwserc.controller;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.*;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre> 
 * 관리자 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COCAdmController.java
 * @Description		: 관리자 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  descripti
 *
 * 	on
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc")
public class COAAdmController {

    //관리자 서비스
    private final COAAdmService cOAAdmService;
    //코드 서비스
    private final COCodeService cOCodeService;
    //저장된 메뉴
    private final COLgnService cOLgnService;
    private final COBMenuService cOBMenuService;
    //이메일 발송
    private final COMessageService cOMessageService;

    /**
     * 관리자 목록 페이지
     */
    @GetMapping(value="/co/coa/list")
    public String getAdmListPage(ModelMap modelMap, COAAdmDTO cOAAdmDTO) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("ADMIN_AUTH_CD");

            // 로그인한 계정
            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            cOAAdmDTO.setLgnSsnId(cOUserDetailsDTO.getId());

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOAAdmDTO);
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

        return "mngwserc/co/coa/COAAdmList.admin";
    }

    /**
     * 관리자 목록 조회
     */
    @PostMapping(value = "/co/coa/select")
    public String selectAdmListPageAjax(COAAdmDTO cOAAdmDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOAAdmService.selectAdmList( cOAAdmDTO ));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coa/COAAdmListAjax";
    }

    /**
     * 관리자 권한 변경 로그를 가져온다.
     */
    @PostMapping(value="/co/coa/log-list.ajax")
    public String getAuthLogList(COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", cOAAdmService.getAuthLogList(pCOAAdmDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/co/coa/COAAdmAuthListAjax";
    }

    /**
     * 관리자 목록 엑셀다운로드
     */
    @GetMapping(value = "/co/coa/excel-down")
    public void selectAdmListExcel(COAAdmDTO cOAAdmDTO, HttpServletResponse response) throws Exception
    {
        try
        {
            cOAAdmDTO.setExcelYn("Y");
            // 목록 조회
            COAAdmDTO newAdmDTO = cOAAdmService.selectAdmList( cOAAdmDTO );
            //엑셀 생성
            cOAAdmService.excelDownload(newAdmDTO, response);
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
     * 관리자 상세 페이지
     */
    @RequestMapping(value="/co/coa/write")
    public String getAdmWritePage(COAAdmDTO cOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            cdDtlList.add("TYPE_CD");

            // 로그인한 계정
            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            cOAAdmDTO.setLgnSsnId(cOUserDetailsDTO.getId());

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", cOAAdmDTO);
            //modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));



            //System.out.println("@@@  = " + COSeedCipherUtil.encryptPassword("dnfntk1##", "hsw120211"));

            // 코드 set 관리자 권한
            cdDtlList.add("ADMIN_AUTH_CD");

            // 코드 set 관리자 계정생성제한 아이디 목록
            cdDtlList.add("ADMIN_ID_LIMIT");

            // 코드 set 부서목록
            cdDtlList.add("ADMIN_DEPT_CD");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(cOAAdmDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", cOAAdmService.selectAdmDtl(cOAAdmDTO));
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

        return "mngwserc/co/coa/COAAdmWrite.admin";
    }

    /**
     * 관리자 내정보변경 페이지
     */
    @RequestMapping(value="/**/profile", method=RequestMethod.GET)
    public String getPrsnDataChngPage(ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO =  COUserDetailsHelperService.getAuthenticatedUser();
            COAAdmDTO lgnCOAAdmDTO = new COAAdmDTO();
            lgnCOAAdmDTO.setDetailsKey( cOUserDetailsDTO.getSeq().toString() );
            modelMap.addAttribute("rtnData", cOAAdmService.selectAdmDtl(lgnCOAAdmDTO));


            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            cdDtlList.add("ADMIN_DEPT_CD");
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

        return "mngwserc/co/coa/COAPrsnDataWrite.admin";
    }

    /**
     * 신고하기 기존 대상자 비밀번호 초기화
     */
    @GetMapping(value = "/co/coa/ethic-list")
    public String setEthicUser(ModelMap modelMap) throws Exception {
        int actCnt = 0;
        try
        {
            //대상자 조회
            actCnt = cOAAdmService.setEthicList();
            modelMap.addAttribute("actCnt", actCnt);
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

    /**
     * 관리자 권한 변경 로그를 가져온다.
     */
    /*@GetMapping(value="/co/coa/log-list.ajax")
    public String getAuthLogList(COAAdmDTO pCOAAdmDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            List<COAAdmDTO> temp =  cOAAdmService.getAuthLogList(pCOAAdmDTO);

            modelMap.addAttribute("rtnList", cOAAdmService.getAuthLogList(pCOAAdmDTO));

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
    }*/



    /**
     * <pre>
     * 로그인/로그아웃을 위한 API Controller
     * </pre>
     *
     * @ClassName		: COLgnRestController.java
     * @Description		: 로그인/로그아웃을 위한 API Controller
     * @author 허진영
     * @since 2020.10.20
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2020.10.20			허진영			 		최초생성
     * </pre>
     */
    @RestController
    @RequestMapping("/mngwserc")
    public class COAAdmRestController  {

        //사이트 명
        @Value("${app.site.name}")
        private String siteName;
        //사이트 웹 소스 URL
        @Value("${app.user-domain}")
        private String httpFrontUrl;
        //사이트 관리자 URL
        @Value("${app.admin-domain}")
        private String httpAdmtUrl;

        /**
         * 관리자를 등록한다.
         */
        @PostMapping(value="/co/coa/insert")
        public COAAdmDTO insertAdm(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            try
            {
                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                pCOAAdmDTO.setRegId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setRegName( cOUserDetailsDTO.getName() );
                pCOAAdmDTO.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
                pCOAAdmDTO.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
                pCOAAdmDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setModId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setModIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setRespCnt( cOAAdmService.insertAdm(pCOAAdmDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pCOAAdmDTO;
        }

        /**
         * 관리자를 수정한다.
         */
        @PostMapping(value="/co/coa/update")
        public COAAdmDTO updateAdm(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            try
            {
                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

                pCOAAdmDTO.setRegId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setRegName( cOUserDetailsDTO.getName() );
                pCOAAdmDTO.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
                pCOAAdmDTO.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
                pCOAAdmDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setModId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setModIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setIsAdmMng("Y");
                pCOAAdmDTO.setRespCnt( cOAAdmService.updateAdm(pCOAAdmDTO) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pCOAAdmDTO;
        }


        /**
         * 관리자를 삭제한다.
         */
        @PostMapping(value="/co/coa/delete")
        public COAAdmDTO deleteAdm(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            try
            {
                COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
                pCOAAdmDTO.setRegId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setModId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setModIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setRespCnt( cOAAdmService.deleteAdm(pCOAAdmDTO) );

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pCOAAdmDTO;
        }



        /**
         * 관리자 메뉴 목록을 조회한다.
         */
        @PostMapping(value="/co/coa/menu-list")
        public String getMenuList(COMenuDTO cOMenuDTO) throws Exception
        {
            JSONArray jSONArray = null;
            try
            {
                cOMenuDTO.setIsChkd("Y");
                cOMenuDTO.setIsAdmUdt("Y");
                List<COMenuDTO> menuList = cOBMenuService.getMenuList(cOMenuDTO);

                int startNum = 0, paramSeq = cOMenuDTO.getMenuSeq();

                jSONArray = cOBMenuService.getJsonData(menuList, startNum, paramSeq);
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return jSONArray.toString();
        }

        /**
         * 관리자 내정보변경을 한다.
         */
        @PostMapping("/**/api-profile-update")
        public COAAdmDTO updatePrsnData(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            try
            {
                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                pCOAAdmDTO.setRegId( cOUserDetailsDTO.getId() );
                pCOAAdmDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
                pCOAAdmDTO.setRespCnt(cOAAdmService.updatePrsnData(pCOAAdmDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pCOAAdmDTO;
        }

        /**
         * 아이디 중복여부를 가져온다.
         */
        @PostMapping(value="/co/coa/id-overlap-check")
        public COAAdmDTO getIdOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            int existCnt = 1;
            try
            {
                existCnt = cOAAdmService.getIdOverlapCheck(pCOAAdmDTO);

                pCOAAdmDTO.setUseYn( ( (existCnt == 0) ? "Y" : "N") );

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pCOAAdmDTO;
        }

        /**
         * 이메일 중복여부를 가져온다.
         */
        @PostMapping(value="/**/email-overlap-check")
        public COAAdmDTO getEmailOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            int existCnt = 1;
            try
            {
                existCnt = cOAAdmService.getEmailOverlapCheck(pCOAAdmDTO);
                pCOAAdmDTO.setUseYn( (existCnt == 0 ? "Y" : "N") );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pCOAAdmDTO;
        }

        /**
         * 비밀번호를 초기화한다.
         */
        @PostMapping(value="/co/coa/pwd-init")
        public COAAdmDTO updatePwdInit(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            int actCnt = 0;
            try
            {
                actCnt = cOAAdmService.updatePwdInit(pCOAAdmDTO);

                if (actCnt > 0)
                {
                    String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
                    //이메일 발송
                    COMailDTO cOMailDTO = new COMailDTO();
                    cOMailDTO.setSubject("["+siteName+"] 임시비밀번호 발급 안내");
                    //수신자 정보
                    COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                    //이메일
                    receiverDto.setEmail(pCOAAdmDTO.getEmail());
                    //이름
                    receiverDto.setName("관리자");
                    //치환문자1
                    receiverDto.setNote1(pCOAAdmDTO.getPwd());
                    //치환문자2
                    receiverDto.setNote2(field2);
                    //수신자 정보 등록
                    cOMailDTO.getReceiver().add(receiverDto);
                    cOMessageService.sendMail(cOMailDTO, "COAAdmPwdInit.html");
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

            pCOAAdmDTO.setRespCd(actCnt+"");
            return pCOAAdmDTO;
        }

        /**
         * 비밀번호 사용가능 여부 확인
         */
        @PostMapping(value="/co/coa/pwd-check")
        public COAAdmDTO getPwdCheck(COAAdmDTO pCOAAdmDTO) throws Exception
        {
            try
            {
                List<String> pwdList = cOAAdmService.getPwdCheck(pCOAAdmDTO);
                pCOAAdmDTO.setUseYn( ( (pwdList != null && pwdList.size() > 0) ? "N" : "Y") );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pCOAAdmDTO;
        }


        @PostMapping(value="/co/coa/searchMemory")
        public HashMap<String, Object> memorySearch(){

            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

            long usedMemory = heapMemoryUsage.getUsed();
            long maxMemory = heapMemoryUsage.getMax();

            //바이트
            long usedMemory2= (usedMemory/1024);//킬로
            long usedMemory3= (usedMemory2/1024);//메가
            long usedMemory4= (usedMemory3/1024);//기가

            long maxMemory2= (maxMemory/1024);//킬로
            long maxMemory3= (maxMemory2/1024);//메가
            long maxMemory4= (maxMemory3/1024);//기가

            double usedPercent = (double) usedMemory3 / maxMemory3 * 100.0;

            //out.println("Heap Memory Usage: Used = " + usedMemory3 + " Mega, Max = " + maxMemory3 + " Mega, Used Percent = " + String.format("%.2f", usedPercent) + "%<br>");
            System.out.println("@@ usedPercent = " + usedPercent);
            // 임계값 설정 (90% 이상)
            if (usedPercent > 90.0) {
                //out.println("<p style='color: red;'>Memory usage is over 90% - Running Garbage Collection</p>");
                //System.gc(); // 강제로 가비지 컬렉션 실행
            }

            HashMap<String, Object> tempMap = new HashMap();

            tempMap.put("nowMemory", usedMemory3);
            tempMap.put("maxMemory", maxMemory3);


            return tempMap;
        }


        @PostMapping(value="/co/coa/clearMemory")
        public HashMap<String, Object> clearMemory(){
            HashMap<String, Object> tempMap = new HashMap();

            System.gc(); // 강제로 가비지 컬렉션 실행


            return tempMap;
        }


    }
}