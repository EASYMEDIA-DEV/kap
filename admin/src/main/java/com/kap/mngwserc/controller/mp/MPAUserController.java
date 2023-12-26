package com.kap.mngwserc.controller.mp;

import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAAttctnDto;
import com.kap.core.dto.mp.mpa.MPAInqrDto;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.service.COMessageService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <pre>
 * 일반회원관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 일반회원관리를 위한 Controller
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mpa")
public class MPAUserController {

    private final MPAUserService mpaUserService;

    @Value("${app.site.name}")
    private String siteName;
    //이메일 발송
    private final COMessageService cOMessageService;

    String tableNm = "MEM_MOD_SEQ";

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getUserListPage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));

        return "mngwserc/mp/mpa/MPAUserList.admin";
    }

    /**
     * 일반회원관리 목록으로 이동한다.
     */
    @PostMapping(value = "/select")
    public String selectUserListPageAjax(@Valid MPAUserDto mpaUserDto ,
                                         ModelMap modelMap ) throws Exception {
        mpaUserDto.setMemCd("CO");
        mpaUserDto.setExcelYn("N");
        modelMap.addAttribute("rtnData", mpaUserService.selectUserList(mpaUserDto));

        return "mngwserc/mp/mpa/MPAUserListAjax";
    }

    /**
     * 미래차공모전 리스트 조회
     */
    @PostMapping(value = "/select-tab-two")
    public String selectUserListPageTabTwoAjax(MPAAttctnDto mpaAttctnDto ,
                                               ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectAttcntList(mpaAttctnDto));

        return "mngwserc/mp/mpa/MPAUserTabTwoAjax";
    }

    /**
     * 문의 내역 리스트 조회
     */
    @PostMapping(value = "/select-tab-three")
    public String selectUserListPageTabThreeAjax(MPAInqrDto mpaInqrDto ,
                                                 ModelMap modelMap ) throws Exception {

        modelMap.addAttribute("rtnData", mpaUserService.selectInqrList(mpaInqrDto));


        return "mngwserc/mp/mpa/MPAUserTabThreeAjax";
    }

    /**
     * 관리자 상세 페이지
     */
    @RequestMapping(value="/write")
    public String getAdmWritePage(MPAUserDto mpaUserDto ,
                                  ModelMap modelMap ) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");

            modelMap.addAttribute("rtnData", mpaUserDto);


            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", mpaUserService.selectUserDtl(mpaUserDto));
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

        return "mngwserc/mp/mpa/MPAUserWrite.admin";
    }

    /**
     * 이메일 중복 체크
     */
    @PostMapping(value="/dup-email")
    public String selectDupEmail(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            String chk;
            if(mpaUserService.selectDupEmail(mpaUserDto) >=1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

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
     * id 중복 체크
     */
    @PostMapping(value="/dup-id")
    public String selectDupId(MPAUserDto mpaUserDto ,
                              ModelMap modelMap ) throws Exception
    {
        try
        {
            String chk;
            if(mpaUserService.selectDupId(mpaUserDto) >=1) {
                chk = "N";
            } else {
                chk = "Y";
            }
            modelMap.addAttribute("dupChk", chk);

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
     * 일반사용자  상세 페이지
     */
    @PostMapping(value="/select-tab-one")
    public String getUserDtlAjax(MPAUserDto mpaUserDto ,
                                 ModelMap modelMap ) throws Exception
    {
        try
        {
            if(!"".equals(mpaUserDto.getDetailsKey())){
                modelMap.addAttribute("rtnDtl", mpaUserService.selectUserDtlTab(mpaUserDto));

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

        return  "mngwserc/mp/mpa/MPAUserTabOneAjax";
    }

    /**
     * 사용자 수정
     */
    @PostMapping(value="/update")
    public String insertPartsCompany(MPAUserDto mpaUserDto ,
                                     ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            mpaUserDto.setModCd("AD"); //임의 관리자 cd
            mpaUserDto.setLgnSsnId(cOUserDetailsDTO.getId());
            mpaUserDto.setRegId( cOUserDetailsDTO.getId() );
            mpaUserDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
            mpaUserDto.setModId( cOUserDetailsDTO.getId() );
            mpaUserDto.setMemSeq(Integer.valueOf(mpaUserDto.getDetailsKey()));
            mpaUserDto.setTableNm(tableNm);

            String emailYn = "N";
            String fndnNtfyRcvYn = mpaUserDto.getFndnNtfyRcvYn();
            String fndnChk = "N";
            String smsYn = "N";

            if(!mpaUserDto.getMemCd().equals("CS")) {
                if (fndnNtfyRcvYn.equals("Y")) {
                    if (mpaUserDto.getNtfyEmailRcvYn().equals('N') && mpaUserDto.getNtfySmsRcvYn().equals('N')) {
                        fndnChk = "Y";
                        mpaUserDto.setFndnNtfyRcvYn("N");
                    }
                }

                if (fndnNtfyRcvYn.equals("N")) {
                    if (mpaUserDto.getNtfyEmailRcvYn().equals('Y') || mpaUserDto.getNtfySmsRcvYn().equals('Y')) {
                        fndnChk = "Y";
                        mpaUserDto.setFndnNtfyRcvYn("Y");
                    }
                }



                if (!mpaUserDto.getOldEmailRcv().equals(String.valueOf(mpaUserDto.getNtfyEmailRcvYn()))) {
                    emailYn = "Y";
                }

                mpaUserDto.setChngEmail(emailYn);


                if (!mpaUserDto.getOldSmsRcv().equals(String.valueOf(mpaUserDto.getNtfySmsRcvYn()))) {
                    smsYn = "Y";
                }

                mpaUserDto.setChngSms(smsYn);
            }

            mpaUserDto.setChngFndn(fndnChk);

            modelMap.addAttribute("respCnt", mpaUserService.updateUserDtl(mpaUserDto));

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


    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(MPAUserDto mpaUserDto ,
                                    HttpServletResponse response) throws Exception
    {
        try
        {
            mpaUserDto.setMemCd("CO");
            mpaUserDto.setExcelYn("Y");
            //엑셀 생성
            mpaUserService.excelDownload(mpaUserService.selectUserList(mpaUserDto), response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 비밀번호 초기화
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value="/pwd-init")
    public String updatePwdInit(@RequestParam("id") String id) throws Exception
    {
        MPPwdInitDto mpPwdInitDto = new MPPwdInitDto();
        int actCnt = 0;
        try
        {
            mpPwdInitDto.setId(id);
            actCnt = mpaUserService.updatePwdInit(mpPwdInitDto);

            if (actCnt > 0)
            {
                //이메일 발송
                COMailDTO cOMailDTO = new COMailDTO();
                cOMailDTO.setSubject("["+siteName+"] 임시비밀번호 발급 안내");
                //인증요청일시
                String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
                //수신자 정보
                COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                //이메일
                receiverDto.setEmail(mpPwdInitDto.getEmail());
                //이름
                receiverDto.setName("");
                //치환문자1
                receiverDto.setNote1(mpPwdInitDto.getPwd());
                //치환문자2
                receiverDto.setNote2(field2);
                //치환문자3
                receiverDto.setNote3(field2);
                if(mpPwdInitDto.getMemCd().equals("CO")) {
                    receiverDto.setNote3("일반 사용자");
                } else if(mpPwdInitDto.getMemCd().equals("CP")) {
                    receiverDto.setNote3("부품 사회원");
                } else if(mpPwdInitDto.getMemCd().equals("CS")) {
                    receiverDto.setNote3("위원");
                }
                //수신자 정보 등록
                cOMailDTO.getReceiver().add(receiverDto);
                cOMessageService.sendMail(cOMailDTO, "UserPwdInit.html");
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