package com.kap.front.controller;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.seed.COBrowserUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import com.kap.core.dto.co.COCNiceMyResDto;
import com.kap.core.dto.co.COCNiceReqEncDto;
import com.kap.core.dto.co.COCNiceServiceDto;
import com.kap.core.dto.co.COCompApiResDto;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre> 
 * 공통 Service Controller
 * </pre>
 *
 * @Modification Information
 * <pre>
 * 		since			author				  descripti
 *    ==========    ==============    =============================
 *    2023.11.15		이옥정				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/")
public class COCOmmController {
    /** 공통 서비스 **/
    private final COCommService cOCommService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /** 교육 서비스 **/
    private final EBBEpisdService eBBEpisdService;
    /** 공지사항 서비스 **/
    private final BDANoticeService bDANoticeService;
    /** 재단소식 서비스 **/
    private final BDBCompanyNewsService bDBCompanyNewsService;
    /** 뉴스레터 서비스 **/
    private final BDDNewsletterService bDDNewsletterService;
    /**
     * 교육 차수 QR 이미지 다운로드
     */
    @Operation(summary = "교육 차수 QR 이미지 다운로드.", tags = "", description = "")
    @GetMapping(value="/episd/qr-image-check")
    public void getQrImageDownload(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        //QR 이미지 링크
        if("Y".equals(RequestContextHolder.getRequestAttributes().getAttribute("episdCheck", RequestAttributes.SCOPE_SESSION))){
            //QR 이미지 타고 들어옴
            //로직 처리

        }
        RequestContextHolder.getRequestAttributes().setAttribute("episdCheck", "Y", RequestAttributes.SCOPE_SESSION);
        response.sendRedirect("/my-page/edu-apply/qrDetail?detailsKey="+eBBEpisdDTO.getDetailsKey()+"&episdYear="+eBBEpisdDTO.getEpisdYear()+"&episdOrd=" + eBBEpisdDTO.getEpisdOrd());
    }

