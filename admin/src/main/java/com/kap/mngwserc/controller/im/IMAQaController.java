package com.kap.mngwserc.controller.im;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.core.dto.im.ima.IMAQaPicDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * <pre>
 * 1:1 문의 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: IMAQaController.java
 * @Description		: 1:1 문의 관리를 위한 Controller
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.01		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/im/ima")
public class IMAQaController {

    /** 1:1 문의 서비스 **/
    private final IMAQaService iMAQaService;

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /**
     * 1:1 문의 목록 페이지
     */
    @GetMapping(value="/list")
    public String getQaListPage(IMAQaDTO iMAQaDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", iMAQaDTO);
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

        return "mngwserc/im/ima/IMAQaList.admin";
    }

    /**
     * 1:1 문의 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectQaListPageAjax(IMAQaDTO iMAQaDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", iMAQaService.selectQaList(iMAQaDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/im/ima/IMAQaListAjax";
    }

    /**
     * 1:1 문의 담당자 목록 조회
     */
    @GetMapping(value = "/select-pic")
    public String selectQaPicLayerPageAjax(IMAQaPicDTO pIMAQaPicDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", iMAQaService.selectQaPicList(pIMAQaPicDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/im/ima/IMAQaPicLayerAjax";
    }

    /**
     * 1:1 문의 상세 페이지
     */
    @GetMapping(value="/write")
    public String getQaWritePage(IMAQaDTO iMAQaDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO= COUserDetailsHelperService.getAuthenticatedUser();

            iMAQaDTO.setRegId(cOUserDetailsDTO.getId());

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(iMAQaDTO.getDetailsKey())){
                modelMap.addAttribute("rtnQaInfo", iMAQaService.selectQaDtl(iMAQaDTO));
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

        return "mngwserc/im/ima/IMAQaWrite.admin";
    }

    /**
     * @ClassName		: IMAQaRestController.java
     * @Description		: 1:1 문의 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.01
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.01		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/im/ima")
    public class IMAQaRestController {

        /** 1:1 문의 서비스 **/
        private final IMAQaService iMAQaService;

        /**
         * 1:1 문의 답변 등록
         */
        @PostMapping(value="/insert")
        public IMAQaDTO qaInsertPage(@Valid IMAQaDTO iMAQaDTO) throws Exception
        {
            try
            {
                iMAQaDTO.setRespCnt(iMAQaService.insertQa(iMAQaDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return iMAQaDTO;
        }

        /**
         * 1:1 문의 담당자 등록
         */
        @PostMapping(value="/insert-pic")
        public IMAQaPicDTO qaPicInsert(@Valid IMAQaPicDTO pIMAQaPicDTO) throws Exception
        {
            try
            {
                pIMAQaPicDTO.setRespCnt(iMAQaService.insertQaPic(pIMAQaPicDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pIMAQaPicDTO;
        }

        /**
         * 1:1 문의 담당자 수정
         */
        @PostMapping(value="/update-pic")
        public IMAQaPicDTO qaPicUpdate(@Valid IMAQaPicDTO pIMAQaPicDTO) throws Exception
        {
            try
            {
                if(pIMAQaPicDTO.getDetailsKey() != null && !pIMAQaPicDTO.getDetailsKey().isEmpty()) {
                    pIMAQaPicDTO.setRespCnt(iMAQaService.updateQaPic(pIMAQaPicDTO));
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
            return pIMAQaPicDTO;
        }

        /**
         * 1:1 문의 담당자 삭제
         */
        @PostMapping(value="/delete-pic")
        public IMAQaPicDTO qaPicDelete(@Valid IMAQaPicDTO pIMAQaPicDTO) throws Exception
        {
            try
            {
                if(pIMAQaPicDTO.getPicSeq() != null && pIMAQaPicDTO.getPicSeq() > -1) {
                    pIMAQaPicDTO.setRespCnt(iMAQaService.deleteQaPic(pIMAQaPicDTO));
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
            return pIMAQaPicDTO;
        }

        /**
         * 1:1 문의 유형별 담당자 등록 개수 조회
         */
        @PostMapping(value="/pic-cnt-check")
        public IMAQaPicDTO getQaPicCnt(IMAQaPicDTO pIMAQaPicDTO) throws Exception
        {
            try
            {
                pIMAQaPicDTO.setRespCnt(iMAQaService.getQaPicCnt(pIMAQaPicDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pIMAQaPicDTO;
        }

        /**
         * 1:1 문의 담당자 상세 조회
         */
        @PostMapping(value="/write-pic")
        public IMAQaPicDTO getQaPicWrite(IMAQaPicDTO pIMAQaPicDTO) throws Exception
        {
            IMAQaPicDTO rtnDto = new IMAQaPicDTO();

            try
            {
                if(pIMAQaPicDTO.getPicSeq() != null && pIMAQaPicDTO.getPicSeq() > -1) {
                    rtnDto = iMAQaService.selectQaPicDtl(pIMAQaPicDTO);
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
            return rtnDto;
        }

    }

}