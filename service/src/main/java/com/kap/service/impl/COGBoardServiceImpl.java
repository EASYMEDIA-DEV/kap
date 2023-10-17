package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COGBoardDTO;
import com.kap.service.COFileService;
import com.kap.service.COGBoardService;
import com.kap.service.COSeqGnrService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.COGBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * COUserDetailsService Helper 클래스
 *
 * @author 임서화
 * @since 2023.09.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.18  임서화         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COGBoardServiceImpl implements COGBoardService {

    //Mapper
    private final COGBoardMapper cOGBoardMapper;

    private final COSeqGnrService cOSeqGnrService;
    //파일 서비스
    private final COFileService cOFileService;
    // DAO
    private final COFileMapper cOFileMapper;
    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    String tableNm = "BOARD_SEQ";

    /**
     * 게시판 조회
     */
    public COGBoardDTO selectBoardList(COGBoardDTO pCOGBoardDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCOGBoardDTO.getPageIndex());
        page.setRecordCountPerPage(pCOGBoardDTO.getListRowSize());

        page.setPageSize(pCOGBoardDTO.getPageRowSize());

        pCOGBoardDTO.setFirstIndex(page.getFirstRecordIndex());
        pCOGBoardDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pCOGBoardDTO.setTotalCount(cOGBoardMapper.getBoardListTotCnt(pCOGBoardDTO));
        pCOGBoardDTO.setList(cOGBoardMapper.selectBoardList(pCOGBoardDTO));
        return pCOGBoardDTO;
    }

    /**
     * 게시판 등록
     */
    public int insertBoard(COGBoardDTO pCOGBoardDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCOGBoardDTO.getFileList());
        pCOGBoardDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        pCOGBoardDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));

        return cOGBoardMapper.insertBoard(pCOGBoardDTO);
    }

    /**
     * 게시판 상세
     */
    public COGBoardDTO selectBoardDtl(COGBoardDTO pCOGBoardDTO) throws Exception{
        pCOGBoardDTO.setDetailsKey(pCOGBoardDTO.getDetailsKey());
        return cOGBoardMapper.selectBoardDtl(pCOGBoardDTO);
    }

    /**
     * 게시판 수정
     */
    public int updateBoard(COGBoardDTO pCOGBoardDTO) throws Exception{
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCOGBoardDTO.getFileList());
        //파일 처리
        pCOGBoardDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        return cOGBoardMapper.updateBoard(pCOGBoardDTO);
    }

    /**
     * 게시판 삭제
     */
    public int deleteBoard(COGBoardDTO pCOGBoardDTO) throws Exception{

        return cOGBoardMapper.deleteBoard(pCOGBoardDTO);
    }
}