    /**
     * 통합 검색
     */
    @Operation(summary = "통합 검색", tags = "", description = "")
    @GetMapping(value={"/search", "/search/{menuType:menu|education|notice|foundation|newsletter}"})
    public String getTotalSearc(@Valid COSearchDTO cOSearchDTO, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request, @PathVariable(required = false) String menuType) throws Exception
    {
        int menuAddCnt = 0;
        //메뉴는 인터셉터 조회
        String[] titleList = new String[3];
        List<COMenuDTO> qMenuList = new ArrayList<COMenuDTO>();
        List<COMenuDTO> menuList = (List)RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION);
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            if(menuList.get(i).getDpth()-3 == 0){
                titleList = new String[3];
            }
            titleList[menuList.get(i).getDpth()-3] = menuList.get(i).getMenuNm();
            if(menuList.get(i).getMenuNm().indexOf(cOSearchDTO.getQ()) > -1
                    && !"".equals(COStringUtil.nullConvert(menuList.get(i).getUserUrl()))){
                COMenuDTO  cOMenuDTO = new COMenuDTO();
                cOMenuDTO.setUserUrl( menuList.get(i).getUserUrl() );
                String menuNm = "";
                for(String val : titleList){
                    if(val != null){
                        if(!"".equals(menuNm)){
                            menuNm += "__" +  val;
                        }else{
                            menuNm += val;
                        }
                    }
                }
                cOMenuDTO.setMenuNm( menuNm );
                qMenuList.add( cOMenuDTO );
            }
        }
        modelMap.put("menuCnt", qMenuList.size());
        modelMap.put("menuAddCnt", menuAddCnt);
        modelMap.put("qMenuList", qMenuList);
        modelMap.put("menuAddPage", 1);
        //교육
        EBBEpisdDTO eBBEpisdDTO = new EBBEpisdDTO();
        eBBEpisdDTO.setQ( cOSearchDTO.getQ() );
        eBBEpisdDTO.setSiteGubun("front");
        eBBEpisdDTO.setApplyListYn("Y");
        int episdCnt = eBBEpisdService.selectEpisdListCnt( eBBEpisdDTO );
        modelMap.put("episdCnt", episdCnt);
        //공지사항
        BDANoticeDTO pBDANoticeDTO = new BDANoticeDTO();
        pBDANoticeDTO.setQ( cOSearchDTO.getQ() );
        pBDANoticeDTO.setMainYn("Y");
        pBDANoticeDTO.setSiteGubun("front");
        int noticeCnt = bDANoticeService.selectNoticeListCnt(pBDANoticeDTO);
        modelMap.put("noticeCnt", noticeCnt);
        //재단소식
        BDBCompanyNewsDTO pBDBCompanyNewsDTO = new BDBCompanyNewsDTO();
        pBDBCompanyNewsDTO.setQ( cOSearchDTO.getQ() );
        pBDBCompanyNewsDTO.setMainYn("Y");
        pBDBCompanyNewsDTO.setSiteGubun("front");
        int newsCnt = bDBCompanyNewsService.selectCompanyNewsListCnt(pBDBCompanyNewsDTO);
        modelMap.put("newsCnt", newsCnt);
        //뉴스레터
        BDDNewsletterDTO pBDDNewsletterDTO = new BDDNewsletterDTO();
        pBDDNewsletterDTO.setQ( cOSearchDTO.getQ() );
        int letterCnt = bDDNewsletterService.selectNewsletterListCnt(pBDDNewsletterDTO);
        modelMap.put("letterCnt", letterCnt);

        modelMap.put("q", cOSearchDTO.getQ());
        modelMap.put("menuType", menuType);
        String rtnUrl = "/front/co/COSearch.front";
        if(menuType != null){
            BaseDTO baseDTO = new BaseDTO();
            modelMap.put("rtnData", baseDTO);
            rtnUrl = "/front/co/COSearchDtl.front";
        }
        return rtnUrl;
    }


    /**
     * 통합 검색 메뉴 탭 더보기 버튼
     */
    @Operation(summary = "메뉴 탭 더보기", tags = "", description = "")
    @GetMapping(value={"/search/{menuType:menu}/tab"})
    public String getTotalSearcMenuAdd(@Valid COSearchDTO cOSearchDTO, @RequestParam("menuAddCnt") int menuAddCnt, @RequestParam("menuFirstIndex") int menuFirstIndex, @RequestParam("menuAddPage") int menuAddPage, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request, @PathVariable(required = false) String menuType) throws Exception
    {
        //메뉴는 인터셉터 조회
        String[] titleList = new String[3];
        List<COMenuDTO> qMenuList = new ArrayList<COMenuDTO>();
        List<COMenuDTO> tabMenuList = new ArrayList<COMenuDTO>();
        List<COMenuDTO> menuList = (List)RequestContextHolder.getRequestAttributes().getAttribute("menuList", RequestAttributes.SCOPE_SESSION);
        for (int i = 0, size = menuList.size(); i < size; i++)
        {
            if(menuList.get(i).getDpth()-3 == 0){
                titleList = new String[3];
            }
            titleList[menuList.get(i).getDpth()-3] = menuList.get(i).getMenuNm();
            if(menuList.get(i).getMenuNm().indexOf(cOSearchDTO.getQ()) > -1 && !"".equals(COStringUtil.nullConvert(menuList.get(i).getUserUrl()))){
                COMenuDTO  cOMenuDTO = new COMenuDTO();
                cOMenuDTO.setUserUrl( menuList.get(i).getUserUrl() );
                String menuNm = "";
                for(String val : titleList){
                    if(val != null){
                        if(!"".equals(menuNm)){
                            menuNm += "__" +  val;
                        }else{
                            menuNm += val;
                        }
                    }
                }
                cOMenuDTO.setMenuNm( menuNm );
                qMenuList.add( cOMenuDTO );
            }
        }
        if(menuAddCnt > 0 && qMenuList.size() > 0){
            for(int i = menuFirstIndex; i < menuAddCnt; i++) {
                tabMenuList.add(qMenuList.get(i));
            }
            modelMap.put("tabMenuList", tabMenuList);
        }
        modelMap.put("menuCnt", qMenuList.size());
        modelMap.put("menuAddPage", menuAddPage);


        return "/front/co/COSearchDtlMenuAjax";
    }

    /**
     * @ClassName		: EXGExamRestController.java
     * @Description		: 교육사업관리 > 시험관리 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.11.10			김학규			 		최초생성
     * </pre>
     */
    @Tag(name = "공통 API", description = "공통")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/")
    public class EXGExamRestController  {
        /** 공통 서비스 **/
        private final COCommService cOCommService;
        /** 코드 서비스 **/
        private final COCodeService cOCodeService;

        @Value("${nice.return-url-user}")
        private String url;

        @Operation(summary = "신청자, 부품사 정보 조회", tags = "회원", description = "신청자, 부품사 정보 조회")
        @GetMapping(value="/**/get-member-company-info")
        public COUserCmpnDto getMemCmpnDtl(ModelMap modelMap, @Parameter(description = "회원 순번", required = true) @RequestParam(required = true) Integer memSeq) throws Exception
        {
            COUserCmpnDto cOUserCmpnDto = null;
            try
            {
                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                // 코드 set
                cdDtlList.add("MEM_CD");
                cdDtlList.add("COMPANY_TYPE");
                cdDtlList.add("CO_YEAR_CD");
                // 정의된 코드id값들의 상세 코드 맵 반환
                cOUserCmpnDto = cOCommService.getMemCmpnDtl(memSeq);
                cOUserCmpnDto.setCdDtlList( cOCodeService.getCmmCodeBindAll(cdDtlList) );
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return cOUserCmpnDto;
        }

        /**
         * 나이스 회사 체크
         * @param coCompApiResDto
         * @return
         * @throws Exception
         */
        @PostMapping("/nice/comp-chk")
        @ResponseBody
        public COCompApiResDto niceChk(COCompApiResDto coCompApiResDto) throws Exception {
            return cOCommService.niceChk(coCompApiResDto.getCompNum());
        }

        /**
         * 나이스 본인 인증 TODO 배포 시 returlUrl 변경 하기
         * @param request
         * @param cocNiceReqEncDto
         * @return
         * @throws Exception
         */
        @PostMapping("/nice/my-idnttvrfct")
        @ResponseBody
        public COCNiceServiceDto idnttvrfct(HttpServletRequest request , COCNiceReqEncDto cocNiceReqEncDto) throws Exception {
            return cOCommService.idnttvrfct(request , cocNiceReqEncDto,url+"/nice/my-idnttvrfct-confirm");
        }


    }

    /**
     * 나이스 본인 인증 후 팝업
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nice/my-idnttvrfct-confirm")
    public String idnttvrfctConfirm(HttpServletRequest request , ModelMap modelMap , HttpSession session) throws Exception {
        COCNiceMyResDto cocNiceMyResDto = cOCommService.idnttvrfctConfirm(request.getParameter("enc_data"), request);
        modelMap.addAttribute("rtnData",cocNiceMyResDto);

        log.info("*************************************");
        log.info("ci:::::"+cocNiceMyResDto.toString());
        log.info("ci:::::"+cocNiceMyResDto.getMobileno());
        log.info("*************************************");

        session.setAttribute("niceSession", cocNiceMyResDto);
        session.setMaxInactiveInterval(30*60); //TODO 양현우 506 시간 재 설정 하기
//        session.setMaxInactiveInterval(20); //TODO 양현우 506 시간 재 설정 하기

        if(cocNiceMyResDto.getReceivedatass().getRedirectUrl().equals("no")) {
            return "/front/co/CONiceUserPopUp";
        } else {
            return  "/front/co/CONicePopUp";
        }
    }

    /**
     * 나이스 본인 인증 테스트  페이지 없다고 나옴 값만 확인!
     * name
     * birthdate
     * gender
     * nationalinfo
     * mobileno
     * ci
     * 사용하고하지는 dto에 변수 추가 하기
     * @param request
     * @param cocNiceMyResDto
     * @throws Exception
     */
    @RequestMapping(value = "/nice/test")
    public void testNice(HttpServletRequest request , COCNiceMyResDto cocNiceMyResDto) throws Exception {
        log.info("호출 되었음");
        log.info("parameters :::: {}", cocNiceMyResDto);

    }
}