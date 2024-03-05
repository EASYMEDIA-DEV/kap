package com.kap.mngwserc.controller.um;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.UMAFtpUploadDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.UMAFtpUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * FTP 업로드 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: UMAFtpUploadController.java
 * @Description		: FTP 업로드 관리를 위한 Controller
 * @author 임서화
 * @since 2023.10.12
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.12		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/um/uma")
public class UMAFtpUploadController {

    //FTP 업로드 서비스
    private final UMAFtpUploadService uMAFtpUploadService;

    //사용자 http 경로
    @Value("${app.user-domain}")
    private String appUserDomain;

    /**
     * FTP 업로드 목록 페이지
     */
    @GetMapping(value="/list")
    public String getFtpListPage(UMAFtpUploadDTO uMAFtpUploadDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", uMAFtpUploadDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/um/uma/UMAFtpUploadList.admin";
    }

    /**
     * FTP 업로드 목록 조회
     */
    @PostMapping(value = "/select")
    public String selectBoardListPageAjax(UMAFtpUploadDTO uMAFtpUploadDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("rtnData", uMAFtpUploadService.selectUploadFileList(uMAFtpUploadDTO));
            modelMap.addAttribute("appUserDomain", appUserDomain);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/um/uma/UMAFtpUploadListAjax";
    }


    /**
     * FTP 업로드 등록 페이지
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String boardInsertPage(UMAFtpUploadDTO uMAFtpUploadDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();

            uMAFtpUploadDTO.setRegId(cOUserDetailsDTO.getId());
            uMAFtpUploadDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            modelMap.addAttribute("respCnt", uMAFtpUploadService.insertUploadFile(uMAFtpUploadDTO));
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
    }


    /**
     * FTP 업로드 글을 삭제한다.
     */
    @PostMapping(value="/delete")
    public String deleteBoard(UMAFtpUploadDTO uMAFtpUploadDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", uMAFtpUploadService.deleteUploadFile(uMAFtpUploadDTO));
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
    }

}