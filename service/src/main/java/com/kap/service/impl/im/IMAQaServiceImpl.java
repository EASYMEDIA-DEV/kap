package com.kap.service.impl.im;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.core.dto.im.ima.IMAQaPicDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COMessageService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import com.kap.service.dao.im.IMAQaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1:1 문의 serviceImpl
 *
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.01  장두석         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IMAQaServiceImpl implements IMAQaService {

    /** Mapper **/
    private final IMAQaMapper iMAQaMapper;

    /** Service **/
    private final COFileService cOFileService;
    private final COFileUtil cOFileUtil;
    private final COMessageService cOMessageService;

    /** Sequence **/
    /* 답변 시퀀스 */
    private final EgovIdGnrService rplyIdgen;
    /* 문의 담당자 시퀀스 */
    private final EgovIdGnrService qaPicIdgen;
    /* 문의 시퀀스 */
    private final EgovIdGnrService qstnIdgen;


    /**
     * 1:1 문의 목록 조회
     */
    public IMAQaDTO selectQaList(IMAQaDTO pIMAQaDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pIMAQaDTO.getPageIndex());
        page.setRecordCountPerPage(pIMAQaDTO.getListRowSize());

        page.setPageSize(pIMAQaDTO.getPageRowSize());

        pIMAQaDTO.setFirstIndex(page.getFirstRecordIndex());
        pIMAQaDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pIMAQaDTO.setTotalCount(iMAQaMapper.getQaListTotCnt(pIMAQaDTO));
        pIMAQaDTO.setList(iMAQaMapper.selectQaList(pIMAQaDTO));
        return pIMAQaDTO;
    }

    /**
     * 1:1 문의 상세
     */
    public IMAQaDTO selectQaDtl(IMAQaDTO pIMAQaDTO) throws Exception{
        IMAQaDTO rtnData;

        pIMAQaDTO.setDetailsKey(pIMAQaDTO.getDetailsKey());

        if(null == pIMAQaDTO.getMypageYn() || !"Y".equals(pIMAQaDTO.getMypageYn())) {
            if (null != pIMAQaDTO.getRsumeCd() && "SYN".equals(pIMAQaDTO.getRsumeCd())) {
                pIMAQaDTO.setRsumeCd("SYNACK");
                iMAQaMapper.updateQaRsume(pIMAQaDTO);
            }
        }

        rtnData = iMAQaMapper.selectQaDtl(pIMAQaDTO);

        if(rtnData.getFileSeq() != null) {
            rtnData.setFileList(cOFileService.getFileInfs(rtnData.getFileSeq()));
        }
        if(rtnData.getRplyFileSeq() != null) {
            rtnData.setRplyFileList(cOFileService.getFileInfs(rtnData.getRplyFileSeq()));
        }

        return rtnData;
    }

    /**
     * 1:1 문의 답변 등록
     */
    public int insertQaRply(IMAQaDTO pIMAQaDTO) throws Exception {
        if(pIMAQaDTO.getRsumeCd().equals("SYNACK")) {
            //등록자
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            pIMAQaDTO.setRegId(cOUserDetailsDTO.getId());
            pIMAQaDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            //파일
//            List<COFileDTO> uploadFiles = new ArrayList<>();
            if (pIMAQaDTO.getFileList() != null && pIMAQaDTO.getFileList().size() > 0) {
                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pIMAQaDTO.getFileList());
                pIMAQaDTO.setRplyFileSeq(fileSeqMap.get("rplyFileSeq"));
//                uploadFiles = cOFileService.getFileInfs(fileSeqMap.get("rplyFileSeq"));
            }

            //메일 발송
            COMailDTO mailForm = new COMailDTO();
            mailForm.setSubject("[ " + pIMAQaDTO.getRegName() + " ] 님의 [ " + pIMAQaDTO.getParntCtgryNm() + " > " + pIMAQaDTO.getCtgryNm() + " ] 문의에 대한 답변 드립니다");
            //첨부파일 처리 (안내 메일이라 제외)
            /*if (uploadFiles != null && uploadFiles.size() > 0) {
                String fileUrl = "";
                String fileName = "";
                for(COFileDTO cOFileDTO : uploadFiles){
                    fileUrl  +=  ( "".equals(fileUrl) ? "" : "|") + cOFileDTO.getWebPath();
                    fileName +=  ( "".equals(fileUrl) ? "" : "|") + cOFileDTO.getOrgnFileNm();
                }
                mailForm.setFile_url(fileUrl);
                mailForm.setFile_name(fileName);
            }*/
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(pIMAQaDTO.getEmail());
            //이름
            receiverDto.setName(pIMAQaDTO.getRegName());
            //치환문자1
            receiverDto.setNote1(pIMAQaDTO.getRegName());
            //치환문자2
            receiverDto.setNote2(pIMAQaDTO.getParntCtgryNm());
            //치환문자3
            receiverDto.setNote3(pIMAQaDTO.getCtgryNm());
            //치환문자4
            receiverDto.setNote4(pIMAQaDTO.getTitl());
            //치환문자5
            receiverDto.setNote5(pIMAQaDTO.getRegDtm());
            //수신자 정보 등록
            mailForm.getReceiver().add(receiverDto);
            cOMessageService.sendMail(mailForm, "IMAQaRply.html");

            //진행상태
            pIMAQaDTO.setRsumeCd("ACK");
            iMAQaMapper.updateQaRsume(pIMAQaDTO);

            //답변 등록
            pIMAQaDTO.setQaRplySeq(rplyIdgen.getNextIntegerId());

            return iMAQaMapper.insertQaRply(pIMAQaDTO);
        } else {
            return 0;
        }
    }

    /**
     * 1:1 문의 담당자 등록
     */
    public int insertQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception {

        //등록자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pIMAQaPicDTO.setRegId(cOUserDetailsDTO.getId());
        pIMAQaPicDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //담당자 등록
        pIMAQaPicDTO.setPicSeq(qaPicIdgen.getNextIntegerId());

        return iMAQaMapper.insertQaPic(pIMAQaPicDTO);
    }

    /**
     * 1:1 문의 담당자 수정
     */
    public int updateQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception {

        //수정자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pIMAQaPicDTO.setModId(cOUserDetailsDTO.getId());
        pIMAQaPicDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        return iMAQaMapper.updateQaPic(pIMAQaPicDTO);
    }

    /**
     * 1:1 문의 담당자 삭제
     */
    public int deleteQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception {

        return iMAQaMapper.deleteQaPic(pIMAQaPicDTO);
    }

    /**
     * 1:1 문의 담당자 목록 조회
     */
    public IMAQaPicDTO selectQaPicList(IMAQaPicDTO pIMAQaPicDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pIMAQaPicDTO.getPageIndex());
        page.setRecordCountPerPage(pIMAQaPicDTO.getListRowSize());

        page.setPageSize(pIMAQaPicDTO.getPageRowSize());

        pIMAQaPicDTO.setFirstIndex(page.getFirstRecordIndex());
        pIMAQaPicDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pIMAQaPicDTO.setTotalCount(iMAQaMapper.getQaPicListTotCnt(pIMAQaPicDTO));
        pIMAQaPicDTO.setList(iMAQaMapper.selectQaPicList(pIMAQaPicDTO));
        return pIMAQaPicDTO;
    }

    /**
     * 1:1 문의 유형별 담당자 등록 개수 조회
     */
    public int getQaPicCnt(IMAQaPicDTO pIMAQaPicDTO) throws Exception{

        return iMAQaMapper.getQaPicCnt(pIMAQaPicDTO);
    }

    /**
     * 1:1 문의 담당자 상세
     */
    public IMAQaPicDTO selectQaPicDtl(IMAQaPicDTO pIMAQaPicDTO) throws Exception{
        return iMAQaMapper.selectQaPicDtl(pIMAQaPicDTO);
    }



    /**
     * 1:1 문의 등록 (사용자)
     */
    @Transactional
    public int insertQa(IMAQaDTO pIMAQaDTO, MultipartFile[] atchFile) throws Exception {
        /* 첨부파일 처리 */
        if(atchFile[0].getSize() > 0) {
            int fileCnt = atchFile.length;
            List<COFileDTO> rtnList = null;
            Map<String, MultipartFile> files = new HashMap<>();
            for (int i = 0; i < fileCnt; i++) {
                files.put("file" + i, atchFile[i]);
            }

            rtnList = cOFileUtil.parseFileInf(files, "", 1, "", "file", 0);

            List<COFileDTO> fileList = new ArrayList();
            for (int j = 0; j < fileCnt; j++) {
                rtnList.get(j).setStatus("success");
                rtnList.get(j).setFieldNm("fileSeq");
                fileList.add(rtnList.get(j));
            }

            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
            pIMAQaDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }


        /* 메일에 표기될 문의 접수일 날짜 셋팅 */
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        System.out.println("현재 날짜: " + formattedDate);


        /* 문의 작성자에게 보내는 메일 처리 */
        COMailDTO toUserMailForm = new COMailDTO();
        toUserMailForm.setSubject("[ " + pIMAQaDTO.getRegName() + " ] 님의 [ " + pIMAQaDTO.getParntCtgryNm() + " > " + pIMAQaDTO.getCtgryNm() + " ] 문의가 접수 되었습니다");
        //수신자 정보
        COMessageReceiverDTO userReceiverDto = new COMessageReceiverDTO();
        //이메일
        userReceiverDto.setEmail(pIMAQaDTO.getEmail());
        //이름
        userReceiverDto.setName(pIMAQaDTO.getRegName());
        //치환문자1
        userReceiverDto.setNote1(pIMAQaDTO.getRegName());
        //치환문자2
        userReceiverDto.setNote2(pIMAQaDTO.getParntCtgryNm());
        //치환문자3
        userReceiverDto.setNote3(pIMAQaDTO.getCtgryNm());
        //치환문자4
        userReceiverDto.setNote4(pIMAQaDTO.getTitl());
        //치환문자5
        userReceiverDto.setNote5(formattedDate);
        //수신자 정보 등록
        toUserMailForm.getReceiver().add(userReceiverDto);
        //메일 발송
        cOMessageService.sendMail(toUserMailForm, "IMAQaToUser.html");


        /* 문의 담당자에게 보내는 메일 처리 */
        List<IMAQaPicDTO> mailToList = iMAQaMapper.selectQaPicCtgryList(pIMAQaDTO);

        if(mailToList.size() > 0) {
            COMailDTO toMngMailForm = new COMailDTO();
            toMngMailForm.setSubject("[ " + pIMAQaDTO.getRegName() + " ] 님의 [ " + pIMAQaDTO.getParntCtgryNm() + " > " + pIMAQaDTO.getCtgryNm() + " ] 문의가 접수 되었습니다");
            //수신자 정보
            for(IMAQaPicDTO pic : mailToList) {
                COMessageReceiverDTO mngReceiverDto = new COMessageReceiverDTO();
                //이메일
                mngReceiverDto.setEmail(pic.getPiceMail());
                //이름
                mngReceiverDto.setName(pic.getPicNm());
                //치환문자1
                mngReceiverDto.setNote1(pIMAQaDTO.getParntCtgryNm());
                //치환문자2
                mngReceiverDto.setNote2(pIMAQaDTO.getCtgryNm());
                //치환문자3
                mngReceiverDto.setNote3(pIMAQaDTO.getTitl());
                //치환문자4
                mngReceiverDto.setNote4(pIMAQaDTO.getRegName());
                //치환문자5
                mngReceiverDto.setNote5(formattedDate);
                //수신자 정보 등록
                toMngMailForm.getReceiver().add(mngReceiverDto);
            }
            //메일 발송
            cOMessageService.sendMail(toMngMailForm, "IMAQaToMng.html");
        }

        pIMAQaDTO.setQaSeq(qstnIdgen.getNextIntegerId());

        iMAQaMapper.insertQaCtgry(pIMAQaDTO);

        return iMAQaMapper.insertQa(pIMAQaDTO);
    }

}
