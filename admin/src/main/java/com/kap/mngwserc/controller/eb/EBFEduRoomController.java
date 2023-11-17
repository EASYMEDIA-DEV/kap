package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomSearchDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomWriteDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBFEduRoomService;
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
 * 교육장 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBFEduRoomController.java
 * @Description		: 교육장 관리를 위한 Controller
 * @author 장두석
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebf")
public class EBFEduRoomController {

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /** 교육장 서비스 **/
    private final EBFEduRoomService eBFEduRoomService;


    /**
     *  교육장 관리 목록 페이지
     */
    @GetMapping(value="/list")
    public String getEduRoomListPage(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO, ModelMap modelMap) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("ED_CITY_CODE");

        // 정의된 코드id값들의 상세 코드 맵 반환
        modelMap.addAttribute("rtnData", pEBFEduRoomSearchDTO);
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        return "mngwserc/eb/ebf/EBFEduRoomList.admin";
    }

    /**
     * 교육장 관리 목록 조회
     */
    @RequestMapping(value = "/select")
    public String selectEduRoomList(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBFEduRoomService.selectEduRoomList(pEBFEduRoomSearchDTO));
            modelMap.addAttribute("eduRoomDto", pEBFEduRoomSearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebf/EBFEduRoomListAjax";
    }

    /**
     * 교육장 등록 및 상세/수정 페이지
     */
    @RequestMapping(value="/write")
    public String getEduRoomWritePage(EBFEduRoomDetailDTO pEBFEduRoomDetailDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통 코드 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("ED_CITY_CODE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(pEBFEduRoomDetailDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", eBFEduRoomService.selectEduRoomDtl(pEBFEduRoomDetailDTO));
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

        return "mngwserc/eb/ebf/EBFEduRoomWrite.admin";
    }

    /**
     * 교육장 검색 레이어 지역구분 조회
     */
    @GetMapping(value = "/codeSelect")
    public String selectPartsComSelectAjax(EBFEduRoomDetailDTO pEBFEduRoomDetailDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("ED_CITY_CODE");
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
        return "mngwserc/eb/ebf/EBFEduRoomSelectAjax";
    }

    /**
     * @ClassName		: EBFEduRoomRestController.java
     * @Description		: 교육장 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.15
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.15		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/eb/ebf")
    public class EBFEduRoomRestController {
        /**
         * 교육장 등록
         */
        @PostMapping(value = "/insert")
        public EBFEduRoomWriteDTO insertEduRoom(@Valid EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception {
            try
            {
                pEBFEduRoomWriteDTO.setRespCnt(eBFEduRoomService.insertEduRoom(pEBFEduRoomWriteDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pEBFEduRoomWriteDTO;
        }

        /**
         * 교육장 수정
         */
        @PostMapping(value="/update")
        public EBFEduRoomWriteDTO updateEduRoom(@Valid EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception
        {
            try
            {
                pEBFEduRoomWriteDTO.setRespCnt(eBFEduRoomService.updateEduRoom(pEBFEduRoomWriteDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pEBFEduRoomWriteDTO;
        }

            /**
             * 교육장 삭제
             */
            @PostMapping(value="/delete")
            public EBFEduRoomSearchDTO deleteEduRoom(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception
            {
                try
                {
                    pEBFEduRoomSearchDTO.setRespCnt(eBFEduRoomService.deleteEduRoom(pEBFEduRoomSearchDTO));
                }
                catch (Exception e)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug(e.getMessage());
                    }
                    throw new Exception(e.getMessage());
                }

                return pEBFEduRoomSearchDTO;
            }
    }

}

