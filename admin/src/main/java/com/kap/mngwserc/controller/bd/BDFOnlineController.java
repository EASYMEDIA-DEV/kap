package com.kap.mngwserc.controller.bd;

import com.kap.core.dto.bd.bdf.BDFOnlineDTO;
import com.kap.service.BDFOnlineService;
import com.kap.service.COCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * <pre>
 * 온라인 메뉴얼 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDFOnlineController.java
 * @Description		: 온라인 메뉴얼 관리를 위한 Controller
 * @author 오병호
 * @since 2024.02.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.02.20		오병호				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/bd/bdf")
public class BDFOnlineController {

    //Online 서비스
    private final BDFOnlineService bDFOnlineService;
    //코드 서비스
    private final COCodeService cOCodeService;

    /**
     * Online 목록 페이지
     */
    @GetMapping(value="/list")
    public String getOnlineListPage(BDFOnlineDTO bDFOnlineDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 코드 set
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("BOARD_TYPE_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("rtnData", bDFOnlineDTO);
            System.err.println("bDFOnlineDTO   :::  " + bDFOnlineDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/bd/bdf/BDFOnlineList.admin";
    }

    /**
     * Online 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectOnlineListPageAjax(BDFOnlineDTO bDFOnlineDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDFOnlineService.selectOnlineList(bDFOnlineDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/bd/bdf/BDFOnlineListAjax";
    }

    /**
     * Online 상세 페이지
     */
    @GetMapping(value="/write")
    public String getOnlineWritePage(BDFOnlineDTO bDFOnlineDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 코드 set
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("BOARD_TYPE_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(bDFOnlineDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDFOnlineService.selectOnlineDtl(bDFOnlineDTO));
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

        return "mngwserc/bd/bdf/BDFOnlineWrite.admin";
    }

    /**
     * @ClassName		: BDFOnlineRestController.java
     * @Description		: Online 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.21
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.21		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/bd/bdf")
    public class BDFOnlineRestController
    {
        /**
         * Online 등록
         */
        @PostMapping(value="/insert")
        public BDFOnlineDTO insertOnline(@Valid BDFOnlineDTO bDFOnlineDTO) throws Exception
        {
            try
            {
                bDFOnlineDTO.setRespCnt(bDFOnlineService.insertOnline(bDFOnlineDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return bDFOnlineDTO;
        }

        /**
         * Online 글을 수정한다.
         */
        @PostMapping(value="/update")
        public BDFOnlineDTO updateOnline(@Valid BDFOnlineDTO bDFOnlineDTO) throws Exception
        {
            try
            {
                bDFOnlineDTO.setRespCnt(bDFOnlineService.updateOnline(bDFOnlineDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return bDFOnlineDTO;
        }

        /**
         * Online 글을 삭제한다.
         */
        @PostMapping(value="/delete")
        public BDFOnlineDTO deleteOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception
        {
            try
            {
                bDFOnlineDTO.setRespCnt(bDFOnlineService.deleteOnline(bDFOnlineDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return bDFOnlineDTO;
        }
    }

}