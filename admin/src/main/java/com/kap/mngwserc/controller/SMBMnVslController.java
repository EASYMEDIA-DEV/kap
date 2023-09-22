package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMBMainVslDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMBMnVslService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/smb/{gubun:pc|mobile}")
public class SMBMnVslController {

    private SMBMnVslService sMBMnVslService;

    /**
     * 메인 비주얼 목록 페이지
     */
    @RequestMapping(value="/list")
    public String getMnVslListPage(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
    {
        try
        {
            sMBMainVslDTO.setDvcCd(gubun);
            sMBMainVslDTO.setLangCd(langCd);

            modelMap.addAttribute("rtnData", sMBMainVslDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslList.admin";
    }

    /**
     * 메인 비주얼 목록 조회
     */
    @RequestMapping(value="/select")
    public String selectMnVslList(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
    {
        try
        {
            sMBMainVslDTO.setDvcCd(gubun);
            sMBMainVslDTO.setLangCd(langCd);

            modelMap.addAttribute("rtnList", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslListAjax";
    }

    /**
     * 메인 비주얼 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getMnVslWritePage(SMBMainVslDTO sMBMainVslDTO, ModelMap modelMap, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
    {
        try
        {
            sMBMainVslDTO.setDvcCd(gubun);
            sMBMainVslDTO.setLangCd(langCd);

            modelMap.addAttribute("rtnData", sMBMnVslService.selectMnVslDtl(sMBMainVslDTO));
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smb/SMBMnVslWrite.admin";
    }


    @RestController
    @RequestMapping(value="/mngwserc/{langCd}/sm/smb/{gubun:pc|mobile}")
    class SMBMnVslRestController {

        /**
         * 메인 비주얼 등록
         */
        @PostMapping(value="/insert")
        public SMBMainVslDTO insertMnVsl(SMBMainVslDTO pSMBMainVslDTO, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
        {
            try
            {
                COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
                pSMBMainVslDTO.setRegId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setRegIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setRegName( coaAdmDTO.getName() );
                pSMBMainVslDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
                pSMBMainVslDTO.setModId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setModIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setDvcCd(gubun);
                pSMBMainVslDTO.setLangCd(langCd);

                pSMBMainVslDTO.setRespCnt( sMBMnVslService.insertMnVsl(pSMBMainVslDTO) );
            }
            catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMBMainVslDTO;
        }

        /**
         * 메인 비주얼 수정
         */
        @PostMapping(value="/update")
        public SMBMainVslDTO updateMnVsl(SMBMainVslDTO pSMBMainVslDTO, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
        {
            try
            {
                COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
                pSMBMainVslDTO.setRegId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setRegIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setRegName( coaAdmDTO.getName() );
                pSMBMainVslDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
                pSMBMainVslDTO.setModId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setModIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setDvcCd(gubun);
                pSMBMainVslDTO.setLangCd(langCd);

                pSMBMainVslDTO.setRespCnt( sMBMnVslService.updateMnVsl(pSMBMainVslDTO) );
            }
            catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMBMainVslDTO;
        }

        /**
         * 메인 비주얼 삭제
         */
        @PostMapping(value="/delete")
        public SMBMainVslDTO deleteMnVsl(SMBMainVslDTO pSMBMainVslDTO, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
        {
            try
            {
                COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
                pSMBMainVslDTO.setRegId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setRegIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setRegName( coaAdmDTO.getName() );
                pSMBMainVslDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
                pSMBMainVslDTO.setModId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setModIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setDvcCd(gubun);
                pSMBMainVslDTO.setLangCd(langCd);

                pSMBMainVslDTO.setRespCnt( sMBMnVslService.deleteMnVsl(pSMBMainVslDTO) );
            }
            catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMBMainVslDTO;
        }

        /**
         * 메인 비주얼 사용 여부 수정
         */
        @PostMapping(value="/use-yn-update")
        public SMBMainVslDTO updateUseYn(SMBMainVslDTO pSMBMainVslDTO, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
        {
            try
            {
                COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
                pSMBMainVslDTO.setRegId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setRegIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setRegName( coaAdmDTO.getName() );
                pSMBMainVslDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
                pSMBMainVslDTO.setModId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setModIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setDvcCd(gubun);
                pSMBMainVslDTO.setLangCd(langCd);

                pSMBMainVslDTO.setRespCnt( sMBMnVslService.updateUseYn(pSMBMainVslDTO) );
            }
            catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMBMainVslDTO;
        }

        /**
         * 메인 비주얼 정렬 수정
         */
        @PostMapping(value="/sort")
        public SMBMainVslDTO updateOrder(SMBMainVslDTO pSMBMainVslDTO, @PathVariable("gubun") String gubun, @PathVariable("langCd") String langCd) throws Exception
        {
            try
            {
                COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
                pSMBMainVslDTO.setRegId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setRegIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setRegName( coaAdmDTO.getName() );
                pSMBMainVslDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
                pSMBMainVslDTO.setModId( coaAdmDTO.getId() );
                pSMBMainVslDTO.setModIp( coaAdmDTO.getLoginIp() );
                pSMBMainVslDTO.setDvcCd(gubun);
                pSMBMainVslDTO.setLangCd(langCd);

                pSMBMainVslDTO.setRespCnt( sMBMnVslService.updateOrder(pSMBMainVslDTO) );
            }
            catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMBMainVslDTO;
        }

    }

}