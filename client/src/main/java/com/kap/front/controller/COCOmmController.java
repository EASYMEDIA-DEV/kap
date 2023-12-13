package com.kap.front.controller;

import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.co.COCNiceMyResDto;
import com.kap.core.dto.co.COCNiceReqEncDto;
import com.kap.core.dto.co.COCNiceServiceDto;
import com.kap.core.dto.co.COCompApiResDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
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

        @Value("${app.user-domain}")
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
    @GetMapping(value = "/nice/my-idnttvrfct-confirm")
    public String idnttvrfctConfirm(HttpServletRequest request , ModelMap modelMap) throws Exception {
        COCNiceMyResDto cocNiceMyResDto = cOCommService.idnttvrfctConfirm(request.getParameter("enc_data"), request);
        modelMap.addAttribute("rtnData",cocNiceMyResDto);

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
     * mobile_no
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