package com.kap.front.controller.consult;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.CBBManageConsultService;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 메인 페이지
 * </pre>
 * @ClassName		: CONsultingController.java
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
@RequestMapping(value="/consulting/{type:tech|Manage}")
public class CONsultingController {
    /** 코드 서비스 **/

    /** 서비스 **/
    private final CBATechGuidanceService cBATechGuidanceService;
    private final CBBManageConsultService cBBManageConsultService;
    private final MPAUserService mPAUserService;

    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;

    @GetMapping("/index")
    public String getConsultIndexPage(ModelMap modelMap, HttpServletRequest request) throws Exception {

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

        return "front/consult/CONsultingIndex.front";
    }
    @GetMapping("/application")
    public String getConsultApplicationPage(ModelMap modelMap, HttpServletRequest request) throws Exception {

        return "front/consult/CONsultingApplication.front";
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="consulting/{type:tech|Manage}")
    public class CONsultingRestController {

        private final COCodeService cOCodeService;
        private final MPAUserService mPAUserService;

        /**
         * 멤버 키로 상세 정보 검색
         */
        @PostMapping(value = "/selectDtlInfo")
        @ResponseBody
        public MPAUserDto selectDtlInfo(@Valid @RequestBody MPAUserDto mpaUserDto) throws Exception {

            return mPAUserService.selectUserDtlTab(mpaUserDto);
        }
    }

    /**
     * 멤버 키로 상세 정보 검색
     */
    @PostMapping(value = "/checkMember")
    @ResponseBody
    public MPAUserDto checkMemberInfo(@Valid @RequestBody MPAUserDto mpaUserDto) throws Exception {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        System.err.println("cOUserDetailsDTO:::"+cOUserDetailsDTO);
        return mPAUserService.selectUserDtlTab(mpaUserDto);
    }

}
