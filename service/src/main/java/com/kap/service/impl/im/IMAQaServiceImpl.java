package com.kap.service.impl.im;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COFileService;
import com.kap.service.COMailService;
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
    private final COMailService cOMailService;

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
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            pIMAQaDTO.setRegId(coaAdmDTO.getId());
            pIMAQaDTO.setRegIp(coaAdmDTO.getLoginIp());

            //파일
            List<COFileDTO> uploadFiles = new ArrayList<>();
            if (pIMAQaDTO.getFileList() != null && pIMAQaDTO.getFileList().size() > 0) {
                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pIMAQaDTO.getFileList());
                pIMAQaDTO.setRplyFileSeq(fileSeqMap.get("rplyFileSeq"));
                uploadFiles = cOFileService.getFileInfs(fileSeqMap.get("rplyFileSeq"));
            }

            //메일 발송
            COMailDTO mailForm = new COMailDTO();
            mailForm.setEmails(pIMAQaDTO.getEmail());
//            mailForm.setEmails("jds7447@easymedia.net");
            mailForm.setName(pIMAQaDTO.getRegName());
            mailForm.setSubject("[ " + pIMAQaDTO.getRegName() + " ] 님의 [ " + pIMAQaDTO.getParntCtgryNm() + " > " + pIMAQaDTO.getCtgryNm() + " ] 문의에 대한 답변 드립니다");
            if (uploadFiles != null && uploadFiles.size() > 0) {
                mailForm.setFileList(uploadFiles);
            }
            mailForm.setField1(pIMAQaDTO.getParntCtgryNm());
            mailForm.setField2(pIMAQaDTO.getCtgryNm());
            mailForm.setField3(pIMAQaDTO.getTitl());
            mailForm.setField4(pIMAQaDTO.getCntn());
            mailForm.setField5(pIMAQaDTO.getRplyCntn());
            cOMailService.sendMail(mailForm, "IMAQaRply.html");

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
