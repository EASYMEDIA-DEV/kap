package com.kap.mngwserc.controller;

import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.MPPwdInitDto;
import com.kap.core.dto.TaxInfoResDto;
import com.kap.service.COMailService;
import com.kap.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/comm")
public class CommonController {

    private final CommonService commonService;


    @Value("${app.site.name}")
    private String siteName;
    //사이트 웹 소스 URL
    @Value("${app.user-domain}")
    private String httpFrontUrl;
    //사이트 관리자 URL
    @Value("${app.admin-domain}")
    private String httpAdmtUrl;

    //이메일 발송
    private final COMailService cOMailService;



    /**
     * 비밀번호를 초기화한다.
     */
    @PostMapping(value="/pwd-init")
    public String updatePwdInit(@RequestParam("id") String id) throws Exception
    {

        MPPwdInitDto mpPwdInitDto = new MPPwdInitDto();
        int actCnt = 0;
        try
        {

            mpPwdInitDto.setId(id);
            actCnt = commonService.updatePwdInit(mpPwdInitDto);



            if (actCnt > 0)
            {
                //이메일 발송
                COMailDTO cOMailDTO = new COMailDTO();
                cOMailDTO.setSubject("["+siteName+"] 임시비밀번호 발급 안내");
                cOMailDTO.setSiteName(siteName);
                cOMailDTO.setHttpFrontUrl(httpFrontUrl);
                cOMailDTO.setHttpAdmUrl(httpAdmtUrl);
                cOMailDTO.setEmails(mpPwdInitDto.getEmail());
                cOMailDTO.setField1(mpPwdInitDto.getPwd());
                //인증요청일시
                String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
                cOMailDTO.setField2(field2);
                if(mpPwdInitDto.getMemCd().equals("CO")) {
                        cOMailDTO.setField3("일반 사용자");
                } else if(mpPwdInitDto.getMemCd().equals("CP")) {
                    cOMailDTO.setField3("부품 사회원");
                }
                cOMailService.sendMail(cOMailDTO, "UserPwdInit.html");
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
        return "jsonView";

    }


}