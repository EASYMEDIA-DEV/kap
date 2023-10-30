package com.kap.service.impl.um;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.UMAFtpUploadDTO;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.UMAFtpUploadService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.um.UMAFtpUploadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    // DAO
    private final COSeqGnrService cOSeqGnrService;

    private final COFileMapper cOFileMapper;
    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    String tableNm = "FTP_SEQ";

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

        System.err.println("pUMAFtpUploadDTO:::"+pUMAFtpUploadDTO);

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
        pUMAFtpUploadDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));

        return uMAFtpUploadMapper.insertUploadFile(pUMAFtpUploadDTO);
    }

    /**
     * FTP 업로드 삭제
     */
    public int deleteUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception{

        return uMAFtpUploadMapper.deleteUploadFile(pUMAFtpUploadDTO);
    }
}
