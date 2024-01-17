package com.kap.front.controller.mp.mpf;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.core.dto.mp.mpf.MPFFileDto;
import com.kap.core.utility.COFileUtil;
import com.kap.service.*;
import com.kap.service.mp.mpd.MPDCmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <pre>
 * 근태 관리 위한 Controller
 * </pre>
 *
 * @ClassName		: MPAUserController.java
 * @Description		: 근태 관리  위한 Controller
 * @author 양현우
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		양현우				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/member/attend")
public class MPFCmssrAttendController {

    //코드 서비스
    private final COCodeService cOCodeService;

    private final MPDCmtService mpdCmtService;

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;
    //파일 업로드 확장자
    @Value("${app.file.fileExtns}")
    private String imgUploadFileExtns;

    /**
     * 근태 페이지
     * @return
     * @throws Exception
     */
    @GetMapping("/attend-page")
    public String getAttendPage(ModelMap modelMap) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        if(!cOUserDetailsDTO.getAuthCd().equals("CS")) {
            return "redirect:/";
        }

        Date now = new Date();
        String now_dt = format.format(now);
        String[] split = now_dt.split("-");
        HashMap<String, Object> dtMap = new HashMap<>();
        dtMap.put("date", split);

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
//        // 코드 set
        cdDtlList.add("CMSSR_ATTEND");

        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

        MPDKenDto mpdKenDto = new MPDKenDto();
        mpdKenDto.setYear(split[0]);
        mpdKenDto.setMnth(split[1]);
        mpdKenDto.setDd(split[2]);
        mpdKenDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
        modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("now_dt", dtMap);
        modelMap.addAttribute("rtnData", mpdCmtService.selectKenMonthCnt(mpdKenDto));

        modelMap.addAttribute("rtnDataCmpn", mpdCmtService.selectKenCmpnDtl(mpdKenDto));
        modelMap.addAttribute("cmpnData", mpdCmtService.selectKenCmpnList(mpdKenDto));

        return "/front/mp/mpf/MPFCmssrAttend.front";

    }

    /**
     * 근태  입력
     * @param mpdKenDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/insert-attend")
    public String insertAtendc(MPDKenDto mpdKenDto) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String now_dt = format.format(now);
            String[] split = now_dt.split("-");
            mpdKenDto.setYear(split[0]);
            mpdKenDto.setMnth(split[1]);
            mpdKenDto.setDd(split[2]);
            mpdKenDto.setAtndcDt(now_dt);
            mpdKenDto.setDetailsKey(String.valueOf(cOUserDetailsDTO.getSeq()));
            mpdKenDto.setRegId(cOUserDetailsDTO.getId());
            mpdKenDto.setRegIp(cOUserDetailsDTO.getLoginIp());
            mpdKenDto.setModId(cOUserDetailsDTO.getId());
            mpdKenDto.setModIp(cOUserDetailsDTO.getLoginIp());

            if(mpdKenDto.getAtndcCd().equals("CMSSR_ATTEND_001")) {
                if(mpdKenDto.getGuidePartCmpn1()!= "") {
                    String[] gudiePartCmpn1Split = mpdKenDto.getGuidePartCmpn1().split("/");
                    mpdKenDto.setGuidePartCmpn1(gudiePartCmpn1Split[0]);
                    mpdKenDto.setRgns1(gudiePartCmpn1Split[1]);
                    mpdKenDto.setCnstgSeq1(gudiePartCmpn1Split[2]);
                }
                if(mpdKenDto.getGuidePartCmpn2()!= "") {
                    String[] gudiePartCmpn2Split = mpdKenDto.getGuidePartCmpn2().split("/");
                    mpdKenDto.setGuidePartCmpn2(gudiePartCmpn2Split[0]);
                    mpdKenDto.setRgns2(gudiePartCmpn2Split[1]);
                    mpdKenDto.setCnstgSeq2(gudiePartCmpn2Split[2]);
                }
            }

            mpdCmtService.insertAtend(mpdKenDto);

        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }

    /**
     * 파일 찾기 시 파일 업로드
     * @param multiRequest
     * @param coFileDTO
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/insert-fileUpload")
    public String testFileUpload(final MultipartHttpServletRequest multiRequest,COFileDTO coFileDTO ,ModelMap modelMap) throws Exception
    {
        List<COFileDTO> result = null;

        Map<String, MultipartFile> files = multiRequest.getFileMap();
        int atchFileCnt = 0;
        if (!files.isEmpty())
        {
            result = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 10485760);
            if (result.size() > 0)
            {
                if(result.get(0).getFileExtn() == null) {
                    JSONObject errObj = new JSONObject();
                    errObj.put("message", "파일 업로드에 실패하였습니다. \n관리자에게 문의해주세요.");
                    modelMap.addAttribute("error", errObj);
                } else {
                    if (!imgUploadFileExtns.contains(result.get(0).getFileExtn())) {
                        JSONObject errObj = new JSONObject();
                        errObj.put("message", "파일 업로드에 실패하였습니다. \n관리자에게 문의해주세요.");
                        modelMap.addAttribute("error", errObj);
                    }

                    if (coFileDTO != null) {
                        result.get(0).setFileSeq(coFileDTO.getFileSeq());
                        result.get(0).setFileOrd(coFileDTO.getFileOrd());
                    }

                    result.get(0).setStatus("success");
                }
            }
        }
        modelMap.addAttribute("fileName", result);

        return "jsonView";

    }

    /**
     * 파일 조회
     * @param mpfFileDto
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/select-file")
    public String selectImage(MPFFileDto mpfFileDto , ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnKick" ,mpdCmtService.selectKenCmpnKickImage(mpfFileDto));
            modelMap.addAttribute("rtnLvl" ,mpdCmtService.selectKenCmpnLvlImage(mpfFileDto));

        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";

    }

    /**
     * 파일 수정
     * @param mpfFileDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/update-consult")
    public String updateCnstg(MPFFileDto mpfFileDto ) throws Exception
    {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        mpfFileDto.setModId(cOUserDetailsDTO.getId());
        mpfFileDto.setModIp(cOUserDetailsDTO.getLoginIp());
        mpdCmtService.updateCnstgRsumeMst(mpfFileDto);

        return "jsonView";

    }

}
