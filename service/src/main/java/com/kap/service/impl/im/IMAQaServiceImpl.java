package com.kap.service.impl.im;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COFileService;
import com.kap.service.COMessageService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import com.kap.service.dao.im.IMAQaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private final COMessageService cOMessageService;

    /** Sequence **/
    /* 관리자 싴퉌스 */
    private final EgovIdGnrService rplyIdgen;
//    String tableNm = "RPLY_SEQ";
//    private final COSeqGnrService cOSeqGnrService;


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
        pIMAQaDTO.setDetailsKey(pIMAQaDTO.getDetailsKey());

        if(pIMAQaDTO.getRsumeCd().equals("SYN")) {
            pIMAQaDTO.setRsumeCd("SYNACK");
            iMAQaMapper.updateQaRsume(pIMAQaDTO);
        }

        return iMAQaMapper.selectQaDtl(pIMAQaDTO);
    }

    /**
     * 1:1 문의 답변 등록
     */
    public int insertQa(IMAQaDTO pIMAQaDTO, HttpServletRequest request) throws Exception {
        if(pIMAQaDTO.getRsumeCd().equals("SYNACK")) {
            //등록자
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            pIMAQaDTO.setRegId(cOUserDetailsDTO.getId());
            pIMAQaDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            //파일
            List<COFileDTO> uploadFiles = new ArrayList<>();
            if (pIMAQaDTO.getFileList() != null && pIMAQaDTO.getFileList().size() > 0) {
                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pIMAQaDTO.getFileList());
                pIMAQaDTO.setRplyFileSeq(fileSeqMap.get("rplyFileSeq"));
                uploadFiles = cOFileService.getFileInfs(fileSeqMap.get("rplyFileSeq"));
            }

            //메일 발송
            COMailDTO mailForm = new COMailDTO();
            mailForm.setSubject("[ " + pIMAQaDTO.getRegName() + " ] 님의 [ " + pIMAQaDTO.getParntCtgryNm() + " > " + pIMAQaDTO.getCtgryNm() + " ] 문의에 대한 답변 드립니다");
            //첨부파일 처리
            if (uploadFiles != null && uploadFiles.size() > 0) {
                String fileUrl = "";
                String fileName = "";
                for(COFileDTO cOFileDTO : uploadFiles){
                    fileUrl  +=  ( "".equals(fileUrl) ? "" : "|") + cOFileDTO.getWebPath();
                    fileName +=  ( "".equals(fileUrl) ? "" : "|") + cOFileDTO.getOrgnFileNm();
                }
                mailForm.setFile_url(fileUrl);
                mailForm.setFile_name(fileName);
            }
            //수신자 정보
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            //이메일
            receiverDto.setEmail(pIMAQaDTO.getEmail());
            //이름
            receiverDto.setName(pIMAQaDTO.getRegName());
            //치환문자1
            receiverDto.setNote1(pIMAQaDTO.getParntCtgryNm());
            //치환문자2
            receiverDto.setNote2(pIMAQaDTO.getCtgryNm());
            //치환문자3
            receiverDto.setNote3(pIMAQaDTO.getTitl());
            //치환문자4
            receiverDto.setNote4(pIMAQaDTO.getCntn());
            //치환문자5
            receiverDto.setNote5(pIMAQaDTO.getRplyCntn());
            //수신자 정보 등록
            mailForm.getReceiver().add(receiverDto);
            cOMessageService.sendMail(mailForm, "IMAQaRply.html");

            //진행상태
            pIMAQaDTO.setRsumeCd("ACK");
            iMAQaMapper.updateQaRsume(pIMAQaDTO);

            //답변 등록
            pIMAQaDTO.setQaRplySeq(rplyIdgen.getNextIntegerId());

            return iMAQaMapper.insertQa(pIMAQaDTO);
        } else {
            return 0;
        }
    }
}
