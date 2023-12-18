package com.kap.mngwserc.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.*;
import com.kap.service.COFileService;
import com.kap.service.COMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <pre> 
 * 샘플 Controller
 * </pre>
 *
 * @ClassName		: COCAdmController.java
 * @Description		: 관리자 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/send")
public class COASmpleMessageController {

    /** 메일 서비스 **/
    private final COMessageService cOMessageService;
    /** 파일 서비스 **/
    private final COFileService cOFileService;

    /**
     * <pre>
     * 샘플 API Controller
     * </pre>
     *
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2020.10.20			허진영			 		최초생성
     * </pre>
     */
    @RestController
    @RequestMapping(value="/send")
    public class COASmpleMessageRestController {
        /**
         * 샘플 이메일 발송
         */
        @GetMapping(value="/email")
        public HashMap<String, Object> setEmailSend(ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
        {
            //메일 발송
            COMailDTO mailForm = new COMailDTO();
            mailForm.setSubject("[ KAP ] 문의에 대한 답변 드립니다");
            //메일 발송할 첨부파일 처리(샘플)
            List<COFileDTO> uploadFiles = cOFileService.getFileInfs(952);
            if (uploadFiles != null && uploadFiles.size() > 0) {
                mailForm.setFileList(uploadFiles);
            }
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail("dldhrwjd@easymedia.net ");
            //이름(비필수)
            receiverDto.setName("이옥정1");
            //치환문자1
            receiverDto.setNote1("치환문자1");
            //치환문자2
            receiverDto.setNote2("치환문자2");
            //치환문자3
            receiverDto.setNote3("치환문자3");
            //치환문자4
            receiverDto.setNote4("치환문자4");
            //치환문자5
            receiverDto.setNote5("치환문자5");
            //수신자 정보 등록
            mailForm.getReceiver().add(receiverDto);

            //수신자 정보
            COMessageReceiverDTO receiverDto2 = new COMessageReceiverDTO();
            //이메일
            receiverDto2.setEmail("dldhrwjd@easymedia.net ");
            //이름(비필수)
            receiverDto2.setName("이옥정2");
            //치환문자1
            receiverDto2.setNote1("치환문자1");
            //치환문자2
            receiverDto2.setNote2("치환문자2");
            //치환문자3
            receiverDto2.setNote3("치환문자3");
            //치환문자4
            receiverDto2.setNote4("치환문자4");
            //치환문자5
            receiverDto2.setNote5("치환문자5");
            //수신자 정보 등록
            mailForm.getReceiver().add(receiverDto2);
            return cOMessageService.sendMail(mailForm, "IMAQaRply.html");
        }

        /**
         * 샘플 SMS 발송
         */
        @GetMapping(value="/sms")
        public HashMap<String, Object> setSmsSend(ModelMap modelMap, HttpServletRequest request, @MapData EmfMap emfMap) throws Exception
        {
            //메일 발송
            COSmsDTO smsDto = new COSmsDTO();
            smsDto.setTitle("[ KAP ] 문의에 대한 답변 드립니다");
            smsDto.setMessage("[$NAME]님 알림 문자 입니다. 전화번호 : [$MOBILE] 비고1 : [$NOTE1] 비고2 : [$NOTE2] 비고3 : [$NOTE3] 비고4 : [$NOTE4] 비고5 : [$NOTE5]");
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //전화번호
            receiverDto.setMobile("01077037810");
            //이름(비필수)
            receiverDto.setName("김학규1");
            //치환문자1
            receiverDto.setNote1("치환문자1");
            //치환문자2
            receiverDto.setNote2("치환문자2");
            //치환문자3
            receiverDto.setNote3("치환문자3");
            //치환문자4
            receiverDto.setNote4("치환문자4");
            //치환문자5
            receiverDto.setNote5("치환문자5");
            smsDto.getReceiver().add(receiverDto);
            //수신자 정보
            COMessageReceiverDTO receiverDto2= new COMessageReceiverDTO();
            //전화번호
            receiverDto2.setMobile("01033746727");
            //이름(비필수)
            receiverDto2.setName("이옥정2");
            //치환문자1
            receiverDto2.setNote1("치환문자1");
            //치환문자2
            receiverDto2.setNote2("치환문자2");
            //치환문자3
            receiverDto2.setNote3("치환문자3");
            //치환문자4
            receiverDto2.setNote4("치환문자4");
            //치환문자5
            receiverDto2.setNote5("치환문자5");
            //수신자 정보 등록
            smsDto.getReceiver().add(receiverDto2);
            //수신자 정보
            COMessageReceiverDTO receiverDto3= new COMessageReceiverDTO();
            //전화번호
            receiverDto3.setMobile("01074471149");
            //이름(비필수)
            receiverDto3.setName("장두석");
            //치환문자1
            receiverDto3.setNote1("치환문자1");
            //치환문자2
            receiverDto3.setNote2("치환문자2");
            //치환문자3
            receiverDto3.setNote3("치환문자3");
            //치환문자4
            receiverDto3.setNote4("치환문자4");
            //치환문자5
            receiverDto3.setNote5("치환문자5");
            //수신자 정보 등록
            smsDto.getReceiver().add(receiverDto3);

            //return cOMessageService.sendSms(smsDto, "SMSSample.txt");
            return cOMessageService.sendSms(smsDto, "");
        }
    }
}