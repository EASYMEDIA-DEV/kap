package com.kap.service.impl.um;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.UMAFtpUploadDTO;
import com.kap.service.COFileService;
import com.kap.service.UMAFtpUploadService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.um.UMAFtpUploadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * UMAFtpUploadService Helper 클래스
 *
 * @author 임서화
 * @since 2023.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.10.17  임서화         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UMAFtpUploadServiceImpl implements UMAFtpUploadService {

    //Mapper
    private final UMAFtpUploadMapper uMAFtpUploadMapper;
    //파일 서비스
    private final COFileService cOFileService;

    /* 이미지 업로드 시퀀스 */
    private final EgovIdGnrService ftpIdgen;

    private final COFileMapper cOFileMapper;
    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    /**
     * FTP 업로드 조회
     */
    public UMAFtpUploadDTO selectUploadFileList(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pUMAFtpUploadDTO.getPageIndex());
        page.setRecordCountPerPage(pUMAFtpUploadDTO.getListRowSize());

        page.setPageSize(pUMAFtpUploadDTO.getPageRowSize());

        pUMAFtpUploadDTO.setFirstIndex( page.getFirstRecordIndex() );
        pUMAFtpUploadDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        pUMAFtpUploadDTO.setList( uMAFtpUploadMapper.selectUploadFileList(pUMAFtpUploadDTO) );
        pUMAFtpUploadDTO.setTotalCount( uMAFtpUploadMapper.getFtpUploadListTotCnt(pUMAFtpUploadDTO) );
        return pUMAFtpUploadDTO;
    }

    /**
     * FTP 업로드 등록
     */
    public int insertUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pUMAFtpUploadDTO.getFileList());
        pUMAFtpUploadDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        pUMAFtpUploadDTO.setPubcSeq(ftpIdgen.getNextIntegerId());

        return uMAFtpUploadMapper.insertUploadFile(pUMAFtpUploadDTO);
    }

    /**
     * FTP 업로드 삭제
     */
    public int deleteUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception{
        return uMAFtpUploadMapper.deleteUploadFile(pUMAFtpUploadDTO);
    }
}
