package com.kap.service.impl.im;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.IMAQaDTO;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.IMAQaService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.im.IMAQaMapper;
import com.kap.service.impl.COMailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    //Mapper
    private final IMAQaMapper iMAQaMapper;

    private final COSeqGnrService cOSeqGnrService;
    //파일 서비스
    private final COFileService cOFileService;
    // DAO
    private final COFileMapper cOFileMapper;
    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    //메일
    private COMailServiceImpl cOMailServiceImpl;

    String tableNm = "RPLY_SEQ";

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
     * 1:1 문의 답변 등록
     */
    public int insertQa(IMAQaDTO pIMAQaDTO) throws Exception {
        //메일 발송
        COMailDTO mailForm = new COMailDTO();
        mailForm.setEmails(pIMAQaDTO.getEmail());
        mailForm.setName(pIMAQaDTO.getRegName());
        mailForm.setSubject(pIMAQaDTO.getRegName() + "님의" + pIMAQaDTO.getInqFir() + " > " + pIMAQaDTO.getInqSec() + " 문의에 대한 답변 드립니다");
        mailForm.setFileList(pIMAQaDTO.getFileList());
        mailForm.setField1(pIMAQaDTO.getInqFir());
        mailForm.setField2(pIMAQaDTO.getInqSec());
        mailForm.setField3(pIMAQaDTO.getTitl());
        mailForm.setField4(pIMAQaDTO.getCntn());
        mailForm.setField5(pIMAQaDTO.getRplyCntn());
        cOMailServiceImpl.sendMail(mailForm, "IMAQaRply.html");

        //파일
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pIMAQaDTO.getFileList());
        pIMAQaDTO.setRplyFileSeq(fileSeqMap.get("fileSeq"));

        //진행상태
        pIMAQaDTO.setRsumeCd("ACK");
        iMAQaMapper.updateQaRsume(pIMAQaDTO);

        //답변 등록
        pIMAQaDTO.setQaRplySeq(cOSeqGnrService.selectSeq(tableNm));

        return iMAQaMapper.insertQa(pIMAQaDTO);
    }

    /**
     * 1:1 문의 상세
     */
    public IMAQaDTO selectQaDtl(IMAQaDTO pIMAQaDTO) throws Exception{
        pIMAQaDTO.setDetailsKey(pIMAQaDTO.getDetailsKey());

        pIMAQaDTO.setRsumeCd("SYNACK");

        iMAQaMapper.updateQaRsume(pIMAQaDTO);

        return iMAQaMapper.selectQaDtl(pIMAQaDTO);
    }
}
