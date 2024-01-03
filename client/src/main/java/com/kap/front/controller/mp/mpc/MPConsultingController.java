package com.kap.front.controller.mp.mpc;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.CBBManageConsultService;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 홈 > 마이페이지 > SQ평가원 자격증 관리 Controller
 * </pre>
 *
 * @ClassName		: MPConsultingController.java
 * @Description		: SQ평가원 자격증 관리 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.29		임서화				   최초 생성
 * </pre>
 */
@Tag(name = "컨설팅 사업 신청내역", description = "컨설팅 사업 신청내역")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/consulting")
public class MPConsultingController {
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /** 서비스 **/
    private final CBATechGuidanceService cBATechGuidanceService;
    private final CBBManageConsultService cBBManageConsultService;
    private final CBATechGuidanceMapper cBATechGuidanceMapper;

    /**
     *  목록
     */
    @GetMapping(value="/list")
    public String getConsultingList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));
            modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "front/mp/mpc/MPConsultingList.front";
    }

    /**
     *  컨설팅 신청 상세 조회
     */
    @GetMapping(value="/detail")
    public String getConsultingDtl(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {*/
            String appctnTypeCd = cBATechGuidanceInsertDTO.getAppctnTypeCd();
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO));
            /* COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));
            modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());*/
        /*}
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }*/
        return "front/mp/mpc/MPConsultingDtl.front";
    }

    /**
     *  컨설팅 신청 상세 조회
     */
    @GetMapping(value="/surveyIndex")
    public String getConsultingSurveyIndex(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {*/
            modelMap.addAttribute("rtnData", cBATechGuidanceService.selectTechGuidanceDtl(cBATechGuidanceInsertDTO));
            System.err.println("bsnYear::::"+cBATechGuidanceInsertDTO);
           /* COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            cBATechGuidanceInsertDTO.setMemSeq(String.valueOf(cOUserDetailsDTO.getSeq()));
            modelMap.addAttribute("rtnData", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO));
            modelMap.addAttribute("totalCnt", cBATechGuidanceMapper.selectMemSeqAppctnMst(cBATechGuidanceInsertDTO).size());*/
        /*}
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }*/
        return "front/mp/mpc/MPConsultingSurveyIndex.front";
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/my-page/consulting")
    public class MPConsultingRestController {

        private final CBATechGuidanceMapper cBATechGuidanceMapper;
        /**
         *  지역 정보로 서브 지역 정보 찾기
         */
        @PostMapping(value = "/appctnType")
        @ResponseBody
        public List<CBATechGuidanceInsertDTO> selectAppctnType(@RequestBody CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception {
            List<CBATechGuidanceInsertDTO> detailList = new ArrayList<>();
            String temp = "";
            try {
                int cnstgSeq = cBATechGuidanceInsertDTO.getCnstgSeq();
                detailList = cBATechGuidanceMapper.selectCnstgAppctnType(cnstgSeq);

                for(int i = 0; i<detailList.size(); i++){
                    temp += "/"+detailList.get(i).getAppctnTypeNm();
                }
                detailList.get(0).setAppctnTypeCd(temp);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return detailList;
        }
    }
}

